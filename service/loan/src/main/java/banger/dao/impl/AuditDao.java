package banger.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.IAuditDao;
import banger.domain.loan.LoanAudit;

/**
 * 贷款审计表数据访问类
 */
@Repository
public class AuditDao extends PageSizeDao<LoanAudit> implements IAuditDao {

	/**
	 * 新增贷款审计表
	 * @param audit 实体对像
	 */
	public void insertAudit(LoanAudit audit){
		audit.setLoanAuditId(this.newId().intValue());
		this.execute("insertAudit",audit);
	}

	/**
	 *修改贷款审计表
	 * @param audit 实体对像
	 */
	public void updateAudit(LoanAudit audit){
		this.execute("updateAudit",audit);
	}

	/**
	 * 通过主键删除贷款审计表
	 * @param loanAuditId 主键Id
	 */
	public void deleteAuditById(Integer loanAuditId){
		this.execute("deleteAuditById",loanAuditId);
	}

	/**
	 * 通过主键得到贷款审计表
	 * @param loanAuditId 主键Id
	 */
	public LoanAudit getAuditById(Integer loanAuditId){
		return (LoanAudit)this.queryEntity("getAuditById",loanAuditId);
	}

	/**
	 * 查询贷款审计表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LoanAudit> queryAuditList(Map<String,Object> condition){
		return (List<LoanAudit>)this.queryEntities("queryAuditList", condition);
	}

	/**
	 * 分页查询贷款审计表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<LoanAudit> queryAuditList(Map<String,Object> condition,IPageSize page){
		return (IPageList<LoanAudit>)this.queryEntities("queryAuditList", page, condition);
	}

}
