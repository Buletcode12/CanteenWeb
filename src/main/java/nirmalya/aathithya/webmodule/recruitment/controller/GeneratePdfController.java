package nirmalya.aathithya.webmodule.recruitment.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.recruitment.model.OfferletterModel;

@Controller
@RequestMapping(value = "recruitment/")
public class GeneratePdfController {

	Logger logger = LoggerFactory.getLogger(GeneratePdfController.class);
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "/offer-letter-pdf" })
	public void generateOfferletter(HttpServletResponse response, Model model, HttpSession session,
			@RequestParam("candId") String encodedParam1, @RequestParam("bandid") String encodedParam2, @RequestParam("offerLetter") String encodedParam3) {
		logger.info("Method : generateOfferletter starts");

		byte[] encodeByte1 = Base64.getDecoder().decode(encodedParam1.getBytes());
		String candId = (new String(encodeByte1));
		
		byte[] encodeByte2 = Base64.getDecoder().decode(encodedParam2.getBytes());
		String bandid = (new String(encodeByte2));
		
		byte[] encodeByte3 = Base64.getDecoder().decode(encodedParam3.getBytes());
		String offerLetterId = (new String(encodeByte3));
		String org=""; 
		String orgDiv="";
		try {
			org = (String) session.getAttribute("ORGANIZATION"); 
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JsonResponse<OfferletterModel> jsonResponse = new JsonResponse<OfferletterModel>();
		try {
			jsonResponse = restTemplate.getForObject(
					env.getRecruitment() + "viewpdf?candId=" + candId + "&bandid=" + bandid+ "&offerLetterId=" + offerLetterId+"&org="+org+"&orgDiv="+orgDiv, JsonResponse.class);

		} catch (

		RestClientException e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();

		OfferletterModel offerLetter = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<OfferletterModel>() {
				});
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("offerLetter", offerLetter);

		/*
		 * String logo = "classpath:static/assets/css/extend/nirmalya_logo.png"; String
		 * sign = "classpath:static/assets/css/extend/signature.jpg"; String stamp =
		 * "classpath:static/assets/css/extend/nrm-stamp.png";
		 */
		
		String logo = "";
		String sign = "";
		String stamp = "";

		if (offerLetter.getLogo() != null && offerLetter.getLogo() != "" && offerLetter.getLogo() != " ") {
			logo = env.getBaseURL() + "document/document/" + offerLetter.getLogo();
		}

		if (offerLetter.getSign() != null &&offerLetter.getSign() != "" && offerLetter.getSign() != " ") {
			sign = env.getBaseURL() + "document/document/" + offerLetter.getSign();
		}
		if (offerLetter.getStamp() != null && offerLetter.getStamp() != "" && offerLetter.getStamp() != " ") {
			stamp = env.getBaseURL() + "document/document/" + offerLetter.getStamp();
		}
		data.put("logo", logo);
		data.put("sign", sign);
		data.put("stamp", stamp);
		
		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=GenerateOfferLetter.pdf");
		File file;
		byte[] fileData = null;
		try {
			file = pdfGeneratorUtil.createPdf("recruitment/generateOfferLetterPdf", data);
			InputStream in = new FileInputStream(file);
			fileData = IOUtils.toByteArray(in);
			response.setContentLength(fileData.length);
			response.getOutputStream().write(fileData);
			response.getOutputStream().flush();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : generateOfferletter ends");
	}
}
