package automation.filehandling;

import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;
import automation.values.BinaryInputValues;
import lombok.AllArgsConstructor;
import automation.utility.DeviceUtils;

@Component
@AllArgsConstructor
public class WriteBinaryInputs {

	BinaryInputValues values;
	private final int SIGNAL = 19;

	public void writeBinaryInputs(List<Row> BIRows) {
		Cell cell = null;
		List<Row> JCIRows = new ArrayList<>();
		List<Row> NoJCIRows = new ArrayList<>();
		int[] BIwriteToStringCells = values.getBIwriteToStringCells();
		String[] BIwriteStringValuesToCell = values.getBIwriteStringValuestoCell();
		int[] BIwriteToDoubleCells = values.getBIwriteToDoubleCells();
		double[] BIwriteDoubleValuesToCell = values.getBIwriteDoubleValuesToCell();
		int UNIT = 24;

		DeviceUtils.seperateRowsByDevice(BIRows, JCIRows, NoJCIRows);

		for (Row row : JCIRows) {
			String unit = row.getCell(UNIT).getStringCellValue(); // e.g. Normal/Alarm
			String[] splitUnit = unit.contains("/") ? unit.split("\\/") : unit.split(" ");
			setJCISignals(row);
			setValues(row, splitUnit);
		}

		for (Row row : NoJCIRows) {
			String unit = row.getCell(UNIT).getStringCellValue(); // e.g. Normal/Alarm
			String[] splitUnit = unit.contains("/") ? unit.split("\\/") : unit.split(" ");
			setNoJCISignals(row);
			setValues(row, splitUnit);
		}

		for (Row row : BIRows) {
			for (int i = 0; i < BIwriteToStringCells.length; i++) {
				cell = row.getCell(BIwriteToStringCells[i]);
				if (BIwriteStringValuesToCell[i].equals("WAHR")) {
					cell.setCellValue(true);
				} else if (BIwriteStringValuesToCell[i].equals("FALSCH")) {
					cell.setCellValue(false);
				} else {
					cell.setCellValue(BIwriteStringValuesToCell[i]);
				}
			}
			for (int i = 0; i < BIwriteToDoubleCells.length; i++) {
				cell = row.getCell(BIwriteToDoubleCells[i]);
				cell.setCellValue(BIwriteDoubleValuesToCell[i]);
			}
		}
	}

	private void setJCISignals(Row row) {
		Cell cell = null;
		cell = row.getCell(SIGNAL);
		cell.setCellValue("Dry Contact Maintained");

	}

	private void setNoJCISignals(Row row) {
		Cell cell = null;
		cell = row.getCell(SIGNAL);
		cell.setCellValue("RT Dry Contact Maintained");

	}

	private void setValues(Row row, String[] splitUnit) {
		Cell cell = null;
		int DEFAULT_VALUE = 32;
		int RELING_DEFAULT = 34;
		cell = row.getCell(DEFAULT_VALUE);
		cell.setCellValue(splitUnit[0]);
		cell = row.getCell(RELING_DEFAULT);
		cell.setCellValue(splitUnit[0]);

	}

}
