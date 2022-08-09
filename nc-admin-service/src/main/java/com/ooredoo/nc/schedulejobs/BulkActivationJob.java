package com.ooredoo.nc.schedulejobs;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ooredoo.nc.dto.B2CSubscriberOnboardingRequest;
import com.ooredoo.nc.dto.B2CSubscriberOnboardingResponse;
import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.model.BulkActivationDetail;
import com.ooredoo.nc.service.BulkActivationService;
import com.ooredoo.nc.service.CreatePrepaidSimService;
import com.ooredoo.nc.utility.ApplicationConstants;

@Component
public class BulkActivationJob implements ApplicationConstants {
	
	@Autowired
	private BulkActivationService bulkActivationService;
	
	@Autowired
	private CreatePrepaidSimService createPrepaidSimService;
	private static final Logger LOGGER = LoggerFactory.getLogger(BulkActivationJob.class);

	@Scheduled(fixedDelayString="${scheduled-job.bulk-activation.fixed-delay.miliseconds}", initialDelayString = "${scheduled-job.bulk-activation.initial-delay.miliseconds}")
	public void handleBulkActivation() {
		LOGGER.info("********************************************************");
		LOGGER.info("Processing started for Bulk-Activation at - {}", SDF_YYYYMMDD_HHMMSS.format(new Date()));
		LOGGER.info("********************************************************");
		try {
			List<BulkActivationDetail> activeOrders = bulkActivationService.getActivationDetailsAscOrder();
			
			if(null != activeOrders && !activeOrders.isEmpty()) {
				for(BulkActivationDetail bad : activeOrders) {
					try {
						bad.setStatus("P");
						bad.setErrorDetails("Processing");
						bad.setStartTime(new Date());
						BulkActivationDetail order = bulkActivationService.updateActivationDetails(bad);
						
						B2CSubscriberOnboardingRequest req = new B2CSubscriberOnboardingRequest();
						
						String msisdnWithoutPrefix = order.getMsisdn().replaceFirst(MSISDN_PREFIX, BLANK);
						req.setMsisdn(msisdnWithoutPrefix);
						req.setCivilId(order.getCivilId());
						req.setPromoId(order.getPromoId());
						req.setUsername(order.getUsername());
						
						if(order.getSubsType().equals("PO")) {
							req.setSubscriberType("POSTPAID");
						} else if(order.getSubsType().equals("PP")) {
							req.setSubscriberType("PREPAID");
						} else {
							req.setSubscriberType("POSTPAID");
						}
						
						
						B2CSubscriberOnboardingResponse resp = createPrepaidSimService.findCleanOnboardB2CProfile(req);
						
						if(null != resp) {
							order.setSpcId(resp.getSiebelRowId());
							order.setSpcType(resp.getSiebelOrderId());
							order.setErrorDetails("Processing complete");
							order.setStatus("C");
							order.setEndTime(new Date());
							bulkActivationService.updateActivationDetails(order);
						} else {
							order.setErrorDetails("No resp received for subscriber creation request");
							order.setStatus("U");
							order.setEndTime(new Date());
							bulkActivationService.updateActivationDetails(order);
						}
					} catch(ApplicationException ae) {
						LOGGER.error("Error occurred while processing activation request.", ae);
						
						bad.setStatus("F");
						bad.setErrorDetails(ae.getErrorMessage());
						bulkActivationService.updateActivationDetails(bad);
					} catch(Exception e) {
						LOGGER.error("Error occurred while processing activation request.", e);
						
						bad.setStatus("F");
						bad.setErrorDetails(e.getMessage());
						bulkActivationService.updateActivationDetails(bad);
					}
				}
			}
		} catch(ApplicationException ae) {
			LOGGER.error("Error occurred while processing activation request.", ae);
		}
		
		LOGGER.info("********************************************************");
		LOGGER.info("Processing ends for Bulk-Activation at - {}", SDF_YYYYMMDD_HHMMSS.format(new Date()));
		LOGGER.info("********************************************************");
	}
	
	
	@Scheduled(fixedDelayString="${scheduled-job.delete-bulk-activation-old-records.fixed-delay.miliseconds}", initialDelayString = "${scheduled-job.delete-bulk-activation-old-records.initial-delay.miliseconds}")
	public void deleteBulkActivationOldRecords() {
		LOGGER.info("********************************************************");
		LOGGER.info("Processing started for Delete-Bulk-Activation-Old-Records at - {}", SDF_YYYYMMDD_HHMMSS.format(new Date()));
		LOGGER.info("********************************************************");
		
		try {
			bulkActivationService.removeOldRecords();
		} catch (ApplicationException e) {
			LOGGER.error("Error occurred while processing deletion request.", e);
		}
		
		LOGGER.info("********************************************************");
		LOGGER.info("Processing ends for Delete-Bulk-Activation-Old-Records at - {}", SDF_YYYYMMDD_HHMMSS.format(new Date()));
		LOGGER.info("********************************************************");
	}
}
