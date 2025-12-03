package automation.filehandling;

import automation.values.BinaryInputValues;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class WriteBinaryValues {

    BinaryInputValues values;

   public void writeBinaryValues(List<Row> BVRows){
        Cell cell = null;
        int UNIT = 24;
        int[] BIwriteToStringCells = values.getBIwriteToStringCells();
        String[] BIwriteStringValuesToCell = values.getBIwriteStringValuestoCell();
        int[] BIwriteToDoubleCells = values.getBIwriteToDoubleCells();
        double[] BIwriteDoubleValuesToCell = values.getBIwriteDoubleValuesToCell();

        for (Row row : BVRows) {
            String unit = row.getCell(UNIT).toString(); // e.g. Normal/Alarm
            String[] splitUnit = unit.contains("/") ? unit.split("\\/") : unit.split(" ");
            setValues(row, splitUnit);
        }

        for(Row row : BVRows) {
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
