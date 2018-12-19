package banger.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import banger.domain.customer.CustPotentialCustomersFilesQuery;
import banger.moduleIntf.IPotentialCustomerFileProvider;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.util.DateUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.IPotentialCustomersFilesDao;
import banger.service.intf.IPotentialCustomersFilesService;
import banger.domain.customer.CustPotentialCustomersFiles;

/**
 * 潜在客户附件文件表业务访问类
 */
@Repository
public class PotentialCustomersFilesService implements IPotentialCustomersFilesService, IPotentialCustomerFileProvider {

	@Autowired
	private IPotentialCustomersFilesDao potentialCustomersFilesDao;

	/**
	 * 新增潜在客户附件文件表
	 * @param potentialCustomersFiles 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertPotentialCustomersFiles(CustPotentialCustomersFiles potentialCustomersFiles,Integer loginUserId){
		potentialCustomersFiles.setCreateUser(loginUserId);
		potentialCustomersFiles.setCreateDate(DateUtil.getCurrentDate());
		potentialCustomersFiles.setUpdateUser(loginUserId);
		potentialCustomersFiles.setUpdateDate(DateUtil.getCurrentDate());
		this.potentialCustomersFilesDao.insertPotentialCustomersFiles(potentialCustomersFiles);
	}

	@Override
	public List<CustPotentialCustomersFiles> getPotentialFilesListByPotentialId(Integer potentialId) {
		Map<String,Object> condition = new HashMap<String, Object>();
		condition.put("potentialId", potentialId);
		condition.put("isDel", 0);
		return queryPotentialCustomersFilesList(condition);
	}



	/**
	 *修改潜在客户附件文件表
	 * @param potentialCustomersFiles 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updatePotentialCustomersFiles(CustPotentialCustomersFiles potentialCustomersFiles,Integer loginUserId){
		potentialCustomersFiles.setUpdateUser(loginUserId);
		potentialCustomersFiles.setUpdateDate(DateUtil.getCurrentDate());
		this.potentialCustomersFilesDao.updatePotentialCustomersFiles(potentialCustomersFiles);
	}

	/**
	 * 通过主键删除潜在客户附件文件表
	 * @param id 主键Id
	 */
	public void deletePotentialCustomersFilesById(Integer id){
		this.potentialCustomersFilesDao.deletePotentialCustomersFilesById(id);
	}

	/**
	 * 通过主键得到潜在客户附件文件表
	 * @param id 主键Id
	 */
	public CustPotentialCustomersFiles getPotentialCustomersFilesById(Integer id){
		return this.potentialCustomersFilesDao.getPotentialCustomersFilesById(id);
	}

	@Override
	public CustPotentialCustomersFiles getPotentialCustomersFilesByFileId(String fileId) {
		Map<String,Object> condition = new HashMap<String, Object>();
		condition.put("fileId", fileId);
		condition.put("isDel", 0);
		List<CustPotentialCustomersFiles> list = queryPotentialCustomersFilesList(condition);
		if (list !=null )
			return list.get(0);
		return null;
	}

	/**
	 * 查询潜在客户附件文件表
	 * @param condition 查询条件
	 * @return
	 */
	public List<CustPotentialCustomersFiles> queryPotentialCustomersFilesList(Map<String,Object> condition){
		return this.potentialCustomersFilesDao.queryPotentialCustomersFilesList(condition);
	}

	/**
	 * 分页查询潜在客户附件文件表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<CustPotentialCustomersFiles> queryPotentialCustomersFilesList(Map<String,Object> condition,IPageSize page){
		return this.potentialCustomersFilesDao.queryPotentialCustomersFilesList(condition,page);
	}

	@Override
	public IPageList<CustPotentialCustomersFilesQuery> queryPotentialCustomersFilesQueryList(Map<String, Object> condition, IPageSize page) {
		return this.potentialCustomersFilesDao.queryPotentialCustomersFilesQueryList(condition,page);
	}
}
