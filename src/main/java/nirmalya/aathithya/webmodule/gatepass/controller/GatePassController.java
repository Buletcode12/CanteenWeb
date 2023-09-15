package nirmalya.aathithya.webmodule.gatepass.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.gatepass.model.GatePassDetailsModel;
import nirmalya.aathithya.webmodule.master.model.EmpRoleModel;
import nirmalya.aathithya.webmodule.procurment.model.InventoryGatePassEntryModel;
import nirmalya.aathithya.webmodule.procurment.model.InventorySkuProductModel;
import nirmalya.aathithya.webmodule.procurment.model.InventoryVendorDocumentModel;

@Controller
@RequestMapping(value = "gatepass/")
public class GatePassController {

	Logger logger = LoggerFactory.getLogger(GatePassController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	private static final String USER_Id = "USER_ID";

	@GetMapping("/gate-pass")
	public String gatePassEntry(Model model, HttpSession session) {
		logger.info("Method : gatePass add starts");

		// String userId = "";
		String org = "";
		String orgDiv = "";

		try {
			// userId = (String) session.getAttribute("USER_ID");
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		try {
			// String userId = (String) session.getAttribute(USER_Id);
			DropDownModel[] dd = restClient.getForObject(
					env.getGatepassUrl() + "get-purchseOrderId-list?org=" + org + "&orgDiv=" + orgDiv,
					DropDownModel[].class);
			List<DropDownModel> pOrderIdList = Arrays.asList(dd);

			model.addAttribute("pOrderIdList", pOrderIdList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			// String userId = (String) session.getAttribute(USER_Id);
			DropDownModel[] dd = restClient.getForObject(env.getGatepassUrl() + "get-purchseOrderIdForExit-list",
					DropDownModel[].class);
			List<DropDownModel> pOrderIdListForExit = Arrays.asList(dd);

			model.addAttribute("pOrderIdListForExit", pOrderIdListForExit);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			// String userId = (String) session.getAttribute(USER_Id);
			DropDownModel[] dd = restClient.getForObject(env.getGatepassUrl() + "get-noOfwheeler-list",
					DropDownModel[].class);
			List<DropDownModel> noOfwheelerList = Arrays.asList(dd);

			model.addAttribute("noOfwheelerList", noOfwheelerList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] uom = restTemplate.getForObject(env.getMasterUrl() + "getUOMListForProduct",
					DropDownModel[].class);
			List<DropDownModel> unitList = Arrays.asList(uom);

			model.addAttribute("unitList", unitList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : gatePass ends");
		return "gatepass/gatePassDetails";
	}

	/*
	 * save image
	 */

	@PostMapping("gate-pass-upload-file")
	public @ResponseBody JsonResponse<Object> uploadFile(@RequestParam("file") MultipartFile inputFile,
			HttpSession session) {
		logger.info("Method : uploadFile controller function 'post-mapping' starts");
		System.out.println("MultipartFile" + inputFile);
		JsonResponse<Object> response = new JsonResponse<Object>();

		try {
			response.setMessage(inputFile.getOriginalFilename());
			session.setAttribute("quotationPFile", inputFile);

		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : uploadFile controller function 'post-mapping' ends");
		return response;
	}

	// gate-pass add

	@SuppressWarnings("unchecked")
	@PostMapping("gate-pass-entry-add")
	public @ResponseBody JsonResponse<Object> addGatepassEntry(HttpSession session,
			@RequestBody List<GatePassDetailsModel> gatePassDetailsModel) {
		logger.info("Method : addGatepassEntry starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDiv = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		for (GatePassDetailsModel m : gatePassDetailsModel) {
			m.setCreatedBy(userId);
			m.setOrganizationName(orgName);
			m.setOrganizationDivision(orgDiv);
		}
		MultipartFile inputFile = (MultipartFile) session.getAttribute("gateDocFile");
		System.out.println("inputFile=====" + inputFile);
		byte[] bytes;
		String imageName = null;
		if (inputFile != null) {
			try {
				bytes = inputFile.getBytes();
				String[] fileType = inputFile.getContentType().split("/");
				imageName = saveAllMultiImages(bytes, fileType[1]);
				System.out.println("imageName====" + imageName);
				gatePassDetailsModel.get(0).setImage(imageName);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		System.out.println("gatePassDetailsModel=====" + gatePassDetailsModel);
		try {
			resp = restTemplate.postForObject(env.getGatepassUrl() + "rest-add-gatepass-entry", gatePassDetailsModel,
					JsonResponse.class);
			ObjectMapper mapper = new ObjectMapper();

			List<GatePassDetailsModel> quotation = mapper.convertValue(resp.getBody(),
					new TypeReference<List<GatePassDetailsModel>>() {
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
			session.removeAttribute("gateDocFile");
		}

		logger.info("Method : addGatepassEntry ends");

		return resp;
	}

	@PostMapping("/gate-pass-doc-upload-file")
	public @ResponseBody JsonResponse<Object> uploadDocFile(@RequestParam("file") MultipartFile inputFile,
			HttpSession session) {
		logger.info("Method : gate uploadDocFile controller  starts");

		JsonResponse<Object> response = new JsonResponse<Object>();

		try {
			response.setMessage(inputFile.getOriginalFilename());
			session.setAttribute("gateDocFile", inputFile);

		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : gate uploadDocFile controller ' ends");
		return response;
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

	// View
	@SuppressWarnings("unchecked")

	@GetMapping("gate-pass-get-gatepassentry-list")
	public @ResponseBody List<InventoryGatePassEntryModel> viewgatepassEntry(HttpSession session) {

		logger.info("Method :viewgatepassEntry starts");
		JsonResponse<List<InventoryGatePassEntryModel>> resp = new JsonResponse<List<InventoryGatePassEntryModel>>();

		try {

			resp = restTemplate.getForObject(env.getGatepassUrl() + "getAllgatepassEntry", JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();

		List<InventoryGatePassEntryModel> inventoryGatePassEntryModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<InventoryGatePassEntryModel>>() {
				});
		String dateFormat = "";
		try {
			dateFormat = (String) session.getAttribute("DATEFORMAT");
		} catch (Exception e) {

		}
		for (InventoryGatePassEntryModel a : inventoryGatePassEntryModel) {
			// a.setQuantitynew(a.getQuantity());
			if (a.getEntrydate() != null && a.getEntrydate() != "") {
				a.setEntrydate(DateFormatter.dateFormat(a.getEntrydate(), dateFormat));
			}

		}

		resp.setBody(inventoryGatePassEntryModel);
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method :viewgatepassEntry ends");
		return resp.getBody();
	}
	// view

	@SuppressWarnings("unchecked")
	@GetMapping("gate-pass-entry-view")
	public @ResponseBody List<GatePassDetailsModel> viewGatePassIn(HttpSession session) {

		logger.info("Method : viewGatePassIn starts");
		JsonResponse<List<GatePassDetailsModel>> resp = new JsonResponse<List<GatePassDetailsModel>>();

		String userid = "";
		String organization = "";
		String orgDivision = "";
		try {
			userid = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}

		EmpRoleModel empModel = new EmpRoleModel();

		empModel.setUserId(userid);
		empModel.setType("WEB");
		empModel.setOrganization(organization);
		empModel.setOrgDivision(orgDivision);

		try {
			resp = restTemplate.postForObject(env.getGatepassUrl() + "viewGatePassEntry", empModel, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		List<GatePassDetailsModel> gatePassDetailsModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<GatePassDetailsModel>>() {
				});
		String dateFormat = "";

		try {
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			if (dateFormat == null) {
				dateFormat = "yyyy-MM-dd";
			}

		} catch (Exception e) {
		}

		if (gatePassDetailsModel != null)
			for (GatePassDetailsModel a : gatePassDetailsModel) {
				if (a.getEntrydate() != null && a.getEntrydate() != "") {
					a.setEntrydate(DateFormatter.dateFormat(a.getEntrydate(), dateFormat));
				}

			}

		logger.info("Method : viewGatePassIn ends");
		return gatePassDetailsModel;

	}

	// edit

	@GetMapping(value = { "gate-pass-entry-edit" })
	public @ResponseBody List<GatePassDetailsModel> editGatePassEntryData(@RequestParam String id,
			HttpSession session) {
		logger.info("Method : editGatePassEntryData starts");
		String organization = "";
		String orgDivision = "";
		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}

		List<GatePassDetailsModel> productList = new ArrayList<GatePassDetailsModel>();
		List<InventoryVendorDocumentModel> documentList = new ArrayList<InventoryVendorDocumentModel>();
		if (id != null && id != "") {
			try {
				GatePassDetailsModel[] gatePassDetailsModel = restTemplate
						.getForObject(env.getGatepassUrl() + "editGatePassEntryData?id=" + id + "&organization="
								+ organization + "&orgDivision=" + orgDivision, GatePassDetailsModel[].class);

				productList = Arrays.asList(gatePassDetailsModel);

				productList.forEach(s -> s.setSlNo(s.getSlNo()));

				int count = 0;

				for (GatePassDetailsModel m : gatePassDetailsModel) {
					// m.setQuantitynew(m.getQuantity());
					count++;
					m.setSlNo(count);
					String dateFormat = (String) session.getAttribute("DATEFORMAT");

					if (m.getEntrydate() != null && m.getEntrydate() != "") {
						m.setEntrydate(DateFormatter.dateFormat(m.getEntrydate(), dateFormat));
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
		logger.info("Method : editGatePassEntryData ends");
		System.err.println("DATA" + productList);
		return productList;
	}
	// gate out add

	@SuppressWarnings("unchecked")
	@PostMapping("gate-pass-exit-add")
	public @ResponseBody JsonResponse<Object> addGatepassExit(HttpSession session,
			@RequestBody List<GatePassDetailsModel> gatePassDetailsModel) {
		logger.info("Method : addGatepassExit starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDiv = "";
		System.out.println("gatePassDetailsModel435=====" + gatePassDetailsModel);
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		for (GatePassDetailsModel m : gatePassDetailsModel) {
			m.setCreatedBy(userId);
			m.setOrganizationName(orgName);
			m.setOrganizationDivision(orgDiv);
		}
		MultipartFile inputFile = (MultipartFile) session.getAttribute("gateDocFile");
		System.out.println("inputFile=====" + inputFile);
		byte[] bytes;
		String imageName = null;
		if (inputFile != null) {
			try {
				bytes = inputFile.getBytes();
				String[] fileType = inputFile.getContentType().split("/");
				imageName = saveAllMultiImages(bytes, fileType[1]);
				System.out.println("imageName====" + imageName);
				gatePassDetailsModel.get(0).setImage(imageName);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		System.out.println("gatePassDetailsModel=====" + gatePassDetailsModel);
		try {
			resp = restTemplate.postForObject(env.getGatepassUrl() + "rest-add-gatepass-exit", gatePassDetailsModel,
					JsonResponse.class);
			ObjectMapper mapper = new ObjectMapper();

			List<GatePassDetailsModel> quotation = mapper.convertValue(resp.getBody(),
					new TypeReference<List<GatePassDetailsModel>>() {
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
			session.removeAttribute("gateDocFile");
		}

		logger.info("Method : addGatepassExit ends");

		return resp;
	}

	// view for gate out

	@SuppressWarnings("unchecked")
	@GetMapping("gate-pass-exit-view")
	public @ResponseBody List<GatePassDetailsModel> viewGatePassExit(HttpSession session) {

		logger.info("Method : viewGatePassExit starts");
		JsonResponse<List<GatePassDetailsModel>> resp = new JsonResponse<List<GatePassDetailsModel>>();

		String userid = "";
		String organization = "";
		String orgDivision = "";
		try {
			userid = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}

		EmpRoleModel empModel = new EmpRoleModel();

		empModel.setUserId(userid);
		empModel.setType("WEB");
		empModel.setOrganization(organization);
		empModel.setOrgDivision(orgDivision);

		try {
			resp = restTemplate.postForObject(env.getGatepassUrl() + "viewGatePassExit", empModel, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		List<GatePassDetailsModel> gatePassDetailsModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<GatePassDetailsModel>>() {
				});
		String dateFormat = "";

		try {
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			if (dateFormat == null) {
				dateFormat = "yyyy-MM-dd";
			}

		} catch (Exception e) {
		}

		if (gatePassDetailsModel != null)
			for (GatePassDetailsModel a : gatePassDetailsModel) {
				if (a.getExitDate() != null && a.getExitDate() != "") {
					a.setExitDate(DateFormatter.dateFormat(a.getExitDate(), dateFormat));
				}

			}

		logger.info("Method : viewGatePassExit ends");
		return gatePassDetailsModel;

	}

	// edit gate out

	@GetMapping(value = { "gate-pass-exit-edit" })
	public @ResponseBody List<GatePassDetailsModel> editGatePassExit(@RequestParam String id, HttpSession session) {
		logger.info("Method : editGatePassExit starts");
		String organization = "";
		String orgDivision = "";
		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}

		List<GatePassDetailsModel> productList = new ArrayList<GatePassDetailsModel>();
		List<InventoryVendorDocumentModel> documentList = new ArrayList<InventoryVendorDocumentModel>();
		if (id != null && id != "") {
			try {
				GatePassDetailsModel[] gatePassDetailsModel = restTemplate.getForObject(env.getGatepassUrl()
						+ "editGatePassExit?id=" + id + "&organization=" + organization + "&orgDivision=" + orgDivision,
						GatePassDetailsModel[].class);

				productList = Arrays.asList(gatePassDetailsModel);

				productList.forEach(s -> s.setSlNo(s.getSlNo()));

				int count = 0;

				for (GatePassDetailsModel m : gatePassDetailsModel) {
					// m.setQuantitynew(m.getQuantity());
					count++;
					m.setSlNo(count);
					String dateFormat = (String) session.getAttribute("DATEFORMAT");

					if (m.getExitDate() != null && m.getExitDate() != "") {
						m.setExitDate(DateFormatter.dateFormat(m.getExitDate(), dateFormat));
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
		logger.info("Method : editGatePassExit ends");
		System.err.println("DATA" + productList);
		return productList;
	}

	// item against po id

	@GetMapping(value = { "gate-pass-getVendorandItemDetails" })
	public @ResponseBody List<GatePassDetailsModel> getVendorandItemDetails(@RequestParam String id,
			HttpSession session) {
		logger.info("Method : getVendorandItemDetails starts");
		List<GatePassDetailsModel> productList = new ArrayList<GatePassDetailsModel>();
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		System.out.println("FF" + orgName);
		System.out.println("FF1" + orgDivision);
		if (id != null && id != "") {

			try {
				GatePassDetailsModel[] GatePassDetailsModel = restTemplate.getForObject(env.getGatepassUrl()
						+ "getVendorandItemDetails?id=" + id + "&org=" + orgName + "&orgDivision=" + orgDivision,
						GatePassDetailsModel[].class);
				productList = Arrays.asList(GatePassDetailsModel);
				productList.forEach(s -> s.setSlNo(s.getSlNo()));
				int count = 0;
				for (GatePassDetailsModel m : GatePassDetailsModel) {
					count++;
					m.setSlNo(count);

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.info("Method : getVendorandItemDetails ends");
		return productList;
	}
	// get item details for exit

	@GetMapping(value = { "gate-pass-getItemDetailsForExit" })
	public @ResponseBody List<GatePassDetailsModel> getItemDetailsForExit(@RequestParam String id,
			HttpSession session) {
		logger.info("Method : getItemDetailsForExit starts");
		List<GatePassDetailsModel> productList = new ArrayList<GatePassDetailsModel>();
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		System.out.println("FF" + orgName);
		System.out.println("FF1" + orgDivision);
		if (id != null && id != "") {

			try {
				GatePassDetailsModel[] GatePassDetailsModel = restTemplate.getForObject(env.getGatepassUrl()
						+ "getItemDetailsForExit?id=" + id + "&org=" + orgName + "&orgDivision=" + orgDivision,
						GatePassDetailsModel[].class);
				productList = Arrays.asList(GatePassDetailsModel);
				productList.forEach(s -> s.setSlNo(s.getSlNo()));
				int count = 0;
				for (GatePassDetailsModel m : GatePassDetailsModel) {
					count++;
					m.setSlNo(count);

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.info("Method : getItemDetailsForExit ends");
		return productList;
	}

	// delete for gate-in
	@SuppressWarnings("unchecked")
	@PostMapping("gate-pass-entry-delete")
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

		 res.getMessage();
		/*
		 * if (message != null && message != "") {
		 * 
		 * } else { res.setMessage("Success"); }
		 */
		logger.info("Method : deleteGatepassEntry function Ends");

		System.out.println("RESPPPPPPP" + res);
		return res;
	}

	// delete for gate-out

	@SuppressWarnings("unchecked")
	@PostMapping("gate-pass-exit-delete")
	public @ResponseBody JsonResponse<Object> deleteGatepassExit(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : deleteGatepassExit function starts");

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
			res = restTemplate.getForObject(env.getGatepassUrl() + "deleteGatepassExit?id=" + id + "&org=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteGatepassExit function Ends");

		System.out.println("RESPPPPPPP" + res);
		return res;
	}

	// approve for gate-in

	@SuppressWarnings("unchecked")
	@GetMapping("gate-pass-entry-approve-th-ajax")
	public @ResponseBody JsonResponse<DropDownModel> approveGatepassEntry(HttpSession session,
			@RequestParam String approveStatus, String getPassEntryId) {

		logger.info("Method : approveGatepassEntry starts");
		JsonResponse<DropDownModel> response = new JsonResponse<DropDownModel>();
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			response = restTemplate.getForObject(
					env.getGatepassUrl() + "approveGatepassEntry?approveStatus=" + approveStatus + "&getPassEntryId="
							+ getPassEntryId + "&org=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (response.getMessage() != "" && response.getMessage() != null) {
			response.setCode(response.getMessage());
			response.setMessage("Unsuccess");
		} else {
			response.setMessage("Success");
		}

		System.out.println("response=====" + response);
		logger.info("Method : approveGatepassEntry ends");
		return response;
	}

	// approve for gate-out

	@SuppressWarnings("unchecked")
	@GetMapping("gate-pass-exit-approve-th-ajax")
	public @ResponseBody JsonResponse<DropDownModel> approveGatepassExit(HttpSession session,
			@RequestParam String approveStatus, String getPassExitId) {

		logger.info("Method : approveGatepassExit starts");
		JsonResponse<DropDownModel> response = new JsonResponse<DropDownModel>();
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			response = restTemplate.getForObject(
					env.getGatepassUrl() + "approveGatepassExit?approveStatus=" + approveStatus + "&getPassExitId="
							+ getPassExitId + "&org=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (response.getMessage() != "" && response.getMessage() != null) {
			response.setCode(response.getMessage());
			response.setMessage("Unsuccess");
		} else {
			response.setMessage("Success");
		}

		System.out.println("response=====" + response);
		logger.info("Method : approveGatepassExit ends");
		return response;
	}
	/*
	 * Item auto search
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "gate-pass-get-item-list" })
	public @ResponseBody JsonResponse<InventorySkuProductModel> getItemAutoSearchNewList(Model model,
			@RequestBody String searchValue, HttpSession session) {
		logger.info("Method : getItemAutoSearchNewList starts");
		String org = "";
		String orgDiv = "";
		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		JsonResponse<InventorySkuProductModel> res = new JsonResponse<InventorySkuProductModel>();

		try {
			res = restTemplate.getForObject(env.getGatepassUrl() + "getItemAutoSearchListforgate?id=" + searchValue
					+ "&org=" + org + "&orgDiv=" + orgDiv, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {

			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		System.out.println("RESPONSE@@" + res);
		logger.info("Method : getItemAutoSearchNewList ends");
		return res;
	}

	/*
	 * Item auto search for Fg
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "gate-pass-get-item-listForFg" })
	public @ResponseBody JsonResponse<InventorySkuProductModel> getItemAutoSearchNewListForFg(Model model,
			@RequestBody String searchValue, HttpSession session) {
		logger.info("Method : getItemAutoSearchNewListForFg starts");
		JsonResponse<InventorySkuProductModel> res = new JsonResponse<InventorySkuProductModel>();
		String org = "";
		String orgDiv = "";
		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			res = restTemplate.getForObject(env.getGatepassUrl() + "getItemAutoSearchNewListForFg?id=" + searchValue
					+ "&org=" + org + "&orgDiv=" + orgDiv, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		System.out.println("RESPONSE@@" + res);
		logger.info("Method : getItemAutoSearchNewListForFg ends");
		return res;
	}

	//  Autosearch
	@GetMapping("gate-pass-get-transport-list")
	public @ResponseBody Object gettransportAutoSearchList(@RequestParam String searchValue,
			HttpSession session) {

		logger.info("Method :gettransportAutoSearchList starts");
		@SuppressWarnings("rawtypes")
		JsonResponse resp = new JsonResponse();
		String org = "";
		String orgDiv = "";

		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(
					env.getGatepassUrl() + "gettransportAutoSearchList?id=" + searchValue + "&org=" + org + "&orgDiv=" + orgDiv, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
//  Autosearch Driver list
	@GetMapping("gate-pass-get-driver-list")
	public @ResponseBody Object getdriverAutoSearchList(@RequestParam String searchValue,
			HttpSession session) {

		logger.info("Method :getdriverAutoSearchList starts");
		@SuppressWarnings("rawtypes")
		JsonResponse resp = new JsonResponse();
		String org = "";
		String orgDiv = "";

		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(
					env.getGatepassUrl() + "getdriverAutoSearchList?id=" + searchValue + "&org=" + org + "&orgDiv=" + orgDiv, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

	//  Autosearch
	@GetMapping("gate-pass-get-vehicle-list")
	public @ResponseBody Object getvehicleAutoSearchList(@RequestParam String searchValue,
			HttpSession session) {

		logger.info("Method :getvehicleAutoSearchList starts");
		@SuppressWarnings("rawtypes")
		JsonResponse resp = new JsonResponse();
		String org = "";
		String orgDiv = "";

		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(
					env.getGatepassUrl() + "getvehicleAutoSearchList?id=" + searchValue + "&org=" + org + "&orgDiv=" + orgDiv, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

	//  Autosearch
	@GetMapping("gate-pass-get-depo-list")
	public @ResponseBody Object getdepoAutoSearchList(@RequestParam String searchValue,
			HttpSession session) {

		logger.info("Method :getdepoAutoSearchList starts");
		@SuppressWarnings("rawtypes")
		JsonResponse resp = new JsonResponse();
		String org = "";
		String orgDiv = "";

		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		try {
			resp = restTemplate.getForObject(
					env.getGatepassUrl() + "getdepoAutoSearchList?id=" + searchValue + "&org=" + org + "&orgDiv=" + orgDiv, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

	//  Autosearch
	@GetMapping("gate-pass-get-visit-list")
	public @ResponseBody Object getvisitList(@RequestParam String searchValue,
			HttpSession session) {

		logger.info("Method :getvisitList starts");
		@SuppressWarnings("rawtypes")
		JsonResponse resp = new JsonResponse();
		String org = "";
		String orgDiv = "";

		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(
					env.getGatepassUrl() + "getvisitList?id=" + searchValue + "&org=" + org + "&orgDiv=" + orgDiv, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
}
