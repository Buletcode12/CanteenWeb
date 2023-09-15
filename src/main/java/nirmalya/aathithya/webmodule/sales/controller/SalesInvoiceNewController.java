package nirmalya.aathithya.webmodule.sales.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.util.IOUtils;
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
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.NumberFormatter;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.master.model.EmpRoleModel;
import nirmalya.aathithya.webmodule.master.model.ProductCategoryModel;
import nirmalya.aathithya.webmodule.master.model.ProductDetailsModel;
import nirmalya.aathithya.webmodule.master.model.ProductMasterModel;
import nirmalya.aathithya.webmodule.procurment.model.InventorySkuProductModel;
import nirmalya.aathithya.webmodule.procurment.model.InventoryVendorDocumentModel;
import nirmalya.aathithya.webmodule.sales.model.CustomerNewModel;
import nirmalya.aathithya.webmodule.sales.model.QuotationNewModel;
import nirmalya.aathithya.webmodule.sales.model.SalesInvoiceNewModel;
import nirmalya.aathithya.webmodule.sales.model.SalesOrderNewModel;

@Controller
@RequestMapping(value = { "sales/" })

public class SalesInvoiceNewController {

	Logger logger = LoggerFactory.getLogger(SalesInvoiceNewController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@Autowired
	SalesInvoiceNewController salesInvoiceNewController;

	@GetMapping(value = { "/view-saleInvoice" })
	public String salesInvoiceDetails(Model model, HttpSession session) {
		logger.info("Method : salesInvoiceDetails starts");
		String organization = "";
		String orgDivision = "";
		String org = "";
		String orgDiv = "";
		
		try {
		
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			DropDownModel[] uom = restTemplate.getForObject(env.getMasterUrl() + "getUOMListForProduct",
					DropDownModel[].class);
			List<DropDownModel> unitList = Arrays.asList(uom);

			model.addAttribute("unitList", unitList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] source = restTemplate.getForObject(env.getPipeline() + "/getCountry",
					DropDownModel[].class);

			List<DropDownModel> sourceList = Arrays.asList(source);
			model.addAttribute("countryList", sourceList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			DropDownModel[] Collection = restTemplate.getForObject(env.getSalesUrl() + "getCollectionList",
					DropDownModel[].class);
			List<DropDownModel> collectionList = Arrays.asList(Collection);

			model.addAttribute("collectionList", collectionList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] Gender = restTemplate.getForObject(env.getEmployeeUrl() + "getgenderList1",
					DropDownModel[].class);
			List<DropDownModel> genderTypeList = Arrays.asList(Gender);
			model.addAttribute("genderTypeList", genderTypeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] payMode = restTemplate.getForObject(env.getSalesUrl() + "GetpaymentModeList",
					DropDownModel[].class);
			List<DropDownModel> paymentModeList = Arrays.asList(payMode);
			logger.info("paymentModeList@" + paymentModeList);
			model.addAttribute("paymentModeList", paymentModeList);

		} catch (Exception e) {
			e.printStackTrace();

		}

		try {
			DropDownModel[] dropDownModel = restTemplate.getForObject(env.getInventoryUrl() + "get-Payment-term",
					DropDownModel[].class);
			List<DropDownModel> paytermList = Arrays.asList(dropDownModel);
			model.addAttribute("paytermList", paytermList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] brand = restTemplate.getForObject(env.getSalesUrl()+"getBrandListForSalesProduct?org=" + org + "&orgDiv=" + orgDiv, DropDownModel[].class);
			List<DropDownModel> brandListt = Arrays.asList(brand);
			
			model.addAttribute("brandListt", brandListt);
			System.out.println("dddddddddddd"+brandListt);
		} catch(Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] mode = restTemplate.getForObject(env.getSalesUrl() + "getModeListForSalesProduct",
					DropDownModel[].class);
			List<DropDownModel> modeList = Arrays.asList(mode);

			model.addAttribute("modeList", modeList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] variationType = restTemplate
					.getForObject(env.getMasterUrl() + "getVariationTypeListtForProduct", DropDownModel[].class);
			List<DropDownModel> variationTypeList = Arrays.asList(variationType);

			model.addAttribute("variationTypeList", variationTypeList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] tmode = restTemplate.getForObject(env.getSalesUrl() + "getTransportationModeList",
					DropDownModel[].class);
			List<DropDownModel> transportationModeList = Arrays.asList(tmode);
			model.addAttribute("transportationModeList", transportationModeList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : salesInvoiceDetails ends");
		return "sales/view-sale-invoice";
	}

	// get Product Category Data List Modal

	@SuppressWarnings("unchecked")
	@PostMapping("/view-saleInvoice-get-total-list")
	public @ResponseBody JsonResponse<List<ProductCategoryModel>> getSalesProductCategoryDataListModal(HttpSession session) {
		logger.info("Method : getSalesProductCategoryDataListModal starts");
		JsonResponse<List<ProductCategoryModel>> resp = new JsonResponse<List<ProductCategoryModel>>();
		try {
			resp = restTemplate.getForObject(env.getSalesUrl() + "getSalesProductCategoryDataListModal",
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String message = resp.getMessage();
		
		if (message != null && message != "") {
			
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : getSalesProductCategoryDataListModal starts");
		return resp;
	}

	/*
	 * Item auto search
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-saleInvoice-item-get-customer-list" })
	public @ResponseBody JsonResponse<InventorySkuProductModel> getItemQuotationAutoSearchListForItem(Model model,
			@RequestBody String searchValue, BindingResult result) {
		logger.info("Method : getItemQuotationAutoSearchListForItem starts");
		logger.info("QuotationNewModel" + searchValue);
		JsonResponse<InventorySkuProductModel> res = new JsonResponse<InventorySkuProductModel>();

		try {
			res = restTemplate.getForObject(
					env.getSalesUrl() + "getItemQuotationAutoSearchListForItem?id=" + searchValue,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getMessage() != null) {

			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("RESPONSE@@" + res);
		logger.info("Method : getItemQuotationAutoSearchListForItem ends");
		return res;
	}



	/*
	 * sales Order Auto Search
	 * 
	 * @SuppressWarnings("unchecked")
	 * 
	 * @PostMapping(value = { "view-saleInvoice-get-customer-list" })
	 * public @ResponseBody JsonResponse<SalesInvoiceNewModel>
	 * getSalesOrderAutoSearchNewList(Model model,
	 * 
	 * @RequestBody String searchValue, BindingResult result) {
	 * 
	 * logger.info("Method : getSalesOrderAutoSearchNewList starts");
	 * logger.info("SalesInvoiceNewModel" + searchValue);
	 * JsonResponse<SalesInvoiceNewModel> res = new
	 * JsonResponse<SalesInvoiceNewModel>();
	 * 
	 * try { res = restTemplate.getForObject(env.getSalesUrl() +
	 * "getSalesOrderAutoSearchNewList?id=" + searchValue, JsonResponse.class); }
	 * catch (Exception e) { e.printStackTrace(); }
	 * 
	 * if (res.getMessage() != null) {
	 * 
	 * res.setCode(res.getMessage()); res.setMessage("Unsuccess"); } else {
	 * res.setMessage("success"); } logger.info("response@" + res);
	 * logger.info("Method : getSalesOrderAutoSearchNewList ends"); return res; }
	 */


	

	/*
	 * Add
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("view-saleInvoice-add")
	public @ResponseBody JsonResponse<Object> addsaleInvoicenew(HttpSession session,
			@RequestBody List<SalesInvoiceNewModel> salesInvoiceNewModel) {
		logger.info("Method : addsaleInvoicenew starts");
		logger.info(salesInvoiceNewModel.toString());
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String dateFormat = "";
		String organization = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}
		

		for (SalesInvoiceNewModel a : salesInvoiceNewModel) {

			if (a.getInvoiceDate() != null && a.getInvoiceDate() != "") {
				a.setInvoiceDate(DateFormatter.inputDateFormat(a.getInvoiceDate(), dateFormat));
			}	
			
			
			/*
			 * if (a.getDueDate() != null && a.getDueDate() != "") {
			 * a.setDueDate(DateFormatter.inputDateFormat(a.getDueDate(), dateFormat)); }
			 */
		}
		for (SalesInvoiceNewModel m : salesInvoiceNewModel) {
			m.setQutCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);
		}
		try {
			resp = restTemplate.postForObject(env.getSalesUrl() + "addsaleInvoicenew", salesInvoiceNewModel,
					JsonResponse.class);

		} catch (Exception e) {

			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("resp+++++++++++++++++++++++" + resp);

		logger.info("Method : addsaleInvoicenew ends");

		return resp;
	}

	/*
	 * view
	 */

	@SuppressWarnings("unchecked")

	@GetMapping("view-saleInvoice-through-ajax")
	public @ResponseBody List<SalesInvoiceNewModel> viewsalesInvoice(HttpSession session) {

		logger.info("Method :getAllsalesInvoice starts");
		JsonResponse<List<SalesInvoiceNewModel>> resp = new JsonResponse<List<SalesInvoiceNewModel>>();
		String userId = "";
		String dateFormat = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}

		EmpRoleModel empModel = new EmpRoleModel();

		empModel.setUserId(userId);
		empModel.setType("WEB");
		empModel.setOrganization(organization);
		empModel.setOrgDivision(orgDivision);
		try {

			resp = restTemplate.postForObject(env.getSalesUrl() + "getAllsalesInvoice", empModel,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();

		List<SalesInvoiceNewModel> salesInvoiceNewModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<SalesInvoiceNewModel>>() {
				});

		for (SalesInvoiceNewModel a : salesInvoiceNewModel) {
			a.setQuantitynew(a.getQuantity());
			if (a.getQutUpdatedOn() != null && a.getQutUpdatedOn() != "") {
				a.setQutUpdatedOn(DateFormatter.dateFormat(a.getQutUpdatedOn(), dateFormat));
			}
			logger.info(salesInvoiceNewModel.toString());

		}

		resp.setBody(salesInvoiceNewModel);
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method :getAllsalesInvoice ends");
		logger.info("RESPONSEview" + resp);
		return resp.getBody();
	}

	/*
	 * edit SalesInvoice
	 */

	@GetMapping(value = { "view-saleInvoice-edit-new" })
	public @ResponseBody List<SalesInvoiceNewModel> viewsalesIvoiceEdit(@RequestParam String id, HttpSession session) {
		logger.info("Method : viewsalesIvoiceEdit starts");
		logger.info(id);
		List<SalesInvoiceNewModel> productList = new ArrayList<SalesInvoiceNewModel>();
		if (id != null && id != "") {
			try {
				SalesInvoiceNewModel[] salesInvoiceNewModel = restTemplate.getForObject(
						env.getSalesUrl() + "viewsalesIvoiceEdit?id=" + id, SalesInvoiceNewModel[].class);

				productList = Arrays.asList(salesInvoiceNewModel);

				productList.forEach(s -> s.setSlNo(s.getSlNo()));

				int count = 0;

				for (SalesInvoiceNewModel m : salesInvoiceNewModel) {
					count++;
					m.setSlNo(count);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.info("Method : viewsalesIvoiceEdit ends");
		logger.info("edit@@@@@@@@SSSSSSSSSSSSSSSSSSSSS" + productList);
		return productList;
	}

	/*
	 * delete
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "view-saleInvoice-delete")
	public @ResponseBody JsonResponse<Object> deletesalesInvoice(@RequestBody SalesInvoiceNewModel salesInvoiceNewModel,
			HttpSession session) {
		logger.info("Method : deletesalesOrder starts");
		logger.info("SalesInvoiceNewModel" + salesInvoiceNewModel);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			salesInvoiceNewModel.setQutCreatedBy(userId);
		} catch (Exception e) {

		}
		try {

			resp = restTemplate.postForObject(env.getSalesUrl() + "deletesalesInvoice", salesInvoiceNewModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");

		} else {
			resp.setMessage("Success");
		}
		logger.info("delete@" + resp);
		logger.info("Method : deletesalesInvoice Ends");
		return resp;
	}

	/*
	 * payment Add
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("view-saleInvoice-payment-add")
	public @ResponseBody JsonResponse<Object> addinvPaymentnew(Model model, HttpSession session,

			@RequestBody SalesInvoiceNewModel salesInvoiceNewModel) {
		logger.info("Method : addinvPaymentnew starts");
		logger.info(salesInvoiceNewModel.toString());
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String dateFormat = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			dateFormat = (String) session.getAttribute("DATEFORMAT");

		} catch (Exception e) {
			e.printStackTrace();
		}

		salesInvoiceNewModel.setQutCreatedBy(userId);

		try {
			resp = restTemplate.postForObject(env.getSalesUrl() + "addinvPaymentnew", salesInvoiceNewModel,
					JsonResponse.class);
			ObjectMapper mapper = new ObjectMapper();

			List<SalesInvoiceNewModel> quotation = mapper.convertValue(resp.getBody(),
					new TypeReference<List<SalesInvoiceNewModel>>() {
					});

			if (salesInvoiceNewModel.getPayDate() != null && salesInvoiceNewModel.getPayDate() != "") {
				salesInvoiceNewModel
						.setPayDate(DateFormatter.inputDateFormat(salesInvoiceNewModel.getPayDate(), dateFormat));

			}

			resp.setBody(quotation);
		} catch (Exception e) {

			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Sradha" + resp);

		logger.info("Method : addinvPaymentnew ends");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-saleInvoice-get-salesperson-list" })
	public @ResponseBody JsonResponse<QuotationNewModel> getSalesPersonAutoSearchList(Model model,
			@RequestBody String searchValue, BindingResult result) {
		logger.info("Method : getSalesPersonAutoSearchList starts");
		// logger.info("QuotationNewModel"+searchValue);
		JsonResponse<QuotationNewModel> res = new JsonResponse<QuotationNewModel>();

		try {
			res = restTemplate.getForObject(env.getSalesUrl() + "getSalesPersonListByAutoSearch?id=" + searchValue,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getMessage() != null) {

			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		// logger.info("RESPONSE@@" + res);
		logger.info("Method : getSalesPersonAutoSearchList ends" + res);
		return res;
	}

	@SuppressWarnings("unchecked")

	@PostMapping("view-saleInvoice-add-salesperson")
	public @ResponseBody JsonResponse<Object> addSalesPerson(@RequestBody QuotationNewModel quotationNewModel,
			HttpSession session) {
		logger.info("Method : addSalesPerson starts");
		String userId = "";
		String dateFormat = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			dateFormat = (String) session.getAttribute("DATEFORMAT");

		} catch (Exception e) {
			e.printStackTrace();
		}
		quotationNewModel.setCreatedBy(userId);

		logger.info("ADDDDATA" + quotationNewModel);

		JsonResponse<Object> resp = new JsonResponse<Object>();
		quotationNewModel.setDobid(DateFormatter.inputDateFormat(quotationNewModel.getDobid(), dateFormat));

		try {

			resp = restTemplate.postForObject(env.getSalesUrl() + "add-salesperson", quotationNewModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("ADDDDDDD" + resp);
		logger.info("Method :addSalesPerson ends");
		return resp;
	}



	

	@SuppressWarnings("unchecked")

	@PostMapping("view-saleInvoice-add-cust-billingaddress")
	public @ResponseBody JsonResponse<Object> addbillingaddress(@RequestBody CustomerNewModel customerNewModel,
			HttpSession session) {
		logger.info("Method : addbillingaddress starts");
		String userId = "";

		try {
			userId = (String) session.getAttribute("USER_ID");

		} catch (Exception e) {
			e.printStackTrace();
		}
		customerNewModel.setCreatedBy(userId);

		logger.info("ADDDDATA" + customerNewModel);

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {

			resp = restTemplate.postForObject(env.getSalesUrl() + "add-billingaddress", customerNewModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("ADDDDDDD" + resp);
		logger.info("Method :addbillingaddress ends");
		return resp;
	}

	@SuppressWarnings({ "unchecked" })
	@PostMapping("/view-saleInvoice-add-cust-shippingaddress")
	public @ResponseBody JsonResponse<Object> saveAddressDetails(@RequestBody CustomerNewModel customerNewModel, HttpSession session) {
		logger.info("Method : saveAddressDetails starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		customerNewModel.setCreatedBy(userId);
		customerNewModel.setOrganization(orgName);
		customerNewModel.setOrgDivision(orgDivision);
		
		try {
			resp = restTemplate.postForObject(env.getSalesUrl()  + "saveAddressDetails", customerNewModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String message = resp.getMessage();
		
		if (message != null && message != "") {
			
		} else {
			resp.setMessage("Success");
		}
		
		logger.info("Method : saveAddressDetails starts");
		return resp;
	}
	// view-customer-stateList
	@SuppressWarnings("unchecked")

	@GetMapping(value = { "view-saleInvoice-stateList" })
	public @ResponseBody JsonResponse<Object> getstateCusList(@RequestParam String id) {
		logger.info("Method : getstateListAJAX starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getSalesUrl() + "getStateLists1?id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("state" + res);
		logger.info("Method : getstateCusList ends");
		return res;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-saleInvoice-get-address")
	public @ResponseBody JsonResponse<CustomerNewModel> getCustomerAddress(@RequestParam String id,@RequestParam String shipId,
			HttpSession session) {
		logger.info(id);
		logger.info("Method : getCustomerAddress starts");

		JsonResponse<CustomerNewModel> jsonResponse = new JsonResponse<CustomerNewModel>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			jsonResponse = restTemplate.getForObject(env.getSalesUrl() + "getCustomerAddressById?id=" + id +"&shipId=" + shipId + "&orgName=" + orgName +"&orgDivision=" + orgDivision,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		CustomerNewModel reimModel = mapper.convertValue(jsonResponse.getBody(), new TypeReference<CustomerNewModel>() {
		});

		jsonResponse.setBody(reimModel);
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("Method : getCustomerAddress ends");
		logger.info("EDITjsonResponse" + jsonResponse);
		return jsonResponse;
	}

	// view-customer-stateList
	@SuppressWarnings("unchecked")

	@GetMapping(value = { "view-saleInvoice-salesorderlist" })
	public @ResponseBody JsonResponse<Object> getSalesorderList(@RequestParam String id, String type) {
		logger.info("Method : getSalesorderList starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getSalesUrl() + "getSalesorderList?id=" + id + "&type=" + type,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("state" + res);
		logger.info("Method : getSalesorderList ends");
		return res;
	}

	// view-saleInvoice-getdataOnSO

	@GetMapping(value = { "view-saleInvoice-getdataOnSO" })
	public @ResponseBody List<SalesOrderNewModel> viewsalesOrderDataOnclickSo(@RequestParam String id,
			HttpSession session) {
		logger.info("Method : viewsalesOrderDataOnclickSo starts");
		logger.info(id);
		List<SalesOrderNewModel> productList = new ArrayList<SalesOrderNewModel>();
		List<InventoryVendorDocumentModel> documentList = new ArrayList<InventoryVendorDocumentModel>();

		if (id != null && id != "") {
			try {
				SalesOrderNewModel[] salesOrderNewModel = restTemplate.getForObject(
						env.getSalesUrl() + "viewsalesOrdeerEdit?id=" + id, SalesOrderNewModel[].class);

				productList = Arrays.asList(salesOrderNewModel);

				productList.forEach(s -> s.setSlNo(s.getSlNo()));

				int count = 0;

				for (SalesOrderNewModel m : salesOrderNewModel) {

					count++;
					m.setSlNo(count);

					String dateFormat = (String) session.getAttribute("DATEFORMAT");

					/*
					 * if (m.getQutValidDate() != null && m.getQutValidDate() != "") {
					 * m.setQutValidDate(DateFormatter.dateFormat(m.getQutValidDate(), dateFormat));
					 * 
					 * }
					 */
					if (m.getOrderReceiveDate() != null && m.getOrderReceiveDate() != "") {
						m.setOrderReceiveDate(DateFormatter.dateFormat(m.getOrderReceiveDate(), dateFormat));

					}
					if (m.getExpectedShipmentDate() != null && m.getExpectedShipmentDate() != "") {
						m.setExpectedShipmentDate(DateFormatter.dateFormat(m.getExpectedShipmentDate(), dateFormat));

					}

				}

				if (productList != null) {
					documentList = productList.get(0).getDocumentList();
					if (documentList != null) {
						for (InventoryVendorDocumentModel m : documentList) {
							if (m.getFileName() != null && m.getFileName() != "") {

								String[] extension = m.getFileName().split("\\.");
								if (extension.length == 2) {
									if (extension[1].equals("xls") || extension[1].equals("xlsx")) {

										String docPath = "<i class=\"fa fa-file-excel-o excel\" title= "
												+ m.getFileName() + "></i> ";

										m.setAction(docPath);
									}
									if (extension[1].equals("pdf")) {
										String docPath = " <i class=\"fa fa-file-pdf-o excel pdf\"   title="
												+ m.getFileName() + " ;></i> ";

										m.setAction(docPath);
									}
									if (extension[1].equals("doc") || extension[1].equals("dox")
											|| extension[1].equals("docx")) {
										String docPath = " <i class=\"fa fa-file-word-o \" aria-hidden=\"true\"  title="
												+ m.getFileName() + "></i> ";
										m.setAction(docPath);
									}
									if (extension[1].equals("png") || extension[1].equals("jpg")
											|| extension[1].equals("jpeg")) {
										String docPath = " <i class=\"fa fa-picture-o \"\" aria-hidden=\"true\" title="
												+ m.getFileName() + "></i>  ";
										m.setAction(docPath);
									}
								} else {
									m.setAction("N/A");
								}
							} else {
								m.setAction("N/A");
							}
							m.setAction("<a href=\"/document/document/" + m.getFileName() + "\" target=\"_blank\" >"
									+ m.getAction() + "</a>");
							logger.info("m.setAction" + m);

						}
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.info("Method : viewsalesOrderDataOnclickSo ends");
		logger.info("edit@@@@@@@@" + productList);
		return productList;
	}

	@SuppressWarnings("unchecked")

	@GetMapping(value = { "view-saleInvoice-get-insertedid" })
	public @ResponseBody JsonResponse<Object> getInvoiceInsertedId() {
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getSalesUrl() + "getInvoiceInsertedId", JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getInvoiceInsertedId ends");
		logger.info("assssssssssssssssssss" + res);
		return res;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-saleInvoice-pdf-downloads")
	public void getInvoicePdfDetails(HttpServletResponse response, Model model, HttpSession session,
			@RequestParam("invId") String encodedParam1,@RequestParam("copytype") String encodedParam2,@RequestParam("label") String encodedParam3) {

		logger.info("Method : getInvoicePdfDetails starts");

		byte[] encodeByte1 = Base64.getDecoder().decode(encodedParam1.getBytes());
		String invIdd = (new String(encodeByte1));
		
		byte[] encodeByte2 = Base64.getDecoder().decode(encodedParam2.getBytes());
		String copytype = (new String(encodeByte2));
		byte[] encodeByte3 = Base64.getDecoder().decode(encodedParam3.getBytes());
		String label = (new String(encodeByte3));
		
		logger.info("invIdd====" + invIdd);
		logger.info("copytype====" + copytype);
		String[] copytypeArray = copytype.split(",");
		Integer countPage = copytypeArray.length;
		
		String[] lebelArray = label.split(",");
		
		logger.info("label====" + label);
		
		List<Integer> pageIndices = new ArrayList<>();
		for (int i = 0; i < countPage; i++) {
		    pageIndices.add(i);
		}
		

		
		//logger.info("lebelArray====" + lebelArray);
		List<SalesInvoiceNewModel> productList = new ArrayList<SalesInvoiceNewModel>();
		try {
			SalesInvoiceNewModel[] salesInvoiceNewModel = restTemplate.getForObject(
					env.getSalesUrl() + "viewsales-invoice-viewPdf?id=" + invIdd, SalesInvoiceNewModel[].class);
			productList = Arrays.asList(salesInvoiceNewModel);
			productList.forEach(s -> s.setSlNo(s.getSlNo()));
			int count = 0;
			for (SalesInvoiceNewModel m : salesInvoiceNewModel) {
				count++;
				m.setSlNo(count);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String id = productList.get(0).getCustId();
		logger.info("id====" + id);
		JsonResponse<CustomerNewModel> jsonResponse = new JsonResponse<CustomerNewModel>();
		try {
			jsonResponse = restTemplate.getForObject(env.getSalesUrl() + "getCustomerAddressById?id=" + id,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		CustomerNewModel reimModel = mapper.convertValue(jsonResponse.getBody(), new TypeReference<CustomerNewModel>() {
		});
		logger.info("JsonResponse====" + jsonResponse);
		logger.info("reimModel====" + reimModel);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("invoice", productList);

		// String logo = "";
		String logo = "classpath:static/assets/images/invoice-banner.jpg";
		data.put("logo", logo);
		data.put("copytype", copytype);
		data.put("copytypeArray", copytypeArray);
		data.put("lebelArray", lebelArray);
		data.put("buyer", reimModel);
		data.put("countPage", countPage);
		data.put("pageIndices", pageIndices);
		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=salesInvoice.pdf");
		File file;
		byte[] fileData = null;
		try {
			file = pdfGeneratorUtil.createPdf("sales/sales-invoice-pdf", data);
			InputStream in = new FileInputStream(file);
			fileData = IOUtils.toByteArray(in);
			response.setContentLength(fileData.length);
			response.getOutputStream().write(fileData);
			response.getOutputStream().flush();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		// logger.info("REsp" + jsonResponse);
		logger.info("Method : getInvoicePdfDetails ends");
	}
	
	@GetMapping("view-saleInvoice-shipping-address")
	public @ResponseBody Object viewShippingAddressData(@RequestParam String customerId, HttpSession session) {

		logger.info("Method :viewShippingAddressData starts");
		@SuppressWarnings("rawtypes")
		JsonResponse resp = new JsonResponse();
		String orgName = "";
		String orgDivision = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getSalesUrl() + "rest-viewShippingAddressData?customerId=" + customerId + "&org="
					+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	@GetMapping("view-saleInvoice-shipping-dataedit")
	public @ResponseBody Object editShippingAddressData(@RequestParam String addressId, HttpSession session) {

		logger.info("Method :editShippingAddressData starts");

		@SuppressWarnings("rawtypes")
		JsonResponse resp = new JsonResponse();
		String orgName = "";
		String orgDivision = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getSalesUrl() + "rest-editShippingAddressData?addressId="
					+ addressId + "&org=" + orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	/*
	 * customer autoSearch
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-saleInvoice-get-customer-list" })
	public @ResponseBody JsonResponse<QuotationNewModel> getCustomerAutoSearchList(Model model,
			@RequestBody String searchValue, BindingResult result, HttpSession session) {
		logger.info("Method : getCustomerAutoSearchList starts");
		// logger.info("QuotationNewModel"+searchValue);
		JsonResponse<QuotationNewModel> res = new JsonResponse<QuotationNewModel>();
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}
		try {
			res = restTemplate.getForObject(env.getSalesUrl() + "getCustomerListByAutoSearch?id=" + searchValue
					+ "&org=" + orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getMessage() != null) {

			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		// logger.info("RESPONSE@@" + res);
		logger.info("Method : getCustomerAutoSearchList ends");
		return res;
	}
	
	// get Product Category Data List Modal

		@SuppressWarnings("unchecked")
		@PostMapping("view-saleInvoice-item-get-product-list")
		public @ResponseBody JsonResponse<List<ProductCategoryModel>> getProductCategoryListModal(
				@RequestBody String yearDtls, HttpSession session) {
			logger.info("Method : getProductCategoryListModal starts");

			JsonResponse<List<ProductCategoryModel>> resp = new JsonResponse<List<ProductCategoryModel>>();
			// logger.info(yearDtls);
			try {
				resp = restTemplate.getForObject(env.getInventoryUrl() + "getProductCategoryDataListModal",
						JsonResponse.class);

			} catch (Exception e) {
				e.printStackTrace();
			}

			if (resp.getMessage() != null && resp.getMessage() != "") {
				resp.setCode(resp.getMessage());
				resp.setMessage("Unsuccess");
			} else {
				resp.setMessage("success");
			}
			// logger.info("resp@@@@@@@"+ resp);
			logger.info("Method : getProductCategoryListModal starts");
			return resp;
		}
		// grt product by cat

				@SuppressWarnings("unchecked")

				@PostMapping(value = { "view-saleInvoice-item-product-by-cat" })
				public @ResponseBody JsonResponse<InventorySkuProductModel> getProductsByCat(Model model, @RequestBody String index,
						BindingResult result) {
					logger.info("Method : getProductsByCat starts");

					String indexValue = index.substring(0, index.length() - 1);

					JsonResponse<InventorySkuProductModel> res = new JsonResponse<InventorySkuProductModel>();

					try {
						res = restTemplate.getForObject(env.getSalesUrl() + "getProductsNByCat?id=" + indexValue,
								JsonResponse.class);
					} catch (Exception e) {
						e.printStackTrace();
					}

					if (res.getMessage() != null) {

						res.setCode(res.getMessage());
						res.setMessage("Unsuccess");
					} else {
						res.setMessage("success");
					}
					// logger.info(res);
					logger.info("Method : getProductsByCat ends");
					return res;
				}
			
				// Add New Customer
				
				
				@SuppressWarnings("unchecked")
				@PostMapping("/view-saleInvoice-adds-customer")
				public @ResponseBody JsonResponse<Object> addCustomer(@RequestBody CustomerNewModel customerNewModel,
						HttpSession session) {

					logger.info("Method : addAccountCustomer starts");
					String organization=""; 
					String orgDivision="";
					try {
						
						organization = (String) session.getAttribute("ORGANIZATION"); 
						orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

					} catch (Exception e) {
						e.printStackTrace();
					}
					
					customerNewModel.setOrganization(organization);
					customerNewModel.setOrgDivision(orgDivision);

					JsonResponse<Object> resp = new JsonResponse<Object>();
					logger.info("web AccountModel ======================" + customerNewModel);
					try {
						String userId = "";
						try {
							userId = (String) session.getAttribute("USER_ID");
						} catch (Exception e) {
							e.printStackTrace();
						}

						logger.info("created by id-------------------------------" + userId);

						customerNewModel.setCreatedBy(userId);

						resp = restTemplate.postForObject(env.getSalesUrl() + "/addAccountCustomer", customerNewModel,
								JsonResponse.class);

					} catch (RestClientException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					String message = resp.getMessage();

					if (message != null && message != "") {

					} else {
						resp.setMessage("Success");
					}
					logger.info("Method : addAccountCustomer ends");

					return resp;
				}
				
				@SuppressWarnings({ "unchecked" })
				@PostMapping("/view-saleInvoice-save-shipping-address")
				public @ResponseBody JsonResponse<Object> saveShippingAddressDetails(@RequestBody CustomerNewModel customerNewModel, HttpSession session) {
					logger.info("Method : saveAddressDetails starts");
					
					JsonResponse<Object> resp = new JsonResponse<Object>();
					
					String userId = "";
					String orgName = "";
					String orgDivision = "";
					
					try {
						userId = (String) session.getAttribute("USER_ID");
						orgName = (String) session.getAttribute("ORGANIZATION");
						orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					customerNewModel.setCreatedBy(userId);
					customerNewModel.setOrganization(orgName);
					customerNewModel.setOrgDivision(orgDivision);
					
					try {
						resp = restTemplate.postForObject(env.getSalesUrl()  + "saveAddressDetails", customerNewModel,
								JsonResponse.class);
					} catch (RestClientException e) {
						e.printStackTrace();
					}
					
					String message = resp.getMessage();
					
					if (message != null && message != "") {
						
					} else {
						resp.setMessage("Success");
					}
					
					logger.info("Method : saveAddressDetails starts");
					return resp;
				}

				
				@SuppressWarnings("unchecked")
				@GetMapping("view-saleInvoice-shippingdetails")
				public @ResponseBody Object viewShippingDetails(HttpSession session,@RequestParam String customerIdd) {
					logger.info("Method :viewShippingDetails starts");
					JsonResponse<Object> resp = new JsonResponse<Object>();
					String orgName = "";
					String orgDivision = "";
					//System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"+customerId);
					try {
						orgName = (String) session.getAttribute("ORGANIZATION");
						orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
					} catch (Exception e) {
						logger.error(e.getMessage());
					}
					try {
						resp = restTemplate.getForObject(
								env.getSalesUrl() + "rest-viewShippingDetails?customerIdd=" + customerIdd + "&orgName=" + orgName +"&orgDivision=" + orgDivision,
								JsonResponse.class);
					} catch (Exception e) {
						e.printStackTrace();
					}
					logger.info("Method :viewShippingDetails ends");
					return resp;
				}
				
				@SuppressWarnings("unchecked")
				@GetMapping("view-saleInvoice-edit-address")
				public @ResponseBody Object editShippingDetails(HttpSession session,@RequestParam String addressId) {
					logger.info("Method :editShippingDetails starts");
					JsonResponse<Object> resp = new JsonResponse<Object>();
					String orgName = "";
					String orgDivision = "";
					try {
						orgName = (String) session.getAttribute("ORGANIZATION");
						orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
					} catch (Exception e) {
						logger.error(e.getMessage());
					}
					try {
						resp = restTemplate.getForObject(
								env.getSalesUrl() + "rest-editShippingDetails?addressId=" + addressId +"&orgName=" + orgName +"&orgDivision=" + orgDivision,
								JsonResponse.class);
					} catch (Exception e) {
						e.printStackTrace();
					}
					logger.info("Method :editShippingDetails ends");
					return resp;
				}
				
				@GetMapping("view-saleInvoice-address-delete")
				public @ResponseBody Object deleteaddressdata(@RequestParam String id, HttpSession session) {

					logger.info("Method :deleteaddressdata starts");
					@SuppressWarnings("rawtypes")
					JsonResponse resp = new JsonResponse();
					String orgName = "";
					String orgDivision = "";

					try {
						orgName = (String) session.getAttribute("ORGANIZATION");
						orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
					} catch (Exception e) {
						e.printStackTrace();
					}
					try {
						resp = restTemplate.getForObject(env.getSalesUrl() + "rest-deleteaddressdata?deleteId=" + id + "&org="
								+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return resp;
				}
				
				/*
				 * @SuppressWarnings("unchecked")
				 * 
				 * @PostMapping("/view-saleInvoice-get-total-list") public @ResponseBody
				 * JsonResponse<List<ProductCategoryModel>>
				 * getProductCategoryDataListModal(HttpSession session) {
				 * logger.info("Method : getProductCategoryDataListModal starts");
				 * JsonResponse<List<ProductCategoryModel>> resp = new
				 * JsonResponse<List<ProductCategoryModel>>(); try { resp =
				 * restTemplate.getForObject(env.getInventoryUrl() +
				 * "getProductCategoryDataListModal", JsonResponse.class); } catch
				 * (RestClientException e) { e.printStackTrace(); }
				 * 
				 * String message = resp.getMessage();
				 * 
				 * if (message != null && message != "") {
				 * 
				 * } else { resp.setMessage("Success"); }
				 * logger.info("Method : getProductCategoryDataListModal starts"); return resp;
				 * }
				 */
				

				@SuppressWarnings({ "unchecked" })
				@PostMapping("/view-saleInvoice-products-save")
				public @ResponseBody JsonResponse<Object> saveProductMaster(@RequestBody ProductMasterModel product,
						HttpSession session) {
					logger.info("Method : saveProductMaster starts");

					JsonResponse<Object> resp = new JsonResponse<Object>();

					String userId = "";
					String orgName = "";
					String orgDivision = "";

					try {
						userId = (String) session.getAttribute("USER_ID");
						orgName = (String) session.getAttribute("ORGANIZATION");
						orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
					} catch (Exception e) {
						e.printStackTrace();
					}

					product.setCreatedBy(userId);
					product.setOrganizationName(orgName);
					product.setOrganizationDivision(orgDivision);
					MultipartFile inputFile = (MultipartFile) session.getAttribute("productPFile");
					byte[] bytes;
					String imageName = null;

					if (inputFile != null) {
						try {
							bytes = inputFile.getBytes();
							String[] fileType = inputFile.getContentType().split("/");
							imageName = saveAllImage(bytes, fileType[1]);
							// logger.info(imageName);

							product.setpImgName(imageName);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}

					try {
						resp = restTemplate.postForObject(env.getMasterUrl() + "saveProductMaster", product, JsonResponse.class);
					} catch (RestClientException e) {
						e.printStackTrace();
					}

					String message = resp.getMessage();

					if (message != null && message != "") {

					} else {
						resp.setMessage("Success");
					}

					logger.info("Method : saveProductMaster starts");
					return resp;
				}
				
				private String saveAllImage(byte[] imageBytes, String ext) {
					logger.info("Method : saveAllImage starts");

					String imageName = null;

					try {

						if (imageBytes != null) {
							long nowTime = new Date().getTime();
							if (ext.contentEquals("jpeg")) {
								imageName = nowTime + ".jpg";
							} else {
								imageName = nowTime + "." + ext;
							}

						}

						Path path = Paths.get(env.getFileUploadMaster() + imageName);
						if (imageBytes != null) {
							Files.write(path, imageBytes);
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
					logger.info("Method : saveAllImage ends");
					return imageName;

				}

				
				@PostMapping("/view-saleInvoice-upload-fileprofuct")
				public @ResponseBody JsonResponse<Object> uploadProductFile(@RequestParam("file") MultipartFile inputFile,
						HttpSession session) {
					logger.info("Method : uploadProductFile controller  starts");

					JsonResponse<Object> response = new JsonResponse<Object>();

					try {
						response.setMessage(inputFile.getOriginalFilename());
						// logger.info(inputFile);
						session.setAttribute("productPFile", inputFile);

					} catch (RestClientException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}

					logger.info("Method : uploadProductFile controller ' ends");
					return response;
				}
				
				@PostMapping("view-saleInvoice-delete-fileproduct")
				public @ResponseBody JsonResponse<Object> deleteFile(HttpSession session) {
					logger.info("Method : deleteFile employee uploadimage controller starts");

					JsonResponse<Object> response = new JsonResponse<Object>();

					try {
						session.removeAttribute("productPFile");
					} catch (RestClientException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}

					logger.info("Method : deleteFile employee uploadimage controller ends");
					return response;
				}
				
				@SuppressWarnings({ "unchecked" })
				@PostMapping("/view-saleInvoice-save-sku-dtls")
				public @ResponseBody JsonResponse<Object> saveProductDetails(@RequestBody ProductDetailsModel product,
						HttpSession session) {
					logger.info("Method : saveProductDetails starts");

					JsonResponse<Object> resp = new JsonResponse<Object>();

					String userId = "";
					String orgName = "";
					String orgDivision = "";

					try {
						userId = (String) session.getAttribute("USER_ID");
						orgName = (String) session.getAttribute("ORGANIZATION");
						orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
					} catch (Exception e) {
						e.printStackTrace();
					}

					product.setCreatedBy(userId);
					product.setOrganizationName(orgName);
					product.setOrganizationDivision(orgDivision);

					try {
						resp = restTemplate.postForObject(env.getMasterUrl() + "saveProductDetails", product, JsonResponse.class);
					} catch (RestClientException e) {
						e.printStackTrace();
					}

					String message = resp.getMessage();

					if (message != null && message != "") {

					} else {
						resp.setMessage("Success");
					}

					logger.info("Method : saveProductDetails starts");
					return resp;
				}
				
				@SuppressWarnings("unchecked")
				@GetMapping(value = { "view-saleInvoice-get-sku-by-product" })
				public @ResponseBody List<ProductDetailsModel> getSKUListingById(Model model, @RequestParam String id,
						HttpServletRequest request, HttpSession session) {
					logger.info("Method : getSKUListingById starts");

					JsonResponse<List<ProductDetailsModel>> res = new JsonResponse<List<ProductDetailsModel>>();

					String dateFormat = "";
					String orgName = "";
					String orgDivision = "";

					try {
						dateFormat = (String) session.getAttribute("DATEFORMAT");
						orgName = (String) session.getAttribute("ORGANIZATION");
						orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
					} catch (Exception e) {
						e.printStackTrace();
					}

					try {
						res = restTemplate.getForObject(env.getMasterUrl() + "getSKUListingById?id=" + id + "&orgName=" + orgName
								+ "&orgDiv=" + orgDivision, JsonResponse.class);
						ObjectMapper mapper = new ObjectMapper();

						List<ProductDetailsModel> product = mapper.convertValue(res.getBody(),
								new TypeReference<List<ProductDetailsModel>>() {
								});

						for (ProductDetailsModel m : product) {
							m.setCreatedDate(DateFormatter.dateFormat(m.getCreatedDate(), dateFormat));
							m.setsPrice(NumberFormatter.doubleToStringWithComma(m.getSalePrice()));
						}
						res.setBody(product);
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (res.getMessage() != null) {
						res.setCode(res.getMessage());
						res.setMessage("Unsuccess");
					} else {
						res.setMessage("success");
					}
					logger.info("Method : getSKUListingById ends");
					return res.getBody();

				}
				
				@SuppressWarnings("unchecked")
				@PostMapping(value = { "view-saleInvoice-get-sku-details" })
				public @ResponseBody JsonResponse<ProductDetailsModel> getSKUDetails(Model model,
						@RequestBody DropDownModel tCountry, BindingResult result) {
					logger.info("Method : getSKUDetails starts");

					JsonResponse<ProductDetailsModel> res = new JsonResponse<ProductDetailsModel>();

					try {
						res = restTemplate.getForObject(
								env.getMasterUrl() + "getSKUDetailsById?id=" + tCountry.getKey() + "&skuid=" + tCountry.getName(),
								JsonResponse.class);

					} catch (Exception e) {
						e.printStackTrace();
					}
					if (res.getMessage() != null) {
						res.setCode(res.getMessage());
						res.setMessage("Unsuccess");
					} else {
						res.setMessage("success");
					}

					logger.info("Method : getSKUDetails ends");
					return res;

				}
				
				@SuppressWarnings("unchecked")
				@GetMapping("view-saleInvoice-deletesku")
				public @ResponseBody JsonResponse<Object> deletesku(Model model, HttpSession session, @RequestParam String id) {

					logger.info("Method : deletesku starts");

					JsonResponse<Object> resp = new JsonResponse<Object>();
					String orgName = "";
					String orgDivision = "";
					try {
						orgName = (String) session.getAttribute("ORGANIZATION");
						orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
					} catch (Exception e) {
						e.printStackTrace();
					}
					try {
						resp = restTemplate.getForObject(
								env.getMasterUrl() + "deletesku?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision,
								JsonResponse.class);
					} catch (RestClientException e) {
						e.printStackTrace();
					}

					if (resp.getCode().equals("success")) {
						resp.setMessage("Success");
					} else {

					}
					logger.info("Method : deletesku ends");
					return resp;
				}
				
				
				@SuppressWarnings("unchecked")
				@PostMapping(value = { "view-saleInvoice-get-tcs-list" })
				public @ResponseBody JsonResponse<QuotationNewModel> getTCSAutoSearchList(Model model,
						@RequestBody String searchValue, BindingResult result,HttpSession session) {
					logger.info("Method : getTCSAutoSearchList starts");
						//logger.info("QuotationNewModel"+searchValue);
					String orgName = "";
					String orgDivision = "";
					try {

						orgName = (String) session.getAttribute("ORGANIZATION");
						orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
					} catch (Exception e) {
						logger.error(e.getMessage());
					}
					JsonResponse<QuotationNewModel> res = new JsonResponse<QuotationNewModel>();

					try {
						res = restTemplate.getForObject(env.getSalesUrl()+ "getTCSAutoSearchList?id=" + searchValue+ "&org="
								+ orgName + "&orgDiv=" + orgDivision,
								JsonResponse.class);
					} catch (Exception e) {
						e.printStackTrace();
					}

					if (res.getMessage() != null) {

						res.setCode(res.getMessage());
						res.setMessage("Unsuccess");
					} else {
						res.setMessage("success");
					}
					//logger.info("RESPONSE@@" + res);
					logger.info("Method : getTCSAutoSearchList ends"+res);
					return res;
				}
				
				//TCS Add
				@SuppressWarnings("unchecked")

				@PostMapping("view-saleInvoice-add-tcs")
				public @ResponseBody JsonResponse<Object> addTcs(
						@RequestBody QuotationNewModel quotationNewModel, HttpSession session) {
					logger.info("Method : addTcs starts");
					String userId = "";
					String organization=""; 
					String orgDivision="";
					try {
						userId = (String) session.getAttribute("USER_ID");
						organization = (String) session.getAttribute("ORGANIZATION"); 
						orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					quotationNewModel.setCreatedBy(userId);
					quotationNewModel.setOrganization(organization);
					quotationNewModel.setOrgDivision(orgDivision);
					logger.info("ADDDDATA" + quotationNewModel);

					

					JsonResponse<Object> resp = new JsonResponse<Object>();
					

					try {

						resp = restTemplate.postForObject(env.getSalesUrl() + "add-tcs", quotationNewModel,
								JsonResponse.class);
					} catch (RestClientException e) {
						e.printStackTrace();
					}

					if (resp.getMessage() != "" && resp.getMessage() != null) {
						resp.setCode(resp.getMessage());
						resp.setMessage("Unsuccess");
					} else {
						resp.setMessage("Success");
					}
					logger.info("ADDDDDDD" + resp);
					logger.info("Method :addTcs ends");
					return resp;
				}	

				
				@SuppressWarnings("unchecked")
				@GetMapping(value = { "view-saleInvoice-get-sku-listing" })
				public @ResponseBody List<ProductMasterModel> getProductSKUListing(Model model, HttpServletRequest request,@RequestParam String type, HttpSession session) {
					logger.info("Method : getProductSKUListing starts");
					
					JsonResponse<List<ProductMasterModel>> res = new JsonResponse<List<ProductMasterModel>>();
					String orgName = "";
					String orgDivision = "";
					try {
						orgName = (String) session.getAttribute("ORGANIZATION");
						orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
					} catch (Exception e) {
						e.printStackTrace();
					}
					try {
						res = restTemplate.getForObject(env.getMasterUrl() + "getProductSKUListing?type="+type +"&orgName=" + orgName + "&orgDiv=" + orgDivision,
								JsonResponse.class);
						
						ObjectMapper mapper = new ObjectMapper();

						List<ProductMasterModel> product = mapper.convertValue(res.getBody(),
								new TypeReference<List<ProductMasterModel>>() {
								});
						for(ProductMasterModel m : product) {
							
							if(m.getProductStatus().contentEquals("1")) {
								m.setProductStatus("Active");
							} else {
								m.setProductStatus("Inactive");
							}
						    m.setpPrice(NumberFormatter.doubleToStringWithComma(m.getPurchasePrice()));
						    m.setsPrice(NumberFormatter.doubleToStringWithComma(m.getSalePrice()));
						}
						
						res.setBody(product);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (res.getMessage() != null) {
						res.setCode(res.getMessage());
						res.setMessage("Unsuccess");
					} else {
						res.setMessage("success");
					}
					
					logger.info("Method : getProductSKUListing ends"+res.getBody());
					return res.getBody();
					
				}
				
				@SuppressWarnings("unchecked")
				@PostMapping(value = { "view-saleInvoice-get-product-details" })
				public @ResponseBody JsonResponse<ProductMasterModel> getProductDetails(Model model, @RequestBody String tCountry,
						BindingResult result) {
					logger.info("Method : getProductDetails starts");
					
					JsonResponse<ProductMasterModel> res = new JsonResponse<ProductMasterModel>();
					
					try {
						res = restTemplate.getForObject(env.getMasterUrl() + "getProductDetailsById?id=" + tCountry,
								JsonResponse.class);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (res.getMessage() != null) {
						res.setCode(res.getMessage());
						res.setMessage("Unsuccess");
					} else {
						res.setMessage("success");
					}
					
					logger.info("Method : getProductDetails ends"+res);
					return res;
					
				}
					
				@SuppressWarnings("unchecked")
				@PostMapping("view-saleInvoice-products-delete")
				public @ResponseBody JsonResponse<Object> deleteProductMaster(Model model, @RequestParam String id,
						@RequestParam String simpleid, HttpSession session) {
					logger.info("Method : deleteProductMaster starts");

					JsonResponse<Object> resp = new JsonResponse<Object>();
					String createdBy = "";
					String orgName = "";
					String orgDivision = "";

					try {
						createdBy = (String) session.getAttribute("USER_ID");
						orgName = (String) session.getAttribute("ORGANIZATION");
						orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					try {
						resp = restTemplate.getForObject(
								env.getMasterUrl() + "deleteProductMaster?id=" + id + "&createdBy=" + createdBy + "&simpleid=" + simpleid +"&org=" + orgName + "&orgDiv=" + orgDivision,
								JsonResponse.class);
					} catch (RestClientException e) {
						e.printStackTrace();
					}

					if (resp.getMessage() != null && resp.getMessage() != "") {
						resp.setCode(resp.getMessage());
						resp.setMessage("Unsuccess");
					} else {
						resp.setMessage("success");
					}

					logger.info("Method : deleteProductMaster ends");
					return resp;
				}
				
				@GetMapping("view-saleInvoice-reject")
				public @ResponseBody Object rejectInvoice(@RequestParam String id,@RequestParam String comment, HttpSession session) {

					logger.info("Method :rejectInvoice starts");
					@SuppressWarnings("rawtypes")
					JsonResponse resp = new JsonResponse();
					String orgName = "";
					String orgDivision = "";

					try {
						orgName = (String) session.getAttribute("ORGANIZATION");
						orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
					} catch (Exception e) {
						e.printStackTrace();
					}
					try {
						resp = restTemplate.getForObject(env.getSalesUrl() + "rest-rejectInvoice?invId=" + id + "&comment="
								+ comment + "&orgName=" + orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return resp;
				}
}
