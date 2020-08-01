package com.iilllii.healthcheck.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.iilllii.healthcheck.dto.HealthCheckResultDTO;
import com.iilllii.healthcheck.service.HealthCheckService;

@Controller
public class HomeController {
	
	Logger log = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	HealthCheckService service;
	
	@GetMapping({"/","/index"})
	public String index(Model model,@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient,@AuthenticationPrincipal OAuth2User oauth2User) {
		
		return "index";
	}

	@PostMapping("/processCheck")
	public String process(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient,@AuthenticationPrincipal OAuth2User oauth2User,Model model,@RequestParam MultipartFile file) {
		HealthCheckResultDTO result = new HealthCheckResultDTO();
		try {
			log.info("Perform website checking...");
			service.doProcess(file,result,authorizedClient);
			result.setStatus("Done!");
		} catch (IOException e) {
			log.error("Error : "+e.getMessage());
			result.setStatus("Error : "+e.getMessage());
		} finally {
			log.info("Checked webistes: {}", result.getTotalWebsites());
			log.info("Successful webistes: {}", result.getSuccess());
			log.info("Failure webistes: {}", result.getFailure());
			log.info("Total times to finished checking website: {}", result.getTotalTime() + " ms");
			model.addAttribute("result",result);
		}
		return "result";
	}
	
}