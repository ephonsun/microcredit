/*
 * banger Inc.
 * ToDo       :This is Class Description...
 * Author     :Administrator
 * Create Date:2014-3-6
 */
package banger.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import banger.common.BaseController;
import banger.common.tools.JsonUtil;
import banger.domain.permission.PmsDataPermit;
import banger.domain.permission.PmsRole;
import banger.framework.pagesize.IPageList;
import banger.moduleIntf.ISystemModule;
import banger.service.intf.IFuncTreeService;
import banger.service.intf.IMenuTreeService;
import banger.service.intf.IRoleService;

/**
 * @author liuj
 * @version $Id: RoleAction.java,v 0.1 2014-3-6 下午2:34:43 liuj Exp $
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {
	private static final long serialVersionUID = 8366773354994176263L;
	
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IMenuTreeService menuTreeService;
	@Autowired
	private IFuncTreeService funcTreeService;
	@Autowired
	private ISystemModule systemModuleImpl;

	
    @RequestMapping("/getRolePage")
	public String getRolePage(){
		return "/permission/vm/roleList";
	}
    
	/**
	 * 查询角色列表
	 * @return
	 */
    @RequestMapping("/queryRoleList")
    @ResponseBody
	public void queryRoleList(HttpServletRequest request){
		String roleName = this.getParameter("roleName");
		IPageList<PmsRole> RolePageList = roleService.queryRoleList(this.getPage(),roleName);
		renderText(JsonUtil.toJson(RolePageList, "roleId", "roleName,roleRemark,roleType").toString());
	}
    
    
	/**
	 * 跳转到新增或编辑角色页面
	 * @return
	 */
    @RequestMapping("/initPmsRolePage")
	public String initPmsRolePage(HttpServletRequest request,String roleId,String flag){
		String menuList,funcList;
		List<PmsDataPermit> dataPermits = roleService.getAllDataPermits();
		if(roleId!=null){
			//编辑角色
			PmsRole role = roleService.getRoleById(Integer.parseInt(roleId));
			//通过roleId得到菜单权限
			List<String> menuIds = roleService.getMenuIdsByRoleId(Integer.parseInt(roleId));
			menuList = menuTreeService.getRoleMenuTree(menuIds);
			//通过roleId得到功能权限
			List<String> funcIds = roleService.getFuncIdsByRoleId(Integer.parseInt(roleId));
			funcList = funcTreeService.getRoleFuncTree(funcIds);
//			for(String s:menuIds){
//				if("intoPiecesManage".equals(s)||!"loanUse".equals(s)||!"intoPiecesInfo".equals(s)||!"trialRule".equals(s)||!"interfaceHistoryList".equals(s)||!"msgHistoryList".equals(s)){
//
//				}
//			}
			request.setAttribute("role", role);
			request.setAttribute("permitIds", roleService.getDataPermitByRoleId(Integer.parseInt(roleId)));
		}else{
			menuList = menuTreeService.getRoleMenuTree();
			funcList = funcTreeService.getRoleFuncTree();
		}
		request.setAttribute("menuList", menuList);
		request.setAttribute("funcList", funcList);
		request.setAttribute("dataPermits", dataPermits);
		request.setAttribute("flag", flag);
		return "/permission/vm/roleSaveDetail";
	}
	
	/**
	 * 跳转到查看角色页面
	 * @return
	 */
    @RequestMapping("/initRoleDetailPage")
	public String initRoleDetailPage(HttpServletRequest request,String roleId,String flag){
		String menuList,funcList;
		List<PmsDataPermit> dataPermits = roleService.getAllDataPermits();
		if(roleId!=null){
			//编辑角色
			PmsRole role = roleService.getRoleById(Integer.parseInt(roleId));
			//通过roleId得到菜单权限
			List<String> menuIds = roleService.getMenuIdsByRoleId(Integer.parseInt(roleId));
			menuList = menuTreeService.getRoleDisMenuTree(menuIds);
			//通过roleId得到功能权限
			List<String> funcIds = roleService.getFuncIdsByRoleId(Integer.parseInt(roleId));
			funcList = funcTreeService.getRoleDisFuncTree(funcIds);
			
			request.setAttribute("role", role);
			request.setAttribute("permitIds", roleService.getDataPermitByRoleId(Integer.parseInt(roleId)));
		}else{
			menuList = menuTreeService.getRoleMenuTree();
			funcList = funcTreeService.getRoleFuncTree();
		}
		request.setAttribute("menuList", menuList);
		request.setAttribute("funcList", funcList);
		request.setAttribute("dataPermits", dataPermits);
		request.setAttribute("flag", flag);
		return "/permission/vm/roleDetail";
	}
    
    @RequestMapping("/addOrUpdateRole")
    @ResponseBody
	public void addOrUpdateRole(HttpServletRequest request,PmsRole role,String canApproval){
    	Integer roleCanApproval = 0;
    	if(("on").equals(canApproval)){
    		roleCanApproval = 1;
    	}
    	role.setRoleCanApproval(roleCanApproval);
		String menuIds = this.getParameter("menuIds");
		String funcIds = this.getParameter("funcIds");
		String permitIds = this.getParameter("permitIds");
	    Integer loginUserId = this.getLoginInfo().getUserId();
	    if(role.getRoleId()==null){
	    	//新增role
	    	roleService.addRole(role, loginUserId);
	    	roleService.updateRoleMenus(role.getRoleId(), menuIds, loginUserId);
	    	roleService.updateRoleFuncs(role.getRoleId(), funcIds, loginUserId);
			roleService.updateDataPermits(role.getRoleId(),permitIds,loginUserId);
			addSystemLog(1);
	    }else{
	    	//修改role
	    	roleService.editRole(role,loginUserId);
	    	roleService.updateRoleMenus(role.getRoleId(), menuIds, loginUserId);
	    	roleService.updateRoleFuncs(role.getRoleId(), funcIds, loginUserId);
			roleService.updateDataPermits(role.getRoleId(),permitIds,loginUserId);
			addSystemLog(2);
	    }
	}
    
    
	/**
	 * 删除角色
	 */
	@RequestMapping("/deleteRoleById")
	@ResponseBody
	public void deleteRoleById(PmsRole role){
		roleService.deleteRoleById(role.getRoleId());
		addSystemLog(3);
	}

	/**
	 * 校验角色名称是否存在
	 * @return
	 */
    @RequestMapping("/checkRoleIsRepeat")
    @ResponseBody
	public void checkRoleIsRepeat(PmsRole role){
		if(roleService.checkRoleIsRepeat(role)){
			renderText(false,"roleName","已存在相同的角色名，请重新输入");
		}else{
			renderText(true,"","");
		}
	}
    
    
	/**
	 * 校验角色是否可以删除
 	*/
    @RequestMapping("/checkRoleCanDelete")
    @ResponseBody
	public void checkRoleCanDelete(PmsRole role){
		if(!roleService.checkRoleCanDelete(role.getRoleId())){
			renderText(false,"cannotDelete","角色“"+ role.getRoleName()+"”已被人员使用，不能删除");
		}else{
			renderText(true,"","");
		}
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
				opeVentAction = "新增角色";
				break;
			case 2:
				opeVentAction = "修改角色";
				break;
			case 3:
				opeVentAction = "删除角色";
				break;
			default:
				break;
		}
		// 新增系统日志
		// insert系统日志表   模块名称、操作对象名称、操作者userId、操作者ip地址
		this.systemModuleImpl.addSysOpeventLog("权限模块", opeVentAction, this.getLoginInfo().getUserId(), this.getLoginInfo().getIp());
	}
}
