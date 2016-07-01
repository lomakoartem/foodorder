package ua.rd.foodorder.infrastructure;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.poi.common.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import ua.rd.foodorder.infrastructure.exceptions.FileParsingException;
import ua.rd.foodorder.infrastructure.exceptions.UnsupportedFileExtentionException;

@Component
public class EmployeeExcelFileParser implements IEmployeeFileParser {

	private static final Logger LOG = LoggerFactory.getLogger(EmployeeExcelFileParser.class);

	private static final int SHEET_NUMBER = 0;
	private static final String FILE_EXTENTION_XLS = "xls";
	private static final String FILE_EXTENTION_XLSX = "xlsx";
	private static final char EXTENSION_SEPARATOR = '.';
	private static final String SPACE_REGEXP = "\\s";

	@Override
	public List<UserNameAndUpsaLinkTuple> parse(MultipartFile file) {
		checkExtension(file.getOriginalFilename());
		try (ByteArrayInputStream bis = new ByteArrayInputStream(file.getBytes());
				XSSFWorkbook workbook = new XSSFWorkbook(bis)) {

			Iterator<Row> rowIterator = getRows(workbook);
			List<UserNameAndUpsaLinkTuple> tuplesList = iterateOverRows(rowIterator);
			return tuplesList;
		} catch (final Exception e) {
			LOG.error("Got exception while file parsing ", e);
			throw new FileParsingException(e);
		}
	}

	private void checkExtension(String fileName) {
		if (!(fileName.endsWith(FILE_EXTENTION_XLSX) || fileName.endsWith(FILE_EXTENTION_XLS))) {
			String fileExtension = fileName.substring(fileName.lastIndexOf(EXTENSION_SEPARATOR));
			throw new UnsupportedFileExtentionException("Received file does not have a standard excel extension.", fileExtension);
		}
	}

	private Iterator<Row> getRows(XSSFWorkbook workbook) {
		XSSFSheet sheet = workbook.getSheetAt(SHEET_NUMBER);
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
		if ((cell.getCellType() == Cell.CELL_TYPE_STRING) && cellContainsUser(cell)) {
			return Optional.of(createUserNameAndUpsaLinkTuple(cell));
		}
		return Optional.ofNullable(null);
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
