/*
 * banger Inc.
 * Copyright (c) 2009-2012 All Rights Reserved.
 * ToDo       :This is Class Description...
 * Author     :wumh
 * Create Date:2013-11-14下午1:17:52
 */
package banger.dao.impl;

import java.util.ArrayList;
import java.util.List;

import banger.dao.intf.IPmsRoleMenuDao;
import banger.domain.permission.PmsRoleMenu;
import banger.framework.dao.PageSizeDao;
import org.springframework.stereotype.Repository;

/**
 * @author wumh
 * @version $Id: PmsRoleMenuDao.java,v 0.1 2013-11-14 下午1:17:52 wumh Exp $
 */
@Repository
public class PmsRoleMenuDao extends PageSizeDao<PmsRoleMenu> implements IPmsRoleMenuDao{

	/**
	 * @param roleIds
	 * @return
	 * @see banger.dao.intf.IPmsRoleMenuDao#getMenuIdsByRoleIds(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getMenuIdsByRoleIds(String roleIds) {
		List<PmsRoleMenu> list = (List<PmsRoleMenu>)queryEntities("queryPmsRoleMenu", roleIds);
		List<String> menuIds = new ArrayList<String>();
		for (PmsRoleMenu pmsRoleMenu : list) {
			menuIds.add(pmsRoleMenu.getPrmMenuId());
		}
		return menuIds;
	}

}
