package banger.dao.intf;

import banger.domain.config.AutoColumnFormulaParams;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

import java.util.List;
import java.util.Map;

/**
 * 自定义字段合计项公式参数数据访问接口
 */
public interface IColumnFormulaParamsDao {

	/**
	 * 新增自定义字段合计项公式参数
	 * @param columnFormulaParams 实体对像
	 */
	void insertColumnFormulaParams(AutoColumnFormulaParams columnFormulaParams);

	/**
	 *修改自定义字段合计项公式参数
	 * @param columnFormulaParams 实体对像
	 */
	void updateColumnFormulaParams(AutoColumnFormulaParams columnFormulaParams);

	/**
	 * 通过主键删除自定义字段合计项公式参数
	 * @param id 主键Id
	 */
	void deleteColumnFormulaParamsById(Integer id);

	/**
	 * 通过主键得到自定义字段合计项公式参数
	 * @param id 主键Id
	 */
	AutoColumnFormulaParams getColumnFormulaParamsById(Integer id);

	/**
	 * 查询自定义字段合计项公式参数
	 * @param condition 查询条件
	 * @return
	 */
	List<AutoColumnFormulaParams> queryColumnFormulaParamsList(Map<String, Object> condition);

	/**
	 * 分页查询自定义字段合计项公式参数
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<AutoColumnFormulaParams> queryColumnFormulaParamsList(Map<String, Object> condition, IPageSize page);

}
