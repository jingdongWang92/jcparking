package com.jcble.jcparking.api.test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import com.jcble.jcparking.common.CommonConstants;
import com.jcble.jcparking.common.utils.SignUtil;

import net.sf.json.JSONObject;

public class TestHandle {

    private static final String begin = ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>begin. url:";
    private static final String end = "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<end. url:";

    private String host = null;

    // 展示结果
    private boolean showResult = true;

    public TestHandle(String host, boolean showResult) {
    	this.host = host;
    	this.showResult = showResult;
    }

    String getHost() {
        return this.host;
    }

    public void postTest(String _url, Map<String, String> paramMap) throws Exception {
    	paramMap = cleanBlank(paramMap);
        String url = getHost() + _url;
        setSign(paramMap);
        JSONObject jsonObject = JSONObject.fromObject(paramMap);
        String respStr = sendPostRequest(url, jsonObject);
        if (showResult) {
            System.out.println(begin + url);
            System.out.println(respStr);
            System.out.println(end + url);
            System.out.println();
        }
    }
    public void putTest(String _url, Map<String, String> paramMap) throws Exception {
    	paramMap = cleanBlank(paramMap);
    	String url = getHost() + _url;
    	setSign(paramMap);
    	JSONObject jsonObject = JSONObject.fromObject(paramMap);
    	String respStr = sendPutRequest(url, jsonObject);
    	if (showResult) {
    		System.out.println(begin + url);
    		System.out.println(respStr);
    		System.out.println(end + url);
    		System.out.println();
    	}
    }
    
    public void deleteTest(String _url, Map<String, String> paramMap) throws Exception {
    	paramMap = cleanBlank(paramMap);
        String url = getHost() + _url;
        setSign(paramMap);
        String paramStr = "";
        Set<String> iterator = paramMap.keySet();
		if (iterator.size() > 0) {
			for (String key : iterator) {
				String value = paramMap.get(key);
				paramStr += key + "=" + value +"&";
			}
		}
	    if(paramStr.length() > 1) {
	    	paramStr = paramStr.substring(0, paramStr.length()-1);
	    	url = url + "?" + paramStr;
	    }
        String respStr = sendDeleteRequest(url);
        if (showResult) {
            System.out.println(begin + url);
            System.out.println(respStr);
            System.out.println(end + url);
            System.out.println();
        }
    }
    
    public void getTest(String _url,Map<String, String> paramMap) throws Exception {
    	paramMap = cleanBlank(paramMap);
        String url = getHost() + _url;
        setSign(paramMap);
        String paramStr = "";
        Set<String> iterator = paramMap.keySet();
		if (iterator.size() > 0) {
			for (String key : iterator) {
				String value = paramMap.get(key);
				paramStr += key + "=" + value +"&";
			}
		}
	    if(paramStr.length() > 1) {
	    	paramStr = paramStr.substring(0, paramStr.length()-1);
	    	url = url + "?" + paramStr;
	    }
        String respStr = sendGetRequest(url);
        if (showResult) {
            System.out.println(begin + url);
            System.out.println(respStr);
            System.out.println(end + url);
            System.out.println();
        }
    }
    
    public Map<String, String> cleanBlank(Map<String, String> paramMap) throws Exception {
    	Map<String, String> map = new HashMap<String, String>();
    	Set<String> iterator = paramMap.keySet();
		for (String key : iterator) {
			String value = paramMap.get(key);
			if(StringUtils.isNotBlank(value)) {
				map.put(key, value);
			}
		}
		return map;
    }
    
    
	public void setSign(Map<String, String> paramMap) throws Exception {
		String sign = SignUtil.signature(paramMap, CommonConstants.SECRET_CODE, true, false,
				CommonConstants.PARAMTER_SIGN);
		paramMap.put("sign", sign);
	}
	
    public static String sendPostRequest(String reqURL, JSONObject json) {
    	HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(reqURL);
        post.setHeader("Content-Type", "application/json");
        post.addHeader("Authorization", "Basic YWRtaW46");
        String result = "";
		try {
			StringEntity s = new StringEntity(json.toString(), "utf-8");
			s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
			post.setEntity(s);
			// 发送请求
			HttpResponse httpResponse = client.execute(post);
			// 获取响应输入流
			InputStream inStream = httpResponse.getEntity().getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "utf-8"));
			StringBuilder strber = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null)
				strber.append(line + "\n");
			inStream.close();
			result = strber.toString();
			System.out.println("HttpResponse:"+httpResponse.getStatusLine().getStatusCode());
		} catch (Exception e) {
			System.out.println("请求异常");
			throw new RuntimeException(e);
		}
		return result;
	}
    
    public static String sendPutRequest(String reqURL, JSONObject json) {
    	HttpClient client = new DefaultHttpClient();
        HttpPut put = new HttpPut(reqURL);
        put.setHeader("Content-Type", "application/json");
        put.addHeader("Authorization", "Basic YWRtaW46");
        String result = "";
		try {
			StringEntity s = new StringEntity(json.toString(), "utf-8");
			s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
			put.setEntity(s);
			// 发送请求
			HttpResponse httpResponse = client.execute(put);
			// 获取响应输入流
			InputStream inStream = httpResponse.getEntity().getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "utf-8"));
			StringBuilder strber = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null)
				strber.append(line + "\n");
			inStream.close();
			result = strber.toString();
			System.out.println("HttpResponse:"+httpResponse.getStatusLine().getStatusCode());
		} catch (Exception e) {
			System.out.println("请求异常");
			throw new RuntimeException(e);
		}
		return result;
	}
    
    public static String sendGetRequest(String reqURL) {
    	HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(reqURL);
        get.setHeader("Content-Type", "application/json");
        get.addHeader("Authorization", "Basic YWRtaW46");
        String result = "";
		try {
			// 发送请求
			HttpResponse httpResponse = client.execute(get);
			// 获取响应输入流
			InputStream inStream = httpResponse.getEntity().getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "utf-8"));
			StringBuilder strber = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null)
				strber.append(line + "\n");
			inStream.close();
			result = strber.toString();
			System.out.println("HttpResponse:"+httpResponse.getStatusLine().getStatusCode());

		} catch (Exception e) {
			System.out.println("请求异常");
			throw new RuntimeException(e);
		}

		return result;
	}
    
	public static String sendDeleteRequest(String reqURL) throws UnsupportedEncodingException {
		HttpClient client = new DefaultHttpClient();
        HttpDelete delete = new HttpDelete(reqURL);
        delete.setHeader("Content-Type", "application/json");
        delete.addHeader("Authorization", "Basic YWRtaW46");
        String result = "";
		try {
			// 发送请求
			HttpResponse httpResponse = client.execute(delete);
			// 获取响应输入流
			InputStream inStream = httpResponse.getEntity().getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "utf-8"));
			StringBuilder strber = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null)
				strber.append(line + "\n");
			inStream.close();
			result = strber.toString();
			System.out.println("HttpResponse:"+httpResponse.getStatusLine().getStatusCode());

		} catch (Exception e) {
			System.out.println("请求异常");
			throw new RuntimeException(e);
		}

		return result;

	}
}
