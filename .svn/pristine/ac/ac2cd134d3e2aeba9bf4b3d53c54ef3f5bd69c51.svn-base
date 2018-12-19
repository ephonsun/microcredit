package banger.service.impl;

import banger.dao.intf.*;
import banger.domain.permission.*;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.util.ArrayUtil;
import banger.framework.util.StringUtil;
import banger.service.intf.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoleService implements IRoleService {

	@Autowired
	private IRoleDao roleDao;

	@Autowired
	private IMenuDao menuDao;

	@Autowired
	private IFuncDao funcDao;

	@Autowired
	private IDataPermitDao dataPermitDao;

	@Autowired
	private IRoleMenuDao roleMenuDao;

	@Autowired
	private IRoleFuncDao roleFuncDao;

	@Autowired
	private IRoleDataPermitDao roleDataPermitDao;
	
	/**
	 * 得到用户所有角色
	 * @return
	 */
	public List<PmsRole> getUserAllRoles(){
		return this.roleDao.getUserAllRoles();
	}
	
	/**
	 * 得到用户所有角色
	 * @return
	 */
	public List<PmsRole> getRolesByUserId(Integer userId){
		return this.roleDao.getRolesByUserId(userId);
	}
	
	/**
	 * 通过主键取角色
	 * @param roleId
	 */
	public PmsRole getRoleById(Integer roleId){
		return this.roleDao.getRoleById(roleId);
	}
	
	/**
	 * 查询角色菜单
	 * @param roleId
	 * @return
	 */
	public List<PmsMenu> getMenusByRoleId(Integer roleId){
		return this.menuDao.getMenusByRoleId(roleId);
	}
	
	/**
	 * 查询角色功能
	 * @param roleId
	 * @return
	 */
	public List<PmsFunc> getFuncsByRoleId(Integer roleId){
		return this.funcDao.getFuncsByRoleId(roleId);
	}
	
	/**
	 * 查询角色菜单Id集合
	 * @param roleId
	 * @return
	 */
	public List<String> getMenuIdsByRoleId(Integer roleId){
		return this.roleMenuDao.getMenuIdsByRoleId(roleId);
	}
	
	/**
	 * 查询角色功能Id集合
	 * @param roleId
	 * @return
	 */
	public List<String> getFuncIdsByRoleId(Integer roleId){
		return this.roleFuncDao.getFuncIdsByRoleId(roleId);
	}
	
	/**
	 * 添加角色
	 * @param role
	 * @param loginUserId
	 */
	public void addRole(PmsRole role,Integer loginUserId){
		Integer roleSort = this.getSortNo();
		role.setRoleSort(roleSort);
		role.setRoleCreateUser(loginUserId);
		role.setRoleCreateDate(Calendar.getInstance().getTime());
		role.setRoleUpdateUser(loginUserId);
		role.setRoleUpdateDate(Calendar.getInstance().getTime());
		this.roleDao.innsertRole(role);
	}
	
	/**
	 * 修改角色
	 * @param role
	 * @param loginUserId
	 */
	public void editRole(PmsRole role,Integer loginUserId){
		role.setRoleUpdateUser(loginUserId);
		role.setRoleUpdateDate(Calendar.getInstance().getTime());
		this.roleDao.updateRole(role);
	}
	
	/**
	 * 删除角色
	 * @param roleId
	 */
	public void deleteRoleById(Integer roleId){
		this.roleDao.deleteRoleById(roleId);
		this.roleMenuDao.deleteMenuByRoleId(roleId);
		this.roleFuncDao.deleteFuncByRoleId(roleId);
	}
	
	/**
	 * 修改角色菜单
	 * @param roleId
	 * @param menuIds
	 * @param loginUserId
	 */
	public void updateRoleMenus(Integer roleId,String menuIds,Integer loginUserId){
		List<PmsRoleMenu> roleMenus = this.roleMenuDao.getRoleMenusByRoleId(roleId);
		Set<String> menuSet = ArrayUtil.asSet(menuIds.split(","));
		for(PmsRoleMenu userMenu : roleMenus){
			if(!menuSet.contains(userMenu.getPrmMenuId())){
				this.roleMenuDao.deleteRoleMenuById(userMenu.getPrmRoleMenuId());
			}else{
				menuSet.remove(userMenu.getPrmMenuId());
			}
		}
		for(String menuId : menuSet){
			PmsRoleMenu roleMenu = new PmsRoleMenu();
			roleMenu.setPrmRoleId(roleId);
			roleMenu.setPrmMenuId(menuId);
			roleMenu.setPrmCreateDate(Calendar.getInstance().getTime());
			roleMenu.setPrmCreateUser(loginUserId);
			roleMenu.setPrmUpdateDate(Calendar.getInstance().getTime());
			roleMenu.setPrmUpdateUser(loginUserId);
			this.roleMenuDao.insertRoleMenu(roleMenu);
		}
	}
	
	/**
	 * 修改角色功能
	 * @param roleId
	 * @param funcIds
	 * @param loginUserId
	 */
	public void updateRoleFuncs(Integer roleId,String funcIds,Integer loginUserId){
		List<PmsRoleFunc> roleFuncs = this.roleFuncDao.getRoleFuncsByRoleId(roleId);
		Set<String> funcSet = ArrayUtil.asSet(funcIds.split(","));
		for(PmsRoleFunc userFunc : roleFuncs){
			if(!funcSet.contains(userFunc.getPrfFuncId())){
				this.roleFuncDao.deleteRoleFuncById(userFunc.getPrfRoleFuncId());
			}else{
				funcSet.remove(userFunc.getPrfFuncId());
			}
		}
		for(String funcId : funcSet){
			PmsRoleFunc roleFunc = new PmsRoleFunc();
			roleFunc.setPrfRoleId(roleId);
			roleFunc.setPrfFuncId(funcId);
			roleFunc.setPrfCreateDate(Calendar.getInstance().getTime());
			roleFunc.setPrfCreateUser(loginUserId);
			roleFunc.setPrfUpdateDate(Calendar.getInstance().getTime());
			roleFunc.setPrfUpdateUser(loginUserId);
			this.roleFuncDao.insertRoleFunc(roleFunc);
		}
	}
	
	/**
	 * 查询角色列表
	 * @param page
	 * @param roleName
	 * @return
	 */
	@Override
	public IPageList<PmsRole> queryRoleList(IPageSize page,String roleName){
		Map<String,Object> condition = new HashMap<String,Object>();
		if(!StringUtil.isNullOrEmpty(roleName))condition.put("roleName", roleName);
		return this.roleDao.queryRoleList(page, condition);
	}

	/**
	 * 判断是否存在该角色名称
	 *
	 * @param role
	 * @return
	 */
	@Override
	public boolean checkRoleIsRepeat(PmsRole role) {
		return this.roleDao.checkRoleIsRepeat(role);
	}

	/**
	 * 校验角色是否可以删除
	 *
	 * @return
	 * @param roleId
	 */
	@Override
	public boolean checkRoleCanDelete(Integer roleId) {
		return roleDao.checkRoleCanDelete(roleId);
	}

	/**
	 * 获取所有数据权限列表
	 *
	 * @return
	 */
	@Override
	public List<PmsDataPermit> getAllDataPermits() {
		return dataPermitDao.queryDataPermitList(new HashMap<String, Object>());
	}

	/**
	 * 通过用户userid获取所有权限列表
	 * @param roleId
	 * @return
	 */
	@Override
	public String getDataPermitByRoleId(Integer roleId) {
		List<PmsDataPermit> dpList = dataPermitDao.queryDataPermitListByRoleId(roleId);
		StringBuilder sb = new StringBuilder();
		if(dpList == null || dpList.size() == 0){
			return "";
		}else{
			for(PmsDataPermit dp : dpList){
				sb.append(",");
				sb.append(dp.getPdpId());
			}
		}
		return sb.toString().substring(1);
	}

	/**
	 * 修改角色功能
	 *
	 * @param roleId
	 * @param dataPermitIds
	 * @param loginUserId
	 */
	@Override
	public void updateDataPermits(Integer roleId, String dataPermitIds, Integer loginUserId) {
		List<PmsRoleDataPermit> roldPermits = this.roleDataPermitDao.queryRoleDataPermitByRoleId(roleId);
		Set<String> permitSet = ArrayUtil.asSet(dataPermitIds.split(","));
		for(PmsRoleDataPermit dataPermit : roldPermits){
			if(!permitSet.contains(dataPermit.getPrdpPermitId())){
				this.roleDataPermitDao.deleteRoleDataPermitById(dataPermit.getPrdpId());
			}else{
				permitSet.remove(dataPermit.getPrdpPermitId());
			}
		}
		for(String dataPermitId : permitSet){
			if(!StringUtil.isNullOrEmpty(dataPermitId)){
				PmsRoleDataPermit dataPermit = new PmsRoleDataPermit();
				dataPermit.setPrdpRoleId(roleId);
				dataPermit.setPrdpPermitId(Integer.parseInt(dataPermitId));
				dataPermit.setPrdpCreateDate(Calendar.getInstance().getTime());
				dataPermit.setPrdpCreateUser(loginUserId);
				dataPermit.setPrdpUpdateDate(Calendar.getInstance().getTime());
				dataPermit.setPrdpUpdateUser(loginUserId);
				this.roleDataPermitDao.insertRoleDataPermit(dataPermit);
			}
		}
	}

	/**
	 * 判断是否具有改类型的数据权限
	 *
	 *
	 * @param dataPermitType
	 * @param userId
	 * @return
	 */
	@Override
	public boolean haveDataPermit(String dataPermitType, Integer userId) {
		return dataPermitDao.haveDataPermit(dataPermitType,userId);
	}

	@Override
	public List<PmsRole> getRolesByType(Integer type) {
		return this.roleDao.getRolesByType(type);
	}

    @Override
    public List<PmsUserRoles> getPmsRoleListByUserId(Integer userId) {
        return this.roleDao.getPmsRoleListByUserId(userId);
    }


	/**
	 * 得到角色排序号
	 * @return
	 */
	private Integer getSortNo(){
		int sortNo = 0;
		List<PmsRole> roles = this.roleDao.getUserAllRoles();
		for(PmsRole role : roles){
			if(sortNo<role.getRoleSort().intValue())sortNo=role.getRoleSort();
		}
		return sortNo+1;
	}

	@Override
	public List<PmsRole> getRoleIdByUID(Integer uid) {
		return roleDao.getRoleIdByUID(uid);
	}
	@Override
	public List<PmsRole>  getTeamRolesByGroupId(Integer groupId){
		return this.roleDao.getTeamRolesByGroupId(groupId);
	}
	
}
