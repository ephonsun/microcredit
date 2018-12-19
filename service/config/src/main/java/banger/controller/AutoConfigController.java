package banger.controller;

import banger.common.BaseController;
import banger.common.tools.JsonUtil;
import banger.domain.config.AutoTableInfo;
import banger.framework.pagesize.IPageList;
import banger.service.intf.ITableInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * 页面访问类
 */
@Controller
@RequestMapping("/AutoTableInfo")
public class AutoConfigController extends BaseController {
	private static final long serialVersionUID = 3865851031774807121L;
	@Autowired
	private ITableInfoService tableInfoService;

	/**
	 * 得到列表页面
	 * @return
	 */
	@RequestMapping("/getTableInfoListPage")
	public String getTableInfoListPage(){
		Map<String,Object> condition = new HashMap<String,Object>();
		IPageList<AutoTableInfo> tableInfoList = tableInfoService.queryTableInfoList(condition,this.getPage());
		setAttribute("tableInfoList",tableInfoList);
		return SUCCESS;
	}

	/**
	 * 查询列表数据
	 * @return
	 */
	@RequestMapping("/queryTableInfoListData")
	public String queryTableInfoListData(){
		Map<String,Object> condition = new HashMap<String,Object>();
		IPageList<AutoTableInfo> tableInfoList = tableInfoService.queryTableInfoList(condition,this.getPage());
		renderText(JsonUtil.toJson(tableInfoList, "tableId","updateUser,createUser,updateDate,createDate,tableName,tableDisplay,tableComment,isFixed,isActived,tableType").toString());
		return null;
	}

	/**
	 * 得到新增页面
	 * @return
	 */
	@RequestMapping("/getTableInfoInsertPage")
	public String getTableInfoInsertPage(){
		AutoTableInfo tableInfo = new AutoTableInfo();
		setAttribute("tableInfo",tableInfo);
		return SUCCESS;
	}

	/**
	 * 得到修改页面
	 * @return
	 */
	@RequestMapping("/getTableInfoUpdatePage")
	public String getTableInfoUpdatePage(){
		String tableId = getParameter("tableId");
		AutoTableInfo tableInfo = tableInfoService.getTableInfoById(Integer.parseInt(tableId));
		setAttribute("tableInfo",tableInfo);
		return SUCCESS;
	}

	/**
	 * 得到查看页面
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
	 * 新增数据
	 * @return
	 */
	@RequestMapping("/insertTableInfo")
	public String insertTableInfo(@ModelAttribute AutoTableInfo tableInfo){
		Integer loginUserId = getLoginInfo().getUserId();
		tableInfoService.insertTableInfo(tableInfo,loginUserId);
		renderText(SUCCESS);
		return null;
	}

	/**
	 * 修改数据
	 * @return
	 */
	@RequestMapping("/updateTableInfo")
	public String updateTableInfo(@ModelAttribute AutoTableInfo tableInfo){
		Integer loginUserId = getLoginInfo().getUserId();
		tableInfoService.updateTableInfo(tableInfo,loginUserId);
		renderText(SUCCESS);
		return null;
	}

	/**
	 * 删除数据
	 * @return
	 */
	@RequestMapping("/deleteTableInfo")
	public String deleteTableInfo(){
		String tableId = getParameter("tableId");
		tableInfoService.deleteTableInfoById(Integer.parseInt(tableId));
		renderText(SUCCESS);
		return null;
	}

}
