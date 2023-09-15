package nirmalya.aathithya.webmodule.account.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.account.model.AccountBankModel;
import nirmalya.aathithya.webmodule.account.model.AccountBranchModel;
import nirmalya.aathithya.webmodule.account.model.AccountJournalVoucherModel;
import nirmalya.aathithya.webmodule.account.model.AccountModel;
import nirmalya.aathithya.webmodule.account.model.ContraVoucherModel;
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
public class AccountJournalVoucherController {
	Logger logger = LoggerFactory.getLogger(AccountJournalVoucherController.class);
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;

	/**
	 * Add Account
	 */

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @PostMapping("/view-account-add") public @ResponseBody JsonResponse<Object>
	 * addAccount(@RequestBody AccountModel accountModel, HttpSession session) {
	 * 
	 * logger.info("Method : addAccount starts");
	 * 
	 * JsonResponse<Object> resp = new JsonResponse<Object>();
	 * System.out.println("web AccountModel ======================" + accountModel);
	 * try { String userId = ""; try { userId = (String)
	 * session.getAttribute("USER_ID"); } catch (Exception e) { e.printStackTrace();
	 * }
	 * 
	 * System.out.println("created by id-------------------------------"+userId);
	 * 
	 * accountModel.setCreatedBy(userId);
	 * 
	 * resp = restClient.postForObject(env.getAccountUrl() + "/addAccount",
	 * accountModel, JsonResponse.class);
	 * 
	 * } catch (RestClientException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } logger.info("Method : addAccount ends");
	 * 
	 * return resp; }
	 */
	/**
	 * View Account
	 * 
	 */

