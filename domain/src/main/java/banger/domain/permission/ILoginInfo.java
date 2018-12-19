package banger.domain.permission;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.List;

public interface ILoginInfo {
	
	/**
	 * 得到登入用户ID
	 * @return
	 */
	Integer getUserId();
	
	/**
	 * 得到登入用户机构
	 * @return
	 */
	Integer getDeptId();
	
	/**
	 * 得到登入用户帐号
	 * @return
	 */
	String getAccount();
	
	/**
	 * 得到登入用户姓名
	 * @return
	 */
	String getUserName();
	
	/**
	 * 得到登入
	 * @return
	 */
	String getDeptName();
	
	/**
	 * 得到登入用户IP
	 * @return
	 */
	String getIp();
	
	/**
	 * 得到用户角色Id集合
	 * @return
	 */
	String getRoleIds();
	
	/**
	 * 得到用户角色名集合
	 * @return
	 */
	List<String> getRoleNames();

	/**
	 * 得到用户数据权限集合
	 * @return
	 */
	String getDataPermitCodes();
	
	int getIsTrayInstalled();
	
	/**
	 * 的团队管理角色
	 * @return
	 */
	int getTeamRoleId();

	public String getUserGroupPermit();//数据权限（团队id）

	public BigDecimal getUserPassAmount();//审批金额上限（含）
	
	public Integer getTeamGroupId();//团队id，没有团队返回null

	/**
	 * 根据角色获取有数据权限的团队id，客户经理只获取数据权限，其他角色的数据权限是配置的数据权限加上本团队的数据
	 * @return
	 */
	public String getTeamGroupIdByRole();

	/**
	 * 根据角色获取有数据权限的团队id，客户经理只获取数据权限，其他角色的数据权限是配置的数据权限加上本团队的数据
	 * @return
	 */
	public String getTeamGroupIdByRole(boolean isLoan);
		
}
