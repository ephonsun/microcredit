package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.config.AutoColumnFormulaParams;

/**
 * 自定义字段合计项公式参数业务访问接口
 */
public interface IColumnFormulaParamsService {

	/**
	 * 新增自定义字段合计项公式参数
	 * @param columnFormulaParams 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertColumnFormulaParams(AutoColumnFormulaParams columnFormulaParams, Integer loginUserId);

	/**
	 *修改自定义字段合计项公式参数
	 * @param columnFormulaParams 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateColumnFormulaParams(AutoColumnFormulaParams columnFormulaParams, Integer loginUserId);

	/**
	 * 通过主键删除自定义字段合计项公式参数
	 * @param ID 主键Id
	 */
	void deleteColumnFormulaParamsById(Integer id);

	/**
	 * 通过主键得到自定义字段合计项公式参数
	 * @param ID 主键Id
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
