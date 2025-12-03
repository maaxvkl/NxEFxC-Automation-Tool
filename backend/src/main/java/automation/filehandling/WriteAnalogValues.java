package automation.filehandling;

import automation.values.AnalogInputValues;
import automation.values.AnalogTrendValues;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class WriteAnalogValues {

    AnalogInputValues values;

    public void writeAnalogValues(List<Row> AVRows){
        Cell cell = null;
        int[] AIwriteToStringCells = values.getAIwriteToStringCells();
        String[] AIwriteStringValuesToCell = values.getAIwriteStringValuesToCell();
        int[] AIwriteToDoubleCells = values.getAIwriteToDoubleCells();
        double[] AIwriteDoubleValuesToCell = values.getAIwriteDoubleValuesToCell();


        for (Row row : AVRows) {
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

    }
}
