package nirmalya.aathithya.webmodule.employee.controller;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.util.IOUtils;
import org.json.JSONObject;
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
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.employee.model.ManageEmployeeAddressModel;
import nirmalya.aathithya.webmodule.employee.model.ManageEmployeeBankDetailsModel;
import nirmalya.aathithya.webmodule.employee.model.ManageEmployeeBenifitModel;
import nirmalya.aathithya.webmodule.employee.model.ManageEmployeeDependentModel;
import nirmalya.aathithya.webmodule.employee.model.ManageEmployeeDocumentModel;
import nirmalya.aathithya.webmodule.employee.model.ManageEmployeeInsuranceModel;
import nirmalya.aathithya.webmodule.employee.model.ManageEmployeeModel;
import nirmalya.aathithya.webmodule.employee.model.ManageEmployeeWorkdetailsModel;
import nirmalya.aathithya.webmodule.qa.model.QaPcroModel;
import nirmalya.aathithya.webmodule.recruitment.model.CandidateDetailsModel;
import nirmalya.aathithya.webmodule.sales.model.CustomerNewModel;
import nirmalya.aathithya.webmodule.sales.model.DeliveryChallanModel;

/*
 * @author Nirmalya labs
 */
@Controller
@RequestMapping(value = { "employee/" })

