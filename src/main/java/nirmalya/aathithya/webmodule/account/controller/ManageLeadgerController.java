package nirmalya.aathithya.webmodule.account.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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

import nirmalya.aathithya.webmodule.account.model.ManageLeadgerModel;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping(value = "account")
public class ManageLeadgerController {
	
	Logger logger = LoggerFactory.getLogger(ManageLeadgerController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("/manage-ledger")
	public String viewColor(Model model, HttpSession session) {

		logger.info("Method : Manage-Ledger starts");

		try {
			DropDownModel[] country= restClient.getForObject(env.getAccountUrl() + "/getCountryList",
					DropDownModel[].class);

			List<DropDownModel> countryList = Arrays.asList(country);
			System.out.println("countryList" + countryList);
			model.addAttribute("countryList", countryList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] dist = restClient.getForObject(env.getAccountUrl() + "/getShoukeenDist",
					DropDownModel[].class);

			List<DropDownModel> distList = Arrays.asList(dist);
			model.addAttribute("distList", distList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		logger.info("Method : Manage-Ledger ends");
		return "account/manage-ledger";
	
}
	
	@GetMapping(value = { "manage-ledger-stateList" })
	public @ResponseBody JsonResponse<Object> getStateListInShoukeen(@RequestParam String id) {
		logger.info("Method : getStateListaccount starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getPipeline() + "getStateLists?id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		System.out.println("state" + res);
		logger.info("Method : getStateListacount ends");
		return res;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@PostMapping("manage-ledger-add")
	public @ResponseBody JsonResponse<Object> addledger(@RequestBody ManageLeadgerModel manageleadgermodel, Model model,
			HttpSession session) {

		logger.info("Method : addledger starts" + manageleadgermodel);

		System.out.println("resp web controller-----------------------------------" + manageleadgermodel);

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {

			resp = restClient.postForObject(env.getAccountUrl() + "resaddledger", manageleadgermodel, JsonResponse.class);

		} catch (RestClientException e) {

			e.printStackTrace();
		}

		if (resp.getMessage() == "") {
			resp.setMessage("Success");
		}
		System.out.println("hello BUlet");
		logger.info("Method : addledger ends" + resp);

		return resp;
		
	}
	
	
	//view
	@SuppressWarnings("unchecked")
	@GetMapping("manage-ledger-view")
	public @ResponseBody List<ManageLeadgerModel> viewLeadger(HttpSession session) {

		logger.info("Method : viewManageLeadger starts");

		JsonResponse<List<ManageLeadgerModel>> resp = new JsonResponse<List<ManageLeadgerModel>>();

		try {
			resp = restClient.getForObject(env.getAccountUrl() + "restViewleadger", JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		List<ManageLeadgerModel> manageleadgermodel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<ManageLeadgerModel>>() {
				});

		resp.setBody(manageleadgermodel);
		System.out.println("resp.getBody()-----------" + resp.getBody());

		logger.info("Method : viewManageLeadger ends");
		return resp.getBody();
	}
	
	
	
	
	// edit
			@SuppressWarnings("unchecked")
			@GetMapping("manage-ledger-edit")
			public @ResponseBody JsonResponse<List<ManageLeadgerModel>> editmanageleadgerInfo(Model model,
					@RequestParam String id, HttpSession session) {

				logger.info("Method : editManageLeadger starts" + id);

				JsonResponse<List<ManageLeadgerModel>> jsonResponse = new JsonResponse<List<ManageLeadgerModel>>();

				try {
					jsonResponse = restClient.getForObject(env.getAccountUrl() + "editmanageledger?id=" + id,
							JsonResponse.class);

				} catch (Exception e) {
					e.printStackTrace();
				}

				ObjectMapper mapper = new ObjectMapper();
				
				 String date = ""; String drProfDoc = null; String dateFormat = (String)
				 (session).getAttribute("DATEFORMAT");
				

				List<ManageLeadgerModel> manageleadger = mapper.convertValue(jsonResponse.getBody(),
						new TypeReference<List<ManageLeadgerModel>>() {
						});

				

				System.out.println("###" + manageleadger);
				jsonResponse.setBody(manageleadger);

				if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

				} else {
					jsonResponse.setMessage("Success");
				}

				System.out.println("REsp" + jsonResponse);
				logger.info("Method :editManageLeadger ends");
				return jsonResponse;
			}


			
	
			@SuppressWarnings("unchecked")
			@GetMapping("manage-ledger-delete")
			public @ResponseBody JsonResponse<Object> deleteleadgerDetails(@RequestParam String id,
					 HttpSession session) {
				logger.info("Method : deleteManageLeadger function starts"+id);

				JsonResponse<Object> res = new JsonResponse<Object>();

				

				try {
					res = restClient.getForObject(env.getAccountUrl() + "deletemanageLedger?id=" + id  , JsonResponse.class);
				} catch (RestClientException e) {
					e.printStackTrace();
				}

				String message = res.getMessage();
				if (message != null && message != "") {

				} else {
					res.setMessage("Success");
				}
				logger.info("Method : deleteManageLeadger function Ends");
				
				System.out.println("Response"+res);
				return res;
			}

		
			//search
			@SuppressWarnings("unchecked")
			@PostMapping(value = { "manage-ledger-group-list" })
			public @ResponseBody JsonResponse<ManageLeadgerModel> getProductAutoSearchList(Model model,
					@RequestBody String searchValue, BindingResult result, HttpSession session) {
				logger.info("Method : getgroupNameSearchList starts");
				// System.out.println("QuotationNewModel"+searchValue);
				JsonResponse<ManageLeadgerModel> res = new JsonResponse<ManageLeadgerModel>();
				String userId = (String) session.getAttribute("USER_ID");
				String userType = (String) session.getAttribute("USER_ROLETYPE");
				System.out.println("==userid====" + userId);
				System.out.println("==user_roles====" + userType);
				try {

					res = restClient.getForObject(env.getAccountUrl() + "getundergrouplist?id=" + searchValue,
							JsonResponse.class);

				} catch (Exception e) {
					e.printStackTrace();
				}

				if (res.getCode() != null) {
					res.setCode("success");
				} else {
					res.setCode("Unsuccess");
				}
				System.out.println("RESPONSE@@" + res);
				logger.info("Method : getgroupNameSearchList ends");
				return res;
			}
          }
	

