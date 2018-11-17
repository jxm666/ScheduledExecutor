package com.haima.regeneration.net;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

public class URLConnection {
	private Logger log = Logger.getLogger(URLConnection.class);

	public String postUrlByJson(String getUrl, String bodyJson) {
		String result ="";
		try {
			URL url = new URL(getUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setConnectTimeout(3500);
			// 设置参数类型是json格式
			connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
			connection.connect();

			String body = bodyJson;
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
			writer.write(body);
			writer.close();

			int responseCode = connection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				InputStream inputStream = connection.getInputStream();
				// 将流转换为字符串。
				result = IOUtils.toString(inputStream, "utf-8");
				log.info(getUrl+" =====result=====" + result);
			}

		} catch (Exception e) {
			result = "error";
			e.printStackTrace();
		}
		return result;
	}
}
