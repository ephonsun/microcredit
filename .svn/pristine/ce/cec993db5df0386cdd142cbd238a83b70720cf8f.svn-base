package banger.dao.impl;

import java.util.List;
import java.util.Map;

import banger.domain.customer.CustPotentialCustomersFilesQuery;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.IPotentialCustomersFilesDao;
import banger.domain.customer.CustPotentialCustomersFiles;

/**
 * 潜在客户附件文件表数据访问类
 */
@Repository
public class PotentialCustomersFilesDao extends PageSizeDao<CustPotentialCustomersFiles> implements IPotentialCustomersFilesDao {

	/**
	 * 新增潜在客户附件文件表
	 * @param potentialCustomersFiles 实体对像
	 */
	public void insertPotentialCustomersFiles(CustPotentialCustomersFiles potentialCustomersFiles){
		potentialCustomersFiles.setId(this.newId().intValue());
		this.execute("insertPotentialCustomersFiles",potentialCustomersFiles);
	}

	/**
	 *修改潜在客户附件文件表
	 * @param potentialCustomersFiles 实体对像
	 */
	public void updatePotentialCustomersFiles(CustPotentialCustomersFiles potentialCustomersFiles){
		this.execute("updatePotentialCustomersFiles",potentialCustomersFiles);
	}

	/**
	 * 通过主键删除潜在客户附件文件表
	 * @param id 主键Id
	 */
	public void deletePotentialCustomersFilesById(Integer id){
		this.execute("deletePotentialCustomersFilesById",id);
	}

	/**
	 * 通过主键得到潜在客户附件文件表
	 * @param id 主键Id
	 */
	public CustPotentialCustomersFiles getPotentialCustomersFilesById(Integer id){
		return (CustPotentialCustomersFiles)this.queryEntity("getPotentialCustomersFilesById",id);
	}

	/**
	 * 查询潜在客户附件文件表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CustPotentialCustomersFiles> queryPotentialCustomersFilesList(Map<String,Object> condition){
		return (List<CustPotentialCustomersFiles>)this.queryEntities("queryPotentialCustomersFilesList", condition);
	}

	/**
	 * 分页查询潜在客户附件文件表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<CustPotentialCustomersFiles> queryPotentialCustomersFilesList(Map<String,Object> condition,IPageSize page){
		return (IPageList<CustPotentialCustomersFiles>)this.queryEntities("queryPotentialCustomersFilesList", page, condition);
	}

	@Override
	public IPageList<CustPotentialCustomersFilesQuery> queryPotentialCustomersFilesQueryList(Map<String, Object> condition, IPageSize page) {
		return (IPageList<CustPotentialCustomersFilesQuery>)this.queryEntities("queryPotentialCustomersFilesQueryList", page, condition);
	}

}
