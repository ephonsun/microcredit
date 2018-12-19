package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.domain.enumerate.LoanLinkTypeEnum;
import banger.domain.loan.LoanAuditTableField;
import banger.domain.loan.LoanAuditTableFieldExtend;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

/**
 * 贷款审合表单字段配置数据访问接口
 */
public interface IAuditTableFieldDao {

	/**
	 * 新增贷款审合表单字段配置
	 * @param auditTableField 实体对像
	 */
	void insertAuditTableField(LoanAuditTableField auditTableField);

	/**
	 *修改贷款审合表单字段配置
	 * @param auditTableField 实体对像
	 */
	void updateAuditTableField(LoanAuditTableField auditTableField);

	/**
	 * 通过主键删除贷款审合表单字段配置
	 * @param id 主键Id
	 */
	void deleteAuditTableFieldById(Integer id);

	/**
	 * 根据关联类型和关联id删除相关配置
	 * @param linkId
	 * @param type
	 */
	void deleteAuditTableFieldByLinkId(Integer linkId, LoanLinkTypeEnum type);

	/**
	 * 通过主键得到贷款审合表单字段配置
	 * @param id 主键Id
	 */
	LoanAuditTableField getAuditTableFieldById(Integer id);

	/**
	 * 查询贷款审合表单字段配置
	 * @param condition 查询条件
	 * @return
	 */
	List<LoanAuditTableField> queryAuditTableFieldList(Map<String,Object> condition);

	/**
	 * 分页查询贷款审合表单字段配置
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<LoanAuditTableField> queryAuditTableFieldList(Map<String,Object> condition,IPageSize page);

	/**
	 * 查询选择审批字段
	 */
	List<LoanAuditTableFieldExtend> queryAuditTableFieldSelect(Integer processId);

}
