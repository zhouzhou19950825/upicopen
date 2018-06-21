package com.upic.doc.exchange;

import com.artofsolving.jodconverter.DefaultDocumentFormatRegistry;
import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.upic.test.FastDFSClient;

import java.io.*;
import java.net.ConnectException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author dtz
 *
 *服务启动 ：./soffice "-accept=socket,host=localhost,port=8100;urp;StarOffice.ServiceManager" -nologo -headless -nofirststartwizard &
 */
public class Doc2HtmlUtils {
	private static Doc2HtmlUtils doc2HtmlUtil;

	/**
	 * 获取Doc2HtmlUtil实例
	 */
	public static synchronized Doc2HtmlUtils getDoc2HtmlUtilInstance() {
		if (doc2HtmlUtil == null) {
			doc2HtmlUtil = new Doc2HtmlUtils();
		}
		return doc2HtmlUtil;
	}

	/**
	 * 转换文件成html
	 *
	 * @param fromFileInputStream:
	 * @throws IOException
	 */
	public String file2Html(InputStream fromFileInputStream, String toFilePath, String type, String host, int port)
			throws IOException {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String timesuffix = sdf.format(date);
		String docFileName = null;
		String htmFileName = null;
		if ("doc".equals(type)) {
			docFileName = "doc_" + timesuffix + ".doc";
			htmFileName = "doc_" + timesuffix + ".html";
		} else if ("docx".equals(type)) {
			docFileName = "docx_" + timesuffix + ".docx";
			htmFileName = "docx_" + timesuffix + ".html";
		} else if ("xls".equals(type)) {
			docFileName = "xls_" + timesuffix + ".xls";
			htmFileName = "xls_" + timesuffix + ".html";
		} else if ("ppt".equals(type)) {
			docFileName = "ppt_" + timesuffix + ".ppt";
			htmFileName = "ppt_" + timesuffix + ".html";
		} else {
			return null;
		}

		File htmlOutputFile = new File(toFilePath + File.separatorChar + htmFileName);
		File docInputFile = new File(toFilePath + File.separatorChar + docFileName);
		if (htmlOutputFile.exists())
			htmlOutputFile.delete();
		htmlOutputFile.createNewFile();
		if (docInputFile.exists())
			docInputFile.delete();
		docInputFile.createNewFile();
		/**
		 * 由fromFileInputStream构建输入文件
		 */
		try {
			OutputStream os = new FileOutputStream(docInputFile);
			int bytesRead = 0;
			byte[] buffer = new byte[1024 * 8];
			while ((bytesRead = fromFileInputStream.read(buffer)) != -1) {
				os.write(buffer, 0, bytesRead);
			}

			os.close();
			fromFileInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		OpenOfficeConnection connection = new SocketOpenOfficeConnection(host, port);
		try {
			connection.connect();
		} catch (ConnectException e) {
			System.err.println("文件转换出错，请检查OpenOffice服务是否启动。");
		}
		// convert
		DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
		converter.convert(docInputFile, htmlOutputFile);
		connection.disconnect();
		// 转换完之后删除word文件
		docInputFile.delete();
		return htmFileName;
	}

	/**
	 * 转换文件成pdf
	 *
	 * @param fromFileInputStream:
	 * @throws IOException
	 */
	public String file2pdf(InputStream fromFileInputStream, String toFilePath, String type, String host, int port)
			throws IOException {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String timesuffix = sdf.format(date);
		String docFileName = null;
		String htmFileName = null;
		if ("doc".equals(type)) {
			docFileName = "doc_" + timesuffix + ".doc";
			htmFileName = "doc_" + timesuffix + ".pdf";
		} else if ("docx".equals(type)) {
			docFileName = "docx_" + timesuffix + ".docx";
			htmFileName = "docx_" + timesuffix + ".pdf";
		} else if ("xls".equals(type)) {
			docFileName = "xls_" + timesuffix + ".xls";
			htmFileName = "xls_" + timesuffix + ".pdf";
		} else if ("ppt".equals(type)||type.contains("ppt")) {
			docFileName = "ppt_" + timesuffix + ".ppt";
			htmFileName = "ppt_" + timesuffix + ".pdf";
		} else {
			return null;
		}

		File htmlOutputFile = new File(toFilePath + File.separatorChar + htmFileName);
		File docInputFile = new File(toFilePath + File.separatorChar + docFileName);
		if (htmlOutputFile.exists())
			htmlOutputFile.delete();
		htmlOutputFile.createNewFile();
		if (docInputFile.exists())
			docInputFile.delete();
		docInputFile.createNewFile();
		/**
		 * 由fromFileInputStream构建输入文件
		 */
		try {
			OutputStream os = new FileOutputStream(docInputFile);
			int bytesRead = 0;
			byte[] buffer = new byte[1024 * 8];
			while ((bytesRead = fromFileInputStream.read(buffer)) != -1) {
				os.write(buffer, 0, bytesRead);
			}

			os.close();
			fromFileInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		OpenOfficeConnection connection = new SocketOpenOfficeConnection(host, port);
		try {
			connection.connect();
		} catch (ConnectException e) {
			System.err.println("文件转换出错，请检查OpenOffice服务是否启动。");
		}
		// convert
		DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
		converter.convert(docInputFile, htmlOutputFile);
		connection.disconnect();
		// 转换完之后删除word文件
		docInputFile.delete();
		return htmFileName;
	}

	/**
	 * 执行前，请启动openoffice服务 进入$OO_HOME\program下 执行soffice -headless
	 * -accept="socket,host=127.0.0.1,port=8100;urp;"
	 * 
	 * @param xlsfile
	 * @param targetfile
	 * @throws Exception
	 */
	public static void fileConvertPdf(String xlsfile, String targetfile, String type, String host, int port)
			throws Exception {
		File xlsf = new File(xlsfile);
		File targetF = new File(targetfile);
		// 获得文件格式
		DefaultDocumentFormatRegistry formatReg = new DefaultDocumentFormatRegistry();
		DocumentFormat pdfFormat = formatReg.getFormatByFileExtension("pdf");
		DocumentFormat docFormat = null;
		if ("doc".equals(type) || "docx".equals(type)) {
			docFormat = formatReg.getFormatByFileExtension("doc");
		} else if ("xls".equals(type) || "xlsx".equals(type)) {
			docFormat = formatReg.getFormatByFileExtension("xls");
		} else if ("ppt".equals(type)) {
			docFormat = formatReg.getFormatByFileExtension("ppt");
		} else {
			docFormat = formatReg.getFormatByFileExtension("doc");
		}
		// stream 流的形式
		InputStream inputStream = new FileInputStream(xlsf);
		OutputStream outputStream = new FileOutputStream(targetF);

		/**
		 *
		 */
		OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);
		try {

			connection.connect();
			DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
			converter.convert(inputStream, docFormat, outputStream, pdfFormat);
		} catch (ConnectException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();
				connection = null;
			}
		}
	}

