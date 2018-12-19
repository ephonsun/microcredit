package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.domain.customer.CustPotentialCustomersFilesQuery;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.customer.CustPotentialCustomersFiles;

/**
 * 潜在客户附件文件表业务访问接口
 */
public interface IPotentialCustomersFilesService {

	/**
	 * 新增潜在客户附件文件表
	 * @param potentialCustomersFiles 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertPotentialCustomersFiles(CustPotentialCustomersFiles potentialCustomersFiles, Integer loginUserId);

	/**
	 *修改潜在客户附件文件表
	 * @param potentialCustomersFiles 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updatePotentialCustomersFiles(CustPotentialCustomersFiles potentialCustomersFiles, Integer loginUserId);

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
	 * 根据潜在客户id获取该客户的影像资料
	 * @param potentialId
	 * @return
	 */
	List<CustPotentialCustomersFiles> getPotentialFilesListByPotentialId(Integer potentialId);


	/**
	 * 分页查询潜在客户附件文件表子类
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
    IPageList<CustPotentialCustomersFilesQuery> queryPotentialCustomersFilesQueryList(Map<String, Object> condition, IPageSize page);
}
