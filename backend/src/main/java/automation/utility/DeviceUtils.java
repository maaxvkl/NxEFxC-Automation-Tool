package automation.utility;

import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import automation.enumeration.JCIDevices;

public class DeviceUtils {

	public static void seperateRowsByDevice(List<Row> dataTypeRows, List<Row> JCIRows, List<Row> NoJCIRows) {
		final int DEVICE_NAME = 8;
		DataFormatter formatter = new DataFormatter();

		for (Row row : dataTypeRows) {
			if (row == null)
				continue;

			Cell cell = row.getCell(DEVICE_NAME);
			if (cell == null) {
				continue;
			}

			String deviceName = formatter.formatCellValue(cell).trim().toUpperCase();

			boolean found = false;
			for (JCIDevices jciDevice : JCIDevices.values()) {
				if (deviceName.contains(jciDevice.name().toUpperCase())) {
					JCIRows.add(row);
					found = true;
					break;
				}
			}

			if (!found) {
				NoJCIRows.add(row);
			}
		}		
	}
}
