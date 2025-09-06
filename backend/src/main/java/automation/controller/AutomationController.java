package automation.controller;

import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import automation.service.AutomationService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("automation/")
@CrossOrigin(origins = "http://localhost:4200")
public class AutomationController {

	private AutomationService automationService;

	@PostMapping("upload")
	public ResponseEntity<byte[]> uploadxlsm(MultipartFile file) throws IOException, IllegalArgumentException {
		byte[] processedFile = automationService.generateExcelFile(file);
		return new ResponseEntity<>(processedFile, HttpStatus.OK);
	}
}
