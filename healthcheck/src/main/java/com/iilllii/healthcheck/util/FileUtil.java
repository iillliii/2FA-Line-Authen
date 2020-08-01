package com.iilllii.healthcheck.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileUtil {

	public static Set<String> readSingleColumnFromCsv(MultipartFile file) throws IOException{
		
		Set<String> siteList = new HashSet<>();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
			String line;
			while ((line = br.readLine()) != null) {
				if(!StringUtils.isEmpty(line)) {
					siteList.add(line.trim());
				}
			}
		}
		return siteList;
		
	}
}
