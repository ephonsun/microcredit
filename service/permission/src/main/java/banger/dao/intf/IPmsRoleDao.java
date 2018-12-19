/*
 * banger Inc.
 * Copyright (c) 2009-2012 All Rights Reserved.
 * ToDo       :This is Class Description...
 * Author     :wumh
 * Create Date:2013-11-13下午8:48:43
 */
package banger.dao.intf;

import java.util.List;

import banger.domain.permission.PmsRole;
/**
 * @author wumh
 * @version $Id: IPmsRoleDao.java,v 0.1 2013-11-13 下午8:48:43 wumh Exp $
 */

public interface IPmsRoleDao{
	
	/**
	 * 新增角色
	 * @param pmsRole
	 * @author wumh
	 * @return 
	 */
	@Deprecated
	PmsRole addPmsRole(PmsRole pmsRole);	
	
	/**
	 * 更新角色
	 * @param pmsRole
	 * @author wumh
	 */
	@Deprecated
	void updatePmsRole(PmsRole pmsRole);
	
	/**
	 * 获取所有角色list
	 * @author wumh
	 * @return
	 */
	List<PmsRole> getAllPmsRoleList();
	
	/**
	 * 根据角色roleId获取角色实体
	 * @param roleId
	 * @author wumh
	 * @return
	 */
	PmsRole getPmsRoleByRoleId(Integer roleId);	
	
	
	/**
	 * 根据userId获取用户角色的列表
	 * @param userId
	 * @return
	 */
	List<PmsRole> getPmsRoleListByUserId(Integer userId);
	
	/**
	 * 获取除admin外的所有角色
	 * @return
	 */
	List<PmsRole> getPmsRoleListExceptAdmin();
}
