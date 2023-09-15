package nirmalya.aathithya.webmodule.master.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.master.model.PayrollApprovalModel;
import nirmalya.aathithya.webmodule.master.model.PayrollModel;
import nirmalya.aathithya.webmodule.master.model.PayslipModel;
import nirmalya.aathithya.webmodule.recruitment.model.OfferletterModel;

@Controller
@RequestMapping(value = "master")
public class PayrollController {

	Logger logger = LoggerFactory.getLogger(PayrollController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	/***************** Process ********************/

	@GetMapping("/view-process")
	public String viewProcess(Model model, HttpSession session) {
		logger.info("Method : viewProcess starts");

		String userId = "";
		String userName = "";
		String userRole = "";
		String organization = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			DropDownModel[] year = restTemplate.getForObject(env.getMasterUrl() + "getYearList-attendance?organization="
					+ organization + "&orgDivision=" + orgDivision, DropDownModel[].class);
			List<DropDownModel> yearList = Arrays.asList(year);

			model.addAttribute("yearList", yearList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// drop down for month list
		try {
			DropDownModel[] month = restTemplate.getForObject(env.getMasterUrl() + "getMonthLists",
					DropDownModel[].class);
			List<DropDownModel> monthLists = Arrays.asList(month);
			model.addAttribute("monthLists", monthLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] dropDownModel = restTemplate.getForObject(env.getMasterUrl()
					+ "getBandMasterAttendance?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> bandType = Arrays.asList(dropDownModel);
			model.addAttribute("bandType", bandType);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] startDay = restTemplate.getForObject(env.getMasterUrl()
					+ "getStartDayForAttendance?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			model.addAttribute("startDayForAtten", startDay[0].getKey());
		} catch (Exception e) {
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
		model.addAttribute("organization", organization);
		model.addAttribute("orgDivision", orgDivision);

		logger.info("Method : viewProcess ends");
		return "master/viewProcess";

	}

	// for componets list
	@SuppressWarnings("unchecked")
	@GetMapping("view-process-getComponetList")
	public @ResponseBody JsonResponse<List<DropDownModel>> getComponetList(Model model, HttpSession session) {
		logger.info("Method : getComponetList starts");

		JsonResponse<List<DropDownModel>> resp = new JsonResponse<List<DropDownModel>>();
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-getComponetList?organization=" + organization
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("getComponetList==" + resp);
		logger.info("Method :  getComponetList ends");
		return resp;
	}

	/*
	 * Process view details
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("view-process-view-details")
	public @ResponseBody List<PayrollModel> viewProcess(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String band, HttpSession session, Model model) {
		logger.info("Method : viewProcess starts");

		JsonResponse<List<PayrollModel>> resp = new JsonResponse<List<PayrollModel>>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(
					env.getMasterUrl() + "rest-viewProcess?fromDate=" + fromDate + "&toDate=" + toDate + "&band=" + band
							+ "&userId=" + userId + "&organization=" + organization + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("resp.getBody()=====" + resp.getBody());
		logger.info("Method : viewProcess ends");
		return resp.getBody();
	}

	// for get Approver Status
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "/view-process-getApproverStatus" })
	public @ResponseBody JsonResponse<Object> getApproverStatus(HttpSession session, @RequestParam String fromDate,
			@RequestParam String userId) {
		logger.info("Method : getApproverStatus starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String process = "upm004";
		try {
			res = restTemplate.getForObject(
					env.getMasterUrl() + "rest-getApproverStatus?fromDate=" + fromDate + "&process=" + process
							+ "&userId=" + userId + "&organization=" + organization + "&orgDivision=" + orgDivision,
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
		logger.info("res=====" + res);
		logger.info("Method : getApproverStatus ends");
		return res;

	}

	/*
	 * Approve Process details
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-process-approve-details" })
	public @ResponseBody JsonResponse<Object> approveProcessDetails(Model model, HttpSession session,
			@RequestBody List<PayrollApprovalModel> data, BindingResult result) {
		logger.info("Method : approveProcessDetails starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (PayrollApprovalModel m : data) {
			m.setApprovedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);
		}
		logger.info("data=======" + data);
		try {
			res = restTemplate.postForObject(env.getMasterUrl() + "approveProcessDetails", data, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : approveProcessDetails ends");
		return res;

	}

	/***************** Approve ********************/
	@GetMapping("/view-approve")
	public String viewApprove(Model model, HttpSession session) {
		logger.info("Method : viewProcess starts");
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			DropDownModel[] year = restTemplate.getForObject(env.getMasterUrl() + "getYearList-attendance?organization="
					+ organization + "&orgDivision=" + orgDivision, DropDownModel[].class);
			List<DropDownModel> yearList = Arrays.asList(year);

			model.addAttribute("yearList", yearList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// drop down for month list
		try {
			DropDownModel[] month = restTemplate.getForObject(env.getMasterUrl() + "getMonthLists",
					DropDownModel[].class);
			List<DropDownModel> monthLists = Arrays.asList(month);
			model.addAttribute("monthLists", monthLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] dropDownModel = restTemplate.getForObject(env.getMasterUrl()
					+ "getBandMasterAttendance?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> bandType = Arrays.asList(dropDownModel);
			model.addAttribute("bandType", bandType);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] startDay = restTemplate.getForObject(env.getMasterUrl()
					+ "getStartDayForAttendance?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			model.addAttribute("startDayForAtten", startDay[0].getKey());
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : viewProcess ends");
		return "master/viewApprove";
	}

	/*
	 * Approve
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("view-approve-view-details")
	public @ResponseBody List<PayrollModel> viewApprove(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String band, HttpSession session, Model model) {

		logger.info("Method : viewApprove starts");

		JsonResponse<List<PayrollModel>> resp = new JsonResponse<List<PayrollModel>>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(
					env.getMasterUrl() + "rest-viewApprove?fromDate=" + fromDate + "&toDate=" + toDate + "&band=" + band
							+ "&userId=" + userId + "&organization=" + organization + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : viewApprove ends");
		return resp.getBody();
	}

	/************************************************
	 * Salary Advice
	 **********************************************/

	@GetMapping("/view-salary-advice")
	public String viewSalaryAdvice(Model model, HttpSession session) {
		logger.info("Method : viewSalaryAdvice starts");
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			DropDownModel[] year = restTemplate.getForObject(env.getMasterUrl() + "getYearList-attendance?organization="
					+ organization + "&orgDivision=" + orgDivision, DropDownModel[].class);
			List<DropDownModel> yearList = Arrays.asList(year);

			model.addAttribute("yearList", yearList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// drop down for month list
		try {
			DropDownModel[] month = restTemplate.getForObject(env.getMasterUrl() + "getMonthLists",
					DropDownModel[].class);
			List<DropDownModel> monthLists = Arrays.asList(month);
			model.addAttribute("monthLists", monthLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] dropDownModel = restTemplate.getForObject(env.getMasterUrl()
					+ "getBandMasterAttendance?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> bandType = Arrays.asList(dropDownModel);
			model.addAttribute("bandType", bandType);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] startDay = restTemplate.getForObject(env.getMasterUrl()
					+ "getStartDayForAttendance?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			model.addAttribute("startDayForAtten", startDay[0].getKey());
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : viewSalaryAdvice ends");
		return "master/salaryAdvice";
	}

	/*
	 * view Salary Advice
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("view-salary-advice-view-details")
	public @ResponseBody List<PayrollModel> viewSalaryAdvice(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String band, HttpSession session, Model model) {

		logger.info("Method : viewSalaryAdvice starts");

		JsonResponse<List<PayrollModel>> resp = new JsonResponse<List<PayrollModel>>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-viewSalaryAdvice?fromDate=" + fromDate
					+ "&toDate=" + toDate + "&band=" + band + "&userId=" + userId + "&organization=" + organization
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : viewSalaryAdvice ends");
		return resp.getBody();
	}

	/***************** EPF ********************/

	@GetMapping("/view-epf")
	public String viewEpf(Model model, HttpSession session) {
		logger.info("Method : viewEpf starts");
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			DropDownModel[] year = restTemplate.getForObject(env.getMasterUrl() + "getYearList-attendance?organization="
					+ organization + "&orgDivision=" + orgDivision, DropDownModel[].class);
			List<DropDownModel> yearList = Arrays.asList(year);

			model.addAttribute("yearList", yearList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// drop down for month list
		try {
			DropDownModel[] month = restTemplate.getForObject(env.getMasterUrl() + "getMonthLists",
					DropDownModel[].class);
			List<DropDownModel> monthLists = Arrays.asList(month);
			model.addAttribute("monthLists", monthLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] dropDownModel = restTemplate.getForObject(env.getMasterUrl()
					+ "getBandMasterAttendance?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> bandType = Arrays.asList(dropDownModel);
			model.addAttribute("bandType", bandType);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] startDay = restTemplate.getForObject(env.getMasterUrl()
					+ "getStartDayForAttendance?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			model.addAttribute("startDayForAtten", startDay[0].getKey());
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : viewEpf ends");
		return "master/viewEpf";
	}

	/*
	 * View EPF
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("view-epf-view-details")
	public @ResponseBody List<PayrollModel> viewEpf(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String band, HttpSession session, Model model) {

		logger.info("Method : viewEpf starts");

		JsonResponse<List<PayrollModel>> resp = new JsonResponse<List<PayrollModel>>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] year = restTemplate.getForObject(env.getMasterUrl() + "getYearList-attendance?organization="
					+ organization + "&orgDivision=" + orgDivision, DropDownModel[].class);
			List<DropDownModel> yearList = Arrays.asList(year);

			model.addAttribute("yearList", yearList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(
					env.getMasterUrl() + "rest-viewEpf?fromDate=" + fromDate + "&toDate=" + toDate + "&band=" + band
							+ "&userId=" + userId + "&organization=" + organization + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : viewEpf ends");
		return resp.getBody();
	}

	/***************** ESI ********************/

	@GetMapping("/view-esi")
	public String viewEsi(Model model, HttpSession session) {
		logger.info("Method : viewEsi starts");
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			DropDownModel[] year = restTemplate.getForObject(env.getMasterUrl() + "getYearList-attendance?organization="
					+ organization + "&orgDivision=" + orgDivision, DropDownModel[].class);
			List<DropDownModel> yearList = Arrays.asList(year);

			model.addAttribute("yearList", yearList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// drop down for month list
		try {
			DropDownModel[] month = restTemplate.getForObject(env.getMasterUrl() + "getMonthLists",
					DropDownModel[].class);
			List<DropDownModel> monthLists = Arrays.asList(month);
			model.addAttribute("monthLists", monthLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] dropDownModel = restTemplate.getForObject(env.getMasterUrl()
					+ "getBandMasterAttendance?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> bandType = Arrays.asList(dropDownModel);
			model.addAttribute("bandType", bandType);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] startDay = restTemplate.getForObject(env.getMasterUrl()
					+ "getStartDayForAttendance?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			model.addAttribute("startDayForAtten", startDay[0].getKey());
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : viewEsi ends");
		return "master/viewEsi";
	}

	/*
	 * View ESI
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("view-esi-view-details")
	public @ResponseBody List<PayrollModel> viewEsi(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String band, HttpSession session, Model model) {

		logger.info("Method : viewEsi starts");

		JsonResponse<List<PayrollModel>> resp = new JsonResponse<List<PayrollModel>>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(
					env.getMasterUrl() + "rest-viewEsi?fromDate=" + fromDate + "&toDate=" + toDate + "&band=" + band
							+ "&userId=" + userId + "&organization=" + organization + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : viewEsi ends");
		return resp.getBody();
	}

	/***************** TAX ********************/
	@GetMapping("/view-tax")
	public String viewTax(Model model, HttpSession session) {
		logger.info("Method : viewTax starts");
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			DropDownModel[] year = restTemplate.getForObject(env.getMasterUrl() + "getYearList-attendance?organization="
					+ organization + "&orgDivision=" + orgDivision, DropDownModel[].class);
			List<DropDownModel> yearList = Arrays.asList(year);

			model.addAttribute("yearList", yearList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// drop down for month list
		try {
			DropDownModel[] month = restTemplate.getForObject(env.getMasterUrl() + "getMonthLists",
					DropDownModel[].class);
			List<DropDownModel> monthLists = Arrays.asList(month);
			model.addAttribute("monthLists", monthLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] dropDownModel = restTemplate.getForObject(env.getMasterUrl()
					+ "getBandMasterAttendance?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> bandType = Arrays.asList(dropDownModel);
			model.addAttribute("bandType", bandType);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] startDay = restTemplate.getForObject(env.getMasterUrl()
					+ "getStartDayForAttendance?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			model.addAttribute("startDayForAtten", startDay[0].getKey());
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : viewTax ends");
		return "master/viewTax";
	}

	/*
	 * View TAX
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("view-tax-view-details")
	public @ResponseBody List<PayrollModel> viewTax(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String band, HttpSession session, Model model) {

		logger.info("Method : viewTax starts");

		JsonResponse<List<PayrollModel>> resp = new JsonResponse<List<PayrollModel>>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(
					env.getMasterUrl() + "rest-viewTax?fromDate=" + fromDate + "&toDate=" + toDate + "&band=" + band
							+ "&userId=" + userId + "&organization=" + organization + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : viewTax ends");
		return resp.getBody();
	}

	/**************** Pay Slip *******************/
	@GetMapping("/view-payslip")
	public String viewPayslip(Model model, HttpSession session) {
		logger.info("Method : viewAttendance starts");

		// drop down for month list
		try {
			DropDownModel[] month = restTemplate.getForObject(env.getMasterUrl() + "getMonthLists",
					DropDownModel[].class);
			List<DropDownModel> monthLists = Arrays.asList(month);
			model.addAttribute("monthLists", monthLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String userId = "";
		String userName = "";
		String userRole = "";
		String organization = "";
		String orgDivision = "";
		String isHr = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		/*
		 * String[] splitData = userRole.split(","); for (String a : splitData) { if
		 * (a.contentEquals("rol001") || a.contentEquals("rol002")) isHr = isHr + "HR";
		 * }
		 */
		/*
		 * String splitData[] = userRole.split("r");
		 * 
		 * String[] removedNull = Arrays.stream(splitData).filter(value -> value != ""
		 * && value.length() > 0) .toArray(size -> new String[size]); for (String part :
		 * removedNull) { String data = "r" + part;
		 * 
		 * if (data.contentEquals("rol001") || data.contentEquals("rol003")) { isHr =
		 * isHr + "HR"; }
		 * 
		 * }
		 */
		// drop down for employee list
		try {
			DropDownModel[] emp = restTemplate
					.getForObject(
							env.getMasterUrl() + "getEmployeeListsSlip?userId=" + userId + "&isHr=" + isHr
									+ "&organization=" + organization + "&orgDivision=" + orgDivision,
							DropDownModel[].class);
			List<DropDownModel> employeeLists = Arrays.asList(emp);
			model.addAttribute("employeeLists", employeeLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] startDay = restTemplate.getForObject(env.getMasterUrl()
					+ "getStartDayForAttendance?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			model.addAttribute("startDayForAtten", startDay[0].getKey());
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("userRole", userRole);
		model.addAttribute("organization", organization);
		model.addAttribute("orgDivision", orgDivision);
		logger.info("Method : viewAttendance ends");
		return "master/viewPayslip";
	}

	/*
	 * View EPF
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("view-payslip-list")
	public @ResponseBody List<PayslipModel> viewPayslipList(@RequestParam String empId, HttpSession session,
			Model model) {

		logger.info("Method : viewPayslipList starts");

		JsonResponse<List<PayslipModel>> resp = new JsonResponse<List<PayslipModel>>();
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "view-employe-paySlip-api?userId=" + empId
					+ "&organization=" + organization + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : viewPayslipList ends");
		return resp.getBody();
	}
	/*
	 * view PaySlip personal details
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("view-payslip-personal-details")
	public @ResponseBody JsonResponse<PayslipModel> viewpaySlipPersonal(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String empId, @RequestParam String organization,
			@RequestParam String orgDivision, HttpSession session, Model model) {

		logger.info("Method : viewpaySlipPersonal starts");

		JsonResponse<PayslipModel> jsonResponse = new JsonResponse<PayslipModel>();

		try {
			jsonResponse = restTemplate.getForObject(
					env.getMasterUrl() + "rest-viewpaySlipPersonal?fromDate=" + fromDate + "&toDate=" + toDate
							+ "&empId=" + empId + "&organization=" + organization + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		/*
		 * if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
		 * jsonResponse.setCode(jsonResponse.getMessage());
		 * jsonResponse.setMessage("Unsuccess");
		 * 
		 * } else { jsonResponse.setMessage("Success"); }
		 */

		logger.info("Method : viewpaySlipPersonal ends");
		return jsonResponse;
	}

	/***************** check payslip eligible ********************/
	// check payslip eligible
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "/view-payslip-checkPayslipEligibility" })
	public @ResponseBody JsonResponse<Object> checkPayslipEligibility(HttpSession session) {
		logger.info("Method : checkPayslipEligibility starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		String organization = "";
		String orgDivision = "";
		String userId = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "check-payslip-eligible?userId=" + userId
					+ "&organization=" + organization + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getCode().equals("success")) {
			res.setCode(res.getMessage());
			res.setMessage("Success");
		} else {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		}
		logger.info("res=====" + res);
		logger.info("Method : checkPayslipEligibility ends");
		return res;

	}

	/**************** Pay slip pdf download *******************/

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "/payslip-pdf-download" })
	public void payslipPdf(HttpServletResponse response, Model model, HttpSession session,
			@RequestParam("fromDate") String encodedParam1, @RequestParam("toDate") String encodedParam2,
			@RequestParam("empId") String encodedParam3, @RequestParam("organization") String encodedParam4,
			@RequestParam("orgDivision") String encodedParam5) {
		logger.info("Method : payslipPdf starts");

		byte[] encodeByte1 = Base64.getDecoder().decode(encodedParam1.getBytes());
		String fromDate = (new String(encodeByte1));

		byte[] encodeByte2 = Base64.getDecoder().decode(encodedParam2.getBytes());
		String toDate = (new String(encodeByte2));

		byte[] encodeByte3 = Base64.getDecoder().decode(encodedParam3.getBytes());
		String empId = (new String(encodeByte3));

		byte[] encodeByte4 = Base64.getDecoder().decode(encodedParam4.getBytes());
		String organization = (new String(encodeByte4));

		byte[] encodeByte5 = Base64.getDecoder().decode(encodedParam5.getBytes());
		String orgDivision = (new String(encodeByte5));
		JsonResponse<OfferletterModel> jsonResponse = new JsonResponse<OfferletterModel>();
		try {
			jsonResponse = restTemplate.getForObject(
					env.getMasterUrl() + "rest-viewpaySlipPersonal?fromDate=" + fromDate + "&toDate=" + toDate
							+ "&empId=" + empId + "&organization=" + organization + "&orgDivision=" + orgDivision,
					JsonResponse.class);

		} catch (

		RestClientException e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();

		PayslipModel payslip = mapper.convertValue(jsonResponse.getBody(), new TypeReference<PayslipModel>() {
		});
		logger.info("payslip==" + payslip);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("payslip", payslip);

		String logo = "";
		//String logo = "classpath:static/assets/css/extend/NEWS_7.png";
		String sign = "";
		String stamp = "";

		if (payslip.getOrgLogo() != null && payslip.getOrgLogo() != "" && payslip.getOrgLogo() != " ") {
			logo = env.getBaseURL() + "document/document/" + payslip.getOrgLogo();
		}

		if (payslip.getOrgSign() != null && payslip.getOrgSign() != "" && payslip.getOrgSign() != " ") {
			sign = env.getBaseURL() + "document/document/" + payslip.getOrgSign();
		}
		if (payslip.getOrgStamp() != null && payslip.getOrgStamp() != "" && payslip.getOrgStamp() != " ") {
			stamp = env.getBaseURL() + "document/document/" + payslip.getOrgStamp();
		}
		data.put("logo", logo);
		data.put("sign", sign);
		data.put("stamp", stamp);

		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=payslipPdfDownload.pdf");
		File file;
		byte[] fileData = null;
		try {
			file = pdfGeneratorUtil.createPdf("master/payslipPdfDownload", data);
			InputStream in = new FileInputStream(file);
			fileData = IOUtils.toByteArray(in);
			response.setContentLength(fileData.length);
			response.getOutputStream().write(fileData);
			response.getOutputStream().flush();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : payslipPdf ends");
	}
}
