package nirmalya.aathithya.webmodule.employee.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;

/**
 * @author Nirmalya Labs
 *
 */
@Controller
public class EmployeeDocumentController {
	Logger logger = LoggerFactory.getLogger(EmployeeDocumentController.class);

	@Autowired
	EnvironmentVaribles env;

	/**
	 * document controller to load images instantly
	 *
	 */
	@RequestMapping(value = "document/employee/{docname}")
	@ResponseBody
	public ResponseEntity<byte[]> getDocument(@PathVariable(value = "docname") String docname) throws IOException {
		logger.info("Method : getDocument controller function starts");

		File dir = ResourceUtils.getFile(env.getFileUploadEmployee());
		File file = new File(dir.getAbsolutePath() + "/" + docname);
		byte[] bytearr = Files.readAllBytes(file.toPath());
		if (docname.endsWith(".png")) {
			logger.info("Method : getDocument controller function starts");
			return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(bytearr);
		} else if (docname.endsWith(".jpeg") || docname.endsWith(".jpg")) {
			logger.info("Method : getDocument controller function starts");
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytearr);
		} else if (docname.endsWith(".pdf")) {
			logger.info("Method : getDocument controller function starts");
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(bytearr);
		} else {
			logger.info("Method : getDocument controller function starts");
			return ResponseEntity.ok().contentType(MediaType.ALL).body(bytearr);
		}
	}

	@RequestMapping(value = "document/employee/thumb/{docname}")
	@ResponseBody
	public ResponseEntity<byte[]> getDocumentThumb(@PathVariable(value = "docname") String docname) throws IOException {
		logger.info("Method : image controller function starts");

		File dir = ResourceUtils.getFile(env.getFileUploadEmployee());
		File file = new File(dir.getAbsolutePath() + "/" + docname);
		byte[] bytearr = Files.readAllBytes(file.toPath());
		if (docname.endsWith(".png")) {
			logger.info("Method : getDocument controller function starts");
			return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(bytearr);
		} else if (docname.endsWith(".jpeg") || docname.endsWith(".jpg")) {
			logger.info("Method : getDocument controller function starts");
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytearr);
		} else if (docname.endsWith(".pdf")) {
			logger.info("Method : getDocument controller function starts");
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(bytearr);
		} else {
			logger.info("Method : getDocument controller function starts");
			return ResponseEntity.ok().contentType(MediaType.ALL).body(bytearr);
		}
	}

	@RequestMapping(value = "document/property/{docname}")

	@ResponseBody
	public ResponseEntity<byte[]> getDocumentPropertyFunRecord(@PathVariable(value = "docname") String docname)
			throws IOException {
		logger.info("Method : getDocumentProperty controller function starts");

		File dir = ResourceUtils.getFile(env.getFileUploadProperty());
		File file = new File(dir.getAbsolutePath() + "/" + docname);
		byte[] bytearr = Files.readAllBytes(file.toPath());
		if (docname.endsWith(".png")) {
			logger.info("Method : getDocument controller function end");
			return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(bytearr);
		} else if (docname.endsWith(".jpeg") || docname.endsWith(".jpg")) {
			logger.info("Method : getDocument controller function end");
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytearr);
		} else if (docname.endsWith(".pdf")) {
			logger.info("Method : getDocument controller function end");
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(bytearr);
		} else if (docname.endsWith(".mp4")) {
			logger.info("Method : getDocument controller function end");
			return ResponseEntity.ok().contentType(MediaType.parseMediaType("video/mp4")).body(bytearr);
		} else {
			logger.info("Method : getDocumentProperty controller function end");
			return ResponseEntity.ok().contentType(MediaType.ALL).body(bytearr);
		}
	}

	@RequestMapping(value = "document/crm/{docname}")

	@ResponseBody
	public ResponseEntity<byte[]> getDocumentCrmFunRecord(@PathVariable(value = "docname") String docname)
			throws IOException {
		logger.info("Method : getDocumentCrmFunRecord controller function starts");

		File dir = ResourceUtils.getFile(env.getFileUploadCrmUrl());
		File file = new File(dir.getAbsolutePath() + "/" + docname);
		byte[] bytearr = Files.readAllBytes(file.toPath());
		if (docname.endsWith(".png")) {
			logger.info("Method : getDocument controller function end");
			return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(bytearr);
		} else if (docname.endsWith(".jpeg") || docname.endsWith(".jpg")) {
			logger.info("Method : getDocument controller function end");
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytearr);
		} else if (docname.endsWith(".pdf")) {
			logger.info("Method : getDocument controller function end");
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(bytearr);
		} else if (docname.endsWith(".mp4")) {
			logger.info("Method : getDocument controller function end");
			return ResponseEntity.ok().contentType(MediaType.parseMediaType("video/mp4")).body(bytearr);
		} else {
			logger.info("Method : getDocumentCrmFunRecord controller function end");
			return ResponseEntity.ok().contentType(MediaType.ALL).body(bytearr);
		}
	}
	@RequestMapping(value = "document/userQrCode/{docname}")
	@ResponseBody
	public ResponseEntity<byte[]> getDocumentUserQrCode(@PathVariable(value = "docname") String docname)
			throws IOException {
		logger.info("Method : getDocumentUserQrCode controller function starts");
		
		File dir = ResourceUtils.getFile(env.getUserQrCode());
		File file = new File(dir.getAbsolutePath() + "/" + docname);
		byte[] bytearr = Files.readAllBytes(file.toPath());
		if (docname.endsWith(".png")) {
			logger.info("Method : getDocument controller function end");
			return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(bytearr);
		} else if (docname.endsWith(".jpeg") || docname.endsWith(".jpg")) {
			logger.info("Method : getDocument controller function end");
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytearr);
		} else if (docname.endsWith(".pdf")) {
			logger.info("Method : getDocument controller function end");
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(bytearr);
		} else if (docname.endsWith(".mp4")) {
			logger.info("Method : getDocument controller function end");
			return ResponseEntity.ok().contentType(MediaType.parseMediaType("video/mp4")).body(bytearr);
		} else {
			logger.info("Method : getDocumentUserQrCode controller function end");
			return ResponseEntity.ok().contentType(MediaType.ALL).body(bytearr);
		}
	}
	@RequestMapping(value = "document/training/{docname}")
	@ResponseBody
	public ResponseEntity<byte[]> getDocumentTraining(@PathVariable(value = "docname") String docname)
			throws IOException {
		logger.info("Method : getDocumentTraining controller function starts");
		logger.info("Doccc"+docname);
		File dir = ResourceUtils.getFile(env.getFileUploadTraining());
		File file = new File(dir.getAbsolutePath() + "/" + docname);
		byte[] bytearr = Files.readAllBytes(file.toPath());
		if (docname.endsWith(".png")) {
			logger.info("Method : getDocument controller function end");
			return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(bytearr);
		} else if (docname.endsWith(".jpeg") || docname.endsWith(".jpg")) {
			logger.info("Method : getDocument controller function end");
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytearr);
		} else if (docname.endsWith(".pdf")) {
			logger.info("Method : getDocument controller function end");
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(bytearr);
		} else if (docname.endsWith(".mp4")) {
			logger.info("Method : getDocument controller function end");
			return ResponseEntity.ok().contentType(MediaType.parseMediaType("video/mp4")).body(bytearr);
		} else if (docname.endsWith(".mp3")) {
			logger.info("Method : getDocument controller function end");
			return ResponseEntity.ok().contentType(MediaType.parseMediaType("audio/mp3")).body(bytearr);
		} else {
			logger.info("Method : getDocumentUserQrCode controller function end");
			return ResponseEntity.ok().contentType(MediaType.ALL).body(bytearr);
		}
	}
	@RequestMapping(value = "document/staffQrCode/{docname}")
	@ResponseBody
	public ResponseEntity<byte[]> getDocumentStaffQrCode(@PathVariable(value = "docname") String docname)
			throws IOException {
		logger.info("Method : getDocumentStaffQrCode controller function starts");
		
		File dir = ResourceUtils.getFile(env.getStaffQrCode());
		File file = new File(dir.getAbsolutePath() + "/" + docname);
		byte[] bytearr = Files.readAllBytes(file.toPath());
		if (docname.endsWith(".png")) {
			logger.info("Method : getDocument controller function end");
			return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(bytearr);
		} else if (docname.endsWith(".jpeg") || docname.endsWith(".jpg")) {
			logger.info("Method : getDocument controller function end");
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytearr);
		} else if (docname.endsWith(".pdf")) {
			logger.info("Method : getDocument controller function end");
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(bytearr);
		} else if (docname.endsWith(".mp4")) {
			logger.info("Method : getDocument controller function end");
			return ResponseEntity.ok().contentType(MediaType.parseMediaType("video/mp4")).body(bytearr);
		} else {
			logger.info("Method : getDocumentStaffQrCode controller function end");
			return ResponseEntity.ok().contentType(MediaType.ALL).body(bytearr);
		}
	}

}