package com.url.urlShorten.entity;

import java.util.Objects;

public class UrlEntity {
	private long urlId;
	private String ipAddress;
	private String fullUrl;
	private String shortenUrl;
	private String keyword;

	public UrlEntity() {
		this.ipAddress = "";
		this.fullUrl = "";
		this.shortenUrl = "";
		this.keyword = "";
	}

	public UrlEntity(String ipAddress, String fullUrl, String shortenUrl, String keyword) {
		this.ipAddress = ipAddress;
		this.fullUrl = fullUrl;
		this.shortenUrl = shortenUrl;
		this.keyword = keyword;
	}

	public long getUrlId() {
		return urlId;
	}

	public void setUrlId(long urlId) {
		this.urlId = urlId;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getFullUrl() {
		return fullUrl;
	}

	public void setFullUrl(String fullUrl) {
		this.fullUrl = fullUrl;
	}

	public String getShortenUrl() {
		return shortenUrl;
	}

	public void setShortenUrl(String shortenUrl) {
		this.shortenUrl = shortenUrl;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Override
	public int hashCode() {
		return Objects.hash(fullUrl, ipAddress, keyword, shortenUrl, urlId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UrlEntity other = (UrlEntity) obj;
		return Objects.equals(fullUrl, other.fullUrl) && Objects.equals(ipAddress, other.ipAddress)
				&& Objects.equals(keyword, other.keyword) && Objects.equals(shortenUrl, other.shortenUrl)
				&& urlId == other.urlId;
	}

	@Override
	public String toString() {
		return "UrlEntity [urlId=" + urlId + ", ipAddress=" + ipAddress + ", fullUrl=" + fullUrl + ", shortenUrl="
				+ shortenUrl + ", keyword=" + keyword + "]";
	}

}
