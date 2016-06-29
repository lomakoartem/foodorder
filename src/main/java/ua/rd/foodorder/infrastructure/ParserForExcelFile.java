package ua.rd.foodorder.infrastructure;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.common.usermodel.Hyperlink;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import ua.rd.foodorder.domain.User;
import ua.rd.foodorder.infrastructure.exceptions.UnsupportedFileExtentionException;
import ua.rd.foodorder.infrastructure.exceptions.WrongFileContentException;

@Component
public class ParserForExcelFile {

	public List<User> parse(MultipartFile file) {
		List<User> users = new ArrayList<>();
	
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(file.getBytes());
			
			Iterator<Row> rowIterator = getRows(file, bis);

			iterateOverRows(users, rowIterator);
			
			bis.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return users;
	}

	private Iterator<Row> getRows(MultipartFile file, ByteArrayInputStream bis)
			throws IOException {
		if (file.getOriginalFilename().endsWith("xls")) {
			HSSFWorkbook workbook = new HSSFWorkbook(bis);
			HSSFSheet sheet = workbook.getSheetAt(0);
			return sheet.iterator();
		} else if (file.getOriginalFilename().endsWith("xlsx")) {
			XSSFWorkbook workbook = new XSSFWorkbook(bis);
			XSSFSheet sheet = workbook.getSheetAt(0);
			return sheet.iterator();
		} else {
			String fileName = file.getOriginalFilename();
			throw new UnsupportedFileExtentionException("Received file does not have a standard excel extension.", fileName.substring(fileName.lastIndexOf('.')));
		}
	}
	
	private void iterateOverRows(List<User> users, Iterator<Row> rowIterator) {
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();

			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();

				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
					String userName = cell.getStringCellValue();
		            Hyperlink hiperLink = cell.getHyperlink();
		            String[] splitedUserName = userName.trim().split("\\s");
		            
		            if(splitedUserName.length > 1 && hiperLink != null){
		            	User user = new User();
		           	 	user.setName(userName);
		           	 	StringBuilder namesForEmail = new StringBuilder();
		                for(String name : splitedUserName){
		                	namesForEmail.append(name+"_");
		                }
		                String firstPartOfEmail = namesForEmail.toString();
		                String userEmail = firstPartOfEmail.substring(0, firstPartOfEmail.length() - 1);
		                user.setEmail(userEmail+"@epam.com");
		                user.setHiperlink(hiperLink.getAddress());
		                users.add(user);
		            }
				break;
				default:
					throw new WrongFileContentException("Wrong content of file");
				}
			}
		}
	}
	
}