	@GetMapping("/view-account-journal-voucher")
	public String viewJournalVoucher(Model model, HttpSession session) {
		logger.info("Method : viewJournalVoucher start");

		try {
			DropDownModel[] costCenter = restClient.getForObject(env.getAccountUrl() + "/getCostCenterList",
					DropDownModel[].class);

			List<DropDownModel> ccList = Arrays.asList(costCenter);
			model.addAttribute("ccList", ccList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] costCenter = restClient.getForObject(env.getAccountUrl() + "/getFiscalYearList",
					DropDownModel[].class);

			List<DropDownModel> fiscalList = Arrays.asList(costCenter);
			model.addAttribute("fiscalList", fiscalList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("Method : viewJournalVoucher end");
		return "account/manage-journal-voucher";
	}

	// view-account-journal-voucher-getAccountDebitGroup

	/*
	 * debit account Auto search 1
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-account-journal-voucher-getAccountDebitGroup" })
	public @ResponseBody JsonResponse<ContraVoucherModel> getDebitAccountJournalSearch(Model model,
			@RequestBody String searchValue, BindingResult result) {
		logger.info("Method : getDebitAccountJournalSearch starts");
		JsonResponse<ContraVoucherModel> res = new JsonResponse<ContraVoucherModel>();

		try {
			res = restClient.getForObject(env.getAccountUrl() + "/getDebitJournalAccountSearch?id=" + searchValue,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getDebitAccountJournalSearch ends");
		return res;
	}

	// view-account-journal-voucher-getAccountCreditGroup

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-account-journal-voucher-getAccountCreditGroup" })
	public @ResponseBody JsonResponse<ContraVoucherModel> getCreditAccountJournalSearch(Model model,
			@RequestBody String searchValue, BindingResult result) {
		logger.info("Method : getCreditAccountJournalSearch starts");
		JsonResponse<ContraVoucherModel> res = new JsonResponse<ContraVoucherModel>();

		try {
			res = restClient.getForObject(env.getAccountUrl() + "/getCreditJournalAccountSearch?id=" + searchValue,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getCreditAccountJournalSearch ends");
		return res;
	}

	// view-account-journal-voucher-add-journal
	// add-journal-voucher

	/*
	 * post Mapping for add SPA table staff assign
	 * 
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "view-account-journal-voucher-add-journal", method = { RequestMethod.POST })
	public @ResponseBody JsonResponse<DropDownModel> addJournalVoucher(
			@RequestBody List<AccountJournalVoucherModel> journalVoucherModel, Model model, HttpSession session) {
		logger.info("Method : addJournalVoucher function starts");
		System.out.println(journalVoucherModel + "journalVoucherModel");
		JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();
		String userId = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		journalVoucherModel.get(0).setCreatedBy(userId);
		try {

			res = restClient.postForObject(env.getAccountUrl() + "addJournalVoucher", journalVoucherModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();

		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : addJournalVoucher function Ends");
		return res;
	}

	/**
	 * Delete Journal Voucher Record
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("view-account-journal-voucher-delete-id")
	public @ResponseBody JsonResponse<Object> deleteJournalDetails(@RequestParam String id, HttpSession session) {
		logger.info("Method : deleteAccoutDetails function starts" + id);

		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getAccountUrl() + "deleteJournalDetails?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : deleteJournalDetails function Ends");

		System.out.println("Response" + res);
		return res;
	}

	// view-account-journal-voucher-approve-id

	/**
	 * Approve Journal Voucher Record
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("view-account-journal-voucher-approve-id")
	public @ResponseBody JsonResponse<Object> approveJournalDetails(@RequestParam String id, HttpSession session) {
		logger.info("Method : approveJournalDetails function starts" + id);

		String userId = (String) session.getAttribute("USER_ID");
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getAccountUrl() + "approveJournalDetails?id=" + id + "&userId=" + userId,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : approveJournalDetails function Ends");

		System.out.println("Response" + res);
		return res;
	}
	// view-account-journal-voucher-reject-id

	/**
	 * Reject Journal Voucher Record
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("view-account-journal-voucher-reject-id")
	public @ResponseBody JsonResponse<Object> rejectJournalDetails(@RequestParam String id, HttpSession session) {
		logger.info("Method : rejectJournalDetails function starts" + id);

		String userId = (String) session.getAttribute("USER_ID");
		System.out.println("==userid====" + userId);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getAccountUrl() + "rejectJournalDetails?id=" + id + "&userId=" + userId,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : rejectJournalDetails function Ends");

		System.out.println("Response" + res);
		return res;
	}
	// view-account-journal-voucher-return-id

	/**
	 * Return Journal Voucher Record
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("view-account-journal-voucher-return-id")
	public @ResponseBody JsonResponse<Object> returnJournalDetails(@RequestParam String id, HttpSession session) {
		logger.info("Method : returnJournalDetails function starts" + id);

		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getAccountUrl() + "returnJournalDetails?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : returnJournalDetails function Ends");

		System.out.println("Response" + res);
		return res;
	}

	/**
	 * View Journal Voucher
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("view-account-journal-voucher-throughAjax")
	public @ResponseBody List<AccountJournalVoucherModel> viewJournalVoucher(HttpSession session) {

		logger.info("Method : viewJournalVoucher starts");

		JsonResponse<List<AccountJournalVoucherModel>> resp = new JsonResponse<List<AccountJournalVoucherModel>>();

		try {
			resp = restClient.getForObject(env.getAccountUrl() + "/restViewJournalVoucher", JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		String date = "";
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");

		List<AccountJournalVoucherModel> accountJournalVoucherModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<AccountJournalVoucherModel>>() {
				});


		String s = "";

		for (AccountJournalVoucherModel m : accountJournalVoucherModel) {
			// byte[] pId = Base64.getEncoder().encode(m.getJournalVoucher().getBytes());
			s = "";
			s = s + "<a data-toggle='modal' title='View' data-target='#myModal' href='javascript:void(0)' onclick='viewInModel(\""
					+ new String(m.getJournalVoucher()) + "\")'><i class='fa fa-search search'></i></a>";

			if ((m.getCurrentStageNo() == m.getApproverStageNo()) && (m.getApproveStatus() != 1)) {
				if (m.getApproveStatus() != 3) {
					s = s + " &nbsp;&nbsp <a title='forward' href='javascript:void(0)' onclick='forwardJournalVoucher(\""
							+ new String(m.getJournalVoucher())
							+ "\")'><i class='fa fa-forward'></i></a> &nbsp;&nbsp; ";
				} else {
					s = s + " &nbsp;&nbsp <a title='resubmit' href='javascript:void(0)' onclick='rejectJournalVoucher(\""
							+ new String(m.getJournalVoucher()) + "\",3)'><i class='fa fa-send'></i></a> &nbsp;&nbsp; ";
				}
				s = s + " &nbsp;&nbsp <a title='reject' href='javascript:void(0)' onclick='rejectJournalVoucher(\""
						+ new String(m.getJournalVoucher()) + "\",1)'><i class='fa fa-close'></i></a> &nbsp;&nbsp; ";
				s = s + " &nbsp;&nbsp <a title='return' href='javascript:void(0)' onclick='rejectJournalVoucher(\""
						+ new String(m.getJournalVoucher()) + "\",2)'><i class='fa fa-undo'></i></a> &nbsp;&nbsp; ";
			}

			m.setAction(s);
			s = "";

			if (m.getApproveStatus() == 3)
				m.setApproveStatusName("Returned");
			else if (m.getApproveStatus() == 1)
				m.setApproveStatusName("Approved");
			else if (m.getApproveStatus() == 2)
				m.setApproveStatusName("Rejected");
			else
				m.setApproveStatusName("Open");
		}

		resp.setBody(accountJournalVoucherModel);

		logger.info("Method : viewJournalVoucher ends");
		return resp.getBody();
	}

	/**
	 * Edit Account Record
	 */

	// view-account-edit

	@SuppressWarnings("unchecked")

	@GetMapping("view-account-journal-edit")
	public @ResponseBody JsonResponse<List<AccountModel>> editAccountInfo(Model model, @RequestParam String id,
			HttpSession session) {

		logger.info("Method : editAccountInfo starts" + id);

		JsonResponse<List<AccountModel>> jsonResponse = new JsonResponse<List<AccountModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getAccountUrl() + "/editJournalInfo?id=" + id,
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

		System.out.println("###" + accountModel);
		jsonResponse.setBody(accountModel);

		System.out.println("REsp" + jsonResponse);
		logger.info("Method : editAccountInfo ends");
		return jsonResponse;
	}

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @GetMapping("view-account-journal-edit") public @ResponseBody
	 * JsonResponse<AccountModel> editAccountInfo(Model model, @RequestParam String
	 * id, HttpSession session) { logger.info("Method : editAccountInfo starts" +
	 * id); List<AccountModel> productList = new ArrayList<AccountModel>();
	 * 
	 * JsonResponse<AccountModel> jsonResponse = new JsonResponse<AccountModel>();
	 * String dateFormat = (String) (session).getAttribute("DATEFORMAT");
	 * System.out.println("date" + dateFormat);
	 * 
	 * AccountModel product = new AccountModel(); ObjectMapper mapper = new
	 * ObjectMapper();
	 * 
	 * try {
	 * 
	 * jsonResponse = restClient.getForObject(env.getAccountUrl() +
	 * "editAccountInfo?id=" + id, JsonResponse.class);
	 * 
	 * product = mapper.convertValue(jsonResponse.getBody(), new
	 * TypeReference<AccountModel>() { });
	 * System.out.println("enter mapper controller" + product);
	 * 
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * jsonResponse.setBody(product);
	 * 
	 * if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
	 * 
	 * } else { jsonResponse.setMessage("Success"); }
	 * 
	 * System.out.println("REsp" + jsonResponse);
	 * 
	 * logger.info("Method : editAccountInfo ends"); return jsonResponse; }
	 */
	
	
	
	
	
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-account-journal-voucher-vouchernumber")
	public @ResponseBody JsonResponse<List<DropDownModel>> vouchernumber(HttpSession session) {
		logger.info("Method : vouchernumber starts");
		JsonResponse<List<DropDownModel>> resp = new JsonResponse<List<DropDownModel>>();
		try {
			resp = restClient.getForObject(env.getAccountUrl() + "/getJournalvouchernumber", JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : vouchernumber ends"+resp);
		return resp;
	}
}
