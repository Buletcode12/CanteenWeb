package nirmalya.aathithya.webmodule.sales.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.NumberFormatter;
import nirmalya.aathithya.webmodule.master.model.EmpRoleModel;
import nirmalya.aathithya.webmodule.master.model.ProductCategoryModel;
import nirmalya.aathithya.webmodule.master.model.ProductDetailsModel;
import nirmalya.aathithya.webmodule.master.model.ProductMasterModel;
import nirmalya.aathithya.webmodule.procurment.model.InventorySkuProductModel;
import nirmalya.aathithya.webmodule.procurment.model.InventoryVendorDocumentModel;
import nirmalya.aathithya.webmodule.sales.model.CustomerNewModel;
import nirmalya.aathithya.webmodule.sales.model.QuotationNewModel;
import nirmalya.aathithya.webmodule.sales.model.SalesOrderNewModel;

@Controller
@RequestMapping(value = { "sales/" })
public class SalesOrderNewController {
	
	Logger logger = LoggerFactory.getLogger(SalesOrderNewController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	SalesOrderNewController salesOrderNewController;

	@GetMapping(value = { "/view-saleorder" })
	public String salesOrderDetails(Model model, HttpSession session) {
		logger.info("Method : salesOrderDetails starts");
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
			DropDownModel[] uom = restTemplate.getForObject(env.getMasterUrl()+"getUOMListForProduct", DropDownModel[].class);
			List<DropDownModel> unitList = Arrays.asList(uom);
			
			model.addAttribute("unitList", unitList);
		} catch(Exception e) {
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
			DropDownModel[] paymentMode = restTemplate.getForObject(env.getEmployeeUrl() + "getPaymentMode",
					DropDownModel[].class);
			List<DropDownModel> PayModeLists = Arrays.asList(paymentMode);

			model.addAttribute("PayModeLists", PayModeLists);

		} catch (RestClientException e) {
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
			DropDownModel[] Gender = restTemplate.getForObject(env.getEmployeeUrl() + "getgenderList1",
					DropDownModel[].class);
			List<DropDownModel> genderTypeList = Arrays.asList(Gender);
			model.addAttribute("genderTypeList", genderTypeList);
		} catch (RestClientException e) {
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
		
		logger.info("Method : salesOrderDetails ends");
		return "sales/view-salesorder";
	}
	
 
	/*
	 * customer autoSearch
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-saleorder-get-customer-list" })
	public @ResponseBody JsonResponse<QuotationNewModel> getCustomerAutoSearchList(Model model,
			@RequestBody String searchValue, BindingResult result,HttpSession session) {
		logger.info("Method : getCustomerAutoSearchList starts");
			//logger.info("QuotationNewModel"+searchValue);
		JsonResponse<QuotationNewModel> res = new JsonResponse<QuotationNewModel>();
		String orgName = "";
		String orgDivision = "";
		try {
			
		
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			
		} catch (Exception e) {

		}
		try {
			res = restTemplate.getForObject(env.getSalesUrl()+ "getCustomerListByAutoSearch?id=" + searchValue+ "&org="
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
		logger.info("Method : getCustomerAutoSearchList ends");
		return res;
	}
	
	
	

	//view-customer-stateList
		@SuppressWarnings("unchecked")

		@GetMapping(value = { "view-saleorder-stateList" })
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
	/*
	 * Item auto search
	 */
		@SuppressWarnings("unchecked")
		@PostMapping(value = { "view-saleorder-item-get-customer-list" })
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

	//get Product Category Data List Modal

		@SuppressWarnings("unchecked")
		@PostMapping("/view-saleorder-get-total-list")
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
		
		
		// grt product by cat

		@SuppressWarnings("unchecked")

		@PostMapping(value = { "view-saleorder-item-product-by-cat" })
		public @ResponseBody JsonResponse<InventorySkuProductModel> getProductsByCat(Model model, @RequestBody String index,
				BindingResult result) {
			logger.info("Method : getProductsByCat starts");
			//logger.info(index);
			String indexValue = index.substring(0, index.length() - 1);

			JsonResponse<InventorySkuProductModel> res = new JsonResponse<InventorySkuProductModel>();

			try {
				res = restTemplate.getForObject(env.getSalesUrl() + "getProductsNByCat?id=" + indexValue, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (res.getMessage() != null) {

				res.setCode(res.getMessage());
				res.setMessage("Unsuccess");
			} else {
				res.setMessage("success");
			}
			//logger.info(res);
			logger.info("Method : getProductsByCat ends");
			return res;
		}
		
		/*
		 * Add
		 */
		@SuppressWarnings("unchecked")
		@PostMapping("view-saleorder-add")
		public @ResponseBody JsonResponse<Object> addsalenew(HttpSession session,
				@RequestBody List<SalesOrderNewModel>salesOrderNewModel ) {
			logger.info("Method : addsalenew starts");
			logger.info(salesOrderNewModel.toString());
			JsonResponse<Object> resp = new JsonResponse<Object>();
			List<SalesOrderNewModel> documentList = new ArrayList<SalesOrderNewModel>();
			List<InventoryVendorDocumentModel> docList = new ArrayList<InventoryVendorDocumentModel>();
			String userId = "";
			String dateFormat = "";
			String organization=""; 
			String orgDivision="";
			try {
				userId = (String) session.getAttribute("USER_ID");
				dateFormat =(String) session.getAttribute("DATEFORMAT");
				organization = (String) session.getAttribute("ORGANIZATION"); 
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {

			}
			for (SalesOrderNewModel a : salesOrderNewModel) {
				/*
				 * if (a.getOrderReceiveDate() != null && a.getOrderReceiveDate() != "") {
				 * a.setOrderReceiveDate(DateFormatter.inputDateFormat(a.getOrderReceiveDate(),
				 * dateFormat)); }
				 */
			
			 if (a.getExpectedShipmentDate() != null && a.getExpectedShipmentDate() != "")
			  { a.setExpectedShipmentDate(DateFormatter.inputDateFormat(a.
			  getExpectedShipmentDate(), dateFormat)); }
			 if (a.getOrderReceiveDate() != null && a.getOrderReceiveDate() != "")
			  { a.setOrderReceiveDate(DateFormatter.inputDateFormat(a.
					  getOrderReceiveDate(), dateFormat)); }
			 
			}
			for (SalesOrderNewModel m : salesOrderNewModel) {
				m.setQutCreatedBy(userId);
				m.setOrganization(organization);
				m.setOrgDivision(orgDivision);
				
			}
			for (InventoryVendorDocumentModel a : salesOrderNewModel.get(0).getDocumentList()) {
				logger.info("saddddddddddddddddd"+a);
				
				if (a.getImageNameEdit() != null && a.getImageNameEdit() != "") {
					a.setFileName(a.getImageNameEdit());
				} else {
					if (a.getFileName() != null && a.getFileName() != "") {
						String[] extension = a.getFileName().split("\\.");
						int lastindex = extension.length - 1;
							for (String s1 : a.getDocumentFile()) {
									try {
										byte[] bytes = Base64.getDecoder().decode(s1);
										logger.info("bytes"+s1);
										String imageName = saveAllMultiImages(bytes, extension[lastindex]);
										a.setFileName(imageName);
										
									} catch (Exception e) {
										e.printStackTrace();
										
									}
							}
					}
				}
			}
logger.info("salesOrderNewModel==="+salesOrderNewModel);
			try {
				resp = restTemplate.postForObject(env.getSalesUrl() + "addsalenew", salesOrderNewModel,
						JsonResponse.class);
				logger.info(salesOrderNewModel.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (resp.getMessage() != "" && resp.getMessage() != null) {
				resp.setCode(resp.getMessage());
				resp.setMessage("Unsuccess");
			} else {
				resp.setMessage("Success");
			}
			logger.info("a===" + resp);

			logger.info("Method : addsalenew ends");

			return resp;
		}
		
		
		
		public String saveAllMultiImages(byte[] imageBytes, String ext) {
			logger.info("Method : saveAllMultiImages starts");
			String imageName1 = null;
			try {
				if (imageBytes != null) {
					long nowTime = new Date().getTime();
					if (ext.contentEquals("jpeg")) {
						imageName1 = nowTime + ".jpg";
					} else {
						imageName1 = nowTime + "." + ext;
					}
				}
				Path path = Paths.get(env.getFileUploadDocumenttUrl() + imageName1);
				if (imageBytes != null) {
					Files.write(path, imageBytes);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method : saveAllMultiImages ends");
			return imageName1;
		}
		/*
		 * view
		 */
		@SuppressWarnings("unchecked")

		@GetMapping("view-saleorder-through-ajax")
		public @ResponseBody List<SalesOrderNewModel> viewsalesOrder(HttpSession session) {

			logger.info("Method :getAllsalesOrder starts");
			JsonResponse<List<SalesOrderNewModel>> resp = new JsonResponse<List<SalesOrderNewModel>>();
			String userId = "";
			String dateFormat = "";
			String organization=""; 
			String orgDivision="";
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

				resp = restTemplate.postForObject(env.getSalesUrl() + "getAllsalesOrder",empModel, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			ObjectMapper mapper = new ObjectMapper();

			List<SalesOrderNewModel> salesOrderNewModel = mapper.convertValue(resp.getBody(),
					new TypeReference<List<SalesOrderNewModel>>() {
					});
		
			for (SalesOrderNewModel a : salesOrderNewModel) {
				if (a.getQutValidDate() != null && a.getQutValidDate() != "") {
					a.setQutValidDate(DateFormatter.dateFormat(a.getQutValidDate(), dateFormat));
				}
				if (a.getOrderReceiveDate() != null && a.getOrderReceiveDate() != "") {
					a.setOrderReceiveDate(DateFormatter.dateFormat(a.getOrderReceiveDate(), dateFormat));
				}
				if (a.getExpectedShipmentDate() != null && a.getExpectedShipmentDate() != "") {
					a.setExpectedShipmentDate(DateFormatter.dateFormat(a.getExpectedShipmentDate(), dateFormat));
				}

				/*
				 * if (a.getOrderReceiveTime() != null && a.getOrderReceiveTime() != "") {
				 * a.setOrderReceiveTime(DateFormatter.dateFormat(a.getOrderReceiveTime(),
				 * dateFormat)); }
				 */
				  if (a.getQutUpdatedOn() != null && a.getQutUpdatedOn() != "") {
				  a.setQutUpdatedOn(DateFormatter.dateFormat(a.getQutUpdatedOn(), dateFormat));
				  }
				logger.info(salesOrderNewModel.toString()); 

			}

			resp.setBody(salesOrderNewModel);
			if (resp.getMessage() != "" && resp.getMessage() != null) {
				resp.setCode(resp.getMessage());
				resp.setMessage("Unsuccess");
			} else {
				resp.setMessage("Success");
			}

			logger.info("Method :getAllsalesOrder ends");
			logger.info("RESPONSEview" + resp);
			return resp.getBody();
		}
		
		
		/*
		* for editing using employee id
		*
		*
		*/
		@GetMapping(value = { "view-saleorder-edit-new" })
		public @ResponseBody List<SalesOrderNewModel> viewsalesOrdeerEdit(@RequestParam String id,
				HttpSession session) {
			logger.info("Method : viewsalesOrdeerEdit starts");
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
											String docPath = "<i class=\"fa fa-file-excel-o excel\" title= " + m.getFileName()+ "></i> ";
											m.setAction(docPath);
										}
										if (extension[1].equals("pdf")) {
											String docPath = " <i class=\"fa fa-file-pdf-o excel pdf\"   title=" + m.getFileName()+ " ;></i> ";
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
								logger.info("m.setAction"+m);
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			logger.info("Method : viewsalesOrdeerEdit ends");
			logger.info("edit@@@@@@@@" + productList);
			return productList;
		}
		
	//packing
		@GetMapping(value = { "view-saleorder-for-packing" })
		public @ResponseBody List<SalesOrderNewModel> viewsalesOrderForPacking(@RequestParam String id,String poidd,String noOfSO,
				HttpSession session) {
			logger.info("Method : viewsalesOrderForPacking starts");
			List<SalesOrderNewModel> productList = new ArrayList<SalesOrderNewModel>();
			if (id != null && id != "") {
				try {
					SalesOrderNewModel[] salesOrderNewModel = restTemplate.getForObject(
							env.getSalesUrl() + "viewsalesOrderForPacking?id=" + id+"&poidd="+poidd+"&noOfSO="+noOfSO, SalesOrderNewModel[].class);
					productList = Arrays.asList(salesOrderNewModel);
					productList.forEach(s -> s.setSlNo(s.getSlNo()));
					int count = 0;
					for (SalesOrderNewModel m : salesOrderNewModel) {
						count++;
						m.setSlNo(count);
						String dateFormat = (String) session.getAttribute("DATEFORMAT");
						if (m.getOrderReceiveDate() != null && m.getOrderReceiveDate() != "") {
							m.setOrderReceiveDate(DateFormatter.dateFormat(m.getOrderReceiveDate(), dateFormat));
						}
						if (m.getExpectedShipmentDate() != null && m.getExpectedShipmentDate() != "") {
							m.setExpectedShipmentDate(DateFormatter.dateFormat(m.getExpectedShipmentDate(), dateFormat));
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			logger.info("Method : viewsalesOrdeerEdit ends");
			logger.info("edit@@@@@@@@" + productList);
			return productList;
		}
		
/*
 * * Delete
 */
		
		@SuppressWarnings("unchecked")
		@PostMapping(value = "view-saleorder-delete")
		public @ResponseBody JsonResponse<Object> deletesalesOrder(
				@RequestBody SalesOrderNewModel salesOrderNewModel, HttpSession session) {
			logger.info("Method : deletesalesOrder starts");
			logger.info("SalesOrderNewModel"+salesOrderNewModel);
			JsonResponse<Object> resp = new JsonResponse<Object>();
			String userId = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
				salesOrderNewModel.setQutCreatedBy(userId);
			} catch (Exception e) {

			}
			try {

				resp = restTemplate.postForObject(env.getSalesUrl() + "deletesalesOrder",
						salesOrderNewModel, JsonResponse.class);
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
			logger.info("Method : deletesalesOrder Ends");
			return resp;
		}
		
		//Add New Customer
		@SuppressWarnings("unchecked")
		@PostMapping("view-saleorder-add-customer")
		public @ResponseBody JsonResponse<Object> addAccountCustomer(@RequestBody CustomerNewModel customerNewModel,
				HttpSession session) {

			logger.info("Method : addAccountCustomer starts");

			JsonResponse<Object> resp = new JsonResponse<Object>();
			logger.info("web AccountModel ======================" + customerNewModel);
			try {
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
				
				logger.info("created by id-------------------------------"+userId);

				customerNewModel.setCreatedBy(userId);
				customerNewModel.setOrganization(organization);
				customerNewModel.setOrgDivision(orgDivision);

				resp = restTemplate.postForObject(env.getSalesUrl() + "/addAccountCustomer", customerNewModel, JsonResponse.class);

			} catch (RestClientException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logger.info("Method : addAccountCustomer ends");

			return resp;
		}
		
		@SuppressWarnings("unchecked")

		@PostMapping("view-saleorder-add-cust-billingaddress")
		public @ResponseBody JsonResponse<Object> addbillingaddress(
				@RequestBody CustomerNewModel customerNewModel, HttpSession session) {
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
		
		
		
		/*
		 * @SuppressWarnings("unchecked")
		 * 
		 * @PostMapping("view-saleorder-add-cust-shippingaddress") public @ResponseBody
		 * JsonResponse<Object> addshippingaddress(
		 * 
		 * @RequestBody CustomerNewModel customerNewModel, HttpSession session) {
		 * logger.info("Method : addshippingaddress starts"); String userId = "";
		 * 
		 * try { userId = (String) session.getAttribute("USER_ID");
		 * 
		 * 
		 * } catch (Exception e) { e.printStackTrace(); }
		 * customerNewModel.setCreatedBy(userId);
		 * 
		 * logger.info("ADDDDATA" + customerNewModel);
		 * 
		 * 
		 * 
		 * JsonResponse<Object> resp = new JsonResponse<Object>();
		 * 
		 * 
		 * 
		 * try {
		 * 
		 * resp = restTemplate.postForObject(env.getSalesUrl() +
		 * "add-shippingaddress", customerNewModel, JsonResponse.class); } catch
		 * (RestClientException e) { e.printStackTrace(); }
		 * 
		 * if (resp.getMessage() != "" && resp.getMessage() != null) {
		 * resp.setCode(resp.getMessage()); resp.setMessage("Unsuccess"); } else {
		 * resp.setMessage("Success"); } logger.info("ADDDDDDD" + resp);
		 * logger.info("Method :addshippingaddress ends"); return resp; }
		 */
		
		@SuppressWarnings("unchecked")
		@GetMapping("view-saleorder-get-address")
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
		
		
		@SuppressWarnings("unchecked")
		@PostMapping(value = { "view-saleorder-get-salesperson-list" })
		public @ResponseBody JsonResponse<QuotationNewModel> getSalesPersonAutoSearchList(Model model,
				@RequestBody String searchValue, BindingResult result,HttpSession session) {
			logger.info("Method : getSalesPersonAutoSearchList starts");
				//logger.info("QuotationNewModel"+searchValue);
			JsonResponse<QuotationNewModel> res = new JsonResponse<QuotationNewModel>();
			String orgName = "";
			String orgDivision = "";
			try {

				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			try {
				res = restTemplate.getForObject(env.getSalesUrl()+ "getSalesPersonListByAutoSearch?id=" + searchValue+ "&org="
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
			logger.info("Method : getSalesPersonAutoSearchList ends"+res);
			return res;
		}
	
		
		
		
		
		@SuppressWarnings("unchecked")

		@PostMapping("view-saleorder-add-salesperson")
		public @ResponseBody JsonResponse<Object> addSalesPerson(
				@RequestBody QuotationNewModel quotationNewModel, HttpSession session) {
			logger.info("Method : addSalesPerson starts");
			String userId = "";
			String dateFormat = "";
			String organization=""; 
			String orgDivision="";
			try {
				userId = (String) session.getAttribute("USER_ID");
				dateFormat = (String) session.getAttribute("DATEFORMAT");
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
			quotationNewModel.setDobid(DateFormatter.inputDateFormat(quotationNewModel.getDobid(),dateFormat));
			

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
		@PostMapping(value = { "view-saleorder-get-tcs-list" })
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
		
		
		
		

		@SuppressWarnings("unchecked")

		@PostMapping("view-saleorder-add-tcs")
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

		@GetMapping(value = { "view-saleorder-get-insertedid" })
		public @ResponseBody JsonResponse<Object> getSOInsertedId() {
			JsonResponse<Object> res = new JsonResponse<Object>();
			try {
				res = restTemplate.getForObject(env.getSalesUrl() + "getSOInsertedId", JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (res.getMessage() != null) {
				res.setCode(res.getMessage());
				res.setMessage("Unsuccess");
			} else {
				res.setMessage("success");
			}
			logger.info("Method : getSOInsertedId ends");
			logger.info("assssssssssssssssssss"+res);
			return res;
		}
		@SuppressWarnings("unchecked")
		@PostMapping(value = { "view-saleorder-get-customer-listt" })
		public @ResponseBody JsonResponse<QuotationNewModel> getCustomerAutoSearchListt(Model model,
				@RequestBody String searchValue, BindingResult result,HttpSession session) {
			logger.info("Method : getCustomerAutoSearchList starts");

			JsonResponse<QuotationNewModel> res = new JsonResponse<QuotationNewModel>();
			String orgName = "";
			String orgDivision = "";
			try {
				
			
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
				
			} catch (Exception e) {

			}
			try {
				res = restTemplate.getForObject(env.getSalesUrl() + "getCustomerListByAutoSearch?id=" + searchValue+ "&org="
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

			logger.info("Method : getCustomerAutoSearchList ends");
			return res;
		}
		
		@SuppressWarnings("unchecked")
		@GetMapping(value = { "view-saleorder-salesPoList" })
		public @ResponseBody JsonResponse<Object> getSalesPoListt(@RequestParam String id) {
			logger.info("Method : getSalesPoListt starts" + id);
			JsonResponse<Object> res = new JsonResponse<Object>();
			try {
				res = restTemplate.getForObject(env.getSalesUrl() + "getSalesPoListt?id=" + id,
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
			logger.info("Method : getSalesPoListt ends");
			return res;
		}
		@SuppressWarnings("unchecked")
		@GetMapping("view-saleorder-po-wise")
		public @ResponseBody List<SalesOrderNewModel> viewsalesOrderPoWise(HttpSession session,@RequestParam String id) {

			logger.info("Method :viewsalesOrderPoWise starts");
			JsonResponse<List<SalesOrderNewModel>> resp = new JsonResponse<List<SalesOrderNewModel>>();
			String orgName = "";
			String orgDivision = "";
			try {

				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			} catch (Exception e) {

			}
			try {

				resp = restTemplate.getForObject(
						env.getSalesUrl() + "rest-viewsalesOrderPoWise?org=" + orgName + "&orgDiv=" + orgDivision+"&id="+id,
						JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			ObjectMapper mapper = new ObjectMapper();

			List<SalesOrderNewModel> soModel = mapper.convertValue(resp.getBody(),
					new TypeReference<List<SalesOrderNewModel>>() {
					});
			resp.setBody(soModel);
			if (resp.getMessage() != "" && resp.getMessage() != null) {
				resp.setCode(resp.getMessage());
				resp.setMessage("Unsuccess");
			} else {
				resp.setMessage("Success");
			}
			logger.info("Method :viewsalesOrderPoWise ends");
			return resp.getBody();
		}
		
		// Block Order
		

				@SuppressWarnings("unchecked")
				@GetMapping("view-saleorder-blockeOrder-th-ajax")
				public @ResponseBody JsonResponse<List<SalesOrderNewModel>> blockSaleOrderItem(HttpSession session,
						@RequestParam String blockeOrder, String salesOrder,String sku) {

					logger.info("Method : blockSaleOrderItem starts");
					JsonResponse<List<SalesOrderNewModel>> response = new JsonResponse<List<SalesOrderNewModel>>();
					try {
						response = restTemplate.getForObject(env.getSalesUrl() + "blockSaleOrderItem?blockeOrder="
								+ blockeOrder + "&salesOrder=" + salesOrder + "&sku=" + sku, JsonResponse.class);

					} catch (RestClientException e) {
						e.printStackTrace();
					}

					if(response.getCode().equals("success")) {
						response.setMessage("Success");
					} else {
						response.setCode(response.getMessage());
						response.setMessage("Unsuccess");
					}
					logger.info("response=====" + response);
					logger.info("Method : blockSaleOrderItem ends");
					return response;
				}
				
				// approve
				@SuppressWarnings("unchecked")
				@GetMapping("view-saleorder-approve-th-ajax")
				public @ResponseBody JsonResponse<List<SalesOrderNewModel>> approveSaleOrder(HttpSession session,
						@RequestParam String approveStatus, String salesOrder,String pendingQut) {

					logger.info("Method : approveSaleOrder starts");
					JsonResponse<List<SalesOrderNewModel>> response = new JsonResponse<List<SalesOrderNewModel>>();
					try {
						response = restTemplate.getForObject(env.getSalesUrl() + "approveSaleOrder?approveStatus="
								+ approveStatus + "&salesOrder=" + salesOrder + "&pendingQut=" + pendingQut, JsonResponse.class);

					} catch (RestClientException e) {
						e.printStackTrace();
					}

					if(response.getCode().equals("success")) {
						response.setMessage("Success");
					} else {
						response.setCode(response.getMessage());
						response.setMessage("Unsuccess");
					}
					logger.info("response=====" + response);
					logger.info("Method : approveSaleOrder ends");
					return response;
				}
				
				@SuppressWarnings({ "unchecked" })
				@PostMapping("/view-saleorder-add-cust-shippingaddress")
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

				
				@GetMapping("view-saleorder-shipping-address")
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
				
				@GetMapping("view-saleorder-shipping-dataedit")
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
				
// Add New Customer
				
				
				@SuppressWarnings("unchecked")
				@PostMapping("/view-saleorder-adds-customer")
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
				
				@SuppressWarnings("unchecked")
				@GetMapping("view-saleorder-shippingdetails")
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
				

				@SuppressWarnings({ "unchecked" })
				@PostMapping("/view-saleorder-save-shipping-address")
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

				@GetMapping("view-saleorder-address-delete")
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
				
				@SuppressWarnings("unchecked")
				@GetMapping("view-saleorder-edit-address")
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
				
				@SuppressWarnings("unchecked")
				@GetMapping(value = { "view-saleorder-get-sku-by-product" })
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
				@PostMapping(value = { "view-saleorder-get-product-details" })
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
				@PostMapping("view-saleorder-products-delete")
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
				
				@SuppressWarnings("unchecked")
				@PostMapping(value = { "view-saleorder-get-sku-details" })
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
				@GetMapping("view-saleorder-deletesku")
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
				
				@PostMapping("view-saleorder-delete-fileproduct")
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
				@PostMapping("/view-saleorder-upload-fileprofuct")
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
				
				/*
				 * @SuppressWarnings("unchecked")
				 * 
				 * @PostMapping("/view-saleorder-get-total-list") public @ResponseBody
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
				@PostMapping("/view-saleorder-products-save")
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

				@SuppressWarnings({ "unchecked" })
				@PostMapping("/view-saleorder-save-sku-dtls")
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
				@GetMapping(value = { "view-saleorder-get-sku-listing" })
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
				@PostMapping("view-saleorder-item-get-product-list")
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
			
					
}
