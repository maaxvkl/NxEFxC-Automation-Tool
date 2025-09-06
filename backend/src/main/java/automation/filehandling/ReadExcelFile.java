package automation.filehandling;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;

@Component
@Getter
public class ReadExcelFile {

	private final int NET_POINT_TYPE = 14;
	private final int POINTS = 3;
	private List<Row> binaryInputs = new ArrayList<>();
	private List<Row> analogInputs = new ArrayList<>();
	private List<Row> binaryOutputs = new ArrayList<>();

	public XSSFWorkbook getWorkbookFromExcelFile(MultipartFile file) throws IOException, IllegalArgumentException {
		InputStream inputStream = file.getInputStream();
		XSSFWorkbook wb = new XSSFWorkbook(inputStream);
		if (wb.getSheetAt(POINTS).getSheetName().equals("Points")) {
			inputStream.close();
			return wb;
		} else {
			inputStream.close();
			throw new IOException();
		}
	}
}
