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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.account.model.AccountPurchaseOrderWebModel;
import nirmalya.aathithya.webmodule.account.model.AccountPurchaseProductWebModel;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

/**
 * @author Nirmalya Labs
 *
 */
@Controller
@RequestMapping(value = "account")
public class AccountPurchaseVoucherController {
	Logger logger = LoggerFactory.getLogger(AccountPurchaseVoucherController.class);
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;

	/**
	 * Add Account
	 */

	

	@GetMapping("/purchase-voucher")
	public String purchaseVoucher(Model model, HttpSession session) {
		logger.info("Method : purchaseVoucher start");

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

		logger.info("Method : purchaseVoucher end");
		return "account/manage-purchase-voucher";
	}

	

	@SuppressWarnings("unchecked")
	@GetMapping("purchase-voucher-view")
	public @ResponseBody List<AccountPurchaseOrderWebModel> viewPurchaseOrder(HttpSession session) {

		logger.info("Method :viewPurchaseOrder starts");
		JsonResponse<List<AccountPurchaseOrderWebModel>> resp = new JsonResponse<List<AccountPurchaseOrderWebModel>>();
		String dateFormat = "";
		String orgName = "";
		String orgDivision = "";
		try {
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
		}
		try {
			resp = restClient.getForObject(env.getAccountUrl() + "rest-viewPurchaseVoucher?org="+orgName +"&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();

		List<AccountPurchaseOrderWebModel> purchaseOrderModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<AccountPurchaseOrderWebModel>>() {
				});
		resp.setBody(purchaseOrderModel);
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method :viewPurchaseOrder ends");
		
		return resp.getBody();
	}
	
	
	/*
	 * view purchase voucher
	 */


	@SuppressWarnings("unchecked")
	@GetMapping("purchase-voucher-edit")
	public @ResponseBody JsonResponse<List<AccountPurchaseOrderWebModel>> viewPurchaseVoucher(Model model, @RequestParam String id,
			HttpSession session) {
		logger.info("Method : viewPurchaseVoucher starts" + id);
		JsonResponse<List<AccountPurchaseOrderWebModel>> jsonResponse = new JsonResponse<List<AccountPurchaseOrderWebModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getAccountUrl() + "viewPurchaseVoucher?id=" + id,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		List<AccountPurchaseOrderWebModel> accountModel = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<AccountPurchaseOrderWebModel>>() {
				});
		
		if (accountModel.get(0).getProductList().size() > 0) {
			int c = 0;
			for (AccountPurchaseProductWebModel a : accountModel.get(0).getProductList()) {
				c = c + 1;
				a.setSlNo(c);
			}
		}
		

		System.out.println("###" + accountModel);
		jsonResponse.setBody(accountModel);

		System.out.println("REsp" + jsonResponse);
		logger.info("Method : viewPurchaseVoucher ends");
		return jsonResponse;
	}

}
