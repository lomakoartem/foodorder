package ua.rd.foodorder.infrastructure;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import ua.rd.foodorder.infrastructure.exceptions.FileParsingException;
import ua.rd.foodorder.infrastructure.exceptions.UnsupportedFileExtentionException;

public interface IEmployeeFileParser {

	/**
	 * Parses given file and finds all information about employees that is
	 * given.
	 * <p>
	 * If file was given to parser that don't supports that file's extention -
	 * {@link UnsupportedFileExtentionException} is throwed. If there was any
	 * exception with file content or some issues with file system -
	 * {@link FileParsingException} is throwed
	 * 
	 * @param file
	 *            file with employees information
	 * @return list of {@link UserNameAndUpsaLinkTuple} that holds all valuable
	 *         information about employees from file
	 * @throws UnsupportedFileExtentionException
	 *             when received file with extention that is not supported by
	 *             this parser
	 * @throws FileParsingException
	 *             when encountered some issues with file content or file system
	 */
	List<UserNameAndUpsaLinkTuple> parse(MultipartFile file);
}