	/**
	 * 执行前，请启动openoffice服务 进入$OO_HOME\program下 执行soffice -headless
	 * -accept="socket,host=127.0.0.1,port=8100;urp;" 或 soffice -headless
	 * -accept="socket,host=127.0.0.1,port=8100;urp;" -nofirststartwizard
	 * 
	 * @param inputStream
	 * @param outputStream
	 * @throws Exception
	 */
	public static void fileConvertPdf(InputStream inputStream, OutputStream outputStream, String type, String host,
			int port) throws Exception {
		// 获得文件格式
		DefaultDocumentFormatRegistry formatReg = new DefaultDocumentFormatRegistry();
		DocumentFormat pdfFormat = formatReg.getFormatByFileExtension("pdf");
		DocumentFormat docFormat = null;
		if (".doc".equals(type) || ".docx".equals(type)) {
			docFormat = formatReg.getFormatByFileExtension("doc");
		} else if (".xls".equals(type) || ".xlsx".equals(type)) {
			docFormat = formatReg.getFormatByFileExtension("xls");
		} else if (".ppt".equals(type)) {
			docFormat = formatReg.getFormatByFileExtension("ppt");
		} else if (".txt".equals(type)) {
			docFormat = formatReg.getFormatByFileExtension("txt");
		} else if (".pdf".equals(type)) {
			docFormat = formatReg.getFormatByFileExtension("pdf");
		} else {
			docFormat = formatReg.getFormatByFileExtension("doc");
		}
		// stream 流的形式
		OpenOfficeConnection connection = new SocketOpenOfficeConnection(host, port);
		try {

			connection.connect();
			DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
			converter.convert(inputStream, docFormat, outputStream, pdfFormat);
		} catch (ConnectException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();
				connection = null;
			}
		}
	}
	
}