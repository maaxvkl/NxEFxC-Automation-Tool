package automation.filehandling;

import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;
import automation.values.BinaryInputValues;

@Component
public class WriteBinaryInputs {

	BinaryInputValues values;

	private final int UNIT = 24;
	private final int SIGNAL = 19;
	private final int DEVICE_NAME = 8;

	private String JCIdevices[] = { "SNE", "SNC", "XPM", "CGM", "IOM" };

	private int BIwriteToStringCells[];
	private String BIwriteStringValuesToCell[];
	private int BIwriteToDoubleCells[];
	private double BIwriteDoubleValuesToCell[];

	private final int DEFAULT_VALUE = 32;
	private final int RELING_DEFAULT = 34;

	WriteBinaryInputs(BinaryInputValues values) {
		this.values = values;
	}

	public void writeBinaryInputs(List<Row> BIRows) {
		BIwriteToStringCells = values.getBIwriteToStringCells();
		BIwriteStringValuesToCell = values.getBIwriteStringValuestoCell();
		BIwriteToDoubleCells = values.getBIwriteToDoubleCells();
		BIwriteDoubleValuesToCell = values.getBIwriteDoubleValuesToCell();
		Cell cell = null;
		List<Row> JCIRows = new ArrayList<>();
		List<Row> NoJCIRows = new ArrayList<>();

		for (Row row : BIRows) {
			String deviceName = row.getCell(DEVICE_NAME).toString();
			boolean found = false;
			for (int i = 0; i < JCIdevices.length; i++) {
				if (deviceName.contains(JCIdevices[i])) {
					JCIRows.add(row);
					found = true;
					break;
				}
			}
			if (!found) {
				NoJCIRows.add(row);
			}
		}

		for (Row row : JCIRows) {
			String unit = row.getCell(UNIT).toString(); // e.g. Normal/Alarm
			String splitUnit[] = unit.split("\\/"); // Normal Alarm
			setJCISignals(row);
			setValues(row, splitUnit);
		}

		for (Row row : NoJCIRows) {
			String unit = row.getCell(UNIT).toString(); // e.g. Normal/Alarm
			String splitUnit[] = unit.split("\\/"); // Normal Alarm
			setNoJCISignals(row);
			setValues(row, splitUnit);
		}

		for (Row row : BIRows) {
			for (int i = 0; i < BIwriteToStringCells.length; i++) {
				cell = row.getCell(BIwriteToStringCells[i]);
				cell.setCellValue(BIwriteStringValuesToCell[i]);
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
		cell = row.getCell(DEFAULT_VALUE);
		cell.setCellValue(splitUnit[0]);
		cell = row.getCell(RELING_DEFAULT);
		cell.setCellValue(splitUnit[0]);

	}

}
