package nirmalya.aathithya.webmodule.employee.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.employee.model.ExtendExitManagementModel;

@Controller
@RequestMapping(value = "employee/")
public class ExtendExitManagementController {

	Logger logger = LoggerFactory.getLogger(ExtendExitManagementController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("exit")
	public String addExitManagement(Model model, HttpSession session) {

		logger.info("Method : addExitManagement starts");
		try {
			DropDownModel[] name = restClient.getForObject(env.getEmployeeUrl() + "getNamelist", DropDownModel[].class);
			List<DropDownModel> namelist = Arrays.asList(name);
			model.addAttribute("namelist", namelist);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] dept = restClient.getForObject(env.getEmployeeUrl() + "getDeptlist", DropDownModel[].class);
			List<DropDownModel> deptlist = Arrays.asList(dept);
			model.addAttribute("deptlist", deptlist);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] clrncPerson = restClient.getForObject(env.getEmployeeUrl() + "getClrncPersonList",
					DropDownModel[].class);
			List<DropDownModel> clrncPList = Arrays.asList(clrncPerson);
			model.addAttribute("clrncPList", clrncPList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String userId = "";
		String userName = "";
		String userRole = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		
		String splitData[] = userRole.split("r");
        String[] removedNull = Arrays.stream(splitData)
                .filter(value ->value != "" && value.length() > 0)
                .toArray(size -> new String[size]);
		for(String part:removedNull ) {
			String data= "r"+part;
			
			if (data.contentEquals("rol001")) {
				model.addAttribute("adRole", data);
				logger.info("data ==" + data);

			} else if (data.contentEquals("rol003")) {

				model.addAttribute("mrRole", data);
				logger.info("data 1==" + data);
			} else {
				model.addAttribute("empRole", data);
				logger.info("data 2==" + data);
			}
			if (data.contentEquals("rol001") || data.contentEquals("rol002") || data.contentEquals("rol015")) {
				model.addAttribute("hrRole", data);
			}
		}
		
		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("userRole", userRole);
		
		logger.info("Method : addExitManagement ends");

		return "employee/extend-exit-management";
	}

	/*
	 * dropdown for job through ajax
	 */
	@SuppressWarnings("unchecked")

	@GetMapping(value = { "exit-emp-job-ajax" })
	public @ResponseBody JsonResponse<Object> getDesignationList(@RequestParam String name) {
		logger.info("Method : getDesignationList starts");

		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getEmployeeUrl() + "rest-get-designationList?id=" + name,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : getDesignationList ends");
		return res;

	}

	/*
	 * Add Exit Management
	 * 
	 */

	@SuppressWarnings("unchecked")

	@PostMapping("exit-add-details")
	public @ResponseBody JsonResponse<Object> addExitManagement(@RequestBody ExtendExitManagementModel exitModel,
			HttpSession session) {
		logger.info("Method : addExitManagement starts");

		String dateFormat = "";
		String userId = "";
		String organization=""; 
		String orgDivision="";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			dateFormat = (String) session.getAttribute("DATEFORMAT");
		} catch (Exception e) {
			e.printStackTrace();
		}

		exitModel.setCreatedBy(userId);
		exitModel.setOrganization(organization);
		exitModel.setOrgDivision(orgDivision);
		if (exitModel.getReleaseDate() != null && exitModel.getReleaseDate() != "") {
			exitModel.setReleaseDate(DateFormatter.inputDateFormat(exitModel.getReleaseDate(), dateFormat));
		}
		if (exitModel.getResignDate() != null && exitModel.getResignDate() != "") {
			exitModel.setResignDate(DateFormatter.inputDateFormat(exitModel.getResignDate(), dateFormat));
		}

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.postForObject(env.getEmployeeUrl() + "addExitdetails", exitModel, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : addExitManagement ends");
		return resp;
	}

	/*
	 * Add Clearance Details
	 * 
	 */

	@SuppressWarnings("unchecked")
	@PostMapping("exit-save-initiate")
	public @ResponseBody JsonResponse<Object> addClearanceDetails(
			@RequestBody ExtendExitManagementModel exitModel, HttpSession session) {

		logger.info("Method : addClearanceDetails starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";
		String organization=""; 
		String orgDivision="";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		exitModel.setCreatedBy(userId);
		exitModel.setOrganization(organization);
		exitModel.setOrgDivision(orgDivision);

		try {
			resp = restClient.postForObject(env.getEmployeeUrl() + "addinitiatedata", exitModel, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : addClearanceDetails ends");

		return resp;
	}

	/*
	 * View Exit Management
	 */

	@SuppressWarnings("unchecked")

	@GetMapping("exit-view-through-ajax")
	public @ResponseBody List<ExtendExitManagementModel> viewExitManagement(HttpSession session,@RequestParam String userid,
			@RequestParam String roleid) {

		logger.info("Method : viewExitManagement starts");
		JsonResponse<List<ExtendExitManagementModel>> resp = new JsonResponse<List<ExtendExitManagementModel>>();
		String organization=""; 
		String orgDivision="";
		try {
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restClient.getForObject(env.getEmployeeUrl() + "viewExitdetails?userId=" + userid +"&userRole="+ roleid+"&organization="+organization+"&orgDivision="+orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : viewExitManagement ends");
		logger.info("VIEWWWWWWWWEXIT"+resp.getBody());
		return resp.getBody();
	}

	/*
	 * Edit Exit Management details
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("exit-details-edit")
	public @ResponseBody JsonResponse<ExtendExitManagementModel> editExitManagement(@RequestParam String id,
			HttpSession session) {
		logger.info("Method : editExitManagement starts");
		
		JsonResponse<ExtendExitManagementModel> jsonResponse = new JsonResponse<ExtendExitManagementModel>();
		String organization=""; 
		String orgDivision="";
		try {
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			jsonResponse = restClient.getForObject(env.getEmployeeUrl() + "editExitManagement?id=" + id+"&organization="+organization+"&orgDivision="+orgDivision,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		ExtendExitManagementModel exitModel = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<ExtendExitManagementModel>() {
				});

		jsonResponse.setBody(exitModel);
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("Method : editExitManagement ends");
		return jsonResponse;

	}
 

	/*
	 * Delete exit details
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("exit-details-delete")
	public @ResponseBody JsonResponse<ExtendExitManagementModel> deleteExitDetails(@RequestParam String deleteId) {

		logger.info("Method : deleteExitDetails starts");

		JsonResponse<ExtendExitManagementModel> jsonResponse = new JsonResponse<ExtendExitManagementModel>();
logger.info("deleteId==="+deleteId);
		try {
			jsonResponse = restClient.getForObject(env.getEmployeeUrl() + "exitManagementdelete?id=" + deleteId,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("Method : deleteExitDetails ends");
		return jsonResponse;
	}

	/*
	 * Add Finance details
	 */
	@SuppressWarnings("unchecked")

	@PostMapping("exit-add-finance-details")
	public @ResponseBody JsonResponse<Object> addFinanceDetails(@RequestBody ExtendExitManagementModel exitModel,
			HttpSession session) {
		logger.info("Method : addFinanceDetails starts");
		String userId = "";
		String organization=""; 
		String orgDivision="";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		exitModel.setCreatedBy(userId);
		exitModel.setOrganization(organization);
		exitModel.setOrgDivision(orgDivision);
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.postForObject(env.getEmployeeUrl() + "addFinancedetails", exitModel, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : addFinanceDetails ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "exit-get-deptClearanceDetails" })
	public @ResponseBody JsonResponse<Object> viewdeptClearanceDetails(HttpSession session, @RequestParam String userid) {
		logger.info("Method : viewdeptClearanceDetails starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			res = restClient.getForObject(env.getEmployeeUrl() + "get-deptClearanceDetails?userid=" + userid,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : viewdeptClearanceDetails ends");

		logger.info("LISTTTT" + res);
		return res;

	}
	/*
	 * View Exit Clear
	 */

	@SuppressWarnings("unchecked")

	@GetMapping("exit-view-clearance-details")
	public @ResponseBody List<ExtendExitManagementModel> viewExitClearance(HttpSession session,@RequestParam String exitid) {

		logger.info("Method : viewExitClearance starts");
		JsonResponse<List<ExtendExitManagementModel>> resp = new JsonResponse<List<ExtendExitManagementModel>>();
		String organization=""; 
		String orgDivision="";
		String userId="";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restClient.getForObject(env.getEmployeeUrl() + "viewExitClearance?userId=" + userId +"&exitid="+ exitid+"&organization="+organization+"&orgDivision="+orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : viewExitClearance ends");
		logger.info("viewExitClearance=="+resp.getBody());
		return resp.getBody();
	}	
	/*
	 * Edit Exit clearance details
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("exit-details-edit-clearance")
	public @ResponseBody JsonResponse<ExtendExitManagementModel> editClearance(@RequestParam String id,
			HttpSession session) {
		logger.info("Method : editClearance starts");
		
		JsonResponse<ExtendExitManagementModel> jsonResponse = new JsonResponse<ExtendExitManagementModel>();
		String organization=""; 
		String orgDivision="";
		try {
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			jsonResponse = restClient.getForObject(env.getEmployeeUrl() + "editClearance?id=" + id+"&organization="+organization+"&orgDivision="+orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("Method : editClearance ends");
		return jsonResponse;

	}	
	
}