package com.upic.api.manger;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.upic.DocPo;
import com.upic.utils.HttpDeal;

@Component
public class DocManger {

	private static final String URL = "http://v.juhe.cn/fileconvert/query";

	private static final String KEY = "";
	// @Autowired
	private HttpDeal httpDeal;

	/**
	 * 获取天气预报XML信息并返回
	 * 
	 * @param source
	 * @return
	 */
	public DocPo getDocToPdfUrl(String url) {
		try {
			Map<String, String> maps = new HashMap<String, String>();
			maps.put("key", "f9e2d6f9ed00da1300c7bc891e91f916");
			maps.put("url", url);
			String post = HttpDeal.post(URL, maps);
			System.out.println(post);
			DocPo parseObject = JSONObject.parseObject(post, DocPo.class);
			System.out.println(parseObject.toString());
			return parseObject;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public static void main(String[] args) {
		DocManger doc=new DocManger();
		doc.getDocToPdfUrl("http://47.98.253.246:8080/group1/M00/00/01/rBCh-FsCTASALuABAfJ-APHldC4369.ppt");
	}
}
