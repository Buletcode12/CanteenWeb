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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.account.model.AccountCreditNoteWebModel;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping(value = "account")
public class AccountDebitNoteController {
	Logger logger = LoggerFactory.getLogger(AccountDebitNoteController.class);
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;
	
	@GetMapping("/debit-note")
	public String debitNote(Model model, HttpSession session) {
		logger.info("Method : debitNote start");
		
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

		logger.info("Method : debitNote end");
		return "account/debit-note";
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = {"debit-note-getorderList"})
	public @ResponseBody JsonResponse<Object> getorderList(@RequestParam String id) {
		logger.info("Method : getorderList starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getAccountUrl() + "getPurchaseorderList?id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getorderList ends");
		return res;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "debit-note-ledgerList" })
	public @ResponseBody JsonResponse<DropDownModel> getDebitLedgerList(Model model,
			@RequestBody String searchValue, BindingResult result, HttpSession session) {
		logger.info("Method : getDebitLedgerList starts");
		JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();
		try {

			res = restClient.getForObject(env.getAccountUrl() + "getDebitLedgerList?id=" + searchValue,
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
		logger.info("Method : getDebitLedgerList ends");
		return res;
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = {"debit-note-getProductList"})
	public @ResponseBody JsonResponse<Object> getProductList(@RequestParam String id) {
		logger.info("Method : getorderList starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getAccountUrl() + "getPurchaseProductList?id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getProductList ends");
		return res;
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("debit-note-getProductDetails")
	public @ResponseBody JsonResponse<List<WebPurchaseItemModel>> getProductDetails(HttpSession session, Model model, @RequestParam String oid,@RequestParam String proid) {

		logger.info("Method :getProductDetails starts");
		JsonResponse<List<WebPurchaseItemModel>> resp = new JsonResponse<List<WebPurchaseItemModel>>();
		try {
			resp = restClient.getForObject(env.getAccountUrl() + "getPurchaseProductDetails?id="+oid+"&pid="+proid,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		List<WebPurchaseItemModel> salesInvoiceNewModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<WebPurchaseItemModel>>() {
				});
		resp.setBody(salesInvoiceNewModel);
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Success");
		} else {
			resp.setMessage("Unsuccess");
		}
		logger.info("Method :getProductDetails ends");
		return resp;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@PostMapping("/debit-note-add")
	public @ResponseBody JsonResponse<Object> addDebitNote(
			@RequestBody List<AccountCreditNoteWebModel> creditNoteModel, Model model, HttpSession session) {
		logger.info("Method : addDebitNote starts" + creditNoteModel);

		JsonResponse<Object> resp = new JsonResponse<Object>();
		List<AccountCreditNoteWebModel> form = null;
		String userId = "";
		
		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (AccountCreditNoteWebModel m : creditNoteModel) {
			m.setCreatedBy(userId);
		}
		try {
			resp = restClient.postForObject(env.getAccountUrl() + "addDebitNote", creditNoteModel,
					JsonResponse.class);
			ObjectMapper mapper = new ObjectMapper();
			form = mapper.convertValue(resp.getBody(), new TypeReference<List<AccountCreditNoteWebModel>>() {
			});
		} catch (Exception e) {

			e.printStackTrace();
		}
		if (resp.getCode() != "" && resp.getCode() != null) {
			resp.setCode("Success");
		} else {
			resp.setCode("Unsuccess");
		}
		logger.info("Method : addDebitNote ends"+resp);
		return resp;
	}
	
	/**
	 * View Account Ajax
	 */
	
	
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("debit-note-view")
	public @ResponseBody List<AccountCreditNoteWebModel> debitNoteView(HttpSession session) {

		logger.info("Method : debitNoteView starts");

		JsonResponse<List<AccountCreditNoteWebModel>> resp = new JsonResponse<List<AccountCreditNoteWebModel>>();
			
		try {
			
			String userId=(String) session.getAttribute("USER_ID");
			
			
			resp = restClient.getForObject(env.getAccountUrl() + "/debitNoteView?userId="+userId, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		List<AccountCreditNoteWebModel> accountJournalVoucherModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<AccountCreditNoteWebModel>>() {
				});

		resp.setBody(accountJournalVoucherModel);

		logger.info("Method : debitNoteView ends");
		return resp.getBody();
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("debit-note-editVoucher")
	public @ResponseBody JsonResponse<AccountCreditNoteWebModel> editVoucher(Model model, @RequestParam String id,
			HttpSession session) {
		logger.info("Method : editVoucher starts" + id);
		JsonResponse<AccountCreditNoteWebModel> jsonResponse = new JsonResponse<AccountCreditNoteWebModel>();
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");
		System.out.println("date" + dateFormat);

		AccountCreditNoteWebModel product = new AccountCreditNoteWebModel();
		ObjectMapper mapper = new ObjectMapper();

		try {

			jsonResponse = restClient.getForObject(env.getAccountUrl() + "viewDebitNote?id=" + id,
					JsonResponse.class);

			product = mapper.convertValue(jsonResponse.getBody(), new TypeReference<AccountCreditNoteWebModel>() {
			});

			if (product.getDebitItemAttribute().size() > 0) {
				int c = 0;
				for (WebPurchaseItemModel a : product.getDebitItemAttribute()) {
					c = c + 1;
					a.setSlNo(c);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		jsonResponse.setBody(product);

		if (jsonResponse.getCode() != null && jsonResponse.getCode() != "") {

		} else {
			jsonResponse.setCode("Success");
		}
		logger.info("Method : editVoucher ends"+jsonResponse);
		return jsonResponse;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("debit-note-vouchernumber")
	public @ResponseBody JsonResponse<List<DropDownModel>> vouchernumber(HttpSession session) {
		logger.info("Method : vouchernumber starts");
		JsonResponse<List<DropDownModel>> resp = new JsonResponse<List<DropDownModel>>();
		try {
			resp = restClient.getForObject(env.getAccountUrl() + "/getDebitNotevoucherNumber", JsonResponse.class);
			resp.setMessage("Success");
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : vouchernumber ends"+resp);
		return resp;
	}
	
}
