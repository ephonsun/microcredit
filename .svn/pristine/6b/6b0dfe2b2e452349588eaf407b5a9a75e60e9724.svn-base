package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.domain.loan.LoanFieldExtend;
import banger.domain.loan.LoanTypeTableField;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

/**
 * 贷款类型表单字段配置业务访问接口
 */
public interface ITypeTableFieldService {

	/**
	 * 新增贷款类型表单字段配置
	 * @param typeTableField 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertTypeTableField(LoanTypeTableField typeTableField,Integer loginUserId);

	/**
	 *修改贷款类型表单字段配置
	 * @param typeTableField 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateTypeTableField(LoanTypeTableField typeTableField,Integer loginUserId);

	/**
	 * 通过主键删除贷款类型表单字段配置
	 * @param ID 主键Id
	 */
	void deleteTypeTableFieldById(Integer id);

	/**
	 * 通过主键得到贷款类型表单字段配置
	 * @param ID 主键Id
	 */
	LoanTypeTableField getTypeTableFieldById(Integer id);

	/**
	 * 查询贷款类型表单字段配置
	 * @param condition 查询条件
	 * @return
	 */
	List<LoanTypeTableField> queryTypeTableFieldList(Map<String,Object> condition);

	/**
	 * 分页查询贷款类型表单字段配置
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<LoanTypeTableField> queryTypeTableFieldList(Map<String,Object> condition,IPageSize page);

	/**
	 * 根据条件联表查询自定义字段集合
	 * 
	 * @param condition
	 * @return
	 */
	List<LoanFieldExtend> queryTypeTableFieldListByCondition(Map<String, Object> condition);

	/**
	 * 保存或修改
	 *  @param loginUserId
	 * @param tableId
     * @param typeId
     * @param precType
     * @param fieldId
     * @param fieldIsRequired
     * @param fieldIsCondition
     * @param fieldWebIsShow
     * @param fieldAppIsShow
     * @param refIds
     */
	void saveOrUpdateTypeTableField(Integer loginUserId, Integer tableId, Integer typeId, String precType,
                                    String[] fieldId, String[] fieldIsRequired,
                                    String[] fieldIsCondition, String[] fieldWebIsShow, String[] fieldAppIsShow, String[] refIds);

}
