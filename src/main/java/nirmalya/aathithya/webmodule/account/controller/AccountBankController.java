package nirmalya.aathithya.webmodule.account.controller;

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
import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.pipeline.model.CrmCustomerModel;

/**
 * @author Nirmalya Labs
 *
 */
@Controller
@RequestMapping(value = "account")
public class AccountBankController {
	Logger logger = LoggerFactory.getLogger(AccountBankController.class);
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;

	/**
	 * Add Bank
	 */
	
	
	//view-account-bank-add
	@SuppressWarnings("unchecked")
	@PostMapping("/view-account-bank-add")
	public @ResponseBody JsonResponse<Object> addBank(@RequestBody AccountBankModel accountBankModel,
			HttpSession session) {

		logger.info("Method : addBank starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		System.out.println("web AccountBankModel ======================" + accountBankModel);
		try {
			String userId = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			System.out.println("created by id-------------------------------"+userId);

			accountBankModel.setCreatedBy(userId);

			resp = restClient.postForObject(env.getAccountUrl() + "/addBank", accountBankModel, JsonResponse.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Method : addCustomer ends");

		return resp;
	}
	

	/**
	 * View Bank
	 * 
	 */

	@GetMapping("view-account-bank")
	public String viewBank(Model model, HttpSession session) {

		logger.info("Method : AccountBankController viewBank starts");
		logger.info("Method : AccountBankController viewBank end");
		return "account/manage-bank";
	}

	
	/**
	 * View Account Dashboard
	 * 
	 */

	@GetMapping("dashboard")
	public String AccDashboard(Model model, HttpSession session) {

		logger.info("Method : AccDashboard starts");
		logger.info("Method : AccDashboard end");
		return "account/manage-dashboard";
	}
	

	/**
	 * Delete Bank Record   
	 */
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-account-bank-delete-id")
	public @ResponseBody JsonResponse<Object> deleteBankDetails(@RequestParam String id,
			 HttpSession session) {
		logger.info("Method : deleteBankDetails function starts"+id);

		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getAccountUrl() + "deleteBankDetails?id="+id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : deleteBankDetails function Ends");
		
		System.out.println("Response"+res);
		return res;
	}
	
	
	
	/**
	 * Edit Bank Record
	 */
	
	
	
	/**
	 * View Bank Ajax
	 */
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-account-bank-throughAjax")
	public @ResponseBody List<AccountBankModel> viewCrmBanks(HttpSession session) {

		logger.info("Method : viewCrmBanks starts");

		JsonResponse<List<AccountBankModel>> resp = new JsonResponse<List<AccountBankModel>>();

		try {
			resp = restClient.getForObject(env.getAccountUrl() + "/restViewBankDetails", JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String date = "";
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");
		
		List<AccountBankModel> accountBankModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<AccountBankModel>>() {
				});
		
		/*for (AccountBankModel i : accountBankModel) {
			if (i.getCreatedDate() != null && i.getCreatedDate() != "") {
				date = DateFormatter.dateFormat(i.getCreatedDate(), dateFormat);
				i.setCreatedDate(date);
				System.out.println("date for creation------------------"+date);
			}
		}*/
		
		resp.setBody(accountBankModel);
		
		logger.info("Method : viewCrmBanks ends");
		return resp.getBody();
	}
	
	
	//view-account-bank-edit
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-account-bank-edit")
	public @ResponseBody JsonResponse<List<AccountBankModel>> editBankInfo(Model model, @RequestParam String id,
			HttpSession session) {

		logger.info("Method : editBankInfo starts" + id);
		
		JsonResponse<List<AccountBankModel>> jsonResponse = new JsonResponse<List<AccountBankModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getAccountUrl() + "editBankInfo?id=" + id,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		

		ObjectMapper mapper = new ObjectMapper();
		String date = "";
		
		String drProfDoc = null;
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");
		
		List<AccountBankModel> accountBankModel = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<AccountBankModel>>() {
				});
		
		for (AccountBankModel i : accountBankModel) {
			if (i.getCreatedDate() != null && i.getCreatedDate() != "") {
				date = DateFormatter.dateFormat(i.getCreatedDate(), dateFormat);
				i.setCreatedDate(date);
				System.out.println("start date---------------"+date);
			}	
			
		}
		
		System.out.println("###" + accountBankModel);
		jsonResponse.setBody(accountBankModel);
		
		System.out.println("REsp" + jsonResponse);
		logger.info("Method : editBankInfo ends");
		return jsonResponse;
	}
	
	
	
}
