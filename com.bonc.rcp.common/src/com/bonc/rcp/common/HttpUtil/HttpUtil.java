package com.bonc.rcp.common.HttpUtil;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;
import java.util.Map;


import org.apache.http.HttpEntity;


import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;

import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import org.apache.http.util.EntityUtils;



public class HttpUtil { 
	
	public static String postRequest(String url, String actionName, Map<String, Object> params)
			throws Exception{
		HttpClient client = HttpClientBuilder.create().build();
		
		 String body = "";
		try{
	        //拼接url
			
			url = url+"/"+actionName ;
	        HttpPost request = new HttpPost(url);
	        RequestConfig requestConfig = RequestConfig.custom()
	                .setConnectTimeout(5000).setConnectionRequestTimeout(500000000)
	                .setSocketTimeout(500000000).build();
	        request.setConfig(requestConfig);
	        
	        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
	        for (Iterator<String> iter = params.keySet().iterator(); iter.hasNext();) {
				String key = (String) iter.next();
				String value = String.valueOf(params.get(key));
				nvps.add(new BasicNameValuePair(key, value));
			}  
	        
			request.setEntity(new UrlEncodedFormEntity(nvps, StandardCharsets.UTF_8));
			//发送请求
			HttpResponse response = client.execute(request);
			
			HttpEntity entity = response.getEntity();  

			body = EntityUtils.toString(entity);  
			 
		}catch(Exception e){
			//连接失败
			
			body="Proxy Server Connection timed out";
			
			throw e;
		}
        return body;  
	}
	//传递自定义类型
	public static String post(String url, String actionName, Map<String,Object> params){
		url = url+"/"+actionName ;
		BufferedReader in = null;
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpPost request = new HttpPost(url);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			for (Iterator<String> iter = params.keySet().iterator(); iter.hasNext();) {
				String key = (String) iter.next();
				String value = String.valueOf(params.get(key));
				nvps.add(new BasicNameValuePair(key, value));
			}
			request.setEntity(new UrlEncodedFormEntity(nvps, StandardCharsets.UTF_8));
			HttpResponse response = client.execute(request);
			int code = response.getStatusLine().getStatusCode();
			if(code == HttpStatus.SC_OK) {
				in = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8));
				StringBuffer sb = new StringBuffer("");
				String line = "";
				String NL = System.getProperty("line.separator");
				while ((line = in.readLine()) != null) {
					sb.append(line + NL);
				}
				in.close();
				return sb.toString();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	}
	
	
