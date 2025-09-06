package automation.exception;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ExceptionHandling {

	@ExceptionHandler(IOException.class)
	public ResponseEntity<String> IOException(IOException exception) {
		return ResponseEntity.status(INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON)
				.body("Bitte wählen Sie die richtige Datei aus");
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> IllegalArgumentException(IllegalArgumentException exception) {
		return ResponseEntity.status(BAD_GATEWAY).contentType(MediaType.APPLICATION_JSON)
				.body("Bitte wählen Sie die richtige Datei aus");
	}

}
