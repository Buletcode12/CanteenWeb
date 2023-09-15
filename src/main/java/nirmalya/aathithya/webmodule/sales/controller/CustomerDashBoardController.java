package nirmalya.aathithya.webmodule.sales.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.sales.model.CustomerGraphModel;
import nirmalya.aathithya.webmodule.sales.model.CustomerStoreGraphModel;

@Controller
@RequestMapping(value = { "sales/" })
public class CustomerDashBoardController {
	Logger logger = LoggerFactory.getLogger(CustomerDashBoardController.class);
	
	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;
	@GetMapping(value = { "/customer-dashboard" })
	public String customerDetails(Model model, HttpSession session) {
		logger.info("Method : customerDetails starts");
		
		DropDownModel[] quotitioncount = restClient.getForObject(env.getSalesUrl() + "quotitionCount",
				DropDownModel[].class);
		List<DropDownModel> totalQuotion = Arrays.asList(quotitioncount);
		model.addAttribute("totalQuotion", totalQuotion);
		/*
		 * Total Sales Order count
		 * 
		 */
		DropDownModel[] ordercount = restClient.getForObject(env.getSalesUrl() + "ordercount",
				DropDownModel[].class);
		List<DropDownModel> totalOrder = Arrays.asList(ordercount);
		model.addAttribute("totalOrder", totalOrder);
		/*
		 * Total Customer count
		 * 
		 */
		DropDownModel[] customercount = restClient.getForObject(env.getSalesUrl() + "customercount",
				DropDownModel[].class);
		List<DropDownModel> totalcustomer = Arrays.asList(customercount);
		model.addAttribute("totalcustomer", totalcustomer);
		/*
		 * Total Sales Invoice count
		 * 
		 */
		DropDownModel[] salesInvoiceCount = restClient.getForObject(env.getSalesUrl() + "salesInvoiceCount",
				DropDownModel[].class);
		List<DropDownModel> totalSalesInvoice = Arrays.asList(salesInvoiceCount);
		model.addAttribute("totalSalesInvoice", totalSalesInvoice);
		/*
		 * Total Sales Store repoort
		 * 
		 */
		List<CustomerStoreGraphModel> resp = new ArrayList<CustomerStoreGraphModel>();

		CustomerStoreGraphModel[] customerStore = restClient
				.getForObject(env.getSalesUrl() + "customerStore", CustomerStoreGraphModel[].class);
		resp = Arrays.asList(customerStore);
		
		model.addAttribute("customerStore", resp);
		/*
		 * Total Customer repoort
		 * 
		 */
		List<CustomerGraphModel> customer = new ArrayList<CustomerGraphModel>();
		
		CustomerGraphModel[] customerReport = restClient
				.getForObject(env.getSalesUrl() + "customer", CustomerGraphModel[].class);
		customer = Arrays.asList(customerReport);
		model.addAttribute("customerReport", customer);
	
		
		logger.info("Method : customerDetails ends");
		return "sales/customer-dashboard";
	}
	
	/*
	 * Customer Graph
	 */
	
	@GetMapping("/customer-dashboard-graph")
	public @ResponseBody JsonResponse<Object>customerGraph(HttpSession session) {

		logger.info("Method : customerGraph starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			CustomerGraphModel[] auditTypes = restClient
					.getForObject(env.getSalesUrl() + "customerGraph", CustomerGraphModel[].class);
			List<CustomerGraphModel> dataList = Arrays.asList(auditTypes);
			List<Integer> totalInvoice = new ArrayList<Integer>();
			List<Integer> totalQuotation = new ArrayList<Integer>();

			List<String> monthList = new ArrayList<String>();

			for (CustomerGraphModel m : auditTypes) {

				totalInvoice.add(m.getTotalInvoice());
				totalQuotation.add(m.getTotalQuotation());
				monthList.add(m.getMonth());

			}

			dataList.get(0).setMonthList(monthList);

			dataList.get(0).setTotalInvoiceList(totalInvoice);
			dataList.get(0).setTotalQuotationList(totalQuotation);
			resp.setBody(dataList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//logger.info(resp);
		logger.info("Method : customerGraph ends");

		return resp;
	}
	/*
	 * Customer Store Graph
	 */
	
	@GetMapping("/customer-dashboard-Store-graph")
	public @ResponseBody JsonResponse<Object>customerStoreGraph(HttpSession session, Model model) {
		
		logger.info("Method : customerStoreGraph starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			CustomerGraphModel[] auditTypes = restClient
					.getForObject(env.getSalesUrl() + "customerStoreGraph", CustomerGraphModel[].class);
			List<CustomerGraphModel> dataList = Arrays.asList(auditTypes);
			List<Integer> totalInvoice = new ArrayList<Integer>();
			List<Integer> totalQuotation = new ArrayList<Integer>();
			
			List<String> store = new ArrayList<String>();
			
			for (CustomerGraphModel m : auditTypes) {
				
				totalInvoice.add(m.getTotalInvoice());
				totalQuotation.add(m.getTotalQuotation());
				store.add(m.getStore());
				
			}
			
			dataList.get(0).setStoreList(store);;
			
			dataList.get(0).setTotalInvoiceList(totalInvoice);
			dataList.get(0).setTotalQuotationList(totalQuotation);
			resp.setBody(dataList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//logger.info(resp);
		logger.info("Method : customerStoreGraph ends");
		
		return resp;
	}

}
