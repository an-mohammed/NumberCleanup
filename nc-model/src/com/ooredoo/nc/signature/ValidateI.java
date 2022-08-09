package com.ooredoo.nc.signature;

import com.ooredoo.nc.exception.ApplicationException;

public interface ValidateI<T> {

	public void validate(T t) throws ApplicationException;
}
