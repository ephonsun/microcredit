package banger.domain.permission;

import banger.domain.enumerate.GroupRolesEnum;
import banger.framework.util.StringUtil;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public class WebLoginInfo implements ILoginInfo,IFuncPermit,Serializable {
	private static final long serialVersionUID = -3104497334502106207L;
	private Integer userId;
	private String userName;
	private Integer deptId;
	private String deptName;
	private String account;
	private String roleIds;
	private List<String> roleNames;
	private String ip;
	private String dataPermitCodes;
	private Set<String> funcCodes;			//操作权限
	private Set<String> menuCodes;
	private int isTrayInstalled;
	private int teamRoleId;
	private String userGroupPermit;//数据权限（团队id）
	private BigDecimal userPassAmount;//审批金额上限（含）
	private Integer teamGroupId;//所在团队的团队id
	private String referer;				//客户端请求地址

	public String getUserGroupPermit() {
		return userGroupPermit;
	}

	public BigDecimal getUserPassAmount() {
		return userPassAmount;
	}

	public void setUserGroupPermit(String userGroupPermit) {
		this.userGroupPermit = userGroupPermit;
	}

	public void setUserPassAmount(BigDecimal userPassAmount) {
		this.userPassAmount = userPassAmount;
	}

	public Integer getUserId() {
		return userId;
	}
	
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public Integer getDeptId() {
		return deptId;
	}
	
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}
	
	public String getDeptName() {
		return deptName;
	}
	
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	public String getAccount() {
		return account;
	}
	
	public void setAccount(String account) {
		this.account = account;
	}
	
	public String getRoleIds() {
		return roleIds;
	}
	
	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
	
	public List<String> getRoleNames() {
		return roleNames;
	}
	
	public void setRoleNames(List<String> roleNames) {
		this.roleNames = roleNames;
	}
	
	public String getIp() {
		return ip;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getDataPermitCodes() {
		return dataPermitCodes;
	}

	public void setDataPermitCodes(String dataPermitCodes) {
		this.dataPermitCodes = dataPermitCodes;
	}

	public Set<String> getFuncCodes() {
		return funcCodes;
	}

	public void setFuncCodes(Set<String> funcCodes) {
		this.funcCodes = funcCodes;
	}
	
	public Set<String> getMenuCodes() {
		return menuCodes;
	}

	public void setMenuCodes(Set<String> menuCodes) {
		this.menuCodes = menuCodes;
	}

	@Override
	public boolean hasFuncCode(String funcCode) {
		if(funcCodes!=null && StringUtil.isNotEmpty(funcCode)){
			String[] funcs = funcCode.split("\\|");
			for(String func : funcs){
				if(funcCodes.contains(func))
					return true;
			}
		}
		return false;
	}
	
	public boolean hasMenuCode(String menuCode){
		if(menuCodes!=null && StringUtil.isNotEmpty(menuCode)){
			String[] menus = menuCode.split("\\|");
			for(String menu : menus){
				if(menuCodes.contains(menu))
					return true;
			}
		}
		return false;
	}

	public int getIsTrayInstalled() {
		return isTrayInstalled;
	}

	public void setIsTrayInstalled(int isTrayInstalled) {
		this.isTrayInstalled = isTrayInstalled;
	}
	
	public void setTeamRoleId(int teamRoleId) {
		this.teamRoleId = teamRoleId;
	}

	@Override
	public int getTeamRoleId() {
		return teamRoleId;
	}

	@Override
	public Integer getTeamGroupId() {
		return this.teamGroupId;
	}

	public void setTeamGroupId(Integer teamGroupId) {
		this.teamGroupId = teamGroupId;
	}

	public String getTeamGroupIdByRole(){
		return  getTeamGroupIdByRole(true);
	}

	/**
	 * 贷款列表默认客户经理没有整个团队的数据权限，其他的默认有本团队的数据权限
	 * @param isLoan
	 * @return
	 */
	public String getTeamGroupIdByRole(boolean isLoan){
		String groupIds = "";
		String teamGroupIds = getUserGroupPermit();
		String roleIds = getRoleIds();
		String manageRoleId = String.valueOf(GroupRolesEnum.MANAGER.getRoleId());
		Integer userTeamId = getTeamGroupId();
		boolean hasTeam = userTeamId != null;
		if (roleIds != null) {
			boolean flag = !(roleIds.equals(manageRoleId) || roleIds.contains(","+manageRoleId+",")	|| roleIds.endsWith(","+manageRoleId)	|| roleIds.startsWith(manageRoleId + ","));
			if (StringUtils.isNotBlank(teamGroupIds)) {
				groupIds = teamGroupIds;
				if (isLoan) {
					if (hasTeam && flag && userTeamId != null)
						groupIds = addThisTeamId(groupIds, userTeamId);
				} else {
					if (getTeamGroupId()!=null && userTeamId != null)
						groupIds = addThisTeamId(groupIds, userTeamId);
				}
			} else {
				if (isLoan) {
					if (flag){
						groupIds = userTeamId == null ? "" : String.valueOf(userTeamId);
					}
				} else {
					groupIds = userTeamId == null ? "" : String.valueOf(userTeamId);
				}
			}
		}
		return groupIds;
	}


	private String addThisTeamId(String groupIds, Integer userTeamId){
		boolean hasThisTeamId = false;
		String [] groupIdList = groupIds.split(",");
		for (String s : groupIdList) {
			if (s.equals(userTeamId)) {
				hasThisTeamId = true;
				break;
			}
		}
		if (!hasThisTeamId)
			groupIds = groupIds + "," + String.valueOf(userTeamId);
		return groupIds;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}
	
}
