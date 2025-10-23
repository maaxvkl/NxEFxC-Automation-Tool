package automation.filehandling;

import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import lombok.Getter;

@Component
@Getter
public class SavedDataTypes {

	private final int NET_POINT_TYPE = 14;
	private final int POINTS = 3;
	private List<Row> binaryInputs = new ArrayList<>();
	private List<Row> analogInputs = new ArrayList<>();
	private List<Row> binaryOutputs = new ArrayList<>();
	private List<Row> analogOutputs = new ArrayList<>();
	private List<Row> binaryValues = new ArrayList<>();

	public void saveDataTypesToList(XSSFWorkbook wb) {
		XSSFSheet worksheet = wb.getSheetAt(POINTS);
		DataFormatter formatter = new DataFormatter(); 
		
		for (Row row : worksheet) {
			if (row == null) continue;

			String cellValue = formatter.formatCellValue(row.getCell(NET_POINT_TYPE)).trim();

			if (cellValue.contains("AI")) {
				analogInputs.add(row);
			} else if (cellValue.contains("BI")) {
				binaryInputs.add(row);
			} else if (cellValue.contains("BO")) {
				binaryOutputs.add(row);
			} else if (cellValue.contains("AO")) {
				analogOutputs.add(row);
			} else if (cellValue.contains("BV")) {
				analogOutputs.add(row);
			}
		}
	}
}
