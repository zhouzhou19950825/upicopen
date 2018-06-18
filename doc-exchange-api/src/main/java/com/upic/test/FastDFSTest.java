package com.upic.test;

import java.io.File;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;



/**
 * 
 * @描述: FastDFS测试 .
 * @作者: WuShuicheng .
 * @创建时间: 2015-3-29,下午8:11:36 .
 * @版本号: V1.0 .
 */
public class FastDFSTest {
	
	/**
	 * 上传测试.
	 * @throws Exception
	 */
	public static void upload() throws Exception {
		String filePath = "C:\\Users\\dtz\\Desktop\\1.pptx";
		File file = new File(filePath);
		String fileId = FastDFSClient.uploadFile(file, filePath);
		System.out.println("Upload local file " + filePath + " ok, fileid=" + fileId);
		// fileId:	group1/M00/00/00/wKgEfVUYPieAd6a0AAP3btxj__E335.jpg
		// url:	http://192.168.4.125:8888/group1/M00/00/00/wKgEfVUYPieAd6a0AAP3btxj__E335.jpg
	}
	
	/**
	 * 下载测试.
	 * @throws Exception
	 */
	public static void download() throws Exception {
		String fileId = "group1/M00/00/00/wKgcg1nK5ByAZveDAAKRv62J1q8856.png";
		InputStream inputStream = FastDFSClient.downloadFile(fileId);
		File destFile = new File("C:\\Users\\dtz\\Desktop\\资产租赁\\a.png");
		FileUtils.copyInputStreamToFile(inputStream, destFile);
	}

	/**
	 * 删除测试
	 * @throws Exception
	 */
	public static void delete() throws Exception {
		int result;
		// @formatter:off
		 String fileId = "group1/M00/00/00/ChUKd1rIZseAHp8MAA7tw3atOv8828.jpg";
		result = FastDFSClient.deleteFile(fileId);
		// @formatter:on

		System.out.println(result == 0 ? "删除成功" : "删除失败:" + result);
	}


	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		upload();
//		download();
//		delete();

	}

}
