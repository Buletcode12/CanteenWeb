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
import nirmalya.aathithya.webmodule.account.model.AccountJournalVoucherModel;
import nirmalya.aathithya.webmodule.account.model.ContraVoucherModel;
import nirmalya.aathithya.webmodule.account.model.ItemShoukeenModel;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping(value = "account")
public class AccountCreditNoteController {
	Logger logger = LoggerFactory.getLogger(AccountCreditNoteController.class);
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;
	
	
	@GetMapping("/credit-note")
	public String creditNote(Model model, HttpSession session) {
		logger.info("Method : creditNote start");
		
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

		logger.info("Method : creditNote end");
		return "account/credit-note";
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "credit-note-ledgerList" })
	public @ResponseBody JsonResponse<DropDownModel> getCreditLedgerList(Model model,
			@RequestBody String searchValue, BindingResult result, HttpSession session) {
		logger.info("Method : getCreditLedgerList starts");
		JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();
		try {

			res = restClient.getForObject(env.getAccountUrl() + "getCreditLedgerList?id=" + searchValue,
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
		logger.info("Method : getCreditLedgerList ends");
		return res;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = {"credit-note-getorderList"})
	public @ResponseBody JsonResponse<Object> getorderList(@RequestParam String id) {
		logger.info("Method : getorderList starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getAccountUrl() + "getorderList?id=" + id, JsonResponse.class);
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
	@GetMapping(value = {"credit-note-getProductList"})
	public @ResponseBody JsonResponse<Object> getProductList(@RequestParam String id) {
		logger.info("Method : getorderList starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getAccountUrl() + "getProductList?id=" + id, JsonResponse.class);
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
	@GetMapping("credit-note-getProductDetails")
	public @ResponseBody JsonResponse<List<ItemShoukeenModel>> getProductDetails(HttpSession session, Model model, @RequestParam String oid,@RequestParam String proid) {

		logger.info("Method :getProductDetails starts");
		JsonResponse<List<ItemShoukeenModel>> resp = new JsonResponse<List<ItemShoukeenModel>>();
		
		try {

			resp = restClient.getForObject(env.getAccountUrl() + "getProductDetails?id="+oid+"&pid="+proid,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();

		List<ItemShoukeenModel> salesInvoiceNewModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<ItemShoukeenModel>>() {
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
	@PostMapping("/credit-note-add")
	public @ResponseBody JsonResponse<Object> addCreditNote(
			@RequestBody List<AccountCreditNoteWebModel> creditNoteModel, Model model, HttpSession session) {
		logger.info("Method : addCreditNote starts" + creditNoteModel);

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
			resp = restClient.postForObject(env.getAccountUrl() + "addCreditNote", creditNoteModel,
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
		logger.info("Method : addCreditNote ends"+resp);
		return resp;
	}
	
	/**
	 * View Account Ajax
	 */
	
	
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("credit-note-view")
	public @ResponseBody List<AccountCreditNoteWebModel> creditNoteView(HttpSession session) {

		logger.info("Method : creditNoteView starts");

		JsonResponse<List<AccountCreditNoteWebModel>> resp = new JsonResponse<List<AccountCreditNoteWebModel>>();
			
		try {
			
			String userId=(String) session.getAttribute("USER_ID");
			
			
			resp = restClient.getForObject(env.getAccountUrl() + "/creditNoteView?userId="+userId, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		List<AccountCreditNoteWebModel> accountJournalVoucherModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<AccountCreditNoteWebModel>>() {
				});


		String s = "";


		resp.setBody(accountJournalVoucherModel);

		logger.info("Method : creditNoteView ends");
		return resp.getBody();
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("credit-note-editVoucher")
	public @ResponseBody JsonResponse<AccountCreditNoteWebModel> editVoucher(Model model, @RequestParam String id,
			HttpSession session) {
		logger.info("Method : editVoucher starts" + id);
		JsonResponse<AccountCreditNoteWebModel> jsonResponse = new JsonResponse<AccountCreditNoteWebModel>();
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");
		System.out.println("date" + dateFormat);

		AccountCreditNoteWebModel product = new AccountCreditNoteWebModel();
		ObjectMapper mapper = new ObjectMapper();

		try {

			jsonResponse = restClient.getForObject(env.getAccountUrl() + "viewCreditNote?id=" + id,
					JsonResponse.class);

			product = mapper.convertValue(jsonResponse.getBody(), new TypeReference<AccountCreditNoteWebModel>() {
			});

			if (product.getItemattribute().size() > 0) {
				int c = 0;
				for (ItemShoukeenModel a : product.getItemattribute()) {
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
	@GetMapping("credit-note-vouchernumber")
	public @ResponseBody JsonResponse<List<DropDownModel>> vouchernumber(HttpSession session) {
		logger.info("Method : vouchernumber starts");
		JsonResponse<List<DropDownModel>> resp = new JsonResponse<List<DropDownModel>>();
		try {
			resp = restClient.getForObject(env.getAccountUrl() + "/getCreditNotevoucherNumber", JsonResponse.class);
			resp.setMessage("Success");
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : vouchernumber ends"+resp);
		return resp;
	}
	
	
}
