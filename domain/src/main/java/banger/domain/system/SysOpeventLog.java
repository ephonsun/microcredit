package banger.domain.system;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * SysOpeventLog entity. @author MyEclipse Persistence Tools
 */

public class SysOpeventLog implements Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -4634802127034821134L;
	private Integer opeventId;
	private Integer opeventUserId;
	private String opeventModule;
	private String opeventAction;
	private Date opeventDate;
	private String opeventIp;

	// Constructors

	/** default constructor */
	public SysOpeventLog() {
	}

	/** full constructor */
	public SysOpeventLog(Integer opeventId, Integer opeventUserId,
			String opeventModule, String opeventAction, Date opeventDate,
			String opeventIp) {
		this.opeventId = opeventId;
		this.opeventUserId = opeventUserId;
		this.opeventModule = opeventModule;
		this.opeventAction = opeventAction;
		this.opeventDate = opeventDate;
		this.opeventIp = opeventIp;
	}

	// Property accessors

	public Integer getOpeventId() {
		return this.opeventId;
	}

	public void setOpeventId(Integer opeventId) {
		this.opeventId = opeventId;
	}

	public Integer getOpeventUserId() {
		return this.opeventUserId;
	}

	public void setOpeventUserId(Integer opeventUserId) {
		this.opeventUserId = opeventUserId;
	}

	public String getOpeventModule() {
		return this.opeventModule;
	}

	public void setOpeventModule(String opeventModule) {
		this.opeventModule = opeventModule;
	}

	public String getOpeventAction() {
		return this.opeventAction;
	}

	public void setOpeventAction(String opeventAction) {
		this.opeventAction = opeventAction;
	}

	public Date getOpeventDate() {
		return this.opeventDate;
	}

	public void setOpeventDate(Date opeventDate) {
		this.opeventDate = opeventDate;
	}

	public String getOpeventIp() {
		return this.opeventIp;
	}

	public void setOpeventIp(String opeventIp) {
		this.opeventIp = opeventIp;
	}


	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SysOpeventLog)) {
			return false;
		}
		SysOpeventLog rhs = (SysOpeventLog) object;
		return new EqualsBuilder().appendSuper(super.equals(object))
				.append(this.opeventUserId, rhs.opeventUserId)
				.append(this.opeventIp, rhs.opeventIp)
				.append(this.opeventId, rhs.opeventId)
				.append(this.opeventDate, rhs.opeventDate)
				.append(this.opeventAction, rhs.opeventAction)
				.append(this.opeventModule, rhs.opeventModule).isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-327350833, -832136131)
				.appendSuper(super.hashCode()).append(this.opeventUserId)
				.append(this.opeventIp).append(this.opeventId)
				.append(this.opeventDate)
				.append(this.opeventAction)
				.append(this.opeventModule).toHashCode();
	}

}