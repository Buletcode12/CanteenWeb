package nirmalya.aathithya.webmodule.ticket.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping(value = "ticket")
public class DepartmentViewController {
	
	Logger logger = LoggerFactory.getLogger(DepartmentViewController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("/departmentview")
	public String departmentView(Model model, HttpSession session) {
		logger.info("Method : departmentView starts");

		logger.info("Method : departmentView ends");
		return "ticket/department-view";
	}
	
	// Proirity List
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("departmentview-priority-list")
	public @ResponseBody Object priorityList(HttpSession session) {
		logger.info("Method :priorityList starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restClient.getForObject(
					env.getTicketUrl() + "rest-deptViewPriorityList?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method :priorityList ends");
		return resp;
	}

}
