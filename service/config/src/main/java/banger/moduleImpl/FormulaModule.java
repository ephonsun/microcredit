package banger.moduleImpl;

import banger.dao.intf.IColumnFormulaDao;
import banger.dao.intf.IColumnFormulaParamsDao;
import banger.domain.config.AutoBaseTemplate;
import banger.domain.config.AutoColumnFormula;
import banger.domain.config.AutoColumnFormulaParams;
import banger.framework.collection.DataRow;
import banger.framework.web.dojo.ObjectConvert;
import banger.moduleIntf.IFormulaModule;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自定义字段合计项公式业务访问类
 */
@Repository
public class FormulaModule implements IFormulaModule {

	@Autowired
	private IColumnFormulaDao columnFormulaDao;
	@Autowired
	private IColumnFormulaParamsDao columnFormulaParamsDao;



	/**
	 * 通过主键得到自定义字段合计项公式
	 * @param ID 主键Id
	 */
	@Override
	public AutoColumnFormula getColumnFormulaById(Integer id){
		return this.columnFormulaDao.getColumnFormulaById(id);
	}

	/**
	 * 查询自定义字段合计项公式
	 * @param condition 查询条件
	 * @return
	 */
	@Override
	public List<AutoColumnFormula> queryColumnFormulaList(Map<String, Object> condition){
		return this.columnFormulaDao.queryColumnFormulaList(condition);
	}

	@Override
	public List<AutoColumnFormula> queryTableFormulaList(String tableName) {
		return this.columnFormulaDao.queryTableFormulaList(tableName);
	}


	/**
	 * 通过主键得到自定义字段合计项公式参数
	 * @param ID 主键Id
	 */
	@Override
	public AutoColumnFormulaParams getColumnFormulaParamsById(Integer id){
		return this.columnFormulaParamsDao.getColumnFormulaParamsById(id);
	}

	/**
	 * 查询自定义字段合计项公式参数
	 * @param condition 查询条件
	 * @return
	 */
	@Override
	public List<AutoColumnFormulaParams> queryColumnFormulaParamsList(Map<String, Object> condition){
		return this.columnFormulaParamsDao.queryColumnFormulaParamsList(condition);
	}

	@Override
	public AutoBaseTemplate getTableFormulaByTemplate(AutoBaseTemplate template) {
		//表单合计
		if ("List".equals(template.getType())&&null!=template.getData()){
			StringBuffer tableFormulaBuf = new StringBuffer();
			AutoColumnFormula formula ; BigDecimal sum ; Object number ;
			DataRow[] rows = (DataRow[])template.getData();

			HashMap<String,Object> condition = new HashMap<String, Object>();
			condition.put("tableName",template.getTableName());
			condition.put("isActived",1);
			List<AutoColumnFormula> tableFormulaList = this.queryColumnFormulaList(condition);
			if(CollectionUtils.isNotEmpty(tableFormulaList)){
				for (int i = 0; i < tableFormulaList.size(); i++) {
					formula = tableFormulaList.get(i);sum = new BigDecimal(0);
					if(i!=0){ tableFormulaBuf.append("，"); }
					//名称
					tableFormulaBuf.append(formula.getFieldColumn()).append("：");					//计算
					for (int j = 0; j < rows.length; j++) {
						number = rows[j].get(formula.getFormulaExpressions());
						if(null!=number&& StringUtils.isNotBlank(number.toString())){
							sum = sum.add(new BigDecimal(number.toString()));
						}
					}
					//处理单位 小数点后多余的0
					sum = sum.stripTrailingZeros();

					tableFormulaBuf.append(sum.toPlainString());
					//单位
					tableFormulaBuf.append(template.getUnitByColumnName(formula.getFormulaExpressions()));

				}

			}

			template.setTableFormula(tableFormulaBuf.toString());

		}
		return template;
	}


}
