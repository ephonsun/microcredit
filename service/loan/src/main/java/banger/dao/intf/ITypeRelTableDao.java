package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.domain.loan.LoanTypeRelTable;
import banger.domain.loan.LoanTypeRelTableExtend;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

/**
 * 贷款类型匹配表单数据访问接口
 */
public interface ITypeRelTableDao {

	/**
	 * 新增贷款类型匹配表单
	 * 
	 * @param typeRelTable
	 *            实体对像
	 */
	void insertTypeRelTable(LoanTypeRelTable typeRelTable);

	/**
	 * 修改贷款类型匹配表单
	 * 
	 * @param typeRelTable
	 *            实体对像
	 */
	void updateTypeRelTable(LoanTypeRelTable typeRelTable);

	/**
	 * 通过主键删除贷款类型匹配表单
	 * 
	 * @param id
	 *            主键Id
	 */
	void deleteTypeRelTableById(Integer id);

	/**
	 * 通过主键得到贷款类型匹配表单
	 * 
	 * @param id
	 *            主键Id
	 */
	LoanTypeRelTable getTypeRelTableById(Integer id);

	/**
	 * 查询贷款类型匹配表单
	 * 
	 * @param condition
	 *            查询条件
	 * @return
	 */
	List<LoanTypeRelTable> queryTypeRelTableList(Map<String, Object> condition);

	/**
	 * 分页查询贷款类型匹配表单
	 * 
	 * @param condition
	 *            查询条件
	 * @param page
	 *            分页对像
	 * @return
	 */
	IPageList<LoanTypeRelTable> queryTypeRelTableList(Map<String, Object> condition, IPageSize page);

	List<LoanTypeRelTable> queryAutoTableInfoByCondition(Map<String, Object> condition);

	/**
	 * 查询最大排序号
	 */
	Integer queryLoanTypeRelTableMaxOrderNum(Integer typeId, String precType);

	/**
	 * 
	 * @param typeID
	 *            贷款类型id
	 * @param precType
	 *            表单名
	 * @return
	 */
	List<LoanTypeRelTableExtend> queryAutoTableInfoByCondition(Integer typeId, String precType);

	/**
	 * 根据条件查询并排序
	 * 
	 * @param condition
	 * @return
	 */
	List<LoanTypeRelTable> queryAutoTableInfoByConditionOrder(Map<String, Object> condition);

}
