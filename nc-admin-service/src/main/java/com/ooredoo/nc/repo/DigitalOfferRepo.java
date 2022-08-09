package com.ooredoo.nc.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ooredoo.nc.model.DigitalOffer;


@Repository
public interface DigitalOfferRepo extends CrudRepository<DigitalOffer, Long>{

	public List<DigitalOffer> findAll();
	public List<DigitalOffer> findByEnabled(Boolean enabled);
	public Optional<DigitalOffer> findByOfferId(Long offerId);
	
}
