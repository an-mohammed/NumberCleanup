package com.ooredoo.nc.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ooredoo.nc.model.NumberAsgnmtHistory;


@Repository
public interface NumberAssignmentHistRepo extends CrudRepository<NumberAsgnmtHistory, Long>{

	public List<NumberAsgnmtHistory> findByAppUserId(Long id);
	public List<NumberAsgnmtHistory> findByStatus(String status);
	public List<NumberAsgnmtHistory> findAll();
	public List<NumberAsgnmtHistory> findAllByOrderByStatus();
	
	@Query(nativeQuery=true, value="SELECT * FROM {h-schema}NUMBER_ASGNMT_HISTORY WHERE ID = (select NAH.ID from {h-schema}NUMBER_ASGNMT_HISTORY NAH, {h-schema}NUMBER_POOL NP WHERE NAH.ID=NP.ASSMT_ID AND NAH.STATUS='A' AND NP.MSISDN=:msisdn)")
	public List<NumberAsgnmtHistory> findByMsisdn(@Param("msisdn") String msisdn);
	
	@Query(nativeQuery=true, value="SELECT * FROM {h-schema}NUMBER_ASGNMT_HISTORY WHERE TRUNC(END_DATE) <= TRUNC(SYSDATE) AND STATUS='A'")
	public List<NumberAsgnmtHistory> findAllExpiredAssignment();
	
	@Modifying
	@Query(nativeQuery=true, value="DELETE FROM {h-schema}NUMBER_ASGNMT_HISTORY WHERE STATUS='C' AND TRUNC(END_DATE) BETWEEN TRUNC(SYSDATE - 30) AND  TRUNC(SYSDATE - 15)")
	public void deleteOldRecords();
	
}
