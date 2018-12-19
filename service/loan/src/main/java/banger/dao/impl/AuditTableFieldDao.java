package banger.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import banger.domain.enumerate.LoanLinkTypeEnum;
import org.springframework.stereotype.Repository;

import banger.dao.intf.IAuditTableFieldDao;
import banger.domain.loan.LoanAuditTableField;
import banger.domain.loan.LoanAuditTableFieldExtend;
import banger.framework.dao.PageSizeDao;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

/**
 * 贷款审合表单字段配置数据访问类
 */
@Repository
public class AuditTableFieldDao extends PageSizeDao<LoanAuditTableField> implements IAuditTableFieldDao {

	/**
	 * 新增贷款审合表单字段配置
	 * @param auditTableField 实体对像
	 */
	public void insertAuditTableField(LoanAuditTableField auditTableField){
		auditTableField.setId(this.newId().intValue());
		this.execute("insertAuditTableField",auditTableField);
	}

	/**
	 *修改贷款审合表单字段配置
	 * @param auditTableField 实体对像
	 */
	public void updateAuditTableField(LoanAuditTableField auditTableField){
		this.execute("updateAuditTableField",auditTableField);
	}

	/**
	 * 通过主键删除贷款审合表单字段配置
	 * @param id 主键Id
	 */
	public void deleteAuditTableFieldById(Integer id){
		this.execute("deleteAuditTableFieldById",id);
	}


	@Override
	public void deleteAuditTableFieldByLinkId(Integer linkId, LoanLinkTypeEnum type) {
		this.execute("deleteAuditTableFieldByLinkId",linkId, type.toString());
	}


	/**
	 * 通过主键得到贷款审合表单字段配置
	 * @param id 主键Id
	 */
	public LoanAuditTableField getAuditTableFieldById(Integer id){
		return (LoanAuditTableField)this.queryEntity("getAuditTableFieldById",id);
	}

	/**
	 * 查询贷款审合表单字段配置
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LoanAuditTableField> queryAuditTableFieldList(Map<String,Object> condition){
		return (List<LoanAuditTableField>)this.queryEntities("queryAuditTableFieldList", condition);
	}

	/**
	 * 分页查询贷款审合表单字段配置
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<LoanAuditTableField> queryAuditTableFieldList(Map<String,Object> condition,IPageSize page){
		return (IPageList<LoanAuditTableField>)this.queryEntities("queryAuditTableFieldList", page, condition);
	}

	/**
	 * 查询选择审批字段
	 */
	@SuppressWarnings("unchecked")
	public List<LoanAuditTableFieldExtend> queryAuditTableFieldSelect(Integer processId) {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("processId", processId);
		return (List<LoanAuditTableFieldExtend>) this.queryEntities("queryAuditTableFieldSelect", condition);
	}

}
