package nirmalya.aathithya.webmodule.employee.controller;

import java.util.ArrayList;
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

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.employee.model.TravelRequisitionModel;
import nirmalya.aathithya.webmodule.master.model.EmpRoleModel;
@Controller
@RequestMapping(value = { "employee/" })
public class TravelRequisitinController {

	Logger logger = LoggerFactory.getLogger(TravelRequisitinController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	RestTemplate restTemplate;
	
	@GetMapping("/travel-requisition")
	public String employee(Model model, HttpSession session) {
		logger.info("Method : reimbursement starts");
		
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
		String[] removedNull = Arrays.stream(splitData).filter(value -> value != "" && value.length() > 0)
				.toArray(size -> new String[size]);
		for (String part : removedNull) {
			String data = "r" + part;

			if (data.contentEquals("rol001") || data.contentEquals("rol003") || data.contentEquals("rol010")) {
				model.addAttribute("hrRole", data);
			}
			if (data.contentEquals("rol001") || data.contentEquals("rol010")) {
				model.addAttribute("adRole", data);
			}
		}
		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("userRole", userRole);
		 
		logger.info("Method : reimbursement ends");
		return "employee/travelRequisition";
	}
	

	
	
	@SuppressWarnings("unchecked")
	@GetMapping("travel-requisition-view-employee")
	public @ResponseBody List<TravelRequisitionModel> viewTravelReq(HttpSession session,@RequestParam String userid,
			@RequestParam String roleid) {

		logger.info("Method : viewTravelReq starts");
		String organization=""; 
		String orgDivision="";
		try {
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		JsonResponse<List<TravelRequisitionModel>> resp = new JsonResponse<List<TravelRequisitionModel>>();
			List<String> roleList = new ArrayList<String>();
			if (roleid != null && roleid != "") {
				String[] arr = roleid.split(",");
				for (int i = 0; i < arr.length; i++) {
					roleList.add(arr[i]);
				}
			}
			EmpRoleModel empModel = new EmpRoleModel();
			empModel.setUserId(userid);
			empModel.setUserRole(roleList);
			empModel.setType("WEB");
			empModel.setOrganization(organization);
			empModel.setOrgDivision(orgDivision);
			
			try {
				resp = restTemplate.postForObject(env.getEmployeeUrl() + "travel-requisition-employee" , empModel, 
						JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}
			
			ObjectMapper mapper = new ObjectMapper();
			List<TravelRequisitionModel> viewRem = mapper.convertValue(resp.getBody(),
					new TypeReference<List<TravelRequisitionModel>>() {
					});	
			for (TravelRequisitionModel a : viewRem) {
				if (a.getAdvanceReq().equals("0")) {
					a.setAdvanceReq("NO");
				}else{
					a.setAdvanceReq("YES");
				}
				if (a.getStatus().equals("0")) {
					a.setStatus("Pending");
				}else if (a.getStatus().equals("1")) {
					a.setStatus("Forwarded");
				}else if (a.getStatus().equals("2")) {
					a.setStatus("Rejected");
				} else if (a.getStatus().equals("3")) {
					a.setStatus("Approved");
				}else{
					a.setStatus("completed");
				}

	 
			}
			resp.setBody(viewRem);
			
			logger.info("Method : viewTravelReq ends");
			return resp.getBody();
		}

	
	/*
	 * post Mapping for adding travel
	 * 
	 */
	@SuppressWarnings({ "unchecked" })
	@PostMapping(value = "travel-requisition-add-travel-details")
	public @ResponseBody JsonResponse<Object> saveTravel(@RequestBody List<TravelRequisitionModel> travelModel,
			HttpSession session) {
		logger.info("Method : saveTravel function starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization=""; 
		String orgDivision="";
		logger.info("web 1===" + travelModel);
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		travelModel.get(0).setCreatedBy(userId);
		travelModel.get(0).setOrganization(organization);
		travelModel.get(0).setOrgDivision(orgDivision);
		try {
			resp = restTemplate.postForObject(env.getEmployeeUrl() + "rest-add-travel", travelModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		/*
		 * if(resp.getCode().equals("success")) { resp.setMessage("Success"); } else {
		 * 
		 * }
		 */
		logger.info("Method : saveTravel function Ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "travel-requisition-edit-employee-trough-ajax" })
	public @ResponseBody List<TravelRequisitionModel> TravelReqEdit(@RequestParam String id,
			HttpSession session) {
		logger.info("Method : TravelReqEdit starts");

		List<TravelRequisitionModel> travelModel = new ArrayList<TravelRequisitionModel>();
		JsonResponse<List<TravelRequisitionModel>> jsonResponse = new JsonResponse<List<TravelRequisitionModel>>();

		if (id != null && id != "") {
			try {
				
				jsonResponse = restClient.getForObject(env.getEmployeeUrl() + "get-travel-edit?id=" + id,
						JsonResponse.class);

				ObjectMapper mapper = new ObjectMapper();

				List<TravelRequisitionModel> addreq = mapper.convertValue(jsonResponse.getBody(),
						new TypeReference<List<TravelRequisitionModel>>() {
						});
logger.info("addreq==="+addreq);

				addreq.forEach(s -> s.setId(s.getServiceId()));

				int count = 0;
				
				for (TravelRequisitionModel m : addreq) {
					
					count ++;
					m.setSlnoId(count);
					
				}
				jsonResponse.setBody(addreq);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
	
		}
		logger.info("Method : TravelReqEdit ends");
		return jsonResponse.getBody();
	}

	//delete travel
	@SuppressWarnings("unchecked")
	@PostMapping(value = "travel-requisition-delete-th-ajax")
	public @ResponseBody JsonResponse<Object> deleteTravel(
			@RequestBody TravelRequisitionModel travelModel, HttpSession session) {
		logger.info("Method : deleteTravel function starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		String userId = "";
		logger.info("masggggggggg"+travelModel.getTravelingReqId());
		try {
			userId = (String) session.getAttribute("USER_ID");
			travelModel.setCreatedBy(userId);
			
		} catch (Exception e) {

		}
		try {  

			res = restTemplate.postForObject(env.getEmployeeUrl() + "rest-delete-travel",
					travelModel, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}


		if (res.getCode().equals("success")) {
			res.setMessage("Success");
		} else {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		}
		logger.info("Method : deleteTravel function Ends");
		return res;
	}
	
	
	
	
	
	
	// approve requisition

	@SuppressWarnings("unchecked")
	@GetMapping("travel-requisition-approve")
	public @ResponseBody JsonResponse<TravelRequisitionModel> approveRequisition(@RequestParam String approveId,String name,String comment,String roleid) {

		logger.info("Method : approveRequisition starts");
		JsonResponse<TravelRequisitionModel> response = new JsonResponse<TravelRequisitionModel>();

		try {
			response = restClient.getForObject(env.getEmployeeUrl() + "approveRequisition?id=" + approveId + "&name=" + name+"&comment="+comment+"&roleid="+roleid,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		if(response.getCode().equals("success")) {
			response.setMessage("Success");
		} else {
			response.setCode(response.getMessage());
			response.setMessage("Unsuccess");
		}
		logger.info("Method : approveRequisition ends");
		return response;
	}
	
	//reject requisition
	
	@SuppressWarnings("unchecked")
	@GetMapping("travel-requisition-reject")
	public @ResponseBody JsonResponse<TravelRequisitionModel> rejectRequisition(@RequestParam String rejectId,String name,String comment,String roleid) {

		logger.info("Method : rejectRequisition starts");
		JsonResponse<TravelRequisitionModel> response = new JsonResponse<TravelRequisitionModel>();

		try {
			response = restClient.getForObject(env.getEmployeeUrl() + "rejectRequisition?id=" + rejectId + "&name=" + name+"&comment="+comment+"&roleid="+roleid,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		if(response.getCode().equals("success")) {
			response.setMessage("Success");
		} else {
			response.setCode(response.getMessage());
			response.setMessage("Unsuccess");
		}
		logger.info("Method : rejectRequisition ends");
		return response;
	}
	
}
