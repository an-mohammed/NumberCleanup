package com.ooredoo.nc.service;

import com.ooredoo.nc.dto.SubscriberProfileDetails;
import com.ooredoo.nc.exception.ApplicationException;

public interface ProfileCleanupServiceI {

	public void validateProfile(SubscriberProfileDetails profile, boolean validate) throws ApplicationException;
}
