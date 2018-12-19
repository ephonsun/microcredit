/*
 * banger Inc.
 * ToDo       :This is Class Description...
 * Author     :Administrator
 * Create Date:2014-3-8
 */
package banger.domain.permission;

import java.io.Serializable;

/**
 * @author Administrator
 * @version $Id: PmsDeptArea.java,v 0.1 2014-3-8 下午8:11:12 Administrator Exp $
 */
public class PmsUserArea implements Serializable{
	private static final long serialVersionUID = -4658016311525460792L;
	private Integer pdaId;
	private Integer areaId;
	private Integer areaUserId;
	public PmsUserArea() {
	}
	public PmsUserArea(Integer pdaId,Integer areaId,Integer areaUserId){
		this.pdaId = pdaId;
		this.areaId = areaId;
		this.areaUserId = areaUserId;
	}
	public Integer getPdaId() {
		return pdaId;
	}
	public void setPdaId(Integer pdaId) {
		this.pdaId = pdaId;
	}
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	public Integer getAreaUserId() {
		return areaUserId;
	}
	public void setAreaUserId(Integer areaUserId) {
		this.areaUserId = areaUserId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((areaId == null) ? 0 : areaId.hashCode());
		result = prime * result
				+ ((areaUserId == null) ? 0 : areaUserId.hashCode());
		result = prime * result + ((pdaId == null) ? 0 : pdaId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PmsUserArea other = (PmsUserArea) obj;
		if (areaId == null) {
			if (other.areaId != null)
				return false;
		} else if (!areaId.equals(other.areaId))
			return false;
		if (areaUserId == null) {
			if (other.areaUserId != null)
				return false;
		} else if (!areaUserId.equals(other.areaUserId))
			return false;
		if (pdaId == null) {
			if (other.pdaId != null)
				return false;
		} else if (!pdaId.equals(other.pdaId))
			return false;
		return true;
	}
}
