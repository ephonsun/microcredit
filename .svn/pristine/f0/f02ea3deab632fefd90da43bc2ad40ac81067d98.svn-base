package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.domain.enumerate.LoanLinkTypeEnum;
import banger.domain.loan.LoanAuditTableField;
import banger.domain.loan.LoanAuditTableFieldExtend;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import net.sf.json.JSONArray;

/**
 * 贷款审合表单字段配置业务访问接口
 */
public interface IAuditTableFieldService {

	/**
	 * 保存贷款审合表单字段配置
	 * @param auditTableField 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void saveAuditTableField(LoanAuditTableField auditTableField,Integer loginUserId);


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
	 * @param ID 主键Id
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
	IPageList<LoanAuditTableField> queryAuditTableFieldList(Map<String, Object> condition, IPageSize page);

	/**
	 * 查询选择审批字段
	 */
	List<LoanAuditTableFieldExtend> queryAuditTableFieldSelect(Integer processId);

	/**
	 * 保存选择审批字段
	 */
	void saveOrUpdateLoanAuditTableField(Integer loginUserId, Integer processId, Integer typeId, Integer paramId, JSONArray jsonArray);

}
