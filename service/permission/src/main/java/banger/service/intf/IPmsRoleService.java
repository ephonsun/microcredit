/*
 * banger Inc.
 * Copyright (c) 2009-2012 All Rights Reserved.
 * ToDo       :This is Class Description...
 * Author     :wumh
 * Create Date:2013-11-15下午3:30:34
 */
package banger.service.intf;

import java.util.List;
import banger.domain.permission.PmsRole;

/**
 * @author wumh
 * @version $Id: IPmsRoleService.java,v 0.1 2013-11-15 下午3:30:34 wumh Exp $
 */
public interface IPmsRoleService {
	
	/**
	 * 获取所有角色列表
	 * @return
	 */
	List<PmsRole> getAllPmsRoleList();
	
	/**
	 * 获取除admin外的所有角色
	 * @return
	 */
	List<PmsRole> getPmsRoleListExceptAdmin();
	
	/**
	 * 根据主键id获取角色
	 * @param id
	 * @return
	 */
	PmsRole getPmsRole(Integer id);
	
	/**
	 * 通过userId获取该用户的所有角色列表
	 * @param userId
	 * @return
	 */
	List<PmsRole> getPmsRoleListByUserId(Integer userId);
	
}