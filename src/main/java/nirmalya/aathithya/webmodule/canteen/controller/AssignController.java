package nirmalya.aathithya.webmodule.canteen.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.canteen.model.WebMenuModel;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping(value = "/canteen")
public class AssignController {

	Logger logger = LoggerFactory.getLogger(AssignController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("/assign")
	public String assign(Model model, HttpSession session) {

		logger.info("Method : Manage-assign starts");

		logger.info("Method : Manage-assign ends");
		return "canteen/assign";

	}
	
	
	 //View
	@SuppressWarnings("unchecked")
	@GetMapping("assign-throughAjax")
	public @ResponseBody List<WebMenuModel> viewIncentive(HttpSession session) {

		logger.info("Method : viewincentivesDetails starts");

		JsonResponse<List<WebMenuModel>> resp = new JsonResponse<List<WebMenuModel>>();

		try {
			resp = restClient.getForObject(env.getcanteenUrl() + "restViewAssignDetails", JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		List<WebMenuModel> manageincentiveModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<WebMenuModel>>() {
				});

		resp.setBody(manageincentiveModel);
		System.out.println("resp.getBody()-----------" + resp.getBody());

		logger.info("Method : viewincentivesDetails ends");
		return resp.getBody();
	}

}
