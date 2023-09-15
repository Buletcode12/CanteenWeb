package nirmalya.aathithya.webmodule.EDMS.controller;

import java.io.File;


import java.util.Arrays;
import java.util.List;
import java.util.Map;

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


import nirmalya.aathithya.webmodule.EDMS.model.WorkSpaceModel;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping(value = "edms")
public class EdmsWorkspaceController {

	Logger logger = LoggerFactory.getLogger(EdmsWorkspaceController.class);

	RestTemplate restClient;

	EnvironmentVaribles env;
	
	
	@Autowired
	public EdmsWorkspaceController(EnvironmentVaribles EnvironmentVaribles,RestTemplate RestTemplate) {
		this.env = EnvironmentVaribles;
		this.restClient=RestTemplate;
	}

	@GetMapping(value = { "work-space" })
	public String workspaceDetails(Model model, HttpSession session) {
		logger.info("Method : workspaceDetails starts");
		String userId="";
		String userName="";
		userId = (String) session.getAttribute("USER_ID");
		userName = (String) session.getAttribute("USER_NAME");
		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);

		try {
			DropDownModel[] grade = restClient.getForObject(env.getEdms() + "rest-fileaccess-type",
					DropDownModel[].class);
			List<DropDownModel> accessType = Arrays.asList(grade);
			model.addAttribute("accessType", accessType);
			System.out.println("CatogaryList===>>>" + accessType);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] grade = restClient.getForObject(env.getEdms() + "rest-fileoperation-type",
					DropDownModel[].class);
			List<DropDownModel> operationType = Arrays.asList(grade);
			model.addAttribute("operationType", operationType);
			System.out.println("CatogaryList===>>>" + operationType);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : workspaceDetails ends");
		return "EDMS/workspace";
	}

	@SuppressWarnings({ "unchecked" })
	@PostMapping("work-space-add")
	public @ResponseBody JsonResponse<Object> saveWorkSpaceModel(@RequestBody WorkSpaceModel eventModel,
			HttpSession session) {

		logger.info("Method : saveWorkSpaceModel function starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId;
		String organization="";
		String orgDivision="";
		userId = (String) session.getAttribute("USER_ID");
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		eventModel.setCreatedBy(userId);
		eventModel.setOrganizationName(organization);
		eventModel.setOrganizationDivision(orgDivision);

		System.out.println("userid===>>" + userId);
		eventModel.setCreatedBy(userId);
		System.out.println("===>>>>" + eventModel);
		String nFolder = eventModel.getParentFolderName().concat("\\").concat("\\")
				.concat(eventModel.getNewFolderName());
		String pFolder = eventModel.getParentFolderName();
		System.out.println("NfolderName" + nFolder);
		String path1="C:\\" + nFolder;
		eventModel.setFolderPath(path1);
		

		File file = new File("C:\\" + eventModel.getParentFolderName());
		File file1 = new File("C:\\" + nFolder);

		System.out.println(file);
		System.out.println("Child" + file1);
		String path = "";
		if (file.exists()) {
			path = "1";
		} else {
			path = "0";
		}

		if (path == "1") {
			if (file1.exists()) {
				System.out.println("Child Folder Already Created");
			} else {
				if (file1.mkdir()) {
					System.out.println("Child Folder Created Successfully");
				}
			}
		} else if (path == "0") {
			if (file.mkdir()) {
				System.out.println("Parent Folder Created Successfully");
				if (file1.mkdir()) {
					System.out.println("Child Folder Created Successfully");
				} else {
					System.out.println("Child Not created");
				}
			}
		} else {
			System.out.println("Errors Found");
		}

		try {
			resp = restClient.postForObject(env.getEdms() + "save-workspacemodel", eventModel, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();
		if (message != null && message != "") {
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		System.out.println("resp==>>" + resp);
		logger.info("Method : saveWorkSpaceModel function Ends");
		return resp;
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("work-space-view")
	public @ResponseBody Object workSpaceView(HttpSession session) {

		logger.info("Method :workSpaceView starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		// String Date = DateFormatter.getStringDate(date);
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			resp = restClient.getForObject(env.getEdms() + "rest-workSpace?userId=" + userId + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("workSpaceView-Data>>>>>>>>>>>>>>>>>-----" + resp);
		logger.info("Method :workSpaceView ends");

		return resp;
	}
	@SuppressWarnings("unchecked")
	@GetMapping("work-space-edit")
	public @ResponseBody Object workSpaceEdit(HttpSession session,@RequestParam String id) {

		logger.info("Method :workSpaceEdit starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		// String Date = DateFormatter.getStringDate(date);
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			resp = restClient.getForObject(env.getEdms() + "rest-workSpace-edit?workSpaceId="+id+"&userId=" + userId + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("workSpaceEdit-Data>>>>>>>>>>>>>>>>>-----" + resp);
		logger.info("Method :workSpaceEdit ends");

		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("work-space-delete")
	public @ResponseBody JsonResponse<Object> deleteWorkSpace(@RequestParam String id , HttpSession session) {

		logger.info("Method : deleteWorkSpace starts"+id);

		JsonResponse<Object> response = new JsonResponse<Object>();
		try {
			response = restClient.getForObject(env.getEdms() + "rest-deleteworksapce?id=" +id, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (response.getMessage() != null && response.getMessage() != "") {

		} else {
			response.setMessage("Success");
		}

		System.out.println("REsp" + response);
		logger.info("Method : deleteWorkSpace ends");
		return response;
	}
	
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = "work-space-add-new")
	public @ResponseBody JsonResponse<Object> addNewWorkSpace(@RequestBody Map<String, Object> itm) {
		// Process the received JSON data here (e.g., save it to the database)
		// For simplicity, I'll just print the received data here.
		logger.info("Method : addNewWorkSpace function starts");
		//System.out.println("Received JSON data:" + itm);
		JsonResponse<Object> resp = new JsonResponse<Object>();

		System.out.println(itm.get("workspaceId"));
		String workSpaceId = (String) itm.get("workspaceId");
		
		String nFolder = (String) itm.get("parentFolderName").toString().concat("\\").concat("\\")
				.concat((String) itm.get("newFolderName").toString());
		System.out.println("NfolderName" + nFolder);
		String path1="C:\\" + nFolder;
		

		File file = new File("C:\\" + (String) itm.get("parentFolderName"));
		File file1 = new File("C:\\" + nFolder);

		String path = "";
		if (file.exists()) {
			path = "1";
		} else {
			path = "0";
		}

		if (path == "1") {
			if (file1.exists()) {
				//System.out.println("Child Folder Already Created");
			} else {
				if (file1.mkdir()) {
					//System.out.println("Child Folder Created Successfully");
				}
			}
		} else if (path == "0") {
			if (file.mkdir()) {
				//System.out.println("Parent Folder Created Successfully");
				if (file1.mkdir()) {
					//System.out.println("Child Folder Created Successfully");
				} else {
					//System.out.println("Child Not created");
				}
			}
		} else {
			System.out.println("Errors Found");
		}

		// Convert the received JSON data to a JSON string

		// Rest api call
		try {
			resp = restClient.postForObject(env.getEdms() + "rest-getNew-workspace-add?workSpaceId=" + workSpaceId, itm, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : addNewWorkSpace function starts");
		return resp;
	}

}
