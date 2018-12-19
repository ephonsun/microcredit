package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.domain.loan.LoanType;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

/**
 * 贷款类型数据访问接口
 */
public interface ITypeDao {

	/**
	 * 新增贷款类型
	 *
	 * @param type
	 *            实体对像
	 */
	void insertType(LoanType type);

	/**
	 * 修改贷款类型
	 *
	 * @param type
	 *            实体对像
	 */
	void updateType(LoanType type);

	/**
	 * 通过主键删除贷款类型
	 *
	 * @param typeId
	 *            主键Id
	 */
	void deleteTypeById(Integer typeId);

	/**
	 * 通过主键得到贷款类型
	 *
	 * @param typeId
	 *            主键Id
	 */
	LoanType getTypeById(Integer typeId);

	/**
	 * 贷款类型列表
	 * @return
	 */
	List<LoanType> getAllLoanTypeList();

	/**
	 * 查询贷款类型
	 *
	 * @param condition
	 *            查询条件
	 * @return
	 */
	List<LoanType> queryTypeList(Map<String, Object> condition);



	/**
	 * 分页查询贷款类型
	 *
	 * @param condition
	 *            查询条件
	 * @param page
	 *            分页对像
	 * @return
	 */
	IPageList<LoanType> queryTypeList(Map<String, Object> condition, IPageSize page);

	/**
	 * 获取贷款类型最大排序号
	 *
	 * @return
	 */
	Integer queryMaxOrderNum();

	/**
	 * 获取合同类型最大排序号
	 *
	 * @return
	 */
	Integer queryContractMaxOrderNum();
}
