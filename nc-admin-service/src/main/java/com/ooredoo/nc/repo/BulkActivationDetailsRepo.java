package com.ooredoo.nc.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ooredoo.nc.model.BulkActivationDetail;


@Repository
public interface BulkActivationDetailsRepo extends CrudRepository<BulkActivationDetail, Long>{

	public List<BulkActivationDetail> findAll();
	
	public List<BulkActivationDetail> findAllByMsisdnOrderByStartTime(String msisdn);
	
	@Query(nativeQuery = true, value="SELECT * FROM {h-schema}BULK_ACTIVATION_DETAILS WHERE TRUNC(START_TIME) = TRUNC(SYSDATE) AND USERNAME=:USERNAME ORDER BY START_TIME DESC, BATCH_ID ASC")
	public List<BulkActivationDetail> findAllByUsername(@Param("USERNAME") String username);
	
	@Query(nativeQuery = true, value="SELECT COUNT(*) FROM {h-schema}BULK_ACTIVATION_DETAILS WHERE TRUNC(START_TIME) = TRUNC(SYSDATE) AND USERNAME=:USERNAME AND STATUS IN ('I','P')")
	public Long countAllByUsername(@Param("USERNAME") String username);
	
	@Query(nativeQuery = true, value="SELECT * FROM {h-schema}BULK_ACTIVATION_DETAILS WHERE STATUS='I' AND TRUNC(START_TIME) = TRUNC(SYSDATE) ORDER BY START_TIME ASC, BATCH_ID ASC")
	public List<BulkActivationDetail> findAllByAscOrder();
	
	@Modifying
	@Query(nativeQuery = true, value="DELETE FROM {h-schema}BULK_ACTIVATION_DETAILS WHERE TRUNC(START_TIME) BETWEEN TRUNC(SYSDATE -3) AND TRUNC(SYSDATE - 3)")
	public void removeOldData();
	
}
