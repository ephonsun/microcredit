package banger.controller;

import banger.common.BaseController;
import banger.common.tools.JsonUtil;
import banger.domain.config.AutoColumnFormula;
import banger.domain.loan.contract.LoanContractTemplateType;
import banger.service.intf.IColumnFormulaService;
import banger.service.intf.ITableInfoService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自定义表信息页面访问类
 */
@Controller
@RequestMapping("/formula")
public class FormulaController extends BaseController {

	private static final long serialVersionUID = -2921478955461808478L;
	@Autowired
	private ITableInfoService tableInfoService;
	@Autowired
	private IColumnFormulaService columnFormulaService;
	/**
	 * 得到自定义表信息列表页面
	 * @return
	 */
	@RequestMapping("/getTableFormulaListPage")
	public String getTableFormulaListPage(){
		setAttribute("tableName",getParameter("tableName"));
		return "/config/vm/formula/tableFormulaList";
	}

	/**
	 * 新增
	 * @return
	 */
	@RequestMapping("addTableFormula")
	@ResponseBody
	public void addTableFormula(HttpServletRequest request){

		Integer userId = this.getLoginInfo().getUserId();
		String fieldColumn = this.getParameter("fieldColumn");
		if(StringUtils.isBlank(fieldColumn)){
			renderText(false,"请输入合计名称！",null);
			return;
		}
		String formulaExpressions = this.getParameter("formulaExpressions");
		if(StringUtils.isBlank(formulaExpressions)){
			renderText(false,"请选择合计项！",null);
			return;
		}
		String tableName = this.getParameter("tableName");
		if(StringUtils.isBlank(tableName)){
			renderText(false,"数据异常，请刷新页面后重试！",null);
			return;
		}

		Map<String, Object> condition = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(fieldColumn)){
			condition.put("fieldColumn",fieldColumn);
		}
		List<AutoColumnFormula> formulaList = columnFormulaService.queryColumnFormulaList(condition);
		if (!CollectionUtils.isEmpty(formulaList)){
			renderText(false,"合计名称已存在！",null);
			return;
		}

		AutoColumnFormula tableFormula = new AutoColumnFormula();
		tableFormula.setTableName(tableName);
		tableFormula.setFieldColumn(fieldColumn);
		tableFormula.setFormulaExpressions(formulaExpressions);
		tableFormula.setIsStatistics(1);
		tableFormula.setIsActived(1);
		columnFormulaService.insertColumnFormula(tableFormula,userId);

		renderText(true, "", null);
	}




	/**
	 * 查询自定义表信息列表数据
	 * @return
	 */
	@RequestMapping("/queryTableFormulaList")
	@ResponseBody
	public void queryTableFormulaList(){
		String tableName = getParameter("tableName");
		List<AutoColumnFormula> autoColumnFormula = columnFormulaService.queryTableFormulaList(tableName);
		renderText(JsonUtil.toJson(autoColumnFormula, "id","fieldColumn,formulaExpressions,formulaExpressionsName,isActived").toString());
	}


	/**
	 * 删除
	 * @return
	 */
	@RequestMapping("delTableFormula")
	@ResponseBody
	public void delTableFormula(HttpServletRequest request){

		String strId = this.getParameter("id");
		if(StringUtils.isBlank(strId)||!StringUtils.isNumeric(strId)){
			renderText(false,"数据异常，请刷新页面后重试！",null);
			return;
		}

		columnFormulaService.deleteColumnFormulaById(Integer.valueOf(strId));

		renderText(true, "", null);
	}

	/**
	 * 更新状态
	 * @return
	 */
	@RequestMapping("updateFormulaActived")
	@ResponseBody
	public void updateFormulaActived(HttpServletRequest request){

		Integer userId = this.getLoginInfo().getUserId();

		String strId = this.getParameter("id");
		String isActived = this.getParameter("isActived");
		if(StringUtils.isBlank(strId)||!StringUtils.isNumeric(strId)||StringUtils.isBlank(isActived)||!StringUtils.isNumeric(isActived)){
			renderText(false,"数据异常，请刷新页面后重试！",null);
			return;
		}

		AutoColumnFormula tableFormula = columnFormulaService.getColumnFormulaById(Integer.valueOf(strId));
		if(null!=tableFormula){
			tableFormula.setIsActived(Integer.valueOf(isActived));
			columnFormulaService.updateColumnFormula(tableFormula,userId);
		}

		renderText(true, "", null);
	}
}
