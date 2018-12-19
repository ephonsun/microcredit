package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.domain.customer.CustPotentialCustomersFilesQuery;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.customer.CustPotentialCustomersFiles;

/**
 * 潜在客户附件文件表数据访问接口
 */
public interface IPotentialCustomersFilesDao {

	/**
	 * 新增潜在客户附件文件表
	 * @param potentialCustomersFiles 实体对像
	 */
	void insertPotentialCustomersFiles(CustPotentialCustomersFiles potentialCustomersFiles);

	/**
	 *修改潜在客户附件文件表
	 * @param potentialCustomersFiles 实体对像
	 */
	void updatePotentialCustomersFiles(CustPotentialCustomersFiles potentialCustomersFiles);

	/**
	 * 通过主键删除潜在客户附件文件表
	 * @param id 主键Id
	 */
	void deletePotentialCustomersFilesById(Integer id);

	/**
	 * 通过主键得到潜在客户附件文件表
	 * @param id 主键Id
	 */
	CustPotentialCustomersFiles getPotentialCustomersFilesById(Integer id);

	/**
	 * 查询潜在客户附件文件表
	 * @param condition 查询条件
	 * @return
	 */
	List<CustPotentialCustomersFiles> queryPotentialCustomersFilesList(Map<String, Object> condition);

	/**
	 * 分页查询潜在客户附件文件表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<CustPotentialCustomersFiles> queryPotentialCustomersFilesList(Map<String, Object> condition, IPageSize page);


	/**
	 * 分页查询潜在客户附件文件表子类
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
    IPageList<CustPotentialCustomersFilesQuery> queryPotentialCustomersFilesQueryList(Map<String, Object> condition, IPageSize page);
}
