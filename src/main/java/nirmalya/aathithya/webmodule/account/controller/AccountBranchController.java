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
public class AccountBranchController {
	Logger logger = LoggerFactory.getLogger(AccountBranchController.class);
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;

	/**
	 * Add Bank
	 */
	
	@SuppressWarnings("unchecked")
	@PostMapping("/view-account-branch-add")
	public @ResponseBody JsonResponse<Object> addBranch(@RequestBody AccountBranchModel accountBranchModel,
			HttpSession session) {

		logger.info("Method : addBranch starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		System.out.println("web AccountBranchModel ======================" + accountBranchModel);
		try {
			String userId = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			System.out.println("created by id-------------------------------"+userId);

			accountBranchModel.setCreatedBy(userId);

			resp = restClient.postForObject(env.getAccountUrl() + "/addBranch", accountBranchModel, JsonResponse.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Method : addBranch ends");

		return resp;
	}
	

	/** 
	 * View Bank Branch
	 * 
	 */
	
	
	@GetMapping("/view-account-branch")
	public String viewCrmLeads(Model model, HttpSession session) {
		logger.info("Method : viewBranch start");	
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
		return "account/manage-bank-branch";
	}
	
	
//view-account-branch-stateList
	
	@SuppressWarnings("unchecked")

	@GetMapping(value = { "view-account-branch-stateList" })
	public @ResponseBody JsonResponse<Object> getstateList(@RequestParam String id) {
		logger.info("Method : getstateListAJAX starts" + id);
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
		logger.info("Method : getstateListAJAX ends");
		return res;
	}
	

	/**
	 * Delete Branch Record   
	 */
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-account-branch-delete-id")
	public @ResponseBody JsonResponse<Object> deleteBranchDetails(@RequestParam String id,
			 HttpSession session) {
		logger.info("Method : deleteBranchDetails function starts"+id);

		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getAccountUrl() + "deleteBranchDetails?id="+id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : deleteBranchDetails function Ends");
		
		System.out.println("Response"+res);
		return res;
	}
	
	
	
	/**
	 * View Bank Ajax
	 */
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-account-branch-throughAjax")
	public @ResponseBody List<AccountBranchModel> viewAccountBranch(HttpSession session) {

		logger.info("Method : viewAccountBranch starts");

		JsonResponse<List<AccountBranchModel>> resp = new JsonResponse<List<AccountBranchModel>>();

		try {
			resp = restClient.getForObject(env.getAccountUrl() + "/restViewBranchDetails", JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String date = "";
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");
		
		List<AccountBranchModel> accountBranchModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<AccountBranchModel>>() {
				});
		
		/*for (AccountBranchModel i : accountBranchModel) {
			if (i.getCreatedDate() != null && i.getCreatedDate() != "") {
				date = DateFormatter.dateFormat(i.getCreatedDate(), dateFormat);
				i.setCreatedDate(date);
				System.out.println("date for creation------------------"+date);
			}
		}*/
		
		resp.setBody(accountBranchModel);
		
		logger.info("Method : viewAccountBranch ends");
		return resp.getBody();
	}
	
	/**
	 * Edit Bank Record
	 */
	
	//view-account-bank-edit
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-account-branch-edit")
	public @ResponseBody JsonResponse<List<AccountBranchModel>> editBranchInfo(Model model, @RequestParam String id,
			HttpSession session) {

		logger.info("Method : editBranchInfo starts" + id);
		
		JsonResponse<List<AccountBranchModel>> jsonResponse = new JsonResponse<List<AccountBranchModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getAccountUrl() + "/editBranchInfo?id=" + id,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		

		ObjectMapper mapper = new ObjectMapper();
		String date = "";
		
		String drProfDoc = null;
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");
		
		List<AccountBranchModel> accountBranchModel = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<AccountBranchModel>>() {
				});
		
		for (AccountBranchModel i : accountBranchModel) {
			if (i.getCreatedDate() != null && i.getCreatedDate() != "") {
				date = DateFormatter.dateFormat(i.getCreatedDate(), dateFormat);
				i.setCreatedDate(date);
				System.out.println("start date---------------"+date);
			}	
			
		}
		
		System.out.println("###" + accountBranchModel);
		jsonResponse.setBody(accountBranchModel);
		
		System.out.println("REsp" + jsonResponse);
		logger.info("Method : editBranchInfo ends");
		return jsonResponse;
	}
	
	
	
}
