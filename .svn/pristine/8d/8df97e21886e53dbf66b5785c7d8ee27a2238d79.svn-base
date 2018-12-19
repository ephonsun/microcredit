package banger.domain.permission;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * PmsUser entity. @author MyEclipse Persistence Tools
 */

public class PmsUser implements Serializable {

	// Fields

	private static final long serialVersionUID = -1095598137713326208L;
	private Integer userId;
	private Integer userDeptId;
	private String userAccount;
	private String userPassword;
	private String userCode;
	private String userCode2;
	private Integer userPasswordReset;
	private Date userPasswordDate;
	private Date userLoginDate;
	private String userLoginIp;
	private String userName;
	private Integer userIsdel;
	private String userRemark;
	private Integer userStatus;
	private Date userCreateDate;
	private Date userUpdateDate;
	private Integer userCreateUser;
	private Integer userUpdateUser;
    private Integer userPasswordStrength;

	private String userPhoneNumber;//联系方式
	private String userGroupPermit;//数据权限（团队id）(1,2,3)
	private BigDecimal userPassAmount;//审批金额上限（含）

	public String getUserPhoneNumber() {
		return userPhoneNumber;
	}

	public void setUserPhoneNumber(String userPhoneNumber) {
		this.userPhoneNumber = userPhoneNumber;
	}

	public String getUserGroupPermit() {
		return userGroupPermit;
	}

	public void setUserGroupPermit(String userGroupPermit) {
		this.userGroupPermit = userGroupPermit;
	}

	public BigDecimal getUserPassAmount() {
		return userPassAmount;
	}

	public void setUserPassAmount(BigDecimal userPassAmount) {
		this.userPassAmount = userPassAmount;
	}

	// Constructors

	/** default constructor */
	public PmsUser() {
	}

	/** minimal constructor */
	public PmsUser(Integer userId, Integer userDeptId) {
		this.userId = userId;
		this.userDeptId = userDeptId;
	}

	// Property accessors

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getUserDeptId() {
		return this.userDeptId;
	}

	public void setUserDeptId(Integer userDeptId) {
		this.userDeptId = userDeptId;
	}

