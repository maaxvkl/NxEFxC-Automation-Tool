package automation.filehandling;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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

	public void saveDataTypesToList(XSSFWorkbook wb) {
		Row row = null;
		XSSFSheet worksheet = wb.getSheetAt(POINTS);
		for (Iterator<Row> rowIterator = worksheet.iterator(); rowIterator.hasNext();) {
			row = rowIterator.next();
			if (row.getCell(NET_POINT_TYPE).getStringCellValue().trim().contains("AI")) {
				analogInputs.add(row);
			}

			if (row.getCell(NET_POINT_TYPE).getStringCellValue().trim().contains("BI")) {
				binaryInputs.add(row);
			}

			if (row.getCell(NET_POINT_TYPE).getStringCellValue().trim().contains("BO")) {
				binaryOutputs.add(row);
			}

			if (row.getCell(NET_POINT_TYPE).getStringCellValue().trim().contains("AO")) {
				analogOutputs.add(row);
			}
		}
	}

}
