package automation.filehandling;

import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;
import automation.values.AnalogOutputValues;
import lombok.AllArgsConstructor;
import automation.utility.DeviceUtils;

@Component
@AllArgsConstructor
public class WriteAnalogOutputs {

	AnalogOutputValues values;
	private final int SIGNAL = 19;
	private final int MIN_VALUE = 28;
	private final int MAX_VALUE = 29;

	public void writeAnalogOutputs(List<Row> AORows, boolean AOTrends) {
		Cell cell = null;
		List<Row> JCIRows = new ArrayList<>();
		List<Row> NoJCIRows = new ArrayList<>();
		int[] AOwriteToStringCells = values.getAOwriteToStringCells();
		String[] AOwriteStringValuesToCell = values.getAOwriteStringValuesToCell();
		int[] AOwriteToDoubleCells = values.getAOwriteToDoubleCells();
		double[] AOwriteDoubleValuesToCell = values.getAOwriteDoubleValuesToCell();
		int PT_MEMO = 215;

		DeviceUtils.seperateRowsByDevice(AORows, JCIRows, NoJCIRows);

		for (Row row : JCIRows) {
			String ptMemo = row.getCell(PT_MEMO).getStringCellValue(); // e.g. [[ACE:|2-10V|0;100|]]
			String subMemo = ptMemo.substring(6); // |2-10V|0;100|]]
			String signal[] = subMemo.split("\\|"); // 2-10V 0;100|]]
			String signalValue = signal[1];
			String rangeInStr = signal[1];
			String rangeOutStr = signal[2];
			String[] rangeIn = (rangeInStr != null && !rangeInStr.isEmpty()) ? rangeInStr.split("\\-")
					: new String[] { "0", "0" };
			String[] rangeOut = (rangeOutStr != null && !rangeOutStr.isEmpty()) ? rangeOutStr.split("\\;")
					: new String[] { "0", "0" };
			setJCISignals(row, signalValue);
			setValues(row, signalValue, rangeIn, rangeOut);
		}

		for (Row row : NoJCIRows) {
			String ptMemo = row.getCell(PT_MEMO).getStringCellValue(); // e.g. [[ACE:|2-10V|0;100|]]
			String subMemo = ptMemo.substring(6); // |2-10V|0;100|]]
			String signal[] = subMemo.split("\\|"); // 2-10V 0;100|]]
			String signalValue = signal[1];
			String rangeInStr = signal[1];
			String rangeOutStr = signal[2];
			String[] rangeIn = (rangeInStr != null && !rangeInStr.isEmpty()) ? rangeInStr.split("\\-")
					: new String[] { "0", "0" };
			String[] rangeOut = (rangeOutStr != null && !rangeOutStr.isEmpty()) ? rangeOutStr.split("\\;")
					: new String[] { "0", "0" };
			setNoJCISignals(row, signalValue);
			setValues(row, signalValue, rangeIn, rangeOut);
		}

		for (Row row : AORows) {
			for (int i = 0; i < AOwriteToStringCells.length; i++) {
				cell = row.getCell(AOwriteToStringCells[i]);
				if (AOwriteStringValuesToCell[i].equals("WAHR")) {
					cell.setCellValue(true);
				} else if (AOwriteStringValuesToCell[i].equals("FALSCH")) {
					cell.setCellValue(false);
				} else {
					cell.setCellValue(AOwriteStringValuesToCell[i]);
				}
			}
			for (int i = 0; i < AOwriteToDoubleCells.length; i++) {
				cell = row.getCell(AOwriteToDoubleCells[i]);
				cell.setCellValue(AOwriteDoubleValuesToCell[i]);
			}
		}
	}

	private void setJCISignals(Row row, String signal) {
		Cell cell = null;
		if (signal.equals("0-10V") || signal.equals("2-10V")) {
			cell = row.getCell(SIGNAL);
			cell.setCellValue("0-10VDC");
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
		} else {
			cell = row.getCell(SIGNAL);
			cell.setCellValue("RT " + signal);
		}
	}

	private void setValues(Row row, String signal, String[] rangeIn, String[] rangeOut) {
		Cell cell = null;
		int RANGE_IN_LOW = 20;
		int RANGE_IN_HIGH = 21;
		int RANGE_OUT_LOW = 22;
		int RANGE_OUT_HIGH = 23;
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
