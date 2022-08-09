package com.ooredoo.nc.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ooredoo.nc.model.NumberPool;


@Repository
public interface NumberPoolRepo extends CrudRepository<NumberPool, Long>{

	public List<NumberPool> findAll();
	public List<NumberPool> findByEnabled(Boolean enabled);
	
	@Query(nativeQuery=true, value="SELECT * FROM {h-schema}NUMBER_POOL NP WHERE NP.ASSMT_ID IS NULL AND NP.ENABLED=1")
	public List<NumberPool> findAvailableNumbers();
	
	public Optional<NumberPool> findByMsisdn(String msisdn);
	public Optional<NumberPool> findBySimNo(String simNo);
	public Optional<NumberPool> findByImsi(String imsi);
	
	@Query(nativeQuery=true, value="SELECT * FROM {h-schema}NUMBER_POOL NP WHERE NP.ASSMT_ID IS NULL AND NP.ENABLED=1 AND NP.MSISDN=:msisdn")
	public Optional<NumberPool> checkAvailableNumber(@Param("msisdn") String msisdn);
	
	@Query(nativeQuery=true, value="SELECT * FROM {h-schema}NUMBER_POOL NP WHERE NP.ASSMT_ID IS NULL AND NP.ENABLED=1 AND ID IN(SELECT A.NP_ID from {h-schema}NC_GRP_MSISDN A, {h-schema}NC_USER_GRP B WHERE A.GRP_ID=B.GRP_ID AND B.USER_ID=:userId)")
	public List<NumberPool> getAllAssignedNumberToUserGroup(@Param("userId") Long id);
	
	@Query(nativeQuery=true, value="SELECT * FROM {h-schema}NUMBER_POOL NP WHERE NP.ASSMT_ID IN (SELECT ID FROM {h-schema}NUMBER_ASGNMT_HISTORY NAH WHERE NAH.ASSIGNED_TO=:userId AND NAH.STATUS='A') AND NP.ENABLED=1 UNION SELECT * FROM {h-schema}NUMBER_POOL NP WHERE NP.ASSMT_ID IS NULL AND NP.ENABLED=1 AND ID IN (SELECT A.NP_ID from {h-schema}NC_GRP_MSISDN A, {h-schema}NC_USER_GRP B WHERE A.GRP_ID=B.GRP_ID AND B.USER_ID=:userId)")
	public List<NumberPool> getAllActiveNumbersForUser(@Param("userId") Long id);
}
