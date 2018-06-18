package com.upic.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upic.dao.ExchangeRepository;
import com.upic.doc.exchange.Doc2HtmlUtils;
import com.upic.po.ExchangePo;
import com.upic.test.FastDFSClient;

@RestController
public class ExchangeController {
	protected static final Logger LOGGER = LoggerFactory.getLogger(ExchangeController.class);
	
	private static final String BASE_URL="http://10.21.10.119:8080/";
	
	private static final String DIR="data";
	
	@Autowired
	private ExchangeRepository exchangeRepository;
	@PostMapping("/exchange")
	public String returnUrl(String url) {
		try {
			if(!url.startsWith(BASE_URL)) {
				throw new Exception("exchange url error,error url:"+BASE_URL);
			}
			ExchangePo findByOldUrl = exchangeRepository.findByOldUrl(url);
			if(findByOldUrl!=null) {
				return findByOldUrl.getTargetUrl();
			}
			String saveExchange = saveExchange(url);
			ExchangePo e =new ExchangePo();
			e.setOldUrl(url);
			e.setTargetUrl(saveExchange);
			exchangeRepository.save(e);
			return saveExchange;
		} catch (Exception e) {
			LOGGER.error("returnUrl:"+e.getMessage());
		}
		return null;
	}
	private String saveExchange(String url) throws IOException {
		Doc2HtmlUtils coc2HtmlUtil = Doc2HtmlUtils.getDoc2HtmlUtilInstance();
		InputStream downloadFile = FastDFSClient.downloadFile(url.split(BASE_URL)[1]);
		String file2pdf = coc2HtmlUtil.file2pdf(downloadFile, DIR, url.split(".")[1], "10.21.10.120", 8100);
		System.out.println(file2pdf);
		File file = new File(DIR+"/"+file2pdf);
		String uploadFile = FastDFSClient.uploadFile(file, file2pdf);
		System.out.println(uploadFile);
		file.delete();
		return BASE_URL+uploadFile;
	}
}
