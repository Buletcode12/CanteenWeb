package nirmalya.aathithya.webmodule.employee.controller;

import javax.servlet.http.HttpSession;

import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.employee.model.HrmsDashboardModel;

@Controller
@RequestMapping(value = "employee")
public class HrmsDashboardController {
	Logger logger = LoggerFactory.getLogger(HrmsDashboardController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("hrms-dashboard")
	public String hrmsDashboard(Model model, HttpSession session) {
		logger.info("Method : hrmsDashboard starts");

		String userRole = "";
		String organization = "";
		String orgDivision = "";
		try {

			userRole = (String) session.getAttribute("USER_ROLES_STRING");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			DropDownModel[] year = restTemplate.getForObject(env.getMasterUrl()+ "getYearList-attendance?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> yearList = Arrays.asList(year);
			
			model.addAttribute("yearList", yearList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String splitData[] = userRole.split("r");
		String[] removedNull = Arrays.stream(splitData).filter(value -> value != "" && value.length() > 0)
				.toArray(size -> new String[size]);
		for (String part : removedNull) {
			String data = "r" + part;
			logger.info("data==="+data);
			if (data.contentEquals("rol003")) {
				model.addAttribute("mRole", data);
			} else if (data.contentEquals("rol002")) {
				model.addAttribute("hrRole", data);
			} else {

			}
		}

		logger.info("Method : hrmsDashboard ends");
		return "employee/hrms-dashboard";
	}

	// Total Monthly Attendance
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "hrms-dashboard-total-monthlyattendance" })
	public @ResponseBody JsonResponse<List<Object>> getTotalMonthlyAttendance(Model model,
			@RequestParam("currentYear") String currentYear, HttpSession session) {

		String userId = "";
		String orgName = "";
		String orgDivision = "";

		JsonResponse<List<Object>> res = new JsonResponse<List<Object>>();

		try {
			logger.info("Method : getTotalMonthlyAttendance starts");
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			res = restTemplate
					.getForObject(
							env.getEmployeeUrl() + "rest-getTotalMonthlyAttendance?userId=" + userId + "&orgName="
									+ orgName + "&orgDiv=" + orgDivision + "&currentYear=" + currentYear,
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
		logger.info("Method : getTotalMonthlyAttendance ends" + res);

		return res;

	}
	// totalMonthlyAttendance aggrid

	@SuppressWarnings("unchecked")

	@GetMapping("hrms-dashboard-total-monthlyattendance-aggrid")
	public @ResponseBody List<HrmsDashboardModel> getTotalMonthlyAttendanceAggrid(HttpSession session, Model model,
			@RequestParam("value1") String value1, @RequestParam("currentYear") String currentYear,
			@RequestParam("month") String month) {

		logger.info("Method : getTotalMonthlyAttendanceAggrid starts");
		String userId = "";
		String orgName = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<List<HrmsDashboardModel>> res = new JsonResponse<List<HrmsDashboardModel>>();

		try {
			res = restTemplate.getForObject(env.getEmployeeUrl() + "rest-getTotalMonthlyAttendanceAggrid?userId="
					+ userId + "&value1=" + value1 + "&orgName=" + orgName + "&orgDiv=" + orgDivision + "&currentYear="
					+ currentYear + "&month=" + month, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getTotalMonthlyAttendanceAggrid ends" + res.getBody());
		return res.getBody();

	}

	// Total Monthly Reimbursement
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "hrms-dashboard-total-monthlyreimbursement" })
	public @ResponseBody JsonResponse<List<Object>> getTotalMonthlyReimbursement(Model model,
			@RequestParam("currentYear") String currentYear, HttpSession session) {
		logger.info("Method : getTotalMonthlyReimbursement starts");
		String userId = "";
		String orgName = "";
		String orgDivision = "";

		JsonResponse<List<Object>> res = new JsonResponse<List<Object>>();

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			res = restTemplate
					.getForObject(
							env.getEmployeeUrl() + "rest-getTotalMonthlyReimbursement?userId=" + userId + "&orgName="
									+ orgName + "&orgDiv=" + orgDivision + "&currentYear=" + currentYear,
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
		logger.info("Method : getTotalMonthlyReimbursement ends" + res);

		return res;

	}

	// Total Monthly Reimbursement Aggrid
	@SuppressWarnings("unchecked")

	@GetMapping("hrms-dashboard-total-monthreimurse-aggrid")
	public @ResponseBody List<HrmsDashboardModel> getTotalMonthlyReimbursementAggrid(HttpSession session, Model model,
			@RequestParam("value1") String value1, @RequestParam("currentYear") String currentYear,
			@RequestParam("month") String month) {

		logger.info("Method : getTotalMonthlyReimbursementAggrid starts");
		String userId = "";
		String orgName = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<List<HrmsDashboardModel>> res = new JsonResponse<List<HrmsDashboardModel>>();

		try {

			res = restTemplate.getForObject(env.getEmployeeUrl() + "rest-getTotalMonthlyReimbursementAggrid?userId="
					+ userId + "&value1=" + value1 + "&orgName=" + orgName + "&orgDiv=" + orgDivision + "&currentYear="
					+ currentYear + "&month=" + month, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getTotalMonthlyReimbursementAggrid ends" + res.getBody());
		return res.getBody();

	}

	// Total leave

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "/hrms-dashboard-total-leave" })
	public @ResponseBody JsonResponse<List<Object>> getTotalLeave(Model model,
			@RequestParam("currentYear") String currentYear, HttpSession session) {
		logger.info("Method : getTotalLeave starts");
		String userId = "";
		String orgName = "";
		String orgDivision = "";

		JsonResponse<List<Object>> res = new JsonResponse<List<Object>>();

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			res = restTemplate.getForObject(env.getEmployeeUrl() + "rest-getTotalLeave?userId=" + userId + "&orgName="
					+ orgName + "&orgDiv=" + orgDivision + "&currentYear=" + currentYear, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getTotalLeave ends" + res);

		return res;

	}
	// Totalleave aggrid

	@SuppressWarnings("unchecked")

	@GetMapping("/hrms-dashboard-total-leave-aggrid")
	public @ResponseBody List<HrmsDashboardModel> getTotalLeaveAggrid(HttpSession session, Model model,
			@RequestParam("value1") String value1, @RequestParam("currentYear") String currentYear) {

		logger.info("Method : getTotalLeaveAggrid starts");
		String userId = "";
		String orgName = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<List<HrmsDashboardModel>> res = new JsonResponse<List<HrmsDashboardModel>>();

		try {
			res = restTemplate.getForObject(
					env.getEmployeeUrl() + "rest-getTotalLeaveAggrid?userId=" + userId + "&value1=" + value1
							+ "&orgName=" + orgName + "&orgDiv=" + orgDivision + "&currentYear=" + currentYear,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getTotalLeaveAggrid ends" + res.getBody());
		return res.getBody();

	}

	// Total Monthly event
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "hrms-dashboard-total-monthlyevent" })
	public @ResponseBody JsonResponse<List<Object>> getTotalMonthlyEvent(Model model,
			@RequestParam("currentYear") String currentYear, HttpSession session) {
		logger.info("Method : getTotalMonthlyEvent starts");
		String userId = "";
		String orgName = "";
		String orgDivision = "";

		JsonResponse<List<Object>> res = new JsonResponse<List<Object>>();

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			res = restTemplate.getForObject(env.getEmployeeUrl() + "rest-getTotalMonthlyEvent?userId=" + userId
					+ "&orgName=" + orgName + "&orgDiv=" + orgDivision + "&currentYear=" + currentYear,
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
		logger.info("Method : getTotalMonthlyEvent ends" + res);
		return res;

	}

	// Monthly event Aggrid
	@SuppressWarnings("unchecked")

	@GetMapping("hrms-dashboard-total-monthlyevent-aggrid")
	public @ResponseBody List<HrmsDashboardModel> getTotalMonthlyEventAggrid(HttpSession session, Model model,
			@RequestParam("value1") String value1, @RequestParam("currentYear") String currentYear,
			@RequestParam("month") String month) {

		logger.info("Method : getTotalMonthlyEventAggrid starts");
		String userId = "";
		String orgName = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<List<HrmsDashboardModel>> res = new JsonResponse<List<HrmsDashboardModel>>();

		try {
			res = restTemplate.getForObject(env.getEmployeeUrl() + "rest-getTotalMonthlyEventAggrid?userId=" + userId
					+ "&value1=" + value1 + "&orgName=" + orgName + "&orgDiv=" + orgDivision + "&currentYear="
					+ currentYear + "&month=" + month, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getTotalMonthlyEventAggrid ends" + res.getBody());
		return res.getBody();

	}

	// Count details
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "hrms-dashboard-countdetails" })
	public @ResponseBody JsonResponse<Object> getCountDetails(Model model,
			@RequestParam("currentYear") String currentYear, HttpSession session) {

		logger.info("Method : getCountDetails starts");
		String userId = "";
		String orgName = "";
		String orgDivision = "";

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			resp = restTemplate.getForObject(env.getEmployeeUrl() + "rest-getCountDetails?userId=" + userId
					+ "&orgName=" + orgName + "&orgDiv=" + orgDivision + "&currentYear=" + currentYear,
					JsonResponse.class);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("success");
		}
		logger.info("Method : getCountDetails ends" + resp);
		return resp;
	}

	// Manager Count Details
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "hrms-dashboard-managercountdetails" })
	public @ResponseBody JsonResponse<Object> getManagerCountDetails(Model model,
			@RequestParam("currentYear") String currentYear, HttpSession session) {

		logger.info("Method : getManagerCountDetails starts");
		String userId = "";
		String orgName = "";
		String orgDivision = "";

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			resp = restTemplate
					.getForObject(
							env.getEmployeeUrl() + "rest-getManagerCountDetails?userId=" + userId + "&orgName="
									+ orgName + "&orgDiv=" + orgDivision + "&currentYear=" + currentYear,
							JsonResponse.class);

		} catch (Exception e) {

			e.printStackTrace();
		}

		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("success");
		}
		logger.info("Method : getManagerCountDetails ends" + resp);
		return resp;
	}

	// Total leave Approve

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "hrms-dashboard-leave-Approve" })
	public @ResponseBody JsonResponse<List<Object>> getLeaveApprove(Model model, @RequestParam String fromdate,
			@RequestParam String todate, HttpSession session) {
		logger.info("Method : getLeaveApprove startssssssssssssssssssssssssss" + fromdate);
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		JsonResponse<List<Object>> res = new JsonResponse<List<Object>>();

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			res = restTemplate
					.getForObject(
							env.getEmployeeUrl() + "rest-getLeaveApprove?userId=" + userId + "&orgName=" + orgName
									+ "&orgDiv=" + orgDivision + "&fromdate=" + fromdate + "&todate=" + todate,
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
		logger.info("Method : getLeaveApprove ends" + res);

		return res;

	}
	// Leave Apply aggrid

	@SuppressWarnings("unchecked")

	@GetMapping("hrms-dashboard-leaveApprove-aggrid")
	public @ResponseBody List<HrmsDashboardModel> getLeaveApprovedAggrid(HttpSession session, Model model,
			@RequestParam("value1") String value1, @RequestParam String fromdate, @RequestParam String todate) {

		logger.info("Method : getLeaveApprovedAggrid starts");
		String userId = "";
		String orgName = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<List<HrmsDashboardModel>> res = new JsonResponse<List<HrmsDashboardModel>>();

		try {
			res = restTemplate.getForObject(env.getEmployeeUrl() + "rest-getLeaveApprovedAggrid?userId=" + userId
					+ "&value1=" + value1 + "&orgName=" + orgName + "&orgDiv=" + orgDivision + "&fromdate=" + fromdate
					+ "&todate=" + todate, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getLeaveApprovedAggrid ends" + res.getBody());
		return res.getBody();

	}

	// Total Reimbursement Approved
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "hrms-dashboard-reimbursementapproved" })
	public @ResponseBody JsonResponse<List<Object>> getReimbursementApproved(Model model,
			@RequestParam String fromdate, @RequestParam String todate, HttpSession session) {
		logger.info("Method : getReimbursementApproved starts");
		String userId = "";
		String orgName = "";
		String orgDivision = "";

		JsonResponse<List<Object>> res = new JsonResponse<List<Object>>();

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			res = restTemplate.getForObject(env.getEmployeeUrl() + "rest-getReimbursementApproved?userId=" + userId + "&orgName="
									+ orgName + "&orgDiv=" + orgDivision + "&fromdate=" + fromdate+"&todate="+todate,
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
		logger.info("Method : getReimbursementApproved ends" + res);

		return res;

	}

	// Total Reimbursement Approved Aggrid
	@SuppressWarnings("unchecked")

	@GetMapping("hrms-dashboard-reimurseapprove-aggrid")
	public @ResponseBody List<HrmsDashboardModel> getReimbursementApprovedAggrid(HttpSession session, Model model,
			@RequestParam("value1") String value1, @RequestParam String fromdate,@RequestParam String todate,
			@RequestParam("month") String month) {

		logger.info("Method : getReimbursementApprovedAggrid starts");
		String userId = "";
		String orgName = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<List<HrmsDashboardModel>> res = new JsonResponse<List<HrmsDashboardModel>>();

		try {

			res = restTemplate.getForObject(env.getEmployeeUrl() + "rest-getReimbursementApprovedAggrid?userId="
					+ userId + "&value1=" + value1 + "&orgName=" + orgName + "&orgDiv=" + orgDivision + "&fromdate="
					+ fromdate +"&todate="+todate+ "&month=" + month, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getReimbursementApprovedAggrid ends" + res.getBody());
		return res.getBody();

	}

	// PayRoll Process

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "hrms-dashboard-payrollprocess" })
	public @ResponseBody JsonResponse<List<Object>> getPayrollProcess(Model model, @RequestParam String fromdate,
			@RequestParam String todate, HttpSession session) {
		logger.info("Method : getPayrollProcess starts");
		String userId = "";
		String orgName = "";
		String orgDivision = "";

		JsonResponse<List<Object>> res = new JsonResponse<List<Object>>();

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			res = restTemplate.getForObject(
					env.getEmployeeUrl() + "rest-getPayrollProcess?userId=" + userId + "&orgName=" + orgName
							+ "&orgDiv=" + orgDivision + "&fromdate=" + fromdate + "&todate=" + todate,
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
		logger.info("Method : getPayrollProcess ends" + res);

		return res;

	}

	// Payroll Process Aggrid
	@SuppressWarnings("unchecked")

	@GetMapping("hrms-dashboard-payrollprocess-aggrid")
	public @ResponseBody List<HrmsDashboardModel> getPayrollProcessAggrid(HttpSession session, Model model,
			@RequestParam("value") String value, @RequestParam String fromdate, @RequestParam String todate) {

		logger.info("Method : getPayrollProcessAggrid starts");
		String userId = "";
		String orgName = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<List<HrmsDashboardModel>> res = new JsonResponse<List<HrmsDashboardModel>>();

		try {

			res = restTemplate.getForObject(env.getEmployeeUrl() + "rest-getPayrollProcessAggrid?userId=" + userId
					+ "&value=" + value + "&orgName=" + orgName + "&orgDiv=" + orgDivision + "&fromdate=" + fromdate
					+ "&todate=" + todate, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getPayrollProcessAggrid ends" + res.getBody());
		return res.getBody();

	}

	// Rating Wise Appresal

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "hrms-dashboard-ratingwise-appraisal" })
	public @ResponseBody JsonResponse<List<Object>> getRatingWise(Model model,
			@RequestParam("currentYear") String currentYear, HttpSession session) {
		logger.info("Method : getRatingWise starts");
		String userId = "";
		String orgName = "";
		String orgDivision = "";

		JsonResponse<List<Object>> res = new JsonResponse<List<Object>>();

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			res = restTemplate.getForObject(env.getEmployeeUrl() + "rest-getRatingWise?userId=" + userId + "&orgName="
					+ orgName + "&orgDiv=" + orgDivision + "&currentYear=" + currentYear, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getRatingWise ends" + res);

		return res;

	}

	// RatingWise Appresal Aggrid
	@SuppressWarnings("unchecked")

	@GetMapping("hrms-dashboard-ratingwise-aggrid")
	public @ResponseBody List<HrmsDashboardModel> getRatingWiseAggrid(HttpSession session, Model model,
			@RequestParam("value1") String value1, @RequestParam("currentYear") String currentYear) {

		logger.info("Method : getRatingWiseAggrid starts");
		String userId = "";
		String orgName = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<List<HrmsDashboardModel>> res = new JsonResponse<List<HrmsDashboardModel>>();

		try {

			res = restTemplate.getForObject(
					env.getEmployeeUrl() + "rest-getRatingWiseAggrid?userId=" + userId + "&value1=" + value1
							+ "&orgName=" + orgName + "&orgDiv=" + orgDivision + "&currentYear=" + currentYear,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getRatingWiseAggrid ends" + res.getBody());
		return res.getBody();

	}

	// HR Count Details
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "hrms-dashboard-hrcountdetails" })
	public @ResponseBody JsonResponse<Object> getHrCountDetails(Model model,
			@RequestParam("currentYear") String currentYear, HttpSession session) {

		logger.info("Method : getHrCountDetails starts");
		String userId = "";
		String orgName = "";
		String orgDivision = "";

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			resp = restTemplate.getForObject(env.getEmployeeUrl() + "rest-getHrCountDetails?userId=" + userId
					+ "&orgName=" + orgName + "&orgDiv=" + orgDivision + "&currentYear=" + currentYear,
					JsonResponse.class);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("success");
		}
		logger.info("Method : getHrCountDetails ends" + resp);
		return resp;
	}

	// Designation By Requisition
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "hrms-dashboard-designationbyrequisition" })
	public @ResponseBody JsonResponse<Object> getDesignationByRequisition(Model model,
			@RequestParam("currentYear") String currentYear, HttpSession session) {

		logger.info("Method : getDesignationByRequisition starts");
		String userId = "";
		String orgName = "";
		String orgDivision = "";

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			resp = restTemplate
					.getForObject(
							env.getEmployeeUrl() + "rest-getDesignationByRequisition?userId=" + userId + "&orgName="
									+ orgName + "&orgDiv=" + orgDivision + "&currentYear=" + currentYear,
							JsonResponse.class);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("success");
		}
		logger.info("Method : getDesignationByRequisition ends" + resp);
		return resp;
	}

