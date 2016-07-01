package ua.rd.foodorder.infrastructure;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.poi.common.usermodel.Hyperlink;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import ua.rd.foodorder.infrastructure.exceptions.FileParsingException;
import ua.rd.foodorder.infrastructure.exceptions.UnsupportedFileExtentionException;

@Component
public class EmployeeExcelFileParser implements IEmployeeFileParser {
	
	private static final String FILE_EXTENTION_XLS = "xls";
	private static final String FILE_EXTENTION_XLSX = "xlsx";
	private static final char EXTENSION_SEPARATOR = '.';
	private static final String SPACE_REGEXP = "\\s";
	
	@Override
	public List<UserNameAndUpsaLinkTuple> parse(MultipartFile file) {
		checkExtension(file.getOriginalFilename());
		try (ByteArrayInputStream bis = new ByteArrayInputStream(file.getBytes());
				HSSFWorkbook workbook = new HSSFWorkbook(bis)) {
			
			Iterator<Row> rowIterator = getRows(workbook);
			List<UserNameAndUpsaLinkTuple> tuplesList = iterateOverRows(rowIterator);
			return tuplesList;
		} catch (IOException e) {
			throw new FileParsingException(e);
		}
	}
	
	private void checkExtension(String fileName) {
		if (!(fileName.endsWith(FILE_EXTENTION_XLSX) || fileName.endsWith(FILE_EXTENTION_XLS))) {
			String fileExtension = fileName.substring(fileName.lastIndexOf(EXTENSION_SEPARATOR));
			throw new UnsupportedFileExtentionException("Received file does not have a standard excel extension.", fileExtension);
		}
	}

	private Iterator<Row> getRows(HSSFWorkbook workbook) {
		HSSFSheet sheet = workbook.getSheetAt(0);
		return sheet.iterator();
	}
	
	private List<UserNameAndUpsaLinkTuple> iterateOverRows(Iterator<Row> rowIterator) {
		List<UserNameAndUpsaLinkTuple> tuplesList = new ArrayList<>();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();

			Optional<UserNameAndUpsaLinkTuple> userNameAndUpsaLinkTupleOptional = 
					getUserNameAndUpsaLinkTupleFromCellIterator(cellIterator);
			if (userNameAndUpsaLinkTupleOptional.isPresent()) {
				tuplesList.add(userNameAndUpsaLinkTupleOptional.get());
			}
		}
		return tuplesList;
	}

	private Optional<UserNameAndUpsaLinkTuple> getUserNameAndUpsaLinkTupleFromCellIterator(Iterator<Cell> cellIterator) {
		Cell cell = cellIterator.next();

		if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			if (cellContainsUser(cell)) {
				return Optional.of(createUserNameAndUpsaLinkTuple(cell));
			} else {
				return Optional.ofNullable(null);
			}
		} else {
			throw new FileParsingException("Wrong content of file");
		}
	}

	private boolean cellContainsUser(Cell cell) {
		Hyperlink upsaLink = cell.getHyperlink();
		String userName = cell.getStringCellValue();
		String[] splitedUserName = userName.trim().split(SPACE_REGEXP);
		return (splitedUserName.length > 1) && (upsaLink != null);
	}
	
	private UserNameAndUpsaLinkTuple createUserNameAndUpsaLinkTuple(Cell cell) {
		String userName = cell.getStringCellValue();
		Hyperlink upsaLink = cell.getHyperlink();
		UserNameAndUpsaLinkTuple userNameAndUpsaLinkTuple = new UserNameAndUpsaLinkTuple(userName, upsaLink.getAddress());
		return userNameAndUpsaLinkTuple;
	}
}
