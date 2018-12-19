package banger.dao.impl;

import banger.dao.intf.IColumnFormulaParamsDao;
import banger.domain.config.AutoColumnFormulaParams;
import banger.framework.dao.PageSizeDao;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 自定义字段合计项公式参数数据访问类
 */
@Repository
public class ColumnFormulaParamsDao extends PageSizeDao<AutoColumnFormulaParams> implements IColumnFormulaParamsDao {

	/**
	 * 新增自定义字段合计项公式参数
	 * @param columnFormulaParams 实体对像
	 */
	public void insertColumnFormulaParams(AutoColumnFormulaParams columnFormulaParams){
		columnFormulaParams.setId(this.newId().intValue());
		this.execute("insertColumnFormulaParams",columnFormulaParams);
	}

	/**
	 *修改自定义字段合计项公式参数
	 * @param columnFormulaParams 实体对像
	 */
	public void updateColumnFormulaParams(AutoColumnFormulaParams columnFormulaParams){
		this.execute("updateColumnFormulaParams",columnFormulaParams);
	}

	/**
	 * 通过主键删除自定义字段合计项公式参数
	 * @param id 主键Id
	 */
	public void deleteColumnFormulaParamsById(Integer id){
		this.execute("deleteColumnFormulaParamsById",id);
	}

	/**
	 * 通过主键得到自定义字段合计项公式参数
	 * @param id 主键Id
	 */
	public AutoColumnFormulaParams getColumnFormulaParamsById(Integer id){
		return (AutoColumnFormulaParams)this.queryEntity("getColumnFormulaParamsById",id);
	}

	/**
	 * 查询自定义字段合计项公式参数
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<AutoColumnFormulaParams> queryColumnFormulaParamsList(Map<String,Object> condition){
		return (List<AutoColumnFormulaParams>)this.queryEntities("queryColumnFormulaParamsList", condition);
	}

	/**
	 * 分页查询自定义字段合计项公式参数
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<AutoColumnFormulaParams> queryColumnFormulaParamsList(Map<String,Object> condition,IPageSize page){
		return (IPageList<AutoColumnFormulaParams>)this.queryEntities("queryColumnFormulaParamsList", page, condition);
	}

}
