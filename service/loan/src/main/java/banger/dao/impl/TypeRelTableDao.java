package banger.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import banger.dao.intf.IApproveProcessReviewDao;
import banger.domain.loan.WfApproveProcessReview;
import org.springframework.stereotype.Repository;

import banger.dao.intf.ITypeRelTableDao;
import banger.domain.loan.LoanTypeRelTable;
import banger.domain.loan.LoanTypeRelTableExtend;
import banger.framework.dao.PageSizeDao;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

/**
 * 贷款类型匹配表单数据访问类
 */
@Repository
public class TypeRelTableDao extends PageSizeDao<LoanTypeRelTable> implements ITypeRelTableDao {

	/**
	 * 新增贷款类型匹配表单
	 * 
	 * @param typeRelTable
	 *            实体对像
	 */
	public void insertTypeRelTable(LoanTypeRelTable typeRelTable) {
		int intValue = this.newId().intValue();
		typeRelTable.setId(intValue);
		this.execute("insertTypeRelTable", typeRelTable);
	}

	/**
	 * 修改贷款类型匹配表单
	 * 
	 * @param typeRelTable
	 *            实体对像
	 */
	public void updateTypeRelTable(LoanTypeRelTable typeRelTable) {
		this.execute("updateTypeRelTable", typeRelTable);
	}

	/**
	 * 通过主键删除贷款类型匹配表单
	 * 
	 * @param id
	 *            主键Id
	 */
	public void deleteTypeRelTableById(Integer id) {
		this.execute("deleteTypeRelTableById", id);
	}

	/**
	 * 通过主键得到贷款类型匹配表单
	 * 
	 * @param id
	 *            主键Id
	 */
	public LoanTypeRelTable getTypeRelTableById(Integer id) {
		return (LoanTypeRelTable) this.queryEntity("getTypeRelTableById", id);
	}

	/**
	 * 查询贷款类型匹配表单
	 * 
	 * @param condition
	 *            查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LoanTypeRelTable> queryTypeRelTableList(Map<String, Object> condition) {
		return (List<LoanTypeRelTable>) this.queryEntities("queryTypeRelTableList", condition);
	}

	/**
	 * 分页查询贷款类型匹配表单
	 * 
	 * @param condition
	 *            查询条件
	 * @param page
	 *            分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<LoanTypeRelTable> queryTypeRelTableList(Map<String, Object> condition, IPageSize page) {
		return (IPageList<LoanTypeRelTable>) this.queryEntities("queryTypeRelTableList", page, condition);
	}

	/**
	 * 根据条件查询自定义表单
	 */
	@SuppressWarnings("unchecked")
	public List<LoanTypeRelTable> queryAutoTableInfoByCondition(Map<String, Object> condition) {
		return (List<LoanTypeRelTable>) this.queryEntities("queryTypeRelTableList", condition);
	}

	/**
	 * 根据条件查询自定义表单
	 */
	@SuppressWarnings("unchecked")
	public List<LoanTypeRelTable> queryAutoTableInfoByConditionOrder(Map<String, Object> condition) {
		return (List<LoanTypeRelTable>) this.queryEntities("queryTypeRelTableListOrder", condition);
	}

	/**
	 * 查询最大排序号
	 */
	public Integer queryLoanTypeRelTableMaxOrderNum(Integer typeId, String precType) {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("typeId", typeId);
		condition.put("precType", precType);
		return this.queryInt("queryMaxOrderNum", condition);
	}

	@SuppressWarnings("unchecked")
	public List<LoanTypeRelTableExtend> queryAutoTableInfoByCondition(Integer typeId, String precType) {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("typeId", typeId);
		condition.put("precType", precType);
		return (List<LoanTypeRelTableExtend>) this.queryEntities("queryDisplayLoanTypeRelTable", condition);
	}

}
