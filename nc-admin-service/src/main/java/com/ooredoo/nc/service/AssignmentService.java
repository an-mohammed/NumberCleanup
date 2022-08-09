package com.ooredoo.nc.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.model.AppUser;
import com.ooredoo.nc.model.NumberPool;
import com.ooredoo.nc.repo.CompositeRepository;
import com.ooredoo.nc.utility.ApplicationConstants;

@Service
public class AssignmentService implements ApplicationConstants {
	
	@Autowired
	private CompositeRepository compositeRepo;
	private static final Logger LOGGER = LoggerFactory.getLogger(AssignmentService.class);
	
	/**
	 * 
	 * @return
	 */
	public List<NumberPool> getAllNumbers() {
		return compositeRepo.getNumberPoolRepo().findAll();
	}
	
	/**
	 * 
	 * @return
	 */
	public List<NumberPool> getAllActiveNumbers() {
		return compositeRepo.getNumberPoolRepo().findByEnabled(true);
	}
	
	/**
	 * 
	 * @return
	 */
	public List<NumberPool> getAllAvailableNumbers() {
		return compositeRepo.getNumberPoolRepo().findAvailableNumbers();
	}
	
	/**
	 * 
	 * @param number
	 * @return
	 * @throws ApplicationException
	 */
	public NumberPool addNumberToPool(NumberPool number) throws ApplicationException {
		if(null != number ) {
			
			if(null != number.getMsisdn() && !number.getMsisdn().isEmpty()) {
				if(number.getMsisdn().length() != 8) {
					throw new ApplicationException("CAS.005");
				} 
				
				number.setMsisdn(MSISDN_PREFIX + number.getMsisdn());
				
				if(null != number.getSimNo() && !number.getSimNo().isEmpty()) {
					
					if(!number.getSimNo().startsWith(SIMNO_PREFIX)) {
						throw new ApplicationException("CAS.006");
					} 
					
					if(null != number.getImsi() && !number.getImsi().isEmpty()) {
						if(null != number.getCreatedBy() && compositeRepo.getUserRepo().existsById(number.getCreatedBy())) {
							number.setCreatedDate(new Date());
							number.setEnabled(true);
							
							Optional<NumberPool> existingMsisdn =  compositeRepo.getNumberPoolRepo().findByMsisdn(number.getMsisdn());
							Optional<NumberPool> existingSimno =  compositeRepo.getNumberPoolRepo().findBySimNo(number.getSimNo());
							Optional<NumberPool> existingImsi =  compositeRepo.getNumberPoolRepo().findByImsi(number.getImsi());
							
							if(existingMsisdn.isPresent()) {
								throw new ApplicationException("CAS.025");
								
							} else {
								
								if(existingSimno.isPresent()) {
									throw new ApplicationException("CAS.026", new Object[] {existingSimno.get().getMsisdn()});
								}
								
								if(existingImsi.isPresent()) {
									throw new ApplicationException("CAS.027", new Object[] {existingImsi.get().getMsisdn()});
								}
								
								
								LOGGER.info("Going to create number in pool with msisdn : {}, SIM : {}, IMSI : {}", number.getMsisdn(), number.getSimNo(), number.getImsi());
								return compositeRepo.getNumberPoolRepo().save(number);
							}
							
						} else {
							throw new ApplicationException("CAS.007");
						}
					} else {
						throw new ApplicationException("CAS.003");
					}
				} else {
					throw new ApplicationException("CAS.002");
				}
			} else {
				throw new ApplicationException("CAS.001");
			}
		} else {
			throw new ApplicationException("CAS.004");
		}
	}
	
	/**
	 * 
	 * @param msisdn
	 * @throws ApplicationException
	 */
	public void deleteNumberFromPool(String msisdn) throws ApplicationException {
		
		if(null != msisdn && !msisdn.isEmpty()) {
			Optional<NumberPool> existingMsisdn = compositeRepo.getNumberPoolRepo().findByMsisdn(msisdn);
			
			if(existingMsisdn.isPresent()) {
				compositeRepo.getNumberPoolRepo().deleteById(existingMsisdn.get().getId());
			} else {
				throw new ApplicationException("CAS.028");
			}
		}
	}
	
