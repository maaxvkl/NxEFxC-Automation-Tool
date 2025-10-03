package automation.filehandling;

import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;
import automation.values.BinaryOutputValues;
import automation.enumeration.JCIDevices;

@Component
public class WriteBinaryOutputs {
	
	BinaryOutputValues values;

	private final int DEVICE_NAME = 8;
	private final int UNIT = 24;
	private final int SIGNAL = 19;
	private final int DEFAULT_VALUE = 32;
	private final int RELING_DEFAULT = 34;

	private int BOwriteToStringCells[];
	private String BOwriteStringValuesToCell[];
	private int BOwriteToDoubleCells[];
	private double BOwriteDoubleValuesToCell[];
	
	WriteBinaryOutputs(BinaryOutputValues values){
		this.values = values;
	}

	public void writeBinaryOutputs(List<Row> BORows) {
	Cell cell = null;	
	BOwriteToStringCells = values.getBOwriteToStringCells();
	BOwriteStringValuesToCell = values.getBOwriteStringValuestoCell();
	BOwriteToDoubleCells  = values.getBOwriteToDoubleCells(); 
	BOwriteDoubleValuesToCell = values.getBOwriteDoubleValuesToCell();	
	List<Row> JCIRows = new ArrayList<>();
	List<Row> NoJCIRows = new ArrayList<>();

	for (Row row : BORows) {
		String deviceName = row.getCell(DEVICE_NAME).getStringCellValue().trim();
		boolean found = false;
 	    for (int i = 0; i < JCIDevices.values().length; i++) {
				if (deviceName.contains(JCIDevices.values()[i].name())) {
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
			String unit = row.getCell(UNIT).getStringCellValue().trim(); // e.g. Normal/Alarm
			String splitUnit[] = unit.split("\\/"); // Normal Alarm
			setJCISignals(row);
			setValues(row, splitUnit);
		}

		for (Row row : NoJCIRows) {
			String unit = row.getCell(UNIT).getStringCellValue().trim(); // e.g. Normal/Alarm
			String splitUnit[] = unit.split("\\/"); // Normal Alarm
			setNoJCISignals(row);
			setValues(row, splitUnit);
		}
	
		for (Row row : BORows) {
			for (int i = 0; i < BOwriteToStringCells.length; i++) {
				cell = row.getCell(BOwriteToStringCells[i]);
				cell.setCellValue(BOwriteStringValuesToCell[i]);
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
		cell = row.getCell(DEFAULT_VALUE);
		cell.setCellValue(splitUnit[0]);
		cell = row.getCell(RELING_DEFAULT);
		cell.setCellValue(splitUnit[0]);

	}

}
