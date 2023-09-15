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

import nirmalya.aathithya.webmodule.account.model.AccountLedgerReportWebModel;
import nirmalya.aathithya.webmodule.account.model.ManageLeadgerModel;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping(value = "account")
public class AccountReportsWebController {
	Logger logger = LoggerFactory.getLogger(AccountReportsWebController.class);
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;

	// view balance sheet page

	@GetMapping("balance-sheet")
	public String balanceSheet(Model model, HttpSession session) {
		logger.info("Method : balanceSheet starts");
		
		try {
			DropDownModel[] costCenter = restClient.getForObject(env.getAccountUrl() + "/getFiscalYearList",
					DropDownModel[].class);

			List<DropDownModel> fiscalList = Arrays.asList(costCenter);
			model.addAttribute("fiscalList", fiscalList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.info("Method : balanceSheet end");
		return "account/manageBalanceSheet";
	}

	// view profit and loss page
	@GetMapping("profile-and-loss")
	public String profileAndLoss(Model model, HttpSession session) {
		logger.info("Method : profileAndLoss starts");
		
		try {
			DropDownModel[] costCenter = restClient.getForObject(env.getAccountUrl() + "/getFiscalYearList",
					DropDownModel[].class);

			List<DropDownModel> fiscalList = Arrays.asList(costCenter);
			model.addAttribute("fiscalList", fiscalList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Method : profileAndLoss end");
		return "account/profit-loss";
	}

	// view trail-balance page
	@GetMapping("trial-balance")
	public String trailBalance(Model model, HttpSession session) {
		logger.info("Method : trailBalance starts");
		
		try {
			DropDownModel[] costCenter = restClient.getForObject(env.getAccountUrl() + "/getFiscalYearList",
					DropDownModel[].class);

			List<DropDownModel> fiscalList = Arrays.asList(costCenter);
			model.addAttribute("fiscalList", fiscalList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.info("Method : trailBalance end");
		return "account/trial-balance";
	}

	// view day-book page
	@GetMapping("day-book")
	public String dayBook(Model model, HttpSession session) {
		logger.info("Method : dayBook starts");
		
		
		try {
			DropDownModel[] costCenter = restClient.getForObject(env.getAccountUrl() + "/getFiscalYearList",
					DropDownModel[].class);

			List<DropDownModel> fiscalList = Arrays.asList(costCenter);
			model.addAttribute("fiscalList", fiscalList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.info("Method : dayBook end");
		return "account/day-book";
	}

	// view cash-flow page
	@GetMapping("cash-flow")
	public String cashFlow(Model model, HttpSession session) {
		logger.info("Method : cashFlow starts");
		
		try {
			DropDownModel[] costCenter = restClient.getForObject(env.getAccountUrl() + "/getFiscalYearList",
					DropDownModel[].class);

			List<DropDownModel> fiscalList = Arrays.asList(costCenter);
			model.addAttribute("fiscalList", fiscalList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.info("Method : cashFlow end");
		return "account/cash-flow";
	}

	// view fund-flow page
	@GetMapping("fund-flow")
	public String fundFlow(Model model, HttpSession session) {
		logger.info("Method : fundFlow starts");
		
		
		try {
			DropDownModel[] costCenter = restClient.getForObject(env.getAccountUrl() + "/getFiscalYearList",
					DropDownModel[].class);

			List<DropDownModel> fiscalList = Arrays.asList(costCenter);
			model.addAttribute("fiscalList", fiscalList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.info("Method : fundFlow end");
		return "account/fund-flow";
	}

	// view account-statement page
	@GetMapping("account-statement")
	public String accountStatement(Model model, HttpSession session) {
		logger.info("Method : accountStatement starts");
		
		
		try {
			DropDownModel[] costCenter = restClient.getForObject(env.getAccountUrl() + "/getFiscalYearList",
					DropDownModel[].class);

			List<DropDownModel> fiscalList = Arrays.asList(costCenter);
			model.addAttribute("fiscalList", fiscalList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.info("Method : accountStatement end");
		return "account/account-statement";
	}

	// view manage-ledger page
	@GetMapping("ledger-voucher")
	public String ledgerVoucher(Model model, HttpSession session) {
		logger.info("Method : ledgerVoucher starts");
		
		
		try {
			DropDownModel[] costCenter = restClient.getForObject(env.getAccountUrl() + "/getFiscalYearList",
					DropDownModel[].class);

			List<DropDownModel> fiscalList = Arrays.asList(costCenter);
			model.addAttribute("fiscalList", fiscalList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		logger.info("Method : ledgerVoucher end");
		return "account/ledger-voucher";
	}
	
	
	//ledger voucher report
	
	@SuppressWarnings("unchecked")
	@GetMapping("ledger-voucher-details")
	public @ResponseBody JsonResponse<List<AccountLedgerReportWebModel>> viewLedgerVoucherReport(Model model,
			@RequestParam String id, HttpSession session) {
		logger.info("Method : viewLedgerVoucherReport starts" + id);
		JsonResponse<List<AccountLedgerReportWebModel>> jsonResponse = new JsonResponse<List<AccountLedgerReportWebModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getAccountUrl() + "viewLedgerVoucherReport?id=" + id,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		List<AccountLedgerReportWebModel> manageleadger = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<AccountLedgerReportWebModel>>() {
				});
		System.out.println("###" + manageleadger);
		jsonResponse.setBody(manageleadger);
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
		} else {
			jsonResponse.setMessage("Success");
		}
		System.out.println("REsp" + jsonResponse);
		logger.info("Method :viewLedgerVoucherReport ends");
		return jsonResponse;
	}
	
	//day book report
	
	@SuppressWarnings("unchecked")
	@GetMapping("day-book-report")
	public @ResponseBody JsonResponse<List<AccountLedgerReportWebModel>> dayBookReport(Model model,
			 HttpSession session,@RequestParam String fromDate,@RequestParam String toDate) {
		logger.info("Method : dayBookReport starts");
		JsonResponse<List<AccountLedgerReportWebModel>> jsonResponse = new JsonResponse<List<AccountLedgerReportWebModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getAccountUrl() + "viewDayBookReport?fromDate="+fromDate+"&toDate="+toDate,JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		List<AccountLedgerReportWebModel> manageleadger = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<AccountLedgerReportWebModel>>() {
		});
		System.out.println("###" + manageleadger);
		jsonResponse.setBody(manageleadger);
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
		} else {
			jsonResponse.setMessage("Success");
		}
		System.out.println("REsp" + jsonResponse);
		logger.info("Method :dayBookReport ends");
		return jsonResponse;
	}
	
	//cash-flow-details
	@SuppressWarnings("unchecked")
	@GetMapping("cash-flow-details")
	public @ResponseBody JsonResponse<List<AccountLedgerReportWebModel>> cashFlowReport(Model model,
			 HttpSession session) {
		logger.info("Method : cashFlowReport starts");
		JsonResponse<List<AccountLedgerReportWebModel>> jsonResponse = new JsonResponse<List<AccountLedgerReportWebModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getAccountUrl() + "viewCashFlowReport",JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		List<AccountLedgerReportWebModel> manageleadger = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<AccountLedgerReportWebModel>>() {
		});
		System.out.println("###" + manageleadger);
		jsonResponse.setBody(manageleadger);
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
		} else {
			jsonResponse.setMessage("Success");
		}
		System.out.println("REsp" + jsonResponse);
		logger.info("Method :cashFlowReport ends");
		return jsonResponse;
	}
	
	
	//monthly summary details
	//view monthly account statement 
	
	@SuppressWarnings("unchecked")
	@GetMapping("account-statement-mothlySummary")
	public @ResponseBody JsonResponse<List<AccountLedgerReportWebModel>> viewLedgerMonthlySummary(Model model,
			@RequestParam String id, HttpSession session) {
		logger.info("Method : viewLedgerMonthlySummary starts" + id);
		JsonResponse<List<AccountLedgerReportWebModel>> jsonResponse = new JsonResponse<List<AccountLedgerReportWebModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getAccountUrl() + "viewLedgerMonthlySummary?id=" + id,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		List<AccountLedgerReportWebModel> manageleadger = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<AccountLedgerReportWebModel>>() {
				});
		System.out.println("###" + manageleadger);
		jsonResponse.setBody(manageleadger);
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
		} else {
			jsonResponse.setMessage("Success");
		}
		System.out.println("REsp" + jsonResponse);
		logger.info("Method :viewLedgerMonthlySummary ends");
		return jsonResponse;
	}

	//view monthly account statement 
	
	@SuppressWarnings("unchecked")
	@GetMapping("account-statement-mothlyDetails")
	public @ResponseBody JsonResponse<List<AccountLedgerReportWebModel>> viewMothlyDetails(Model model,
			@RequestParam String month,@RequestParam String ledgerId,HttpSession session) {
		logger.info("Method : viewMothlyDetails starts" + ledgerId);
		JsonResponse<List<AccountLedgerReportWebModel>> jsonResponse = new JsonResponse<List<AccountLedgerReportWebModel>>();
		try {
			
			System.out.println("month====>>>"+month);
			System.out.println("ledgerId====>>>"+ledgerId);
			
			
			jsonResponse = restClient.getForObject(env.getAccountUrl() + "viewMothlyDetails?month=" + month+"&ledgerId="+ledgerId,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		List<AccountLedgerReportWebModel> manageleadger = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<AccountLedgerReportWebModel>>() {
				});
		System.out.println("###" + manageleadger);
		jsonResponse.setBody(manageleadger);
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
		} else {
			jsonResponse.setMessage("Success");
		}
		System.out.println("REsp" + jsonResponse);
		logger.info("Method :viewMothlyDetails ends");
		return jsonResponse;
	}
	
	//trial balance report
	
	@SuppressWarnings("unchecked")
	@GetMapping("trial-balance-report")
	public @ResponseBody JsonResponse<List<AccountLedgerReportWebModel>> trialBalance(Model model,
			 HttpSession session,@RequestParam String fromDate,@RequestParam String toDate) {
		logger.info("Method : trialBalance starts");
		JsonResponse<List<AccountLedgerReportWebModel>> jsonResponse = new JsonResponse<List<AccountLedgerReportWebModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getAccountUrl() + "trialBalanceReport?fromDate="+fromDate+"&toDate="+toDate,JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		List<AccountLedgerReportWebModel> manageleadger = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<AccountLedgerReportWebModel>>() {
		});
		System.out.println("###" + manageleadger);
		jsonResponse.setBody(manageleadger);
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
		} else {
			jsonResponse.setMessage("Success");
		}
		System.out.println("REsp" + jsonResponse);
		logger.info("Method :trialBalance ends");
		return jsonResponse;
	}
	
	//profile-and-loss-data
	
	@SuppressWarnings("unchecked")
	@GetMapping("profile-and-loss-data")
	public @ResponseBody JsonResponse<List<AccountLedgerReportWebModel>> profitLossData(Model model,
			 HttpSession session,@RequestParam String fromDate,@RequestParam String toDate) {
		logger.info("Method : profitLossData starts");
		JsonResponse<List<AccountLedgerReportWebModel>> jsonResponse = new JsonResponse<List<AccountLedgerReportWebModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getAccountUrl() + "profitLossReport?fromDate="+fromDate+"&toDate="+toDate,JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		List<AccountLedgerReportWebModel> manageleadger = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<AccountLedgerReportWebModel>>() {
		});
		System.out.println("###" + manageleadger);
		jsonResponse.setBody(manageleadger);
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
		} else {
			jsonResponse.setMessage("Success");
		}
		System.out.println("REsp" + jsonResponse);
		logger.info("Method :profitLossData ends");
		return jsonResponse;
	}

}
