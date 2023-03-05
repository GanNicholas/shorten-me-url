package com.url.urlShorten.repository;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UrlShortenRepository {

	private static Logger logger = LoggerFactory.getLogger(UrlShortenRepository.class);

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public UrlShortenRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {

		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;

	}

	/**
	 * Creates new entry for a specific user and specific full url
	 * @param map
	 * @return keyword - to append with frontend URL to create shorten URL
	 */
	public String createShortenUrl(Map<String, Object> map) {
		StringBuilder sql = new StringBuilder();
		MapSqlParameterSource bean = new MapSqlParameterSource(map);

		sql.append(
				"INSERT INTO urlTable (ipAddress, fullUrl, shortenUrl, keyword) " + "VALUES(:ipAddress, :fullUrl, :shortenUrl, :keyword)");

		try {

			namedParameterJdbcTemplate.update(sql.toString(), bean);
			StringBuilder sqlGetInsert = new StringBuilder();
			MapSqlParameterSource beanGetInsert = new MapSqlParameterSource(map);
			sqlGetInsert.append("SELECT keyword " + "FROM urlTable " + "WHERE ipAddress=:ipAddress AND fullUrl=:fullUrl");

			String result = namedParameterJdbcTemplate.queryForObject(sqlGetInsert.toString(), beanGetInsert,
					String.class);

			return result;

		} catch (DataAccessException ex) {
			System.out.println("Error spotted (createShortenUrl): " + ex.getMessage());
			logger.error("SQL/Foreign Key error: " + ex);
			return null;

		} catch (Exception ex) {

			logger.error("Error creating shorten Url: " + ex);
			return null;

		}

	}
	
	/**
	 * Repo method to get back keyword from full url for specific IP address
	 * This method is used when the url user wishes to save already exists under the database and is tagged to user.
	 * Keyword is given, since domain is provided by frontend
	 * @param map
	 * @return
	 */
	public String getShortUrlByFullUrlAndIp(Map<String, Object> map) {
		StringBuilder sql = new StringBuilder();
		MapSqlParameterSource bean = new MapSqlParameterSource(map);
		sql.append("SELECT keyword FROM urlTable WHERE fullUrl=:fullUrl AND ipAddress=:ipAddress;");

		try {

			String result = namedParameterJdbcTemplate.queryForObject(sql.toString(), bean, String.class);

			return result;

		} catch (DataAccessException ex) {
			System.out.println("Error spotted (getShortUrlByFullUrlAndIp): " + ex.getMessage());
			logger.error("SQL/Foreign Key error: " + ex);
			return null;

		} catch (Exception ex) {

			logger.error("Error retrieving shortened url from full url: " + ex);
			return null;

		}

	}

	/**
	 * Repo method to get back full url from shorten url for specific IP address.
	 * @param map
	 * @return
	 */
	public String getFullUrlByKeywordAndIp(Map<String, Object> map) {
		StringBuilder sql = new StringBuilder();
		MapSqlParameterSource bean = new MapSqlParameterSource(map);
		sql.append("SELECT fullUrl FROM urlTable WHERE keyword=:keyword;");

		try {
			String result = namedParameterJdbcTemplate.queryForObject(sql.toString(), bean, String.class);
			return result;

		} catch (DataAccessException ex) {
			System.out.println("Error spotted (getFullUrlByShortUrlAndIp): " + ex.getMessage());
			logger.error("SQL/Foreign Key error: " + ex);
			return null;

		} catch (Exception ex) {

			logger.error("Error retrieving full url from shortened: " + ex);
			return null;

		}

	}

	/**
	 * Checks if full url has been mapped before by current user
	 * @param map
	 * @return
	 */
	public Boolean checkUrlExistForIp(Map<String, Object> map) {
		StringBuilder sql = new StringBuilder();
		MapSqlParameterSource bean = new MapSqlParameterSource(map);
		sql.append("SELECT shortenUrl FROM urlTable WHERE fullUrl=:fullUrl AND ipAddress=:ipAddress;");

		try {

			String result = namedParameterJdbcTemplate.queryForObject(sql.toString(), bean, String.class);
			if (result != null && !result.isEmpty()) {
				return true;
			} else {
				return false;
			}

		} catch (DataAccessException ex) {
			System.out.println("Error spotted (checkUrlExistForIp): " + ex.getMessage());
			logger.error("SQL/Foreign Key error: " + ex);
			return false;

		} catch (Exception ex) {

			logger.error("Error retrieving full url from shortened: " + ex);
			return null;

		}
	}
}
