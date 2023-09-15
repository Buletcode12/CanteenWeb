package nirmalya.aathithya.webmodule.asset.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.asset.model.AssetPolicyModel;
import nirmalya.aathithya.webmodule.asset.model.AssetViewMasterModel;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping(value = "asset/")
public class AssetPolicyController {

	Logger logger = LoggerFactory.getLogger(AssetPolicyController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	RestTemplate restTemplate;
	
	@GetMapping("/asset-policy")
	public String employee(Model model, HttpSession session) {
		logger.info("Method : reimbursement starts");
		
		String userId = "";
		String userName = "";
		String userRole = "";
		String org = "";
		String orgDiv = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		String splitData[] = userRole.split("r");
		String[] removedNull = Arrays.stream(splitData).filter(value -> value != "" && value.length() > 0)
				.toArray(size -> new String[size]);
		for (String part : removedNull) {
			String data = "r" + part;

			if (data.contentEquals("rol001") || data.contentEquals("rol003") || data.contentEquals("rol010")) {
				model.addAttribute("hrRole", data);
			}
			if (data.contentEquals("rol001") || data.contentEquals("rol010")) {
				model.addAttribute("adRole", data);
			}
		}
		try {
			DropDownModel[] cat = restTemplate.getForObject(
					env.getAssetUrl() + "getCategoryListforAsset?org=" + org + "&orgDiv=" + orgDiv+"&userId="+userId,
					DropDownModel[].class);
			List<DropDownModel> catLists = Arrays.asList(cat);
			System.out.println("hhhhhhhhhhhhhh"+catLists);
			model.addAttribute("catLists", catLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] cat = restTemplate.getForObject(
					env.getAssetUrl() + "getPriorityListforAsset?org=" + org + "&orgDiv=" + orgDiv+"&userId="+userId,
					DropDownModel[].class);
			List<DropDownModel> prioLists = Arrays.asList(cat);
			System.out.println("hhhhhhhhhhhhhh"+prioLists);
			model.addAttribute("prioLists", prioLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		/*
		 * try { DropDownModel[] loc = restTemplate.getForObject( env.getAssetUrl() +
		 * "getSubCategoryforAsset?org=" + org + "&orgDiv=" + orgDiv+"&userId="+userId,
		 * DropDownModel[].class); List<DropDownModel> locLists = Arrays.asList(loc);
		 * 
		 * model.addAttribute("locLists", locLists);
		 * 
		 * } catch (RestClientException e) { e.printStackTrace(); }
		 */
		
		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("userRole", userRole);
		 
		logger.info("Method : reimbursement ends");
		return "asset/asset-policy";
	}
	
	// getSubCategory
	@SuppressWarnings("unchecked")
	@GetMapping("asset-policy-subcategory")
	public @ResponseBody Object getSubCategory(@RequestParam String id, HttpSession session) {
		logger.info("Method :getSubCategory starts");
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

			resp = restTemplate.getForObject(env.getAssetUrl() + "rest-asset-policy-subcategory?id=" + id + "&orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		System.out.println("edit>>>-----" + resp);
		logger.info("Method :getSubCategory ends");
		return resp;
	}
	
	// addAssetPolicy

	@SuppressWarnings({ "unchecked" })

	@PostMapping(value = { "asset-policy-add" })
	public @ResponseBody JsonResponse<Object> addAssetPolicy(@RequestBody List<AssetPolicyModel> av, HttpSession session) {
		logger.info("Method : addAssetPolicy function starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		for (AssetPolicyModel m : av) {
			m.setCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);

		}
		System.out.println("qc= ==" + av);
		try {
			resp = restTemplate.postForObject(env.getAssetUrl() + "rest-asset-policy-add", av, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : addAssetPolicy function Ends");
		System.out.println("Final Save>>>------" + resp);
		return resp;
	}
	
	// viewQc

	@SuppressWarnings("unchecked")

	@GetMapping("asset-policy-view")
	public @ResponseBody Object viewAssetPolicy(HttpSession session) {
		logger.info("Method :viewAssetPolicy starts");
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
					env.getAssetUrl() + "rest-asset-policy-view?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		System.out.println("view===" + resp);
		logger.info("Method :viewAssetPolicy ends");
		return resp;
	}


	// editPolicy
	@SuppressWarnings("unchecked")
	@GetMapping("asset-policy-edit")
	public @ResponseBody Object editAssetPolicy(@RequestParam String id, HttpSession session) {
		logger.info("Method :editAssetPolicy starts");
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

			resp = restTemplate.getForObject(env.getAssetUrl() + "rest-asset-policy-edit?id=" + id + "&orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		System.out.println("edit>>>-----" + resp);
		logger.info("Method :editAssetPolicy ends");
		return resp;
	}
	
	// deleteAssetPolicy

	@SuppressWarnings("unchecked")
	@PostMapping("asset-policy-delete")
	public @ResponseBody JsonResponse<Object> deleteAssetPolicy(@RequestParam String id, Model model, HttpSession session) {
		logger.info("Method : deleteAssetPolicy function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			res = restTemplate.getForObject(
					env.getAssetUrl() + "rest-asset-policy-delete?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteAssetPolicy function Ends");

		System.out.println("RESPPPPPPP" + res);
		return res;
	}

	
	// approveAssetPolicy

	@SuppressWarnings("unchecked")
	@PostMapping("asset-policy-approve")
	public @ResponseBody JsonResponse<Object> approveAssetPolicy(@RequestParam String id, Model model, HttpSession session) {
		logger.info("Method : approveAssetPolicy function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			res = restTemplate.getForObject(
					env.getAssetUrl() + "rest-asset-policy-approve?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {
			res.setMessage("Success");
		} else {
			
		}
		logger.info("Method : approveAssetPolicy function Ends");
		return res;
	}




}
