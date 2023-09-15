package nirmalya.aathithya.webmodule.EDMS.controller;


import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;


import groovyjarjarpicocli.CommandLine.Model;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;


	@Controller
	@RequestMapping(value = "edms")
	public class EdmsDocumentController {

		Logger logger = LoggerFactory.getLogger(EdmsDocumentController.class);

		RestTemplate restClient;

		EnvironmentVaribles env;
		
		@Autowired
		public EdmsDocumentController(EnvironmentVaribles EnvironmentVaribles,RestTemplate RestTemplate) {
			this.env = EnvironmentVaribles;
			this.restClient=RestTemplate;
		}
		
		
		@GetMapping(value = { "document-control" })
		public String documentControl( HttpSession session) {
			logger.info("Method : documentControl starts");
			
			
			logger.info("Method : documentControl ends");
			return "EDMS/documentcontrol";
		}

}

