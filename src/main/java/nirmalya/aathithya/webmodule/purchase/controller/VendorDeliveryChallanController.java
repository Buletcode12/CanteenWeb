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

import nirmalya.aathithya.webmodule.common.utils.ActivitylogModel;
import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.procurment.model.InventoryVendorDocumentModel;
import nirmalya.aathithya.webmodule.purchase.model.VendorDeliveryChallanModel;

/*
 * @author NirmalyaLabs
 *
 */
@Controller
@RequestMapping(value = "purchase/")
public class VendorDeliveryChallanController {
	Logger logger = LoggerFactory.getLogger(VendorDeliveryChallanController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("vendor-delivery-challan")
	public String generateDeliveryChallan(Model model, HttpSession session) {

		logger.info("Method : generatePo starts");

		/**
		 * get DropDown value for Requisition Type
		 *
		 */

		try {
			DropDownModel[] dropDownModel = restTemplate.getForObject(env.getPurchaseUrl() + "getCarrierList",
					DropDownModel[].class);
			List<DropDownModel> CarrierLists = Arrays.asList(dropDownModel);
			model.addAttribute("CarrierLists", CarrierLists);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : generatePo ends");
		return "purchase/vendor-deliveryChallan";

	}

	/*
	 * Add
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("vendor-delivery-challan-add")
	public @ResponseBody JsonResponse<Object> addvendorDeliveryChallan(HttpSession session,
			@RequestBody List<VendorDeliveryChallanModel> VendorDeliveryChallanModel) {
		logger.info("Method : addvendorDeliveryChallan starts");

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

		for (VendorDeliveryChallanModel a : VendorDeliveryChallanModel) {
			if (a.getEbillDate() != null && a.getEbillDate() != "") {
				a.setEbillDate(DateFormatter.inputDateFormat(a.getEbillDate(), dateFormat));
			}
		}

		for (VendorDeliveryChallanModel m : VendorDeliveryChallanModel) {
			m.setQutCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);

		}
		try {

			resp = restTemplate.postForObject(env.getPurchaseUrl() + "addvendorDeliveryChallan",
					VendorDeliveryChallanModel, JsonResponse.class);
			ObjectMapper mapper = new ObjectMapper();

			List<VendorDeliveryChallanModel> quotation = mapper.convertValue(resp.getBody(),
					new TypeReference<List<VendorDeliveryChallanModel>>() {
					});

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

		logger.info("Method : addvendorDeliveryChallan ends");

		return resp;
	}

	/*
	 * view
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("vendor-delivery-challan-view-ajax")
	public @ResponseBody List<VendorDeliveryChallanModel> viewvendordeliveryChallan(HttpSession session) {

		logger.info("Method :viewvendordeliveryChallan starts");
		JsonResponse<List<VendorDeliveryChallanModel>> resp = new JsonResponse<List<VendorDeliveryChallanModel>>();
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}
		try {

			resp = restTemplate.getForObject(
					env.getPurchaseUrl() + "rest-viewvendordeliveryChallan?org=" + orgName + "&orgDiv=" + orgDivision+ "&userId=" + userId , 
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();

		List<VendorDeliveryChallanModel> VendorDeliveryChallanModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<VendorDeliveryChallanModel>>() {
				});

		resp.setBody(VendorDeliveryChallanModel);
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method :viewvendordeliveryChallan ends");

		return resp.getBody();
	}

	/*
	 * edit Delivery Challan
	 */

	@GetMapping(value = { "vendor-delivery-challan-edit-new" })
	public @ResponseBody List<VendorDeliveryChallanModel> viewDeliveryChallanEdit(@RequestParam String id,
			HttpSession session) {
		logger.info("Method : viewDeliveryChallanEdit starts");

		List<VendorDeliveryChallanModel> productList = new ArrayList<VendorDeliveryChallanModel>();
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}
		if (id != null && id != "") {
			
			try {
				VendorDeliveryChallanModel[] vendorDeliveryChallanModel = restTemplate.getForObject(
						env.getPurchaseUrl() + "viewDeliveryChallanEdit?id=" + id +"&orgName=" + orgName+ "&orgDivision=" + orgDivision, VendorDeliveryChallanModel[].class);

				productList = Arrays.asList(vendorDeliveryChallanModel);

				productList.forEach(s -> s.setSlNo(s.getSlNo()));

				int count = 0;

				for (VendorDeliveryChallanModel m : vendorDeliveryChallanModel) {
					// m.setQuantitynew(m.getQuantity());
					count++;
					m.setSlNo(count);
					String dateFormat = (String) session.getAttribute("DATEFORMAT");
					if (m.getEbillDate() != null && m.getEbillDate() != "") {
						m.setEbillDate(DateFormatter.dateFormat(m.getEbillDate(), dateFormat));
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.info("Method : viewDeliveryChallanEdit ends");

		return productList;
	}

	/*
	 * delete
	 */
	
	
	@SuppressWarnings("unchecked")
	@PostMapping("vendor-delivery-challan-delete")
	public @ResponseBody JsonResponse<Object> deleteDeliveryChallan(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : deleteDeliveryChallan function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			res = restTemplate.getForObject(env.getPurchaseUrl() + "deleteDeliveryChallan?id=" + id,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteDeliveryChallan function Ends");

		return res;
	}

}
