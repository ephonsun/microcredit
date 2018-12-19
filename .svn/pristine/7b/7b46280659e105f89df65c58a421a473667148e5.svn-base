package banger.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import banger.domain.config.ModeTemplateInfo;
import banger.domain.config.SysFormSettings;
import banger.moduleIntf.IFormSettingsProvider;
import banger.service.intf.IFormSettingsService;
import banger.service.intf.ITemplateInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import banger.common.BaseController;
import banger.common.constant.GlobalConst;
import banger.common.tools.JsonUtil;
import banger.domain.config.AutoTableInfo;
import banger.framework.pagesize.IPageList;
import banger.service.intf.ITableInfoService;

/**
 * 自定义表信息页面访问类
 */
@Controller
@RequestMapping("/tableTemplate")
public class TableInfoController extends BaseController {
	private static final long serialVersionUID = 3037269482825726125L;
	@Autowired
	private ITableInfoService tableInfoService;
	@Autowired
	private ITemplateInfoService templateInfoService;
	@Autowired
	private IFormSettingsProvider formSettingsProvider;
	/**
	 * 得到自定义表信息列表页面
	 * @return
	 */
	@RequestMapping("/getTableInfoListPage")
	public String getTableInfoListPage(){
		Map<String,Object> condition = new HashMap<String,Object>();
		IPageList<AutoTableInfo> tableInfoList = tableInfoService.queryTableInfoList(condition,this.getPage());
		setAttribute("loginUserName",getLoginInfo().getUserName());
		setAttribute("tableInfoList",tableInfoList);
		return "/config/vm/sysTableInfoList";
	}

	/**
	 * 查询自定义表信息列表数据
	 * @return
	 */
	@RequestMapping("/queryTableInfoListData")
	public void queryTableInfoListData(){
		Map<String,Object> condition = new HashMap<String,Object>();
//		condition.put("tableModule","LOAN");
		IPageList<AutoTableInfo> tableInfoList = tableInfoService.queryTableInfoList(condition, this.getPage());
		renderText(JsonUtil.toJson(tableInfoList, "tableId","updateUser,createUser,tableDbName,createDate,updateDate,tableDisplayName,tableModuleName,tableComment,isFixed,isActived").toString());
	}

	/**
	 * 启用自定义表单模版
	 * @return
	 */
	@RequestMapping("/enable/{id}")
	public void enableTableTemplate(@PathVariable("id") Integer id) {
		Integer loginUserId = getLoginInfo().getUserId();
		AutoTableInfo tableInfo = new AutoTableInfo();
		tableInfo.setTableId(id);
		tableInfo.setIsActived(GlobalConst.YesOrNo.YES);
		tableInfoService.updateTableInfo(tableInfo,loginUserId);
		renderText(SUCCESS);
	}

	/**
	 * 禁用自定义表单模版
	 * 
	 * @return
	 */
	@RequestMapping("/disable/{id}")
	public void disableTableTemplate(@PathVariable("id") Integer id) {
		Integer loginUserId = getLoginInfo().getUserId();
		AutoTableInfo tableInfo = new AutoTableInfo();
		tableInfo.setTableId(id);
		tableInfo.setIsActived(GlobalConst.YesOrNo.NO);
		tableInfoService.updateTableInfo(tableInfo,loginUserId);
		renderText(SUCCESS);
	}

	/**
	 * 得到自定义表信息新增页面
	 * @return
	 */
	@RequestMapping("/getTableInfoInsertPage")
	public String getTableInfoInsertPage(){
		AutoTableInfo tableInfo = new AutoTableInfo();
		setAttribute("tableInfo",tableInfo);
		return SUCCESS;
	}

	/**
	 * 得到自定义表信息修改页面
	 * @return
	 */
	@RequestMapping("/getTableInfoUpdatePage")
	public String getTableInfoUpdatePage(){
		String tableId = getParameter("tableId");
		AutoTableInfo tableInfo = tableInfoService.getTableInfoById(Integer.parseInt(tableId));
		setAttribute("tableInfo",tableInfo);
		return "/config/vm/tableInfoListUpdate";
	}

	/**
	 * 得到自定义表信息查看页面
	 * @return
	 */
	@RequestMapping("/getTableInfoDetailPage")
	public String getTableInfoDetailPage(){
		String tableId = getParameter("tableId");
		AutoTableInfo tableInfo = tableInfoService.getTableInfoById(Integer.parseInt(tableId));
		setAttribute("tableInfo",tableInfo);
		return SUCCESS;
	}

	/**
	 * 新增自定义表信息数据
	 * @return
	 */
	@RequestMapping("/insertTableInfo")
	public void insertTableInfo(AutoTableInfo tableInfo){
		Integer loginUserId = getLoginInfo().getUserId();
		tableInfoService.insertTableInfo(tableInfo,loginUserId);
		renderText(SUCCESS);
	}

	/**
	 * 修改自定义表信息数据
	 * @return
	 */
	@RequestMapping("/updateTableInfo")
	public void updateTableInfo(AutoTableInfo tableInfo){
		Integer loginUserId = getLoginInfo().getUserId();
		tableInfoService.updateTableInfo(tableInfo,loginUserId);
		//同步修改MODE_TEMPLATE_INFO表
		ModeTemplateInfo templateInfoById = templateInfoService.getTemplateInfoById(tableInfo.getTableId());
		if (templateInfoById != null) {
			templateInfoById.setTableName(tableInfo.getTableDisplayName());
			templateInfoService.updateTemplateInfo(templateInfoById,loginUserId);
		}
		//同步修改SYS_FORM_SETTINGS表
		formSettingsProvider.updateDisplayNameByTableName(tableInfo.getTableDisplayName(),tableInfo.getTableDbName());
		renderText(SUCCESS);
	}

	/**
	 * 删除自定义表信息数据
	 * @return
	 */
	@RequestMapping("/deleteTableInfo")
	public void deleteTableInfo(){
		String tableId = getParameter("tableId");
		tableInfoService.deleteTableInfoById(Integer.parseInt(tableId));
		renderText(SUCCESS);
	}

	/**
	 * 校验模块显示名称
	 * 
	 * @param loanTypeName
	 */
	@RequestMapping("checkTableInfoNameRule")
	@ResponseBody
	public void checkTableInfoNameRule(@RequestParam("tableDisplayName") String tableDisplayName,
			@RequestParam("tableId") Integer tableId) {

		Map<String, Object> condition = new HashMap<String, Object>();

		AutoTableInfo tableInfoById = tableInfoService.getTableInfoById(tableId);

		if (null != tableInfoById && tableDisplayName.equals(tableInfoById.getTableDisplayName())) {
			renderText(true, "", null);
		}
		condition.put("tableDisplayName", tableDisplayName);
		List<AutoTableInfo> list = tableInfoService.queryTableInfoList(condition);
		if (null == list) {
			return;
		}
		// 如果数据库中存在相同的名字
		if (list.size() > 0) {
			renderText(false, "显示名称已经存在，请重新输入", null);
		} else {
			renderText(true, "", null);
		}

	}
}
