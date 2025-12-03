package automation.filehandling;


import automation.values.BinaryOutputValues;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@AllArgsConstructor
public class WriteMultiStateValues {

    BinaryOutputValues values;
    final int UNIT = 24;
    final int DEFAULT_VALUE = 32;
    final int RELING_DEFAULT = 34;

    public void writeMultiStateValues(List<Row> MVRows) {
        Cell cell;
        final int PT_MEMO = 215;
        int[] MVwriteToStringCells = values.getBOwriteToStringCells();
        String[] MVwriteStringValuesToCell = values.getBOwriteStringValuestoCell();
        int[] MVwriteToDoubleCells = values.getBOwriteToDoubleCells();
        double[] MVwriteDoubleValuesToCell = values.getBOwriteDoubleValuesToCell();

        for (Row row : MVRows) {
            if (row.getCell(UNIT).toString().isEmpty()) {
                if(!row.getCell(PT_MEMO).toString().isEmpty() && !row.getCell(PT_MEMO).toString().equals("[[ACE:|||]])")){
                    String ptMemo = row.getCell(PT_MEMO).toString(); // e.g. [[ACE:|||States (0-2)]]
                    String subMemo = ptMemo.substring(9); // States (0-2)]]
                    String cleanMemo = subMemo.replaceAll("]", ""); //States (0-2);
                    cell = row.getCell(UNIT);
                    cell.setCellValue(cleanMemo);
                    cell = row.getCell(DEFAULT_VALUE);
                    cell.setCellValue("State 0");
                    cell = row.getCell(RELING_DEFAULT);
                    cell.setCellValue("State 0");
                }
            }
            for (int i = 0; i < MVwriteToStringCells.length; i++) {
                cell = row.getCell(MVwriteToStringCells[i]);
                if (MVwriteStringValuesToCell[i].equals("WAHR")) {
                    cell.setCellValue(true);
                } else if (MVwriteStringValuesToCell[i].equals("FALSCH")) {
                    cell.setCellValue(false);
                } else {
                    cell.setCellValue(MVwriteStringValuesToCell[i]);
                }
            }
            for (int i = 0; i < MVwriteToDoubleCells.length; i++) {
                cell = row.getCell(MVwriteToDoubleCells[i]);
                cell.setCellValue(MVwriteDoubleValuesToCell[i]);
            }
            if (row.getCell(DEFAULT_VALUE).toString().isEmpty()) {
                generateDefaultValue(row);
            }
        }
    }

    private void generateDefaultValue(Row row) {
        Cell cell;
        String valueUnit = row.getCell(UNIT).toString();
        String [] defaultValue = valueUnit.split("/");
        cell = row.getCell(DEFAULT_VALUE);
        cell.setCellValue(defaultValue[0]);
        cell = row.getCell(RELING_DEFAULT);
        cell.setCellValue(defaultValue[0]);
    }
}

