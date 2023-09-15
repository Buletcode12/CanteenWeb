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
	public class EdmsManageController {

		Logger logger = LoggerFactory.getLogger(EdmsManageController.class);

		RestTemplate restClient;

		EnvironmentVaribles env;
		
		@Autowired
		public EdmsManageController(EnvironmentVaribles EnvironmentVaribles,RestTemplate RestTemplate) {
			this.env = EnvironmentVaribles;
			this.restClient=RestTemplate;
		}
		
		
		@GetMapping(value = { "manage-control" })
		public String manageControl( HttpSession session) {
			logger.info("Method : manageControl starts");
			
			
			logger.info("Method : manageControl ends");
			return "EDMS/manage";
		}

}


