package banger.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.util.DateUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.IAuditDao;
import banger.service.intf.IAuditService;
import banger.domain.loan.LoanAudit;

/**
 * 贷款审计表业务访问类
 */
@Repository
public class AuditService implements IAuditService {

	@Autowired
	private IAuditDao auditDao;

	/**
	 * 新增贷款审计表
	 * @param audit 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertAudit(LoanAudit audit,Integer loginUserId){
		audit.setCreateUser(loginUserId);
		audit.setCreateDate(DateUtil.getCurrentDate());
		audit.setUpdateUser(loginUserId);
		audit.setUpdateDate(DateUtil.getCurrentDate());
		this.auditDao.insertAudit(audit);
	}

	/**
	 *修改贷款审计表
	 * @param audit 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateAudit(LoanAudit audit,Integer loginUserId){
		audit.setUpdateUser(loginUserId);
		audit.setUpdateDate(DateUtil.getCurrentDate());
		this.auditDao.updateAudit(audit);
	}

	/**
	 * 通过主键删除贷款审计表
	 * @param LOAN_AUDIT_ID 主键Id
	 */
	public void deleteAuditById(Integer loanAuditId){
		this.auditDao.deleteAuditById(loanAuditId);
	}

	/**
	 * 通过主键得到贷款审计表
	 * @param LOAN_AUDIT_ID 主键Id
	 */
	public LoanAudit getAuditById(Integer loanAuditId){
		return this.auditDao.getAuditById(loanAuditId);
	}

	/**
	 * 查询贷款审计表
	 * @param condition 查询条件
	 * @return
	 */
	public List<LoanAudit> queryAuditList(Map<String,Object> condition){
		return this.auditDao.queryAuditList(condition);
	}

	/**
	 * 分页查询贷款审计表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<LoanAudit> queryAuditList(Map<String,Object> condition,IPageSize page){
		return this.auditDao.queryAuditList(condition,page);
	}

}
