package com.upic.utils;
import com.artofsolving.jodconverter.DefaultDocumentFormatRegistry;
import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

import java.io.*;
import java.net.ConnectException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author dtz
 *
 */
public class Doc2HtmlUtil {
    private static Doc2HtmlUtil doc2HtmlUtil;

    /**
     * 获取Doc2HtmlUtil实例
     */
    public static synchronized Doc2HtmlUtil getDoc2HtmlUtilInstance() {
        if (doc2HtmlUtil == null) {
            doc2HtmlUtil = new Doc2HtmlUtil();
        }
        return doc2HtmlUtil;
    }

    /**
     * 转换文件成html
     *
     * @param fromFileInputStream:
     * @throws IOException
     */
    public String file2Html(InputStream fromFileInputStream, String toFilePath,String type,String host,int port) throws IOException {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String timesuffix = sdf.format(date);
        String docFileName = null;
        String htmFileName = null;
        if("doc".equals(type)){
            docFileName = "doc_" + timesuffix + ".doc";
            htmFileName = "doc_" + timesuffix + ".html";
        }else if("docx".equals(type)){
            docFileName = "docx_" + timesuffix + ".docx";
            htmFileName = "docx_" + timesuffix + ".html";
        }else if("xls".equals(type)){
            docFileName = "xls_" + timesuffix + ".xls";
            htmFileName = "xls_" + timesuffix + ".html";
        }else if("ppt".equals(type)){
            docFileName = "ppt_" + timesuffix + ".ppt";
            htmFileName = "ppt_" + timesuffix + ".html";
        }else{
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

        OpenOfficeConnection connection = new SocketOpenOfficeConnection(host,port);
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
    public String file2pdf(InputStream fromFileInputStream, String toFilePath,String type,String host,int port) throws IOException {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String timesuffix = sdf.format(date);
        String docFileName = null;
        String htmFileName = null;
        if("doc".equals(type)){
            docFileName = "doc_" + timesuffix + ".doc";
            htmFileName = "doc_" + timesuffix + ".pdf";
        }else if("docx".equals(type)){
            docFileName = "docx_" + timesuffix + ".docx";
            htmFileName = "docx_" + timesuffix + ".pdf";
        }else if("xls".equals(type)){
            docFileName = "xls_" + timesuffix + ".xls";
            htmFileName = "xls_" + timesuffix + ".pdf";
        }else if("ppt".equals(type)){
            docFileName = "ppt_" + timesuffix + ".ppt";
            htmFileName = "ppt_" + timesuffix + ".pdf";
        }else{
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

        OpenOfficeConnection connection = new SocketOpenOfficeConnection(host,port);
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
     * 执行前，请启动openoffice服务
     * 进入$OO_HOME\program下
     * 执行soffice -headless -accept="socket,host=127.0.0.1,port=8100;urp;"
     * @param xlsfile
     * @param targetfile
     * @throws Exception
     */
    public static void fileConvertPdf(String xlsfile, String targetfile,String type,String host,int port)
            throws Exception {
        File xlsf = new File(xlsfile);
        File targetF = new File(targetfile);
        // 获得文件格式
        DefaultDocumentFormatRegistry formatReg = new DefaultDocumentFormatRegistry();
        DocumentFormat pdfFormat = formatReg.getFormatByFileExtension("pdf");
        DocumentFormat docFormat = null;
        if("doc".equals(type) || "docx".equals(type)){
            docFormat = formatReg.getFormatByFileExtension("doc");
        }else if("xls".equals(type) || "xlsx".equals(type)){
            docFormat = formatReg.getFormatByFileExtension("xls");
        }else if("ppt".equals(type)){
            docFormat = formatReg.getFormatByFileExtension("ppt");
        }else{
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
     * 执行前，请启动openoffice服务
     * 进入$OO_HOME\program下
     * 执行soffice -headless -accept="socket,host=127.0.0.1,port=8100;urp;"  或 soffice -headless -accept="socket,host=127.0.0.1,port=8100;urp;" -nofirststartwizard
     * @param inputStream
     * @param outputStream
     * @throws Exception
     */
    public static void fileConvertPdf(InputStream inputStream, OutputStream outputStream,String type,String host,int port)
            throws Exception {
        // 获得文件格式
        DefaultDocumentFormatRegistry formatReg = new DefaultDocumentFormatRegistry();
        DocumentFormat pdfFormat = formatReg.getFormatByFileExtension("pdf");
        DocumentFormat docFormat = null;
        if(".doc".equals(type) || ".docx".equals(type)){
            docFormat = formatReg.getFormatByFileExtension("doc");
        }else if(".xls".equals(type) || ".xlsx".equals(type)){
            docFormat = formatReg.getFormatByFileExtension("xls");
        }else if(".ppt".equals(type)){
            docFormat = formatReg.getFormatByFileExtension("ppt");
        }else if(".txt".equals(type)){
            docFormat = formatReg.getFormatByFileExtension("txt");
        }else if(".pdf".equals(type)){
            docFormat = formatReg.getFormatByFileExtension("pdf");
        }else{
            docFormat = formatReg.getFormatByFileExtension("doc");
        }
        // stream 流的形式
        OpenOfficeConnection connection = new SocketOpenOfficeConnection(host,port);
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


    public static void main(String[] args) throws Exception {

//        URL url=new URL("http://47.98.253.246:8080/group1/M00/00/01/rBCh-FsCTASALuABAfJ-APHldC4369.ppt");//默认主页
//        InputStream is=url.openStream();//获取网络流


        /*//获取网络资源,编码格式不同会出现乱码****************
        byte[] flush=new byte[1024];
        int len=0;
        while(-1!=(len=is.read(flush)))
        {

            System.out.println(new String(flush,0,len));
        }
        is.close();
        //获取网络资源,编码格式不同会出现乱码*****************/


        //解决乱码的方法,转换流
//        BufferedReader br=new BufferedReader(new InputStreamReader(url.openStream(),"utf-8"));//解码方式,utf-8
//        String msg=null;
//        BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(new FileOutputStream("e:\\Temp\\1.ppt"),"utf-8"));
//        while((msg=br.readLine())!=null)
//        {
//            bw.append(msg);
//            bw.newLine();
//        }
//        bw.flush();
//        bw.close();
//        br.close();




        Doc2HtmlUtil coc2HtmlUtil = getDoc2HtmlUtilInstance();
//        String a = "D:\\poi-test\\exportExcel.xls";
//        String b = "D:\\poi-test\\exportExcel.pdf";
//        InputStream inputStream = new FileInputStream(a);
//        OutputStream outputStream = new FileOutputStream(b);
//        coc2HtmlUtil.fileConvertPdf(inputStream,outputStream,"xls");


       File file = null;
        FileInputStream fileInputStream = null;

        file = new File("e:\\Temp\\testoffice\\2.ppt");
        fileInputStream = new FileInputStream(file);
     // coc2HtmlUtil.file2Html(fileInputStream, "D:/poi-test/openOffice/xls","xls");
        //(InputStream fromFileInputStream, String toFilePath,String type,String host,int port)
        coc2HtmlUtil.file2pdf(fileInputStream, "e:\\Temp\\testoffice","ppt","127.0.01",8100);

/*        file = new File("D:/poi-test/test.doc");
        fileInputStream = new FileInputStream(file);
//      coc2HtmlUtil.file2Html(fileInputStream, "D:/poi-test/openOffice/doc","doc");
        coc2HtmlUtil.file2pdf(fileInputStream, "D:/poi-test/openOffice/doc","doc");

        file = new File("D:/poi-test/周报模版.ppt");
        fileInputStream = new FileInputStream(file);
//      coc2HtmlUtil.file2Html(fileInputStream, "D:/poi-test/openOffice/ppt","ppt");
        coc2HtmlUtil.file2pdf(fileInputStream, "D:/poi-test/openOffice/ppt","ppt");

        file = new File("D:/poi-test/test.docx");
        fileInputStream = new FileInputStream(file);
//      coc2HtmlUtil.file2Html(fileInputStream, "D:/poi-test/openOffice/docx","docx");
        coc2HtmlUtil.file2pdf(fileInputStream, "D:/poi-test/openOffice/docx","docx");*/

    }

}