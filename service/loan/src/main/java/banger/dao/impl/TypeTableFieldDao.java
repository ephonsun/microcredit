package banger.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import banger.dao.intf.ITypeTableFieldDao;
import banger.domain.loan.LoanFieldExtend;
import banger.domain.loan.LoanTypeTableField;
import banger.framework.dao.PageSizeDao;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

/**
 * 贷款类型表单字段配置数据访问类
 */
@Repository
public class TypeTableFieldDao extends PageSizeDao<LoanTypeTableField> implements ITypeTableFieldDao {

	/**
	 * 新增贷款类型表单字段配置
	 * @param typeTableField 实体对像
	 */
	public void insertTypeTableField(LoanTypeTableField typeTableField){
		typeTableField.setId(this.newId().intValue());
		this.execute("insertTypeTableField",typeTableField);
	}

	/**
	 *修改贷款类型表单字段配置
	 * @param typeTableField 实体对像
	 */
	public void updateTypeTableField(LoanTypeTableField typeTableField){
		this.execute("updateTypeTableField",typeTableField);
	}

	/**
	 * 通过主键删除贷款类型表单字段配置
	 * @param id 主键Id
	 */
	public void deleteTypeTableFieldById(Integer id){
		this.execute("deleteTypeTableFieldById",id);
	}

	/**
	 * 通过主键得到贷款类型表单字段配置
	 * @param id 主键Id
	 */
	public LoanTypeTableField getTypeTableFieldById(Integer id){
		return (LoanTypeTableField)this.queryEntity("getTypeTableFieldById",id);
	}

	/**
	 * 查询贷款类型表单字段配置
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LoanTypeTableField> queryTypeTableFieldList(Map<String,Object> condition){
		return (List<LoanTypeTableField>)this.queryEntities("queryTypeTableFieldList", condition);
	}

	/**
	 * 分页查询贷款类型表单字段配置
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<LoanTypeTableField> queryTypeTableFieldList(Map<String,Object> condition,IPageSize page){
		return (IPageList<LoanTypeTableField>)this.queryEntities("queryTypeTableFieldList", page, condition);
	}

	/**
	 * 根据条件联表查询自定义字段集合
	 * 
	 * @param condition
	 */
	@SuppressWarnings("unchecked")
	public List<LoanFieldExtend> queryTypeTableFieldListByCondition(Map<String, Object> condition) {
		return (List<LoanFieldExtend>) this.queryEntities("queryTypeTableFieldListByCondition", condition);
	}

	/**
	 * 根据fieldId查询
	 * 
	 * @param valueOf
	 * @return
	 */
	public LoanTypeTableField queryTypeTableFieldByFieldId(Integer fieldId) {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("fieldId", fieldId);
		return (LoanTypeTableField) this.queryEntities("queryTypeTableFieldByFieldId", condition);
	}

    @Override
    public List<LoanFieldExtend> queryTypeTableFieldListLive(Map<String, Object> condition) {
        return (List<LoanFieldExtend>) this.queryEntities("queryTypeTableFieldListLive",condition);
    }

	@Override
	public List<LoanFieldExtend> queryTypeTableFieldListData(Map<String, Object> condition) {
		return (List<LoanFieldExtend>) this.queryEntities("queryTypeTableFieldListData",condition);
	}

}
