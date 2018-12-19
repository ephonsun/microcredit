package banger.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import banger.moduleIntf.IPremissionMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import banger.dao.intf.IMenuDao;
import banger.dao.intf.IRoleMenuDao;
import banger.domain.permission.PmsMenu;
import banger.domain.permission.PmsRoleMenu;
import banger.service.intf.IMenuTreeService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class MenuTreeService implements IMenuTreeService ,IPremissionMenu {

	@Autowired
	private IMenuDao menuDao;			//菜单

	@Autowired
	private IRoleMenuDao roleMenuDao;			//角色菜单

	@Override
	public String getRoleMenuTree() {
		return this.getRoleMenuTree(null);
	}

	/**
	 * 得到角色菜单树
	 * @return
	 */
	public String getRoleMenuTree(List<String> menuIds){
		Set<String> menuSet = new HashSet<String>();
		if(menuIds!=null && menuIds.size()>0){
			for(String menuId : menuIds)menuSet.add(menuId);
		}
		List<PmsMenu> menus = this.menuDao.getAllMenu();
		Map<String,PmsMenu> menuMap = new HashMap<String,PmsMenu>();
		JSONArray ja = new JSONArray();
		int rootCount = 0;			//根节点数量
		for(int i=0;i<menus.size();i++){
			PmsMenu menu = menus.get(i);
			if("roleManage".equals(menu.getMenuId()) || "roleManage".equals(menu.getMenuParentId()))continue;
			if("intoPiecesManage".equals(menu.getMenuId()) || "intoPiecesManage".equals(menu.getMenuParentId()))continue;
			menuMap.put(menu.getMenuId(), menu);
			if(!menuMap.containsKey(menu.getMenuParentId()))rootCount++;
			
			JSONObject jo = new JSONObject();
			jo.put("id", menu.getMenuId());
			jo.put("pId", menu.getMenuParentId());
			jo.put("name", menu.getMenuName());
			if(menuSet.contains(menu.getMenuId()))jo.put("checked", true);
			jo.put("icon", "../core/imgs/icon/agency.gif");
			ja.add(jo);
		}
		
		if(rootCount==1){
			((JSONObject)ja.get(0)).put("open", true);
		}
		return ja.toString();
	}

	@Override
	public String getMenuTreeByRoleIds(String roleIds, Integer userId, String account) {
		List<PmsMenu> menus = this.getMenuByRoleIds(roleIds);
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("userId", userId);
		//List<CrmUserQuery> userQueries = cusBasicDataProvider.queryUserQueryList(condition);
		Map<String,PmsMenu> menuMap = new HashMap<String,PmsMenu>();
		Map<String,JSONArray> jaMap = new HashMap<String,JSONArray>();
		JSONArray ja  = new JSONArray();
		for(PmsMenu menu : menus){
			JSONObject jo = new JSONObject();
			menuMap.put(menu.getMenuId(), menu);
			jo.put("id", menu.getMenuId());
			jo.put("display", menu.getMenuName());
			if(menuMap.containsKey(menu.getMenuParentId())){
				jo.put("hasCloseConfirm",menu.getMenuNeedConfirm().equals(1)?true:false);
//				jo.put("id", menu.getMenuId()); 
				jo.put("title", menuMap.get(menu.getMenuParentId()).getMenuName()+"-"+menu.getMenuName());
//				jo.put("display",menu.getMenuName());
				jo.put("url", menu.getMenuUrl());
//                if(menu.getMenuId().equals("addVideoProduct")) {
//                    StringBuffer sb = new StringBuffer();
//                    sb.append(menu.getMenuUrl());
//                    sb.append("?autoLogin=true&account="+account);
//                    jo.put("url", sb.toString());
//                }
				jo.put("lock", false);
				jaMap.get(menu.getMenuParentId()).add(jo);
			}else{
				jaMap.put(menu.getMenuId(),new JSONArray());
				ja.add(jo);
			}
		}
		
		for(int i=0;i<ja.size();i++){
			JSONObject jo = (JSONObject)ja.get(i);			
			jo.put("sub", jaMap.get(jo.getString("id")));
		}
		return ja.toString();
	}
	
	/**
	 * 根据色角取菜单
	 */
	public List<PmsMenu> getMenuByRoleIds(String roleIds) {
		List<PmsMenu> menus = new ArrayList<PmsMenu>();
		List<PmsMenu> allMenus = menuDao.getAllMenu();
		Map<String,PmsRoleMenu> menuMap = this.getRoleMenuMap(roleIds);
    	for(PmsMenu menu : allMenus){
    		if(menuMap.containsKey(menu.getMenuId())){
    			menus.add(menu);
    		}
    	}
    	return menus;
	}
	
	/**
	 * 得到角色菜单
	 * @param roleIds
	 * @return
	 */
	private Map<String,PmsRoleMenu> getRoleMenuMap(String roleIds){
		Map<String,PmsRoleMenu> menuMap = new HashMap<String,PmsRoleMenu>();
		List<PmsRoleMenu> roleMenus = roleMenuDao.getMenuByRoleIds(roleIds);
		for(PmsRoleMenu roleMenu : roleMenus)
			if(!menuMap.containsKey(roleMenu.getPrmMenuId()))
				menuMap.put(roleMenu.getPrmMenuId(), roleMenu);
		return menuMap;
	}

	/**
	 * 根扰菜单ID得到Json数据
	 * @param menuIds
	 * @return
	 */
	public String getMenuByMenuIds(String[] menuIds){
		Set<String> menuSet = new HashSet<String>();
		if(menuIds!=null && menuIds.length>0){
			for(String menuId : menuIds)menuSet.add(menuId);
		}
		List<PmsMenu> menus = this.menuDao.getAllMenu();
		Map<String,PmsMenu> menuMap = new HashMap<String,PmsMenu>();
		Map<String,JSONArray> jaMap = new HashMap<String,JSONArray>();
		JSONArray ja = new JSONArray();
		for(int i=0;i<menus.size();i++){
			PmsMenu menu = menus.get(i);
			if(menuSet.contains(menu.getMenuId())) {
				JSONObject jo = new JSONObject();
				menuMap.put(menu.getMenuId(), menu);
				jo.put("id", menu.getMenuId());
				jo.put("display", menu.getMenuName());

				if (menuMap.containsKey(menu.getMenuParentId())) {
					jo.put("hasCloseConfirm", menu.getMenuNeedConfirm().equals(1) ? true : false);
					jo.put("title", menuMap.get(menu.getMenuParentId()).getMenuName() + "-" + menu.getMenuName());
					jo.put("url", menu.getMenuUrl());
					jo.put("lock", false);
					jaMap.get(menu.getMenuParentId()).add(jo);
				} else {
					jaMap.put(menu.getMenuId(), new JSONArray());
					ja.add(jo);
				}
			}
		}

		for(int i=0;i<ja.size();i++){
			JSONObject jo = (JSONObject)ja.get(i);
			jo.put("sub", jaMap.get(jo.getString("id")));
		}
		return ja.toString();
	}

	/**
	 * 得到不可勾选的角色菜单树
	 * @return
	 */
	public String getRoleDisMenuTree(List<String> menuIds){
		Set<String> menuSet = new HashSet<String>();
		if(menuIds!=null && menuIds.size()>0){
			for(String menuId : menuIds)menuSet.add(menuId);
		}
		List<PmsMenu> menus = this.menuDao.getAllMenu();
		Map<String,PmsMenu> menuMap = new HashMap<String,PmsMenu>();
		JSONArray ja = new JSONArray();
		int rootCount = 0;			//根节点数量
		for(int i=0;i<menus.size();i++){
			PmsMenu menu = menus.get(i);
			if("roleManage".equals(menu.getMenuId()) || "roleManage".equals(menu.getMenuParentId()))continue;
			if("intoPiecesManage".equals(menu.getMenuId()) || "intoPiecesManage".equals(menu.getMenuParentId()))continue;
			menuMap.put(menu.getMenuId(), menu);
			if(!menuMap.containsKey(menu.getMenuParentId()))rootCount++;
			
			JSONObject jo = new JSONObject();
			jo.put("id", menu.getMenuId());
			jo.put("pId", menu.getMenuParentId());
			jo.put("name", menu.getMenuName());
			if(menuSet.contains(menu.getMenuId()))jo.put("checked", true);
			jo.put("icon", "../core/imgs/icon/agency.gif");
			jo.put("chkDisabled", true);
			ja.add(jo);
		}
		
		if(rootCount==1){
			((JSONObject)ja.get(0)).put("open", true);
		}
		return ja.toString();
	}
}
