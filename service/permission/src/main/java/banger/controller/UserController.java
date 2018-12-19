/*
 * banger Inc.
 * ToDo       :This is Class Description...
 * Author     :Administrator
 * Create Date:2014-3-5
 */
package banger.controller;

import banger.common.BaseController;
import banger.common.tools.JsonUtil;
import banger.dao.intf.IDeptDao;
import banger.domain.loan.LoanApplyInfo;
import banger.domain.loan.LoanCurrentAuditStatus;
import banger.domain.permission.PmsRole;
import banger.domain.permission.PmsUserRoleDept;
import banger.domain.permission.PmsUserRoles;
import banger.domain.permission.PmsUser_Query;
import banger.framework.pagesize.IPageList;
import banger.framework.util.Md5Encrypt;
import banger.framework.util.StringUtil;
import banger.framework.web.servlet.ServletContextHolder;
import banger.moduleIntf.ILoanApplyProvider;
import banger.moduleIntf.ILoanAuditProvider;
import banger.moduleIntf.IPermissionModule;
import banger.moduleIntf.ISystemModule;
import banger.service.intf.IRoleService;
import banger.service.intf.IUserRoleDeptService;
import banger.service.intf.IUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author liuj
 * @version $Id: UserAction.java,v 0.1 2014-3-5 下午7:19:02 liuj Exp $
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
	private static final long serialVersionUID = 2358343229263381470L;

	@Autowired
	private IUserService userService;
	@Autowired
	private IRoleService roleService;
	@Autowired
    private IUserRoleDeptService userRoleDeptService;
	@Autowired
    private IPermissionModule module;
	@Autowired
	private ISystemModule systemModuleImpl;
	@Autowired
	private ILoanApplyProvider loanApplyProvider;
	@Autowired
	private ILoanAuditProvider loanAuditProvider;
	@Autowired
	private IDeptDao deptDao;
	private PmsUser_Query user;
	private List<PmsRole> roleList;
	private String roleDeptIds;
	private String userCount;

	public String getRoleDeptIds() {
		return roleDeptIds;
	}

	public void setRoleDeptIds(String roleDeptIds) {
		this.roleDeptIds = roleDeptIds;
	}

	public String getUserCount() {
		return userCount;
	}

	public void setUserCount(String userCount) {
		this.userCount = userCount;
	}


	/**
	 * 得到机构页面
	 * @return
	 */
	@RequestMapping("/getGroupSelectUser")
	@ResponseBody
	public void getGroupSelectUser(){
 		String userName = this.getParameter("userName");
		String exUserIds = this.getParameter("exUserIds");
		String groupId = this.getParameter("groupId");
		String userJson = this.userService.getGroupSelectUser(userName, exUserIds, groupId);
		this.renderText(userJson);
	}


	/**
	 * 得到机构页面
	 * @return
	 */
	@RequestMapping("/getGroupSelectSubUser")
	@ResponseBody
	public void getGroupSelectSubUser(){
		String userName = this.getParameter("userName");
		String exUserIds = this.getParameter("exUserIds");
		String groupId = this.getParameter("groupId");
		String userJson = this.userService.getGroupSelectSubUser(userName, exUserIds, groupId);
		this.renderText(userJson);
	}
	/**
	 * 查询客户列表
	 * @return
	 */
	@RequestMapping("/queryDeptUserList")
	@ResponseBody
	public void queryDeptUserList(){
		boolean isInclude = false;
		String userAccount = this.getParameter("userAccount");
		String userName = this.getParameter("userName");
		String includeSub = this.getParameter("includeSub");
        String userStatus = this.getParameter("userStatus");
        String deptId = this.getParameter("deptId");
        Integer userState = StringUtil.isNotEmpty(userStatus)?Integer.parseInt(userStatus):null;
		if(includeSub!=null&&includeSub.equals("1")){
			isInclude = true;
		}
		IPageList<PmsUser_Query> userPageList = userService.queryDeptUserList(this.getPage(),Integer.valueOf(deptId),userAccount,userName,userState,isInclude);
		this.setListOptionItemEnable(userPageList);
		renderText(JsonUtil.toJson(userPageList, "userId", "userAccount,userName,roleNames,userStatueName,userStatus,deptName,optionFlag,userLoginDate","yyyy-MM-dd HH:mm:ss").toString());
	}
	
	private void setListOptionItemEnable(List<PmsUser_Query> list){
		String userRoleIdsStr = this.getLoginInfo().getRoleIds();
		if (userRoleIdsStr != null) {
			String[] userRoleIds = userRoleIdsStr.split(",");

			for(String userRoleId :userRoleIds){
				for(PmsUser_Query queryUser : list){
					if (queryUser.getRoleIds() != null) {
						String[] roleIds = queryUser.getRoleIds().split(",");
						for(String roleId : roleIds){
							if(userRoleId.equals("2") ){//登录角色为机构管理员，人员维护角色也为机构管理员
								if(roleId.equals("2")) {
									queryUser.setOptionFlag("0");
								}else{
									queryUser.setOptionFlag("1");
								}
							}else if(userRoleId.equals("5")){//登录角色为支行业务管理员
								if(roleId.equals("3")||roleId.equals("6")){
									queryUser.setOptionFlag("1");
								}else{
									queryUser.setOptionFlag("0");
								}

							}else{
								queryUser.setOptionFlag("1");
							}

						}
					}
				}
			}
		}
	}
	
	/**
	 * 跳转到新增或编辑用户页面
	 * @return
	 */
	@RequestMapping("/initPmsUserPage")
	public String initPmsUserPage(HttpServletRequest request){
		String userId = this.getParameter("userId");
		if(userId!=null){
			user = userService.getUserById(Integer.parseInt(userId.toString()));
            List<PmsRole> userRoles = roleService.getRolesByUserId(user.getUserId());
            //处理角色关联的机构ID
            List<PmsUserRoleDept> rDeptList = userRoleDeptService.getPmsUserRoleDeptList(user.getUserId());
            if(rDeptList.size()>0){
                String deptIds = "";
                for (int i = 0; i < userRoles.size(); i++){
                    PmsRole role = userRoles.get(i);
                    deptIds = "";
                    for (PmsUserRoleDept rDept : rDeptList) {
                        if(role.getRoleId()==rDept.getRoleId()){
                            if(!StringUtil.isNullOrEmpty(deptIds)){
                                deptIds = deptIds + "," + rDept.getDeptId();
                            }else{
                                deptIds = rDept.getDeptId().toString();
                            }
                        }
                    }
                    role.setRoleDeptIds(deptIds);
                    userRoles.set(i, role);
                }
            }
            request.setAttribute("userRoles", userRoles);
            request.setAttribute("userId",userId);
            request.setAttribute("user",user);
		}else{
			request.setAttribute("userId","-1");
			if(deptDao.getDeptById(Integer.parseInt(getParameter("user.userDeptId")))!=null) {
				request.setAttribute("dename", deptDao.getDeptById(Integer.parseInt(getParameter("user.userDeptId"))).getDeptName());
			}
			request.setAttribute("depId",getParameter("user.userDeptId"));
        }
		List<PmsRole> roleList = roleService.getRolesByType(0);
		List<PmsRole> checkRoleList = roleService.getRolesByType(1);
		this.setAttribute("roleList", roleList);
		this.setAttribute("checkRoleList", checkRoleList);
		return "/permission/vm/userAdd";
	}
	
	/**
	 * 筛选角色list
	 * 
	 * @param roleList
	 * @return
	 */
	private List<PmsRole> filterRoleList(List<PmsRole> roleList) {
		List<PmsRole> filterList = new ArrayList<PmsRole>();
		String roleIdsStr = this.getLoginInfo().getRoleIds();
		String[] roleIds = roleIdsStr.split(",");
		Set<String> roleIdSet = new HashSet<String>();
		for (String roleId : roleIds)
			roleIdSet.add(roleId);
		for (PmsRole role : roleList) {
			if (role.getRoleId() == 2 || role.getRoleId() == 8) {// 2.机构管理员 8.科技
				if (roleIdSet.contains("1"))
					filterList.add(role);
			} else if (role.getRoleId() == 3 || role.getRoleId() == 6) {// 3.业务主管
																		// 6.理财经理/柜员
				if (roleIdSet.contains("1") || roleIdSet.contains("2")
						|| roleIdSet.contains("5"))
					filterList.add(role);
			} else if (role.getRoleId() == 4 || role.getRoleId() == 5
					|| role.getRoleId() == 7) {// 4.分行业务管理员(产品部) 5.支行业务管理员
												// 7.分行业务管理员(职能部)
				if (roleIdSet.contains("1") || roleIdSet.contains("2"))
					filterList.add(role);
			} else {
				if (!roleIdSet.contains("5"))
					filterList.add(role);
			}
		}
		return filterList;
	}

	/**
	 * 新增或编辑用户
	 * @return
	 */
	@RequestMapping("/updatePmsUser")
	@ResponseBody
	public void updatePmsUser(HttpServletRequest request){

	    String roleIds = this.getParameter("roleIds");
	    Integer loginUserId = this.getLoginInfo().getUserId();

		user = new PmsUser_Query();
		String userId = this.getParameter("user.userId");
		if(StringUtils.isNotBlank(userId)&&StringUtils.isNumeric(userId)){
			user.setUserId(Integer.valueOf(userId));
		}
		String userDeptId = this.getParameter("user.userDeptId");
		if(StringUtils.isNotBlank(userDeptId)&&StringUtils.isNumeric(userDeptId)){
			user.setUserDeptId(Integer.valueOf(userDeptId));
		}
		String userName = this.getParameter("user.userName");
		if(StringUtils.isNotBlank(userName)){
			user.setUserName(userName);
		}
		String userAccount = this.getParameter("user.userAccount");
		if(StringUtils.isNotBlank(userAccount)){
			user.setUserAccount(userAccount);
		}
		String userStatus = this.getParameter("user.userStatus");
		if(StringUtils.isNotBlank(userStatus)&&StringUtils.isNumeric(userStatus)){
			user.setUserStatus(Integer.valueOf(userStatus));
		}
		String userRemark = this.getParameter("user.userRemark");
		if(StringUtils.isNotBlank(userRemark)){
			user.setUserRemark(userRemark);
		}
		String userPhoneNumber = this.getParameter("user.userPhoneNumber");
		if(StringUtils.isNotBlank(userPhoneNumber)){
			user.setUserPhoneNumber(userPhoneNumber);
		}
		String userGroupPermit = this.getParameter("user.userGroupPermit");
		user.setUserGroupPermit(userGroupPermit);
		String userPassAmount = this.getParameter("user.userPassAmount");
		if(StringUtils.isNotBlank(userPassAmount)){
			user.setUserPassAmount(new BigDecimal(userPassAmount));
		}else{
			user.setUserPassAmount(new BigDecimal(0));
		}

		//处理用户新增和编辑业务
		if(user.getUserId()==null){
			//新增用户
			userService.addUser(user, loginUserId);
			userService.updateUserRole(user.getUserId(), roleIds, loginUserId);
			addSystemLog(1);
		}else{
			//编辑用户
			userService.editUser(user, loginUserId);
			userService.updateUserRole(user.getUserId(), roleIds, loginUserId);
			addSystemLog(2);
		}
		this.renderText(true, "", "");
	}
	/**
	 * 删除用户
	 * @return
	 */
	@RequestMapping("/deleteUser")
	public void deleteUser(HttpServletRequest request){
        String userId = this.getParameter("userId");
        Map<String,Object> condition = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(userId)&&StringUtils.isNumeric(userId)){

			//查询用户是否为 客户经理、审批人员
			List<PmsUserRoles> roleList = roleService.getPmsRoleListByUserId(Integer.valueOf(userId));
			for (PmsUserRoles pmsRole : roleList) {
				//如果是客户经理
				if(pmsRole.getPurRoleId() == 4){
					//查看是否有贷款
					condition.put("loanBelongId",userId);
					List<LoanApplyInfo> loanList = loanApplyProvider.queryApplyInfoList(condition);
					if(loanList != null && loanList.size() > 0){
						this.renderText(false, "", "该客户经理拥有贷款，不能删除");
						return;
					}
				}else if(pmsRole.getPurRoleId() == 5){
					//如果是审批人员
					condition.put("auditResult",0);//待审
					condition.put("isValid",1);//有效
					condition.put("auditUserId",userId);//审核人
					List<LoanCurrentAuditStatus> loanCurrentAuditStatuses = loanAuditProvider.queryLoanAuditByCondition(condition);
					if(loanCurrentAuditStatuses != null && loanCurrentAuditStatuses.size() > 0){
						this.renderText(false, "", "该审核人拥有待审批贷款，不能删除");
						return;
					}
				}

			}
			userService.deleteUserById(Integer.valueOf(userId));
			addSystemLog(3);
			this.renderText(true, "", "删除成功");
		}
	}
	
	/**
	 * 获得多选下拉单选项
	 */
	public String getManagementArea(){
		return null;
	}
	
	/**
	 * 重置密码
	 * @return
	 */
	@RequestMapping("/resetPmsUserPasswd")
	@ResponseBody
	public String resetPmsUserPasswd(HttpServletRequest request){
		String userId = this.getParameter("userId");
		if(StringUtils.isNotBlank(userId) && StringUtils.isNumeric(userId)){
			userService.resetPmsUserPassword(Integer.parseInt(userId), this.getLoginInfo().getUserId());
			addSystemLog(5);
		}
		return null;
	}

	/**
	 *  检查用户名是否重复
	 * @return
	 */
	@RequestMapping("/checkUserAccountIsRepeat")
	@ResponseBody
	public String checkUserAccountIsRepeat(HttpServletRequest request){
		user = new PmsUser_Query();
		String userId = this.getParameter("user.userId");
		if(StringUtils.isNotBlank(userId)&&StringUtils.isNumeric(userId)){
			user.setUserId(Integer.valueOf(userId));
		}
		String userDeptId = this.getParameter("user.userDeptId");
		if(StringUtils.isNotBlank(userDeptId)&&StringUtils.isNumeric(userDeptId)){
			user.setUserDeptId(Integer.valueOf(userDeptId));
		}
		String userName = this.getParameter("user.userName");
		if(StringUtils.isNotBlank(userName)){
			user.setUserName(userName);
		}
		String userAccount = this.getParameter("user.userAccount");
		if(StringUtils.isNotBlank(userAccount)){
			user.setUserAccount(userAccount);
		}
		String userStatus = this.getParameter("user.userStatus");
		if(StringUtils.isNotBlank(userStatus)&&StringUtils.isNumeric(userStatus)){
			user.setUserStatus(Integer.valueOf(userStatus));
		}
		String userRemark = this.getParameter("user.userRemark");
		if(StringUtils.isNotBlank(userRemark)){
			user.setUserRemark(userRemark);
		}
		String userPhoneNumber = this.getParameter("user.userPhoneNumber");
		if(StringUtils.isNotBlank(userPhoneNumber)){
			user.setUserPhoneNumber(userPhoneNumber);
		}
		String userGroupPermit = this.getParameter("user.userGroupPermit");
		if(StringUtils.isNotBlank(userGroupPermit)){
			user.setUserGroupPermit(userGroupPermit);
		}
		String userPassAmount = this.getParameter("user.userPassAmount");
		if(StringUtils.isNotBlank(userPassAmount)){
			user.setUserPassAmount(new BigDecimal(userPassAmount));
		}
		if(userService.checkUserAccountIdExist(user)){
			renderText(false, "userAcccountRepeat", "已存在相同的用户名，请重新输入");
		}
//		else{
//			renderText("noExist");
//		}
		return null;
	}

	/**
	 * 检查人员编号是否重复
	 * @return
	 */
	@RequestMapping("/checkUserCodeIsRepeat")
	@ResponseBody
	public String checkUserCodeIsRepeat(){
		if(userService.checkUserCodeIsExist(user)){
			renderText(false,"userCodeRepeat","已存在相同的人员编号，请重新输入");
		}else{
			this.renderText(true, "", "");
		}
		return null;
	}

	/**
	 * 修改密码
	 * @return
	 */
	@RequestMapping("/changeUserPassword")
	@ResponseBody
	public void changeUserPassword(HttpServletRequest request){
		Integer userId = this.getLoginInfo().getUserId();
		String oldPassword = this.getParameter("oldPassword");
		String newPassword = this.getParameter("password");
        String passwordStr = this.getParameter("passwordStr");
		user = userService.getUserById(userId);
//		if (StringUtils.isNotBlank(oldPassword)) {
//			oldPassword = Base64Util.getFromBase64(oldPassword);
//		}
//		if (StringUtils.isNotBlank(newPassword)) {
//			newPassword = Base64Util.getFromBase64(newPassword);
//		}
		if(user.getUserPassword().equals(oldPassword)){
			if(oldPassword.equals(newPassword)){
				renderText("oldAndNewTheSame");
			}else{
				user.setUserPassword(newPassword);
				user.setUserPasswordStrength(StringUtil.isNullOrEmpty(passwordStr) ? 1 : Integer.parseInt(passwordStr));
				userService.changeUserPassword(user);
				addSystemLog(4);
			}
		}else{
			renderText("errorPasswd");
		}
	}

	/**
	 * 密码过期页面修改密码
	 * @return
	 */
	@RequestMapping("/changeUserPass")
	@ResponseBody
	public String changeUserPass(HttpServletRequest request){
		Integer userId = this.getLoginInfo().getUserId();
		String oldPassword = this.getParameter("oldPassword");
		String newPassword = this.getParameter("password");
		String passwordStr = this.getParameter("passwordStr");
		user = userService.getUserById(userId);
			if(user.getUserPassword().equals(Md5Encrypt.encrypt(newPassword)))
			{
				renderText("oldAndNewTheSame");
			}else{
				user.setUserPassword(Md5Encrypt.encrypt(newPassword));
				user.setUserPasswordStrength(StringUtil.isNullOrEmpty(passwordStr) ? 1 : Integer.parseInt(passwordStr));
				userService.changeUserPassword(user);
				addSystemLog(4);
			}
		return null;
	}

	//修改密码页面
	@RequestMapping("/toChangePasswordPage")
	public String toChangePasswordPage(){
		return "/permission/vm/changePassword";
	}

    //启用用户
	@RequestMapping("/enableUser")
	@ResponseBody
    public String enableUser(){
        return updateIsActivedTag(1);
    }
    //停用用户
	@RequestMapping("disableUser")
	@ResponseBody
	public String disableUser(){
        return updateIsActivedTag(0);
    }

    private String updateIsActivedTag(Integer tag){
        String id = ServletContextHolder.getRequest().getParameter("userId");
        this.userService.updateIsActivedTag(Integer.parseInt(id),tag);
        this.renderText(SUCCESS);
        return null;
    }
	//初始化用户信息页面
    @RequestMapping("/initPmsUserInfoPage")   
	public String initPmsUserInfoPage(){
		user = userService.getUserInfoById(this.getLoginInfo().getUserId());
		setAttribute("user",user);
		return "/permission/vm/userInfo";
	}

    @RequestMapping("/initImportUser")  
    public String initImportUser(){
        return "/permission/vm/importUser";
    }

	/**
	 * 添加系统日志
	 *
	 * @param type
	 */
	private void addSystemLog(int type) {
		String opeVentAction = null;
		switch (type) {
			case 1:
				opeVentAction = "新建用户";
				break;
			case 2:
				opeVentAction = "修改用户信息";
				break;
			case 3:
				opeVentAction = "删除用户";
				break;
			case 4:
				opeVentAction = "修改密码";
				break;
			case 5:
				opeVentAction = "重置密码";
				break;
			default:
				break;
		}
		// 新增系统日志
		// insert系统日志表   模块名称、操作对象名称、操作者userId、操作者ip地址
		this.systemModuleImpl.addSysOpeventLog("权限模块", opeVentAction, this.getLoginInfo().getUserId(), this.getLoginInfo().getIp());
	}
	//日期方法
	private String getNowTimeAndWeek(){
		Calendar ca = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
		String[] week = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
		int i = ca.get(Calendar.DAY_OF_WEEK);
		return  df.format(ca.getTime())+"     "+ week[i-1];
	}
}
