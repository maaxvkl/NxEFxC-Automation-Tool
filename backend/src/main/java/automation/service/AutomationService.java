package automation.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import automation.filehandling.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
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
	WriteMultiStateValues MVWriter;
	WriteAnalogValues AVWriter;
	WriteBinaryValues BVWriter;

	public byte[] generateExcelFile(MultipartFile file, boolean AITrends, boolean AOTrends) throws IOException, IllegalArgumentException {
		XSSFWorkbook wb = excelReader.getWorkbookFromExcelFile(file);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		dataTypes.saveDataTypesToList(wb);
		AIWriter.writeAnalogInputs(dataTypes.getAnalogInputs(), AITrends);
		BIWriter.writeBinaryInputs(dataTypes.getBinaryInputs());
		BOWriter.writeBinaryOutputs(dataTypes.getBinaryOutputs());
		AOWriter.writeAnalogOutputs(dataTypes.getAnalogOutputs(), AOTrends);
		if(!dataTypes.getMultiStateValues().isEmpty()){
			MVWriter.writeMultiStateValues(dataTypes.getMultiStateValues());
		}
		if(!dataTypes.getAnalogValues().isEmpty()){
			AVWriter.writeAnalogValues(dataTypes.getAnalogValues());
		}
		if(!dataTypes.getBinaryValues().isEmpty()){
			BVWriter.writeBinaryValues(dataTypes.getBinaryValues());
		}
		wb.write(outputStream);
		outputStream.close();	
		return outputStream.toByteArray();

	}
}
