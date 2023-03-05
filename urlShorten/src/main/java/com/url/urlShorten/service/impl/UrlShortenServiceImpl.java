package com.url.urlShorten.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.url.urlShorten.entity.UrlEntity;
import com.url.urlShorten.exceptions.UrlEntityCreationException;
import com.url.urlShorten.exceptions.UrlEntityRetrievalException;
import com.url.urlShorten.repository.UrlShortenRepository;
import com.url.urlShorten.service.UrlShortenService;

@Service
public class UrlShortenServiceImpl implements UrlShortenService {
	private static Logger logger = LoggerFactory.getLogger(UrlShortenServiceImpl.class);

	@Autowired
	private UrlShortenRepository urlShortenRepository;

	@Override
	public String createUrl(UrlEntity urlEntity) throws UrlEntityCreationException, UrlEntityRetrievalException {
		shortenUrl(urlEntity);

//		System.out.println("Check entity (createUrl): " + urlEntity);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ipAddress", urlEntity.getIpAddress());
		map.put("fullUrl", urlEntity.getFullUrl());
		map.put("shortenUrl", urlEntity.getShortenUrl());
		map.put("keyword", urlEntity.getKeyword());

		Boolean urlExist = urlShortenRepository.checkUrlExistForIp(map);

		if (urlExist == null) {
			throw new UrlEntityRetrievalException("Error processing the creation of url. Unable to validate entry.");
		} else if (urlExist) {
			String result = urlShortenRepository.getShortUrlByFullUrlAndIp(map);
			if (result == null) {
				logger.error("Error retrieving existing Url Entity from Database.");
				throw new UrlEntityCreationException("Error retrieving existing Url Entity from Database.");
			} else {
				logger.info("Ip address: " + urlEntity.getIpAddress() + "  retrieved shortened url: " + result);
				return result;
			}
		} else {
			String result = urlShortenRepository.createShortenUrl(map);
			if (result == null) {
				logger.error("Error creating Url Entity in Database.");
				throw new UrlEntityCreationException("Error creating Url Entity in Database.");
			} else {
				logger.info("Ip address: " + urlEntity.getIpAddress() + "  stored a new shortened url: "
						+ urlEntity.getShortenUrl());
				return result;
			}
		}

	}

	@Override
	public String getFullUrlFromKeyword(UrlEntity urlEntity)
			throws UrlEntityCreationException, UrlEntityRetrievalException {

//		System.out.println("Check entity (getFullUrlFromKeyword): " + urlEntity);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ipAddress", urlEntity.getIpAddress());
		map.put("fullUrl", urlEntity.getFullUrl());
		map.put("shortenUrl", urlEntity.getShortenUrl());
		map.put("keyword", urlEntity.getKeyword());

		String result = urlShortenRepository.getFullUrlByKeywordAndIp(map);

		if (result == null) {
			logger.error("Error retrieving full url from Database.");
			throw new UrlEntityCreationException("Error retrieving full url from Database.");
		} else {
			logger.info("Ip address: " + urlEntity.getIpAddress() + "  retrieved full url: " + result);
			return result;
		}

	}

	private void shortenUrl(UrlEntity urlEntity) {
		String frontHeader = urlEntity.getShortenUrl();
		String shortenedUrl = UUID.randomUUID().toString().replace("-", "").substring(0,8);
//		String shortenedUrl = RandomStringUtils.random(12, "0123456789abcdef");

		StringBuilder sb = new StringBuilder();
		sb.append(frontHeader);
		sb.append(shortenedUrl);
		urlEntity.setShortenUrl(sb.toString());
		urlEntity.setKeyword(shortenedUrl);
	}
}
