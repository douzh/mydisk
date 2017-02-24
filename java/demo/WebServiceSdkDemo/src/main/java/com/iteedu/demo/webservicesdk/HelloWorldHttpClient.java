package com.iteedu.demo.webservicesdk;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HelloWorldHttpClient {
	public static void main(String[] args) throws ClientProtocolException,
			IOException {
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectionRequestTimeout(30).setConnectTimeout(30)
				.setSocketTimeout(30).build();
		CloseableHttpClient httpClient = HttpClients.custom()
				.setDefaultRequestConfig(requestConfig).build();
		HttpPost httpPost = new HttpPost("http://localhost:8080/helloWorld");
		StringEntity entity = new StringEntity(getRequestXML(), "UTF-8");
		httpPost.setEntity(entity);
		httpPost.setHeader("Content-Type", "text/xml; charset=" + "UTF-8");
		httpPost.setHeader("SOAPAction", "");
		CloseableHttpResponse httpResponse = httpClient.execute(httpPost);

		String result = null;
		int iCode = httpResponse.getStatusLine().getStatusCode();
		if (HttpStatus.SC_OK == iCode) {
			HttpEntity httpEntity = httpResponse.getEntity();
			result = EntityUtils.toString(httpEntity, "UTF-8");
		} else { // 如果返回非200，则手动关闭连接
			httpPost.abort();
			System.out.println("get status code {} from server:"
					+ httpResponse.getStatusLine().getStatusCode());
		}
		System.out.println(result);
	}

	public static String getRequestXML() {
		StringBuilder sb = new StringBuilder("<soapenv:Envelope");
		sb.append(" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"");
		sb.append(" xmlns:web=\"http://webservicesdk.demo.iteedu.com/\">");
		sb.append("   <soapenv:Header/>");
		sb.append("   <soapenv:Body>");
		sb.append("      <web:sayHiToUser>");
		sb.append("         <arg0>");
		sb.append("         <name>assd</name></arg0>");
		sb.append("      </web:sayHiToUser>");
		sb.append("   </soapenv:Body>");
		sb.append("</soapenv:Envelope>");
		return sb.toString();
	}
}