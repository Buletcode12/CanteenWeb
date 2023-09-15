package nirmalya.aathithya.webmodule.procurment.controller;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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

import nirmalya.aathithya.webmodule.common.utils.ActivitylogModel;
import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EmailAttachmentSender;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.FileUpload;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.MailService;
import nirmalya.aathithya.webmodule.employee.model.ManageEmployeeBankDetailsModel;
import nirmalya.aathithya.webmodule.master.model.LocationMasterModel;
import nirmalya.aathithya.webmodule.master.model.VendorContactMasterModel;
import nirmalya.aathithya.webmodule.master.model.VendorDocumentMaster;
import nirmalya.aathithya.webmodule.master.model.VendorLocationMasterModel;
import nirmalya.aathithya.webmodule.master.model.VendorMasterModel;

/**
 * @author NirmalyaLabs
 *
 */
@Controller
@RequestMapping(value = { "inventory/" })
public class VendorCompanyController {
	Logger logger = LoggerFactory.getLogger(VendorCompanyController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	PasswordEncoder pw;

	@Autowired
	MailService mailService;

	@Autowired
	FileUpload fileUpload;

	@GetMapping(value = { "vendor-company" })
	public String manageVendor(Model model, HttpSession session) {
		logger.info("Method : manageVenor starts");
		try {
			DropDownModel[] locationType = restClient.getForObject(env.getMasterUrl() + "getLocationTypeList",
					DropDownModel[].class);
			List<DropDownModel> locationTypeList = Arrays.asList(locationType);

			model.addAttribute("locationTypeList", locationTypeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {

			DropDownModel[] jobType = restClient.getForObject(env.getRecruitment() + "jobTypeList",
					DropDownModel[].class);
			List<DropDownModel> jobTypeList = Arrays.asList(jobType);
			model.addAttribute("jobTypeList", jobTypeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {

			DropDownModel[] location = restClient.getForObject(env.getRecruitment() + "jobLocationList",
					DropDownModel[].class);
			List<DropDownModel> jobLocationList = Arrays.asList(location);
			model.addAttribute("jobLocationList", jobLocationList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {

			DropDownModel[] department = restClient.getForObject(env.getRecruitment() + "DepartmentList",
					DropDownModel[].class);
			List<DropDownModel> departmentList = Arrays.asList(department);
			model.addAttribute("DepartmentList", departmentList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] category = restClient.getForObject(env.getMasterUrl() + "getCategoryList",
					DropDownModel[].class);
			List<DropDownModel> categoryTypeList = Arrays.asList(category);

			model.addAttribute("categoryTypeList", categoryTypeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] country = restClient.getForObject(env.getMasterUrl() + "getCountryListForLocation",
					DropDownModel[].class);
			List<DropDownModel> countryList = Arrays.asList(country);

			model.addAttribute("countryList", countryList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] documentType = restClient.getForObject(env.getMasterUrl() + "getDocumentTypeList",
					DropDownModel[].class);
			List<DropDownModel> documentTypeList = Arrays.asList(documentType);

			model.addAttribute("documentTypeList", documentTypeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] country = restClient.getForObject(env.getEmployeeUrl() + "getCountryList",
					DropDownModel[].class);
			List<DropDownModel> counntryList = Arrays.asList(country);

			model.addAttribute("counntryList", counntryList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		 
//		try {
//			DropDownModel[] city = restClient.getForObject(env.getEmployeeUrl() + "getcityList1",
//					DropDownModel[].class);
//			List<DropDownModel> cityList = Arrays.asList(city);
//
//			model.addAttribute("cityList", cityList);
//		} catch (RestClientException e) {
//			e.printStackTrace();
//		}
		try {
			DropDownModel[] bank = restClient.getForObject(env.getEmployeeUrl() + "getBankNameList",
					DropDownModel[].class);
			List<DropDownModel> bankNameList = Arrays.asList(bank);

			model.addAttribute("BankNameList", bankNameList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			LocationMasterModel[] location = restClient.getForObject(env.getMasterUrl() + "getLocationList",
					LocationMasterModel[].class);
			List<LocationMasterModel> locationList = Arrays.asList(location);

			int count = 0;

			for (LocationMasterModel m : locationList) {
				count = count + 1;
				if (m.getLocVirtual().equals("0")) {
					m.setLocVirtual("No");
				}
				if (m.getLocVirtual().equals("1")) {
					m.setLocVirtual("Yes");
				}
				if (m.getLocStatus().equals("0")) {
					m.setLocStatus("Inactive");
				}
				if (m.getLocStatus().equals("1")) {
					m.setLocStatus("Active");
				}
			}

			model.addAttribute("count", count);

			model.addAttribute("locationList", locationList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : manageLocation ends");
		return "procurement/vendor-company";
	}
	//Get type list
		@SuppressWarnings("unchecked")
		@GetMapping(value = { "vendor-company-master-get-type-list" })
		public @ResponseBody JsonResponse<Object> getTypeListByCategoryWise(@RequestParam String category) {
			logger.info("Method : getTypeListByCategoryWise starts");
			JsonResponse<Object> res = new JsonResponse<Object>();
			try {
				res = restClient.getForObject(env.getMasterUrl() + "getTypeListByCategoryWise?id=" + category, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (res.getCode().equals("success")) {
				res.setMessage("success");
			} else {
				res.setCode(res.getMessage());
				res.setMessage("Unsuccess");
			}
			logger.info("Method : getTypeListByCategoryWise ends"+res);
			return res;
		}
	@SuppressWarnings("unchecked")
	@PostMapping("/vendor-company-master-save")
	public @ResponseBody JsonResponse<Object> saveVendorMaster(@RequestBody VendorMasterModel vendorMasterModel,
			HttpSession session) {
		logger.info("Method : saveVendorMaster starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		String dateFormat = "";

		try {
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		vendorMasterModel.setCreatedBy(userId);
		vendorMasterModel.setOrganizationName(orgName);
		vendorMasterModel.setOrganizationDivision(orgDivision);

		MultipartFile inputFile = (MultipartFile) session.getAttribute("quotationPFile");
		byte[] bytes;
		String imageName = null;

		if (inputFile != null) {
			try {
				bytes = inputFile.getBytes();
				String[] fileType = inputFile.getContentType().split("/");
				imageName = saveAllImage(bytes, fileType[1]);

				vendorMasterModel.setFileVendor(imageName);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		if(vendorMasterModel.getCompanyDate() !=null && vendorMasterModel.getCompanyDate()!="") {
			vendorMasterModel.setCompanyDate(DateFormatter.inputDateFormat(vendorMasterModel.getCompanyDate(), dateFormat));
		}
		try {

			resp = restClient.postForObject(env.getMasterUrl() + "saveVendorMaster", vendorMasterModel,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			session.removeAttribute("quotationPFile");
			resp.setMessage("Success");
		}

		logger.info("Method : saveVendorMaster starts");
		return resp;
	}

	public String saveAllImage(byte[] imageBytes, String ext) {
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

	@SuppressWarnings("unchecked")
	@PostMapping("/vendor-company-master-location-save")
	public @ResponseBody JsonResponse<Object> saveVendorLocationMaster(
			@RequestBody VendorLocationMasterModel vendorLocationMasterModel, HttpSession session) {
		logger.info("Method : saveVendorLocationMaster starts");

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

		vendorLocationMasterModel.setCreatedBy(userId);
		vendorLocationMasterModel.setOrganizationName(orgName);
		vendorLocationMasterModel.setOrganizationDivision(orgDivision);

		try {
			resp = restClient.postForObject(env.getMasterUrl() + "saveVendorLocationMaster", vendorLocationMasterModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : saveVendorLocationMaster starts");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "vendor-company-master-get-state-list" })
	public @ResponseBody JsonResponse<Object> getStateNameForLocationVendor(Model model, @RequestBody String tCountry,
			BindingResult result) {
		logger.info("Method : getStateNameForLocation starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			res = restClient.getForObject(env.getMasterUrl() + "getStateListForLoc?id=" + tCountry, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*
		 * if (res.getMessage() != null) { res.setCode(res.getMessage());
		 * res.setMessage("Unsuccess"); } else { res.setMessage("success"); }
		 */
		if (res.getCode().equals("success")) {
			res.setMessage("success");
		} else {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		}

		logger.info("Method : getStateNameForLocation ends");
		return res;

	}
	
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "vendor-company-get-city-list" })
	public @ResponseBody JsonResponse<Object> getCityForVendorLocation(Model model, @RequestBody String tCountry,
			BindingResult result) {
		
		logger.info("Method : getCityForLocation starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			res = restClient.getForObject(env.getMasterUrl() + "getCityForLocation?id=" + tCountry, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*
		 * if (res.getMessage() != null) { res.setCode(res.getMessage());
		 * res.setMessage("Unsuccess"); } else { res.setMessage("success"); }
		 */
		if (res.getCode().equals("success")) {
			res.setMessage("success");
		} else {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		}

		logger.info("Method : getCityForLocation ends"+res);
		return res;

	}
	

	@SuppressWarnings("unchecked")
	@GetMapping("vendor-company-master-data-through-ajax")
	public @ResponseBody List<VendorMasterModel> vendorThroughAjax(Model model, HttpServletRequest request,
			HttpSession session) {
		logger.info("Method : vendorThroughAjax starts");

		JsonResponse<List<VendorMasterModel>> jsonResponse = new JsonResponse<List<VendorMasterModel>>();
		String vendorId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			vendorId = (String) session.getAttribute("VENDOR_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {

			jsonResponse = restClient.getForObject(
					env.getMasterUrl() + "get-vendor-list-by-vendor?vendorId=" + vendorId +"&org=" + orgName + "&orgDiv=" + orgDivision, JsonResponse.class);

			ObjectMapper mapper = new ObjectMapper();

			List<VendorMasterModel> addreq = mapper.convertValue(jsonResponse.getBody(),
					new TypeReference<List<VendorMasterModel>>() {
					});
			String dateFormat = (String) (session).getAttribute("DATEFORMAT");
			for (VendorMasterModel m : addreq) {
				String date = m.getCreatedOn();
				if (date != null && date != "") {
					date = DateFormatter.dateFormat(date, dateFormat);
					m.setCreatedOn(date);
				}

			}

			jsonResponse.setBody(addreq);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method ; vendorThroughAjax ends");

		return jsonResponse.getBody();
	}

//vendor location through ajax start
	@SuppressWarnings("unchecked")
	@GetMapping("vendor-company-master-through-ajax")
	public @ResponseBody List<VendorLocationMasterModel> vendorLocationThroughAjax(Model model,
			HttpServletRequest request, @RequestParam String id, HttpSession session) {
		logger.info("Method : vendorLocationThroughAjax starts");

		JsonResponse<List<VendorLocationMasterModel>> jsonResponse = new JsonResponse<List<VendorLocationMasterModel>>();

		String orgName = "";
		String orgDivision = "";
		try {
			
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {

			jsonResponse = restClient.getForObject(env.getMasterUrl() + "get-vendor-location-list?id=" + id +"&org="+orgName +"&orgDiv=" + orgDivision,
					JsonResponse.class);

			ObjectMapper mapper = new ObjectMapper();

			List<VendorLocationMasterModel> addreq = mapper.convertValue(jsonResponse.getBody(),
					new TypeReference<List<VendorLocationMasterModel>>() {
					});
			String dateFormat = (String) (session).getAttribute("DATEFORMAT");
			for (VendorLocationMasterModel m : addreq) {
				String date = m.getCreatedOn();
				if (date != null && date != "") {
					date = DateFormatter.dateFormat(date, dateFormat);
					m.setCreatedOn(date);
				}

			}

			jsonResponse.setBody(addreq);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method ; vendorLocationThroughAjax ends");

		return jsonResponse.getBody();
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/vendor-company-master-location-edit")
	public @ResponseBody JsonResponse<Object> editVendorLocationMaster(@RequestBody String vendorId,
			HttpSession session) {
		logger.info("Method : editVendorLocationMaster starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		
		try {
			
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			resp = restClient.getForObject(env.getMasterUrl() + "editVendorLoactionById?id=" + vendorId +"&org=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);

			ObjectMapper mapper = new ObjectMapper();

			VendorLocationMasterModel locDetails = mapper.convertValue(resp.getBody(),
					new TypeReference<VendorLocationMasterModel>() {
					});
			try {
				DropDownModel[] state = restClient.getForObject(
						env.getMasterUrl() + "viewStateLocListByCountry?id=" + locDetails.getVendorCountry(),
						DropDownModel[].class);
				List<DropDownModel> stateList = Arrays.asList(state);

				locDetails.setStateList(stateList);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			try {
				DropDownModel[] city = restClient.getForObject(
						env.getMasterUrl() + "viewCityLocListByState?id=" + locDetails.getVendorState(),
						DropDownModel[].class);
				List<DropDownModel> cityList = Arrays.asList(city);

				locDetails.setCityList(cityList);
			} catch (RestClientException e) {
				e.printStackTrace();
			}
			resp.setBody(locDetails);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("success");
		}

		logger.info("Method : editVendorLocationMaster starts");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("vendor-company-master-location-delete")
	public @ResponseBody JsonResponse<Object> deleteVendorLocationMaster(Model model, @RequestParam String id,
			@RequestParam String simpleid, @RequestParam String vendorId, HttpSession session) {
		logger.info("Method : deleteVendorLocationMaster starts");

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
			resp = restClient.getForObject(env.getMasterUrl() + "deleteVendorLocation?id=" + id + "&createdBy="
					+ createdBy + "&simpleid=" + simpleid + "&vendorId=" + vendorId +"&org=" + orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("success");
		}

		logger.info("Method : deleteVendorLocationMaster ends");
		return resp;
	}
	
	//Delete Contacts
	  
			@SuppressWarnings("unchecked")

			@PostMapping("vendor-company-master-contact-delete")
			public @ResponseBody JsonResponse<Object> deleteVendorContacts(Model model, @RequestParam String id,

					@RequestParam String simpleid, @RequestParam String vendorId, HttpSession session) {
				logger.info("Method : deleteVendorContacts starts");

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
					resp = restClient.getForObject(env.getMasterUrl() + "deleteVendorContacts?id=" + id + "&createdBy="
							+ createdBy + "&simpleid=" + simpleid + "&vendorId=" + vendorId +"&org=" + orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
				} catch (RestClientException e) {
					e.printStackTrace();
				}

				if (resp.getMessage() != null && resp.getMessage() != "") {
					resp.setCode(resp.getMessage());
					resp.setMessage("Unsuccess");
				} else {
					resp.setMessage("success");
				}

				logger.info("Method : deleteVendorContacts ends");
				return resp;
			}
		
			//Delete Bank
			@SuppressWarnings("unchecked")

			@PostMapping("vendor-company-master-bank-delete")
			public @ResponseBody JsonResponse<Object> deleteBankDetails(Model model, @RequestParam String id,

					@RequestParam String simpleid, @RequestParam String vendorId, HttpSession session) {
				logger.info("Method : deleteBankDetails starts");

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
					resp = restClient.getForObject(env.getMasterUrl() + "deleteBankDetails?id=" + id + "&createdBy="
							+ createdBy + "&simpleid=" + simpleid + "&vendorId=" + vendorId +"&org=" + orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
				} catch (RestClientException e) {
					e.printStackTrace();
				}

				if (resp.getMessage() != null && resp.getMessage() != "") {
					resp.setCode(resp.getMessage());
					resp.setMessage("Unsuccess");
				} else {
					resp.setMessage("success");
				}

				logger.info("Method : deleteBankDetails ends");
				return resp;
			}
		

	@SuppressWarnings("unchecked")
	@PostMapping("vendor-company-master-delete")
	public @ResponseBody JsonResponse<Object> deleteVendorMaster(Model model, @RequestParam String id,
			@RequestParam String simpleid, HttpSession session) {
		logger.info("Method : deleteVendorLocationMaster starts");

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
			resp = restClient.getForObject(
					env.getMasterUrl() + "deleteVendor?id=" + id + "&createdBy=" + createdBy + "&simpleid=" + simpleid +"&org="+orgName +"&orgDiv=" + orgDivision,
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

		logger.info("Method : deleteVendorLocationMaster ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/vendor-company-master-edit")
	public @ResponseBody JsonResponse<Object> editVendorMaster(@RequestBody String vendorId, HttpSession session) {
		logger.info("Method : editVendorMaster starts");

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
			resp = restClient.getForObject(env.getMasterUrl() + "editVendorById?id=" + vendorId +"&org="+orgName +"&orgDiv=" + orgDivision, JsonResponse.class);

			ObjectMapper mapper = new ObjectMapper();

			VendorMasterModel vendorMasterModel = mapper.convertValue(resp.getBody(),
					new TypeReference<VendorMasterModel>() {
					});
			try {
				DropDownModel[] category = restClient.getForObject(env.getMasterUrl() + "getCategoryList",
						DropDownModel[].class);
				List<DropDownModel> categoryTypeList = Arrays.asList(category);
				vendorMasterModel.setCategoryList(categoryTypeList);

			} catch (RestClientException e) {
				e.printStackTrace();
			}
			if (vendorMasterModel.getFileVendor() != null && vendorMasterModel.getFileVendor() != ""
					&& !vendorMasterModel.getFileVendor().equals("null")) {
				String fileVendor = env.getBaseURL() + "document/module/" + vendorMasterModel.getFileVendor();

				vendorMasterModel.setFileVendor(fileVendor);
			}

			resp.setBody(vendorMasterModel);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("success");
		}

		logger.info("Method : editVendorMaster starts");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/vendor-company-master-contact-save")
	public @ResponseBody JsonResponse<Object> saveVendorContactMaster(
			@RequestBody VendorContactMasterModel vendorContactMasterModel, HttpSession session) {
		logger.info("Method : saveVendorContactMaster starts");

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

		vendorContactMasterModel.setCreatedBy(userId);
		vendorContactMasterModel.setOrganizationName(orgName);
		vendorContactMasterModel.setOrganizationDivision(orgDivision);

		try {
			resp = restClient.postForObject(env.getMasterUrl() + "saveVendorContactMaster", vendorContactMasterModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : saveVendorContactMaster starts"+resp);
		return resp;
	}

	// vendor contact through ajax start
	@SuppressWarnings("unchecked")
	@GetMapping("vendor-company-master-contact-through-ajax")
	public @ResponseBody List<VendorContactMasterModel> vendorContactThroughAjax(Model model,
			HttpServletRequest request, @RequestParam String id, HttpSession session) {
		logger.info("Method : vendorContactThroughAjax starts");

		JsonResponse<List<VendorContactMasterModel>> jsonResponse = new JsonResponse<List<VendorContactMasterModel>>();
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {

			jsonResponse = restClient.getForObject(env.getMasterUrl() + "get-vendor-contact-list?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);

			ObjectMapper mapper = new ObjectMapper();

			List<VendorContactMasterModel> addreq = mapper.convertValue(jsonResponse.getBody(),
					new TypeReference<List<VendorContactMasterModel>>() {
					});
			String dateFormat = (String) (session).getAttribute("DATEFORMAT");
			for (VendorContactMasterModel m : addreq) {
				String date = m.getCreatedOn();
				if (date != null && date != "") {
					date = DateFormatter.dateFormat(date, dateFormat);
					m.setCreatedOn(date);
				}

			}

			jsonResponse.setBody(addreq);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method ; vendorContactThroughAjax ends"+jsonResponse.getBody());
		return jsonResponse.getBody();
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/vendor-company-master-contact-edit")
	public @ResponseBody JsonResponse<Object> editVendorContactMaster(@RequestBody String vendorId,
			HttpSession session) {
		logger.info("Method : editVendorLocationMaster starts");

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
			resp = restClient.getForObject(env.getMasterUrl() + "editVendorContactById?id=" + vendorId + "&org=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);

			ObjectMapper mapper = new ObjectMapper();

			VendorContactMasterModel contactDetails = mapper.convertValue(resp.getBody(),
					new TypeReference<VendorContactMasterModel>() {
					});

			resp.setBody(contactDetails);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("success");
		}

		logger.info("Method : editVendorContactMaster starts"+resp);
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/vendor-company-master-document-save")
	public @ResponseBody JsonResponse<Object> saveVendorDocumentMaster(
			@RequestBody List<VendorDocumentMaster> vendorDocumentMaster, HttpSession session) {
		logger.info("Method : saveVendorDocumentMaster starts");

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

		for (VendorDocumentMaster m : vendorDocumentMaster) {
			m.setCreatedBy(userId);
			m.setDocumentImage(uploadPhoto(m.getDocumentImage(), m.getFile()));
			m.setOrganizationName(orgName);
			m.setOrganizationDivision(orgDivision);
		}

		try {
			resp = restClient.postForObject(env.getMasterUrl() + "saveVendorDocumentMaster", vendorDocumentMaster,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		logger.info("Method : saveVendorDocumentMaster starts");
		return resp;
	}

	public String uploadPhoto(String document, List<String> file) {

		if (document != null && document != "") {
			String delimiters = "\\.";
			String[] x = document.split(delimiters);
			if (x[1].matches("png") || x[1].matches("jpg") || x[1].matches("jpeg")) {
				for (String s1 : file) {

					try {

						byte[] bytes = new sun.misc.BASE64Decoder().decodeBuffer(s1);
						String imageName = fileUpload.saveAllImage(bytes);
						document = imageName;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (x[1].matches("pdf")) {
				for (String s1 : file) {
					try {

						byte[] bytes = new sun.misc.BASE64Decoder().decodeBuffer(s1);
						String pdfName = fileUpload.saveAllPdf(bytes);
						document = pdfName;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (x[1].matches("docx")) {
				for (String s1 : file) {
					try {
						byte[] bytes = new sun.misc.BASE64Decoder().decodeBuffer(s1);
						String pdfName = fileUpload.saveAllDocx(bytes);
						document = pdfName;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (x[1].matches("doc")) {
				for (String s1 : file) {
					try {
						byte[] bytes = new sun.misc.BASE64Decoder().decodeBuffer(s1);
						String pdfName = fileUpload.saveAllDoc(bytes);
						document = pdfName;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (x[1].matches("xls")) {
				for (String s1 : file) {
					try {
						byte[] bytes = new sun.misc.BASE64Decoder().decodeBuffer(s1);
						String pdfName = fileUpload.saveAllXls(bytes);
						document = pdfName;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (x[1].matches("xlsx")) {
				for (String s1 : file) {
					try {
						byte[] bytes = new sun.misc.BASE64Decoder().decodeBuffer(s1);
						String pdfName = fileUpload.saveAllXlsx(bytes);
						document = pdfName;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return document;
	}

	public String saveAllImage(byte[] imageBytes) {
		logger.info("Method : saveAllImage starts");

		String imageName = null;

		try {
			if (imageBytes != null) {
				long nowTime = new Date().getTime();
				imageName = nowTime + ".png";
			}

			Path path = Paths.get(env.getFileUploadEmployee() + imageName);
			if (imageBytes != null) {
				Files.write(path, imageBytes);

				ByteArrayInputStream in = new ByteArrayInputStream(imageBytes);
				Integer height = 50;
				Integer width = 50;

				try {
					BufferedImage img = ImageIO.read(in);
					if (height == 0) {
						height = (width * img.getHeight()) / img.getWidth();
					}
					if (width == 0) {
						width = (height * img.getWidth()) / img.getHeight();
					}

					Image scaledImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);

					BufferedImage imageBuff = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
					imageBuff.getGraphics().drawImage(scaledImage, 0, 0, new Color(0, 0, 0), null);

					ByteArrayOutputStream buffer = new ByteArrayOutputStream();

					ImageIO.write(imageBuff, "png", buffer);

					byte[] thumb = buffer.toByteArray();

					Path pathThumb = Paths.get(env.getFileUploadEmployee() + "thumb/" + imageName);
					Files.write(pathThumb, thumb);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveAllImage ends");
		return imageName;
	}
	
	/*
	 * for save all pdf in folder and return name
	 */

	public String saveAllPdf(byte[] imageBytes) {
		logger.info("Method : saveAllPdf starts");

		String pdfName = null;

		try {
			if (imageBytes != null) {
				long nowTime = new Date().getTime();
				pdfName = nowTime + ".pdf";
			}

			Path path = Paths.get(env.getFileUploadEmployee() + pdfName);
			logger.info("path " + path);
			if (imageBytes != null) {
				Files.write(path, imageBytes);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveAllPdf ends");
		return pdfName;
	}

	public String saveAllDocx(byte[] imageBytes) {
		logger.info("Method : saveAllDocx starts");

		String pdfName = null;

		try {
			if (imageBytes != null) {
				long nowTime = new Date().getTime();
				pdfName = nowTime + ".docx";
			}

			Path path = Paths.get(env.getFileUploadEmployee() + pdfName);
			logger.info("path" + path);
			if (imageBytes != null) {
				Files.write(path, imageBytes);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveAllDocx ends");
		return pdfName;
	}

	public String saveAllDoc(byte[] imageBytes) {
		logger.info("Method : saveAllDoc starts");

		String pdfName = null;

		try {
			if (imageBytes != null) {
				long nowTime = new Date().getTime();
				pdfName = nowTime + ".doc";
			}

			Path path = Paths.get(env.getFileUploadEmployee() + pdfName);
			logger.info("path" + path);
			if (imageBytes != null) {
				Files.write(path, imageBytes);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveAllDoc ends");
		return pdfName;
	}

	public String saveAllXls(byte[] imageBytes) {
		logger.info("Method : saveAllDoc starts");

		String pdfName = null;

		try {
			if (imageBytes != null) {
				long nowTime = new Date().getTime();
				pdfName = nowTime + ".xls";
			}

			Path path = Paths.get(env.getFileUploadEmployee() + pdfName);
			logger.info("path" + path);
			if (imageBytes != null) {
				Files.write(path, imageBytes);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveAllDoc ends");
		return pdfName;
	}

	public String saveAllXlsx(byte[] imageBytes) {
		logger.info("Method : saveAllDoc starts");

		String pdfName = null;

		try {
			if (imageBytes != null) {
				long nowTime = new Date().getTime();
				pdfName = nowTime + ".xlsx";
			}

			Path path = Paths.get(env.getFileUploadEmployee() + pdfName);
			logger.info("path" + path);
			if (imageBytes != null) {
				Files.write(path, imageBytes);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveAllDoc ends");
		return pdfName;
	}
	@SuppressWarnings("unchecked")
	@PostMapping("/vendor-company-master-bankdetails-save")
	public @ResponseBody JsonResponse<Object> saveemployeebankdetails(
			@RequestBody ManageEmployeeBankDetailsModel manageEmployeeBankDetails, HttpSession session) {
		logger.info("Method : saveemployeebankdetailsVendor starts");

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

		manageEmployeeBankDetails.setCreatedBy(userId);
		manageEmployeeBankDetails.setOrganization(orgName);
		manageEmployeeBankDetails.setOrgDivision(orgDivision);

		try {
			resp = restClient.postForObject(env.getMasterUrl() + "saveemployeebankdetailsVendor",
					manageEmployeeBankDetails, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() == null && resp.getMessage() != "") {
			resp.setMessage("Success");

		} else {
			resp.setMessage("unsucess");
		}

		logger.info("Method : saveemployeebankdetailsVendor starts");
		return resp;
	}
	// vendor contact through ajax start

	@SuppressWarnings("unchecked")
	@GetMapping("vendor-company-master-bank-through-ajax")
	public @ResponseBody List<ManageEmployeeBankDetailsModel> viewmanageemployeebankdetails(Model model,
			HttpServletRequest request, @RequestParam String id, HttpSession session) {
		logger.info("Method : viewmanageemployeebankdetails starts");

		JsonResponse<List<ManageEmployeeBankDetailsModel>> jsonResponse = new JsonResponse<List<ManageEmployeeBankDetailsModel>>();
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {

			jsonResponse = restClient.getForObject(env.getMasterUrl() + "viewVendorBankDetails?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);

			ObjectMapper mapper = new ObjectMapper();

			List<ManageEmployeeBankDetailsModel> addreq = mapper.convertValue(jsonResponse.getBody(),
					new TypeReference<List<ManageEmployeeBankDetailsModel>>() {
					});

			jsonResponse.setBody(addreq);
			String dateFormat = (String) (session).getAttribute("DATEFORMAT");
			for (ManageEmployeeBankDetailsModel m : addreq) {
				String date = m.getCreatedBy();
				if (date != null && date != "") {
					date = DateFormatter.dateFormat(date, dateFormat);
					m.setCreatedBy(date);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method ; viewmanageemployeedependent ends");

		return jsonResponse.getBody();
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/vendor-company-master-bank-edit")
	public @ResponseBody JsonResponse<Object> editVendorBankMaster(@RequestBody String vendorId, HttpSession session) {
		logger.info("Method : editVendorBankMaster starts");

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
			resp = restClient.getForObject(env.getMasterUrl() + "editVendorBankById?id=" + vendorId + "&org=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);

			ObjectMapper mapper = new ObjectMapper();

			ManageEmployeeBankDetailsModel bankDetails = mapper.convertValue(resp.getBody(),
					new TypeReference<ManageEmployeeBankDetailsModel>() {
					});
			try {
				DropDownModel[] state = restClient.getForObject(
						env.getMasterUrl() + "viewStateLocListByCountry?id=" + bankDetails.getEbankCountry(),
						DropDownModel[].class);
				List<DropDownModel> stateList = Arrays.asList(state);

				bankDetails.setStateList(stateList);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			try {
				DropDownModel[] city = restClient.getForObject(
						env.getMasterUrl() + "viewCityLocListByState?id=" + bankDetails.getEbankState(),
						DropDownModel[].class);
				List<DropDownModel> cityList = Arrays.asList(city);

				bankDetails.setCityList(cityList);
			} catch (RestClientException e) {
				e.printStackTrace();
			}
			resp.setBody(bankDetails);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("success");
		}

		logger.info("Method : editVendorBankMaster starts");
		return resp;
	}

	@PostMapping("/vendor-company-master-upload-file")
	public @ResponseBody JsonResponse<Object> uploadFile(@RequestParam("file") MultipartFile inputFile,
			HttpSession session) {
		logger.info("Method : uploadFile controller function 'post-mapping' starts");

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

	@GetMapping(value = { "vendor-company-master-activity-through-ajax" })
	public @ResponseBody List<ActivitylogModel> getActivityLog(@RequestParam String id, HttpSession session) {
		logger.info("Method : getActivityLog starts");
		List<ActivitylogModel> activityLogList = new ArrayList<ActivitylogModel>();

		String orgName = "";
		String orgDivision = "";
		try {
			
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {

			ActivitylogModel[] activityLog = restClient
					.getForObject(env.getMasterUrl() + "get-activity-log-vendor?id=" + id +"&org="+orgName +"&orgDiv=" + orgDivision, ActivitylogModel[].class);
			activityLogList = Arrays.asList(activityLog);

			String dateFormat = (String) (session).getAttribute("DATEFORMAT");
			for (ActivitylogModel m : activityLogList) {
				String date = m.getOperationOn();
				if (date != null && date != "") {
					date = DateFormatter.dateFormat(date, dateFormat);
					m.setOperationOn(date);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : getActivityLog ends");
		return activityLogList;
	}

	/* createVendorWiseUser */

	@SuppressWarnings("unchecked")
	@PostMapping("/vendor-company-master-create-user")
	public @ResponseBody JsonResponse<Object> createVendorWiseUser(@RequestBody VendorContactMasterModel vendorId,
			HttpSession session) {
		logger.info("Method : createVendorWiseUser starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";
		String orgName = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		vendorId.setCreatedBy(userId);
		vendorId.setOrganizationName(orgName);
		vendorId.setOrganizationDivision(orgDivision);
		String pass = "$2a$10$SkYOZORZ4PUSURzL1fmvk.RcUwCfLk/R826sxBXKx/ZZyoQvkcaa6";
		String password="User@123";
		vendorId.setPwd(pass);
		try {
			resp = restClient.postForObject(env.getMasterUrl() + "createVendorWiseUser", vendorId, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String message = resp.getMessage();
		if (message != null && message != "") {
		} else {
			resp.setMessage("success");
			logger.info("Here==="+password);
			String uid = (String) resp.getBody();
			logger.info("uid==="+uid);
			String subject = "Samudyam Vendor - Login Credentials";
			
			String emailMessage = "Hello "+vendorId.getContactName()+","+System.lineSeparator()+
					"Your UserId is  "+uid+" or "+vendorId.getPhone()+" and password is "+password+System.lineSeparator()+
					"Link: "+"https://samudyam.nerp.in/";
			String to = vendorId.getEmail();
			
			logger.info("TOOOOO"+to);
			logger.info("sub"+subject);
			logger.info("msg"+emailMessage);
 
			try {
				mailService.sendEmail(to,subject,emailMessage); 
				logger.info("Email sent.");
			} catch (Exception ex) {
				logger.info("Could not send email.");
				ex.printStackTrace();
			}
		}
		logger.info("Method : createVendorWiseUser starts");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("vendor-company-master-reset-password")
	public @ResponseBody JsonResponse<Object> resetPasswordVendor(@RequestBody VendorContactMasterModel vendorId,
			HttpSession session) {
		logger.info("Method : resetPasswordVendor starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";
		String orgName = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		vendorId.setCreatedBy(userId);
		vendorId.setOrganizationName(orgName);
		vendorId.setOrganizationDivision(orgDivision);

		String password = "User@123";
		if (password != null && password != "") {
			String pwd = pw.encode(password);
			vendorId.setPwd(pwd);
		}

		try {
			resp = restClient.postForObject(env.getMasterUrl() + "resetPasswordVendor", vendorId, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("success");

			String subject = "Extend - Reset Password";

			String emailMessage = "Hello " + vendorId.getContactName() + "," + System.lineSeparator()
					+ "Your new password = " + password + System.lineSeparator() + "Link: "
					+ "<a href='https://nerp.in'>https://nerp.in</a>";

			String attachFiles[] = null;

			List<String> emailIds = new ArrayList<String>();
			emailIds.add(vendorId.getEmail());
			List<String> ccUsers = new ArrayList<String>();

			try {
				EmailAttachmentSender.sendEmailWithAttachments("Smtp.gmail.com", "587", "sagaranchal4@gmail.com",
						"kharasrota", emailIds, ccUsers, subject, emailMessage, attachFiles);
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}

		logger.info("Method : createVendorWiseUser starts");
		return resp;
	}

	
	//VENDOR PROFILE DOCUMENTS
	//Upload document
	  @PostMapping("/vendor-company-master-doc-upload-file") public @ResponseBody
	  JsonResponse<Object> uploadDocFile(@RequestParam("file") MultipartFile
	  inputFile, HttpSession session) {
	  logger.info("Method : vendor uploadDocFile controller  starts");
	  
	  JsonResponse<Object> response = new JsonResponse<Object>();
	  logger.info("inputFile==="+inputFile.getContentType());
	  try { response.setMessage(inputFile.getOriginalFilename());
	  session.setAttribute("venderDocFile", inputFile);
	  
	  } catch (RestClientException e) { e.printStackTrace(); } catch (Exception e)
	  { e.printStackTrace(); } logger.info("#####"+response);
	  logger.info("Method : vendor uploadDocFile controller ' ends"); return
	  response; }
	  
	//Save Vendor Document
	  @SuppressWarnings("unchecked")
		@PostMapping("/vendor-company-profile-document-save")
		public @ResponseBody JsonResponse<Object> addEmpDocument(@RequestBody VendorDocumentMaster docModel,
				Model model, HttpSession session) {
			logger.info("Method :addVendorDocument starts");
			
			logger.info("@@@@@@@@@@@@@@@@" + docModel);
			
			MultipartFile inputFile = (MultipartFile) session.getAttribute("venderDocFile");
			logger.info("inputFile====="+inputFile);
			byte[] bytes;
			String imageName = null;
			if (inputFile != null){
				try {
					bytes = inputFile.getBytes();
					String[] fileType = inputFile.getContentType().split("/");
					imageName = saveAllImage(bytes, fileType[1]);

					logger.info("imageName===="+imageName);

					docModel.setDocumentImage(imageName);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			
			JsonResponse<Object> resp = new JsonResponse<Object>();
			String userId = "";
			String orgName = "";
			String orgDivision = "";
			try {

				userId = (String) session.getAttribute("USER_ID");
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			} catch (RestClientException e) {
				e.printStackTrace();

			}
			docModel.setCreatedBy(userId);
			docModel.setOrganizationName(orgName); 
			docModel.setOrganizationDivision(orgDivision); 
			logger.info("docModel===="+docModel);
			try {
				resp = restClient.postForObject(env.getMasterUrl() + "rest-addVendorDocument", docModel,
						JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}
			String message = resp.getMessage();
			if (message != null && message != "") {

			} else {
				resp.setMessage("Success");
				session.removeAttribute("employeeDocFile");
			}
			logger.info("Method : addVendorDocument ends");
			return resp;
		}

	  //view vendor documents
	  @SuppressWarnings("unchecked")
		@GetMapping("vendor-company-profile-view-document-ajax")
		public @ResponseBody List<VendorDocumentMaster> viewvendordocumentdetails(HttpSession session,
				@RequestParam String id) {
			logger.info("Method : viewmanagevendordocumentdetails starts");
			String organization = "";
			String orgDivision = "";
			try {
				organization = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			JsonResponse<List<VendorDocumentMaster>> jsonResponse = new JsonResponse<List<VendorDocumentMaster>>();
			try {
				jsonResponse = restClient.getForObject(env.getMasterUrl() + "view-vendordocument?id=" + id + "&organization="+ organization + "&orgDivision=" + orgDivision, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method ; viewmanagevendordocumentdetails ends");
			return jsonResponse.getBody();
		}
		//Edit document
		@SuppressWarnings("unchecked")
		@GetMapping("vendor-company-profile-document-edit")
		public @ResponseBody JsonResponse<VendorDocumentMaster> editDocumentDetails(@RequestParam String docid,
				HttpSession session) {

			logger.info("Method : editDocumentDetails starts");
			JsonResponse<VendorDocumentMaster> jsonResponse = new JsonResponse<VendorDocumentMaster>();
			String organization = "";
			String orgDivision = "";
			try {
				organization = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				jsonResponse = restClient.getForObject(env.getMasterUrl() + "rest-editDocumentDetails?docid=" + docid
						+ "&organization=" + organization + "&orgDivision=" + orgDivision,JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}
			if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
				jsonResponse.setCode(jsonResponse.getMessage());
				jsonResponse.setMessage("Unsuccess");
			} else {
				jsonResponse.setMessage("Success");
			}
			logger.info("Method : editDocumentDetails ends");
			logger.info("editDocumentDetails====="+jsonResponse);
			return jsonResponse;
		}
		//delete document
		@SuppressWarnings("unchecked")
		@GetMapping("/vendor-company-profile-document-delete")
		public @ResponseBody JsonResponse<Object> deleteEmpDoc(Model model, HttpSession session, @RequestParam String docid) {
			logger.info("Method : deleteEmpDoc starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			String orgName = "";
			String orgDivision = "";
			try {
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			} catch (RestClientException e) {
				e.printStackTrace();
			}
			logger.info("docid==="+docid);
			try {
				resp = restClient.getForObject(env.getMasterUrl() + "deleteVenderDoc?docid=" + docid+ "&organization="+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}
			if (resp.getCode().equals("success")) {
				resp.setMessage("Success");
			} else {
				resp.setMessage("Unsuccess");
			}
			logger.info("Method : deleteEmpDoc ends");

			return resp;
		}
}
 