public class ManageEmployeeController {
	Logger logger = LoggerFactory.getLogger(ManageEmployeeController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	PasswordEncoder passEncoder;
	
	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	// Summary
	@GetMapping("/view-manage-employee")
	public String employee(Model model, HttpSession session) {

		logger.info("Method : employee starts");
		String userId = "";
		String userName = "";
		String userRole = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		String splitData[] = userRole.split("r");
		String[] removedNull = Arrays.stream(splitData).filter(value -> value != "" && value.length() > 0)
				.toArray(size -> new String[size]);
		for (String part : removedNull) {
			String data = "r" + part;

			if (data.contentEquals("rol001")) {

				model.addAttribute("adRole", data);
				logger.info("data ==" + data);

			} else if (data.contentEquals("rol003")) {

				model.addAttribute("mrRole", data);
				logger.info("data 1==" + data);
			} else {
				model.addAttribute("empRole", data);
				logger.info("data 2==" + data);
			}
		}
		try {
			DropDownModel[] Gender = restClient.getForObject(env.getEmployeeUrl() + "getgenderList1",
					DropDownModel[].class);
			List<DropDownModel> genderTypeList = Arrays.asList(Gender);
			model.addAttribute("genderTypeList", genderTypeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] Nationality = restClient.getForObject(env.getEmployeeUrl() + "getnationalityList1",
					DropDownModel[].class);
			List<DropDownModel> nationalityList = Arrays.asList(Nationality);

			model.addAttribute("nationalityList", nationalityList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] BloodGroup = restClient.getForObject(env.getEmployeeUrl() + "getbloodgroupList1",
					DropDownModel[].class);
			List<DropDownModel> bloodgroupList = Arrays.asList(BloodGroup);

			model.addAttribute("bloodgroupList", bloodgroupList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] MaritalStatus = restClient.getForObject(env.getEmployeeUrl() + "getmaritalstatusList1",
					DropDownModel[].class);
			List<DropDownModel> maritalstatusList = Arrays.asList(MaritalStatus);

			model.addAttribute("maritalstatusList", maritalstatusList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] Country = restClient.getForObject(env.getEmployeeUrl() + "getCountryList",
					DropDownModel[].class);
			List<DropDownModel> counntryList = Arrays.asList(Country);

			model.addAttribute("counntryList", counntryList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {

			DropDownModel[] addressType = restClient.getForObject(env.getRecruitment() + "addressTypeList",
					DropDownModel[].class);
			List<DropDownModel> addressTypeList = Arrays.asList(addressType);
			model.addAttribute("addressTypeList", addressTypeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] JobType = restClient.getForObject(
					env.getEmployeeUrl() + "getJobType1?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> jobtypeList = Arrays.asList(JobType);

			model.addAttribute("jobtypeList", jobtypeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] Department = restClient.getForObject(env.getEmployeeUrl()
					+ "getDepartmentList1?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> DepartmentList = Arrays.asList(Department);

			model.addAttribute("DepartmentList", DepartmentList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		/*
		 * try { DropDownModel[] JobType = restClient.getForObject(env.getEmployeeUrl()
		 * + "getJobType1", DropDownModel[].class); List<DropDownModel> jobtypeList =
		 * Arrays.asList(JobType);
		 * 
		 * model.addAttribute("jobtypeList", jobtypeList); } catch (RestClientException
		 * e) { e.printStackTrace(); }
		 */

		/*
		 * try { DropDownModel[] Department =
		 * restClient.getForObject(env.getEmployeeUrl() + "getDepartmentList1",
		 * DropDownModel[].class); List<DropDownModel> DepartmentList =
		 * Arrays.asList(Department);
		 * 
		 * model.addAttribute("DepartmentList", DepartmentList); } catch
		 * (RestClientException e) { e.printStackTrace(); }
		 */
		try {
			DropDownModel[] TimesheetType = restClient.getForObject(env.getEmployeeUrl()
					+ "getTimesheetType1?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> TimesheetList = Arrays.asList(TimesheetType);

			model.addAttribute("TimesheetList", TimesheetList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {

			DropDownModel[] band = restClient.getForObject(
					env.getRecruitment() + "bandList?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> bandList = Arrays.asList(band);
			model.addAttribute("bandList", bandList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] employmentType = restClient.getForObject(env.getEmployeeUrl()
					+ "getemploymentType1?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> employmentstatusList = Arrays.asList(employmentType);

			model.addAttribute("employmentstatusList", employmentstatusList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {

			DropDownModel[] manager = restClient.getForObject(
					env.getEmployeeUrl() + "EmployeeList?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> managerList = Arrays.asList(manager);
			model.addAttribute("EmployeeList", managerList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] Benefits = restClient.getForObject(
					env.getEmployeeUrl() + "getBenefits?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> benefitsList = Arrays.asList(Benefits);

			model.addAttribute("benefitsList", benefitsList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] dependentType = restClient.getForObject(env.getEmployeeUrl() + "dependentTypeList",
					DropDownModel[].class);
			List<DropDownModel> dependentTypeList = Arrays.asList(dependentType);

			model.addAttribute("dependentTypeList", dependentTypeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] relationship = restClient.getForObject(env.getEmployeeUrl() + "relationshipList",
					DropDownModel[].class);
			List<DropDownModel> relationshipList = Arrays.asList(relationship);

			model.addAttribute("relationshipList", relationshipList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] Bank = restClient.getForObject(env.getEmployeeUrl() + "getBankNameList",
					DropDownModel[].class);
			List<DropDownModel> BankNameList = Arrays.asList(Bank);

			model.addAttribute("BankNameList", BankNameList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] Bank = restClient.getForObject(env.getEmployeeUrl() + "insuranceCompanyList?organization="
					+ organization + "&orgDivision=" + orgDivision, DropDownModel[].class);
			List<DropDownModel> insuranceCompanyList = Arrays.asList(Bank);

			model.addAttribute("insuranceCompanyList", insuranceCompanyList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] Bank = restClient.getForObject(env.getEmployeeUrl() + "documentTypeList",
					DropDownModel[].class);
			List<DropDownModel> documentTypeList = Arrays.asList(Bank);

			model.addAttribute("documentTypeList", documentTypeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] dropDown = restClient.getForObject(env.getEmployeeUrl() + "getDesignations?organization="
					+ organization + "&orgDivision=" + orgDivision, DropDownModel[].class);
			List<DropDownModel> Designation = Arrays.asList(dropDown);
			model.addAttribute("Designation", Designation);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("userRole", userRole);

		logger.info("Method : employee ends");

		return "employee/view-manage-employee";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-manage-employee-manager-list")
	public @ResponseBody JsonResponse<List<DropDownModel>> getManager(Model model, @RequestParam String id,
			HttpSession session) {
		logger.info("Method : getManager starts");
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		JsonResponse<List<DropDownModel>> resp = new JsonResponse<List<DropDownModel>>();

		try {
			resp = restClient.getForObject(env.getEmployeeUrl() + "getManager?id=" + id + "&organization="
					+ organization + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method :  getManager ends");
		return resp;
	}

	@PostMapping("/view-manage-employee-upload-file")
	public @ResponseBody JsonResponse<Object> uploadFile(@RequestParam("file") MultipartFile inputFile,
			HttpSession session) {
		logger.info("Method : employee uploadimage controller  starts");

		JsonResponse<Object> response = new JsonResponse<Object>();

		try {
			response.setMessage(inputFile.getOriginalFilename());
			// logger.info(inputFile);
			session.setAttribute("employeePFile", inputFile);

		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : employee uploadimage controller ' ends");
		return response;
	}

	@PostMapping("/view-manage-employee-delete-file")
	public @ResponseBody JsonResponse<Object> deleteFile(HttpSession session) {
		logger.info("Method : deleteFile employee uploadimage controller starts");

		JsonResponse<Object> response = new JsonResponse<Object>();

		try {
			session.removeAttribute("employeePFile");
		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : deleteFile employee uploadimage controller ends");
		return response;
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-manage-employee-state-list" })
	public @ResponseBody JsonResponse<Object> getStateNameForemployee(Model model, @RequestBody String tCountry,
			BindingResult result) {
		logger.info("Method : getStateNameForemployee starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			res = restClient.getForObject(env.getMasterUrl() + "getStateListForLoc?id=" + tCountry, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getCode().equals("success")) {
			res.setMessage("success");
		} else {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		}
		logger.info("Method : getStateNameForemployee ends");
		return res;

	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-manage-employee-city-list" })
	public @ResponseBody JsonResponse<Object> getCityForcity(Model model, @RequestBody String tCountry,
			BindingResult result) {
		logger.info("Method : getCityForLocation starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			res = restClient.getForObject(env.getMasterUrl() + "getCityForLocation?id=" + tCountry, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getCode().equals("success")) {
			res.setMessage("success");
		} else {
			res.setCode(res.getMessage());
			res.setMessage("unsuccess");
		}
		logger.info("Method : getCityForcity ends");
		return res;

	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-manage-employee-state-list-bank" })
	public @ResponseBody JsonResponse<Object> getStateNameForemployeebank(Model model, @RequestBody String tCountry,
			BindingResult result) {
		logger.info("Method : getStateNameForemployee starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			res = restClient.getForObject(env.getMasterUrl() + "getStateListForLoc?id=" + tCountry, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : getStateNameForemployee ends");
		return res;

	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-manage-employee-city-list-bank" })
	public @ResponseBody JsonResponse<Object> getCityForcitybank(Model model, @RequestBody String tCountry,
			BindingResult result) {
		logger.info("Method : getCityForLocation starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			res = restClient.getForObject(env.getMasterUrl() + "getCityForLocation?id=" + tCountry, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : getCityForcity ends");
		return res;

	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-manage-employee-subdepartment-list")
	public @ResponseBody JsonResponse<List<DropDownModel>> getSubdepartmentList(Model model,
			@RequestParam String department, HttpSession session) {
		logger.info("Method : getSubdepartmentList starts");

		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		JsonResponse<List<DropDownModel>> resp = new JsonResponse<List<DropDownModel>>();

		try {
			resp = restClient.getForObject(env.getEmployeeUrl() + "getSubdepartmentList?id=" + department
					+ "&organization=" + organization + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method :  getSubdepartmentList ends");
		return resp;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@PostMapping("/view-manage-employee-master-save")
	public @ResponseBody JsonResponse<Object> saveManangeEmployee(@RequestBody ManageEmployeeModel manageEmployeeModel,
			HttpSession session) {
		logger.info("Method : saveManangeEmployee starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";
		String dateFormat = "";
		String pass = "$2a$10$SkYOZORZ4PUSURzL1fmvk.RcUwCfLk/R826sxBXKx/ZZyoQvkcaa6";
		// String pass = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		manageEmployeeModel.setPassword(pass);
		manageEmployeeModel.setCreatedBy(userId);
		manageEmployeeModel.setOrganization(organization);
		manageEmployeeModel.setOrgDivision(orgDivision);

		MultipartFile inputFile = (MultipartFile) session.getAttribute("employeePFile");
		byte[] bytes;
		String imageName = null;

		if (inputFile != null) {
			try {
				bytes = inputFile.getBytes();
				String[] fileType = inputFile.getContentType().split("/");
				imageName = saveAllImage(bytes, fileType[1]);
				// logger.info(imageName);

				manageEmployeeModel.setFileEmployeeimg(imageName);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		try {

			resp = restClient.postForObject(env.getEmployeeUrl() + "saveemployeeMaster", manageEmployeeModel,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getCode().equals("success")) {
			resp.setMessage("Success");
			session.removeAttribute("employeePFile");
		} else {

		}
		
		JSONObject jsonQr = new JSONObject();

		ObjectMapper mapper = new ObjectMapper();
		ManageEmployeeModel emp = mapper.convertValue(resp.getBody(), new TypeReference<ManageEmployeeModel>() {
		});
		if (emp.getEmployeeId() == null || emp.getEmployeeId() == "") {
			logger.info("Method : QR code cannotbe generated");

		} else {
			String fullName = emp.getFirstName() + " " + emp.getLastName();
			jsonQr.put("EmployeeId", emp.getEmployeeId());
			jsonQr.put("Employee Name", fullName);
			jsonQr.put("Department", emp.getDepartment());
			jsonQr.put("Organization", emp.getOrganization());
			jsonQr.put("Devision", emp.getOrgDivision());

			String qrdata = jsonQr.toString();

			try {
				String qrCodeData = qrdata;
				String qrName = "QR" + emp.getEmployeeId() + ".png";
				String filePath = env.getStaffQrCode() + qrName;

				String charset = "UTF-8";// "ISO-8859-1";

				Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();

				hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
				BitMatrix matrix = new MultiFormatWriter().encode(new String(qrCodeData.getBytes(charset), charset),
						BarcodeFormat.QR_CODE, 200, 200, hintMap);
				MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath.lastIndexOf('.') + 1),
						new File(filePath));

				jsonQr.keySet().clear();
				logger.info("Method : QR code function Ends");

			} catch (Exception e) {
				System.err.println(e);
			}

		}
		
		
		logger.info("Method : saveManangeEmployee End");
		return resp;
	}

	/************************* View Employee *********************************/
	@SuppressWarnings({ "unchecked" })
	@GetMapping("view-manage-employee-master-through-ajax")
	public @ResponseBody List<ManageEmployeeModel> viewEmpolyee(Model model, HttpSession session) {
		logger.info("Method : viewEmpolyee starts");

		JsonResponse<List<ManageEmployeeModel>> jsonResponse = new JsonResponse<List<ManageEmployeeModel>>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		// JSONObject jsonQr = new JSONObject();
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			jsonResponse = restClient.getForObject(env.getEmployeeUrl() + "view-manage-employee-maste?userId=" + userId
					+ "&organization=" + organization + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		List<ManageEmployeeModel> emp = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<ManageEmployeeModel>>() {
				});
		logger.info("Method : viewEmpolyee ends");
		return jsonResponse.getBody();
	}

	public String saveAllImage(byte[] imageBytes, String ext) {
		logger.info("Method : saveAllImage starts");

		String imageName = null;

		try {

			if (imageBytes != null) {
				long nowTime = new Date().getTime();
				if (ext.contentEquals("png") || ext.contentEquals("jpg") || ext.contentEquals("jpeg")) {
					imageName = nowTime + ".jpg";
				} else {
					imageName = nowTime + "." + ext;
				}

			}

			Path path = Paths.get(env.getFileUploadEmployee() + imageName);
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
	@PostMapping("/view-manage-employee-address-save")
	public @ResponseBody JsonResponse<Object> saveemployeeaddress(
			@RequestBody ManageEmployeeAddressModel manageEmployeeAddressModel, HttpSession session) {
		logger.info("Method : saveemployeeaddress starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";
		String organization = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		manageEmployeeAddressModel.setCreatedBy(userId);
		manageEmployeeAddressModel.setOrganization(organization);
		manageEmployeeAddressModel.setOrgDivision(orgDivision);
		try {
			resp = restClient.postForObject(env.getEmployeeUrl() + "saveemployeeaddress", manageEmployeeAddressModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getCode().equals("success")) {
			resp.setMessage("Success");
		} else {

		}

		logger.info("Method : saveemployeeaddress ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-manage-employee-address-through-ajax")
	public @ResponseBody List<ManageEmployeeAddressModel> empLocationThroughAjax(Model model,
			HttpServletRequest request, @RequestParam String id, HttpSession session) {
		logger.info("Method : empLocationThroughAjax starts");
		String organization = "";
		String orgDivision = "";

		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}
		JsonResponse<List<ManageEmployeeAddressModel>> jsonResponse = new JsonResponse<List<ManageEmployeeAddressModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getEmployeeUrl() + "viewEmployeeadd?id=" + id + "&organization="
					+ organization + "&orgDivision=" + orgDivision, JsonResponse.class);

			ObjectMapper mapper = new ObjectMapper();

			List<ManageEmployeeAddressModel> addreq = mapper.convertValue(jsonResponse.getBody(),
					new TypeReference<List<ManageEmployeeAddressModel>>() {
					});

			for (ManageEmployeeAddressModel m : addreq) {
				if (m.getStatus().equals("1")) {
					m.setStatusId("Active");
				} else if (m.getStatus().equals("0")) {
					m.setStatusId("Inactive");
				}
			}

			jsonResponse.setBody(addreq);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method ; empLocationThroughAjax ends");
		return jsonResponse.getBody();
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@PostMapping("/view-manage-employee-workdetails-save")
	public @ResponseBody JsonResponse<Object> saveemployeeworkdetails(
			@RequestBody ManageEmployeeWorkdetailsModel manageEmployeeWorkdetailsModel, HttpSession session) {
		logger.info("Method : saveemployeeworkdetails starts");

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
			e.printStackTrace();
		}

		manageEmployeeWorkdetailsModel.setCreatedBy(userId);
		manageEmployeeWorkdetailsModel.setOrganization(organization);
		manageEmployeeWorkdetailsModel.setOrgDivision(orgDivision);

		if (manageEmployeeWorkdetailsModel.getStartDate() != null
				&& manageEmployeeWorkdetailsModel.getStartDate() != "") {
			manageEmployeeWorkdetailsModel.setStartDate(
					DateFormatter.inputDateFormat(manageEmployeeWorkdetailsModel.getStartDate(), dateFormat));
		}
		if (manageEmployeeWorkdetailsModel.getEndDate() != null && manageEmployeeWorkdetailsModel.getEndDate() != "") {
			manageEmployeeWorkdetailsModel
					.setEndDate(DateFormatter.inputDateFormat(manageEmployeeWorkdetailsModel.getEndDate(), dateFormat));
		}

		try {
			resp = restClient.postForObject(env.getEmployeeUrl() + "saveemployeeworkdetails",
					manageEmployeeWorkdetailsModel, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("resp@@@@@@@@"+resp);
		JSONObject jsonQr = new JSONObject();

		ObjectMapper mapper = new ObjectMapper();
		ManageEmployeeWorkdetailsModel emp = mapper.convertValue(resp.getBody(), new TypeReference<ManageEmployeeWorkdetailsModel>() {
		});
		if (emp.getEmployeeId() == null || emp.getEmployeeId() == "") {
			logger.info("Method : qrcode cannotbe generated");

		} else {
			String fullName = emp.getEmployeeName();
			jsonQr.put("EmployeeId", emp.getEmployeeId());
			jsonQr.put("Employee Name", fullName);
			jsonQr.put("Department", emp.getDepartment());
			jsonQr.put("Organization", emp.getOrganization());
			jsonQr.put("Devision", emp.getOrgDivision());

			String qrdata = jsonQr.toString();

			try {
				String qrCodeData = qrdata;
				String qrName = "QR" + emp.getEmployeeId() + ".png";
				String filePath = env.getStaffQrCode() + qrName;

				String charset = "UTF-8";// "ISO-8859-1";

				Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();

				hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
				BitMatrix matrix = new MultiFormatWriter().encode(new String(qrCodeData.getBytes(charset), charset),
						BarcodeFormat.QR_CODE, 200, 200, hintMap);
				MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath.lastIndexOf('.') + 1),
						new File(filePath));

				jsonQr.keySet().clear();
				logger.info("Method : qrcode function Ends");

			} catch (Exception e) {
				System.err.println(e);
			}

		}
		
		if (resp.getCode().equals("success")) {
			session.removeAttribute("employeePFile");
			resp.setMessage("Success");
		} else {

		}

		logger.info("Method : saveemployeeworkdetails starts");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-manage-employee-work-ajax")
	public @ResponseBody List<ManageEmployeeWorkdetailsModel> viewmanageemployeeworkajax(HttpSession session,
			@RequestParam String id) {
		logger.info("Method : viewmanageemployeeworkajax starts");
		String organization = "";
		String orgDivision = "";

		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<List<ManageEmployeeWorkdetailsModel>> jsonResponse = new JsonResponse<List<ManageEmployeeWorkdetailsModel>>();

		try {
			jsonResponse = restClient.getForObject(env.getEmployeeUrl() + "viewEmployeework?id=" + id + "&organization="
					+ organization + "&orgDivision=" + orgDivision, JsonResponse.class);

			ObjectMapper mapper = new ObjectMapper();

			List<ManageEmployeeWorkdetailsModel> addreq = mapper.convertValue(jsonResponse.getBody(),
					new TypeReference<List<ManageEmployeeWorkdetailsModel>>() {
					});

			String dateFormat = (String) (session).getAttribute("DATEFORMAT");
			for (ManageEmployeeWorkdetailsModel m : addreq) {
				if (m.getManagerName() == null || m.getManagerName() == "" || m.getManagerName() == "null") {
					m.setManagerName("");
				}
				if (m.getStartDate() != null && m.getStartDate() != "") {
					m.setStartDate(DateFormatter.dateFormat(m.getStartDate(), dateFormat));
				}
				if (m.getEndDate() != null && m.getEndDate() != "") {
					m.setEndDate(DateFormatter.dateFormat(m.getEndDate(), dateFormat));
				}

			}

			jsonResponse.setBody(addreq);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method ; viewmanageemployeeworkajax ends");

		return jsonResponse.getBody();
	}

	@SuppressWarnings("unchecked")
	@PostMapping("view-manage-employee-delete-address-emp")
	public @ResponseBody JsonResponse<Object> deleteempaddress(Model model, @RequestParam String id,
			HttpSession session) {
		logger.info("Method : delectRequistion starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String createdBy = "";

		try {
			createdBy = (String) session.getAttribute("USER_ID");
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			resp = restClient.getForObject(env.getEmployeeUrl() + "deleteaddressemp?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("success");
		}

		logger.info("Method :  deleteempaddress ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/view-manage-employee-dependent-save")
	public @ResponseBody JsonResponse<Object> saveemployeedependent(
			@RequestBody ManageEmployeeDependentModel manageEmployeeDependentModel, HttpSession session) {
		logger.info("Method : saveemployeedependent starts");

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
			e.printStackTrace();
		}

		manageEmployeeDependentModel.setCreatedBy(userId);
		manageEmployeeDependentModel.setOrganization(organization);
		manageEmployeeDependentModel.setOrgDivision(orgDivision);
		try {
			resp = restClient.postForObject(env.getEmployeeUrl() + "saveemployeedependent",
					manageEmployeeDependentModel, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			session.removeAttribute("employeePFile");
			resp.setMessage("Success");

		}


		logger.info("Method : saveemployeedependent ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-manage-employee-dependent-ajax")
	public @ResponseBody List<ManageEmployeeDependentModel> viewmanageemployeedependent(HttpSession session,
			@RequestParam String id) {
		logger.info("Method : viewmanageemployeedependent starts");
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<List<ManageEmployeeDependentModel>> jsonResponse = new JsonResponse<List<ManageEmployeeDependentModel>>();

		try {
			jsonResponse = restClient.getForObject(env.getEmployeeUrl() + "viewempdepent?id=" + id + "&organization="
					+ organization + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method ; viewmanageemployeedependent ends");

		return jsonResponse.getBody();
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/view-manage-employee-bankdetails-save")
	public @ResponseBody JsonResponse<Object> saveemployeebank(
			@RequestBody ManageEmployeeBankDetailsModel manageEmployeeBankDetailsModel, HttpSession session) {
		logger.info("Method : saveemployeebank starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		manageEmployeeBankDetailsModel.setCreatedBy(userId);
		manageEmployeeBankDetailsModel.setOrganization(organization);
		manageEmployeeBankDetailsModel.setOrgDivision(orgDivision);

		try {
			resp = restClient.postForObject(env.getEmployeeUrl() + "saveemployeebank", manageEmployeeBankDetailsModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			session.removeAttribute("employeePFile");
			resp.setMessage("Success");
		}


		logger.info("Method : saveemployeebank starts");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-manage-employee-bankdetails-ajax")
	public @ResponseBody List<ManageEmployeeBankDetailsModel> viewmanageemployeebankdetails(Model model,
			HttpServletRequest request, @RequestParam String id, HttpSession session) {
		logger.info("Method : viewmanageemployeebankdetails starts");
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<List<ManageEmployeeBankDetailsModel>> jsonResponse = new JsonResponse<List<ManageEmployeeBankDetailsModel>>();

		try {

			jsonResponse = restClient.getForObject(env.getEmployeeUrl() + "viewebank?id=" + id + "&organization="
					+ organization + "&orgDivision=" + orgDivision, JsonResponse.class);

			ObjectMapper mapper = new ObjectMapper();

			List<ManageEmployeeBankDetailsModel> addreq = mapper.convertValue(jsonResponse.getBody(),
					new TypeReference<List<ManageEmployeeBankDetailsModel>>() {
					});

			jsonResponse.setBody(addreq);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method ; viewmanageemployeedependent ends");

		return jsonResponse.getBody();
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/view-manage-employee-insurancedetails-save")
	public @ResponseBody JsonResponse<Object> saveempinsurance(
			@RequestBody ManageEmployeeInsuranceModel manageEmployeeInsuranceModel, HttpSession session) {
		logger.info("Method : saveempinsurance starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";
		String dateFormat = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			dateFormat = (String) session.getAttribute("DATEFORMAT");
		} catch (Exception e) {
			e.printStackTrace();
		}

		manageEmployeeInsuranceModel.setCreatedBy(userId);

		if (manageEmployeeInsuranceModel.getEifromdate() != null
				&& manageEmployeeInsuranceModel.getEifromdate() != "") {
			manageEmployeeInsuranceModel.setEifromdate(
					DateFormatter.inputDateFormat(manageEmployeeInsuranceModel.getEifromdate(), dateFormat));
		}
		if (manageEmployeeInsuranceModel.getEitodate() != null && manageEmployeeInsuranceModel.getEitodate() != "") {
			manageEmployeeInsuranceModel
					.setEitodate(DateFormatter.inputDateFormat(manageEmployeeInsuranceModel.getEitodate(), dateFormat));
		}

		try {
			resp = restClient.postForObject(env.getEmployeeUrl() + "saveempinsurance", manageEmployeeInsuranceModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			session.removeAttribute("employeePFile");
			resp.setMessage("Success");
		}


		logger.info("Method : saveempinsurance starts");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/view-manage-employee-benifit")
	public @ResponseBody JsonResponse<Object> saveempbenifit(
			@RequestBody ManageEmployeeBenifitModel manageEmployeeBenifitModel, HttpSession session) {
		logger.info("Method : saveempbenifit starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		manageEmployeeBenifitModel.setCreatedby(userId);

		try {
			resp = restClient.postForObject(env.getEmployeeUrl() + "savebenifit", manageEmployeeBenifitModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			session.removeAttribute("employeePFile");
			resp.setMessage("Success");
		}


		logger.info("Method : saveempbenifit starts");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-manage-employee-benifit-ajax")
	public @ResponseBody JsonResponse<List<ManageEmployeeBenifitModel>> viewempbenifit(Model model,
			HttpServletRequest request, @RequestParam String id, HttpSession session) {
		logger.info("Method : ManageEmployeeBenifitModel starts");

		JsonResponse<List<ManageEmployeeBenifitModel>> jsonResponse = new JsonResponse<List<ManageEmployeeBenifitModel>>();

		try {
			String organization = "";
			String orgDivision = "";
			try {
				organization = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			} catch (Exception e) {
				e.printStackTrace();
			}

			jsonResponse = restClient.getForObject(env.getEmployeeUrl() + "viewbenifit?empid=" + id + "&organization="
					+ organization + "&orgDivision=" + orgDivision, JsonResponse.class);

			ObjectMapper mapper = new ObjectMapper();

			List<ManageEmployeeBenifitModel> addreq = mapper.convertValue(jsonResponse.getBody(),
					new TypeReference<List<ManageEmployeeBenifitModel>>() {
					});

			jsonResponse.setBody(addreq);

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (jsonResponse.getCode().equals("success")) {
			jsonResponse.setMessage("Success");
		} else {

		}

		logger.info("Method ; ManageEmployeeBenifitModel ends");

		return jsonResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-manage-employee-insurancedetails-ajax")
	public @ResponseBody List<ManageEmployeeInsuranceModel> viewmanageemployeeinsurancedetails(HttpSession session,
			@RequestParam String id) {
		logger.info("Method : viewmanageemployeeinsurancedetails starts");
		String organization = "";
		String orgDivision = "";

		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}
		JsonResponse<List<ManageEmployeeInsuranceModel>> jsonResponse = new JsonResponse<List<ManageEmployeeInsuranceModel>>();

		try {
			logger.info(env.getEmployeeUrl() + "vieweinsurance?id=" + id);
			jsonResponse = restClient.getForObject(env.getEmployeeUrl() + "vieweinsurance?id=" + id + "&organization="
					+ organization + "&orgDivision=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method ; viewmanageemployeeinsurancedetails ends");

		return jsonResponse.getBody();
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/view-manage-employee-master-edit")
	public @ResponseBody JsonResponse<Object> editmanageEmployeemaster(@RequestParam String employeeId,
			HttpSession session) {
		logger.info("Method : editmanageEmployeemaster starts");

		String organization = "";
		String orgDivision = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getEmployeeUrl() + "editmanageEmployeemasterById?id=" + employeeId
					+ "&organization=" + organization + "&orgDivision=" + orgDivision, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getCode().equals("success")) {
			resp.setMessage("Success");
		} else {
			resp.setMessage("Unsuccess");
		}
		logger.info("Method : editmanageEmployeemaster end");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/view-manage-employee-address-delete")
	public @ResponseBody JsonResponse<Object> deleteAddress(Model model, HttpSession session, @RequestParam String id) {

		logger.info("Method : deleteAddress starts");
		String organization = "";
		String orgDivision = "";

		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			resp = restClient.getForObject(env.getEmployeeUrl() + "deleteAddress?id=" + id + "&organization="
					+ organization + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getCode().equals("success")) {
			resp.setMessage("Success");
		} else {

		}

		logger.info("Method : deleteAddress ends");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/view-manage-employee-work-delete")
	public @ResponseBody JsonResponse<Object> deletework(Model model, HttpSession session, @RequestParam String id) {

		logger.info("Method : deletework starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			resp = restClient.getForObject(env.getEmployeeUrl() + "deletework?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getCode().equals("success")) {
			resp.setMessage("Success");
		} else {

		}

		logger.info("Method : deletework ends");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/view-manage-employee-dependent-delete")
	public @ResponseBody JsonResponse<Object> deletedependent(Model model, HttpSession session,
			@RequestParam String id) {

		logger.info("Method : deletedependent starts");
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getEmployeeUrl() + "rest-deletedependent?id=" + id + "&organization="
					+ organization + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() == "" || resp.getMessage() == null) {

			resp.setMessage("Success");
		}

		logger.info("Method : deletedependent ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/view-manage-employee-bank-delete")
	public @ResponseBody JsonResponse<Object> deletebank(Model model, HttpSession session, @RequestParam String id) {

		logger.info("Method : deletebank starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getEmployeeUrl() + "deletebank?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() == "" || resp.getMessage() == null) {

			resp.setMessage("Success");
		}

		logger.info("Method : deletebank ends");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/view-manage-employee-insurancedetails-delete")
	public @ResponseBody JsonResponse<Object> deleteinsurance(Model model, HttpSession session,
			@RequestParam String id) {

		logger.info("Method : deleteinsurance starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getEmployeeUrl() + "deleteinsurance?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() == "" || resp.getMessage() == null) {

			resp.setMessage("Success");
		}

		logger.info("Method : deleteinsurance ends");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-manage-employee-team-list")
	public @ResponseBody JsonResponse<List<DropDownModel>> getTeam(Model model, @RequestParam String id,
			HttpSession session) {
		logger.info("Method : getTeam starts");

		JsonResponse<List<DropDownModel>> resp = new JsonResponse<List<DropDownModel>>();
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restClient.getForObject(env.getEmployeeUrl() + "getTeam?id=" + id + "&organization=" + organization
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method :  getTeam ends");
		return resp;
	}

	public String saveAllImage(byte[] imageBytes) {
		logger.info("Method : saveAllImage starts");

		String imageName = null;

		try {
			if (imageBytes != null) {
				long nowTime = new Date().getTime();
				imageName = nowTime + ".png";
			}

			Path path = Paths.get(env.getFileUploadProcurement() + imageName);
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

					Path pathThumb = Paths.get(env.getFileUploadProcurement() + "thumb/" + imageName);
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

			Path path = Paths.get(env.getFileUploadProcurement() + pdfName);
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

			Path path = Paths.get(env.getFileUploadProcurement() + pdfName);
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

			Path path = Paths.get(env.getFileUploadProcurement() + pdfName);
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

			Path path = Paths.get(env.getFileUploadProcurement() + pdfName);
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

			Path path = Paths.get(env.getFileUploadProcurement() + pdfName);
			if (imageBytes != null) {
				Files.write(path, imageBytes);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveAllDoc ends");
		return pdfName;
	}

	// New Employee Form
	@GetMapping("/new-employee")
	public String newemployeeform(Model model, HttpSession session) {

		logger.info("Method : newemployeeform starts");

		try {
			DropDownModel[] Gender = restClient.getForObject(env.getEmployeeUrl() + "getgenderList1",
					DropDownModel[].class);
			List<DropDownModel> genderTypeList = Arrays.asList(Gender);

			model.addAttribute("genderTypeList", genderTypeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] Nationality = restClient.getForObject(env.getEmployeeUrl() + "getnationalityList1",
					DropDownModel[].class);
			List<DropDownModel> nationalityList = Arrays.asList(Nationality);

			model.addAttribute("nationalityList", nationalityList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] BloodGroup = restClient.getForObject(env.getEmployeeUrl() + "getbloodgroupList1",
					DropDownModel[].class);
			List<DropDownModel> bloodgroupList = Arrays.asList(BloodGroup);

			model.addAttribute("bloodgroupList", bloodgroupList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] MaritalStatus = restClient.getForObject(env.getEmployeeUrl() + "getmaritalstatusList1",
					DropDownModel[].class);
			List<DropDownModel> maritalstatusList = Arrays.asList(MaritalStatus);

			model.addAttribute("maritalstatusList", maritalstatusList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] Country = restClient.getForObject(env.getEmployeeUrl() + "getCountryList",
					DropDownModel[].class);
			List<DropDownModel> counntryList = Arrays.asList(Country);

			model.addAttribute("counntryList", counntryList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] offerletter = restClient.getForObject(env.getEmployeeUrl() + "OfferletterList",
					DropDownModel[].class);
			List<DropDownModel> offerletterList = Arrays.asList(offerletter);
			model.addAttribute("offerletterList", offerletterList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : newemployeeform ends");
		return "employee/newEmployeeForm";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/new-employee-getCandidateDetails")
	public @ResponseBody JsonResponse<List<CandidateDetailsModel>> getCandidateDetails(HttpSession session,
			String candidateId) {
		logger.info("Method : getCandidateDetails starts");

		JsonResponse<List<CandidateDetailsModel>> resp = new JsonResponse<List<CandidateDetailsModel>>();

		try {
			resp = restClient.getForObject(env.getRecruitment() + "editCandidates?id=" + candidateId,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		List<CandidateDetailsModel> empDetails = mapper.convertValue(resp.getBody(),
				new TypeReference<List<CandidateDetailsModel>>() {
				});
		if (empDetails.get(0).getFileUpload() != null && empDetails.get(0).getFileUpload() != ""
				&& !empDetails.get(0).getFileUpload().equals("null")) {
			String fileEmployeeimg = env.getBaseURL() + "document/employee/" + empDetails.get(0).getFileUpload();

			empDetails.get(0).setFileUpload(fileEmployeeimg);

		}

		String dateFormat = (String) (session).getAttribute("DATEFORMAT");

		for (CandidateDetailsModel m : empDetails) {
			if (m.getDob() != null && m.getDob() != "") {
				String date = DateFormatter.dateFormat(m.getDob(), dateFormat);
				m.setDob(date);
			}
			if (m.getJoiningDate() != null && m.getJoiningDate() != "") {
				String jdate = DateFormatter.dateFormat(m.getJoiningDate(), dateFormat);
				m.setJoiningDate(jdate);
			}
		}

		resp.setBody(empDetails);

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}
		logger.info("resp====" + resp);
		logger.info("Method : getCandidateDetails ends");
		return resp;
	}

	/*********** Save new employee details ***********/
	@SuppressWarnings({ "unchecked", "deprecation" })
	@PostMapping("/new-employee-save-details")
	public @ResponseBody JsonResponse<Object> savenewemployee(@RequestBody ManageEmployeeModel manageEmployeeModel,
			HttpSession session) {
		logger.info("Method : savenewemployee personalMaster starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";
		String dateFormat = "";
		String pass = "$2a$10$SkYOZORZ4PUSURzL1fmvk.RcUwCfLk/R826sxBXKx/ZZyoQvkcaa6";
		// String pass = "";
		String organization = "";
		String orgDivision = "";
		String qrName = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		manageEmployeeModel.setPassword(pass);
		manageEmployeeModel.setCreatedBy(userId);
		manageEmployeeModel.setOrganization(organization);
		manageEmployeeModel.setOrgDivision(orgDivision);
		if (manageEmployeeModel.getEmployeeId() != null && manageEmployeeModel.getEmployeeId() != "") {
			logger.info("Inside if");
			qrName = "QR" + manageEmployeeModel.getEmployeeId() + ".png";
			manageEmployeeModel.setQrCode(qrName);
		} else {
			manageEmployeeModel.setQrCode("");
		}

		MultipartFile inputFile = (MultipartFile) session.getAttribute("employeePFile");
		byte[] bytes;
		String imageName = null;

		if (inputFile != null) {
			try {
				bytes = inputFile.getBytes();
				String[] fileType = inputFile.getContentType().split("/");
				imageName = saveAllImage(bytes, fileType[1]);
				// logger.info(imageName);

				manageEmployeeModel.setFileEmployeeimg(imageName);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		logger.info("manageEmployeeModel====" + manageEmployeeModel);
		try {
			resp = restClient.postForObject(env.getEmployeeUrl() + "saveNewEmployeeMaster", manageEmployeeModel,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		JSONObject jsonQr = new JSONObject();

		ObjectMapper mapper = new ObjectMapper();
		ManageEmployeeModel emp = mapper.convertValue(resp.getBody(), new TypeReference<ManageEmployeeModel>() {
		});
		if (emp.getEmployeeId() == null || emp.getEmployeeId() == "") {
			logger.info("Method : qrcode cannotbe generated");

		} else {
			String fullName = emp.getFirstName() + " " + emp.getLastName();
			jsonQr.put("EmployeeId", emp.getEmployeeId());
			jsonQr.put("Employee Name", fullName);
			jsonQr.put("Department", emp.getDepartment());
			jsonQr.put("Organization", emp.getOrganization());
			jsonQr.put("Devision", emp.getOrgDivision());

			String qrdata = jsonQr.toString();

			try {
				String qrCodeData = qrdata;
				qrName = "QR" + emp.getEmployeeId() + ".png";
				String filePath = env.getStaffQrCode() + qrName;

				String charset = "UTF-8";// "ISO-8859-1";

				Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();

				hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
				BitMatrix matrix = new MultiFormatWriter().encode(new String(qrCodeData.getBytes(charset), charset),
						BarcodeFormat.QR_CODE, 200, 200, hintMap);
				MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath.lastIndexOf('.') + 1),
						new File(filePath));

				jsonQr.keySet().clear();
				logger.info("Method : qrcode function Ends");

			} catch (Exception e) {
				System.err.println(e);
			}

		}

		logger.info("resp====" + resp);
		if (resp.getCode().equals("success")) {
			session.removeAttribute("employeePFile");
		}
		logger.info("Method : savenewemployee personalMaster End");
		return resp;
	}

	/**
	 * Function for save new password
	 *
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/view-manage-employee-resetPassword")
	public @ResponseBody JsonResponse<Object> saveNewPassword(HttpSession session,
			@RequestBody DropDownModel dropDownModel) {
		logger.info("Method : saveResetPassword starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		userId = (String) session.getAttribute("USER_ID");
		dropDownModel.setKey(userId);
		String pass = "";
		pass = (String) session.getAttribute("USER_PASSWORD");
		Boolean validity = true;

		if (dropDownModel.getName().equals(dropDownModel.getCode())) {
			validity = false;
		}
		if (validity) {
			try {
				if (passEncoder.matches(dropDownModel.getName(), pass)) {

					resp = restClient.postForObject(env.getEmployeeUrl() + "resetPassword", dropDownModel,
							JsonResponse.class);
					ObjectMapper mapper = new ObjectMapper();

					DropDownModel seat = mapper.convertValue(resp.getBody(), new TypeReference<DropDownModel>() {
					});
					resp.setBody(seat);
				} else {
					resp.setCode("failed");
					resp.setMessage("Old password not match please try again");
				}
			} catch (Exception e) {

				e.printStackTrace();
			}
		} else {
			resp.setCode("failed");
			resp.setMessage("New password can't be same as old password");
		}
		/*
		 * try {
		 * 
		 * resp = restClient.postForObject(env.getEmployeeUrl() + "resetPassword",
		 * dropDownModel, JsonResponse.class); //ObjectMapper mapper = new
		 * ObjectMapper();
		 * 
		 * 
		 * DropDownModel seat = mapper.convertValue(resp.getBody(), new
		 * TypeReference<DropDownModel>() { }); resp.setBody(seat);
		 * 
		 * } catch (Exception e) {
		 * 
		 * e.printStackTrace(); }
		 */

		logger.info("Method : saveResetPassword ends");
		logger.info("resp===" + resp);
		return resp;
	}

	/*
	 * ############################################################################
	 */
	// Edit Address
	@SuppressWarnings("unchecked")
	@GetMapping("view-manage-employee-address-edit")
	public @ResponseBody JsonResponse<ManageEmployeeAddressModel> editAddressDetails(@RequestParam String addressId,
			String addressType, HttpSession session) {

		logger.info("Method : editAddressDetails starts");
		JsonResponse<ManageEmployeeAddressModel> jsonResponse = new JsonResponse<ManageEmployeeAddressModel>();
		String organization = "";
		String orgDivision = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			jsonResponse = restClient.getForObject(
					env.getEmployeeUrl() + "rest-editAddressDetails?addressId=" + addressId + "&addressType="
							+ addressType + "&organization=" + organization + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");
		} else {
			jsonResponse.setMessage("Success");
		}
		logger.info("Method : editAddressDetails ends");
		logger.info("editAddressDetails=====" + jsonResponse);
		return jsonResponse;
	}

	// Edit Insurance
	@SuppressWarnings("unchecked")
	@GetMapping("view-manage-employee-insurance-edit")
	public @ResponseBody JsonResponse<ManageEmployeeInsuranceModel> editInsurance(@RequestParam String Id,
			HttpSession session) {

		logger.info("Method : editInsurance starts");
		JsonResponse<ManageEmployeeInsuranceModel> jsonResponse = new JsonResponse<ManageEmployeeInsuranceModel>();
		logger.info("id====" + Id);
		String organization = "";
		String orgDivision = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			jsonResponse = restClient.getForObject(env.getEmployeeUrl() + "rest-editInsurance?id=" + Id
					+ "&organization=" + organization + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");
		} else {
			jsonResponse.setMessage("Success");
		}
		logger.info("Method : editInsurance ends");
		logger.info("editInsurance=====" + jsonResponse);
		return jsonResponse;
	}

	// Edit Work Details
	@SuppressWarnings("unchecked")
	@GetMapping("view-manage-employee-work-edit")
	public @ResponseBody JsonResponse<ManageEmployeeWorkdetailsModel> editWorkDetails(@RequestParam String id,
			HttpSession session) {

		logger.info("Method : editWorkDetails starts");
		JsonResponse<ManageEmployeeWorkdetailsModel> jsonResponse = new JsonResponse<ManageEmployeeWorkdetailsModel>();
		logger.info("id====" + id);
		String organization = "";
		String orgDivision = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			jsonResponse = restClient.getForObject(env.getEmployeeUrl() + "rest-editWorkDetail?id=" + id

					+ "&organization=" + organization + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");
		} else {
			jsonResponse.setMessage("Success");
		}
		logger.info("Method : editWorkDetails ends");
		logger.info("editWorkDetails=====" + jsonResponse);
		return jsonResponse;
	}

	// Edit dependent
	@SuppressWarnings("unchecked")
	@GetMapping("view-manage-employee-dependant-edit")
	public @ResponseBody JsonResponse<ManageEmployeeDependentModel> editDependentDetails(@RequestParam String id,
			HttpSession session) {

		logger.info("Method : editDependentDetails starts");
		JsonResponse<ManageEmployeeDependentModel> jsonResponse = new JsonResponse<ManageEmployeeDependentModel>();
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			jsonResponse = restClient.getForObject(env.getEmployeeUrl() + "rest-editDependentDetails?id=" + id
					+ "&organization=" + organization + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");
		} else {
			jsonResponse.setMessage("Success");
		}
		logger.info("Method : editDependentDetails ends");
		logger.info("editDependentDetails=====" + jsonResponse);
		return jsonResponse;
	}

	// Edit dependent
	@SuppressWarnings("unchecked")
	@GetMapping("view-manage-employee-bank-edit")
	public @ResponseBody JsonResponse<ManageEmployeeBankDetailsModel> editBankDetails(@RequestParam String id,
			HttpSession session) {

		logger.info("Method : editBankDetails starts");
		JsonResponse<ManageEmployeeBankDetailsModel> jsonResponse = new JsonResponse<ManageEmployeeBankDetailsModel>();
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			jsonResponse = restClient.getForObject(env.getEmployeeUrl() + "rest-editBankDetails?id=" + id
					+ "&organization=" + organization + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");
		} else {
			jsonResponse.setMessage("Success");
		}
		logger.info("Method : editBankDetails ends");
		logger.info("editBankDetails=====" + jsonResponse);
		return jsonResponse;
	}

	@PostMapping("/view-manage-employee-doc-upload-file")
	public @ResponseBody JsonResponse<Object> uploadDocFile(@RequestParam("file") MultipartFile inputFile,
			HttpSession session) {
		logger.info("Method : employee uploadDocFile controller  starts");

		JsonResponse<Object> response = new JsonResponse<Object>();

		try {
			response.setMessage(inputFile.getOriginalFilename());
			session.setAttribute("employeeDocFile", inputFile);

		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : employee uploadDocFile controller ' ends");
		return response;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/view-manage-employee-document-save")
	public @ResponseBody JsonResponse<Object> addEmpDocument(@RequestBody ManageEmployeeDocumentModel docModel,
			Model model, HttpSession session) {
		logger.info("Method :addEmpDocument starts");

		logger.info("@@@@@@@@@@@@@@@@" + docModel);

		MultipartFile inputFile = (MultipartFile) session.getAttribute("employeeDocFile");
		logger.info("inputFile=====" + inputFile);
		byte[] bytes;
		String imageName = null;
		if (inputFile != null) {
			try {
				bytes = inputFile.getBytes();
				String[] fileType = inputFile.getContentType().split("/");
				imageName = saveAllImage(bytes, fileType[1]);

				logger.info("imageName====" + imageName);

				docModel.setDocumentName(imageName);
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
		docModel.setOrganization(orgName);
		docModel.setOrgDivision(orgDivision);
		logger.info("docModel====" + docModel);
		try {
			resp = restClient.postForObject(env.getEmployeeUrl() + "rest-addEmpDocument", docModel, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String message = resp.getMessage();
		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
			session.removeAttribute("employeeDocFile");
		}
		logger.info("Method : addEmpDocument ends");
		logger.info(resp.toString());
		return resp;
	}
	// View Employee Document

	@SuppressWarnings("unchecked")
	@GetMapping("view-manage-employee-document-ajax")
	public @ResponseBody List<ManageEmployeeDocumentModel> viewmanageemployeedocumentdetails(HttpSession session,
			@RequestParam String id) {
		logger.info("Method : viewmanageemployeedocumentdetails starts");
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		JsonResponse<List<ManageEmployeeDocumentModel>> jsonResponse = new JsonResponse<List<ManageEmployeeDocumentModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getEmployeeUrl() + "viewedocument?id=" + id + "&organization="
					+ organization + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method ; viewmanageemployeedocumentdetails ends");
		return jsonResponse.getBody();
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/view-manage-employee-document-delete")
	public @ResponseBody JsonResponse<Object> deleteEmpDoc(Model model, HttpSession session,
			@RequestParam String docType, String empid) {
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
		logger.info("empid===" + empid);
		try {
			resp = restClient.getForObject(env.getEmployeeUrl() + "deleteEmpDoc?docType=" + docType + "&empid=" + empid
					+ "&organization=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
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

	// Edit dependent
	@SuppressWarnings("unchecked")
	@GetMapping("view-manage-employee-document-edit")
	public @ResponseBody JsonResponse<ManageEmployeeDocumentModel> editDocumentDetails(@RequestParam String docType,
			String empid, HttpSession session) {

		logger.info("Method : editDocumentDetails starts");
		JsonResponse<ManageEmployeeDocumentModel> jsonResponse = new JsonResponse<ManageEmployeeDocumentModel>();
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			jsonResponse = restClient
					.getForObject(
							env.getEmployeeUrl() + "rest-editDocumentDetails?docType=" + docType + "&empid=" + empid
									+ "&organization=" + organization + "&orgDivision=" + orgDivision,
							JsonResponse.class);
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
		logger.info("editDocumentDetails=====" + jsonResponse);
		return jsonResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "view-manage-employee-documenttype-list" })
	public @ResponseBody JsonResponse<Object> getDocumentList(HttpSession session, @RequestParam String empid) {
		logger.info("Method : getDocumentList starts");
		String orgName = "";
		String orgDivision = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getEmployeeUrl() + "get-getDocumentTypeList?empid=" + empid + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getCode().equals("success")) {
			res.setMessage("success");
		} else {
			res.setMessage("Unsuccess");
		}

		logger.info("Method : getDocumentList ends");

		logger.info("LISTTTT" + res);
		return res;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-idcard-pdf-downloadsc")
	public void getDcPdfDetails1(HttpServletResponse response, Model model, HttpSession session,
			@RequestParam("dcId") String encodedParam1,@RequestParam("qrurl") String encodedParam2) {

		logger.info("Method : getDcPdfDetails starts");
		System.out.println("JsonResponse====BBBBBBBBHHHHHHHHHHHAAAAAAABBBBBBBBBBAAAAAAAAANNNNNNNNNIIIIIIIIII");
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		byte[] encodeByte1 = Base64.getDecoder().decode(encodedParam1.getBytes());
		String dcId = (new String(encodeByte1));

		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			resp = restClient.getForObject(env.getEmployeeUrl() + "getIdCardDetails?id=" + dcId
					+ "&organization=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("JsonResponse=vvvvvvvvvvvvvvvvvvvv===" +resp.getBody());

		List<ManageEmployeeModel> listModel = new ArrayList<ManageEmployeeModel>();
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			listModel = mapper.readValue(resp.getBody().toString(), List.class);
		} catch (JsonParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (JsonMappingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
 
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("idcard", listModel);
 
		System.out.println("data====" + data);
		String logo = "classpath:static/assets/images/invoice-banner.jpg";
		data.put("logo", logo);
		
		String qrcode = encodedParam2;
		
		data.put("qrcode", qrcode);
 
		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=ID Card.pdf");
		File file;
		byte[] fileData = null;
		try {
			file = pdfGeneratorUtil.createPdf("employee/employee-idcard.html", data);
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

		logger.info("Method : getDcPdfDetails ends");
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-idcard-pdf-downloads")
	public void getDcPdfDetails(HttpServletResponse response, Model model, HttpSession session,
			@RequestParam("dcId") String encodedParam1,@RequestParam("qrurl") String encodedParam2) {


		logger.info("Method : getDcPdfDetails starts");
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		byte[] encodeByte1 = Base64.getDecoder().decode(encodedParam1.getBytes());
		String dcId = (new String(encodeByte1));
 
		
		List<ManageEmployeeModel> productList = new ArrayList<ManageEmployeeModel>();
		try {
			ManageEmployeeModel[] manageEmployeeModel = restClient.getForObject(env.getEmployeeUrl() + "getIdCardDetails?id=" + dcId
					+ "&organization=" + orgName + "&orgDivision=" + orgDivision,
					ManageEmployeeModel[].class);
			productList = Arrays.asList(manageEmployeeModel);
			//productList.forEach(s -> s.setSlNo(s.getSlNo()));
			int count = 0;
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		/*
		 * CustomerNewModel reimModel = mapper.convertValue(jsonResponse.getBody(), new
		 * TypeReference<CustomerNewModel>() { });
		 */

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("idcard", productList);

		// String logo = "";
		String logo = (String) session.getAttribute("ORGANIZATION_LOGO");
		data.put("logo", logo);

		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=salesInvoice.pdf");
		File file;
		byte[] fileData = null;
		try {
			file = pdfGeneratorUtil.createPdf("employee/employee-idcard.html", data);
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
		System.out.println("hhhhhhhiiiiiiiiiiiiiiiiiiii"+data);
		logger.info("Method : getDcPdfDetails ends");
	}

}