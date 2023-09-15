package nirmalya.aathithya.webmodule.employee.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
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
import nirmalya.aathithya.webmodule.common.utils.EmailAttachmentSender;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.employee.model.EmployeeResignationModel;
import nirmalya.aathithya.webmodule.employee.model.ExtendExitManagementModel;

@Controller
@RequestMapping(value = { "employee/" })
public class EmployeeResignationController {
	Logger logger = LoggerFactory.getLogger(EmployeeResignationController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("employee-resignation")
	public String resignationApply(Model model, HttpSession session) {
		logger.info("Method : resignationApply starts");

		String userId = "";
		String userName = "";
		String userRole = "";
		String userManager = "";
		String orgName = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
			userManager = (String) session.getAttribute("MANAGER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		logger.info("userManager==" + userManager);
		// drop down for employee list
		try {
			DropDownModel[] emplist = restClient.getForObject(env.getEmployeeUrl() + "getEmployeeLists?orgName="+orgName+"&orgDivision="+orgDivision,
					DropDownModel[].class);
			List<DropDownModel> emplists = Arrays.asList(emplist);
			model.addAttribute("emplists", emplists);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] emplist = restClient.getForObject(env.getEmployeeUrl() + "getEmployeeListsCC?orgName="+orgName+"&orgDivision="+orgDivision+"&managerId="+userManager+"&userId="+userId,
					DropDownModel[].class);
			List<DropDownModel> emplists = Arrays.asList(emplist);
			model.addAttribute("emplistscc", emplists);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String splitData[] = userRole.split("r");
		String[] removedNull = Arrays.stream(splitData).filter(value -> value != "" && value.length() > 0)
				.toArray(size -> new String[size]);
		for (String part : removedNull) {
			String data = "r" + part;

			if (data.contentEquals("rol001") || data.contentEquals("rol003")) {
				model.addAttribute("hrRole", data);
			}
		}

		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("userRole", userRole);
		model.addAttribute("userManager", userManager);

		logger.info("Method : resignationApply ends");
		return "employee/employeeResignation.html";
	}
	/*
	 * Add Employee Resignation In Draft
	 *
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "employee-resignation-draft-apply")
	public @ResponseBody JsonResponse<Object> resinationApplyDraft(
			@RequestBody List<EmployeeResignationModel> employeeResignationModel, HttpSession session, Model model) {
		logger.info("Method : resinationApplyDraft function starts");
		String userId = "";
		String dateFormat = "";
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
		for (EmployeeResignationModel m : employeeResignationModel) {
			m.setCreatedBy(userId);
			m.setResignDate(DateFormatter.inputDateFormat(m.getResignDate(), dateFormat));
			m.setReleaseDate(DateFormatter.inputDateFormat(m.getReleaseDate(), dateFormat));
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);
		}
		logger.info("employeeResignationModel===" + employeeResignationModel);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {

			res = restClient.postForObject(env.getEmployeeUrl() + "rest-resignation-apply-draft", employeeResignationModel,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		if (res.getMessage() != "" && res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("Success");
		}

		logger.info("Method : resinationApply function Ends");
		return res;
	}
	/*
	 * View Resignation Details Draft
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("employee-resignation-draft-view")
	public @ResponseBody List<EmployeeResignationModel> viewResignationDraft(HttpSession session,@RequestParam String userid,@RequestParam String roleid) {

		logger.info("Method : viewResignationDraft starts");
		EmployeeResignationModel employeeResignationModel = new EmployeeResignationModel();
 		JsonResponse<List<EmployeeResignationModel>> resp = new JsonResponse<List<EmployeeResignationModel>>();
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}

		try {
			resp = restClient.getForObject(env.getEmployeeUrl() + "rest-viewResignationDraft?userId=" + userid +"&userRole="+ roleid+"&organization="+organization+"&orgDivision="+orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : viewResignationDraft ends"+employeeResignationModel.getOrganization());
		return resp.getBody();
	}
	
	
	//   edit Resignation apply Draft

	@SuppressWarnings("unchecked")
	@GetMapping("employee-resignation-draft-edit")
	public @ResponseBody JsonResponse<List<EmployeeResignationModel>> editResignationApplyDraft(@RequestParam String id, HttpSession session) {

		logger.info("Method : editResignationApplyDraft starts");
		//List<EmployeeResignationModel> reg = new ArrayList<EmployeeResignationModel>();
		JsonResponse<List<EmployeeResignationModel>> resp = new JsonResponse<List<EmployeeResignationModel>>();
		try {

			resp = restClient.getForObject(env.getEmployeeUrl() + "rest-editResignationApplyDraft?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();

		List<EmployeeResignationModel> regapply = mapper.convertValue(resp.getBody(),
				new TypeReference<List<EmployeeResignationModel>>() {
				});

		String dateFormat = "";
		try {

			dateFormat = (String) session.getAttribute("DATEFORMAT");
		} catch (Exception e) {

		}
		for (EmployeeResignationModel a : regapply) {
			if (a.getResignDate()!= null && a.getResignDate() != "") {
				a.setResignDate(DateFormatter.dateFormat(a.getResignDate(), dateFormat));
			}
			if (a.getReleaseDate() != null && a.getReleaseDate() != "") {
				a.setReleaseDate(DateFormatter.dateFormat(a.getReleaseDate(), dateFormat));
			}
		}
		resp.setBody(regapply);
		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");

		} else {
			// logger.info(resp.getMessage());
			resp.setMessage("Success");
		}
	logger.info(resp.toString());
		logger.info("Method : editResignationApplyDraft ends");

		return resp;
	}
	// delete resignation details table

	@SuppressWarnings("unchecked")
	@GetMapping("employee-resignation-delete")
	public @ResponseBody JsonResponse<EmployeeResignationModel> deleteResignationApply(@RequestParam String deleteId) {

		logger.info("Method : deleteResignationApply starts");
		//logger.info(deleteId);
		JsonResponse<EmployeeResignationModel> response = new JsonResponse<EmployeeResignationModel>();

		try {
			response = restClient.getForObject(env.getEmployeeUrl() + "rest-deleteResignation?id=" + deleteId,
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

		logger.info("Method : deleteResignationApply ends");
		return response;
	}
	
	
	
	/*
	 * Add Employee Resignation
	 *
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "employee-resignation-apply")
	public @ResponseBody JsonResponse<Object> resinationApply(
			@RequestBody List<EmployeeResignationModel> employeeResignationModel, HttpSession session, Model model) {
		logger.info("Method : resinationApply function starts");
		List<EmployeeResignationModel> empReg = new ArrayList<EmployeeResignationModel>();
		
		String userId = "";
		String userEmail = "";
		String dateFormat = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			userEmail = (String) session.getAttribute("USER_EMAIL");
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (EmployeeResignationModel m : employeeResignationModel) {
			m.setCreatedBy(userId);
			m.setResignDate(DateFormatter.inputDateFormat(m.getResignDate(), dateFormat));
			m.setReleaseDate(DateFormatter.inputDateFormat(m.getReleaseDate(), dateFormat));
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);
		}
		logger.info("employeeResignationModel===" + employeeResignationModel);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {

			res = restClient.postForObject(env.getEmployeeUrl() + "rest-resignation-apply", employeeResignationModel,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		if (res.getMessage() != "" && res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("Success");

			String tolist = employeeResignationModel.get(0).getRegTo();

			String cclist = employeeResignationModel.get(0).getRegCC();

			String comma = ",";
			String newlist = tolist.concat(comma).concat(cclist);
			String str[] = newlist.split(",");
			
			logger.info("str " + Arrays.toString(str));
		 
			ArrayList<String> mylist = new ArrayList<String>();
			mylist.addAll(Arrays.asList(str));
 
			JsonResponse<Object> resp = new JsonResponse<Object>();
			List<String> toAddress = new ArrayList<String>();
			List<String> CcAddress = new ArrayList<String>();

			String userName = "";
			try {
				userName = (String) session.getAttribute("USER_NAME");
			} catch (Exception e) {
			}

			String subject = "From :-" + userName + System.lineSeparator() + "Subject:-"
					+ employeeResignationModel.get(0).getSubject();
			String message1 = employeeResignationModel.get(0).getReason();

			for (int i = 0; i < mylist.size(); i++) {
				try {
					resp = restClient.getForObject(env.getEmployeeUrl() + "get_maildetails?mylist=" + mylist.get(i),
							JsonResponse.class);
					logger.info("emails:- " + (String) resp.getBody());
					toAddress.add((String) resp.getBody());
				} catch (RestClientException e) {
					e.printStackTrace();
				}
			}
			
			//CcAddress.add("amarendramaity108@gmail.com");
			//toAddress.add("rajendranarayan716@gmail.com");
			// logger.info("toAddress "+toAddress);
			// EmailAttachmentSender email = new EmailAttachmentSender();

			try {
				logger.info("message1 " + message1);
				EmailAttachmentSender.sendEmailWithAttachments("smtp.gmail.com", "587",userEmail,
						"Nirmalya@123",toAddress, CcAddress, subject, message1, null);
 
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			res.setBody(empReg);
		}

		logger.info("Method : resinationApply function Ends");
		return res;
	}
	

	
	/*
	 * View Resignation Details
	 */
	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @GetMapping("employee-resignation-view") public @ResponseBody
	 * List<EmployeeResignationModel> viewResignation(HttpSession
	 * session,@RequestParam String userid,@RequestParam String roleid) {
	 * 
	 * logger.info("Method : viewResignation starts");
	 * JsonResponse<List<EmployeeResignationModel>> resp = new
	 * JsonResponse<List<EmployeeResignationModel>>(); try { resp =
	 * restClient.getForObject(env.getEmployeeUrl() + "rest-viewResignation?userId="
	 * + userid +"&userRole="+ roleid, JsonResponse.class); } catch
	 * (RestClientException e) { e.printStackTrace(); } ObjectMapper mapper = new
	 * ObjectMapper(); List<EmployeeResignationModel> viewReg =
	 * mapper.convertValue(resp.getBody(), new
	 * TypeReference<List<EmployeeResignationModel>>() { }); for
	 * (EmployeeResignationModel m : viewReg) { if (m.getStatus().equals("0")) {
	 * m.setStatus("Pending"); }else if (m.getStatus().equals("1")) {
	 * m.setStatus("Forwarded"); } else if (m.getStatus().equals("3")) {
	 * m.setStatus("Approved"); }else{ m.setStatus("Rejected"); } }
	 * resp.setBody(viewReg); logger.info("Method : viewResignation ends"); return
	 * resp.getBody(); }
	 */
	/*
	 * View Exit Management
	 */

	@SuppressWarnings("unchecked")

	@GetMapping("employee-resignation-view")
	public @ResponseBody List<ExtendExitManagementModel> viewResignation(HttpSession session,@RequestParam String userid,
			@RequestParam String roleid) {

		logger.info("Method : viewResignation starts");
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
		logger.info("Method : viewResignation ends");
		logger.info("VIEWWWWWWWWEXIT"+resp.getBody());
		return resp.getBody();
	}
	/*
	 * Get Employee Name for choosen Js(Edit)
	 */
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "employee-resignation-getempname" })
	public @ResponseBody JsonResponse<DropDownModel> getDraftCcName(Model model, @RequestParam("id") String id,
			HttpSession session) {
		logger.info("Method :getDraftCcName starts");
		JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();

		try {
			res = restClient.getForObject(env.getEmployeeUrl() + "rest-draft-getempname?id=" + id, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		// logger.info(res.getBody().getKey());
		logger.info("Method : getDraftCcName ends");
		logger.info("getDraftCcName " + res);
		return res;
	}
}
