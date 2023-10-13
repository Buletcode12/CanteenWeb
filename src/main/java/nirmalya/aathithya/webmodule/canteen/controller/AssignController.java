package nirmalya.aathithya.webmodule.canteen.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.canteen.model.WebMenuModel;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping(value = "/canteen")
public class AssignController {

	Logger logger = LoggerFactory.getLogger(AssignController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("/assign")
	public String assign(Model model, HttpSession session) {

		logger.info("Method : Manage-assign starts");
		
		
		try {
			DropDownModel[] orderStatus = restClient.getForObject(env.getcanteenUrl() + "/getAssignCatagory",
					DropDownModel[].class);

			List<DropDownModel> incentivedetails = Arrays.asList(orderStatus);
			System.out.println("incentivedetails" + incentivedetails);
			model.addAttribute("incentivedetails", incentivedetails);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//for club member drop down list
		try {
			DropDownModel[] orderStatus = restClient.getForObject(env.getcanteenUrl() + "/getAssignSubCatagiry",
					DropDownModel[].class);

			List<DropDownModel> clubMemberDetails = Arrays.asList(orderStatus);
			System.out.println("clubMemberDetails" + clubMemberDetails);
			model.addAttribute("clubMemberDetails", clubMemberDetails);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] orderStatus = restClient.getForObject(env.getcanteenUrl() + "/getAssignvariants",
					DropDownModel[].class);

			List<DropDownModel> variantDetails = Arrays.asList(orderStatus);
			System.out.println("variantDetails" + variantDetails);
			model.addAttribute("variantDetails", variantDetails);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] comboDetails = restClient.getForObject(env.getcanteenUrl() + "/getAssigncomboDetails",
					DropDownModel[].class);

			List<DropDownModel> comboDetailsData = Arrays.asList(comboDetails);
			System.out.println("comboDetails" + comboDetailsData);
			model.addAttribute("comboDetails", comboDetailsData);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

		logger.info("Method : Manage-assign ends");
		return "canteen/assign";

	}

	//View
		@SuppressWarnings("unchecked")
		@GetMapping("assign/canteen-item-list")
		public @ResponseBody List<WebMenuModel> viewItemList(HttpSession session , String catId ,String subCatId ,String variant ,String combo ) {

			logger.info("Method : viewItemList starts"+variant);

			JsonResponse<List<WebMenuModel>> resp = new JsonResponse<List<WebMenuModel>>();

			try {
				resp = restClient.getForObject(env.getcanteenUrl() + "rest-assign-item-list?CatId="+catId+"&SubCatId="+subCatId+"&variant="+variant+"&combo="+combo, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			ObjectMapper mapper = new ObjectMapper();

			List<WebMenuModel> manageincentiveModel = mapper.convertValue(resp.getBody(),
					new TypeReference<List<WebMenuModel>>() {
					});

			resp.setBody(manageincentiveModel);
			System.out.println("resp.getBody()-----------" + resp.getBody());
			
			if(resp.getMessage() ==null) {
				resp.setMessage("Success");
			}

			logger.info("Method : viewItemList ends"+resp);
			return resp.getBody();
		}
		
	
	
	
	 //View
	@SuppressWarnings("unchecked")
	@GetMapping("assign-throughAjax")
	public @ResponseBody List<WebMenuModel> viewIncentive(HttpSession session) {

		logger.info("Method : viewincentivesDetails starts");

		JsonResponse<List<WebMenuModel>> resp = new JsonResponse<List<WebMenuModel>>();

		try {
			resp = restClient.getForObject(env.getcanteenUrl() + "restViewAssignDetails", JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		List<WebMenuModel> manageincentiveModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<WebMenuModel>>() {
				});

		resp.setBody(manageincentiveModel);
		System.out.println("resp.getBody()-----------" + resp.getBody());

		logger.info("Method : viewincentivesDetails ends");
		return resp.getBody();
	}
	
	
	//searchin
			@SuppressWarnings("unchecked")
			@PostMapping(value = { "assign-menu-list" })
			public @ResponseBody JsonResponse<WebMenuModel> getProductAutoSearchList(Model model,
					@RequestBody String searchValue, BindingResult result, HttpSession session) {
				logger.info("Method : getProductAutoSearchList starts");
				 System.out.println("QuotationNewModel"+searchValue);
				JsonResponse<WebMenuModel> res = new JsonResponse<WebMenuModel>();
				String userId = (String) session.getAttribute("USER_ID");
				String userType = (String) session.getAttribute("USER_ROLETYPE");
				System.out.println("==userid====" + userId);
				System.out.println("==user_roles====" + userType);
				try {

					res = restClient.getForObject(env.getcanteenUrl() + "getProductSassign?id=" + searchValue,
							JsonResponse.class);

				} catch (Exception e) {
					e.printStackTrace();
				}

				if (res.getCode() != null) {
					res.setCode("success");
				} else {
					res.setCode("Unsuccess");
				}
				System.out.println("RESPONSE@@" + res);
				logger.info("Method : getProductAutoSearchList ends");
				return res;
			}
			
			
			//searchin
			@SuppressWarnings("unchecked")
			@PostMapping(value = { "assign-combo-list" })
			public @ResponseBody JsonResponse<WebMenuModel> getComboAutoSearchList(Model model,
					@RequestBody String searchValue, BindingResult result, HttpSession session) {
				logger.info("Method : getComboAutoSearchList starts");
				 System.out.println("QuotationNewModel"+searchValue);
				JsonResponse<WebMenuModel> res = new JsonResponse<WebMenuModel>();
				String userId = (String) session.getAttribute("USER_ID");
				String userType = (String) session.getAttribute("USER_ROLETYPE");
				System.out.println("==userid====" + userId);
				System.out.println("==user_roles====" + userType);
				try {

					res = restClient.getForObject(env.getcanteenUrl() + "getComboAssign?id=" + searchValue,
							JsonResponse.class);

				} catch (Exception e) {
					e.printStackTrace();
				}

				if (res.getCode() != null) {
					res.setCode("success");
				} else {
					res.setCode("Unsuccess");
				}
				System.out.println("RESPONSE@@" + res);
				logger.info("Method : getComboAutoSearchList ends");
				return res;
			}
			
			

}
