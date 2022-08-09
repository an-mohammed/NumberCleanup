package com.ooredoo.nc.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ooredoo.nc.model.NcAppUserGrp;


@Repository
public interface UserGroupRepo extends CrudRepository<NcAppUserGrp, Long>{

	public Optional<NcAppUserGrp> findByGrpName(String grpName);
	
	public Boolean existsByGrpName(String grpName);
	
	public List<NcAppUserGrp> findAll();
	
	@Query(nativeQuery = true, value = "select grp.* from nc_app_user_grp grp, nc_user_grp ug where grp.id=ug.grp_id and ug.user_id=:USER_ID")
	public List<NcAppUserGrp> getAllGroupsForUser(@Param(value = "USER_ID") Long id);
	
}
