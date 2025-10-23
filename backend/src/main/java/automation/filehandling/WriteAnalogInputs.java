package automation.filehandling;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;
import automation.values.AnalogInputValues;
import automation.values.AnalogTrendValues;
import automation.utility.DeviceUtils;

@Component
@AllArgsConstructor
public class WriteAnalogInputs {

	AnalogInputValues values;
	AnalogTrendValues trendValues;
	private final int SIGNAL = 19;
	private final int MIN_VALUE = 28;
	private final int MAX_VALUE = 29;

	public void writeAnalogInputs(List<Row> AIRows, boolean AITrends) {
		Cell cell = null;
		List<Row> JCIRows = new ArrayList<>();
		List<Row> NoJCIRows = new ArrayList<>();
		int[] AIwriteToStringCells = values.getAIwriteToStringCells();
		String[] AIwriteStringValuesToCell = values.getAIwriteStringValuesToCell();
		int[] AIwriteToDoubleCells = values.getAIwriteToDoubleCells();
		double[] AIwriteDoubleValuesToCell = values.getAIwriteDoubleValuesToCell();
		final int PT_MEMO = 215;

		DeviceUtils.seperateRowsByDevice(AIRows, JCIRows, NoJCIRows);

		for (Row row : JCIRows) {
			String ptMemo = row.getCell(PT_MEMO).toString(); // e.g. [[ACE:|2-10V|0;100|]]
			String subMemo = ptMemo.substring(6); // |2-10V|0;100|]]
			String[] signal = subMemo.split("\\|"); // 2-10V 0;100 ]]
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
			String ptMemo = row.getCell(PT_MEMO).toString(); // e.g. [[ACE:|2-10V|0;100|]]
			String subMemo = ptMemo.substring(6); // |2-10V|0;100|]]
			String[] signal = subMemo.split("\\|"); // 2-10V 0;100|]]
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

		for (Row row : AIRows) {
			for (int i = 0; i < AIwriteToStringCells.length; i++) {
				cell = row.getCell(AIwriteToStringCells[i]);
				if (AIwriteStringValuesToCell[i].equals("WAHR")) {
					cell.setCellValue(true);
				} else if (AIwriteStringValuesToCell[i].equals("FALSCH")) {
					cell.setCellValue(false);
				} else {
					cell.setCellValue(AIwriteStringValuesToCell[i]);
				}
			}
			for (int i = 0; i < AIwriteToDoubleCells.length; i++) {
				cell = row.getCell(AIwriteToDoubleCells[i]);
				cell.setCellValue(AIwriteDoubleValuesToCell[i]);
			}
		}

		if (AITrends) {
			for (Row row : AIRows) {
				setTrendValues(row);
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
		} else if (signal.equals("0-20mA")) {
			cell = row.getCell(SIGNAL);
			cell.setCellValue("4-20mA");
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
		final int RANGE_IN_LOW = 20;
		final int RANGE_IN_HIGH = 21;
		final int RANGE_OUT_LOW = 22;
		final int RANGE_OUT_HIGH = 23;
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

	private void setTrendValues(Row row) {
		Cell cell = null;
		int[] trendWriteToStringCells = trendValues.getTrendWriteToStringCells();
		String[] trendWriteStringValuesToCell = trendValues.getTrendWriteStringValuesToCell();
		int[] trendWriteToDoubleCells = trendValues.getTrendWriteToDoubleCells();
		double[] trendWriteDoubleValuesToCell = trendValues.getTrendWriteDoubleValuesToCell();
		final int UNIT = 24;
		final int CLIENT_COV_INCR = 140;

		String unit = row.getCell(UNIT).toString();
		switch (unit) {
		case "Pa":
			cell = row.getCell(CLIENT_COV_INCR);
			cell.setCellValue(5);
			break;
		case "%":
			cell = row.getCell(CLIENT_COV_INCR);
			cell.setCellValue(1);
			break;
		case "deg C":
			cell = row.getCell(CLIENT_COV_INCR);
			cell.setCellValue(0.2);
			break;
		}
		for (int i = 0; i < trendWriteToStringCells.length; i++) {
			cell = row.getCell(trendWriteToStringCells[i]);
			cell.setCellValue(trendWriteStringValuesToCell[i]);
		}
		for (int i = 0; i < trendWriteToDoubleCells.length; i++) {
			cell = row.getCell(trendWriteToDoubleCells[i]);
			cell.setCellValue(trendWriteDoubleValuesToCell[i]);
		}

	}
}
