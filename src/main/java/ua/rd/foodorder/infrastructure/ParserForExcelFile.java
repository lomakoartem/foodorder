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

import ua.rd.foodorder.infrastructure.exceptions.UnsupportedFileExtentionException;
import ua.rd.foodorder.infrastructure.exceptions.WrongFileContentException;

@Component
public class ParserForExcelFile {
	
	private static final String FILE_EXTENTION_XLS = "xls";
	private static final String FILE_EXTENTION_XLSX = "xlsx";
	private static final char EXTENSION_SEPARATOR = '.';
	private static final String SPACE_REGEXP = "\\s";
	
	public List<GenericTuple<String, String>> parse(MultipartFile file) throws IOException {
		checkExtension(file.getOriginalFilename());
		ByteArrayInputStream bis = new ByteArrayInputStream(file.getBytes());
		Iterator<Row> rowIterator = getRows(file.getOriginalFilename(), bis);
		List<GenericTuple<String, String>> tuplesList = iterateOverRows(rowIterator);
		return tuplesList;
	}
	
	private void checkExtension(String fileName) {
		if (!(fileName.endsWith(FILE_EXTENTION_XLSX) || fileName.endsWith(FILE_EXTENTION_XLS))) {
			String fileExtension = fileName.substring(fileName.lastIndexOf(EXTENSION_SEPARATOR));
			throw new UnsupportedFileExtentionException("Received file does not have a standard excel extension.", fileExtension);
		}
	}

	private Iterator<Row> getRows(String originalFileName, ByteArrayInputStream bis)
			throws IOException {
		HSSFWorkbook workbook = new HSSFWorkbook(bis);
		HSSFSheet sheet = workbook.getSheetAt(0);
		return sheet.iterator();
	}
	
	private List<GenericTuple<String, String>> iterateOverRows(Iterator<Row> rowIterator) {
		List<GenericTuple<String, String>> tuplesList = new ArrayList<>();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();

			Optional<GenericTuple<String, String>> userNameAndUpsaLinkTupleOptional = 
					getUserNameAndUpsaLinkTupleFromCellIterator(cellIterator);
			if (userNameAndUpsaLinkTupleOptional.isPresent()) {
				tuplesList.add(userNameAndUpsaLinkTupleOptional.get());
			}
		}
		return tuplesList;
	}

	private Optional<GenericTuple<String, String>> getUserNameAndUpsaLinkTupleFromCellIterator(Iterator<Cell> cellIterator) {
		Cell cell = cellIterator.next();

		if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			if (cellContainsUser(cell)) {
				return Optional.of(createUserNameAndUpsaLinkTuple(cell));
			} else {
				return Optional.ofNullable(null);
			}
		} else {
			throw new WrongFileContentException("Wrong content of file");
		}
	}

	private boolean cellContainsUser(Cell cell) {
		Hyperlink upsaLink = cell.getHyperlink();
		String userName = cell.getStringCellValue();
		String[] splitedUserName = userName.trim().split(SPACE_REGEXP);
		return (splitedUserName.length > 1) && (upsaLink != null);
	}
	
	private GenericTuple<String, String> createUserNameAndUpsaLinkTuple(Cell cell) {
		String userName = cell.getStringCellValue();
		Hyperlink upsaLink = cell.getHyperlink();
		GenericTuple<String, String> userNameAndUpsaLinkTuple = new GenericTuple<>(userName, upsaLink.getAddress());
		return userNameAndUpsaLinkTuple;
	}
}
