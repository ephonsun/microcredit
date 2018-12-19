package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.config.AutoColumnFormula;

/**
 * 自定义字段合计项公式业务访问接口
 */
public interface IColumnFormulaService {

	/**
	 * 新增自定义字段合计项公式
	 * @param columnFormula 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertColumnFormula(AutoColumnFormula columnFormula, Integer loginUserId);

	/**
	 *修改自定义字段合计项公式
	 * @param columnFormula 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateColumnFormula(AutoColumnFormula columnFormula, Integer loginUserId);

	/**
	 * 通过主键删除自定义字段合计项公式
	 * @param ID 主键Id
	 */
	void deleteColumnFormulaById(Integer id);

	/**
	 * 通过主键得到自定义字段合计项公式
	 * @param ID 主键Id
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

//	根据表名查询合计项
	List<AutoColumnFormula> queryTableFormulaList(String tableName);
}
