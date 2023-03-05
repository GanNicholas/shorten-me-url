package com.url.urlShorten.service;

import com.url.urlShorten.entity.UrlEntity;
import com.url.urlShorten.exceptions.UrlEntityCreationException;
import com.url.urlShorten.exceptions.UrlEntityRetrievalException;

public interface UrlShortenService {
	public String createUrl(UrlEntity urlEntity) throws UrlEntityCreationException, UrlEntityRetrievalException;

	public String getFullUrlFromKeyword(UrlEntity urlEntity)
			throws UrlEntityCreationException, UrlEntityRetrievalException;
}
