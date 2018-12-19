package banger.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import banger.dao.intf.ITypeDao;
import banger.domain.loan.LoanType;
import banger.framework.dao.PageSizeDao;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

/**
 * 贷款类型数据访问类
 */
@Repository
public class TypeDao extends PageSizeDao<LoanType> implements ITypeDao {

	/**
	 * 新增贷款类型
	 *
	 * @param type
	 *            实体对像
	 */
	public void insertType(LoanType type) {
		type.setTypeId(this.newId().intValue());
		this.execute("insertType", type);
	}

	/**
	 * 修改贷款类型
	 *
	 * @param type
	 *            实体对像
	 */
	public void updateType(LoanType type) {
		this.execute("updateType", type);
	}

	/**
	 * 通过主键删除贷款类型
	 *
	 * @param typeId
	 *            主键Id
	 */
	public void deleteTypeById(Integer typeId) {
		this.execute("deleteTypeById", typeId);
	}

	/**
	 * 通过主键得到贷款类型
	 *
	 * @param typeId
	 *            主键Id
	 */
	public LoanType getTypeById(Integer typeId) {
		return (LoanType) this.queryEntity("getTypeById", typeId);
	}

	/**
	 * 贷款类型列表
	 * @return
	 */
	public List<LoanType> getAllLoanTypeList(){
		return (List<LoanType>) this.queryEntities("getAllLoanTypeList", "");
	}

	/**
	 * 查询贷款类型
	 *
	 * @param condition
	 *            查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LoanType> queryTypeList(Map<String, Object> condition) {
		return (List<LoanType>) this.queryEntities("queryTypeList", condition);
	}

	/**
	 * 分页查询贷款类型
	 *
	 * @param condition
	 *            查询条件
	 * @param page
	 *            分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<LoanType> queryTypeList(Map<String, Object> condition, IPageSize page) {
		return (IPageList<LoanType>) this.queryEntities("queryTypeList", page, condition);
	}

	/**
	 * 获取贷款类型最大排序号
	 *
	 * @return
	 */
	public Integer queryMaxOrderNum() {
		Map<String, Object> condition = new HashMap<String, Object>();
		return this.queryInt("queryLoanTypeMaxOrderNum", condition);
	}

	/**
	 * 获取合同类型最大排序号
	 *
	 * @return
	 */
	public Integer queryContractMaxOrderNum() {
		Map<String, Object> condition = new HashMap<String, Object>();
		return this.queryInt("queryLoanContractTypeMaxOrderNum", condition);
	}
}
