package nirmalya.aathithya.webmodule.master.controller;

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

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.model.EmpRoleModel;
import nirmalya.aathithya.webmodule.master.model.EventActivityModel;
import nirmalya.aathithya.webmodule.master.model.EventManagementModel;

@Controller
@RequestMapping(value = "master")
public class EventManagementController {
	Logger logger = LoggerFactory.getLogger(EventManagementController.class);
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;
	@GetMapping("/event-management")
	public String getMapManageEvent(Model model, HttpSession session) {
		logger.info("Method : getMapManageEvent starts");String userId = "";
		String userName = "";
		String userRole = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		
		//dropdown of event manage page starts here
		try {
			DropDownModel[] organiser = restTemplate.getForObject(env.getMasterUrl() + "rest-getOrganiserList",
					DropDownModel[].class);
			List<DropDownModel> organiserList = Arrays.asList(organiser);
			model.addAttribute("organiserList", organiserList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] eventType = restTemplate.getForObject(env.getMasterUrl() + "rest-getEventTypeList",
					DropDownModel[].class);
			List<DropDownModel> eventTypeList = Arrays.asList(eventType);
			model.addAttribute("eventTypeList", eventTypeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] responsible = restTemplate.getForObject(env.getMasterUrl() + "rest-getResponsibleList",
					DropDownModel[].class);
			List<DropDownModel> responsibleList = Arrays.asList(responsible);
			model.addAttribute("responsibleList", responsibleList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] vanue = restTemplate.getForObject(env.getMasterUrl() + "rest-getVanueList",
					DropDownModel[].class);
			List<DropDownModel> vanueList = Arrays.asList(vanue);
			model.addAttribute("vanueList", vanueList);
		} catch (RestClientException e) {
			e.printStackTrace();
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
			/*
			 * if (data.contentEquals("rol001")) { model.addAttribute("adRole", data);
			 * logger.info("data 2=="+data); }
			 */
		}

		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("userRole", userRole);
		//dropdown of event manage page ends here 
		logger.info("Method : getMapManageEvent starts");
		return "master/event-management";
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("event-management-details-view")
	public @ResponseBody List<EventManagementModel> viewEventManagement(HttpSession session,@RequestParam String userid,
			@RequestParam String roleid) {

		logger.info("Method : viewEventManagement starts");
		JsonResponse<List<EventManagementModel>> resp = new JsonResponse<List<EventManagementModel>>();
 
			List<String> roleList = new ArrayList<String>();
			String organization=""; 
			String orgDivision="";
			try {
				
				organization = (String) session.getAttribute("ORGANIZATION"); 
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
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
				resp = restTemplate.postForObject(env.getMasterUrl() + "viewEventManagement" , empModel, 
						JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}
			ObjectMapper mapper = new ObjectMapper();
			List<EventManagementModel> eventManagementModel = mapper.convertValue(resp.getBody(),
					new TypeReference<List<EventManagementModel>>() {
					});
			String dateFormat = "";
			
			try {
				logger.info("#####"+(String) session.getAttribute("DATEFORMAT"));
				dateFormat = (String) session.getAttribute("DATEFORMAT");
				if(dateFormat == null) {
					dateFormat = "yyyy-MM-dd";
				}
				
			} catch (Exception e) {
			}
			//logger.info(eventManagementModel.get(0).getAdvanceApplyDate());
			//logger.info(eventManagementModel.get(0).getApprovedDate());
		
			if (eventManagementModel != null)
				for (EventManagementModel a : eventManagementModel) {
					if (a.getFromDate() != null && a.getFromDate() != "") {
						a.setFromDate(DateFormatter.dateFormat(a.getFromDate(), dateFormat));
					}
					if (a.getToDate() != null && a.getToDate() != "") {
						a.setToDate(DateFormatter.dateFormat(a.getToDate(), dateFormat));
					}
					if (a.getRegdStartDate() != null && a.getRegdStartDate() != "") {
						a.setRegdStartDate(DateFormatter.dateFormat(a.getRegdStartDate(), dateFormat));
					}
					if (a.getRegdEndDate() != null && a.getRegdEndDate() != "") {
						a.setRegdEndDate(DateFormatter.dateFormat(a.getRegdEndDate(), dateFormat));
					}
					if (a.getCreatedOn() != null && a.getCreatedOn() != "") {
						a.setCreatedOn(DateFormatter.dateFormat(a.getCreatedOn(), dateFormat));
					}
				}
		 
			logger.info("EventManagement VIEWWW" + eventManagementModel);
			logger.info("Method : viewEventManagement ends");
			return eventManagementModel;
		}
	
	
	
	
	//method to save data for event management
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/event-management-add")
	public @ResponseBody JsonResponse<Object> addEvant(@RequestBody EventManagementModel eventManagementModel, Model model,
			HttpSession session) {
		logger.info("Method :addEvant starts");
		JsonResponse<Object> jsonResponse = new JsonResponse<Object>();
		String userId = "";
		String dateFormat = "";
		String organization=""; 
		String orgDivision="";
		try {
			userId = (String) session.getAttribute("USER_ID");
			dateFormat =(String) session.getAttribute("DATEFORMAT");
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (eventManagementModel.getFromDate() != null && eventManagementModel.getFromDate() != "") {
			eventManagementModel.setFromDate(DateFormatter.inputDateFormat(eventManagementModel.getFromDate(), dateFormat));
		}
		if (eventManagementModel.getToDate() != null && eventManagementModel.getToDate() != "") {
			eventManagementModel.setToDate(DateFormatter.inputDateFormat(eventManagementModel.getToDate(), dateFormat));
		}
		if (eventManagementModel.getRegdStartDate() != null && eventManagementModel.getRegdStartDate() != "") {
			eventManagementModel.setRegdStartDate(DateFormatter.inputDateFormat(eventManagementModel.getRegdStartDate(), dateFormat));
		}
		if (eventManagementModel.getRegdEndDate() != null && eventManagementModel.getRegdEndDate() != "") {
			eventManagementModel.setRegdEndDate(DateFormatter.inputDateFormat(eventManagementModel.getRegdEndDate(), dateFormat));
		}

		List<EventActivityModel> activity = eventManagementModel.getActivity();
		for(EventActivityModel m: activity) {
			
			if (m.getEventActivityDate() != null && m.getEventActivityDate() != "") {
				m.setEventActivityDate(DateFormatter.inputDateFormat(m.getEventActivityDate(), dateFormat));
			}
		}
		eventManagementModel.setEventCreatedBy(userId);
		eventManagementModel.setOrganization(organization);
		eventManagementModel.setOrgDivision(orgDivision);
		try {
			jsonResponse = restTemplate.postForObject(env.getMasterUrl() + "rest-add-evant", eventManagementModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String message = jsonResponse.getMessage();
		if (message != null && message != "") {

		} else {
			jsonResponse.setMessage("Success");
		}
		logger.info("Method : addEvant ends");
		return jsonResponse;
	}
	
	// approve leave

		@SuppressWarnings("unchecked")
		@GetMapping("event-management-details-approve")
		public @ResponseBody JsonResponse<EventManagementModel> approveEventManagement(@RequestParam String approveId,
				String name, String comment,String roleid) {

			logger.info("Method : approveEventManagement starts");
			JsonResponse<EventManagementModel> response = new JsonResponse<EventManagementModel>();
			try {
				response = restTemplate.getForObject(env.getMasterUrl() + "approveEventManagement?id=" + approveId + "&name="
						+ name + "&comment=" + comment+"&roleid="+roleid, JsonResponse.class);

			} catch (RestClientException e) {
				e.printStackTrace();
			}
			if (response.getMessage() != null && response.getMessage() != "") {
				response.setCode(response.getMessage());
				response.setMessage("Unsuccess");
			} else {
				response.setMessage("Success");
			}
			logger.info("Method : approveEventManagement ends");
			logger.info("APPROVE"+response);
			return response;
		}

		// reject leave

		@SuppressWarnings("unchecked")
		@GetMapping("event-management-details-reject")
		public @ResponseBody JsonResponse<EventManagementModel> rejectEventManagement(@RequestParam String rejectId,
				String name, String comment) {

			logger.info("Method : rejectEventManagement starts");
			JsonResponse<EventManagementModel> response = new JsonResponse<EventManagementModel>();
			try {
				response = restTemplate.getForObject(
						env.getMasterUrl() + "rejectEventManagement?id=" + rejectId + "&name=" + name + "&comment=" + comment,
						JsonResponse.class);

			} catch (RestClientException e) {
				e.printStackTrace();
			}
			if (response.getMessage() != null && response.getMessage() != "") {
				response.setCode(response.getMessage());
				response.setMessage("Unsuccess");
			} else {
				response.setMessage("Success");
			}
			logger.info("Method : rejectEventManagement ends");
			logger.info("APPROVE"+response);
			return response;
		}
		// edit

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @GetMapping("event-management-details-edit") public @ResponseBody
	 * JsonResponse<EventManagementModel> editEventManagement(@RequestParam String
	 * id, HttpSession session) {
	 * 
	 * logger.info("Method : editEventManagement starts");
	 * JsonResponse<EventManagementModel> jsonResponse = new
	 * JsonResponse<EventManagementModel>(); logger.info("id====" + id); try {
	 * jsonResponse = restTemplate.getForObject(env.getMasterUrl() +
	 * "editEventManagement?id=" + id, JsonResponse.class); } catch
	 * (RestClientException e) { e.printStackTrace(); } ObjectMapper mapper = new
	 * ObjectMapper(); EventManagementModel eventModel =
	 * mapper.convertValue(jsonResponse.getBody(), new
	 * TypeReference<EventManagementModel>() { }); jsonResponse.setBody(eventModel);
	 * if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
	 * jsonResponse.setCode(jsonResponse.getMessage());
	 * jsonResponse.setMessage("Unsuccess"); } else {
	 * jsonResponse.setMessage("Success"); }
	 * 
	 * 
	 * logger.info("Method : editEventManagement ends");
	 * logger.info("editEventManagement=====" + jsonResponse); return
	 * jsonResponse; }
	 */
		
		@SuppressWarnings("unchecked")
		@GetMapping("event-management-details-edit")
		public @ResponseBody List<EventManagementModel> editEventManagement(HttpSession session,@RequestParam String userid) {

			logger.info("Method : editEventManagement starts");
			JsonResponse<List<EventManagementModel>> resp = new JsonResponse<List<EventManagementModel>>();
	 
			//	List<String> roleList = new ArrayList<String>();
				String organization=""; 
				String orgDivision="";
				try {
					
					organization = (String) session.getAttribute("ORGANIZATION"); 
					orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				EmpRoleModel empModel = new EmpRoleModel();

				empModel.setUserId(userid);
				//empModel.setUserRole(roleList);
				empModel.setType("WEB");
				empModel.setOrganization(organization);
				empModel.setOrgDivision(orgDivision);
				
				try {
					resp = restTemplate.postForObject(env.getMasterUrl() + "editEventManagement" , empModel, 
							JsonResponse.class);
				} catch (RestClientException e) {
					e.printStackTrace();
				}
				ObjectMapper mapper = new ObjectMapper();
				List<EventManagementModel> eventManagementModel = mapper.convertValue(resp.getBody(),
						new TypeReference<List<EventManagementModel>>() {
						});
				String dateFormat = "";
				
				try {
					logger.info("#####"+(String) session.getAttribute("DATEFORMAT"));
					dateFormat = (String) session.getAttribute("DATEFORMAT");
					if(dateFormat == null) {
						dateFormat = "yyyy-MM-dd";
					}
					
				} catch (Exception e) {
				}
				//logger.info(eventManagementModel.get(0).getAdvanceApplyDate());
				//logger.info(eventManagementModel.get(0).getApprovedDate());
			
		/*
		 * if (eventManagementModel != null) for (EventManagementModel a :
		 * eventManagementModel) { if (a.getFromDate() != null && a.getFromDate() != "")
		 * { a.setFromDate(DateFormatter.dateFormat(a.getFromDate(), dateFormat)); } if
		 * (a.getToDate() != null && a.getToDate() != "") {
		 * a.setToDate(DateFormatter.dateFormat(a.getToDate(), dateFormat)); } if
		 * (a.getRegdStartDate() != null && a.getRegdStartDate() != "") {
		 * a.setRegdStartDate(DateFormatter.dateFormat(a.getRegdStartDate(),
		 * dateFormat)); } if (a.getRegdEndDate() != null && a.getRegdEndDate() != "") {
		 * a.setRegdEndDate(DateFormatter.dateFormat(a.getRegdEndDate(), dateFormat)); }
		 * }
		 */
				logger.info("EventManagement EDITTTT" + eventManagementModel);
				logger.info("Method : editEventManagement ends");
				return eventManagementModel;
			}
		
		
		//delete 
				@SuppressWarnings("unchecked")
				@GetMapping("event-management-delete")
				public @ResponseBody JsonResponse<Object> deleteEventManagement(@RequestParam String id,
						Model model, HttpSession session) {
					logger.info("Method : deleteEventManagement function starts");

					JsonResponse<Object> res = new JsonResponse<Object>();

					try {
						res = restTemplate.getForObject(env.getMasterUrl() + "deleteEventManagement?id=" + id, JsonResponse.class);
					} catch (RestClientException e) {
						e.printStackTrace();
					}

					String message = res.getMessage();
					if (message != null && message != "") {

					} else {
						res.setMessage("Success");
					}
					logger.info("Method : deleteEventManagement function Ends");
					
					logger.info("deleteEventManagement"+res);
					return res;
				}
}


