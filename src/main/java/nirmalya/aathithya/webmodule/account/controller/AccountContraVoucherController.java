package nirmalya.aathithya.webmodule.account.controller;

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
public class AccountContraVoucherController {
	Logger logger = LoggerFactory.getLogger(AccountContraVoucherController.class);
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;

	/**
	 * Add Account
	 */

	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "view-account-contra-voucher-add", method = { RequestMethod.POST })
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

			res = restClient.postForObject(env.getAccountUrl() + "addContraVoucher", journalVoucherModel,
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
	 * View Contra Voucher
	 * 
	 */
	
	
	@GetMapping("/view-account-contra-voucher")
	public String viewManageContraVoucher(Model model, HttpSession session) {
		logger.info("Method : viewManageContraVoucher start");	
		try {
			DropDownModel[] voucher = restClient.getForObject(env.getAccountUrl() + "/getVoucherTypeList",
					DropDownModel[].class);

			List<DropDownModel> voucherList = Arrays.asList(voucher);
			model.addAttribute("voucherList", voucherList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

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

		logger.info("Method : viewManageContraVoucher end");
		return "account/manage-contra-voucher";
	}
	
	
	

	/**
	 * Delete Account Record   
	 */
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-account-contra-voucher-deleteId")
	public @ResponseBody JsonResponse<Object> deleteContraDetails(@RequestParam String id,HttpSession session) {
		logger.info("Method : deleteContraDetails function starts"+id);

		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getAccountUrl() + "deleteContraDetails?id="+id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : deleteContraDetails function Ends");
		return res;
	}
	

	
	/**
	 * View Account Ajax
	 */
	
	
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-account-contra-voucher-throughAjax")
	public @ResponseBody List<AccountJournalVoucherModel> viewJournalVoucher(HttpSession session) {

		logger.info("Method : viewContraVoucher starts");

		JsonResponse<List<AccountJournalVoucherModel>> resp = new JsonResponse<List<AccountJournalVoucherModel>>();

		try {
			resp = restClient.getForObject(env.getAccountUrl() + "/restViewContraVouDetails", JsonResponse.class);
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

		logger.info("Method : viewContraVoucher ends");
		return resp.getBody();
	}
	
	/**
	 * Edit Account Record
	 */
	
	//view-account-contra-voucher-edit

	@SuppressWarnings("unchecked")
	@GetMapping("view-account-contra-voucher-edit")
	public @ResponseBody JsonResponse<List<AccountModel>> editContraVouInfo(Model model, @RequestParam String id,
			HttpSession session) {
		logger.info("Method : editAccountInfo starts" + id);
		JsonResponse<List<AccountModel>> jsonResponse = new JsonResponse<List<AccountModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getAccountUrl() + "/editContraInfo?id=" + id,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		List<AccountModel> accountModel = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<AccountModel>>() {
				});

		System.out.println("###" + accountModel);
		jsonResponse.setBody(accountModel);

		System.out.println("REsp" + jsonResponse);
		logger.info("Method : editContraVouInfo ends");
		return jsonResponse;
	}
	
	
	//view-account-contra-voucher-debit-list
	


		/*
		 * debit account Auto search      1
		 */
		
		@SuppressWarnings("unchecked")
		@PostMapping(value = { "view-account-contra-voucher-debit" })
		public @ResponseBody JsonResponse<ContraVoucherModel> getDebitAccountSearch(Model model,
				@RequestBody String searchValue, BindingResult result) {
			logger.info("Method : getDebitAccountSearch starts");
			JsonResponse<ContraVoucherModel> res = new JsonResponse<ContraVoucherModel>();

			try {
				res = restClient.getForObject(env.getAccountUrl()+ "/getDebitAccountSearch?id=" + searchValue,
						JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("Method : getDebitAccountSearch ends");
			return res;
		}
		
		//view-account-contra-voucher-credit     2
	
		@SuppressWarnings("unchecked")
		@PostMapping(value = { "view-account-contra-voucher-credit" })
		public @ResponseBody JsonResponse<ContraVoucherModel> getCreditAccountSearch(Model model,
				@RequestBody String searchValue, BindingResult result) {
			logger.info("Method : getCreditAccountSearch starts");
			JsonResponse<ContraVoucherModel> res = new JsonResponse<ContraVoucherModel>();

			try {
				res = restClient.getForObject(env.getAccountUrl()+ "/getCreditAccountSearch?id=" + searchValue,
						JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("Method : getCreditAccountSearch ends");
			return res;
		}
		
		
		//view-account-contra-voucher-fromAccB2B        3
		
		@SuppressWarnings("unchecked")
		@PostMapping(value = { "view-account-contra-voucher-fromAccB2B" })
		public @ResponseBody JsonResponse<ContraVoucherModel> getFromAccountB2BSearch(Model model,
				@RequestBody String searchValue, BindingResult result) {
			logger.info("Method : getFromAccountB2BSearch starts");
			JsonResponse<ContraVoucherModel> res = new JsonResponse<ContraVoucherModel>();

			try {
				res = restClient.getForObject(env.getAccountUrl()+ "/getFromAccountB2BSearch?id=" + searchValue,
						JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("Method : getFromAccountB2BSearch ends");
			return res;
		}
		
		
		//view-account-contra-voucher-toAccountB2B     4
		
		
		@SuppressWarnings("unchecked")
		@PostMapping(value = { "view-account-contra-voucher-toAccountB2B" })
		public @ResponseBody JsonResponse<ContraVoucherModel> getToAccountB2BSearch(Model model,
				@RequestBody String searchValue, BindingResult result) {
			logger.info("Method : getToAccountB2BSearch starts");
			JsonResponse<ContraVoucherModel> res = new JsonResponse<ContraVoucherModel>();

			try {
				res = restClient.getForObject(env.getAccountUrl()+ "/getFromAccountB2BSearch?id=" + searchValue,
						JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("Method : getToAccountB2BSearch ends");
			return res;
		}
		
		
		//view-account-contra-voucher-fromBankB2C      5
		
		@SuppressWarnings("unchecked")
		@PostMapping(value = { "view-account-contra-voucher-fromBankB2C" })
		public @ResponseBody JsonResponse<ContraVoucherModel> getFromAccountB2CSearch(Model model,
				@RequestBody String searchValue, BindingResult result) {
			logger.info("Method : getFromAccountB2CSearch starts");
			JsonResponse<ContraVoucherModel> res = new JsonResponse<ContraVoucherModel>();

			try {
				res = restClient.getForObject(env.getAccountUrl()+ "/getFromAccountB2BSearch?id=" + searchValue,
						JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("Method : getFromAccountB2CSearch ends");
			return res;
		}
		
		//view-account-contra-voucher-toBankC2B    6
		
		@SuppressWarnings("unchecked")
		@PostMapping(value = { "view-account-contra-voucher-toBankC2B" })
		public @ResponseBody JsonResponse<ContraVoucherModel> getToAccountC2BSearch(Model model,
				@RequestBody String searchValue, BindingResult result) {
			logger.info("Method : getToAccountC2BSearch starts");
			JsonResponse<ContraVoucherModel> res = new JsonResponse<ContraVoucherModel>();

			try {
				res = restClient.getForObject(env.getAccountUrl()+ "/getFromAccountB2BSearch?id=" + searchValue,
						JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("Method : getToAccountC2BSearch ends");
			return res;
		}
		
		@SuppressWarnings("unchecked")
		@GetMapping("view-account-contra-voucher-vouchernumber")
		public @ResponseBody JsonResponse<List<DropDownModel>> contraVouchernumber(HttpSession session) {
			logger.info("Method : contraVouchernumber starts");
			JsonResponse<List<DropDownModel>> resp = new JsonResponse<List<DropDownModel>>();
			try {
				resp = restClient.getForObject(env.getAccountUrl() + "/getContravoucherNumber", JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}
			logger.info("Method : contraVouchernumber ends"+resp);
			return resp;
		}
	
}
