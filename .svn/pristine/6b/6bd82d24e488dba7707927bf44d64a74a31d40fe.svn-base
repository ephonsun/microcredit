package banger.dao.impl;

import banger.dao.intf.IColumnFormulaDao;
import banger.domain.config.AutoColumnFormula;
import banger.framework.dao.PageSizeDao;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 自定义字段合计项公式数据访问类
 */
@Repository
public class ColumnFormulaDao extends PageSizeDao<AutoColumnFormula> implements IColumnFormulaDao {

	/**
	 * 新增自定义字段合计项公式
	 * @param columnFormula 实体对像
	 */
	public void insertColumnFormula(AutoColumnFormula columnFormula){
		columnFormula.setId(this.newId().intValue());
		this.execute("insertColumnFormula",columnFormula);
	}

	/**
	 *修改自定义字段合计项公式
	 * @param columnFormula 实体对像
	 */
	public void updateColumnFormula(AutoColumnFormula columnFormula){
		this.execute("updateColumnFormula",columnFormula);
	}

	/**
	 * 通过主键删除自定义字段合计项公式
	 * @param id 主键Id
	 */
	public void deleteColumnFormulaById(Integer id){
		this.execute("deleteColumnFormulaById",id);
	}

	/**
	 * 通过主键得到自定义字段合计项公式
	 * @param id 主键Id
	 */
	public AutoColumnFormula getColumnFormulaById(Integer id){
		return (AutoColumnFormula)this.queryEntity("getColumnFormulaById",id);
	}

	/**
	 * 查询自定义字段合计项公式
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<AutoColumnFormula> queryColumnFormulaList(Map<String,Object> condition){
		return (List<AutoColumnFormula>)this.queryEntities("queryColumnFormulaList", condition);
	}

	/**
	 * 分页查询自定义字段合计项公式
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<AutoColumnFormula> queryColumnFormulaList(Map<String,Object> condition,IPageSize page){
		return (IPageList<AutoColumnFormula>)this.queryEntities("queryColumnFormulaList", page, condition);
	}

	//根据表名查询合计项
	@Override
	public List<AutoColumnFormula> queryTableFormulaList(String tableName) {
		return (List<AutoColumnFormula>)this.queryEntities("queryTableFormulaList", tableName);
	}

}
