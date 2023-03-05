package com.url.urlShorten.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.url.urlShorten.entity.UrlEntity;
import com.url.urlShorten.exceptions.UrlEntityCreationException;
import com.url.urlShorten.exceptions.UrlEntityRetrievalException;
import com.url.urlShorten.service.UrlShortenService;

@Controller
@RequestMapping(UrlShortenController.BASE_URL)
public class UrlShortenController {

	public static final String BASE_URL = "/api/url";

	private static Logger logger = LoggerFactory.getLogger(UrlShortenController.class);

	@Autowired
	private UrlShortenService urlShortenService;

	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> createBlock(HttpServletRequest request, HttpServletResponse response,
			@RequestBody UrlEntity urlEntity) throws UrlEntityCreationException, UrlEntityRetrievalException {

		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
			ip = request.getHeader("Proxy-Client-IP");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
			ip = request.getHeader("WL-Proxy-Client-IP");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
			ip = request.getHeader("HTTP_CLIENT_IP");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
			ip = request.getRemoteAddr();
		ip = ip != null ? ip.trim() : "";

		urlEntity.setIpAddress(ip);
		String result = urlShortenService.createUrl(urlEntity);
		logger.info("IpAddress " + urlEntity.getIpAddress() + " has created a new url shortened entry: " + result);
		return new ResponseEntity<String>(result, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/get", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> getFullUrl(HttpServletRequest request, HttpServletResponse response,
			@RequestBody UrlEntity urlEntity) throws UrlEntityCreationException, UrlEntityRetrievalException {

		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
			ip = request.getHeader("Proxy-Client-IP");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
			ip = request.getHeader("WL-Proxy-Client-IP");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
			ip = request.getHeader("HTTP_CLIENT_IP");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
			ip = request.getRemoteAddr();
		ip = ip != null ? ip.trim() : "";

		urlEntity.setIpAddress(ip);

		String result = urlShortenService.getFullUrlFromKeyword(urlEntity);
		logger.info("Ip Address " + urlEntity.getIpAddress() + " has retrieved keyword for url: " + result);
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}

}
