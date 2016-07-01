/**
 *
 */
package ua.rd.foodorder.infrastructure;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class EmployeeExcelFileParserTest {

	private static final String PATH_TO_TEST_EMPLOYEES_FILE = "src/test/resources/UPSA report_example.xlsx";

	private IEmployeeFileParser parser;

	@Before
	public void setUp() {
		parser = new EmployeeExcelFileParser();
	}

	@Test
	public void testParseFileReturnsApropriateNumberOfEmployees() throws FileNotFoundException, IOException {
		MultipartFile multipartFile = getMultipartFileWithEmployees();
		List<UserNameAndUpsaLinkTuple> list = parser.parse(multipartFile);
		int employeesInFileCount = 11;
		assertEquals(employeesInFileCount, list.size());
	}

	private MultipartFile getMultipartFileWithEmployees() throws FileNotFoundException, IOException {
		final File file = getTestFileWithEmployees();
		try (FileInputStream fis = new FileInputStream(file)) {
			final MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain", IOUtils.toByteArray(fis));
			return multipartFile;
		}
	}

	private File getTestFileWithEmployees() {
		final File f = new File(PATH_TO_TEST_EMPLOYEES_FILE);
		return f;
	}

}
