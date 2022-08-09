package com.ooredoo.nc.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ooredoo.nc.model.AppUser;


@Repository
public interface UserRepo extends CrudRepository<AppUser, Long>{

	public Optional<AppUser> findByUName(String uName);
	
	public List<AppUser> findAll();
	
	public List<AppUser> findAllByIsEnabled(Boolean isEnabled);
	
	public Boolean existsByUName(String uName);
	
	public Boolean existsByEmail(String email);
	
	public Boolean existsByContactNo(String contactNo);
	
	public Optional<AppUser> findByEmail(String email);
	
	public Optional<AppUser> findByContactNo(String contactNo);
	
	public Optional<AppUser> findByUNameAndIsEnabledEquals(String uName, Boolean isEnabled);
	
	public Optional<AppUser> findByUNameAndIsEnabledEqualsAndIsAccLockedEquals(String uName, Boolean isEnabled, Boolean isLocked);
	
	@Query(nativeQuery = true, value="SELECT AU.* FROM {h-schema}APP_USERS AU WHERE AU.U_NAME=:U_NAME AND AU.IS_ENABLED=:IS_ENABLED AND AU.IS_ACC_LOCKED=:IS_ACC_LOCKED")
	public Optional<AppUser> getUserDetailsForAuthentication(@Param("U_NAME") String uName, @Param("IS_ENABLED")Boolean isEnabled, @Param("IS_ACC_LOCKED")Boolean isLocked);
	
	public Optional<AppUser> findByIdAndIsEnabledEquals(Long id, Boolean isEnabled);
	
	public List<AppUser> findAll(Specification<AppUser> spec);
	
	public List<AppUser> findAll(Specification<AppUser> spec, Sort sort);
	
	public List<AppUser> findByRolesNameIn(List<String> name);
	
	public List<AppUser> findByIsEnabledEqualsAndRolesNameIn(Boolean isEnabled, List<String> name);
	
}
