package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.LoanAudit;

/**
 * 贷款审计表业务访问接口
 */
public interface IAuditService {

	/**
	 * 新增贷款审计表
	 * @param audit 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertAudit(LoanAudit audit, Integer loginUserId);

	/**
	 *修改贷款审计表
	 * @param audit 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateAudit(LoanAudit audit, Integer loginUserId);

	/**
	 * 通过主键删除贷款审计表
	 * @param LOAN_AUDIT_ID 主键Id
	 */
	void deleteAuditById(Integer loanAuditId);

	/**
	 * 通过主键得到贷款审计表
	 * @param LOAN_AUDIT_ID 主键Id
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
