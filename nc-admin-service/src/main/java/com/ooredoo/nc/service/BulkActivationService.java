package com.ooredoo.nc.service;

import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.model.BulkActivationDetail;
import com.ooredoo.nc.model.NumberPool;
import com.ooredoo.nc.repo.CompositeRepository;
import com.ooredoo.nc.utility.ApplicationConstants;

@Service
public class BulkActivationService implements ApplicationConstants  {
	
	@Autowired
	private ReloadableResourceBundleMessageSource externalConfig;
	
	@Autowired
	private NumberPoolService numberPoolService; 
	
	@Autowired
	private CompositeRepository compositeRepo;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BulkActivationService.class);

	@Transactional(isolation = Isolation.READ_COMMITTED)
	public List<BulkActivationDetail> getActivationDetailsForUser(String username) throws ApplicationException {
		return compositeRepo.getBulkActivationRepo().findAllByUsername(username);
	}
	
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public List<BulkActivationDetail> getActivationDetailsAscOrder() throws ApplicationException {
		return compositeRepo.getBulkActivationRepo().findAllByAscOrder();
	}
	
	public BulkActivationDetail updateActivationDetails(BulkActivationDetail bad) throws ApplicationException {
		return compositeRepo.getBulkActivationRepo().save(bad);
	}
	
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public Long getActivationDetailsCountForUser(String username) throws ApplicationException {
		return compositeRepo.getBulkActivationRepo().countAllByUsername(username);
	}
	
	@Transactional
	public void removeOldRecords() throws ApplicationException {
		compositeRepo.getBulkActivationRepo().removeOldData();
	}
	
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public Long createActivationDetailsForUser(List<BulkActivationDetail> activationList) throws ApplicationException {
		Long batchId = 0L;
		
		if(null != activationList && !activationList.isEmpty()) {
			
			Long activeCount = getActivationDetailsCountForUser(activationList.get(0).getUsername());
			if(null != activeCount && activeCount > 0L) {
				LOGGER.error("Execution still pending from previous job. New Request can only be posted once the previous batch is complete");
				throw new ApplicationException("Execution still pending from previous job. New Request can only be posted once the previous batch is complete", true);
			}
			
			batchId = compositeRepo.getCommonDao().getBatchIdForBulkActivation();
			ListIterator<BulkActivationDetail> li = activationList.listIterator();
			
			while(li.hasNext()) {
				BulkActivationDetail i = li.next();
				i.setStartTime(new Date());
				i.setStatus("I");
				i.setBatchId(batchId);
				
				
				if(null != i.getSubsType() && !i.getSubsType().isEmpty()) {
					if(null != i.getCoType() && !i.getCoType().isEmpty()) {
						if(i.getSubsType().equals("PO") || i.getSubsType().equals("PP")) {
							if(i.getCoType().equals("V") || i.getCoType().equals("D")) {
								String promoDetails = null;
								String[] promoDef = null;
								
								if(i.getSubsType().equals("PO")) {
									if(i.getCoType().equals("V")) {
										promoDetails = externalConfig.getMessage("bulk-activation.promo.postpaid.voice", null, Locale.getDefault());
										promoDef = promoDetails.split(HASH);
										
									} else if(i.getCoType().equals("D")) {
										promoDetails = externalConfig.getMessage("bulk-activation.promo.postpaid.data", null, Locale.getDefault());
										promoDef = promoDetails.split(HASH);
									}
								} else if(i.getSubsType().equals("PP")) {
									if(i.getCoType().equals("V")) {
										promoDetails = externalConfig.getMessage("bulk-activation.promo.prepaid.voice", null, Locale.getDefault());
										promoDef = promoDetails.split(HASH);
										
									} else if(i.getCoType().equals("D")) {
										promoDetails = externalConfig.getMessage("bulk-activation.promo.prepaid.data", null, Locale.getDefault());
										promoDef = promoDetails.split(HASH);
									}
								}
								
								i.setPromoName(promoDef[0]);
								i.setPromoId(promoDef[1]);
								
								if(i.getMsisdn().startsWith(MSISDN_PREFIX) && i.getMsisdn().length() == 11) {
									try {
										NumberPool np = numberPoolService.getNumberPoolByMsisdn(i.getMsisdn());
										
										if(null != np) {
											i.setImsi(np.getImsi());
											i.setSimNo(np.getSimNo());
											
										} else {
											i.setErrorDetails("MSISDN NOT found in number pool. Please add the same in Number-Pool");
											i.setStatus("E");
										}
									} catch(ApplicationException ae) {
										i.setErrorDetails("MSISDN NOT found in number pool. Please add the same in Number-Pool");
										i.setStatus("E");
									}
								} else if(i.getMsisdn().length() == 8) {
									String tempMsisdn = MSISDN_PREFIX + i.getMsisdn();
									
									try {
										NumberPool np = numberPoolService.getNumberPoolByMsisdn(tempMsisdn);
										
										if(null != np) {
											i.setMsisdn(tempMsisdn);
											i.setImsi(np.getImsi());
											i.setSimNo(np.getSimNo());
											
										} else {
											i.setErrorDetails("MSISDN NOT found in number pool. Please add the same in Number-Pool");
											i.setStatus("E");
										}
									} catch(ApplicationException ae) {
										i.setErrorDetails("MSISDN NOT found in number pool. Please add the same in Number-Pool");
										i.setStatus("E");
									}
								} else {
									i.setErrorDetails("Invalid MSISDN");
									i.setStatus("E");
								}
							} else {
								i.setErrorDetails("Invalid Line Type. Valid values are V(VOICE)/D(DATA)");
								i.setStatus("E");
							}
						} else {
							i.setErrorDetails("Invalid Subscriber Type. Valid values are PO(POSTPAID)/PP(PREPAID)");
							i.setStatus("E");
						}
					} else {
						i.setErrorDetails("Invalid Line Type. Valid values are V(VOICE)/D(DATA)");
						i.setStatus("E");
					}
				} else {
					i.setErrorDetails("Invalid Subscriber Type. Valid values are PO(POSTPAID)/PR(PREPAID)");
					i.setStatus("E");
				}
				
				li.set(i);
			}
			
			compositeRepo.getBulkActivationRepo().saveAll(activationList);
		} else {
			throw new ApplicationException("No line definition received", true);
		}
		
		return batchId;
	}
}
