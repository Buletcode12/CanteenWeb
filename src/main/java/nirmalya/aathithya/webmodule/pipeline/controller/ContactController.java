package nirmalya.aathithya.webmodule.pipeline.controller;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
import nirmalya.aathithya.webmodule.employee.model.EmployeeDocumentModel;
import nirmalya.aathithya.webmodule.pipeline.model.CrmActivityModel;
import nirmalya.aathithya.webmodule.pipeline.model.CrmCallModel;
import nirmalya.aathithya.webmodule.pipeline.model.CrmCampaignModel;
import nirmalya.aathithya.webmodule.pipeline.model.CrmContactModel;
import nirmalya.aathithya.webmodule.pipeline.model.CrmLeadTaskModel;
import nirmalya.aathithya.webmodule.pipeline.model.crmMeetingModel;
import nirmalya.aathithya.webmodule.procurment.model.InventoryVendorDocumentModel;
import nirmalya.aathithya.webmodule.sales.model.SalesOrderNewModel;

@Controller
@Component
@RequestMapping(value = "pipeline")
public class ContactController {
	Logger logger = LoggerFactory.getLogger(ContactController.class);
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;

	/*
	 * get mapping for adding campaign name
	 */

	@GetMapping("/view-crm-contacts")
	public String viewCRMContacts(Model model, HttpSession session) {
		logger.info("Method : viewCRMContacts start");
		CrmContactModel ContactModel = new CrmContactModel();
		CrmContactModel form = (CrmContactModel) session.getAttribute("sContactModel");
		String message = (String) session.getAttribute("message");
		if (message != null && message != "") {
			model.addAttribute("message", message);
		}
		session.setAttribute("message", "");
		if (form != null) {
			model.addAttribute("ContactModel", form);
			session.setAttribute("sContactModel", null);

		} else {
			model.addAttribute("ContactModel", ContactModel);
		}

		try {
			DropDownModel[] owner = restClient.getForObject(env.getPipeline() + "/getOwnerList", DropDownModel[].class);

			List<DropDownModel> ownerList = Arrays.asList(owner);
			logger.info("ownerList" + ownerList);
			model.addAttribute("ownerList", ownerList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			DropDownModel[] states = restClient.getForObject(env.getPipeline() + "/getStatesList",
					DropDownModel[].class);

			List<DropDownModel> statesList = Arrays.asList(states);
			model.addAttribute("statesList", statesList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		try {
			DropDownModel[] countries = restClient.getForObject(env.getPipeline() + "/getCountryList",
					DropDownModel[].class);

			List<DropDownModel> countryList = Arrays.asList(countries);
			model.addAttribute("countryList", countryList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			DropDownModel[] source = restClient.getForObject(env.getPipeline() + "/getLeadSourceList",
					DropDownModel[].class);

			List<DropDownModel> sourceList = Arrays.asList(source);
			model.addAttribute("sourceList", sourceList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();

		}

		logger.info("Method : viewCrmPipeLine end");
		return "pipeline/crm-contacts";
	}

	@GetMapping("/view-crm-contacts-detail")
	public String viewCrmContactsDetails(Model model, @RequestParam String id, HttpSession session) {
		logger.info("Method : viewCrmContactsDetails start");
		logger.info("id url -----------------" + id);
		model.addAttribute("Leadidval", id);

		try {
			DropDownModel[] owner = restClient.getForObject(env.getPipeline() + "/getOwnerList", DropDownModel[].class);

			List<DropDownModel> ownerList = Arrays.asList(owner);
			logger.info("ownerList" + ownerList);
			model.addAttribute("ownerList", ownerList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// leadList

		try {
			DropDownModel[] lead = restClient.getForObject(env.getPipeline() + "/getLeadNameList",
					DropDownModel[].class);

			List<DropDownModel> leadList = Arrays.asList(lead);
			logger.info("leadList" + leadList);
			model.addAttribute("leadList", leadList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("Method : viewCrmContactsDetails end");
		return "pipeline/crm-contacts-detail";
	}

	/*
	 * post mapping for adding pipeline
	 */

	@SuppressWarnings("unchecked")

	@PostMapping("/view-crm-contacts-add")
	public @ResponseBody JsonResponse<Object> addContact(@RequestBody CrmContactModel contactModel,
			HttpSession session) {

		logger.info("Method : addContact starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		logger.info("web addContact ======================" + contactModel);
		try {
			String userId = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
			} catch (Exception e) {
				e.printStackTrace();
			}

			contactModel.setCreatedBy(userId);

			resp = restClient.postForObject(env.getPipeline() + "/addContact", contactModel, JsonResponse.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : addContact ends");

		return resp;
	}
	
	/*
	 * Post Mapping for search view-crm-contact-view-Data-search
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/view-crm-contacts-view-Data-filter")
	public @ResponseBody JsonResponse<List<CrmContactModel>> viewContactSearchDetails(
			@RequestBody CrmContactModel crmModel, Model model, HttpSession session) {

		logger.info("Method : viewContactSearchDetails starts" + crmModel);

		logger.info("VIEWWWWWWWWWWWDATA" + crmModel);
		JsonResponse<List<CrmContactModel>> resp = new JsonResponse<List<CrmContactModel>>();

		try {

			resp = restClient.postForObject(env.getPipeline() + "viewContactSearchDetails", crmModel,
					JsonResponse.class);

		} catch (RestClientException e) {

			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		List<CrmContactModel> contactModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<CrmContactModel>>() {
				});
		String date = "";
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");
		
		for (CrmContactModel i : contactModel) {
			
			
			if (i.getCreatedDate() != null && i.getCreatedDate() != "") {
				date = DateFormatter.dateFormat(i.getCreatedDate(), dateFormat);
				i.setCreatedDate(date);
				logger.info("start date---------------"+date);
			}
			
			if (i.getOwnerImage() != null && i.getOwnerImage() != "") {
				 String fileOwnerImage = env.getBaseURL() + "document/crm/" + i.getOwnerImage();
				
				logger.info("fileOwnerImage---------" + fileOwnerImage);
				i.setOwnerImage(fileOwnerImage); 
			}
			
		}
		resp.setBody(contactModel);

		logger.info("VIEWMESSAGE" + resp.getMessage());

		if (resp.getMessage() != null || resp.getMessage() != "") {
			resp.setMessage("Success");
		}
		logger.info("CONTACT  VIEWWWWWW" + resp);
		logger.info("Method : viewContactSearchDetails ends");
		return resp;
	}

	

	/*
	 * View all pipeline
	 *
	 */

	@SuppressWarnings("unchecked")

	@GetMapping("view-crm-contacts-throughAjax")
	public @ResponseBody List<CrmContactModel> viewAllContact() {

		logger.info("Method : viewAllContact starts");

		// logger.info("fvsjkbhkfvsjk");

		JsonResponse<List<CrmContactModel>> resp = new JsonResponse<List<CrmContactModel>>();

		try {
			// logger.info(env.getPipeline() + "getAllPipeLine");
			resp = restClient.getForObject(env.getPipeline() + "/getAllContact", JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		// logger.info("vcxfvfcvf " + resp.getBody());
		return resp.getBody();
	}

	/*
	 * ---------------------------------------------------------------------------
	 */

	// view-crm-accounts-view-Data

	/// view
	@SuppressWarnings("unchecked")

	@GetMapping("view-crm-contacts-view-Data")
	public @ResponseBody JsonResponse<List<CrmContactModel>> viewCrmContact(HttpSession session) {
		logger.info("Method : View viewCrmContact starts");

		JsonResponse<List<CrmContactModel>> resp = new JsonResponse<List<CrmContactModel>>();

		try {
			resp = restClient.getForObject(env.getPipeline() + "getAllContact", JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		List<CrmContactModel> contactModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<CrmContactModel>>() {
				});
		String date = "";
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");
		
		for (CrmContactModel i : contactModel) {
			
			
			if (i.getCreatedDate() != null && i.getCreatedDate() != "") {
				date = DateFormatter.dateFormat(i.getCreatedDate(), dateFormat);
				i.setCreatedDate(date);
				logger.info("start date---------------"+date);
			}
			
			if (i.getOwnerImage() != null && i.getOwnerImage() != "") {
				 String fileOwnerImage = env.getBaseURL() + "document/crm/" + i.getOwnerImage();
				
				logger.info("fileOwnerImage---------" + fileOwnerImage);
				i.setOwnerImage(fileOwnerImage); 
			}
			
		}
		resp.setBody(contactModel);

		

		if (resp.getMessage() == null) {
			resp.setMessage("Success");
		}

		logger.info("views" + resp);
		logger.info("Method : View viewCrmContact ends");
		return resp;
	}

	/*---------------------------------------------------------------------------*/

	// edit contact

	/**
	 * get mapping for edit pipeline
	 */

	@SuppressWarnings("unchecked")

	@GetMapping("view-crm-contacts-edit")
	public @ResponseBody JsonResponse<CrmContactModel> editContact(@RequestParam String id, HttpSession session) {

		logger.info("Method : edit Contact starts");

		JsonResponse<CrmContactModel> jsonResponse = new JsonResponse<CrmContactModel>();

		try {
			logger.info(id + "IDIDIDIDID");
			jsonResponse = restClient.getForObject(env.getPipeline() + "/editContact?id=" + id, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		CrmContactModel contactModel = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<CrmContactModel>() {
				});

		String date = "";
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");
		/*
		 * if (contactModel.getDateBirth() != null && contactModel.getDateBirth() != "")
		 * { date = DateFormatter.dateFormat(contactModel.getDateBirth(), dateFormat);
		 * contactModel.setDateBirth(date); }
		 */

		jsonResponse.setBody(contactModel);
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("Method : edit Contacts ends");
		logger.info("EDITTTTT==================" + jsonResponse);
		return jsonResponse;

	}

	// view contact

	@SuppressWarnings("unchecked")

	@GetMapping("view-crm-contacts-detailsview")
	public @ResponseBody JsonResponse<CrmContactModel> ViewContact(@RequestParam String id, HttpSession session) {

		logger.info("Method : ViewContact starts");

		JsonResponse<CrmContactModel> jsonResponse = new JsonResponse<CrmContactModel>();

		try {
			logger.info(id + "IDIDIDIDID");
			jsonResponse = restClient.getForObject(env.getPipeline() + "/viewContact?id=" + id, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		CrmContactModel contactModel = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<CrmContactModel>() {
				});

		String date = "";
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");
		if (contactModel.getDateBirth() != null && contactModel.getDateBirth() != "") {
			date = DateFormatter.dateFormat(contactModel.getDateBirth(), dateFormat);
			contactModel.setDateBirth(date);
		}

		jsonResponse.setBody(contactModel);
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("Method : ViewContact ends");
		logger.info("VIEWWWW==================" + jsonResponse);
		return jsonResponse;

	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-crm-contacts-delete-id")
	public @ResponseBody JsonResponse<Object> deleteCrmContactDetails(@RequestParam String id, HttpSession session) {
		logger.info("Method : deleteCrmContactDetails function starts" + id);

		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getPipeline() + "delete-contact-Details?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteCrmContactDetails function Ends");
		logger.info("Response" + res);
		return res;
	}

	/// view-crm-contacts-get-customer-list

	/*
	 * customer Auto search
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-crm-contacts-get-account-list" })
	public @ResponseBody JsonResponse<SalesOrderNewModel> getAccountNameAutoSearchNewList(Model model,
			@RequestBody String searchValue, BindingResult result) {
		logger.info("Method : getAccountNameAutoSearchNewList starts");
		JsonResponse<SalesOrderNewModel> res = new JsonResponse<SalesOrderNewModel>();

		try {
			res = restClient.getForObject(env.getPipeline() + "getAccountNameAutoSearchNewList?id=" + searchValue,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		/*if (res.getMessage() != null) {

			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}*/

		logger.info("Method : getAccountNameAutoSearchNewList ends");
		return res;
	}

	// view-crm-contacts-view-detail-task

	@SuppressWarnings("unchecked")
	@GetMapping("view-crm-contacts-view-detail-task")
	public @ResponseBody JsonResponse<List<CrmLeadTaskModel>> viewcontactsTaskInfo(Model model, @RequestParam String id,
			HttpSession session) {

		logger.info("Method : viewcontactsTaskInfo starts" + id);

		JsonResponse<List<CrmLeadTaskModel>> jsonResponse = new JsonResponse<List<CrmLeadTaskModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getPipeline() + "view-rest-ContactTaskInfo?id=" + id,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		String date = "";
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");

		List<CrmLeadTaskModel> taskModel = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<CrmLeadTaskModel>>() {
				});

		for (CrmLeadTaskModel i : taskModel) {
			if (i.getDueDate() != null && i.getDueDate() != "") {
				date = DateFormatter.dateFormat(i.getDueDate(), dateFormat);
				i.setDueDate(date);
				logger.info("Due date---------------" + date);
			}

		}

		jsonResponse.setBody(taskModel);

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("REsp" + jsonResponse);
		logger.info("Method : viewcontactsTaskInfo ends");
		return jsonResponse;
	}
	// view-crm-contacts-view-detail-meeting

	@SuppressWarnings("unchecked")
	@GetMapping("view-crm-contacts-view-detail-meeting")
	public @ResponseBody JsonResponse<List<crmMeetingModel>> viewContactMeetingInfo(Model model,
			@RequestParam String id, HttpSession session) {

		logger.info("Method : viewContactMeetingInfo starts" + id);

		JsonResponse<List<crmMeetingModel>> jsonResponse = new JsonResponse<List<crmMeetingModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getPipeline() + "view-rest-ContactMeetingInfo?id=" + id,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		String date = "";
		String date1 = "";
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");

		List<crmMeetingModel> meetingModel = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<crmMeetingModel>>() {
				});

		for (crmMeetingModel i : meetingModel) {
			if (i.getMeetingFromDate() != null && i.getMeetingFromDate() != "") {
				date = DateFormatter.dateFormat(i.getMeetingFromDate(), dateFormat);
				i.setMeetingFromDate(date);
				logger.info("Meeting from date---------------" + date);
			}

			if (i.getMeetingToDate() != null && i.getMeetingToDate() != "") {
				date1 = DateFormatter.dateFormat(i.getMeetingToDate(), dateFormat);
				i.setMeetingToDate(date1);
				logger.info("Meeting to date---------------" + date1);
			}

		}

		jsonResponse.setBody(meetingModel);

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("REsp" + jsonResponse);
		logger.info("Method : viewContactMeetingInfo ends");
		return jsonResponse;
	}

	// view-crm-contacts-view-detail-call

	@SuppressWarnings("unchecked")
	@GetMapping("view-crm-contacts-view-detail-call")
	public @ResponseBody JsonResponse<List<CrmCallModel>> viewcontactsCallInfo(Model model, @RequestParam String id,
			HttpSession session) {

		logger.info("Method : viewcontactsCallInfo starts" + id);

		JsonResponse<List<CrmCallModel>> jsonResponse = new JsonResponse<List<CrmCallModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getPipeline() + "view-rest-ContactCallInfo?id=" + id,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		String date = "";
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");

		List<CrmCallModel> callModel = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<CrmCallModel>>() {
				});

		for (CrmCallModel i : callModel) {
			if (i.getCallStartDate() != null && i.getCallStartDate() != "") {
				date = DateFormatter.dateFormat(i.getCallStartDate(), dateFormat);
				i.setCallStartDate(date);
				logger.info("Call from date---------------" + date);
			}
		}

		jsonResponse.setBody(callModel);
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

		} else {
			jsonResponse.setMessage("Success");
		}
		logger.info("REsp" + jsonResponse);
		logger.info("Method : viewcontactsCallInfo ends");
		return jsonResponse;
	}

	// view-crm-leads-detail-add-task-dtls

	@SuppressWarnings("unchecked")

	@PostMapping("/view-crm-contacts-add-task-dtls")
	public @ResponseBody JsonResponse<Object> addTaskByContactsDtls(@RequestBody CrmLeadTaskModel crmLeadTaskModel,
			HttpSession session) {

		logger.info("Method : addTaskByContactsDtls starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		logger.info("web addTaskByContactsDtls======================" + crmLeadTaskModel);
		try {
			String userId = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
			} catch (Exception e) {
				e.printStackTrace();
			}

			crmLeadTaskModel.setCreatedBy(userId);

			resp = restClient.postForObject(env.getPipeline() + "/addTask", crmLeadTaskModel, JsonResponse.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : addTaskByContactsDtls ends");

		return resp;
	}

	// view-crm-leads-detail-add-meeting-dtls

	@SuppressWarnings("unchecked")

	@PostMapping("/view-crm-contacts-detail-add-meeting-dtls")
	public @ResponseBody JsonResponse<Object> addMeetingsByContactsDtls(@RequestBody crmMeetingModel meetingModel,
			HttpSession session) {

		logger.info("Method : addMeetingsByContactsDtls starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		logger.info("web addMeetingsByContactsDtls lead======================" + meetingModel);
		try {
			String userId = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
			} catch (Exception e) {
				e.printStackTrace();
			}

			meetingModel.setCreatedBy(userId);

			resp = restClient.postForObject(env.getPipeline() + "/addMeeting", meetingModel, JsonResponse.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : addMeetingsByContactsDtls ends");

		return resp;
	}

	// view-crm-leads-detail-add-call-dtls

	@SuppressWarnings("unchecked")

	@PostMapping("/view-crm-contacts-detail-add-call-dtls")
	public @ResponseBody JsonResponse<Object> addCallsByContactsDtls(@RequestBody CrmCallModel callModel,
			HttpSession session) {

		logger.info("Method : addCallsByContactsDtls starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		logger.info("web addCallsByContactsDtls lead======================" + callModel);
		try {
			String userId = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
			} catch (Exception e) {
				e.printStackTrace();
			}

			callModel.setCreatedBy(userId);

			resp = restClient.postForObject(env.getPipeline() + "/addCall", callModel, JsonResponse.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : addCallsByContactsDtls ends");

		return resp;
	}

	// view-crm-leads-detail-add-campaign-dtls

	@SuppressWarnings("unchecked")

	@PostMapping("/view-crm-contacts-detail-add-campaign-dtls")
	public @ResponseBody JsonResponse<Object> addCampaignsByContactDtls(@RequestBody CrmCampaignModel crmCampaignModel,
			HttpSession session) {

		logger.info("Method : addCampaignsByContactDtls starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		logger.info("web addCampaignsByContactDtls lead======================" + crmCampaignModel);
		try {
			String userId = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
			} catch (Exception e) {
				e.printStackTrace();
			}

			crmCampaignModel.setCreatedBy(userId);

			resp = restClient.postForObject(env.getPipeline() + "/addCampaign", crmCampaignModel,
					JsonResponse.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : addCampaignsByContactDtls ends");

		return resp;
	}

	// view-crm-leads-view-detail-campaign

	@SuppressWarnings("unchecked")
	@GetMapping("view-crm-contacts-view-detail-campaign")
	public @ResponseBody JsonResponse<List<CrmCampaignModel>> viewContactCampaignInfo(Model model,
			@RequestParam String id, HttpSession session) {

		logger.info("Method : viewContactCampaignInfo starts" + id);

		JsonResponse<List<CrmCampaignModel>> jsonResponse = new JsonResponse<List<CrmCampaignModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getPipeline() + "view-rest-viewContactCampaignInfo?id=" + id,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		String date = "";
		String date1 = "";
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");

		List<CrmCampaignModel> campaignModel = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<CrmCampaignModel>>() {
				});

		for (CrmCampaignModel i : campaignModel) {
			if (i.getStartDate() != null && i.getStartDate() != "") {
				date = DateFormatter.dateFormat(i.getStartDate(), dateFormat);
				i.setStartDate(date);
				logger.info("start date---------------" + date);
			}

			if (i.getEndDate() != null && i.getEndDate() != "") {
				date1 = DateFormatter.dateFormat(i.getEndDate(), dateFormat);
				i.setEndDate(date1);
				logger.info("end date---------------" + date1);
			}
		}

		jsonResponse.setBody(campaignModel);

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("REsp" + jsonResponse);
		logger.info("Method : viewContactCampaignInfo ends");
		return jsonResponse;
	}

/////////////////////////////////// Document save for mail /////////////////////////////////////////////////

	public String saveAllImage(byte[] imageBytes) {
		logger.info("Method : saveAllImage starts");

		String imageName = null;

		try {
			if (imageBytes != null) {
				long nowTime = new Date().getTime();
				imageName = nowTime + ".png";
			}

			Path path = Paths.get(env.getFileUploadCrmUrl() + imageName);
			logger.info("path for image write----------" + path);
			if (imageBytes != null) {
				Files.write(path, imageBytes);

				ByteArrayInputStream in = new ByteArrayInputStream(imageBytes);
				Integer height = 50;
				Integer width = 50;

				try {
					BufferedImage img = ImageIO.read(in);
					if (height == 0) {
						height = (width * img.getHeight()) / img.getWidth();
					}
					if (width == 0) {
						width = (height * img.getWidth()) / img.getHeight();
					}

					Image scaledImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);

					BufferedImage imageBuff = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
					imageBuff.getGraphics().drawImage(scaledImage, 0, 0, new Color(0, 0, 0), null);

					ByteArrayOutputStream buffer = new ByteArrayOutputStream();

					ImageIO.write(imageBuff, "png", buffer);

					byte[] thumb = buffer.toByteArray();

					Path pathThumb = Paths.get(env.getFileUploadCrmUrl() + "thumb/" + imageName);
					logger.info("thumb image path--------------" + pathThumb);
					Files.write(pathThumb, thumb);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveAllImage ends");
		return imageName;
	}

	/*
	 * for save all pdf in folder and return name
	 */

	public String saveAllPdf(byte[] imageBytes) {
		logger.info("Method : saveAllPdf starts");

		String pdfName = null;

		try {
			if (imageBytes != null) {
				long nowTime = new Date().getTime();
				pdfName = nowTime + ".pdf";
			}

			Path path = Paths.get(env.getFileUploadCrmUrl() + pdfName);
			if (imageBytes != null) {
				Files.write(path, imageBytes);
				logger.info("path for pdf write----------" + path);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveAllPdf ends");
		return pdfName;
	}

	public String saveAllDocx(byte[] imageBytes) {
		logger.info("Method : saveAllDocx starts");

		String pdfName = null;

		try {
			if (imageBytes != null) {
				long nowTime = new Date().getTime();
				pdfName = nowTime + ".docx";
			}

			Path path = Paths.get(env.getFileUploadCrmUrl() + pdfName);
			if (imageBytes != null) {
				Files.write(path, imageBytes);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveAllDocx ends");
		return pdfName;
	}

	public String saveAllDoc(byte[] imageBytes) {
		logger.info("Method : saveAllDoc starts");

		String pdfName = null;

		try {
			if (imageBytes != null) {
				long nowTime = new Date().getTime();
				pdfName = nowTime + ".doc";
			}

			Path path = Paths.get(env.getFileUploadCrmUrl() + pdfName);
			if (imageBytes != null) {
				Files.write(path, imageBytes);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveAllDoc ends");
		return pdfName;
	}

	public String saveAllXls(byte[] imageBytes) {
		logger.info("Method : saveAllDoc starts");

		String pdfName = null;

		try {
			if (imageBytes != null) {
				long nowTime = new Date().getTime();
				pdfName = nowTime + ".xls";
			}

			Path path = Paths.get(env.getFileUploadCrmUrl() + pdfName);
			if (imageBytes != null) {
				Files.write(path, imageBytes);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveAllDoc ends");
		return pdfName;
	}

	public String saveAllXlsx(byte[] imageBytes) {
		logger.info("Method : saveAllDoc starts");

		String pdfName = null;

		try {
			if (imageBytes != null) {
				long nowTime = new Date().getTime();
				pdfName = nowTime + ".xlsx";
			}

			Path path = Paths.get(env.getFileUploadCrmUrl() + pdfName);
			if (imageBytes != null) {
				Files.write(path, imageBytes);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveAllDoc ends");
		return pdfName;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/view-crm-contacts-add-emails-ajax")
	public @ResponseBody JsonResponse<Object> addContactEmailDoc(@RequestBody EmployeeDocumentModel employeeDocumentModel,
			HttpSession session) {
		logger.info("Method : addNoteDoc starts1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		employeeDocumentModel.setCreatedBy(userId);

		for (InventoryVendorDocumentModel a : employeeDocumentModel.getDocumentList()) {
	
			logger.info("hello doc list---------" + employeeDocumentModel.getDocumentList());
			if (a.getFileName() != null && a.getFileName() != "") {
				String delimiters = "\\.";
				String[] x = a.getFileName().split(delimiters);

				if (x[1].contentEquals("png") || x[1].contentEquals("jpg") || x[1].contentEquals("jpeg")) {

					for (String s1 : a.getDocumentFile()) {
						if (s1 != null)
							try {
								byte[] bytes = Base64.getDecoder().decode(s1);
								String imageName = saveAllImage(bytes);
								a.setFileName(imageName);
							} catch (Exception e) {
								e.printStackTrace();
							}
					}
				} else if (x[1].contentEquals("pdf")) {
					for (String s1 : a.getDocumentFile()) {
						try {
							byte[] bytes = Base64.getDecoder().decode(s1);
							String pdfName = saveAllPdf(bytes);
							a.setFileName(pdfName);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} else if (x[1].contentEquals("docx")) {
					for (String s1 : a.getDocumentFile()) {
						try {
							byte[] bytes = Base64.getDecoder().decode(s1);
							String pdfName = saveAllDocx(bytes);
							a.setFileName(pdfName);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} else if (x[1].contentEquals("doc")) {
					for (String s1 : a.getDocumentFile()) {
						try {
							byte[] bytes = Base64.getDecoder().decode(s1);
							String pdfName = saveAllDoc(bytes);
							a.setFileName(pdfName);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} else if (x[1].contentEquals("xls")) {
					for (String s1 : a.getDocumentFile()) {
						try {
							byte[] bytes = Base64.getDecoder().decode(s1);
							String pdfName = saveAllXls(bytes);
							a.setFileName(pdfName);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} else if (x[1].contentEquals("xlsx")) {
					for (String s1 : a.getDocumentFile()) {
						try {
							byte[] bytes = Base64.getDecoder().decode(s1);
							String pdfName = saveAllXlsx(bytes);
							a.setFileName(pdfName);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
			// }
		}
		

		try {
			logger.info("ADDDDOCCCC"+employeeDocumentModel);
			resp = restClient.postForObject(env.getPipeline() + "addContactEmailDoc", employeeDocumentModel,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("success");
		}

		logger.info("Method : addContactEmailDoc end");
		logger.info("ADDDDDOCCC"+resp);
		return resp;
	}
	
	
	///view-crm-contacts-view-detail-activity
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-crm-contacts-view-detail-activity")
	public @ResponseBody JsonResponse<List<CrmActivityModel>> viewLeadActivityInfo(Model model, @RequestParam String id,
			HttpSession session) {

		logger.info("Method : viewLeadActivityInfo starts" + id);

		JsonResponse<List<CrmActivityModel>> jsonResponse = new JsonResponse<List<CrmActivityModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getPipeline() + "view-rest-LeadActivityInfo?id=" + id,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String date = "";
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");
		
		String meetingFromDate ="";
		String meetingToDate ="";
		
		List<CrmActivityModel> activityModel = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<CrmActivityModel>>() {
				});
		
		for (CrmActivityModel i : activityModel) {
			if (i.getCreatedOn() != null && i.getCreatedOn() != "") {
				date = DateFormatter.dateFormat(i.getCreatedOn(), dateFormat);
				i.setCreatedOn(date);
				logger.info("Activity createdOn---------------"+date);
			}
			
			if (i.getMeetingFromDate() != null && i.getMeetingFromDate() != "") {
				meetingFromDate = DateFormatter.dateFormat(i.getMeetingFromDate(), dateFormat);
				i.setMeetingFromDate(meetingFromDate);
				logger.info("Activity meetingFromDate---------------"+date);
			}
			
			if (i.getMeetingToDate() != null && i.getMeetingToDate() != "") {
				meetingToDate = DateFormatter.dateFormat(i.getMeetingToDate(), dateFormat);
				i.setMeetingToDate(meetingToDate);
				logger.info("Activity meetingToDate---------------"+date);
			}
		}
		
		jsonResponse.setBody(activityModel);
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

		} else {
			jsonResponse.setMessage("Success");
		}
		logger.info("REsp" + jsonResponse);
		logger.info("Method : viewLeadActivityInfo ends");
		return jsonResponse;
	}
	
	

}
