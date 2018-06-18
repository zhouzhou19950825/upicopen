package com.upic.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

/**
 * 在java中处理http请求.
 * 
 * @author nagsh
 * 
 */
@Component
public class HttpDeal {

	private final static String GET_USEER = "http://URL:PORT/amconsole/SimpleRestService?loginType=cd&artifact=AFFID";

	/**
	 * 处理get请求.
	 * 
	 * @param url
	 *            请求路径
	 * @return json
	 */
	public static String get(String url) {
		// 实例化httpclient
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 实例化get方法
		HttpGet httpget = new HttpGet(url);
		// 请求结果
//		httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:39.0) Gecko/20100101 Firefox/39.0");  
		CloseableHttpResponse response = null;
		String content = "";
		try {
			// 执行get方法
			response = httpclient.execute(httpget);
			if (response.getStatusLine().getStatusCode() == 200) {
				content = EntityUtils.toString(response.getEntity(), "utf-8");
				// System.out.println(content);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}

	/**
	 * 处理post请求.
	 * 
	 * @param url
	 *            请求路径
	 * @param params
	 *            参数
	 * @return json
	 * @throws Exception
	 */
	public static String post(String url, Map<String, String> params) throws Exception {
		// 实例化httpClient
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 实例化post方法
		HttpPost httpPost = new HttpPost(url);
		// 处理参数
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		Set<String> keySet = params.keySet();
		for (String key : keySet) {
			nvps.add(new BasicNameValuePair(key, params.get(key)));
		}
		// 结果
		CloseableHttpResponse response = null;
		String content = "";
		try {
			// 提交的参数
			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(nvps, "UTF-8");
			// 将参数给post方法
			httpPost.setEntity(uefEntity);
//			httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:39.0) Gecko/20100101 Firefox/39.0");  
			// 执行post方法
			response = httpclient.execute(httpPost);
		
			if (response.getStatusLine().getStatusCode() == 200) {
				content = EntityUtils.toString(response.getEntity(), "utf-8");
				// System.out.println(content);
				//重定向
			}else if(response.getStatusLine().getStatusCode() == 302) {
				Header header = response.getFirstHeader("Location");
				// 重定向地址
				String location = header.getValue();
				System.out.println(location);
				content = get(location);
			} 
			else {
				throw new Exception("calis请求异常");
			}
		} catch (ClientProtocolException e) {
			throw new Exception("calis连接异常");
		} catch (IOException e) {
			throw new Exception("calis连接异常！");
		}
		return content;
	}

	public String getData(String url) throws ParseException, IOException {
		HttpClient httpClient = HttpClients.createDefault();

		HttpPost httpPost = new HttpPost(url);

		CloseableHttpResponse response = (CloseableHttpResponse) httpClient.execute(httpPost);

		int statusCode = response.getStatusLine().getStatusCode();
		System.out.println("statusCode==" + statusCode); // 返回码

		Header header = response.getFirstHeader("Location");

		// 重定向地址
		String location = header.getValue();
		System.out.println(location);
		// 然后再对新的location发起请求即可
		return get(location);

	}

	public static void main(String[] args) throws Exception {

	}
}
