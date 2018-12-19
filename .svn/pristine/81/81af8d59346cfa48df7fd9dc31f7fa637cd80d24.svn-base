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
public class PmsDeptArea implements Serializable{
	private static final long serialVersionUID = 5316970625553761804L;
	private Integer pdaId;
	private Integer areaId;
	private Integer areaDeptId;
	public PmsDeptArea() {
	}
	public PmsDeptArea(Integer pdaId,Integer areaId,Integer areaDeptId){
		this.pdaId = pdaId;
		this.areaId = areaId;
		this.areaDeptId = areaDeptId;
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
	public Integer getAreaDeptId() {
		return areaDeptId;
	}
	public void setAreaDeptId(Integer areaDeptId) {
		this.areaDeptId = areaDeptId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((areaDeptId == null) ? 0 : areaDeptId.hashCode());
		result = prime * result + ((areaId == null) ? 0 : areaId.hashCode());
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
		PmsDeptArea other = (PmsDeptArea) obj;
		if (areaDeptId == null) {
			if (other.areaDeptId != null)
				return false;
		} else if (!areaDeptId.equals(other.areaDeptId))
			return false;
		if (areaId == null) {
			if (other.areaId != null)
				return false;
		} else if (!areaId.equals(other.areaId))
			return false;
		if (pdaId == null) {
			if (other.pdaId != null)
				return false;
		} else if (!pdaId.equals(other.pdaId))
			return false;
		return true;
	}
	
}
