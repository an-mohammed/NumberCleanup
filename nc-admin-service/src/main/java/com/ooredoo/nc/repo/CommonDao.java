package com.ooredoo.nc.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CommonDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	private static final String BATCH_ID_QRY = "SELECT BULK_ACTIVATION_BATCH_ID_SEQ.NEXTVAL FROM DUAL";
	
	public synchronized Long getBatchIdForBulkActivation() {
		return jdbcTemplate.queryForObject(BATCH_ID_QRY, Long.class);
	}
}
