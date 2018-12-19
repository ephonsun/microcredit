package banger.controller;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import banger.service.intf.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import banger.common.BaseController;
import banger.common.constant.GlobalConst;
import banger.common.tools.JsonUtil;
import banger.domain.config.AutoTableInfo;
import banger.domain.enumerate.LoanProcessTypeEnum;
import banger.domain.loan.LoanTypeRelTable;
import banger.domain.loan.LoanTypeRelTableExtend;
import banger.service.intf.IAutoTableInfoService;
import banger.service.intf.ITypeRelTableService;

@Controller
@RequestMapping("/loanTypeRelTable")
public class LoanTypeRelTableController extends BaseController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ITypeRelTableService typeRelTableService;
	@Autowired
	private IAutoTableInfoService tableInfoService;
	@Autowired
	private ITypeService typeService;
	/**
	 * 跳转页面
	 * 
	 * @param tableId
	 *            表id
	 * @param typeId
	 *            类型id
	 * @return
	 */
	@RequestMapping("/getqueryAutoTableInfoListPage")
	public ModelAndView getLoanTypeRelTablePage(@RequestParam(value = "precType") String precType,
			@RequestParam("typeId") Integer typeId) throws UnsupportedEncodingException {

		ModelAndView mv = new ModelAndView("/loan/vm/loanTypeRelTable");
		// 从枚举中获取必填项id
		LoanProcessTypeEnum valueOfType = LoanProcessTypeEnum.valueOfType(precType);
		String fixedTableIds = Arrays.toString(valueOfType.fixedTableIds);
		mv.addObject("fixedTableIds", fixedTableIds);
		mv.addObject("precTypeName", LoanProcessTypeEnum.getTypeNameByType(precType)+"表");
		mv.addObject("loanType", typeService.getTypeById(typeId).getLoanTypeName());
		mv.addObject("precType", precType);
		mv.addObject("typeId", typeId);
		return mv;

	}

	/**
	 * 根据贷款类型和流程表查询自定义表信息
	 * 
	 * @param processId
	 *            流程表id
	 * @param typeId
	 *            贷款类型
	 */
	@RequestMapping("/queryAutoTableInfoList")
	@ResponseBody
	public void queryLoanTypeRelTableList(@RequestParam("typeId") Integer typeId,
			@RequestParam("precType") String precType) {

		List<LoanTypeRelTableExtend> loanTypeRelTableExtend = typeRelTableService.queryAutoTableInfoByCondition(typeId,
				precType);

		renderText(JsonUtil.toJson(loanTypeRelTableExtend, "id",
				"tableDisplayName,isActived,tableType,loanTypeId,loanPrecType,tableId,isActivedthis").toString());

	}

	/**
	 * 启用自定义表
	 * 
	 * @param id
	 */
	@RequestMapping("/enable/{id}")
	public void enableLoanType(@PathVariable Integer id) {
		Integer loginUserId = getLoginInfo().getUserId();
		LoanTypeRelTable loanTypeRelTable = new LoanTypeRelTable();
		loanTypeRelTable.setId(id);
		loanTypeRelTable.setIsActived(GlobalConst.YesOrNo.YES);
		typeRelTableService.updateTypeRelTable(loanTypeRelTable, loginUserId);
		renderText(SUCCESS);
	}

	/**
	 * 禁用自定义表
	 * 
	 * @param id
	 */
	@RequestMapping("/disable/{id}")
	public void disableLoanType(@PathVariable Integer id) {
		Integer loginUserId = getLoginInfo().getUserId();
		LoanTypeRelTable loanTypeRelTable = new LoanTypeRelTable();
		loanTypeRelTable.setId(id);
		loanTypeRelTable.setIsActived(GlobalConst.YesOrNo.NO);
		typeRelTableService.updateTypeRelTable(loanTypeRelTable, loginUserId);
		renderText(SUCCESS);
	}

	/**
	 * 跳转新增自定义信息表
	 * 
	 * @return
	 */
	@RequestMapping("/getLoanTypeRelTableInsertPage")
	public ModelAndView getInsertLoanTypePage(@RequestParam("typeId") Integer typeId,
			@RequestParam("precType") String precType) {
		ModelAndView mv = new ModelAndView("/loan/vm/loanTypeRelTableSave");
		mv.addObject("typeId", typeId);
		mv.addObject("precType", precType);
		return mv;
	}

	/**
	 * 自定义表的新增
	 * 
	 * @param tableId
	 *            表类型 id
	 * @param typeId
	 *            贷款类型id
	 * @param precType
	 *            进度名
	 */
	@RequestMapping("/saveLoanTypeRelTable")
	public void InsertLoanTypeRelTable(@RequestParam("loanTypeRelTableSelect") Integer tableId,
			@RequestParam("typeId") Integer typeId, @RequestParam("precType") String precType) {
		Integer loginUserId = getLoginInfo().getUserId();

		LoanTypeRelTable typeRelTable = new LoanTypeRelTable();
		typeRelTable.setTableId(tableId);
		typeRelTable.setLoanTypeId(typeId);
		typeRelTable.setLoanPrecType(precType);
		// 是否启用
		typeRelTable.setIsActived(GlobalConst.YesOrNo.YES);
		// 排序号+1
		Integer maxOrderNum = typeRelTableService.queryLoanTypeRelTableMaxOrderNum(typeId, precType);
		if (null == maxOrderNum) {
			maxOrderNum = 0;
		}
		typeRelTable.setSortno(maxOrderNum + 1);
		// 保存
		typeRelTableService.insertTypeRelTable(typeRelTable, loginUserId);
	}

	/**
	 * 进入修改页面
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/getTableInfoUpdataPage")
	public String getUpdateLoanTypePage(@RequestParam("tableId") Integer tableId) {
		AutoTableInfo autoTableInfo = tableInfoService.getTableInfoById(tableId);
		setAttribute("autoTableInfo", autoTableInfo);
		return "/loan/vm/tableInfoUpdata";
	}

	/**
	 * 更新自定义表名(tableDisplayName)
	 * 
	 * @param id
	 */
	@RequestMapping("/updateTableInfo")
	public void updateLoanTypePage(@RequestParam("tableId") Integer tableId,
			@RequestParam("tableDisplayName") String tableDisplayName) {
		Integer loginUserId = getLoginInfo().getUserId();
		AutoTableInfo autoTableInfo = new AutoTableInfo();
		autoTableInfo.setTableId(tableId);
		autoTableInfo.setTableDisplayName(tableDisplayName);
		tableInfoService.updateTableInfo(autoTableInfo, loginUserId);
		renderText(SUCCESS);
	}

	/**
	 * 上移下移
	 */
	@RequestMapping("/moveTableInfoOrderNo")
	@ResponseBody
	public void moveTableInfoOrderNo(@RequestParam("typeId") Integer typeId, @RequestParam("type") String type,
			@RequestParam("precType") String precType, @RequestParam("tableId") Integer tableId) {
		typeRelTableService.moveTableInfoOrderNo(type, typeId, precType, tableId);
		this.renderText(SUCCESS);
	}

	/**
	 * 校验名字
	 * @param tableId
	 * @param tableDisplayName
	 */
	@RequestMapping("checkTableDisplayNameRule")
	public void checkTableDisplayNameRule(@RequestParam("tableId") Integer tableId,
			@RequestParam("tableDisplayName") String tableDisplayName) {
		if (tableId != null) {
			AutoTableInfo tableInfoById = tableInfoService.getTableInfoById(tableId);

			if (null != tableInfoById && tableDisplayName.equals(tableInfoById.getTableDisplayName())) {
				renderText(true, "", null);
			}
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("tableDisplayName", tableDisplayName);
			List<AutoTableInfo> list = tableInfoService.queryTableInfoList(condition);
			// 如果数据库中存在相同的名字
			if (list.size() > 0) {
				renderText(false, "自定义表名已经存在，请重新输入", null);
			} else {
				renderText(true, "", null);
			}

		}
	}
	@RequestMapping("deleteLoanType")
	public void deleteLoanType(@RequestParam("id") Integer id){
		typeRelTableService.deleteTypeRelTableById(id);
	}
}
