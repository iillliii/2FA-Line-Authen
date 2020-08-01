package com.iilllii.healthcheck.util;

import java.io.IOException;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class HttpUtil {

	static Logger log = LoggerFactory.getLogger(HttpUtil.class);
	
	public static boolean checkAvaialble(String endpoint, int timeout) throws IOException {
		return doGet(endpoint,timeout) != 0;
	}

	public static int doGet(String endpoint,Integer timeout) throws IOException {
		int responseCode = 0;
		int conTimeout = timeout == null ? 30 : timeout;
		RequestConfig config = RequestConfig.custom().setConnectTimeout(conTimeout * 1000).setConnectionRequestTimeout(conTimeout * 1000).setSocketTimeout(conTimeout * 1000).build();
		
		try (CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build()) {
			HttpGet httpGet = new HttpGet(endpoint);
			
			CloseableHttpResponse response = client.execute(httpGet);
			responseCode = response.getStatusLine().getStatusCode();
		}
		return responseCode;
	}

	public static void doPost(String endpoint, String authorization, String jsonData, String contentType) throws IOException {
		try (CloseableHttpClient client = HttpClients.createDefault()) {
			HttpPost httpPost = new HttpPost(endpoint);
			if (!StringUtils.isEmpty(authorization)) {
				httpPost.addHeader("authorization", "Bearer " + authorization);
			}
			if (!StringUtils.isEmpty(contentType)) {
				httpPost.addHeader("content-type", contentType);
			}
			
			StringEntity params = new StringEntity(jsonData);
			httpPost.setEntity(params);
			
			CloseableHttpResponse response = client.execute(httpPost);

			if (!StringUtils.isEmpty(response.getStatusLine().getStatusCode())) {
				log.info("Done!");
			} else {
				log.info("Failed status : {}", response.getStatusLine().getStatusCode());
			}
			log.debug("response : {}", response);
		}
	}

}
