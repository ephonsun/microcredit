package banger.dao.intf;

import banger.domain.config.AutoColumnFormula;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

import java.util.List;
import java.util.Map;

/**
 * 自定义字段合计项公式数据访问接口
 */
public interface IColumnFormulaDao {

	/**
	 * 新增自定义字段合计项公式
	 * @param columnFormula 实体对像
	 */
	void insertColumnFormula(AutoColumnFormula columnFormula);

	/**
	 *修改自定义字段合计项公式
	 * @param columnFormula 实体对像
	 */
	void updateColumnFormula(AutoColumnFormula columnFormula);

	/**
	 * 通过主键删除自定义字段合计项公式
	 * @param id 主键Id
	 */
	void deleteColumnFormulaById(Integer id);

	/**
	 * 通过主键得到自定义字段合计项公式
	 * @param id 主键Id
	 */
	AutoColumnFormula getColumnFormulaById(Integer id);

	/**
	 * 查询自定义字段合计项公式
	 * @param condition 查询条件
	 * @return
	 */
	List<AutoColumnFormula> queryColumnFormulaList(Map<String, Object> condition);

	/**
	 * 分页查询自定义字段合计项公式
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<AutoColumnFormula> queryColumnFormulaList(Map<String, Object> condition, IPageSize page);

	//根据表明查询合计项
	List<AutoColumnFormula> queryTableFormulaList(String tableName);
}