	// Designation By Requisition Aggrid
	@SuppressWarnings("unchecked")

	@GetMapping("hrms-dashboard-designationRequisition-aggrid")
	public @ResponseBody List<HrmsDashboardModel> getDesignationByRequisitionAggrid(HttpSession session, Model model,
			@RequestParam("value1") String value1, @RequestParam("currentYear") String currentYear,
			@RequestParam("value") String value) {

		logger.info("Method : getDesignationByRequisitionAggrid starts");
		String userId = "";
		String orgName = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<List<HrmsDashboardModel>> res = new JsonResponse<List<HrmsDashboardModel>>();

		try {

			res = restTemplate.getForObject(env.getEmployeeUrl() + "rest-getDesignationByRequisitionAggrid?userId="
					+ userId + "&value1=" + value1 + "&orgName=" + orgName + "&orgDiv=" + orgDivision + "&currentYear="
					+ currentYear + "&value=" + value, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getDesignationByRequisitionAggrid ends" + res.getBody());
		return res.getBody();

	}

	// Requisition Status
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "hrms-dashboard-requisitionstatus" })
	public @ResponseBody JsonResponse<Object> getRequisitionStatus(Model model,
			@RequestParam("currentYear") String currentYear, HttpSession session) {

		logger.info("Method : getRequisitionStatus starts");
		String userId = "";
		String orgName = "";
		String orgDivision = "";

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			resp = restTemplate.getForObject(env.getEmployeeUrl() + "rest-getRequisitionStatus?userId=" + userId
					+ "&orgName=" + orgName + "&orgDiv=" + orgDivision + "&currentYear=" + currentYear,
					JsonResponse.class);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("success");
		}
		logger.info("Method : getRequisitionStatus ends" + resp);
		return resp;
	}

	// Requisition Aggrid
	@SuppressWarnings("unchecked")

	@GetMapping("hrms-dashboard-requisitionStatus-aggrid")
	public @ResponseBody List<HrmsDashboardModel> getRequisitionStatusAggrid(HttpSession session, Model model,
			@RequestParam("value") String value, @RequestParam("currentYear") String currentYear) {

		logger.info("Method : getRequisitionStatusAggrid starts");
		String userId = "";
		String orgName = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<List<HrmsDashboardModel>> res = new JsonResponse<List<HrmsDashboardModel>>();

		try {

			res = restTemplate.getForObject(
					env.getEmployeeUrl() + "rest-getRequisitionStatusAggrid?userId=" + userId + "&value=" + value
							+ "&orgName=" + orgName + "&orgDiv=" + orgDivision + "&currentYear=" + currentYear,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getRequisitionStatusAggrid ends" + res.getBody());
		return res.getBody();

	}

	//// Gender Wise Candidate/Employee
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "hrms-dashboard-genderewisecandidate" })
	public @ResponseBody JsonResponse<List<Object>> getGendereWiseCandidate(Model model,
			@RequestParam("currentYear") String currentYear, HttpSession session) {
		logger.info("Method : getGendereWiseCandidate starts");
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		JsonResponse<List<Object>> res = new JsonResponse<List<Object>>();

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			res = restTemplate
					.getForObject(
							env.getEmployeeUrl() + "rest-getGendereWiseCandidate?userId=" + userId + "&orgName="
									+ orgName + "&orgDiv=" + orgDivision + "&currentYear=" + currentYear,
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
		logger.info("Method : getGendereWiseCandidate ends" + res);

		return res;

	}
	// Gender Wise Candidate/Employee Aggrid

	@SuppressWarnings("unchecked")

	@GetMapping("hrms-dashboard-genderwisecandidate-aggrid")
	public @ResponseBody List<HrmsDashboardModel> getGendereWiseCandidateAggrid(HttpSession session, Model model,
			@RequestParam("value1") String value1, @RequestParam("currentYear") String currentYear) {

		logger.info("Method : getGendereWiseCandidateAggrid starts");
		String userId = "";
		String orgName = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<List<HrmsDashboardModel>> res = new JsonResponse<List<HrmsDashboardModel>>();

		try {
			res = restTemplate.getForObject(
					env.getEmployeeUrl() + "rest-getGendereWiseCandidateAggrid?userId=" + userId + "&value1=" + value1
							+ "&orgName=" + orgName + "&orgDiv=" + orgDivision + "&currentYear=" + currentYear,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getGendereWiseCandidateAggrid ends" + res.getBody());
		return res.getBody();

	}

	// Yearly Selection
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "hrms-dashboard-yearlyevent" })
	public @ResponseBody JsonResponse<List<Object>> getYearlyEvent(Model model,
			@RequestParam("currentYear") String currentYear, HttpSession session) {
		logger.info("Method : getYearlyEvent starts");
		String userId = "";
		String orgName = "";
		String orgDivision = "";

		JsonResponse<List<Object>> res = new JsonResponse<List<Object>>();

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			res = restTemplate.getForObject(env.getEmployeeUrl() + "rest-getYearlyEvent?userId=" + userId + "&orgName="
					+ orgName + "&orgDiv=" + orgDivision + "&currentYear=" + currentYear, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getYearlyEvent ends" + res);
		return res;

	}

	// Yearly Selection Aggrid
	@SuppressWarnings("unchecked")

	@GetMapping("hrms-dashboard-total-yearlyevent-aggrid")
	public @ResponseBody List<HrmsDashboardModel> getYearlyEventAggrid(HttpSession session, Model model,
			@RequestParam("value1") String value1, @RequestParam("currentYear") String currentYear,
			@RequestParam("month") String month) {

		logger.info("Method : getYearlyEventAggrid starts");
		String userId = "";
		String orgName = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<List<HrmsDashboardModel>> res = new JsonResponse<List<HrmsDashboardModel>>();

		try {
			res = restTemplate.getForObject(env.getEmployeeUrl() + "rest-getYearlyEventAggrid?userId=" + userId
					+ "&value1=" + value1 + "&orgName=" + orgName + "&orgDiv=" + orgDivision + "&currentYear="
					+ currentYear + "&month=" + month, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getYearlyEventAggrid ends" + res.getBody());
		return res.getBody();

	}

	@SuppressWarnings("unchecked")
	@GetMapping("hrms-dashboard-yearlyselection")
	public @ResponseBody JsonResponse<List<Object>> viewYearSelectionData(Model model, @RequestParam String currentYear,
			HttpSession session) {

		logger.info("Method : viewYearSelectionData starts" + currentYear);

		JsonResponse<List<Object>> jsonResponse = new JsonResponse<List<Object>>();
		logger.info(currentYear);

		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			jsonResponse = restTemplate.getForObject(env.getEmployeeUrl() + "dashboard-yearlyselection?currentYear="
					+ currentYear + "&orgname=" + organization + "&orgdiv=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("REsp" + jsonResponse);
		logger.info("Method : viewYearSelectionData ends");
		return jsonResponse;

	}
	// Total attendance

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "hrms-dashboard-attendance-report" })
	public @ResponseBody JsonResponse<List<Object>> getAttendanceReports(Model model, @RequestParam String fromdate,
			@RequestParam String todate, HttpSession session) {
		logger.info("Method : getAttendanceReports starts" + fromdate);
		logger.info("Method : getAttendanceReports starts" + todate);
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		JsonResponse<List<Object>> res = new JsonResponse<List<Object>>();
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			res = restTemplate.getForObject(
					env.getEmployeeUrl() + "rest-getAttendanceReports?userId=" + userId + "&orgName=" + orgName
							+ "&orgDiv=" + orgDivision + "&fromdate=" + fromdate + "&todate=" + todate,
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
		logger.info("Method : getAttendanceReports ends" + res);

		return res;

	}

	@SuppressWarnings("unchecked")

	@GetMapping("hrms-dashboard-total-employeeattendance-aggrid")
	public @ResponseBody List<HrmsDashboardModel> getTotalEmployeeAttendanceAggrid(HttpSession session, Model model,
			@RequestParam String value1, @RequestParam String fromdate, @RequestParam String todate,
			@RequestParam String shift) {

		logger.info("Method : getTotalMonthlyAttendanceAggrid starts" + value1 + fromdate + todate + shift);
		String userId = "";
		String orgName = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<List<HrmsDashboardModel>> res = new JsonResponse<List<HrmsDashboardModel>>();

		try {
			res = restTemplate.getForObject(env.getEmployeeUrl() + "rest-getTotalemployeeAttendanceAggrid?userId="
					+ userId + "&value1=" + value1 + "&orgName=" + orgName + "&orgDiv=" + orgDivision + "&fromdate="
					+ fromdate + "&todate=" + todate + "&shift=" + shift, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getTotalMonthlyAttendanceAggrid ends" + res.getBody());
		return res.getBody();

	}
}
