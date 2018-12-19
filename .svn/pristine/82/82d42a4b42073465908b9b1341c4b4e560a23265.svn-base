/*
 * banger Inc.
 * Copyright (c) 2009-2012 All Rights Reserved.
 * ToDo       :This is Class Description...
 * Author     :wumh
 * Create Date:2013-11-15下午3:30:45
 */
package banger.service.impl;

import java.util.List;
import banger.dao.intf.IPmsRoleDao;
import banger.domain.permission.PmsRole;
import banger.service.intf.IPmsRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wumh
 * @version $Id: PmsRoleService.java,v 0.1 2013-11-15 下午3:30:45 wumh Exp $
 */
public class PmsRoleService implements IPmsRoleService{
	private IPmsRoleDao pmsRoleDao;
	/**
	 * @return
	 * @see banger.service.intf.IPmsRoleService#getAllPmsRoleList()
	 */
	@Override
	public List<PmsRole> getAllPmsRoleList() {
		return pmsRoleDao.getAllPmsRoleList();
	}

	/**
	 * @param id
	 * @return
	 * @see banger.service.intf.IPmsRoleService#getPmsRole(java.lang.Integer)
	 */
	@Override
	public PmsRole getPmsRole(Integer id) {
		return pmsRoleDao.getPmsRoleByRoleId(id);
	}
	
	/**
	 * @param userId
	 * @return
	 * @see banger.service.intf.IPmsRoleService#getPmsRoleListByUserId(java.lang.Integer)
	 */
	@Override
	public List<PmsRole> getPmsRoleListByUserId(Integer userId) {
		return pmsRoleDao.getPmsRoleListByUserId(userId);
	}

	public void setPmsRoleDao(IPmsRoleDao pmsRoleDao) {
		this.pmsRoleDao = pmsRoleDao;
	}

	/**
	 * @return
	 * @see banger.service.intf.IPmsRoleService#getPmsRoleListExceptAdmin()
	 */
	@Override
	public List<PmsRole> getPmsRoleListExceptAdmin() {
		return pmsRoleDao.getPmsRoleListExceptAdmin();
	}

}
