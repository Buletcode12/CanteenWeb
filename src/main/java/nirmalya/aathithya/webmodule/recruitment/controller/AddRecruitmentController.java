package nirmalya.aathithya.webmodule.recruitment.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import nirmalya.aathithya.webmodule.recruitment.model.ActionEmployeeDetailsModel;
import nirmalya.aathithya.webmodule.recruitment.model.AddRecruitentModel;
import nirmalya.aathithya.webmodule.recruitment.model.RequisitionActivityModel;
import nirmalya.aathithya.webmodule.recruitment.model.RequisitionVendorAllocationModel;
import nirmalya.aathithya.webmodule.recruitment.model.RequisitionVendorModel;

@Controller
@RequestMapping(value = "recruitment")
public class AddRecruitmentController {

	Logger logger = LoggerFactory.getLogger(AddRecruitmentController.class);
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	// Summary
	@GetMapping("/view-new-requi-mstr")
	public String requisition(Model model, HttpSession session) {
		logger.info("Method : requisition starts");

		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {

			DropDownModel[] jobType = restTemplate.getForObject(env.getRecruitment() + "jobTypeList",
					DropDownModel[].class);
			List<DropDownModel> jobTypeList = Arrays.asList(jobType);
			model.addAttribute("jobTypeList", jobTypeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {

			DropDownModel[] location = restTemplate.getForObject(env.getRecruitment() + "jobLocationList",
					DropDownModel[].class);
			List<DropDownModel> jobLocationList = Arrays.asList(location);
			model.addAttribute("jobLocationList", jobLocationList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {

			DropDownModel[] department = restTemplate.getForObject(env.getRecruitment() + "DepartmentList",
					DropDownModel[].class);
			List<DropDownModel> DepartmentList = Arrays.asList(department);
			model.addAttribute("DepartmentList", DepartmentList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			ActionEmployeeDetailsModel[] manager = restTemplate.getForObject(env.getRecruitment() + "EmployeeFullList?org="+organization+"&orgDiv="+orgDivision,
			ActionEmployeeDetailsModel[].class);
			List<ActionEmployeeDetailsModel> managerList = Arrays.asList(manager);
			model.addAttribute("managerList", managerList);
			} catch (RestClientException e) {
			e.printStackTrace();
			}

		try {

			DropDownModel[] band = restTemplate.getForObject(
					env.getRecruitment() + "bandList?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> bandList = Arrays.asList(band);
			model.addAttribute("bandList", bandList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {

			DropDownModel[] designation = restTemplate.getForObject(env.getRecruitment() + "designationLists",
					DropDownModel[].class);
			List<DropDownModel> designationList = Arrays.asList(designation);
			model.addAttribute("designationList", designationList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {

			DropDownModel[] education = restTemplate.getForObject(env.getRecruitment() + "educationList",
					DropDownModel[].class);
			List<DropDownModel> educationList = Arrays.asList(education);
			model.addAttribute("educationList", educationList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {

			DropDownModel[] workHour = restTemplate.getForObject(env.getRecruitment() + "workHourList",
					DropDownModel[].class);
			List<DropDownModel> workHourList = Arrays.asList(workHour);
			model.addAttribute("workHourList", workHourList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {

			DropDownModel[] benefits = restTemplate.getForObject(env.getRecruitment() + "benefitsList",
					DropDownModel[].class);
			List<DropDownModel> benefitsList = Arrays.asList(benefits);
			model.addAttribute("benefitsList", benefitsList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {

			DropDownModel[] aboutCompany = restTemplate.getForObject(env.getRecruitment() + "aboutCompany",
					DropDownModel[].class);
			List<DropDownModel> aboutCompanyData = Arrays.asList(aboutCompany);
			model.addAttribute("aboutComapany", aboutCompanyData);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String userRole = "";

		try {
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		String splitData[] = userRole.split("r");
		String[] removedNull = Arrays.stream(splitData).filter(value -> value != "" && value.length() > 0)
				.toArray(size -> new String[size]);
		for (String part : removedNull) {
			String data = "r" + part;

			if (data.contentEquals("rol001") || data.contentEquals("rol002")) {
				model.addAttribute("hrRole", data);
			}
		}
		for (String part : removedNull) {
			String data = "r" + part;

			if (data.contentEquals("rol001")) {
				model.addAttribute("adminRole", data);
			}
		}

		model.addAttribute("userRole", userRole);
		logger.info("Method : requisition ends");

		return "recruitment/view-new-requi-mstr";
	}

	@GetMapping("/view-candidate")
	public String viewCandidate(Model model, HttpSession session) {

		logger.info("Method : viewCandidate starts");

		try {
			DropDownModel[] Gender = restTemplate.getForObject(env.getEmployeeUrl() + "getgenderList1",
					DropDownModel[].class);
			List<DropDownModel> genderTypeList = Arrays.asList(Gender);

			model.addAttribute("genderTypeList", genderTypeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] Nationality = restTemplate.getForObject(env.getEmployeeUrl() + "getnationalityList1",
					DropDownModel[].class);
			List<DropDownModel> nationalityList = Arrays.asList(Nationality);

			model.addAttribute("nationalityList", nationalityList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] BloodGroup = restTemplate.getForObject(env.getEmployeeUrl() + "getbloodgroupList1",
					DropDownModel[].class);
			List<DropDownModel> bloodgroupList = Arrays.asList(BloodGroup);

			model.addAttribute("bloodgroupList", bloodgroupList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] MaritalStatus = restTemplate.getForObject(env.getEmployeeUrl() + "getmaritalstatusList1",
					DropDownModel[].class);
			List<DropDownModel> maritalstatusList = Arrays.asList(MaritalStatus);

			model.addAttribute("maritalstatusList", maritalstatusList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] country = restTemplate.getForObject(env.getMasterUrl() + "getCountryListForLocation",
					DropDownModel[].class);
			List<DropDownModel> countryList = Arrays.asList(country);

			model.addAttribute("countryList", countryList);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		/*
		 * try { DropDownModel[] State = restTemplate.getForObject(env.getEmployeeUrl()
		 * + "getstateList1", DropDownModel[].class); List<DropDownModel> stateList =
		 * Arrays.asList(State);
		 * 
		 * model.addAttribute("stateList", stateList); } catch (RestClientException e) {
		 * e.printStackTrace(); }
		 */
		/*
		 * try { DropDownModel[] City = restTemplate.getForObject(env.getEmployeeUrl() +
		 * "getcityList1", DropDownModel[].class); List<DropDownModel> cityList =
		 * Arrays.asList(City);
		 * 
		 * model.addAttribute("cityList", cityList); } catch (RestClientException e) {
		 * e.printStackTrace(); }
		 */
		try {

			DropDownModel[] education = restTemplate.getForObject(env.getRecruitment() + "educationList",
					DropDownModel[].class);
			List<DropDownModel> educationList = Arrays.asList(education);
			model.addAttribute("educationList", educationList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {

			DropDownModel[] addressType = restTemplate.getForObject(env.getRecruitment() + "addressTypeList",
					DropDownModel[].class);
			List<DropDownModel> addressTypeList = Arrays.asList(addressType);
			model.addAttribute("addressTypeList", addressTypeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] Bank = restTemplate.getForObject(env.getEmployeeUrl() + "documentTypeList",
					DropDownModel[].class);
			List<DropDownModel> documentTypeList = Arrays.asList(Bank);
		
			model.addAttribute("documentTypeList", documentTypeList);
			logger.info("DOCUMENTTTTT"+documentTypeList);
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
		String[] removedNull = Arrays.stream(splitData).filter(value -> value != "" && value.length() > 0)
				.toArray(size -> new String[size]);
		for (String part : removedNull) {
			String data = "r" + part;

			if (data.contentEquals("rol001") || data.contentEquals("rol002")) {
				model.addAttribute("hrRole", data);
			}
		}
		for (String part : removedNull) {
			String data = "r" + part;

			if (data.contentEquals("rol001")) {
				model.addAttribute("adminRole", data);
			}
		}

		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("userRole", userRole);

		logger.info("Method : viewCandidate ends");

		return "recruitment/view-candidate";
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/view-new-requi-mstr-ajax")
	public @ResponseBody JsonResponse<Object> addRequisition(Model model, HttpSession session,
			@RequestBody AddRecruitentModel reqModel) {

		logger.info("Method : addRequisition starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String dateFormat = "";
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		reqModel.setCreatedBy(userId);
		reqModel.setOrganization(organization);
		reqModel.setOrgDivision(orgDivision);
		reqModel.setJoinDate(DateFormatter.inputDateFormat(reqModel.getJoinDate(), dateFormat));
		reqModel.setApplyStartDate(DateFormatter.inputDateFormat(reqModel.getApplyStartDate(), dateFormat));
		reqModel.setApplyEndDate(DateFormatter.inputDateFormat(reqModel.getApplyEndDate(), dateFormat));

		/*
		 * if (reqModel.getJoinDate() != null && reqModel.getJoinDate() != "") {
		 * reqModel.setJoinDate(DateFormatter.inputDateFormat(reqModel.getJoinDate(),
		 * dateFormat)); }
		 */
		logger.info("reqModel===" + reqModel);
		try {
			resp = restTemplate.postForObject(env.getRecruitment() + "addRequisition", reqModel, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : addRequisition ends");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-new-requi-mstr-view-data")
	public @ResponseBody List<AddRecruitentModel> viewRequisitionThroughAjax(Model model, HttpServletRequest request,
			HttpSession session) {
		logger.info("Method : viewRequisitionThroughAjax starts");

		JsonResponse<List<AddRecruitentModel>> jsonResponse = new JsonResponse<List<AddRecruitentModel>>();

		int count = 0;
		try {

			jsonResponse = restTemplate.getForObject(env.getRecruitment() + "viewRequistion", JsonResponse.class);

			ObjectMapper mapper = new ObjectMapper();

			List<AddRecruitentModel> addreq = mapper.convertValue(jsonResponse.getBody(),
					new TypeReference<List<AddRecruitentModel>>() {
					});
			for (AddRecruitentModel m : addreq) {
				String date = "";
				count++;
				m.setCount(count);
				/*
				 * if (m.getActivityStatus().equals("1")) { m.setActivityStatus("Created"); }
				 * else if (m.getActivityStatus().equals("2")) { m.setActivityStatus("Active");
				 * } else if (m.getActivityStatus().equals("3")) {
				 * m.setActivityStatus("Closed"); }
				 */
				String dateFormat = (String) (session).getAttribute("DATEFORMAT");
				if (m.getJoinDate() != null && m.getJoinDate() != "") {
					date = DateFormatter.dateFormat(m.getJoinDate(), dateFormat);
					m.setJoinDate(date);
				}
				if (m.getApplyStartDate() != null && m.getApplyStartDate() != "") {
					date = DateFormatter.dateFormat(m.getApplyStartDate(), dateFormat);
					m.setApplyStartDate(date);
				}
				if (m.getApplyEndDate() != null && m.getApplyEndDate() != "") {
					date = DateFormatter.dateFormat(m.getApplyEndDate(), dateFormat);
					m.setApplyEndDate(date);
				}
				if (m.getCreatedOn() != null && m.getCreatedOn() != "") {
					date = DateFormatter.dateFormat(m.getCreatedOn(), dateFormat);
					m.setCreatedOn(date);
				}
			}

			jsonResponse.setBody(addreq);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("Method ; viewRequisitionThroughAjax ends");
		logger.info("VIEWWWWW" + jsonResponse);
		return jsonResponse.getBody();
	}

	@SuppressWarnings("unchecked")
	@PostMapping("view-new-requi-mstr-delete")
	public @ResponseBody JsonResponse<Object> deleterequistion(Model model, @RequestParam String id,
			HttpSession session) {
		logger.info("Method : delectRequistion starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String createdBy = "";

		try {
			createdBy = (String) session.getAttribute("USER_ID");
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			resp = restTemplate.getForObject(env.getRecruitment() + "deleteRequistion?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("success");
		}

		logger.info("Method :  delectRequistion ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/view-new-requi-mstr-edit")
	public @ResponseBody JsonResponse<List<AddRecruitentModel>> editRequisition(Model model, @RequestParam String id,
			HttpSession session) {

		logger.info("Method : editRequisition starts");

		JsonResponse<List<AddRecruitentModel>> jsonResponse = new JsonResponse<List<AddRecruitentModel>>();

		try {
			jsonResponse = restTemplate.getForObject(env.getRecruitment() + "editRequisition?id=" + id,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		List<AddRecruitentModel> addreq = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<AddRecruitentModel>>() {
				});
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");

		for (AddRecruitentModel m : addreq) {

			if (m.getApplicants() == null && m.getApplicants() == "") {
				m.setApplicants("0");
			}
			if (m.getShortlisted() == null && m.getShortlisted() == "") {
				m.setShortlisted("0");
			}
			if (m.getInterviewed() == null && m.getInterviewed() == "") {
				m.setInterviewed("0");
			}

			if (m.getJoinDate() != null && m.getJoinDate() != "") {
				String date = DateFormatter.dateFormat(m.getJoinDate(), dateFormat);
				m.setJoinDate(date);
			}
			if (m.getApplyStartDate() != null && m.getApplyStartDate() != "") {
				String date = DateFormatter.dateFormat(m.getApplyStartDate(), dateFormat);
				m.setApplyStartDate(date);
			}
			if (m.getApplyEndDate() != null && m.getApplyEndDate() != "") {
				String date = DateFormatter.dateFormat(m.getApplyEndDate(), dateFormat);
				m.setApplyEndDate(date);
			}
			if (m.getCreatedOn() != null && m.getCreatedOn() != "") {
				String date = DateFormatter.dateFormat(m.getCreatedOn(), dateFormat);
				m.setCreatedOn(date);
			}
		}
		jsonResponse.setBody(addreq);
		String message = (String) session.getAttribute("message");

		if (message != null && message != "") {
			model.addAttribute("message", message);
		}

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("Method : editRequisition ends");
		logger.info("EDITTTT"+jsonResponse);
		return jsonResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/view-new-requi-mstr-view-activity")
	public @ResponseBody List<RequisitionActivityModel> activityRequisition(Model model, @RequestParam String id,
			HttpSession session) {

		logger.info("Method : activityRequisition starts");

		JsonResponse<List<RequisitionActivityModel>> jsonResponse = new JsonResponse<List<RequisitionActivityModel>>();

		try {
			jsonResponse = restTemplate.getForObject(env.getRecruitment() + "activityRequisition?id=" + id,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String message = (String) session.getAttribute("message");

		if (message != null && message != "") {
			model.addAttribute("message", message);
		}

		ObjectMapper mapper = new ObjectMapper();
		List<RequisitionActivityModel> req = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<RequisitionActivityModel>>() {
				});

		for (RequisitionActivityModel m : req) {
			String date = "";

			if (m.getActivityHistoryStatus().equals("1")) {
				m.setActivityHistoryStatus("Created");
			}
			if (m.getActivityHistoryStatus().equals("2")) {
				m.setActivityHistoryStatus("Updated");
			}

			String dateFormat = (String) (session).getAttribute("DATEFORMAT");
			if (m.getActivityHistoryDate() != null && m.getActivityHistoryDate() != "") {
				date = DateFormatter.dateFormat(m.getActivityHistoryDate(), dateFormat);
				m.setActivityHistoryDate(date);
			}

		}

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");

		} else {
			jsonResponse.setMessage("Success");
		}
		jsonResponse.setBody(req);

		logger.info("Method : activityRequisition ends");
		return jsonResponse.getBody();
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/view-new-requi-mstr-add-vendor-ajax")
	public @ResponseBody JsonResponse<Object> addRequisitionVendorAllocation(Model model, HttpSession session,
			@RequestBody RequisitionVendorAllocationModel allocModel) {

		logger.info("Method : addRequisitionVendorAllocation starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID");

		} catch (Exception e) {
			e.printStackTrace();
		}
		allocModel.setCreatedBy(userId);
		try {
			resp = restTemplate.postForObject(env.getRecruitment() + "addRequisitionVendorAllocation", allocModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() == "" || resp.getMessage() == null) {

			resp.setMessage("Success");
		}

		logger.info("Method : addRequisitionVendorAllocation ends");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-new-requi-mstr-view-data-ajax")
	public @ResponseBody List<RequisitionVendorModel> viewVendorAllocation() {
		logger.info("Method : viewVendorAllocation starts");

		JsonResponse<List<RequisitionVendorModel>> jsonResponse = new JsonResponse<List<RequisitionVendorModel>>();

		jsonResponse = restTemplate.getForObject(env.getRecruitment() + "get-vendor-list", JsonResponse.class);

		ObjectMapper mapper = new ObjectMapper();

		List<RequisitionVendorModel> addreq = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<RequisitionVendorModel>>() {
				});

		jsonResponse.setBody(addreq);

		logger.info("Method ; viewVendorAllocation ends");

		return jsonResponse.getBody();
	}

	// approve

	@SuppressWarnings("unchecked")
	@GetMapping("view-new-requi-mstr-approve")
	public @ResponseBody JsonResponse<AddRecruitentModel> approveApply(HttpSession session,
			@RequestParam String approveId, String name, String comment, String roleid) {

		logger.info("Method : approveRequisition starts");
		JsonResponse<AddRecruitentModel> response = new JsonResponse<AddRecruitentModel>();
		try {
			response = restTemplate.getForObject(env.getRecruitment() + "approveRequisitionapply?id=" + approveId
					+ "&name=" + name + "&comment=" + comment + "&userRole=" + roleid, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (response.getCode().equals("success")) {
			response.setMessage("Success");
		} else {
			response.setCode(response.getMessage());
			response.setMessage("Unsuccess");
		}
		logger.info("response=====" + response);
		logger.info("Method : approveRequisition ends");
		return response;
	}

	// reject

	@SuppressWarnings("unchecked")
	@GetMapping("view-new-requi-mstr-reject")
	public @ResponseBody JsonResponse<AddRecruitentModel> rejectApply(@RequestParam String rejectId, String name,
			String comment) {

		logger.info("Method : rejectRequisition starts");
		JsonResponse<AddRecruitentModel> response = new JsonResponse<AddRecruitentModel>();

		try {
			response = restTemplate.getForObject(env.getRecruitment() + "rejectRequisitionapply?id=" + rejectId
					+ "&name=" + name + "&comment=" + comment, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (response.getCode().equals("success")) {
			response.setMessage("Success");
		} else {
			response.setCode(response.getMessage());
			response.setMessage("Unsuccess");
		}
		logger.info("response=====" + response);
		logger.info("Method : rejectRequisition ends");
		return response;
	}

}
