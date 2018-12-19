package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.domain.loan.LoanFieldExtend;
import banger.domain.loan.LoanTypeTableField;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

/**
 * 贷款类型表单字段配置数据访问接口
 */
public interface ITypeTableFieldDao {

	/**
	 * 新增贷款类型表单字段配置
	 * @param typeTableField 实体对像
	 */
	void insertTypeTableField(LoanTypeTableField typeTableField);

	/**
	 *修改贷款类型表单字段配置
	 * @param typeTableField 实体对像
	 */
	void updateTypeTableField(LoanTypeTableField typeTableField);

	/**
	 * 通过主键删除贷款类型表单字段配置
	 * @param id 主键Id
	 */
	void deleteTypeTableFieldById(Integer id);

	/**
	 * 通过主键得到贷款类型表单字段配置
	 * @param id 主键Id
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
	 */
	List<LoanFieldExtend> queryTypeTableFieldListByCondition(Map<String, Object> condition);

	/**
	 * 根据fieldId查询
	 * 
	 * @param valueOf
	 * @return
	 */
	LoanTypeTableField queryTypeTableFieldByFieldId(Integer fieldId);

	List<LoanFieldExtend> queryTypeTableFieldListLive(Map<String, Object> condition);

	List<LoanFieldExtend> queryTypeTableFieldListData(Map<String, Object> condition);
}
