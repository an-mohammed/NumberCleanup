package com.ooredoo.nc.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.utility.ApplicationConstants;

@Component
public class BSCSCleanupDao implements ApplicationConstants {

	@Autowired
	private JdbcTemplate bscsJdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate bscsNJdbcTemplate;
	
	@Value("#{bscsCleanupQueries}")
	private Map<Integer, String> bscsCleanupQueries;
	
	@Value("${qry.extract.profile}")
	private String qryExtractBscsProfile;
	
	@Value("${qry.extract.profilevalidation}")
	private String qryExtractBscsProfileForValidation;
	
	@Value("${qry.extract.subscription}")
	private String qryExtractBscsSubscription;
	
	@Value("${qry.extract.occ}")
	private String qryExtractBscsOcc;
	
	@Value("${qry.extract.pending-request}")
	private String qryExtractBscsPendingRequest;
	
	public final String QRY_EXTRACT_IMSI_FROM_SIMNUMBER = "SELECT PORT_NUM FROM PORT P, STORAGE_MEDIUM SM WHERE P.SM_ID = SM.SM_ID AND SM.SM_SERIALNUM = ?";
	public final String QRY_EXTRACT_COID_FROM_MSISDN = "SELECT  A.CO_ID FROM CONTRACT_ALL A, CONTR_SERVICES_CAP CT, DIRECTORY_NUMBER D WHERE   A.CO_ID = CT.CO_ID AND D.DN_ID = CT.DN_ID AND DN_NUM = ?";
	private static final Logger LOGGER = LoggerFactory.getLogger(BSCSCleanupDao.class);
	
	/**
	 * 
	 * @param msisdn
	 * @param imsi
	 * @param simNo
	 * @throws ApplicationException
	 */
	public void executeCleanupQueries(String msisdn, String imsi, String simNo) throws ApplicationException {
        try {
		    
        	List<Long> coIds = getCoidsFromMsisdn(msisdn);
        	
		    for(Entry<Integer, String> e : bscsCleanupQueries.entrySet()) {
		    	String query = e.getValue();
		    	
		    	if(e.getKey() == 1) {
		    		LOGGER.trace("Executing cleanup query :" + query + " with argument : " + msisdn);
		    		int updateResult = bscsJdbcTemplate.update(query, msisdn);
		    		
		    		LOGGER.trace("Update result :{}", updateResult );
		    		
		    	} else if(e.getKey() == 2 || e.getKey() == 3) {
		    		LOGGER.trace("Executing cleanup query :" + query + " with argument : " + simNo);
		    		int updateResult = bscsJdbcTemplate.update(query, simNo);
		    		
		    		LOGGER.trace("Update result :{}", updateResult );
		    		
		    	} else {
		    		if(null == coIds || coIds.isEmpty()) {
		    			LOGGER.debug("No contract Id found for MSISDN {}, Skipping all query execution", msisdn );
		    		} else {
		    			LOGGER.trace("Executing cleanup query :" + query + " with argument : " + getCoIdString(coIds));
			    		SqlParameterSource parameters = new MapSqlParameterSource("CO_IDS", coIds);
			    		int updateResult = bscsNJdbcTemplate.update(query, parameters);
			    		
			    		LOGGER.trace("Update result :{}", updateResult );
		    		}
		    	}
		    }
        } catch(DataAccessException dae) {
        	throw new ApplicationException(dae.getMessage(), dae, true);
        	
        }catch(Exception e) {
        	throw new ApplicationException(e.getMessage(), e);
        }
	}
	
	/**
	 * 
	 * @param simNo
	 * @return
	 * @throws ApplicationException
	 */
	public String getImsiFromSimNo(String simNo) throws ApplicationException {
		String imsi = null;
		
		try {
			Map<String, Object> rs = bscsJdbcTemplate.queryForMap(QRY_EXTRACT_IMSI_FROM_SIMNUMBER, simNo);
			imsi = (String) rs.get("PORT_NUM");
			
		} catch(Exception e) {
			throw new ApplicationException("Unable to extract IMSI from BSCS", e);
		}
		
		return imsi;
	}
	
	public List<Long> getCoidsFromMsisdn(String msisdn) throws ApplicationException {
		List<Long> coIds = null;
		
		try {
			List<Map<String, Object>> rs = bscsJdbcTemplate.queryForList(QRY_EXTRACT_COID_FROM_MSISDN, msisdn);
			
			if(null != rs && !rs.isEmpty()) {
				coIds = new ArrayList<>();
				
				for(Map<String, Object> coId : rs) {
					coIds.add(((BigDecimal)coId.get("CO_ID")).longValue());
				}
			}
		} catch(Exception e) {
			throw new ApplicationException("Unable to extract CO_ID from BSCS for MSISDN - " + msisdn, e);
		}
		
		return coIds;
	}
	
	/**
	 * 
	 * @param msisdn
	 * @return
	 * @throws ApplicationException
	 */
	public List<Map<String, Object>> getBscsProfile(String msisdn) throws ApplicationException{
		List<Map<String, Object>> rs = null;
		try {
			rs = bscsJdbcTemplate.queryForList(qryExtractBscsProfile, msisdn);
			
		} catch(Exception e) {
			throw new ApplicationException("Unable to extract BSCS profile details", e);
		}
		
		return rs;
	}
	
	public List<Map<String, Object>> getBscsProfileForValidation(String msisdn) throws ApplicationException{
		List<Map<String, Object>> rs = null;
		try {
			rs = bscsJdbcTemplate.queryForList(qryExtractBscsProfileForValidation, msisdn);
			
		} catch(Exception e) {
			throw new ApplicationException("Unable to extract BSCS profile details for validation", e);
		}
		
		return rs;
	}
	
	/**
	 * 
	 * @param custId
	 * @param coId
	 * @return
	 * @throws ApplicationException
	 */
	public List<Map<String, Object>> getBscsSubscription(Long custId, Long coId) throws ApplicationException{
		List<Map<String, Object>> rs = null;
		try {
			rs = bscsJdbcTemplate.queryForList(qryExtractBscsSubscription, coId);
			
		} catch(Exception e) {
			throw new ApplicationException("Unable to extract BSCS subscription details", e);
		}
		
		return rs;
	}
	
	/**
	 * 
	 * @param custId
	 * @param coId
	 * @return
	 * @throws ApplicationException
	 */
	public List<Map<String, Object>> getBscsOcc(Long custId, Long coId) throws ApplicationException{
		List<Map<String, Object>> rs = null;
		try {
			rs = bscsJdbcTemplate.queryForList(qryExtractBscsOcc, custId, coId);
			
		} catch(Exception e) {
			throw new ApplicationException("Unable to extract BSCS OCC details", e);
		}
		
		return rs;
	}
	
	/**
	 * 
	 * @param custId
	 * @param coId
	 * @return
	 * @throws ApplicationException
	 */
	public List<Map<String, Object>> getBscsPendingReq(Long custId, Long coId) throws ApplicationException{
		List<Map<String, Object>> rs = null;
		try {
			rs = bscsJdbcTemplate.queryForList(qryExtractBscsPendingRequest, custId, coId);
			
		} catch(Exception e) {
			throw new ApplicationException("Unable to extract BSCS pending request details", e);
		}
		
		return rs;
	}
	
	private String getCoIdString(List<Long> coIds) {
		StringBuilder sb = new StringBuilder();
		
		if(null != coIds && !coIds.isEmpty()) {
			int i=1;
			for(Long coId : coIds) {
				sb.append(coId);
				
				if(i < coIds.size()) {
					sb.append(COMMA);
				}
			}
		}
		
		return sb.toString();
	}
}
