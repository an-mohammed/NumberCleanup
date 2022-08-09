package com.ooredoo.nc.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ooredoo.nc.exception.ApplicationException;

@Component
public class ERPCleanupDao {

	@Autowired
	private JdbcTemplate erpJdbcTemplate;
	
	@Autowired
	private ReloadableResourceBundleMessageSource externalConfig;
	
	private static final String QUERY_EXTRACT_ALL_LOCATIONS = "SELECT LOCATOR_NAME,LOCATOR_ID,SIEBEL_LOCATOR_ID FROM APPS.NMTC_INV_LOCATORS_V";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ERPCleanupDao.class);
	
	public void executeCleanupQueries(String simNo, String locationTo) throws ApplicationException {
        Connection connection = null;
        CallableStatement callableStatement = null;
        String locationFrom = null;
		try {
		    String itemCode = externalConfig.getMessage("service.cleanup.erp.itemcode", null, Locale.getDefault());
        	connection = erpJdbcTemplate.getDataSource().getConnection();
        	callableStatement = connection.prepareCall("{call APPS.NMTC_SERIAL_CLEANUP_PKG.NMTC_SERIAL_CLEANUP_PRC(?, ?, ?, ?, ?, ?)}");
        	callableStatement.setString(1, itemCode);
        	callableStatement.setString(2, simNo);
        	callableStatement.setString(3, locationFrom);
        	callableStatement.setString(4, locationTo);
        	callableStatement.registerOutParameter(5, Types.VARCHAR);
        	callableStatement.registerOutParameter(6, Types.VARCHAR);
        	callableStatement.executeUpdate();
        	
        	String retStatus = callableStatement.getString(5);
        	String retMessage = callableStatement.getString(6);
        	LOGGER.info("ERP cleanup return status : {} message : {}", retStatus, retMessage);
        	
			if((null == retStatus || retStatus.isEmpty()) || (null == retMessage|| retMessage.isEmpty())) {
				throw new ApplicationException("No response received", true);
			}
        	
        	if(null != retStatus && !retStatus.isEmpty()) {
        		
        		if(!retStatus.equals("S")) {
        			throw new ApplicationException(retMessage, true);
        		} else {
        			if(!retMessage.startsWith("Sucess") && !retMessage.startsWith("Serial MOVED TO")) {
        				throw new ApplicationException(retMessage, true);
        			}
        		}
        	}
        } catch(DataAccessException dae) {
        	throw new ApplicationException(dae.getMessage(), dae, true);
        	
        }catch(ApplicationException e) {
        	throw new ApplicationException(e.getErrorMessage(), e, true);
        	
        }catch(Exception e) {
        	throw new ApplicationException(e.getMessage(), e, true);
        	
        } finally {
        	if(null != callableStatement) {
        		try {
					callableStatement.close();
				} catch (SQLException e) {
					LOGGER.error("Error occurred while closing callable statement");
				}
        	}
        	
        	if(null != connection) {
        		try {
        			connection.close();
				} catch (SQLException e) {
					LOGGER.error("Error occurred while closing callable statement");
				}
        	}
        }
	}
	
	public Map<String, String> getErpLocations() throws ApplicationException {
		 Map<String, String> locations = null;
		try {
			List<Map<String, Object>> rs = erpJdbcTemplate.queryForList(QUERY_EXTRACT_ALL_LOCATIONS);
			
			if(null != rs && !rs.isEmpty()) {
				locations = new HashMap<String, String>();
				
				for(Map<String, Object> e : rs) {
					locations.put((String) e.get("SIEBEL_LOCATOR_ID"), (String) e.get("LOCATOR_NAME"));
				}
			}
			
		} catch(Exception e) {
			throw new ApplicationException("Unable to extract Erp Locations", e);
		}
		
		
		return locations;
	}
}
