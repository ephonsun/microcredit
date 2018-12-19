package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.LoanAudit;

/**
 * 贷款审计表数据访问接口
 */
public interface IAuditDao {

	/**
	 * 新增贷款审计表
	 * @param audit 实体对像
	 */
	void insertAudit(LoanAudit audit);

	/**
	 *修改贷款审计表
	 * @param audit 实体对像
	 */
	void updateAudit(LoanAudit audit);

	/**
	 * 通过主键删除贷款审计表
	 * @param loanAuditId 主键Id
	 */
	void deleteAuditById(Integer loanAuditId);

	/**
	 * 通过主键得到贷款审计表
	 * @param loanAuditId 主键Id
	 */
	LoanAudit getAuditById(Integer loanAuditId);

	/**
	 * 查询贷款审计表
	 * @param condition 查询条件
	 * @return
	 */
	List<LoanAudit> queryAuditList(Map<String, Object> condition);

	/**
	 * 分页查询贷款审计表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<LoanAudit> queryAuditList(Map<String, Object> condition, IPageSize page);

}
