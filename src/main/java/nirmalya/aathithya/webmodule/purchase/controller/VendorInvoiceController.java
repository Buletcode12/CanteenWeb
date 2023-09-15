package nirmalya.aathithya.webmodule.purchase.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.FileUpload;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.master.model.EmpRoleModel;
import nirmalya.aathithya.webmodule.purchase.model.VendorInvoiceModel;

@Controller
@RequestMapping(value = "purchase/")
public class VendorInvoiceController {

	Logger logger = LoggerFactory.getLogger(VendorInvoiceController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	FileUpload fileUpload;

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@GetMapping("vendor-invoice")
	public String getInvoicePage(Model model, HttpSession session) {

		logger.info("Method : getInvoiceViewPage starts");
		try {
			DropDownModel[] dropDownModel = restTemplate.getForObject(env.getPurchaseUrl() + "getPaymentterm",
					DropDownModel[].class);
			List<DropDownModel> PaytermList = Arrays.asList(dropDownModel);
			model.addAttribute("PaytermList", PaytermList);
			System.out.println("PAYMENT" + PaytermList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : getInvoiceViewPage ends");
		return "purchase/vendor-invoice-details";
	}

	/*
	 * Add
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("vendor-invoice-add")
	public @ResponseBody JsonResponse<Object> addVendorInvoicenew(HttpSession session,
			@RequestBody List<VendorInvoiceModel> VendorInvoiceModel) {
		logger.info("Method : addVendorInvoicenew starts");
		System.out.println(VendorInvoiceModel);
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
		for (VendorInvoiceModel a : VendorInvoiceModel) {

			if (a.getInvoiceDate() != null && a.getInvoiceDate() != "") {
				a.setInvoiceDate(DateFormatter.inputDateFormat(a.getInvoiceDate(), dateFormat));
			}
			if (a.getDueDate() != null && a.getDueDate() != "") {
				a.setDueDate(DateFormatter.inputDateFormat(a.getDueDate(), dateFormat));
			}
		}
		for (VendorInvoiceModel m : VendorInvoiceModel) {
			m.setQutCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);
		}
		try {
			resp = restTemplate.postForObject(env.getPurchaseUrl() + "addVendorInvoicenew", VendorInvoiceModel,
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
		System.out.println("resp+++++++++++++++++++++++" + resp);

		logger.info("Method : addVendorInvoicenew ends");

		return resp;
	}
	/*
	 * view
	 */

	@SuppressWarnings("unchecked")

	@GetMapping("vendor-invoice-through-ajax")
	public @ResponseBody List<VendorInvoiceModel> viewVendorInvoice(HttpSession session) {

		logger.info("Method :viewVendorInvoice starts");
		JsonResponse<List<VendorInvoiceModel>> resp = new JsonResponse<List<VendorInvoiceModel>>();
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

			resp = restTemplate.postForObject(env.getPurchaseUrl() + "viewVendorInvoice", empModel,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();

		List<VendorInvoiceModel> VendorInvoiceModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<VendorInvoiceModel>>() {
				});

		for (VendorInvoiceModel a : VendorInvoiceModel) {
			a.setQuantitynew(a.getQuantity());
			if (a.getQutUpdatedOn() != null && a.getQutUpdatedOn() != "") {
				a.setQutUpdatedOn(DateFormatter.dateFormat(a.getQutUpdatedOn(), dateFormat));
			}
			System.out.println(VendorInvoiceModel);

		}

		resp.setBody(VendorInvoiceModel);
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method :viewVendorInvoice ends");
		System.out.println("RESPONSEview" + resp);
		return resp.getBody();
	}

	/*
	 * edit 
	 */

	@GetMapping(value = { "vendor-invoice-edit-new" })
	public @ResponseBody List<VendorInvoiceModel> viewVendorIvoiceEdit(@RequestParam String id, HttpSession session) {
		logger.info("Method : viewVendorIvoiceEdit starts");
		System.out.println(id);
		List<VendorInvoiceModel> productList = new ArrayList<VendorInvoiceModel>();
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

		if (id != null && id != "") {
			try {
				VendorInvoiceModel[] VendorInvoiceModel = restTemplate.getForObject(
						env.getPurchaseUrl() + "viewVendorIvoiceEdit?id=" + id +"&organization="
								+ organization + "&orgDivision=" + orgDivision,  VendorInvoiceModel[].class);

				productList = Arrays.asList(VendorInvoiceModel);

				productList.forEach(s -> s.setSlNo(s.getSlNo()));

				int count = 0;

				for (VendorInvoiceModel m : VendorInvoiceModel) {
					count++;
					m.setSlNo(count);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.info("Method : viewVendorIvoiceEdit ends");
		System.out.println("edit@@@@@@@@SSSSSSSSSSSSSSSSSSSSS" + productList);
		return productList;
	}

	/*
	 * delete
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "vendor-invoice-delete")
	public @ResponseBody JsonResponse<Object> deleteVendorInvoice(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : deleteVendorInvoice function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

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

		try {
			res = restTemplate.getForObject(env.getPurchaseUrl() + "deleteVendorInvoice?id=" + id+"&organization="
					+ organization + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteVendorInvoice function Ends");

		return res;
	}

}
