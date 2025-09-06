package automation.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import automation.filehandling.ReadExcelFile;
import automation.filehandling.SavedDataTypes;
import automation.filehandling.WriteAnalogInputs;
import automation.filehandling.WriteAnalogOutputs;
import automation.filehandling.WriteBinaryInputs;
import automation.filehandling.WriteBinaryOutputs;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AutomationService {

	ReadExcelFile excelReader;
	SavedDataTypes dataTypes;
	WriteAnalogInputs AIWriter;
	WriteBinaryInputs BIWriter;
	WriteBinaryOutputs BOWriter;
	WriteAnalogOutputs AOWriter;

	public byte[] generateExcelFile(MultipartFile file) throws IOException, IllegalArgumentException {
		XSSFWorkbook wb = excelReader.getWorkbookFromExcelFile(file);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		dataTypes.saveDataTypesToList(wb);
		AIWriter.writeAnalogInputs(dataTypes.getAnalogInputs());
		BIWriter.writeBinaryInputs(dataTypes.getBinaryInputs());
		BOWriter.writeBinaryOutputs(dataTypes.getBinaryOutputs());
		AOWriter.writeAnalogOutputs(dataTypes.getAnalogOutputs());
		wb.write(outputStream);
		outputStream.close();
		return outputStream.toByteArray();

	}
}
