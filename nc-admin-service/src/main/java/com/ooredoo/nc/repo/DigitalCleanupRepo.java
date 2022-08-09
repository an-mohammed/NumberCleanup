package com.ooredoo.nc.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ooredoo.nc.model.NcDigitalOnboarding;


@Repository
public interface DigitalCleanupRepo extends CrudRepository<NcDigitalOnboarding, Long>{

	public List<NcDigitalOnboarding> findAll();
	
	@Query(nativeQuery=true, value="SELECT * FROM {h-schema}NC_DIGITAL_ONBOARDING DO WHERE DO.CLEANUP_STATUS IN ('I','F')")
	public List<NcDigitalOnboarding> findActiveOnboadringTrx();
	
	@Query(nativeQuery=true, value="SELECT * FROM {h-schema}NC_DIGITAL_ONBOARDING DO WHERE DO.CLEANUP_STATUS IN ('I','F') AND MSISDN=:msisdn")
	public Optional<NcDigitalOnboarding> findActiveOnboadringTrxForMsisdn(@Param("msisdn") String msisdn);
	
	//@Query(nativeQuery=true, value="SELECT * FROM {h-schema}NC_DIGITAL_ONBOARDING DO WHERE DO.USERNAME=:username and DO.TRX_DATE BETWEEN (SYSDATE - 72) AND SYSDATE ORDER BY ID DESC")
	//@Query(nativeQuery=true, value="SELECT * FROM  NC_DIGITAL_ONBOARDING DO  where DO.USERNAME=:username and DO.TRX_DATE BETWEEN (SYSDATE - 72) AND SYSDATE and rowid=(SELECT max(rowid) from  NC_DIGITAL_ONBOARDING DO )")
	@Query(nativeQuery=true, value="SELECT * FROM {h-schema}NC_DIGITAL_ONBOARDING DO WHERE DO.TRX_ID=:username")
	public List<NcDigitalOnboarding> findTrxForUsername(@Param("username") String username);
	
	public List<NcDigitalOnboarding> findByMsisdn(String msisdn);
	
	public List<NcDigitalOnboarding> findByTrxId(String trxId);
	
}
