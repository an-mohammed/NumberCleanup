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
public class NMSCleanupDao {

	@Autowired
	private JdbcTemplate nmsJdbcTemplate;
	
	@Value("#{nmsCleanupQueriesPO}")
	private Map<Integer, String> nmsCleanupQueriesPO;
	
	@Value("#{nmsCleanupQueriesPP}")
	private Map<Integer, String> nmsCleanupQueriesPP;
	
	@Value("#{nmsPriceUpdate}")
	private Map<Integer, String> nmsPriceUpdate;
	
	@Value("#{nmsPoolUpdate}")
	private Map<Integer, String> nmsPoolUpdate;
	
	@Value("#{nmsDealerUpdate}")
	private Map<Integer, String> nmsDealerUpdate;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NMSCleanupDao.class);
	
	public void executeCleanupQueries(SubscriberProfileDetails profile) throws ApplicationException {
        try {
        	if(null != profile.getSubscriberType() && !profile.getSubscriberType().isEmpty()) {
			    if(profile.getSubscriberType().equals("PO")) {
			    	
			    	for(Entry<Integer, String> e : nmsCleanupQueriesPO.entrySet()) {
				    	String query = e.getValue();
				    	LOGGER.info("Executing cleanup query :[" + query + "] with argument MSISDN : " + profile.getMsisdnWithoutPrefix());
			    		int updateResult = nmsJdbcTemplate.update(query, profile.getMsisdnWithoutPrefix());
			    		
			    		LOGGER.info("Update result :{}", updateResult );
			    	}
			    	
			    } else if(profile.getSubscriberType().equals("PP")) {
			    	
			    	for(Entry<Integer, String> e : nmsCleanupQueriesPP.entrySet()) {
				    	String query = e.getValue();
				    	
				    	if(e.getKey() == 3 || e.getKey() == 4) {
				    		LOGGER.info("Executing cleanup query :[" + query + "] with argument : " + profile.getSimNo());
				    		int updateResult = nmsJdbcTemplate.update(query, profile.getSimNo());
				    		
				    		LOGGER.info("Update result :{}", updateResult );
				    		
				    	} else if(e.getKey() == 5) {
				    		LOGGER.info("Executing cleanup query :[" + query + "] with argument MSISDN :" + profile.getMsisdnWithoutPrefix() + "SIM No :" + profile.getSimNo());
				    		int updateResult = nmsJdbcTemplate.update(query, profile.getMsisdnWithoutPrefix(), profile.getSimNo());
				    		
				    		LOGGER.info("Update result :{}", updateResult );
				    		
				    	} else if(e.getKey() == 6) {
				    		LOGGER.info("Executing cleanup query :[" + query + "] with argument MSISDN :" + profile.getMsisdnWithoutPrefix());
				    		int updateResult = nmsJdbcTemplate.update(query, profile.getMsisdnWithoutPrefix());
				    		
				    		LOGGER.info("Update result :{}", updateResult );
				    		
				    	} else {
				    		LOGGER.info("Executing cleanup query :[" + query + "] with argument MSISDN :" + profile.getMsisdnWithoutPrefix());
				    		int updateResult = nmsJdbcTemplate.update(query, profile.getMsisdnWithoutPrefix());
				    		
				    		LOGGER.info("Update result :{}", updateResult );
				    		
				    	}
			    	}
			    }
			    
			    if(null != profile.getPrice()) {
			    	String query = this.nmsPriceUpdate.get(1);
			    	LOGGER.info("Executing cleanup query :[" + query + "] with argument Price -" + profile.getPrice() + "MSISDN :" + profile.getMsisdnWithoutPrefix());
		    		int updateResult = nmsJdbcTemplate.update(query, profile.getPrice(), profile.getMsisdnWithoutPrefix());
		    		
		    		LOGGER.info("Update result :{}", updateResult );
			    }
			    
			    if(null != profile.getSelecedNmsPool() && !profile.getSelecedNmsPool().isEmpty()) {
			    	String query = this.nmsPoolUpdate.get(1);
			    	LOGGER.info("Executing cleanup query :[" + query + "] with argument Pool -" + profile.getSelecedNmsPool() + "MSISDN :" + profile.getMsisdnWithoutPrefix());
		    		int updateResult = nmsJdbcTemplate.update(query, profile.getSelecedNmsPool(), profile.getMsisdnWithoutPrefix());
		    		
		    		LOGGER.info("Update result :{}", updateResult );
			    }
			    
			    if(null != profile.getSelectedDealer() && !profile.getSelectedDealer().isEmpty()) {
			    	String query = this.nmsDealerUpdate.get(1);
			    	LOGGER.info("Executing cleanup query :[" + query + "] with argument Dealer -" + profile.getSelectedDealer() + "MSISDN :" + profile.getMsisdnWithoutPrefix());
		    		int updateResult = nmsJdbcTemplate.update(query, profile.getSelectedDealer(), profile.getMsisdnWithoutPrefix());
		    		
		    		LOGGER.info("Update result :{}", updateResult );
			    }
        	} else {
        		throw new ApplicationException("Unable to get subscriber type", true);
        	}
		    
        } catch(DataAccessException dae) {
        	throw new ApplicationException(dae.getMessage(), dae, true);
        	
        } catch(ApplicationException e) {
        	throw e;
        	
        } catch(Exception e) {
        	throw new ApplicationException(e.getMessage(), e);
        }
	}
}
