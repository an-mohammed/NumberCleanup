package com.ooredoo.nc.schedulejobs;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.model.AppUser;
import com.ooredoo.nc.model.NumberAsgnmtHistory;
import com.ooredoo.nc.repo.CompositeRepository;
import com.ooredoo.nc.service.NumberPoolService;
import com.ooredoo.nc.utility.ApplicationConstants;

@Component
public class AssignmentHandlerJob implements ApplicationConstants {
	private static final Logger LOGGER = LoggerFactory.getLogger(AssignmentHandlerJob.class);

	@Autowired
	private NumberPoolService numberPoolService;
	
	@Autowired
	private CompositeRepository compositeRepository;
	
	@Scheduled(fixedDelayString="${scheduled-job.finish-assignment.fixed-delay.miliseconds}", initialDelayString = "${scheduled-job.finish-assignment.initial-delay.miliseconds}")
	public void finishExpiredAssignment() {
		LOGGER.info("********************************************************");
		LOGGER.info("Processing started for Finish Expired Assignment at - {}", SDF_YYYYMMDD_HHMMSS.format(new Date()));
		LOGGER.info("********************************************************");
		
		try {
			List<NumberAsgnmtHistory> expiredAssignment = numberPoolService.getAllExpiredAssignment();
			
			if (null != expiredAssignment && !expiredAssignment.isEmpty()) {
				
				AppUser actionUser = compositeRepository.getUserRepo().findByUName("superu").get();
				
				for(NumberAsgnmtHistory e: expiredAssignment) {
					try {
						e.setModBy(actionUser.getId());
						numberPoolService.completeAssignment(e);
					} catch(ApplicationException ae) {
						LOGGER.error("Error occurred while marking assignment as expired assignment with name- " + e.getDescription());
					}
				}
			}
		} catch (ApplicationException e) {
			LOGGER.error("Error occurred while processing expired assignment");
		}
		
		LOGGER.info("********************************************************");
		LOGGER.info("Processing ends for  Finish Expired Assignment at - {}", SDF_YYYYMMDD_HHMMSS.format(new Date()));
		LOGGER.info("********************************************************");
	}
	
	
	@Scheduled(fixedDelayString="${scheduled-job.delete-old-assignment.fixed-delay.miliseconds}", initialDelayString = "${scheduled-job.delete-old-assignment.initial-delay.miliseconds}")
	public void deleteAssignmentInventoryOldRecords() {
		LOGGER.info("********************************************************");
		LOGGER.info("Processing started for Delete Assignment Inv Old Records at - {}", SDF_YYYYMMDD_HHMMSS.format(new Date()));
		LOGGER.info("********************************************************");
		
		try {
			numberPoolService.deleteOldAssignmentHistory();
		} catch (ApplicationException e) {
			LOGGER.error("Error occurred while deleting old expired assignments");
		}
		
		LOGGER.info("********************************************************");
		LOGGER.info("Processing ends for Delete Assignment Inv Old Records at - {}", SDF_YYYYMMDD_HHMMSS.format(new Date()));
		LOGGER.info("********************************************************");
	}
}
