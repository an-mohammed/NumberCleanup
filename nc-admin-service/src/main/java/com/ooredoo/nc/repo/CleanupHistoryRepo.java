package com.ooredoo.nc.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ooredoo.nc.model.CleanupLogMaster;


@Repository
public interface CleanupHistoryRepo extends CrudRepository<CleanupLogMaster, Long>{

	public List<CleanupLogMaster> findAll();
	
	public List<CleanupLogMaster> findAllByMsisdnOrderByExecDateDesc(String msisdn);
	
	@Query(nativeQuery = true, value="SELECT * FROM (SELECT * FROM CLEANUP_LOG_MASTER WHERE MSISDN=:MSISDN ORDER BY EXEC_DATE DESC) WHERE ROWNUM <20")
	public List<CleanupLogMaster> findAllByMsisdnWithLimit(@Param("MSISDN") String msisdn);
	
	
	
}
