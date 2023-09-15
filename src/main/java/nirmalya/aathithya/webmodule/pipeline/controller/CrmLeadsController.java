package nirmalya.aathithya.webmodule.pipeline.controller;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;

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
import nirmalya.aathithya.webmodule.pipeline.model.CrmLeadTaskModel;
import nirmalya.aathithya.webmodule.pipeline.model.CrmLeadsModel;
import nirmalya.aathithya.webmodule.pipeline.model.CrmProductModel;
import nirmalya.aathithya.webmodule.pipeline.model.crmMeetingModel;
import nirmalya.aathithya.webmodule.procurment.model.InventoryVendorDocumentModel;

@Controller
@RequestMapping(value = "pipeline")
public class CrmLeadsController {

	Logger logger = LoggerFactory.getLogger(CrmLeadsController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("/view-crm-leads-detail")
	public String viewCrmLeadsDetails(Model model,@RequestParam String id,HttpSession session){
		logger.info("Method : viewCrmLeadsDetails start");
		logger.info("id url -----------------"+id);
		model.addAttribute("Leadidval",id);
		logger.info("Method : viewCrmLeadsDetails end");
		
		try {
			DropDownModel[] owner = restTemplate.getForObject(env.getPipeline() + "/getOwnerList",
					DropDownModel[].class);

			List<DropDownModel> ownerList = Arrays.asList(owner);
			logger.info("ownerList" + ownerList);
			model.addAttribute("ownerList", ownerList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] campaign = restTemplate.getForObject(env.getPipeline() + "/getDealCampaignList",
					DropDownModel[].class);

			List<DropDownModel> campaignList = Arrays.asList(campaign);
			model.addAttribute("campaignList", campaignList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//leadList
		
		try {
			DropDownModel[] lead = restTemplate.getForObject(env.getPipeline() + "/getLeadNameList",
					DropDownModel[].class);

			List<DropDownModel> leadList = Arrays.asList(lead);
			logger.info("leadList" + leadList);
			model.addAttribute("leadList", leadList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "pipeline/crm-leads-detail";
	}
	
	
/*	@GetMapping("/view-crm-leads-detail")
	public String getEmployeeById(@PathVariable long id, HttpServletRequest request){
		logger.info("Method : viewCrmLeadsDetails start");
		
		String url = request.getRequestURL().toString();
		logger.info("id url -----------------"+url);
		
		logger.info("Method : viewCrmLeadsDetails end");
		return "pipeline/crm-leads";
	}*/
	
	
	//view-crm-leads-detail-add-task-dtls
	
	@SuppressWarnings("unchecked")

	@PostMapping("/view-crm-leads-detail-add-task-dtls")
	public @ResponseBody JsonResponse<Object> addTaskByLeadDtls(@RequestBody CrmLeadTaskModel crmLeadTaskModel,
			HttpSession session) {

		logger.info("Method : addTaskByLeadDtls starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		logger.info("web addTaskByLeadDtls lead======================" + crmLeadTaskModel);
		try {
			String userId = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
			} catch (Exception e) {
				e.printStackTrace();
			}

			crmLeadTaskModel.setCreatedBy(userId);

			resp = restTemplate.postForObject(env.getPipeline() + "/addTask", crmLeadTaskModel, JsonResponse.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * if (resp.getMessage() != "" && resp.getMessage() != null) {
		 * resp.setCode(resp.getMessage()); resp.setMessage("Unsuccess"); } else {
		 * resp.setMessage("Success"); }
		 */

		logger.info("Method : addTaskByLeadDtls ends");

		return resp;
	}
	
	//view-crm-leads-detail-add-meeting-dtls
	
	@SuppressWarnings("unchecked")

	@PostMapping("/view-crm-leads-detail-add-meeting-dtls")
	public @ResponseBody JsonResponse<Object> addMeetingsByLeadDtls(@RequestBody crmMeetingModel meetingModel,
			HttpSession session) {

		logger.info("Method : addMeetingsByLeadDtls starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		logger.info("web addMeetingsByLeadDtls lead======================" + meetingModel);
		try {
			String userId = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
			} catch (Exception e) {
				e.printStackTrace();
			}

			meetingModel.setCreatedBy(userId);

			resp = restTemplate.postForObject(env.getPipeline() + "/addMeeting", meetingModel, JsonResponse.class);

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

		logger.info("Method : addMeetingsByLeadDtls ends");

		return resp;
	}
	
	//view-crm-leads-detail-add-call-dtls
	
	@SuppressWarnings("unchecked")

	@PostMapping("/view-crm-leads-detail-add-call-dtls")
	public @ResponseBody JsonResponse<Object> addCallsByLeadDtls(@RequestBody CrmCallModel callModel,
			HttpSession session) {

		logger.info("Method : addCallsByLeadDtls starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		logger.info("web addCallsByLeadDtls lead======================" + callModel);
		try {
			String userId = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
			} catch (Exception e) {
				e.printStackTrace();
			}

			callModel.setCreatedBy(userId);

			resp = restTemplate.postForObject(env.getPipeline() + "/addCall", callModel, JsonResponse.class);

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

		logger.info("Method : addCallsByLeadDtls ends");

		return resp;
	}
	
	//view-crm-leads-detail-add-campaign-dtls
	
	@SuppressWarnings("unchecked")

	@PostMapping("/view-crm-leads-detail-add-campaign-dtls")
	public @ResponseBody JsonResponse<Object> addCampaignsByLeadDtls(@RequestBody CrmCampaignModel crmCampaignModel,
			HttpSession session) {

		logger.info("Method : addCampaignsByLeadDtls starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		logger.info("web addCampaignsByLeadDtls lead======================" + crmCampaignModel);
		try {
			String userId = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
			} catch (Exception e) {
				e.printStackTrace();
			}

			crmCampaignModel.setCreatedBy(userId);

			resp = restTemplate.postForObject(env.getPipeline() + "/addCampaign", crmCampaignModel, JsonResponse.class);

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

		logger.info("Method : addCampaignsByLeadDtls ends");

		return resp;
	}
	
	
	
	// edit 

		@SuppressWarnings("unchecked")
		@GetMapping("view-crm-leads-details-email-view")
		public @ResponseBody JsonResponse<CrmLeadsModel> viewEmailDetails(@RequestParam String id1,@RequestParam String id2,@RequestParam String id3,
				HttpSession session) {

			logger.info("Method : viewEmailDetails starts");
			//JsonResponse<CrmLeadsModel> jsonResponse = new JsonResponse<CrmLeadsModel>();
			JsonResponse<CrmLeadsModel> res = new JsonResponse<CrmLeadsModel>();
			logger.info("VIEWW"+res);
			logger.info("id1====" + id1);
			logger.info("id2====" + id2);
			logger.info("id3====" + id3);
			try {
				//jsonResponse = restTemplate.getForObject(env.getPipeline() + "viewEmailDetails?empId=" + id1 + "&id=" + id2 +"&tomail=" + id3,
				//		JsonResponse.class);
				res = restTemplate.getForObject(env.getPipeline() + "viewEmailDetailsView?id1=" + id1 + "&id2=" + id2+ "&id3=" + id3,JsonResponse.class);

			} catch (Exception e) {
				e.printStackTrace();
			}

			
			ObjectMapper mapper = new ObjectMapper();
			CrmLeadsModel leadModel = mapper.convertValue(res.getBody(),
					new TypeReference<CrmLeadsModel>() {
					});
			String profile = null;
			if (leadModel.getAttachment() != null && leadModel.getAttachment() != ""
					&& !leadModel.getAttachment().equals("null")) {

				profile = env.getBaseURL() + "document/crm/" + leadModel.getAttachment();
				leadModel.setOwnerImageLink(profile);
				
			}
			
			res.setBody(leadModel);
			logger.info("###" + leadModel.getAttachment());
			res.setBody((CrmLeadsModel) leadModel);
			
			
			if (res.getMessage() != null) {

				res.setCode(res.getMessage());
				res.setMessage("Unsuccess");
			} else {
				res.setMessage("success");
			}
			logger.info("Method : viewEmailDetails ends");
			logger.info("viewEmailDetails" + res);
			return res;
		}	
		
		

	/*
	 * Post Mapping for search view-crm-leads-view-Data-search
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/view-crm-leads-view-Data-search")
	public @ResponseBody JsonResponse<List<CrmLeadsModel>> viewLeadSearchDetails(@RequestBody CrmLeadsModel crmModel,
			Model model, HttpSession session) {

		logger.info("Method : viewLeadSearchDetails starts" + crmModel);

		logger.info("VIEWWWWWWWWWWWDATA" + crmModel);
		JsonResponse<List<CrmLeadsModel>> resp = new JsonResponse<List<CrmLeadsModel>>();

		try {

			resp = restTemplate.postForObject(env.getPipeline() + "viewLeadSearchDetails", crmModel,
					JsonResponse.class);

		} catch (RestClientException e) {

			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		List<CrmLeadsModel> quotationNewModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<CrmLeadsModel>>() {
				});
		String drProfDoc = null;
		String date = "";
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");
		for (CrmLeadsModel i : quotationNewModel) {
			if (i.getImageName() != null && i.getImageName() != "") {

				String fileEmployeeimg = env.getBaseURL() + "document/crm/" + i.getImageName();

				logger.info("Image" + fileEmployeeimg);
				i.setImageName(fileEmployeeimg);
			}
			
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

		logger.info("###" + quotationNewModel);
		resp.setBody(quotationNewModel);

		logger.info("VIEWMESSAGE"+resp.getMessage());

		if (resp.getMessage() != null|| resp.getMessage() !="") {
			resp.setMessage("Success");
		}
		logger.info("VIEWWWWWeeeeee" + resp);
		logger.info("Method : viewLeadSearchDetails ends");
		return resp;
	}

	
	/*
	 * get mapping for view-crm-leads
	 * 
	 */
	
	@GetMapping("/view-crm-leads")
	public String viewCrmLeads(Model model, HttpSession session) {
		logger.info("Method : viewCrmLeads start");
		// PipelineModel PipelineModel = new PipelineModel();
		// PipelineModel form = (PipelineModel) session.getAttribute("sPipelineModel");

		try {
			logger.info(env.getPipeline());
			DropDownModel[] source = restTemplate.getForObject(env.getPipeline() + "/getSourceList",
					DropDownModel[].class);

			List<DropDownModel> sourceList = Arrays.asList(source);
			model.addAttribute("customerList", sourceList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			logger.info(env.getPipeline());
			DropDownModel[] status = restTemplate.getForObject(env.getPipeline() + "/getLeadStatusList",
					DropDownModel[].class);

			List<DropDownModel> statusList = Arrays.asList(status);
			model.addAttribute("statusList", statusList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			DropDownModel[] owner = restTemplate.getForObject(env.getPipeline() + "/getOwnerList",
					DropDownModel[].class);

			List<DropDownModel> ownerList = Arrays.asList(owner);
			logger.info("ownerList" + ownerList);
			model.addAttribute("ownerList", ownerList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		try {
			logger.info(env.getPipeline());
			DropDownModel[] source = restTemplate.getForObject(env.getPipeline() + "/getDocumentList",
					DropDownModel[].class);

			List<DropDownModel> sourceList = Arrays.asList(source);
			model.addAttribute("documentTypeList", sourceList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			DropDownModel[] source = restTemplate.getForObject(env.getPipeline() + "/getLeadList",
					DropDownModel[].class);

			List<DropDownModel> sourceList = Arrays.asList(source);
			model.addAttribute("leadList", sourceList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			DropDownModel[] source = restTemplate.getForObject(env.getPipeline() + "/getindustrylist",
					DropDownModel[].class);

			List<DropDownModel> sourceList = Arrays.asList(source);
			model.addAttribute("industryList", sourceList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			DropDownModel[] rating = restTemplate.getForObject(env.getPipeline() + "/getRatingList",
					DropDownModel[].class);

			List<DropDownModel> ratingList = Arrays.asList(rating);
			model.addAttribute("ratingList", ratingList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			DropDownModel[] source = restTemplate.getForObject(env.getPipeline() + "/getCountry",
					DropDownModel[].class);

			List<DropDownModel> sourceList = Arrays.asList(source);
			model.addAttribute("countryList", sourceList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("Method : viewCrmLeads end");
		return "pipeline/crm-leads";
	}

	@SuppressWarnings("unchecked")

	@GetMapping(value = { "view-crm-leads-stateList" })
	public @ResponseBody JsonResponse<Object> getstateList(@RequestParam String id) {
		logger.info("Method : getstateListAJAX starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getPipeline() + "getStateLists?id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("state" + res);
		logger.info("Method : getstateListAJAX ends");
		return res;
	}

	/*
	 * Post Mapping for adding new assignSkill
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/view-crm-leads-add-lead-details")
	public @ResponseBody JsonResponse<Object> addassignSkillMasterPost(@RequestBody CrmLeadsModel crmModel, Model model,
			HttpSession session) {

		logger.info("Method : addassignSkillMasterPost starts" + crmModel);

		MultipartFile inputFile = (MultipartFile) session.getAttribute("employeePFile");
		byte[] bytes;
		String imageName = null;

		if (inputFile != null) {
			try {
				bytes = inputFile.getBytes();
				String[] fileType = inputFile.getContentType().split("/");
				imageName = saveAllImage(bytes, fileType[1]);
				logger.info("imageName lead------------------"+imageName);

				crmModel.setImageName(imageName);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {

			resp = restTemplate.postForObject(env.getPipeline() + "rest-add-lead-details", crmModel,
					JsonResponse.class);

		} catch (RestClientException e) {

			e.printStackTrace();
		}

		if (resp.getMessage() == "") {
			resp.setMessage("Success");
		}
		logger.info("Method : addassignSkillMasterPost ends" + resp);

		return resp;
	}

	public String saveAllImage(byte[] imageBytes, String ext) {
		logger.info("Method : saveAllImage starts");

		String imageName = null;
		Path imagePath = null;
		try {

			if (imageBytes != null) {
				long nowTime = new Date().getTime();
				if (ext.contentEquals("jpeg")) {
					imageName = nowTime + ".jpg";
				} else {
					imageName = nowTime + "." + ext;
				}

			}
			
			logger.info("imageNAme in save image------------------"+imageName);

			Path path = Paths.get(env.getFileUploadCrmUrl() + imageName);
			
			logger.info("path in save image------------------"+path);
			if (imageBytes != null) {
				Files.write(path, imageBytes);
				// crm.setImagePath(path);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : saveAllImage ends" + imagePath);
		return imageName;
	}

	/// view
	@SuppressWarnings("unchecked")

	@GetMapping("view-crm-leads-view-Data")
	public @ResponseBody JsonResponse<List<CrmLeadsModel>> viewItemCategory(HttpSession session) {
		logger.info("Method : View starts");

		JsonResponse<List<CrmLeadsModel>> resp = new JsonResponse<List<CrmLeadsModel>>();

		try {
			resp = restTemplate.getForObject(env.getPipeline() + "viewLeadData", JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		List<CrmLeadsModel> quotationNewModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<CrmLeadsModel>>() {
				});
		String drProfDoc = null;
		String date = "";
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");
		for (CrmLeadsModel i : quotationNewModel) {
			if (i.getImageName() != null && i.getImageName() != "") {
				 String fileEmployeeimg = env.getBaseURL() + "document/crm/" + i.getImageName();
				
				logger.info("Image" + fileEmployeeimg);
				i.setImageName(fileEmployeeimg); 
			}
			
			if (i.getOwnerImage() != null && i.getOwnerImage() != "") {
				 String fileOwnerImage = env.getBaseURL() + "document/crm/" + i.getOwnerImage();
				
				logger.info("fileOwnerImage---------" + fileOwnerImage);
				i.setOwnerImage(fileOwnerImage); 
			}
			
			if (i.getCreatedDate() != null && i.getCreatedDate() != "") {
				date = DateFormatter.dateFormat(i.getCreatedDate(), dateFormat);
				i.setCreatedDate(date);
				logger.info("start date---------------"+date);
			}
		}
		
		
		

		logger.info("###" + quotationNewModel);
		resp.setBody(quotationNewModel);

		if (resp.getMessage() == null) {
			resp.setMessage("View successfully");
		}

		logger.info("views" + resp);
		logger.info("Method : View ends");
		return resp;
	}

	/// edit
	@SuppressWarnings("unchecked")
	@GetMapping("view-crm-leads-editDetails")
	public @ResponseBody JsonResponse<List<CrmLeadsModel>> editLeadInfo(Model model, @RequestParam String id,
			HttpSession session) {

		logger.info("Method : editLeadInfo starts" + id);

		JsonResponse<List<CrmLeadsModel>> jsonResponse = new JsonResponse<List<CrmLeadsModel>>();
		try {
			jsonResponse = restTemplate.getForObject(env.getPipeline() + "edit-rest-LeadInfo?id=" + id,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();

		List<CrmLeadsModel> quotationNewModel = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<CrmLeadsModel>>() {
				});
		String drProfDoc = null;
		for (CrmLeadsModel i : quotationNewModel) {
			if (i.getImageName() != null && i.getImageName() != "") {
				 String fileEmployeeimg = env.getBaseURL() + "document/crm/" + i.getImageName();
				
				logger.info("Image" + fileEmployeeimg);
				i.setImageName(fileEmployeeimg); 
			}
		}

		logger.info("###" + quotationNewModel);
		jsonResponse.setBody(quotationNewModel);

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("REsp" + jsonResponse);
		logger.info("Method : editLeadInfo ends");
		return jsonResponse;
	}
	
	//view-crm-leads-view-Details
	
		@SuppressWarnings("unchecked")
		@GetMapping("view-crm-leads-view-detail")
		public @ResponseBody JsonResponse<List<CrmLeadsModel>> viewLeadInfo(Model model, @RequestParam String id,
				HttpSession session) {

			logger.info("Method : viewLeadInfo starts" + id);

			JsonResponse<List<CrmLeadsModel>> jsonResponse = new JsonResponse<List<CrmLeadsModel>>();
			try {
				jsonResponse = restTemplate.getForObject(env.getPipeline() + "view-rest-LeadInfo?id=" + id,
						JsonResponse.class);

			} catch (Exception e) {
				e.printStackTrace();
			}

			if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

			} else {
				jsonResponse.setMessage("Success");
			}

			logger.info("REsp" + jsonResponse);
			logger.info("Method : viewLeadInfo ends");
			return jsonResponse;
		}
		
		//view-crm-leads-detail-converted

		/*
				 * post mapping for converted lead to Contact Account
				 */

				@SuppressWarnings("unchecked")

				@PostMapping("/view-crm-leads-detail-converted")
				public @ResponseBody JsonResponse<Object> leadConvertedToAccContDeal(@RequestBody CrmLeadsModel crmLeadsModel,
						HttpSession session) {

					logger.info("Method : leadConvertedToAccContDeal starts");

					JsonResponse<Object> resp = new JsonResponse<Object>();
					logger.info("web leadConvertedToAccContDeal lead======================" + crmLeadsModel);
					try {
						String userId = "";
						try {
							userId = (String) session.getAttribute("USER_ID");
						} catch (Exception e) {
							e.printStackTrace();
						}

						crmLeadsModel.setCreatedBy(userId);

						resp = restTemplate.postForObject(env.getPipeline() + "/convertToAccContDeal", crmLeadsModel, JsonResponse.class);

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

					logger.info("Method : leadConvertedToAccContDeal ends");

					return resp;
				}
				

		
		//view-crm-leads-view-note-Details
		
		@SuppressWarnings("unchecked")
		@GetMapping("view-crm-leads-view-detail-note")
		public @ResponseBody JsonResponse<List<CrmLeadsModel>> viewLeadNoteInfo(Model model, @RequestParam String id,
				HttpSession session) {

			logger.info("Method : viewLeadNoteInfo starts" + id);

			JsonResponse<List<CrmLeadsModel>> jsonResponse = new JsonResponse<List<CrmLeadsModel>>();
			try {
				jsonResponse = restTemplate.getForObject(env.getPipeline() + "view-rest-LeadNoteInfo?id=" + id,
						JsonResponse.class);

			} catch (Exception e) {
				e.printStackTrace();
			}
			ObjectMapper mapper = new ObjectMapper();

			List<CrmLeadsModel> leadModel = mapper.convertValue(jsonResponse.getBody(),
					new TypeReference<List<CrmLeadsModel>>() {
					});
			
			String date = "";
			String dateFormat = (String) (session).getAttribute("DATEFORMAT");
			for (CrmLeadsModel i : leadModel) {
				if (i.getNoteDoc() != null && i.getNoteDoc() != "") {
					String fileEmployeeimg = env.getBaseURL() + "document/crm/" + i.getNoteDoc();
					logger.info("image for document--------"+fileEmployeeimg);
					i.setNoteDocLink(fileEmployeeimg); 
				}
				
				if (i.getLeadImage() != null && i.getLeadImage() != "") {
					String leadImage = env.getBaseURL() + "document/crm/" + i.getLeadImage();
					logger.info("image for getLeadImage--------"+leadImage);
					i.setLeadImageLink(leadImage); 
				}
				
				if (i.getOwnerImage() != null && i.getOwnerImage() != "") {
					String ownerImage = env.getBaseURL() + "document/crm/" + i.getOwnerImage();
					logger.info("image for ownerImage--------"+ownerImage);
					i.setOwnerImageLink(ownerImage); 
				}
				
				
				if (i.getContactOwnerImage() != null && i.getContactOwnerImage() != "") {
					String contactOwnerImage = env.getBaseURL() + "document/crm/" + i.getContactOwnerImage();
					logger.info("image for contactOwnerImage--------"+contactOwnerImage);
					i.setContactOwnerLink(contactOwnerImage); 
				}
				
				if (i.getAccountOwnerImage() != null && i.getAccountOwnerImage() != "") {
					String accountOwnerImage = env.getBaseURL() + "document/crm/" + i.getAccountOwnerImage();
					logger.info("image for account owner Image--------"+accountOwnerImage);
					i.setAccountOwnerImageLink(accountOwnerImage); 
				}
				
				if (i.getDealOwnerImage() != null && i.getDealOwnerImage() != "") {
					String dealOwnerImage = env.getBaseURL() + "document/crm/" + i.getDealOwnerImage();
					logger.info("image for deal owner Image--------"+dealOwnerImage);
					i.setDealOwnerImageLink(dealOwnerImage); 
				}
				
				if (i.getQuoteOwnerImage() != null && i.getQuoteOwnerImage() != "") {
					String quoteOwnerImage = env.getBaseURL() + "document/crm/" + i.getQuoteOwnerImage();
					logger.info("image for quote owner Image--------"+quoteOwnerImage);
					i.setQuoteOwnerImageLink(quoteOwnerImage); 
				}
				
				if (i.getSoOwnerImage() != null && i.getSoOwnerImage() != "") {
					String soOwnerImage = env.getBaseURL() + "document/crm/" + i.getSoOwnerImage();
					logger.info("image for so owner Image--------"+soOwnerImage);
					i.setSoOwnerImageLink(soOwnerImage); 
				}
				
				if (i.getPoOwnerImage() != null && i.getPoOwnerImage() != "") {
					String poOwnerImage = env.getBaseURL() + "document/crm/" + i.getPoOwnerImage();
					logger.info("image for po owner Image--------"+poOwnerImage);
					i.setPoOwnerImageLink(poOwnerImage); 
				}
				
				if (i.getInvOwnerImage() != null && i.getInvOwnerImage() != "") {
					String invOwnerImage = env.getBaseURL() + "document/crm/" + i.getInvOwnerImage();
					logger.info("image for invoice owner Image--------"+invOwnerImage);
					i.setInvOwnerImageLink(invOwnerImage); 
				}
				
				
				if (i.getCreatedDate() != null && i.getCreatedDate() != "") {
					date = DateFormatter.dateFormat(i.getCreatedDate(), dateFormat);
					i.setCreatedDate(date);
					logger.info("start date---------------"+date);
				}
			}

			logger.info("###" + leadModel);
			jsonResponse.setBody(leadModel);

			if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

			} else {
				jsonResponse.setMessage("Success");
			}

			logger.info("REsp" + jsonResponse);
			logger.info("Method : viewLeadNoteInfo ends");
			return jsonResponse;
		}
		
		////view-crm-leads-view-detail-mail
		
		@SuppressWarnings("unchecked")
		@GetMapping("view-crm-leads-view-detail-mail")
		public @ResponseBody JsonResponse<List<CrmLeadsModel>> viewLeadMailInfo(Model model, @RequestParam String id,
				HttpSession session) {

			logger.info("Method : viewLeadMailInfo starts" + id);

			JsonResponse<List<CrmLeadsModel>> jsonResponse = new JsonResponse<List<CrmLeadsModel>>();
			try {
				jsonResponse = restTemplate.getForObject(env.getPipeline() + "view-rest-LeadMailInfo?id=" + id,
						JsonResponse.class);

			} catch (Exception e) {
				e.printStackTrace();
			}
		

			if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

			} else {
				jsonResponse.setMessage("Success");
			}

			logger.info("REsp" + jsonResponse);
			logger.info("Method : viewLeadMailInfo ends");
			return jsonResponse;
		}
		
		//view-crm-leads-view-detail-product
		
		@SuppressWarnings("unchecked")
		@GetMapping("view-crm-leads-view-detail-product")
		public @ResponseBody JsonResponse<List<CrmProductModel>> viewLeadProductInfo(Model model, @RequestParam String id,
				HttpSession session) {

			logger.info("Method : viewLeadProductInfo starts" + id);

			JsonResponse<List<CrmProductModel>> jsonResponse = new JsonResponse<List<CrmProductModel>>();
			try {
				jsonResponse = restTemplate.getForObject(env.getPipeline() + "view-rest-LeadProductInfo?id=" + id,
						JsonResponse.class);

			} catch (Exception e) {
				e.printStackTrace();
			}
			
			ObjectMapper mapper = new ObjectMapper();
			String supportStartDate = "";
			String supportEndDate = "";
			String dateFormat = (String) (session).getAttribute("DATEFORMAT");
			
			List<CrmProductModel> productModel = mapper.convertValue(jsonResponse.getBody(),
					new TypeReference<List<CrmProductModel>>() {
					});
			
			for (CrmProductModel i : productModel) {
				if (i.getSupportStartDate() != null && i.getSupportStartDate() != "") {
					supportStartDate = DateFormatter.dateFormat(i.getSupportStartDate(), dateFormat);
					i.setSupportStartDate(supportStartDate);
					logger.info("supportStartDate---------------"+supportStartDate);
				}
				
				if (i.getSupportEndDate() != null && i.getSupportEndDate() != "") {
					supportEndDate = DateFormatter.dateFormat(i.getSupportEndDate(), dateFormat);
					i.setSupportEndDate(supportEndDate);
					logger.info("supportEndDate---------------"+supportEndDate);
				}
			}
			
			jsonResponse.setBody(productModel);
		

			if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

			} else {
				jsonResponse.setMessage("Success");
			}

			logger.info("REsp" + jsonResponse);
			logger.info("Method : viewLeadProductInfo ends");
			return jsonResponse;
		}
		
		
		//view-crm-leads-view-detail-campaign
		
		@SuppressWarnings("unchecked")
		@GetMapping("view-crm-leads-view-detail-campaign")
		public @ResponseBody JsonResponse<List<CrmCampaignModel>> viewLeadCampaignInfo(Model model, @RequestParam String id,
				HttpSession session) {

			logger.info("Method : viewLeadCampaignInfo starts" + id);

			JsonResponse<List<CrmCampaignModel>> jsonResponse = new JsonResponse<List<CrmCampaignModel>>();
			try {
				jsonResponse = restTemplate.getForObject(env.getPipeline() + "view-rest-LeadCampaignInfo?id=" + id,
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
					logger.info("start date---------------"+date);
				}
				
				if (i.getEndDate() != null && i.getEndDate() != "") {
					date1 = DateFormatter.dateFormat(i.getEndDate(), dateFormat);
					i.setEndDate(date1);
					logger.info("end date---------------"+date1);
				}
			}
			
			jsonResponse.setBody(campaignModel);
		

			if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

			} else {
				jsonResponse.setMessage("Success");
			}

			logger.info("REsp" + jsonResponse);
			logger.info("Method : viewLeadCampaignInfo ends");
			return jsonResponse;
		}
		
		//view-crm-leads-view-detail-task
		
		@SuppressWarnings("unchecked")
		@GetMapping("view-crm-leads-view-detail-task")
		public @ResponseBody JsonResponse<List<CrmLeadTaskModel>> viewLeadTaskInfo(Model model, @RequestParam String id,
				HttpSession session) {

			logger.info("Method : viewLeadTaskInfo starts" + id);

			JsonResponse<List<CrmLeadTaskModel>> jsonResponse = new JsonResponse<List<CrmLeadTaskModel>>();
			try {
				jsonResponse = restTemplate.getForObject(env.getPipeline() + "view-rest-LeadTaskInfo?id=" + id,
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
					logger.info("Due date---------------"+date);
				}
				
			}
			
			jsonResponse.setBody(taskModel);
		

			if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

			} else {
				jsonResponse.setMessage("Success");
			}

			logger.info("REsp" + jsonResponse);
			logger.info("Method : viewLeadTaskInfo ends");
			return jsonResponse;
		}
		
		//view-crm-leads-view-detail-meeting
		
		@SuppressWarnings("unchecked")
		@GetMapping("view-crm-leads-view-detail-meeting")
		public @ResponseBody JsonResponse<List<crmMeetingModel>> viewLeadMeetingInfo(Model model, @RequestParam String id,
				HttpSession session) {

			logger.info("Method : viewLeadMeetingInfo starts" + id);

			JsonResponse<List<crmMeetingModel>> jsonResponse = new JsonResponse<List<crmMeetingModel>>();
			try {
				jsonResponse = restTemplate.getForObject(env.getPipeline() + "view-rest-LeadMeetingInfo?id=" + id,
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
					logger.info("Meeting from date---------------"+date);
				}
				
				if (i.getMeetingToDate() != null && i.getMeetingToDate() != "") {
					date1 = DateFormatter.dateFormat(i.getMeetingToDate(), dateFormat);
					i.setMeetingToDate(date1);
					logger.info("Meeting to date---------------"+date1);
				}
				
			}
			
			jsonResponse.setBody(meetingModel);
		

			if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

			} else {
				jsonResponse.setMessage("Success");
			}

			logger.info("REsp" + jsonResponse);
			logger.info("Method : viewLeadMeetingInfo ends");
			return jsonResponse;
		}
		
		//view-crm-leads-view-detail-call
		
		@SuppressWarnings("unchecked")
		@GetMapping("view-crm-leads-view-detail-call")
		public @ResponseBody JsonResponse<List<CrmCallModel>> viewLeadCallInfo(Model model, @RequestParam String id,
				HttpSession session) {

			logger.info("Method : viewLeadCallInfo starts" + id);

			JsonResponse<List<CrmCallModel>> jsonResponse = new JsonResponse<List<CrmCallModel>>();
			try {
				jsonResponse = restTemplate.getForObject(env.getPipeline() + "view-rest-LeadCallInfo?id=" + id,
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
					logger.info("Call from date---------------"+date);
				}
			}
			
			jsonResponse.setBody(callModel);
			if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

			} else {
				jsonResponse.setMessage("Success");
			}
			logger.info("REsp" + jsonResponse);
			logger.info("Method : viewLeadCallInfo ends");
			return jsonResponse;
		}
		
		
		//view-crm-leads-view-detail-activity
		
		@SuppressWarnings("unchecked")
		@GetMapping("view-crm-leads-view-detail-activity")
		public @ResponseBody JsonResponse<List<CrmActivityModel>> viewLeadActivityInfo(Model model, @RequestParam String id,
				HttpSession session) {

			logger.info("Method : viewLeadActivityInfo starts" + id);

			JsonResponse<List<CrmActivityModel>> jsonResponse = new JsonResponse<List<CrmActivityModel>>();
			try {
				jsonResponse = restTemplate.getForObject(env.getPipeline() + "view-rest-LeadActivityInfo?id=" + id,
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
		

	/*
	 * upload image
	 */
	@PostMapping("/view-crm-leads-upload-file")
	public @ResponseBody JsonResponse<Object> uploadFile(@RequestParam("file") MultipartFile inputFile,
			HttpSession session) {
		logger.info("Method : employee uploadimage controller  starts");

		JsonResponse<Object> response = new JsonResponse<Object>();

		try {
			response.setMessage(inputFile.getOriginalFilename());
			// logger.info(inputFile);
			session.setAttribute("employeePFile", inputFile);

		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : employee uploadimage controller ' ends" + response);
		return response;
	}

	@PostMapping("/view-crm-leads-delete-file")
	public @ResponseBody JsonResponse<Object> deleteFile(HttpSession session) {
		logger.info("Method : deleteFile employee uploadimage controller starts");

		JsonResponse<Object> response = new JsonResponse<Object>();

		try {
			session.removeAttribute("employeePFile");
		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : deleteFile employee uploadimage controller ends");
		return response;
	}
	
	
	
	//delete 
			@SuppressWarnings("unchecked")
			@GetMapping("view-crm-leads-deleteDetails")
			public @ResponseBody JsonResponse<Object> deleteDetails(@RequestParam String id,
					 HttpSession session) {
				logger.info("Method : deleteDetails function starts"+id);

				JsonResponse<Object> res = new JsonResponse<Object>();

				

				try {
					res = restTemplate.getForObject(env.getPipeline() + "delete-Details?id=" + id  , JsonResponse.class);
				} catch (RestClientException e) {
					e.printStackTrace();
				}

				String message = res.getMessage();
				if (message != null && message != "") {

				} else {
					res.setMessage("Success");
				}
				logger.info("Method : deleteDetails function Ends");
				
				logger.info("RESPPPPPPP"+res);
				return res;
			}
			
			
			/*
			 * //Main save for Task
			 * 
			 */

			@SuppressWarnings({ "unchecked" })
			@PostMapping(value = "view-crm-leads-save-task")
			public @ResponseBody JsonResponse<Object> saveCRMLeadTask(@RequestBody List<CrmLeadsModel> leadModel,
					HttpSession session) {
				logger.info("Method : saveCRMLeadTask function starts"+leadModel);

				//logger.info(purchaseOrder);
				JsonResponse<Object> resp = new JsonResponse<Object>();
				String userId = "";

				try {
					userId = (String) session.getAttribute("USER_ID");
				} catch (Exception e) {

				}
				for (CrmLeadsModel m : leadModel) {
					m.setCreatedBy(userId);
					
				}
				try {

					resp = restTemplate.postForObject(env.getPipeline() + "saveleadtask", leadModel,
							JsonResponse.class);

				} catch (RestClientException e) {
					e.printStackTrace();
				}

				String message = resp.getMessage();

				if (message != null && message != "") {

				} else {
					resp.setMessage("Success");
				}
				//logger.info("WEBBB" + resp);
				logger.info("Method : saveLeadTask function Ends"+resp);

				return resp;
			}
			
			
			/*
			 * //Main save for macro
			 * 
			 */

			@SuppressWarnings({ "unchecked" })
			@PostMapping(value = "view-crm-leads-save-macro")
			public @ResponseBody JsonResponse<Object> saveCRMLeadMacro(@RequestBody List<CrmLeadsModel> leadModel,
					HttpSession session) {
				logger.info("Method : saveCRMLeadMacro function starts"+leadModel);

				//logger.info(purchaseOrder);
				JsonResponse<Object> resp = new JsonResponse<Object>();
				String userId = "";

				try {
					userId = (String) session.getAttribute("USER_ID");
				} catch (Exception e) {

				}
				for (CrmLeadsModel m : leadModel) {
					m.setCreatedBy(userId);
					
				}
				try {

					resp = restTemplate.postForObject(env.getPipeline() + "saveleadMacro", leadModel,
							JsonResponse.class);

				} catch (RestClientException e) {
					e.printStackTrace();
				}

				String message = resp.getMessage();

				if (message != null && message != "") {

				} else {
					resp.setMessage("Success");
				}
				//logger.info("WEBBB" + resp);
				logger.info("Method : saveCRMLeadMacro function Ends"+resp);

				return resp;
			}
			
			
			//view-crm-leads-save-campaigns
			
			@SuppressWarnings({ "unchecked" })
			@PostMapping(value = "view-crm-leads-save-campaigns")
			public @ResponseBody JsonResponse<Object> saveCRMLeadCampaign(@RequestBody List<CrmLeadsModel> leadModel,
					HttpSession session) {
				logger.info("Method : saveCRMLeadCampaign function starts"+leadModel);

				//logger.info(purchaseOrder);
				JsonResponse<Object> resp = new JsonResponse<Object>();
				String userId = "";

				try {
					userId = (String) session.getAttribute("USER_ID");
				} catch (Exception e) {

				}
				for (CrmLeadsModel m : leadModel) {
					m.setCreatedBy(userId);
					
				}
				
				try {

					resp = restTemplate.postForObject(env.getPipeline() + "saveleadCampaign", leadModel,
							JsonResponse.class);

				} catch (RestClientException e) {
					e.printStackTrace();
				}

				String message = resp.getMessage();

				if (message != null && message != "") {

				} else {
					resp.setMessage("Success");
				}
				//logger.info("WEBBB" + resp);
				logger.info("Method : saveCRMLeadCampaign function Ends"+resp);

				return resp;
			}
			
			
			/*
			 * //Main save for mail
			 * 
			 */
			
			
			
			
			@SuppressWarnings({ "unchecked" })
			@PostMapping(value = "view-crm-leads-save-mail")
			public @ResponseBody JsonResponse<Object> saveCRMLeadMail(@RequestBody List<CrmLeadsModel> leadModel,
					HttpSession session) {
				logger.info("Method : saveCRMLeadMail function starts"+leadModel);

				//logger.info(purchaseOrder);
				JsonResponse<Object> resp = new JsonResponse<Object>();
				String userId = "";

				try {
					userId = (String) session.getAttribute("USER_ID");
				} catch (Exception e) {

				}
				for (CrmLeadsModel m : leadModel) {
					m.setCreatedBy(userId);
					
				}
				try {

					resp = restTemplate.postForObject(env.getPipeline() + "saveleadmails", leadModel,
							JsonResponse.class);

				} catch (RestClientException e) {
					e.printStackTrace();
				}

				String message = resp.getMessage();

				if (message != null && message != "") {

				} else {
					resp.setMessage("Success");
				}
				//logger.info("WEBBB" + resp);
				logger.info("Method : saveCRMLeadMail function Ends"+resp);

				return resp;
			}
			
			//view-crm-leads-save-tags
			
			/*
			 * //Main save for Task
			 * 
			 */

			@SuppressWarnings({ "unchecked" })
			@PostMapping(value = "view-crm-leads-save-tags")
			public @ResponseBody JsonResponse<Object> saveCRMLeadTags(@RequestBody List<CrmLeadsModel> leadModel,
					HttpSession session) {
				logger.info("Method : saveCRMLeadTags function starts"+leadModel);

				//logger.info(purchaseOrder);
				JsonResponse<Object> resp = new JsonResponse<Object>();
				String userId = "";

				try {
					userId = (String) session.getAttribute("USER_ID");
				} catch (Exception e) {

				}
				for (CrmLeadsModel m : leadModel) {
					m.setCreatedBy(userId);
					
				}
				try {

					resp = restTemplate.postForObject(env.getPipeline() + "saveleadtags", leadModel,
							JsonResponse.class);

				} catch (RestClientException e) {
					e.printStackTrace();
				}

				String message = resp.getMessage();

				if (message != null && message != "") {

				} else {
					resp.setMessage("Success");
				}
				//logger.info("WEBBB" + resp);
				logger.info("Method : saveCRMLeadTags function Ends"+resp);

				return resp;
			}
			
			
			@SuppressWarnings({ "unchecked" })
			@PostMapping(value="view-crm-leads-add-macro")
			public @ResponseBody JsonResponse<Object> addOrganisation(
					@RequestBody CrmLeadTaskModel masterOrganisationalModel, Model model, HttpSession session) {
				logger.info("Method :addTask starts");
				logger.info("@@@@@@@@@@@@@@@@" + masterOrganisationalModel);
				JsonResponse<Object> resp = new JsonResponse<Object>();
				
				try {
					
					resp = restTemplate.postForObject(env.getPipeline() + "rest-addTask-Leads",
							masterOrganisationalModel, JsonResponse.class);
				} catch (RestClientException e) {
					e.printStackTrace();
				}
				String message = resp.getMessage();
				if (message != null && message != "") {

				} else {
					resp.setMessage("Success");
				}
				logger.info("Method : addTask ends");
				return resp;
			}
			
			@SuppressWarnings("unchecked")
			@PostMapping("/view-crm-leads-massUpdate")
			public @ResponseBody JsonResponse<Object> massUpdate(@RequestBody CrmLeadsModel crmModel, Model model,
					HttpSession session) {

				logger.info("Method : massUpdate starts" + crmModel);

				JsonResponse<Object> resp = new JsonResponse<Object>();
				try {

					resp = restTemplate.postForObject(env.getPipeline() + "rest-add-massUpdate", crmModel,
							JsonResponse.class);

				} catch (RestClientException e) {

					e.printStackTrace();
				}

				if (resp.getMessage() == "") {
					resp.setMessage("Success");
				}
				logger.info("Method : massUpdate ends" + resp);

				return resp;
			}
			///////////////////////////////////Document save for note/////////////////////////////////////////////////
			
			public String saveAllImage(byte[] imageBytes) {
				logger.info("Method : saveAllImage starts");

				String imageName = null;

				try {
					if (imageBytes != null) {
						long nowTime = new Date().getTime();
						imageName = nowTime + ".png";
					}

					Path path = Paths.get(env.getFileUploadCrmUrl() + imageName);
					logger.info("path for image write----------"+path);
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
							logger.info("thumb image path--------------"+pathThumb);
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
						logger.info("path for pdf write----------"+path);
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
			@PostMapping("/view-crm-leads-add-notes-ajax")
			public @ResponseBody JsonResponse<Object> addNoteDoc(@RequestBody EmployeeDocumentModel employeeDocumentModel,
					HttpSession session) {
				logger.info("Method : addNoteDoc starts");

				JsonResponse<Object> resp = new JsonResponse<Object>();
				String userId = "";
				
				try {
					userId = (String) session.getAttribute("USER_ID");
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				employeeDocumentModel.setCreatedBy(userId);
				for (InventoryVendorDocumentModel a : employeeDocumentModel.getDocumentList()) {
					//if (a.getImageNameEdit() != null && a.getImageNameEdit() != "") {
					//	logger.info("hello doc getImageNameEdit---------"+a.getImageNameEdit());
						//a.setFileName(a.getImageNameEdit());
					//} else {
						logger.info("hello doc list---------"+employeeDocumentModel.getDocumentList());
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
					//}
				}
				
				try {
					resp = restTemplate.postForObject(env.getPipeline() + "addNoteDoc", employeeDocumentModel,
							JsonResponse.class);

					
				} catch (RestClientException e) {
					e.printStackTrace();
				}
				
				String message = resp.getMessage();

				if (message != null && message != "") {

				} else {
					resp.setMessage("success");
				}

				logger.info("Method : addNoteDoc end");
				return resp;
			}
			
			
			

        //view-crm-leads-details-product-view

		@SuppressWarnings("unchecked")
		@GetMapping("view-crm-leads-details-product-view")
		public @ResponseBody JsonResponse<List<CrmProductModel>> viewProductDetails(Model model, @RequestParam String id,
				@RequestParam String id2,@RequestParam String pageType,@RequestParam String productCode, HttpSession session) {

			logger.info("Method : viewProductDetails starts" + id);

			JsonResponse<List<CrmProductModel>> jsonResponse = new JsonResponse<List<CrmProductModel>>();
			try {
				jsonResponse = restTemplate.getForObject(env.getPipeline() + "viewProductDetailsView?id=" + id+ "&id2=" + id2+ "&pageType=" + pageType+ "&productCode=" + productCode,JsonResponse.class);
				

			} catch (Exception e) {
				e.printStackTrace();
			}
		

			if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

			} else {
				jsonResponse.setMessage("Success");
			}

			logger.info("REsp" + jsonResponse);
			logger.info("Method : viewProductDetails ends");
			return jsonResponse;
		}

		
		//view-crm-leads-add-product
		
			/*
			 * //Main save for Task
			 * 
			 */
		

			@SuppressWarnings({ "unchecked" })
			@PostMapping(value = "view-crm-leads-add-product")
			public @ResponseBody JsonResponse<Object> saveCRMProductAdd(@RequestParam String id,@RequestBody List<CrmLeadsModel> leadModel,
					HttpSession session) {
				logger.info("Method : saveCRMProductAdd function starts"+leadModel);

				//logger.info(purchaseOrder);
				JsonResponse<Object> resp = new JsonResponse<Object>();
				String userId = "";

				try {
					userId = (String) session.getAttribute("USER_ID");
				} catch (Exception e) {

				}
				for (CrmLeadsModel m : leadModel) {
					m.setCreatedBy(userId);
					
				}
				try {

					resp = restTemplate.postForObject(env.getPipeline() + "saveCRMProductAdd?id=" + id, leadModel,
							JsonResponse.class);

				} catch (RestClientException e) {
					e.printStackTrace();
				}

				String message = resp.getMessage();

				if (message != null && message != "") {

				} else {
					resp.setMessage("Success");
				}
				//logger.info("WEBBB" + resp);
				logger.info("Method : saveCRMProductAdd function Ends"+resp);

				return resp;
			}

		    //view-crm-leads-autosearchProduct
		/*	@SuppressWarnings("unchecked")
			@PostMapping(value = { "view-crm-leads-autosearchProduct" })
			public @ResponseBody JsonResponse<DropDownModel> getNameAutoSearchProduct(Model model,
					@RequestBody String searchVal, @RequestBody String leadId,@RequestBody String assigRow,
					BindingResult result) {
				logger.info("Method : getNameAutoSearchProduct starts");
				JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();

				try {
					res = restTemplate.getForObject(env.getPipeline() + "getNameAutoSearchProduct?id=" + searchVal+ "&leadId=" + leadId+ "&assigRow=" + assigRow,
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
				logger.info("Method : getNameAutoSearchProduct ends");
				logger.info("AUTOSEARCHHH" + res);
				return res;
			}	*/
			
			//view-crm-leads-autosearchProduct
			
			@SuppressWarnings("unchecked")
			@GetMapping("view-crm-leads-autosearchProduct")
			public @ResponseBody JsonResponse<List<DropDownModel>> viewProductSearch(Model model, @RequestParam String searchVal,
					@RequestParam String id,@RequestParam String assigRow,@RequestParam String pageType,HttpSession session) {

				logger.info("Method : searchVal starts" + searchVal);

				JsonResponse<List<DropDownModel>> jsonResponse = new JsonResponse<List<DropDownModel>>();
				try {
					jsonResponse = restTemplate.getForObject(env.getPipeline() + "viewProductSearchView?searchVal=" + searchVal+ "&id=" + id+ "&assigRow=" + assigRow+ "&pageType=" + pageType,JsonResponse.class);
					

				} catch (Exception e) {
					e.printStackTrace();
				}
			

				if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

				} else {
					jsonResponse.setMessage("Success");
				}

				logger.info("REsp" + jsonResponse);
				logger.info("Method : DropDownModel ends");
				return jsonResponse;
			}
			
			
}
