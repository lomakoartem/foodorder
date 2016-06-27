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

@Component
public class ParserForExcelFile {

	public ParserForExcelFile() {
	}

	public List<User> parse(MultipartFile file) {
		List<User> users = new ArrayList<>();
		Iterator<Row> rowIterator;
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(file.getBytes());
			
			if (file.getOriginalFilename().endsWith("xls")) {
				HSSFWorkbook workbook = new HSSFWorkbook(bis);
				HSSFSheet sheet = workbook.getSheetAt(0);
				rowIterator = sheet.iterator();
			} else if (file.getOriginalFilename().endsWith("xlsx")) {
				XSSFWorkbook workbook = new XSSFWorkbook(bis);
				XSSFSheet sheet = workbook.getSheetAt(0);
				rowIterator = sheet.iterator();
			} else {
				throw new IllegalArgumentException("Received file does not have a standard excel extension.");
			}

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
                        
                        if(splitedUserName.length > 1){
                        	User user = new User();
                       	 	user.setName(userName);
                       	 	StringBuffer namesForEmail = new StringBuffer();
                            for(String name : splitedUserName){
                            	namesForEmail.append(name+"_");
                            }
                            String firstPartOfEmail = namesForEmail.toString();
                            String userEmail = firstPartOfEmail.substring(0, firstPartOfEmail.length() - 1);
                            user.setEmail(userEmail+"@epam.com");
                            if(hiperLink != null){
                            	user.setHiperlink(hiperLink.getAddress());
                            }
                            users.add(user);
                        }
					break;
					}
				}
			}
			bis.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		

		return users;
	}
}
