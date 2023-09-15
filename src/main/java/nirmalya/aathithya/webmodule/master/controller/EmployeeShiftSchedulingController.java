package nirmalya.aathithya.webmodule.master.controller;

import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.model.EmployeeShiftSchedulingModel;

@Controller
@RequestMapping(value = "master")
public class EmployeeShiftSchedulingController {

	Logger logger = LoggerFactory.getLogger(EmployeeShiftSchedulingController.class);
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	public int countDaysInMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		// Note that month is 0-based in calendar, bizarrely.
		calendar.set(year, month - 1, 1);
		int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

		return daysInMonth;
	}

	// Summary
	@GetMapping("/employee-shift-scheduling")
	public String employeeweekoff(Model model, HttpSession session) {

		logger.info("Method : employeeWeekoff starts");

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
			DropDownModel[] year = restTemplate.getForObject(env.getMasterUrl()+ "getYearList-attendance?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> yearList = Arrays.asList(year);
			
			model.addAttribute("yearList", yearList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] startDay = restTemplate.getForObject(env.getMasterUrl() + "getStartDayForAttendance?organization=" + organization+"&orgDivision=" + orgDivision,
					DropDownModel[].class);
			// List<DropDownModel> startDayForAtten = Arrays.asList(startDay);
			model.addAttribute("startDayForAtten", startDay[0].getKey());
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] dropDownModel = restTemplate.getForObject(env.getMasterUrl() + "get-departmentListt?organization=" + organization+"&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> departmentList = Arrays.asList(dropDownModel);
			model.addAttribute("departmentList", departmentList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
 
		String splitData[] = userRole.split("r");
		String[] removedNull = Arrays.stream(splitData).filter(value -> value != "" && value.length() > 0)
				.toArray(size -> new String[size]);
		for (String part : removedNull) {
			String data = "r" + part;

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
		}

		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("organization", organization);
		model.addAttribute("orgDivision", orgDivision);

		logger.info("Method : employeeWeekoff ends");

		return "master/employeeShiftScheduling";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("employee-shift-scheduling-shiftdetails")
	public @ResponseBody JsonResponse<List<DropDownModel>> viewshiftdetails(Model model, HttpSession session,
			@RequestParam String deptId, String subDept) {

		logger.info("Method : viewshiftdetails ");
		JsonResponse<List<DropDownModel>> resp = new JsonResponse<List<DropDownModel>>();
		String userId = "";
		String organization = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			resp = restTemplate.getForObject(
					env.getMasterUrl() + "getShiftLists?userId=" + userId + "&organization=" + organization+ "&orgDivision=" + orgDivision + "&deptId=" + deptId + "&subDeptId=" + subDept,
					JsonResponse.class);
		} catch (

		RestClientException e) {
			e.printStackTrace();
		}
		if (resp.getCode().equals("success")) {
			resp.setMessage("Success");
		} else {
			resp.setMessage("Unsuccess");
		}
		logger.info("Method : viewshiftdetails  ends");
		logger.info("respwwwwwwwwwwwwwwwweb view" + resp);

		return resp;

	}

	/*
	 * get dept list
	 */

	@SuppressWarnings("unchecked")

	@GetMapping(value = { "employee-shift-scheduling-dept-ajax" })
	public @ResponseBody JsonResponse<Object> getdeptidList(@RequestParam String id,HttpSession session) {
		logger.info("Method : getdeptidList starts");

		JsonResponse<Object> res = new JsonResponse<Object>();
		String organization = "";
		String orgDivision = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "get-nameandDeptList?id=" + id+"&organization=" + organization
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : getdeptidList ends");

		logger.info("LISTTTT" + res);
		return res;

	}

	/*
	 * Shift type autoSearch
	 */
 
	@SuppressWarnings("unchecked")
	@GetMapping("employee-shift-scheduling-autosearch-shiftDetails")
	public @ResponseBody JsonResponse<DropDownModel> getEmpShiftAutoSearchList(Model model,
			@RequestParam String searchValue, @RequestParam String dept, String subDept, HttpServletRequest request,
			HttpSession session) {
		logger.info("Method : getEmpShiftAutoSearchList starts");
		JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			res = restTemplate.getForObject(
					env.getMasterUrl() + "getEmpShiftAutoSearchList?id=" + searchValue + "&dept=" + dept + "&subDept="
							+ subDept + "&organization=" + organization + "&orgDivision=" + orgDivision,
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
		logger.info("Method : getEmpShiftAutoSearchList ends");
		logger.info("AUTOSEARCHHH" + res);
		return res;
	}

	public Boolean getLeapYear(String year) {
		Boolean leapyear = false;

		Integer yr = Integer.parseInt(year);

		if (((yr % 4 == 0) && (yr % 100 != 0)) || (yr % 400 == 0))
			leapyear = true;
		else
			leapyear = false;

		return leapyear;
	}

	@SuppressWarnings("unchecked")

	@PostMapping(value = { "employee-shift-scheduling-shift-date" })
	public @ResponseBody JsonResponse<Object> getEmployeeShiftDate(Model model, @RequestBody DropDownModel data,
			BindingResult result,HttpSession session) {
		logger.info("Method : getEmployeeShiftDate starts");

		JsonResponse<Object> res = new JsonResponse<Object>();
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String year = data.getName();
		Boolean leapyr = false;
		if (year != null && year != "") {
			leapyr = getLeapYear(year);
		}

		try {
			res = restTemplate.getForObject(
					env.getMasterUrl() + "getAttendanceDate?month=" + data.getKey() + "&lyear=" + leapyr.toString()+"&organization=" + organization+"&orgDivision=" + orgDivision,
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
		logger.info("VIEWWWDATEDATA===" + res);
		logger.info("Method : getEmployeeShiftDate ends");
		return res;

	}

	// save assisn data

	@SuppressWarnings({ "unchecked" })
	@PostMapping(value = "employee-shift-scheduling-assign-details")
	public @ResponseBody JsonResponse<Object> assignShiftDetails(
			@RequestBody List<EmployeeShiftSchedulingModel> assignshiftModel, HttpSession session) {
		logger.info("Method : assignShiftDetails function starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
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
		for (EmployeeShiftSchedulingModel m : assignshiftModel) {
			m.setAssignBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);

		}
		logger.info("data=======" + assignshiftModel);
		try {
			resp = restTemplate.postForObject(env.getMasterUrl() + "rest-assignShiftDetails", assignshiftModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : assignShiftDetails function Ends");
		return resp;
	}

	@SuppressWarnings("unchecked")

	@GetMapping("employee-shift-scheduling-view-shift-details")
	public @ResponseBody JsonResponse<List<EmployeeShiftSchedulingModel>> viewEmployeeShiftDetails(Model model,
			@RequestParam String fromDate, @RequestParam String toDate, @RequestParam String userId,
			@RequestParam String dept, @RequestParam String subDept, HttpServletRequest request, HttpSession session) {

		logger.info("Method :viewEmployeeShiftDetails starts");
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		JsonResponse<List<EmployeeShiftSchedulingModel>> resp = new JsonResponse<List<EmployeeShiftSchedulingModel>>();
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-viewEmployeeShiftDetails?fromDate=" + fromDate + "&toDate="
									+ toDate + "&userId=" + userId + "&dept=" + dept + "&subDept=" + subDept+"&organization=" + organization
									+ "&orgDivision=" + orgDivision,JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("REVALLL" + resp);
		logger.info("Method :viewEmployeeShiftDetails ends" + resp);
		return resp;
	}

	/*
	 * get shiftSubDepartment
	 */
	@SuppressWarnings("unchecked")

	@GetMapping(value = { "employee-shift-scheduling-getShiftSubDepartment" })
	public @ResponseBody JsonResponse<Object> getshiftSubDepartmentByDept(HttpSession session,
			@RequestParam String shiftDeptId) {
		logger.info("Method : getshiftSubDepartmentByDept starts");
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
			res = restTemplate.getForObject(env.getMasterUrl() + "get-shiftSubDepartment?shiftDeptId=" + shiftDeptId
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

		logger.info("Method : getshiftSubDepartmentByDept ends");
		return res;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "/employee-shift-scheduling-download-excel-shift-details" })
	public ModelAndView downloadExcelEmployeeShiftDetails(HttpServletResponse response, Model model,
			HttpSession session, @RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String userId, @RequestParam String dept, @RequestParam String subDept, String monthYear,
			String days) {
		logger.info("Method : downloadExcelEmployeeShiftDetails starts");
		
		  byte[] encodeByte1 = Base64.getDecoder().decode(fromDate.getBytes());
		  String fromDate1 = (new String(encodeByte1));
		  
		  byte[] encodeByte2 = Base64.getDecoder().decode(toDate.getBytes());
		  String toDate1 = (new String(encodeByte2));
		  
		  byte[] encodeByte3 = Base64.getDecoder().decode(userId.getBytes());
		  String userId1 = (new String(encodeByte3));
		  
			String organization = "";
			String orgDivision = "";

			try {
				organization = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}
		JsonResponse<List<EmployeeShiftSchedulingModel>> resp = new JsonResponse<List<EmployeeShiftSchedulingModel>>();
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			resp = restTemplate
					.getForObject(
							env.getMasterUrl() + "rest-viewEmployeeShiftDetails?fromDate=" + fromDate1 + "&toDate="
									+ toDate1 + "&userId=" + userId1 + "&dept=" + dept + "&subDept=" + subDept+"&organization=" + organization
									+ "&orgDivision=" + orgDivision,
							JsonResponse.class);

			ObjectMapper mapper = new ObjectMapper();
			List<EmployeeShiftSchedulingModel> shift = mapper.convertValue(resp.getBody(),
					new TypeReference<List<EmployeeShiftSchedulingModel>>() {
					});
			shift.get(0).setDays(days);
			logger.info("shift==" + shift);

			data.put("shift", shift);

			response.setContentType("application/ms-excel");
			response.setHeader("Content-disposition", "attachment; filename=" + "Employee Shift Scheduling-" + monthYear
					+ " " + new Date().getTime() + ".xls");

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : downloadExcelEmployeeShiftDetails ends");
		return new ModelAndView(new EmployeeShiftSchedulingExcelModel(), data);
	}
 
}
