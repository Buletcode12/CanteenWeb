package nirmalya.aathithya.webmodule.master.controller;

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
import nirmalya.aathithya.webmodule.master.model.SalaryRevisionModel;

@Controller
@RequestMapping(value = "master/")
public class SalaryRevisionandPromotionController {
	Logger logger = LoggerFactory.getLogger(SalaryRevisionandPromotionController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("view-salary-revision")
	public String leaveApply(Model model, HttpSession session) {
		logger.info("Method : salary starts");
		String organization=""; 
		String orgDivision="";
		try {
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] departmentType = restTemplate.getForObject(env.getMasterUrl()
					+ "getDepartmentTypeForShiftType?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);

			List<DropDownModel> department = Arrays.asList(departmentType);
			model.addAttribute("department", department);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			DropDownModel[] financialYrType = restTemplate
					.getForObject(env.getMasterUrl() + "getFinancialYrForSalaryRevision?organization="+organization+"&orgDivision="+orgDivision, DropDownModel[].class);

			List<DropDownModel> financialYr = Arrays.asList(financialYrType);
			model.addAttribute("financialYr", financialYr);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			DropDownModel[] dropDownModel = restTemplate.getForObject(env.getMasterUrl() + "getBandMasterAttendance?organization=" + organization+"&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> bandType = Arrays.asList(dropDownModel);
			model.addAttribute("bandType", bandType);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] dropDown = restTemplate.getForObject(env.getMasterUrl() + "rest-getDesignationDropDown?organization="+organization+"&orgDivision="+orgDivision,
					DropDownModel[].class);
			List<DropDownModel> Designation = Arrays.asList(dropDown);
			model.addAttribute("Designation", Designation);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String userId = "";
		/*
		 * String organization=""; String orgDivision="";
		 */
		try {
			userId = (String) session.getAttribute("USER_ID"); 
			//organization = (String) session.getAttribute("ORGANIZATION"); 
			//orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION"); 
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		//logger.info("organization=="+organization+"orgDivision=="+orgDivision);
		model.addAttribute("userId", userId);
		
		logger.info("Method : salary ends");
		return "master/salaryRevisionPromotion";
	}
	@SuppressWarnings("unchecked")

	@GetMapping(value = { "view-salary-revision-getSubDepartmentByDept" })
	public @ResponseBody JsonResponse<Object> getSubDepartmentByDepts(HttpSession session,
			@RequestParam String DeptId) {
		logger.info("Method : getSubDepartmentByDepts starts");
		String orgName = "";
		String orgDivision = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "get-shiftSubDepartment?shiftDeptId=" + DeptId
					+ "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : getSubDepartmentByDepts ends");

		logger.info("LISTTTT" + res);
		return res;
	}
	@SuppressWarnings("unchecked")
	@GetMapping("view-salary-revision-getEmpListBySubDept")
	public @ResponseBody JsonResponse<List<DropDownModel>> getEmpListBySubDept(Model model, @RequestParam String subDeptId,
			HttpSession session) {
		logger.info("Method : getEmpListBySubDept starts");
		JsonResponse<List<DropDownModel>> resp = new JsonResponse<List<DropDownModel>>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "get-EmployeeId?subDeptId=" + subDeptId + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("success");
		}
		logger.info("Method :  getEmpListBySubDept ends");
		return resp;
	}
	@SuppressWarnings("unchecked")
	@GetMapping("view-salary-revision-getEmployeeList")
	public @ResponseBody JsonResponse<List<DropDownModel>> getEmployeeList(Model model,HttpSession session) {
		logger.info("Method : getEmployeeList starts");
		JsonResponse<List<DropDownModel>> resp = new JsonResponse<List<DropDownModel>>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "get-employee-list?orgName="+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("success");
		}
		logger.info("Method :  getEmployeeList ends");
		return resp;
	}
	/*
	 *
	 * add salary revision
	 * 
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-salary-revision-save" })
	public @ResponseBody JsonResponse<Object> addsalaryrevision(HttpSession session,
			@RequestBody SalaryRevisionModel data) {
		logger.info("Method : salary-revision starts");

		JsonResponse<Object> res = new JsonResponse<Object>();
		String dateFormat = "";
		String userId = "";
		String organization=""; 
		String orgDivision="";
		try {
			userId = (String) session.getAttribute("USER_ID");
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			data.setEffectiveDate(DateFormatter.inputDateFormat(data.getEffectiveDate(), dateFormat));
		} catch (Exception e) {
			e.printStackTrace();
		}
		data.setCreatedBy(userId);
		data.setOrganization(organization);
		data.setOrgDivision(orgDivision);
		logger.info("data=======" + data);
		try {
			res = restTemplate.postForObject(env.getMasterUrl() + "rest-addnew-salary-revision", data,
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

		logger.info("Method : salary-revision ends");
		logger.info("adddd=======" + res);
		return res;

	}

	/*
	 * get date list
	 */
	@SuppressWarnings("unchecked")

