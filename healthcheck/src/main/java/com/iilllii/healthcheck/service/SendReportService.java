package com.iilllii.healthcheck.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.iilllii.healthcheck.dto.HealthCheckResultDTO;
import com.iilllii.healthcheck.util.HttpUtil;

@Service
public class SendReportService {

	Logger log = LoggerFactory.getLogger(SendReportService.class);

	@Value("${healthcheck.report.endpoint}")
	private String reportEndpoint;

	@Retryable(value = { IOException.class }, maxAttempts = 3, backoff = @Backoff(delay = 5000))
	public void sendReport(HealthCheckResultDTO healthCheckResultDTO, OAuth2AuthorizedClient authorizedClient) throws UnsupportedEncodingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
		String json = mapper.writeValueAsString(healthCheckResultDTO);
		doSendReport(json, authorizedClient.getAccessToken().getTokenValue());
	}

	@Retryable(value = { IOException.class }, maxAttempts = 3, backoff = @Backoff(delay = 5000))
	private void doSendReport(String content, String token) throws IOException {
		HttpUtil.doPost(reportEndpoint, token, content, "application/json");
	}

}
