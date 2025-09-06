package automation.filehandling;

import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;
import automation.values.AnalogInputValues;

@Component
public class WriteAnalogInputs {

	AnalogInputValues values;

	private String JCIdevices[] = { "SNE", "SNC", "XPM", "CGM", "IOM" };
	private final int DEVICE_NAME = 8;
	private final int PT_MEMO = 215;
	private final int SIGNAL = 19;
	private final int RANGE_IN_LOW = 20;
	private final int RANGE_IN_HIGH = 21;
	private final int RANGE_OUT_LOW = 22;
	private final int RANGE_OUT_HIGH = 23;
	private final int MIN_VALUE = 28;
	private final int MAX_VALUE = 29;
	private int AIwriteToStringCells[];
	private String AIwriteStringValuesToCell[];
	private int AIwriteToDoubleCells[];
	private double AIwriteDoubleValuesToCell[];

	WriteAnalogInputs(AnalogInputValues values) {
		this.values = values;
	}

	public void writeAnalogInputs(List<Row> AIRows) {
		AIwriteToStringCells = values.getAIwriteToStringCells();
		AIwriteStringValuesToCell = values.getAIwriteStringValuesToCell();
		AIwriteToDoubleCells = values.getAIwriteToDoubleCells();
		AIwriteDoubleValuesToCell = values.getAIwriteDoubleValuesToCell();
		Cell cell = null;
		List<Row> JCIRows = new ArrayList<>();
		List<Row> NoJCIRows = new ArrayList<>();

		for (Row row : AIRows) {
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
			String ptMemo = row.getCell(PT_MEMO).toString(); // e.g. [[ACE:|2-10V|0;100|]]
			String subMemo = ptMemo.substring(6); // |2-10V|0;100|]]
			String signal[] = subMemo.split("\\|"); // 2-10V 0;100 ]]
			String rangeIn[] = signal[1].split("\\-"); // 2 10V
			String rangeOut[] = signal[2].split("\\;"); // 0 100 ]]
			setJCISignals(row, signal[1]);
			setValues(row, signal[1], rangeIn, rangeOut);
		}

		for (Row row : NoJCIRows) {
			String ptMemo = row.getCell(PT_MEMO).toString(); // e.g. [[ACE:|2-10V|0;100|]]
			String subMemo = ptMemo.substring(6); // |2-10V|0;100|]]
			String signal[] = subMemo.split("\\|"); // 2-10V 0;100|]]
			String rangeIn[] = signal[1].split("\\-"); // 2 10V
			String rangeOut[] = signal[2].split("\\;"); // 0 100|]]
			setNoJCISignals(row, signal[1]);
			setValues(row, signal[1], rangeIn, rangeOut);
		}

		for (Row row : AIRows) {
			for (int i = 0; i < AIwriteToStringCells.length; i++) {
				cell = row.getCell(AIwriteToStringCells[i]);
				cell.setCellValue(AIwriteStringValuesToCell[i]);
			}
			for (int i = 0; i < AIwriteToDoubleCells.length; i++) {
				cell = row.getCell(AIwriteToDoubleCells[i]);
				cell.setCellValue(AIwriteDoubleValuesToCell[i]);
			}
		}
	}

	private void setJCISignals(Row row, String signal) {
		Cell cell = null;
		if (signal.equals("0-10V") || signal.equals("2-10V")) {
			cell = row.getCell(SIGNAL);
			cell.setCellValue("0-10VDC");
		} else if (signal.equals("Pt1000")) {
			cell = row.getCell(SIGNAL);
			cell.setCellValue("Platinum 1K RTD");
		} else {
			cell = row.getCell(SIGNAL);
			cell.setCellValue(signal);
		}
	}

	private void setNoJCISignals(Row row, String signal) {
		Cell cell = null;
		if (signal.equals("0-10V") || signal.equals("2-10V")) {
			cell = row.getCell(SIGNAL);
			cell.setCellValue("RT 0-10VDC");
		} else if (signal.equals("Pt1000")) {
			cell = row.getCell(SIGNAL);
			cell.setCellValue("RT Platinum 1K RTD");
		} else {
			cell = row.getCell(SIGNAL);
			cell.setCellValue("RT " + signal);
		}
	}

	private void setValues(Row row, String signal, String[] rangeIn, String[] rangeOut) {
		Cell cell = null;
		if (!signal.equals("Pt1000")) {
			String cleanValue = rangeIn[1].replaceAll("[^\\d.,-]", "");
			cell = row.getCell(RANGE_IN_LOW);
			cell.setCellValue(Double.parseDouble(rangeIn[0]));
			cell = row.getCell(RANGE_IN_HIGH);
			cell.setCellValue(Double.parseDouble(cleanValue));
			cell = row.getCell(RANGE_OUT_LOW);
			cell.setCellValue(Double.parseDouble(rangeOut[0]));
			cell = row.getCell(RANGE_OUT_HIGH);
			cell.setCellValue(Double.parseDouble(rangeOut[1]));
			calculateLimits(row, rangeOut);
		} else {
			cell = row.getCell(RANGE_IN_LOW);
			cell.setCellValue(0);
			cell = row.getCell(RANGE_IN_HIGH);
			cell.setCellValue(2000);
			cell = row.getCell(RANGE_OUT_LOW);
			cell.setCellValue(Double.parseDouble(rangeOut[0]));
			cell = row.getCell(RANGE_OUT_HIGH);
			cell.setCellValue(Double.parseDouble(rangeOut[1]));
			cell = row.getCell(MIN_VALUE);
			cell.setCellValue(-46);
			cell = row.getCell(MAX_VALUE);
			cell.setCellValue(121);
		}
	}

	private void calculateLimits(Row row, String[] rangeOut) {
		Cell cell = null;
		double minValue;
		double maxValue;
		minValue = Double.parseDouble(rangeOut[0]);
		if (minValue < 0) {
			minValue = minValue + (minValue * 10 / 100);
		} else {
			minValue = minValue - (minValue * 10 / 100);
		}
		maxValue = Double.parseDouble(rangeOut[1]);
		maxValue = maxValue + (maxValue * 10 / 100);
		cell = row.getCell(MIN_VALUE);
		cell.setCellValue(minValue);
		cell = row.getCell(MAX_VALUE);
		cell.setCellValue(maxValue);
	}
}
