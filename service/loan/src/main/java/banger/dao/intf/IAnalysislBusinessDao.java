package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.LoanAnalysislBusiness;

/**
 * 主键ID数据访问接口
 */
public interface IAnalysislBusinessDao {

	/**
	 * 新增主键ID
	 * @param analysislBusiness 实体对像
	 */
	void insertAnalysislBusiness(LoanAnalysislBusiness analysislBusiness);

	/**
	 *修改主键ID
	 * @param analysislBusiness 实体对像
	 */
	void updateAnalysislBusiness(LoanAnalysislBusiness analysislBusiness);

	/**
	 * 通过主键删除主键ID
	 * @param id 主键Id
	 */
	void deleteAnalysislBusinessById(Integer id);

	/**
	 * 通过主键得到主键ID
	 * @param id 主键Id
	 */
	LoanAnalysislBusiness getAnalysislBusinessById(Integer id);

	/**
	 * 查询主键ID
	 * @param condition 查询条件
	 * @return
	 */
	List<LoanAnalysislBusiness> queryAnalysislBusinessList(Map<String, Object> condition);

	/**
	 * 分页查询主键ID
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<LoanAnalysislBusiness> queryAnalysislBusinessList(Map<String, Object> condition, IPageSize page);

}
