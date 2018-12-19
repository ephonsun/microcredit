package banger.moduleIntf;

import banger.domain.config.AutoBaseTemplate;
import banger.domain.config.AutoColumnFormula;
import banger.domain.config.AutoColumnFormulaParams;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

import java.util.List;
import java.util.Map;

/**
 * 自定义字段合计项公式业务访问接口
 */
public interface IFormulaModule {


	AutoColumnFormula getColumnFormulaById(Integer id);

	List<AutoColumnFormula> queryColumnFormulaList(Map<String, Object> condition);

	List<AutoColumnFormula> queryTableFormulaList(String tableName);

	AutoColumnFormulaParams getColumnFormulaParamsById(Integer id);

	List<AutoColumnFormulaParams> queryColumnFormulaParamsList(Map<String, Object> condition);

	AutoBaseTemplate getTableFormulaByTemplate(AutoBaseTemplate template);
}
