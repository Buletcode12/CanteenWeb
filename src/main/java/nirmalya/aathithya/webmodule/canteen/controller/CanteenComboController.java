package nirmalya.aathithya.webmodule.canteen.controller;

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
@RequestMapping(value = "canteen")
public class CanteenComboController {
	
	Logger logger = LoggerFactory.getLogger(CanteenComboController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("/canteen-combo")
	public String viewIncentive(Model model, HttpSession session) {



	logger.info("Method : Manage-incentivesDetails starts");
	

	try {
		DropDownModel[] orderStatus = restClient.getForObject(env.getcanteenUrl() + "/getComboCatagory",
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
		DropDownModel[] orderStatus = restClient.getForObject(env.getcanteenUrl() + "/getComboSubCatagiry",
				DropDownModel[].class);

		List<DropDownModel> clubMemberDetails = Arrays.asList(orderStatus);
		System.out.println("clubMemberDetails" + clubMemberDetails);
		model.addAttribute("clubMemberDetails", clubMemberDetails);
	} catch (RestClientException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	try {
		DropDownModel[] orderStatus = restClient.getForObject(env.getcanteenUrl() + "/getCombovariants",
				DropDownModel[].class);

		List<DropDownModel> variantDetails = Arrays.asList(orderStatus);
		System.out.println("variantDetails" + variantDetails);
		model.addAttribute("variantDetails", variantDetails);
	} catch (RestClientException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	 
	logger.info("Method : Manage-incentivesDetails ends");
	return "canteen/canteen-combo";

}
	

	     //Add
		@SuppressWarnings("unchecked")
		@PostMapping("canteen-combo-add-dtls")
		public @ResponseBody JsonResponse<Object> addIncentive(@RequestBody List<WebMenuModel> webMenuModel, Model model,
				HttpSession session) {

			logger.info("Method : addincentivesDetails starts" + webMenuModel);

			System.out.println("resp web controller-----------------------------------" + webMenuModel);

			JsonResponse<Object> resp = new JsonResponse<Object>();

			try {

				resp = restClient.postForObject(env.getcanteenUrl() + "restcomboadd",  webMenuModel, JsonResponse.class);

			} catch (RestClientException e) {

				e.printStackTrace();
			}

			if (resp.getMessage() == "") {
				resp.setMessage("Success");
			}
			logger.info("Method : addincentivesDetails ends" + resp);

			return resp;
		}
		

		  //View
		@SuppressWarnings("unchecked")
		@GetMapping("canteen-combo-throughAjax")
		public @ResponseBody List<WebMenuModel> viewItemList(HttpSession session , String catId ,String subCatId ,String variant ) {

			logger.info("Method : viewItemList starts"+variant);

			JsonResponse<List<WebMenuModel>> resp = new JsonResponse<List<WebMenuModel>>();

			try {
				resp = restClient.getForObject(env.getcanteenUrl() + "restViewCombo?CatId="+catId+"&SubCatId="+subCatId+"&variant="+variant, JsonResponse.class);
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
		

}
