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
public class AccountPaymentVoucherController {
	Logger logger = LoggerFactory.getLogger(AccountPaymentVoucherController.class);
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;

	/**
	 * Add Account
	 */

	

	@GetMapping("/payment-voucher")
	public String paymentVoucher(Model model, HttpSession session) {
		logger.info("Method : paymentVoucher start");

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

		logger.info("Method : paymentVoucher end");
		return "account/manage-payment-voucher";
	}

	
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "view-account-payment-voucher-add", method = { RequestMethod.POST })
	public @ResponseBody JsonResponse<DropDownModel> addPaymentVoucher(
			@RequestBody List<AccountJournalVoucherModel> journalVoucherModel, Model model, HttpSession session) {
		logger.info("Method : addPaymentVoucher function starts");
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

			res = restClient.postForObject(env.getAccountUrl() + "addPaymentVoucher", journalVoucherModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();

		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : addPaymentVoucher function Ends");
		return res;
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-account-payment-voucher")
	public @ResponseBody List<AccountJournalVoucherModel> viewPaymentVoucher(HttpSession session) {

		logger.info("Method : viewPaymentVoucher starts");

		JsonResponse<List<AccountJournalVoucherModel>> resp = new JsonResponse<List<AccountJournalVoucherModel>>();

		try {
			resp = restClient.getForObject(env.getAccountUrl() + "/restViewPaymentDetails", JsonResponse.class);
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

		logger.info("Method : viewPaymentVoucher ends");
		return resp.getBody();
	}
	
	@SuppressWarnings("unchecked")

	@GetMapping("view-account-payment-voucher-edit")
	public @ResponseBody JsonResponse<List<AccountModel>> editPaymentInfo(Model model, @RequestParam String id,
			HttpSession session) {

		logger.info("Method : editPaymentInfo starts" + id);

		JsonResponse<List<AccountModel>> jsonResponse = new JsonResponse<List<AccountModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getAccountUrl() + "/editPaymentInfo?id=" + id,
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
		logger.info("Method : editPaymentInfo ends");
		return jsonResponse;
	}

	/**
	 * Delete Journal Voucher Record
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("payment-voucher-delete-id")
	public @ResponseBody JsonResponse<Object> deleteJournalDetails(@RequestParam String id, HttpSession session) {
		logger.info("Method : deleteAccoutDetails function starts" + id);

		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getAccountUrl() + "deletePaymentDetails?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : deleteJournalDetails function Ends");

		System.out.println("Response" + res);
		return res;
	}

	// view-account-journal-voucher-approve-id

	@SuppressWarnings("unchecked")
	@GetMapping("payment-voucher-vouchernumber")
	public @ResponseBody JsonResponse<List<DropDownModel>> vouchernumber(HttpSession session) {
		logger.info("Method : vouchernumber starts");
		JsonResponse<List<DropDownModel>> resp = new JsonResponse<List<DropDownModel>>();
		try {
			resp = restClient.getForObject(env.getAccountUrl() + "/getPaymentvoucherNumber", JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : vouchernumber ends"+resp);
		return resp;
	}
	
	/*
	 * debit account Auto search 1
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "payment-voucher-getAccountDebitGroup" })
	public @ResponseBody JsonResponse<ContraVoucherModel> getDebitAccountJournalSearch(Model model,
			@RequestBody String searchValue, BindingResult result) {
		logger.info("Method : getDebitAccountJournalSearch starts");
		JsonResponse<ContraVoucherModel> res = new JsonResponse<ContraVoucherModel>();

		try {
			res = restClient.getForObject(env.getAccountUrl() + "/getDebitPaymentAccountSearch?id=" + searchValue,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getDebitAccountJournalSearch ends");
		return res;
	}

	// view-account-journal-voucher-getAccountCreditGroup

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "payment-voucher-getAccountCreditGroup" })
	public @ResponseBody JsonResponse<ContraVoucherModel> getCreditAccountJournalSearch(Model model,
			@RequestBody String searchValue, BindingResult result) {
		logger.info("Method : getCreditAccountJournalSearch starts");
		JsonResponse<ContraVoucherModel> res = new JsonResponse<ContraVoucherModel>();

		try {
			res = restClient.getForObject(env.getAccountUrl() + "/getCreditPaymentAccountSearch?id=" + searchValue,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getCreditAccountJournalSearch ends");
		return res;
	}
}
