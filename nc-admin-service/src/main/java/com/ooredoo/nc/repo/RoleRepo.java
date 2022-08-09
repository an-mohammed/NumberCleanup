package com.ooredoo.nc.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ooredoo.nc.model.Role;

@Repository
public interface RoleRepo extends CrudRepository<Role, Long>{

	public Optional<Role> findByName(String rName);
	
	public List<Role> findAllByOrderByIdAsc();
	
	public Boolean existsByName(String rName);
	
	public List<Role> findAllByIsServiceRole(Boolean isServiceRole);
	
	public List<Role> findByNameNotIn(List<String> name);
	
	public List<Role> findByNameIn(List<String> name);
	
	@Query(nativeQuery = true, value="select r.* from {h-schema}user_roles ur, {h-schema}roles r where ur.role_id=r.id and ur.user_id=:USER_ID")
	public List<Role> getRolesForUser(@Param("USER_ID")Long id);
	
}