	@GetMapping(value = { "view-salary-revision-date-ajax" })
	public @ResponseBody JsonResponse<Object> getDateList(@RequestParam String name) {
		logger.info("Method : getDateList starts");

		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "get-dateList?id=" + name, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : getDateList ends");
		logger.info("LISTTTT" + res);
		return res;

	}

	// view

	@SuppressWarnings("unchecked")

	@GetMapping("view-salary-revision-view")
	public @ResponseBody List<SalaryRevisionModel> viewSalaryMaster(HttpSession session,@RequestParam String userid) {
		logger.info("Method : viewSalaryMaster starts");

		JsonResponse<List<SalaryRevisionModel>> resp = new JsonResponse<List<SalaryRevisionModel>>();
		String organization=""; 
		String orgDivision="";
		try {
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "viewSalaryMaster?userid="+userid+"&organization="+organization+"&orgDivision="+orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		List<SalaryRevisionModel> Model = mapper.convertValue(resp.getBody(),
				new TypeReference<List<SalaryRevisionModel>>() {
				});
		String dateFormat = "";
		try {
			dateFormat = (String) session.getAttribute("DATEFORMAT");
		} catch (Exception e) {

		}
		for (SalaryRevisionModel a : Model) {
			if (a.getEffectiveDate() != null && a.getEffectiveDate() != "") {
				a.setEffectiveDate(DateFormatter.dateFormat(a.getEffectiveDate(), dateFormat));
			}
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : viewSalaryMaster  ends");
		logger.info("respwwwwwwwwwwwwwwwweb view" + resp);
		logger.info("Viewwww " + Model);
		return Model;

	}

	 // edit 

	@SuppressWarnings("unchecked")
	@GetMapping("view-salary-revision-edit")
	public @ResponseBody JsonResponse<SalaryRevisionModel> editSalaryRevision(@RequestParam String Id,
			HttpSession session) {

		logger.info("Method : editSalaryRevision starts");
		JsonResponse<SalaryRevisionModel> jsonResponse = new JsonResponse<SalaryRevisionModel>();
 
		try {
			jsonResponse = restTemplate.getForObject(env.getMasterUrl() + "rest-salary-revision-edit?id=" + Id,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		
		ObjectMapper mapper = new ObjectMapper();
		SalaryRevisionModel Model = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<SalaryRevisionModel>() {
				});
		/*for (SalaryRevisionModel a : jsonResponse) {
			if (a.getEffectiveDate() != null && a.getEffectiveDate() != "") {
				a.setEffectiveDate(DateFormatter.dateFormat(a.getEffectiveDate(), dateFormat));
			}
		}*/
		jsonResponse.setBody(Model);
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");
		} else {
			jsonResponse.setMessage("Success");
		}
		logger.info("Method : editSalaryRevision ends");
		logger.info("editSalaryRevision====="+jsonResponse);
		return jsonResponse;
	}
	/*
	 * get name and designation list
	 */
	@SuppressWarnings("unchecked")

	@GetMapping(value = { "view-salary-revision-nameDesignation-ajax" })
	public @ResponseBody JsonResponse<Object> getnameAndDesignationList(HttpSession session,@RequestParam String name) {
		logger.info("Method : getnameAndDesignationList starts");

		JsonResponse<Object> res = new JsonResponse<Object>();
		String organization=""; 
		String orgDivision="";
		try {
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "get-nameandDesignationList?id=" + name+"&organization="+organization+"&orgDivision="+orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : getnameAndDesignationList ends");
		
		logger.info("LISTTTT" + res);
		return res;

	}

			
	// delete details
	@SuppressWarnings("unchecked")
	@PostMapping("view-salary-revision-delete")
	public @ResponseBody JsonResponse<Object> deleteSalaryRevision(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : deleteSalaryRevision function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();
		String organization=""; 
		String orgDivision="";
		try {
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "rest-SalaryRevision-delete?id=" + id+"&organization="+organization+"&orgDivision="+orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteSalaryRevision function Ends");

		logger.info("RESPPPPPPP" + res);
		return res;
	}

}
