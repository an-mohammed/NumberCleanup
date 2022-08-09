package com.ooredoo.nc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.ooredoo.nc.dto.ReleaseNumberFromAssignmentReq;
import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.model.AppUser;
import com.ooredoo.nc.model.NumberAsgnmtHistory;
import com.ooredoo.nc.model.NumberPool;
import com.ooredoo.nc.repo.CompositeRepository;
import com.ooredoo.nc.utility.ApplicationConstants;

@Service
public class NumberPoolService implements ApplicationConstants {
	
	@Autowired
	private CompositeRepository compositeRepo;
	private static final Logger LOGGER = LoggerFactory.getLogger(NumberPoolService.class);
	
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
	 * @return
	 */
	public List<NumberAsgnmtHistory> getAvailableAssignmentForUser(Long id) {
		return compositeRepo.getaHistRepo().findByAppUserId(id);
	}
	
	public List<NumberAsgnmtHistory> getAllAssignment() {
		List<NumberAsgnmtHistory> resp = compositeRepo.getaHistRepo().findAllByOrderByStatus();
		return resp;
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
					throw new ApplicationException("CAS.044");
				} 
				
				number.setMsisdn(MSISDN_PREFIX + number.getMsisdn());
				
				if(null != number.getSimNo() && !number.getSimNo().isEmpty()) {
					
					if(!number.getSimNo().startsWith(SIMNO_PREFIX)) {
						throw new ApplicationException("CAS.045");
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
							throw new ApplicationException("CAS.046");
						}
					} else {
						throw new ApplicationException("CAS.042");
					}
				} else {
					throw new ApplicationException("CAS.041");
				}
			} else {
				throw new ApplicationException("CAS.040");
			}
		} else {
			throw new ApplicationException("CAS.043");
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
					throw new ApplicationException("CAS.046");
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
					throw new ApplicationException("CAS.046");
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
				if(number.getMsisdn().length() == 8 || number.getMsisdn().length() == 11) {
					if(number.getMsisdn().length() == 8) {
						number.setMsisdn(MSISDN_PREFIX + number.getMsisdn());
					}
				} else {
					throw new ApplicationException("CAS.044");
				}
				
				if(null == number.getModBy()) {
					throw new ApplicationException("CAS.008", new Object[] {"Number Pool", "Modified By"});
					
				} else {
					uO = compositeRepo.getUserRepo().findByIdAndIsEnabledEquals(number.getModBy(), true);
					
					if(!uO.isPresent()) {
						throw new ApplicationException("CAS.046");
					}
				}
				
				
				Optional<NumberPool> existingMsisdn =  compositeRepo.getNumberPoolRepo().findByMsisdn(number.getMsisdn());
				
				if(existingMsisdn.isPresent()) {
					NumberPool eNumber = existingMsisdn.get();
					
					if((null != number.getImsi() && !number.getImsi().isEmpty()) || (null != number.getSimNo() && !number.getSimNo().isEmpty())) {
						
						if(null != number.getSimNo() && !number.getSimNo().isEmpty()) {
							
							if(!number.getSimNo().startsWith(SIMNO_PREFIX)) {
								throw new ApplicationException("CAS.045");
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
						eNumber.setEnabled(number.getEnabled());
						eNumber.setPhSim(number.getPhSim());
						return compositeRepo.getNumberPoolRepo().save(eNumber);
						
					} else {
						throw new ApplicationException("CAS.032");
					}
				} else {
					throw new ApplicationException("CAS.031", new Object[] {number.getMsisdn()});
				}
				
			} else {
				throw new ApplicationException("CAS.040");
			}
		} else {
			throw new ApplicationException("CAS.043");
		}
	}
	
	public NumberPool updateNumberToPool(ReleaseNumberFromAssignmentReq number) throws ApplicationException {
		Optional<AppUser> uO = null;
		
		if(null != number ) {
			if(null != number.getMsisdn() && !number.getMsisdn().isEmpty()) {
				if(number.getMsisdn().length() == 8 || number.getMsisdn().length() == 11) {
					if(number.getMsisdn().length() == 8) {
						number.setMsisdn(MSISDN_PREFIX + number.getMsisdn());
					}
				} else {
					throw new ApplicationException("CAS.044");
				}
				
				if(null == number.getModBy()) {
					throw new ApplicationException("CAS.008", new Object[] {"Number Pool", "Modified By"});
					
				} else {
					uO = compositeRepo.getUserRepo().findByIdAndIsEnabledEquals(number.getModBy(), true);
					
					if(!uO.isPresent()) {
						throw new ApplicationException("CAS.046");
					}
				}
				
				
				Optional<NumberPool> existingMsisdn =  compositeRepo.getNumberPoolRepo().findByMsisdn(number.getMsisdn());
				
				if(existingMsisdn.isPresent()) {
					NumberPool eNumber = existingMsisdn.get();
					
					if(null != eNumber.getNumberAsgnmtHistory() && null != eNumber.getNumberAsgnmtHistory().getId()) {
						eNumber.setNumberAsgnmtHistory(null);
						eNumber.setModBy(number.getModBy());
						eNumber.setModDate(new Date());
						
						return compositeRepo.getNumberPoolRepo().save(eNumber);
						
					} else {
						throw new ApplicationException("Number is Not assigned to any user", true);
					}
					
				} else {
					throw new ApplicationException("CAS.031", new Object[] {number.getMsisdn()});
				}
				
			} else {
				throw new ApplicationException("CAS.040");
			}
		} else {
			throw new ApplicationException("No request provided", true);
		}
	}
	
	public List<NumberAsgnmtHistory> searchAssignment(String msisdn) throws ApplicationException {
		List<NumberAsgnmtHistory> hist = compositeRepo.getaHistRepo().findByMsisdn(msisdn);
		
		return hist;
	}
	
	public NumberPool searchNumber(String msisdn) throws ApplicationException {
		NumberPool np = null;
		Optional<NumberPool> npO = compositeRepo.getNumberPoolRepo().findByMsisdn(msisdn);
		
		if(npO.isPresent()) {
			np = npO.get();
		}
		
		return np;
	}
	
	/**
	 * 
	 * @param asgmt
	 * @return
	 * @throws ApplicationException
	 */
	public NumberAsgnmtHistory createAssignment(NumberAsgnmtHistory asgmt) throws ApplicationException {
		LOGGER.info("Creating project for user :{}", asgmt.getAssignedToUser());
		
		if(null != asgmt.getCreatedBy() && compositeRepo.getUserRepo().existsById(asgmt.getCreatedBy())) {
			Optional<AppUser> uO = compositeRepo.getUserRepo().findByUNameAndIsEnabledEquals(asgmt.getAssignedToUser(), true); 
			
			if(uO.isPresent()) {
				if(null != asgmt.getNumberPools() && !asgmt.getNumberPools().isEmpty()) {
					List<NumberPool> np = null;
					
					np = new ArrayList<NumberPool>();
					
					for(NumberPool aN : asgmt.getNumberPools()) {
						Optional<NumberPool> npO = compositeRepo.getNumberPoolRepo().checkAvailableNumber(aN.getMsisdn());
						
						if(npO.isPresent()) {
							np.add(npO.get());
						} else {
							throw new ApplicationException("CAS.037", new Object[] {aN.getMsisdn()});
						}
					}
					asgmt.getNumberPools().clear();
					asgmt.setNumberPools(np);
					
					
					if(null != asgmt.getStartDate() 
							&& null != asgmt.getDescription() && !asgmt.getDescription().isEmpty() 
							&& null != asgmt.getReason() && !asgmt.getReason().isEmpty()) {
						asgmt.setAppUser(uO.get());
						asgmt.setCreatedOn(new Date());
						asgmt.setStatus("A");
						asgmt.setAssignedToUser(null);
						return compositeRepo.getaHistRepo().save(asgmt);
					} else {
						throw new ApplicationException("CAS.035");
					}
				} else {
					throw new ApplicationException("CAS.034");
				}
			} else {
				throw new ApplicationException("CAS.033");
			}
		} else {
			throw new ApplicationException("CAS.046");
		}
	}
	
	/**
	 * 
	 * @param req
	 * @return
	 * @throws ApplicationException
	 */
	@Modifying
	@Transactional
	public NumberAsgnmtHistory completeAssignment(NumberAsgnmtHistory req) throws ApplicationException {
		if(null != req.getId() && compositeRepo.getaHistRepo().existsById(req.getId())) {
			
			if(null != req.getModBy()) {
				Optional<AppUser> modBy = compositeRepo.getUserRepo().findByIdAndIsEnabledEquals(req.getModBy(), true);
				
				if(modBy.isPresent()) {
					Optional<NumberAsgnmtHistory> hO = compositeRepo.getaHistRepo().findById(req.getId());
					
					if(hO.isPresent()) {
						NumberAsgnmtHistory hist = hO.get();
						
						if(hist.getStatus().equals("A")) {
							hist.setStatus("C");
							hist.setModOn(new Date());
							hist.setModBy(req.getModBy());
							
							if(null == hist.getEndDate()) {
								hist.setEndDate(new Date());
							}
							
							ListIterator<NumberPool> li = hist.getNumberPools().listIterator();
							
							while(li.hasNext()) {
								li.next().setNumberAsgnmtHistory(null);
							}
							
							NumberAsgnmtHistory resp = compositeRepo.getaHistRepo().save(hist);
							if(null != resp && !resp.getNumberPools().isEmpty()) {
								resp.getNumberPools().clear();
							}
							return resp;
						} else {
							throw new ApplicationException("CAS.038");
						}
					} else {
						throw new ApplicationException("CAS.053", new Object[] {req.getId()});
					}
					
				} else {
					throw new ApplicationException("CAS.046");
				}
			} else {
				throw new ApplicationException("CAS.008", new Object[] {"NumberAssignmentHistory", "Modified By"});
			}
		} else {
			throw new ApplicationException("CAS.039");
		}
	}
	
	public NumberAsgnmtHistory updateAssignment(NumberAsgnmtHistory req) throws ApplicationException {
		if(null != req.getId() && compositeRepo.getaHistRepo().existsById(req.getId())) {
			
			if(null != req.getModBy()) {
				Optional<AppUser> modBy = compositeRepo.getUserRepo().findByIdAndIsEnabledEquals(req.getModBy(), true);
				
				if(modBy.isPresent()) {
					Optional<NumberAsgnmtHistory> hO = compositeRepo.getaHistRepo().findById(req.getId());
					
					if(hO.isPresent()) {
						NumberAsgnmtHistory hist = hO.get();
						
						hist.setModOn(new Date());
						hist.setModBy(req.getModBy());
						
						if(null != req.getAssignedToUser() && !req.getAssignedToUser().equals(hist.getAppUser().getuName())) {
							Optional<AppUser> uO = compositeRepo.getUserRepo().findByUNameAndIsEnabledEquals(req.getAssignedToUser(), true);
							if(uO.isPresent()) {
								hist.setAppUser(uO.get());
							} else {
								throw new ApplicationException("CAS.047");
							}
						}
						
						if(req.getNumberPools().isEmpty()) {
							throw new ApplicationException("Assignment must have atleast one number profile.");
							
						} else {
							List<NumberPool> n = new ArrayList<NumberPool>();
							
							for(NumberPool np : req.getNumberPools()) {
								Optional<NumberPool> nO = compositeRepo.getNumberPoolRepo().findByMsisdn(np.getMsisdn());
								
								if(nO.isPresent()) {
									n.add(nO.get());
								}
							}
							
							ListIterator<NumberPool> liNp = hist.getNumberPools().listIterator();
							while(liNp.hasNext()) {
								NumberPool nLocal = liNp.next();
								nLocal.setNumberAsgnmtHistory(null);
								liNp.set(nLocal);
							}
							
							hist.setNumberPools(n);
						}
						
						if(null != req.getStatus() && req.getStatus().equals(hist.getStatus())) {
							hist.setStatus(req.getStatus());
						}
						
						if(null != req.getEndDate() && req.getEndDate().compareTo(hist.getEndDate()) != 0) {
							hist.setEndDate(req.getEndDate());
						}
						
						if(null != req.getStartDate() && req.getStartDate().compareTo(hist.getStartDate()) != 0) {
							hist.setStartDate(req.getStartDate());
						}
						
						if(null != req.getDescription() && !req.getDescription().equals(hist.getDescription())) {
							hist.setDescription(req.getDescription());
						}
						
						if(null != req.getReason() && !req.getReason().equals(hist.getReason())) {
							hist.setReason(req.getReason());
						}
						
						NumberAsgnmtHistory resp = compositeRepo.getaHistRepo().save(hist);
						
						return resp;
					} else {
						throw new ApplicationException("CAS.053", new Object[] {req.getId()});
					}
				} else {
					throw new ApplicationException("CAS.046");
				}
			} else {
				throw new ApplicationException("CAS.008", new Object[] {"NumberAssignmentHistory", "Modified By"});
			}
		} else {
			throw new ApplicationException("CAS.039");
		}
	}
	
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public List<NumberAsgnmtHistory> getAllExpiredAssignment() throws ApplicationException {
		return compositeRepo.getaHistRepo().findAllExpiredAssignment();
	}
	
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public void deleteOldAssignmentHistory() throws ApplicationException {
		compositeRepo.getaHistRepo().deleteOldRecords();
	}
	
	public List<NumberPool> getAllAssignedNumberToUserGroup(Long userId) {
		return compositeRepo.getNumberPoolRepo().getAllAssignedNumberToUserGroup(userId);
	}
	
	public List<NumberPool> getAllActiveNumbersForUser(Long userId) {
		return compositeRepo.getNumberPoolRepo().getAllActiveNumbersForUser(userId);
	}
	
	public NumberPool getNumberPoolByMsisdn(String msisdn) throws ApplicationException{
		NumberPool np = null;
		
		if(null != msisdn && !msisdn.isEmpty()) {
			Optional<NumberPool> npO = null;
			
			if(msisdn.length() == 11 && msisdn.startsWith(MSISDN_PREFIX)) {
				npO = compositeRepo.getNumberPoolRepo().findByMsisdn(msisdn);
				
			} else if(msisdn.length() == 8) {
				npO = compositeRepo.getNumberPoolRepo().findByMsisdn(MSISDN_PREFIX + msisdn);
				
			} else {
				throw new ApplicationException("Invalid MSISDN provided", true);
			}
			
			if(null != npO && npO.isPresent()) {
				np = npO.get();
			}
		} else {
			throw new ApplicationException("No MSISDN provided", true);
		}
		
		return np;
	}
}
