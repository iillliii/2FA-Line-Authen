package com.iilllii.healthcheck.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.iilllii.healthcheck.dto.HealthCheckResultDTO;
import com.iilllii.healthcheck.util.FileUtil;
import com.iilllii.healthcheck.util.HttpUtil;

@Service
public class HealthCheckService {

	Logger log = LoggerFactory.getLogger(HealthCheckService.class);

	@Value("${http.timeout}")
	private Integer timeout;
	
	@Value("${healthcheck.report.endpoint}")
	private String reportEndpoint;

	@Autowired
	private SendReportService sendReportService;

	public void doProcess(MultipartFile processFile, HealthCheckResultDTO result,OAuth2AuthorizedClient authorizedClient) throws UnsupportedEncodingException, IOException {
		doHealthCheck(processFile, result);
		sendReportService.sendReport(result, authorizedClient);
	}

	private HealthCheckResultDTO doHealthCheck(MultipartFile processFile, HealthCheckResultDTO result)throws IOException {
		Set<String> siteList = new HashSet<>();
		siteList = FileUtil.readSingleColumnFromCsv(processFile);
		long startTime = System.currentTimeMillis();
		siteList.forEach(endpoint -> {
			try {
				HttpUtil.checkAvaialble(endpoint,timeout);
				result.setSuccess(result.getSuccess() + 1);
			} catch (IOException | RuntimeException e) {
				result.setFailure(result.getFailure() + 1);
			}
		});
		long endTime = System.currentTimeMillis();
		result.setTotalWebsites(siteList.size());
		result.setTotalTime(endTime - startTime);

		return result;
	}

}