	public String getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getUserPassword() {
		return this.userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserCode() {
		return this.userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	
	public String getUserCode2() {
		return userCode2;
	}

	public void setUserCode2(String userCode2) {
		this.userCode2 = userCode2;
	}

	public Integer getUserPasswordReset() {
		return this.userPasswordReset;
	}

	public void setUserPasswordReset(Integer userPasswordReset) {
		this.userPasswordReset = userPasswordReset;
	}

	public Date getUserPasswordDate() {
		return this.userPasswordDate;
	}

	public void setUserPasswordDate(Date userPasswordDate) {
		this.userPasswordDate = userPasswordDate;
	}

	public Date getUserLoginDate() {
		return this.userLoginDate;
	}

	public void setUserLoginDate(Date userLoginDate) {
		this.userLoginDate = userLoginDate;
	}

	public String getUserLoginIp() {
		return this.userLoginIp;
	}

	public void setUserLoginIp(String userLoginIp) {
		this.userLoginIp = userLoginIp;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getUserIsdel() {
		return this.userIsdel;
	}

	public void setUserIsdel(Integer userIsdel) {
		this.userIsdel = userIsdel;
	}

	public String getUserRemark() {
		return this.userRemark;
	}

	public void setUserRemark(String userRemark) {
		this.userRemark = userRemark;
	}

	public Integer getUserStatus() {
		return this.userStatus;
	}

	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}

	public Date getUserCreateDate() {
		return this.userCreateDate;
	}

	public void setUserCreateDate(Date userCreateDate) {
		this.userCreateDate = userCreateDate;
	}

	public Date getUserUpdateDate() {
		return this.userUpdateDate;
	}

	public void setUserUpdateDate(Date userUpdateDate) {
		this.userUpdateDate = userUpdateDate;
	}

	public Integer getUserCreateUser() {
		return this.userCreateUser;
	}

	public void setUserCreateUser(Integer userCreateUser) {
		this.userCreateUser = userCreateUser;
	}

	public Integer getUserUpdateUser() {
		return this.userUpdateUser;
	}

	public void setUserUpdateUser(Integer userUpdateUser) {
		this.userUpdateUser = userUpdateUser;
	}

    public Integer getUserPasswordStrength() {
        return userPasswordStrength;
    }

    public void setUserPasswordStrength(Integer userPasswordStrength) {
        this.userPasswordStrength = userPasswordStrength;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PmsUser pmsUser = (PmsUser) o;

        if (userAccount != null ? !userAccount.equals(pmsUser.userAccount) : pmsUser.userAccount != null) return false;
        if (userCode != null ? !userCode.equals(pmsUser.userCode) : pmsUser.userCode != null) return false;
        if (userCode2 != null ? !userCode2.equals(pmsUser.userCode2) : pmsUser.userCode2 != null) return false;
        if (userCreateDate != null ? !userCreateDate.equals(pmsUser.userCreateDate) : pmsUser.userCreateDate != null)
            return false;
        if (userCreateUser != null ? !userCreateUser.equals(pmsUser.userCreateUser) : pmsUser.userCreateUser != null)
            return false;
        if (userDeptId != null ? !userDeptId.equals(pmsUser.userDeptId) : pmsUser.userDeptId != null) return false;
        if (userId != null ? !userId.equals(pmsUser.userId) : pmsUser.userId != null) return false;
        if (userIsdel != null ? !userIsdel.equals(pmsUser.userIsdel) : pmsUser.userIsdel != null) return false;
        if (userLoginDate != null ? !userLoginDate.equals(pmsUser.userLoginDate) : pmsUser.userLoginDate != null)
            return false;
        if (userLoginIp != null ? !userLoginIp.equals(pmsUser.userLoginIp) : pmsUser.userLoginIp != null) return false;
        if (userName != null ? !userName.equals(pmsUser.userName) : pmsUser.userName != null) return false;
        if (userPassword != null ? !userPassword.equals(pmsUser.userPassword) : pmsUser.userPassword != null)
            return false;
        if (userPasswordDate != null ? !userPasswordDate.equals(pmsUser.userPasswordDate) : pmsUser.userPasswordDate != null)
            return false;
        if (userPasswordReset != null ? !userPasswordReset.equals(pmsUser.userPasswordReset) : pmsUser.userPasswordReset != null)
            return false;
        if (userRemark != null ? !userRemark.equals(pmsUser.userRemark) : pmsUser.userRemark != null) return false;
        if (userStatus != null ? !userStatus.equals(pmsUser.userStatus) : pmsUser.userStatus != null) return false;
        if (userUpdateDate != null ? !userUpdateDate.equals(pmsUser.userUpdateDate) : pmsUser.userUpdateDate != null)
            return false;
        if (userUpdateUser != null ? !userUpdateUser.equals(pmsUser.userUpdateUser) : pmsUser.userUpdateUser != null)
            return false;
        if (userPasswordStrength != null ? !userPasswordStrength.equals(pmsUser.userPasswordStrength) : pmsUser.userPasswordStrength != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (userDeptId != null ? userDeptId.hashCode() : 0);
        result = 31 * result + (userAccount != null ? userAccount.hashCode() : 0);
        result = 31 * result + (userPassword != null ? userPassword.hashCode() : 0);
        result = 31 * result + (userCode != null ? userCode.hashCode() : 0);
        result = 31 * result + (userCode2 != null ? userCode2.hashCode() : 0);
        result = 31 * result + (userPasswordReset != null ? userPasswordReset.hashCode() : 0);
        result = 31 * result + (userPasswordDate != null ? userPasswordDate.hashCode() : 0);
        result = 31 * result + (userLoginDate != null ? userLoginDate.hashCode() : 0);
        result = 31 * result + (userLoginIp != null ? userLoginIp.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (userIsdel != null ? userIsdel.hashCode() : 0);
        result = 31 * result + (userRemark != null ? userRemark.hashCode() : 0);
        result = 31 * result + (userStatus != null ? userStatus.hashCode() : 0);
        result = 31 * result + (userCreateDate != null ? userCreateDate.hashCode() : 0);
        result = 31 * result + (userUpdateDate != null ? userUpdateDate.hashCode() : 0);
        result = 31 * result + (userCreateUser != null ? userCreateUser.hashCode() : 0);
        result = 31 * result + (userUpdateUser != null ? userUpdateUser.hashCode() : 0);
        result = 31 * result + (userPasswordStrength != null ? userPasswordStrength.hashCode() : 0);
        return result;
    }
}