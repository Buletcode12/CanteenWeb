package nirmalya.aathithya.webmodule.qa.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;




import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;


@Controller

@RequestMapping(value = { "production/" })
public class QaRequestController {

	Logger logger = LoggerFactory.getLogger(QaRequestController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "qa-request" })

	public String qaRequest(Model model, HttpSession session) {
		logger.info("Method :qaRequest starts");

		logger.info("Method : qaRequest ends");

		return "qa/qa-requsted";
	}

	// View QA-Request-Data

	@SuppressWarnings("unchecked")
	@GetMapping("qa-request-view")
	public @ResponseBody Object viewQaRequestData(HttpSession session) {

		logger.info("Method :viewQaRequestData starts");
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

			resp = restTemplate.getForObject(
					env.getProduction() + "rest-viewQaRequstedData?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("QA-Request-Data>>>>>>>>>>>>>>>>>-----" + resp);
		logger.info("Method :viewQaRequestData ends");

		return resp;
	}

	// Child_view.

	@SuppressWarnings("unchecked")
	@GetMapping("qa-request-detls")
	public @ResponseBody Object qaRequestDtls(@RequestParam String id, HttpSession session) {

		logger.info("Method :qaRequestDtls starts");
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
			resp = restTemplate.getForObject(env.getProduction() + "rest-qaRequestDtls?id=" + id + "&orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("edit>>>-----" + resp);
		logger.info("Method :qaRequestDtls ends");

		return resp;
	}

	
	// Change Status.

	@SuppressWarnings("unchecked")
	@GetMapping("qa-request-status-change")
	public @ResponseBody Object qaRequestChangeStatus(@RequestParam String id, HttpSession session) {

		logger.info("Method :qaRequestChangeStatus starts");
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
			resp = restTemplate.getForObject(env.getProduction() + "rest-qaRequestChangeStatus?id=" + id + "&orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("edit>>>-----" + resp);
		logger.info("Method :qaRequestChangeStatus ends");

		return resp;
	}

}
