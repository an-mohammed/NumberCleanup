package com.ooredoo.nc.dao;

import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ooredoo.nc.dto.SubscriberProfileDetails;
import com.ooredoo.nc.exception.ApplicationException;

@Component
public class RASCleanupDao {

	@Autowired
	private JdbcTemplate rasJdbcTemplate;
	
	@Value("#{rasCleanupQueries}")
	private Map<Integer, String> rasCleanupQueries;
	
	@Value("${disconnectionQuery}")
	private String dcQuery;
	private static final Logger LOGGER = LoggerFactory.getLogger(RASCleanupDao.class);
	
	public void executeCleanupQueries(SubscriberProfileDetails profile) throws ApplicationException {
        try {
        	
        	String query = null;
		    for(Entry<Integer, String> e : rasCleanupQueries.entrySet()) {
		    	
		    	if(e.getKey() == 1 || e.getKey() == 2 || e.getKey() == 3 || e.getKey() == 4 || e.getKey() == 5) {
		    		query = e.getValue();
			    	
			    	LOGGER.info("Executing cleanup query :" + query + " with argument : " + profile.getMsisdnWithoutPrefix());
		    		int updateResult = rasJdbcTemplate.update(query, profile.getMsisdnWithoutPrefix());
		    		LOGGER.info("Update result :{}", updateResult );
		    		
		    	} else if(e.getKey() == 6 || e.getKey() == 7 || e.getKey() == 8 || e.getKey() == 9 || e.getKey() == 10) {
		    		query = e.getValue();
			    	
			    	LOGGER.info("Executing cleanup query :" + query + " with argument : " + profile.getSimNo());
		    		int updateResult = rasJdbcTemplate.update(query, profile.getSimNo());
		    		LOGGER.info("Update result :{}", updateResult );
		    	}
		    }
        } catch(DataAccessException dae) {
        	throw new ApplicationException(dae.getMessage(), dae, true);
        	
        }catch(Exception e) {
        	throw new ApplicationException(e.getMessage(), e);
        }
	}
	
	public int pushDisconnectionRequest(String msisdn, Long contractId)  throws ApplicationException {
		int updateResult = 0;
		
		try {
			updateResult = rasJdbcTemplate.update(dcQuery, msisdn, contractId);
		} catch(Exception e) {
			throw new ApplicationException(e.getMessage(), e, true);
		}
		 
		return updateResult;
	}
}
