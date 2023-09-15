package nirmalya.aathithya.webmodule.account.controller;

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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.account.model.AccountBankModel;
import nirmalya.aathithya.webmodule.account.model.AccountBranchModel;
import nirmalya.aathithya.webmodule.account.model.AccountModel;
import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.pipeline.model.CrmCustomerModel;

/**
 * @author Nirmalya Labs
 *
 */
@Controller
@RequestMapping(value = "account")
public class ManageAccountController {
	Logger logger = LoggerFactory.getLogger(ManageAccountController.class);
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;

	/**
	 * Add Account
	 */
	
	
	@SuppressWarnings("unchecked")
	@PostMapping("/view-account-add")
	public @ResponseBody JsonResponse<Object> addAccount(@RequestBody AccountModel accountModel,
			HttpSession session) {

		logger.info("Method : addAccount starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		System.out.println("web AccountModel ======================" + accountModel);
		try {
			String userId = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			System.out.println("created by id-------------------------------"+userId);

			accountModel.setCreatedBy(userId);

			resp = restClient.postForObject(env.getAccountUrl() + "/addAccount", accountModel, JsonResponse.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Method : addAccount ends");

		return resp;
	}

	/** 
	 * View Account
	 * 
	 */
	
	
	@GetMapping("/view-account")
	public String viewManageAccount(Model model, HttpSession session) {
		logger.info("Method : viewManageAccount start");	
		try {
			DropDownModel[] bank = restClient.getForObject(env.getAccountUrl() + "/getBankList",
					DropDownModel[].class);

			List<DropDownModel> bankList = Arrays.asList(bank);
			model.addAttribute("bankList", bankList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			DropDownModel[] source = restClient.getForObject(env.getPipeline() + "/getCountry",
					DropDownModel[].class);

			List<DropDownModel> sourceList = Arrays.asList(source);
			model.addAttribute("countryList", sourceList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("Met    hod : viewBranch end");
		return "account/manage-account";
	}
	

	/**
	 * Delete Account Record   
	 */
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-account-delete-id")
	public @ResponseBody JsonResponse<Object> deleteAccoutDetails(@RequestParam String id,
			 HttpSession session) {
		logger.info("Method : deleteAccoutDetails function starts"+id);

		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getAccountUrl() + "deleteAccountDetails?id="+id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : deleteAccoutDetails function Ends");
		
		System.out.println("Response"+res);
		return res;
	}
	
	
	
	/**
	 * View Account Ajax
	 */
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-account-throughAjax")
	public @ResponseBody List<AccountModel> viewAccount(HttpSession session) {

		logger.info("Method : viewAccount starts");

		JsonResponse<List<AccountModel>> resp = new JsonResponse<List<AccountModel>>();

		try {
			resp = restClient.getForObject(env.getAccountUrl() + "/restViewAccountDetails", JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String date = "";
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");
		
		List<AccountModel> accountModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<AccountModel>>() {
				});
		
		/*for (AccountModel i : accountModel) {
			if (i.getCreatedDate() != null && i.getCreatedDate() != "") {
				date = DateFormatter.dateFormat(i.getCreatedDate(), dateFormat);
				i.setCreatedDate(date);
				System.out.println("date for creation------------------"+date);
			}
		}*/
		
		resp.setBody(accountModel);
		
		logger.info("Method : viewAccount ends");
		return resp.getBody();
	}
	
	/**
	 * Edit Account Record
	 */
	
	//view-account-edit
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-account-edit")
	public @ResponseBody JsonResponse<List<AccountModel>> editAccountInfo(Model model, @RequestParam String id,
			HttpSession session) {

		logger.info("Method : editAccountInfo starts" + id);
		
		JsonResponse<List<AccountModel>> jsonResponse = new JsonResponse<List<AccountModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getAccountUrl() + "/editAccountInfo?id=" + id,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		

		ObjectMapper mapper = new ObjectMapper();
		String date = "";
		
		String drProfDoc = null;
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");
		
		List<AccountModel> accountModel = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<AccountModel>>() {
				});
		
		for (AccountModel i : accountModel) {
			if (i.getCreatedDate() != null && i.getCreatedDate() != "") {
				date = DateFormatter.dateFormat(i.getCreatedDate(), dateFormat);
				i.setCreatedDate(date);
				System.out.println("start date---------------"+date);
			}	
			
		}
		
		System.out.println("###" + accountModel);
		jsonResponse.setBody(accountModel);
		
		System.out.println("REsp" + jsonResponse);
		logger.info("Method : editAccountInfo ends");
		return jsonResponse;
	}
	
	
	
}
