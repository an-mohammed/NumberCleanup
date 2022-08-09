package com.ooredoo.nc.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ooredoo.nc.model.Privilege;


@Repository
public interface PrivilegeRepo extends CrudRepository<Privilege, Long>{

	public Optional<Privilege> findByPName(String pName);
	
	public List<Privilege> findAll();
	
	public List<Privilege> findAllByOrderByIdAsc();
	
	public Boolean existsByPName(String pName);
		
}
