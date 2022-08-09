package com.ooredoo.nc.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the NC_USER_GRP database table.
 * 
 */
@Embeddable
public class NcUserGrpPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="USER_ID", insertable=false, updatable=false)
	private long userId;

	@Column(name="GRP_ID", insertable=false, updatable=false)
	private long grpId;

	public NcUserGrpPK() {
	}
	public long getUserId() {
		return this.userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getGrpId() {
		return this.grpId;
	}
	public void setGrpId(long grpId) {
		this.grpId = grpId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof NcUserGrpPK)) {
			return false;
		}
		NcUserGrpPK castOther = (NcUserGrpPK)other;
		return 
			(this.userId == castOther.userId)
			&& (this.grpId == castOther.grpId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.userId ^ (this.userId >>> 32)));
		hash = hash * prime + ((int) (this.grpId ^ (this.grpId >>> 32)));
		
		return hash;
	}
}