	/**
	 * 
	 * @param msisdn
	 * @param username
	 * @return
	 * @throws ApplicationException
	 */
	public NumberPool disableNumber(String msisdn, String username)  throws ApplicationException {

		if(null != msisdn && !msisdn.isEmpty() && null != username && !username.isEmpty()) {
			Optional<NumberPool> existingMsisdn = compositeRepo.getNumberPoolRepo().findByMsisdn(msisdn);
			
			if(existingMsisdn.isPresent()) {
				Optional<AppUser> uO = compositeRepo.getUserRepo().findByUNameAndIsEnabledEquals(username, true);
				
				if(uO.isPresent()) {
					NumberPool np = existingMsisdn.get();
					
					if(np.getEnabled()) {
						
						if(null != np.getNumberAsgnmtHistory() && null != np.getNumberAsgnmtHistory().getId()) {
							throw new ApplicationException("CAS.030", new Object[] {np.getNumberAsgnmtHistory().getDescription()});
						}
						
						np.setEnabled(false);
						np.setModDate(new Date());
						np.setModBy(uO.get().getId());
						return compositeRepo.getNumberPoolRepo().save(np);
						
					} else {
						throw new ApplicationException("CAS.029");
					}
				} else {
					throw new ApplicationException("CAS.007");
				}
			} else {
				throw new ApplicationException("CAS.028");
			}
		} else {
			throw new ApplicationException("Invalid request. Missing msisdn or username", true);
		}
	}
	
	/**
	 * 
	 * @param msisdn
	 * @param username
	 * @return
	 * @throws ApplicationException
	 */
	public NumberPool enableNumber(String msisdn, String username)  throws ApplicationException {

		if(null != msisdn && !msisdn.isEmpty() && null != username && !username.isEmpty()) {
			Optional<NumberPool> existingMsisdn = compositeRepo.getNumberPoolRepo().findByMsisdn(msisdn);
			
			if(existingMsisdn.isPresent()) {
				Optional<AppUser> uO = compositeRepo.getUserRepo().findByUNameAndIsEnabledEquals(username, true);
				
				if(uO.isPresent()) {
					NumberPool np = existingMsisdn.get();
					
					if(!np.getEnabled()) {
						np.setEnabled(true);
						np.setModDate(new Date());
						np.setModBy(uO.get().getId());
						return compositeRepo.getNumberPoolRepo().save(np);
						
					} else {
						throw new ApplicationException("CAS.029");
					}
				} else {
					throw new ApplicationException("CAS.007");
				}
			} else {
				throw new ApplicationException("CAS.028");
			}
		} else {
			throw new ApplicationException("Invalid request. Missing msisdn or username", true);
		}
	}
	
	/**
	 * 
	 * @param number
	 * @return
	 * @throws ApplicationException
	 */
	public NumberPool updateNumberToPool(NumberPool number) throws ApplicationException {
		Optional<AppUser> uO = null;
		if(null != number ) {
			if(null != number.getMsisdn() && !number.getMsisdn().isEmpty()) {
				if(number.getMsisdn().length() != 8) {
					throw new ApplicationException("CAS.005");
				} 
				
				number.setMsisdn(MSISDN_PREFIX + number.getMsisdn());
				
				if(null == number.getModBy()) {
					throw new ApplicationException("CAS.008", new Object[] {"Number Pool", "Modified By"});
					
				} else {
					uO = compositeRepo.getUserRepo().findByIdAndIsEnabledEquals(number.getModBy(), true);
					
					if(!uO.isPresent()) {
						throw new ApplicationException("CAS.007");
					}
				}
				
				
				Optional<NumberPool> existingMsisdn =  compositeRepo.getNumberPoolRepo().findByMsisdn(number.getMsisdn());
				
				if(existingMsisdn.isPresent()) {
					NumberPool eNumber = existingMsisdn.get();
					
					if((null != number.getImsi() && !number.getImsi().isEmpty()) || (null != number.getSimNo() && !number.getSimNo().isEmpty())) {
						
						if(null != number.getSimNo() && !number.getSimNo().isEmpty()) {
							
							if(!number.getSimNo().startsWith(SIMNO_PREFIX)) {
								throw new ApplicationException("CAS.006");
							}
							
							Optional<NumberPool> existingSimno =  compositeRepo.getNumberPoolRepo().findBySimNo(number.getSimNo());
							
							if(existingSimno.isPresent() && !existingSimno.get().getMsisdn().equals(number.getMsisdn())) {
								throw new ApplicationException("CAS.026", new Object[] {existingSimno.get().getMsisdn()});
							} else {
								eNumber.setSimNo(number.getSimNo());
							}
						}
						
						if(null != number.getImsi() && !number.getImsi().isEmpty()) {
							
							Optional<NumberPool> existingImsi =  compositeRepo.getNumberPoolRepo().findByImsi(number.getImsi());
							
							if(existingImsi.isPresent() && !existingImsi.get().getMsisdn().equals(number.getMsisdn())) {
								throw new ApplicationException("CAS.026", new Object[] {existingImsi.get().getMsisdn()});
							} else {
								eNumber.setImsi(number.getImsi());
							}
						}
						
						eNumber.setModDate(new Date());
						eNumber.setModBy(uO.get().getId());
						return compositeRepo.getNumberPoolRepo().save(eNumber);
						
					} else {
						throw new ApplicationException("CAS.032");
					}
				} else {
					throw new ApplicationException("CAS.031", new Object[] {number.getMsisdn()});
				}
				
			} else {
				throw new ApplicationException("CAS.001");
			}
		} else {
			throw new ApplicationException("CAS.004");
		}
	}
}
