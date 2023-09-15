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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.procurment.model.InventoryVendorDocumentModel;
import nirmalya.aathithya.webmodule.purchase.model.PurchaseOrderModel;

@Controller

@RequestMapping(value = { "purchase/" })

public class GateReceivedController {
	
	Logger logger = LoggerFactory.getLogger(GateReceivedController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "gate-received" })
	
	public String awaitingQaRequest(Model model, HttpSession session) {
		logger.info("Method :awaitingQaRequest starts");

		logger.info("Method : awaitingQaRequest ends");

		return "purchase/gate-received";
	}
	
	
	// View Gate Received Data.

		@SuppressWarnings("unchecked")

		@GetMapping("gate-received-view")
		public @ResponseBody Object gateReceivedDataView(HttpSession session) {

			logger.info("Method :gateReceivedDataView starts");
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

				resp = restTemplate.getForObject(env.getPurchaseUrl() + "rest-gateReceivedDataView?orgName=" + orgName
						+ "&orgDivision=" + orgDivision, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("Method :gateReceivedDataView ends");
			
			logger.info(">>>-----"+resp);

			return resp;
		}
		
		@SuppressWarnings("unchecked")
		@GetMapping("gate-received-detls")
		public @ResponseBody Object gateReceivedDtls(@RequestParam String id,@RequestParam String po, HttpSession session) {
			
			logger.info("Method :gateReceivedDtls starts");
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
						env.getPurchaseUrl() + "rest-gateReceivedDtls?id=" + id +"&po=" + po +"&orgName=" +orgName+"&orgDivision=" +orgDivision ,
						JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("Child Data>>>-----" + resp);
			logger.info("Method :gateReceivedDtls ends");

			return resp;
		}
		
		
		@GetMapping(value = { "gate-received-GRNdata" })
		public @ResponseBody List<PurchaseOrderModel> getGRNdata(@RequestParam String id, String sku, String gatePass, HttpSession session) {
			logger.info("Method : getGRNdata starts");
			String orgName = "";
			String orgDivision = "";
			try {

				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {

			}

			List<PurchaseOrderModel> productList = new ArrayList<PurchaseOrderModel>();
			List<InventoryVendorDocumentModel> documentList = new ArrayList<InventoryVendorDocumentModel>();
			if (id != null && id != "") {
				logger.info("IDD" + id);
				try {
					PurchaseOrderModel[] purchaseOrderModel = restTemplate.getForObject(env.getPurchaseUrl()
							+ "getGRNdata?id=" + id + "&sku=" + sku + "&gatePass="+ gatePass + "&org=" + orgName + "&orgDiv=" + orgDivision,
							PurchaseOrderModel[].class);

					productList = Arrays.asList(purchaseOrderModel);

					productList.forEach(s -> s.setSlNo(s.getSlNo()));

					int count = 0;

					for (PurchaseOrderModel m : purchaseOrderModel) {
						// m.setQuantitynew(m.getQuantity());
						count++;
						m.setSlNo(count);
						String dateFormat = (String) session.getAttribute("DATEFORMAT");

						if (m.getUpdatedOn() != null && m.getUpdatedOn() != "") {
							m.setUpdatedOn(DateFormatter.dateFormat(m.getUpdatedOn(), dateFormat));

						}
						if (m.getQutValidDate() != null && m.getQutValidDate() != "") {
							m.setQutValidDate(DateFormatter.dateFormat(m.getQutValidDate(), dateFormat));

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
			logger.info("Method : getGRNdata ends");
			logger.info("edit@@@@@@@@" + productList);
			return productList;
		}
		
		// Delete Entry Data.
		
		
		@SuppressWarnings("unchecked")
		@PostMapping("gate-received-delete")
		public @ResponseBody JsonResponse<Object> deleteGatepassEntry(@RequestParam String id, Model model,
				HttpSession session) {
			logger.info("Method : deleteGatepassEntry function starts");

			JsonResponse<Object> res = new JsonResponse<Object>();

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
				res = restTemplate.getForObject(env.getGatepassUrl() + "deleteGatepassEntry?id=" + id + "&org=" + orgName
						+ "&orgDivision=" + orgDivision, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			String message = res.getMessage();
			if (message != null && message != "") {

			} else {
				res.setMessage("Success");
			}
			logger.info("Method : deleteGatepassEntry function Ends");

			logger.info("RESPPPPPPP" + res);
			return res;
		}

}
