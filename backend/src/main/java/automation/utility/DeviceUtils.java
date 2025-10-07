package automation.utility;

import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import automation.enumeration.JCIDevices;

public class DeviceUtils {
	
	private final static int DEVICE_NAME = 8;

	private DeviceUtils() {

	}

	public static void seperateRowsByDevice(List<Row> DataTypeRows, List<Row> JCIRows, List<Row> NoJCIRows) {
		for (Row row : DataTypeRows) {
			String deviceName = row.getCell(DEVICE_NAME).getStringCellValue().trim();
			boolean found = false;
			for (JCIDevices jciDevices : JCIDevices.values()) {
				if (deviceName.contains(jciDevices.name())) {
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
