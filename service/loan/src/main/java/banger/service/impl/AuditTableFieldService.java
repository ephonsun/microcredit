package banger.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import banger.domain.enumerate.LoanLinkTypeEnum;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import banger.dao.intf.IAuditTableFieldDao;
import banger.domain.loan.LoanAuditTableField;
import banger.domain.loan.LoanAuditTableFieldExtend;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.util.DateUtil;
import banger.service.intf.IAuditTableFieldService;

/**
 * 贷款审合表单字段配置业务访问类
 */
@Repository
public class AuditTableFieldService implements IAuditTableFieldService {

	@Autowired
	private IAuditTableFieldDao auditTableFieldDao;


	public void saveAuditTableField(LoanAuditTableField auditTableField,Integer loginUserId){
		auditTableField.setUpdateUser(loginUserId);
		auditTableField.setUpdateDate(DateUtil.getCurrentDate());
		if (auditTableField.getId() == null || auditTableField.getId().intValue() == 0) {
			auditTableField.setCreateUser(loginUserId);
			auditTableField.setCreateDate(DateUtil.getCurrentDate());
			this.auditTableFieldDao.insertAuditTableField(auditTableField);
		} else {
			this.auditTableFieldDao.updateAuditTableField(auditTableField);
		}
	}


	/**
	 * 通过主键删除贷款审合表单字段配置
	 * @param id 主键Id
	 */
	public void deleteAuditTableFieldById(Integer id){
		this.auditTableFieldDao.deleteAuditTableFieldById(id);
	}


	public void deleteAuditTableFieldByLinkId(Integer linkId, LoanLinkTypeEnum type) {
		this.auditTableFieldDao.deleteAuditTableFieldByLinkId(linkId, type);
	}

	/**
	 * 通过主键得到贷款审合表单字段配置
	 * @param id 主键Id
	 */
	public LoanAuditTableField getAuditTableFieldById(Integer id){
		return this.auditTableFieldDao.getAuditTableFieldById(id);
	}

	/**
	 * 查询贷款审合表单字段配置
	 * @param condition 查询条件
	 * @return
	 */
	public List<LoanAuditTableField> queryAuditTableFieldList(Map<String,Object> condition){
		return this.auditTableFieldDao.queryAuditTableFieldList(condition);
	}

	/**
	 * 分页查询贷款审合表单字段配置
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<LoanAuditTableField> queryAuditTableFieldList(Map<String,Object> condition,IPageSize page){
		return this.auditTableFieldDao.queryAuditTableFieldList(condition,page);
	}

	/**
	 * 查询选择审批字段
	 */
	public List<LoanAuditTableFieldExtend> queryAuditTableFieldSelect(Integer processId) {
		return this.auditTableFieldDao.queryAuditTableFieldSelect(processId);
	}

	/**
	 * 保存选择审批字段
	 */
	public void saveOrUpdateLoanAuditTableField(Integer loginUserId, Integer processId, Integer typeId, Integer paramId, JSONArray jsonArray) {

		for (Object o : jsonArray) {
			JSONObject jsonObject = JSONObject.fromObject(o);
			// conditionId和paramId 先搁置
			LoanAuditTableField loanAuditTableField = new LoanAuditTableField();
			int id = jsonObject.getInt("id");
			if (id == 0) {
				// tableId写死
				loanAuditTableField.setTableId(15);
				loanAuditTableField.setLoanAuditProcessId(processId);
				loanAuditTableField.setLoanTypeId(typeId);
				loanAuditTableField.setLoanParamId(paramId);
				loanAuditTableField.setFieldId(jsonObject.getInt("fieldId"));
			}
			loanAuditTableField.setId(id);
			loanAuditTableField.setFieldIsRequired(jsonObject.getInt("fieldIsRequired"));
			loanAuditTableField.setFieldWebIsShow(jsonObject.getInt("fieldWebIsShow"));
			loanAuditTableField.setFieldAppIsShow(jsonObject.getInt("fieldAppIsShow"));
			saveAuditTableField(loanAuditTableField, loginUserId);
		}
	}

}
