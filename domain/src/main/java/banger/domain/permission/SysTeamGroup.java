package banger.domain.permission;

import java.io.Serializable;
import java.util.Date;

/**
 * 机构类
 */
public class SysTeamGroup implements Serializable {

	private static final long serialVersionUID = -4793308578007819435L;
	// Fields

	private Integer teamGroupId;
	private String teamGroupName;
	private Integer delFlag;
	private Date createDate;
	private Date updateDate;
	private Integer createUser;
	private Integer updateUser;

	// Constructors

	/** default constructor */
	public SysTeamGroup() {
	}

	public Integer getTeamGroupId() {
		return teamGroupId;
	}

	public void setTeamGroupId(Integer teamGroupId) {
		this.teamGroupId = teamGroupId;
	}

	public String getTeamGroupName() {
		return teamGroupName;
	}

	public void setTeamGroupName(String teamGroupName) {
		this.teamGroupName = teamGroupName;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Integer getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}

	public Integer getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(Integer updateUser) {
		this.updateUser = updateUser;
	}
}