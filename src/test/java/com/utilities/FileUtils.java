package com.utilities;

import java.io.File;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.openqa.common.RestApiHelper;

public class FileUtils {

	protected final static Logger logger = LogManager.getLogger(FileUtils.class.getName());

	public static InputStream openFileAsInputStream(String filename) throws Exception {
		if (StringUtils.isEmpty(filename)) {
			logger.fatal("Empty file name! ");
			throw new Exception("Filename is empty!");
		}
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
	}

	public static boolean isFileExisted(String path) {
		if (new File(path).exists())
			return true;
		logger.error("File \"" + path + "\" doesn't exist! ");
		return false;
	}

	public static String[] getSubfoldersList(String path) throws Exception {
		if (isFileExisted(path)) {
			File file = new File(path);
			String[] directories = file.list(new FilenameFilter() {

				@Override
				public boolean accept(File current, String name) {
					return new File(current, name).isDirectory();
				}
			});
			return directories;
		}
		logger.info("No sub-folder detected! ");
		return null;
	}

	public static String getFileContent(String path) throws Exception {
		if (isFileExisted(path))
			return new String(Files.readAllBytes(Paths.get(path)));
		logger.error("File content is null! ");
		return null;
	}

	public static String convertFileContentToDataStream(String path) throws Exception {
		if (isFileExisted(path)) {
			StringWriter stringWriter = new StringWriter();
			TransformerFactory.newInstance().newTransformer().transform(new DOMSource(DocumentBuilderFactory
					.newInstance().newDocumentBuilder().parse(new File(path).toURI().toURL().getPath())),
					new StreamResult(stringWriter));
			return stringWriter.getBuffer().toString();
		}
		logger.error("Data is null! ");
		return null;
	}

	public static String buildURIFromFileContent(String filePath) throws Exception {
		if (isFileExisted(filePath)) {
			String uriParams = getFileContent(filePath + "/URL.txt");
			String uRI = RestApiHelper.REST_API_HOST + uriParams;
			return uRI;
		} else {
			logger.error("URI is null! ");
			return null;
		}
	}
}
