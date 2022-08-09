package com.ooredoo.nc.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the NC_GRP_MSISDN database table.
 * 
 */
@Embeddable
public class NcGrpMsisdnPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="GRP_ID", insertable=false, updatable=false)
	private long grpId;

	@Column(name="NP_ID", insertable=false, updatable=false)
	private long npId;

	public NcGrpMsisdnPK() {
	}
	public long getGrpId() {
		return this.grpId;
	}
	public void setGrpId(long grpId) {
		this.grpId = grpId;
	}
	public long getNpId() {
		return this.npId;
	}
	public void setNpId(long npId) {
		this.npId = npId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof NcGrpMsisdnPK)) {
			return false;
		}
		NcGrpMsisdnPK castOther = (NcGrpMsisdnPK)other;
		return 
			(this.grpId == castOther.grpId)
			&& (this.npId == castOther.npId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.grpId ^ (this.grpId >>> 32)));
		hash = hash * prime + ((int) (this.npId ^ (this.npId >>> 32)));
		
		return hash;
	}
}