package automation.filehandling;

import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;
import automation.values.BinaryOutputValues;
import lombok.AllArgsConstructor;
import automation.utility.DeviceUtils;

@Component
@AllArgsConstructor
public class WriteBinaryOutputs {

	BinaryOutputValues values;
	private final int SIGNAL = 19;

	public void writeBinaryOutputs(List<Row> BORows) {
		Cell cell = null;
		List<Row> JCIRows = new ArrayList<>();
		List<Row> NoJCIRows = new ArrayList<>();
		int[] BOwriteToStringCells = values.getBOwriteToStringCells();
		String[] BOwriteStringValuesToCell = values.getBOwriteStringValuestoCell();
		int[] BOwriteToDoubleCells = values.getBOwriteToDoubleCells();
		double[] BOwriteDoubleValuesToCell = values.getBOwriteDoubleValuesToCell();
		int UNIT = 24;

		DeviceUtils.seperateRowsByDevice(BORows, JCIRows, NoJCIRows);

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

		for (Row row : BORows) {
			for (int i = 0; i < BOwriteToStringCells.length; i++) {
				cell = row.getCell(BOwriteToStringCells[i]);
				if (BOwriteStringValuesToCell[i].equals("WAHR")) {
					cell.setCellValue(true);
				} else if (BOwriteStringValuesToCell[i].equals("FALSCH")) {
					cell.setCellValue(false);
				} else {
					cell.setCellValue(BOwriteStringValuesToCell[i]);
				}
			}
			for (int i = 0; i < BOwriteToDoubleCells.length; i++) {
				cell = row.getCell(BOwriteToDoubleCells[i]);
				cell.setCellValue(BOwriteDoubleValuesToCell[i]);
			}
		}
	}

	private void setJCISignals(Row row) {
		Cell cell = null;
		cell = row.getCell(SIGNAL);
		cell.setCellValue("24VAC Maintained");
	}

	private void setNoJCISignals(Row row) {
		Cell cell = null;
		cell = row.getCell(SIGNAL);
		cell.setCellValue("RT 24VAC-240VAC Maintained");
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
