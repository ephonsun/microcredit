package banger.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import banger.common.tools.StringUtil;
import banger.framework.component.progress.IProgressBarManager;
import banger.framework.component.progress.ProgressBar;
import banger.framework.util.OperationUtil;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.ui.Model;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import banger.framework.pagesize.IPageList;
import banger.service.intf.IImportHistoryService;
import banger.domain.system.SysImportHistory;
import banger.common.BaseController;
import banger.common.tools.JsonUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * 导入历史表页面访问类
 */
@Controller
@RequestMapping("/sysImportHistory")
public class ImportHistoryController extends BaseController {
	private static final long serialVersionUID = 7492652927296314057L;
	@Autowired
	private IImportHistoryService importHistoryService;
	@Resource
	private IProgressBarManager progressBarManager;
	/**
	 * 得到导入历史表列表页面
	 * @return
	 */
	@RequestMapping("/getImportHistoryListPage")
	public ModelAndView getImportHistoryListPage(){
		ModelAndView mv = new ModelAndView("/system/vm/importHistoryPage");
		return mv;
	}

	/**
	 * 查询导入历史表列表数据
	 * @return
	 */
	@RequestMapping("/queryImportHistoryListData")
	public String queryImportHistoryListData(){
		Map<String,Object> condition = new HashMap<String,Object>();
		IPageList<SysImportHistory> importHistoryList = importHistoryService.queryImportHistoryList(condition,this.getPage());
		for (SysImportHistory history : importHistoryList) {
			//配置用户su导入操作
			if (history.getUserId() == 0) history.setUserName("配置用户");
			//先判断是否完成
			if(history.getIsComplete() == 0) {
				//数据库显示导入未成功,内存中没有导入进程,说明服务再倒入时候挂掉,按照导入完成处理
				ProgressBar bar = progressBarManager.getProgressBarById(history.getProgressCode());
				if (bar == null) {
					history.setProgressRate("100%");
				}else{
					history.setProgressRate(String.valueOf(OperationUtil.divide(new BigDecimal(history.getImportTotal()), 1, new BigDecimal(bar.getCurrent()))));
				}
			}else{
				history.setProgressRate("100%");
			}
		}
		renderText(JsonUtil.toJson(importHistoryList, "id","userId,userName,progressRate,progressCode,progressName,importModuleName,remark,createDate,updateDate").toString());
		return null;
	}

	/**
	 * 得到导入历史表新增页面
	 * @return
	 */
	@RequestMapping("/getImportHistoryInsertPage")
	public String getImportHistoryInsertPage(){
		SysImportHistory importHistory = new SysImportHistory();
		setAttribute("importHistory",importHistory);
		return SUCCESS;
	}

	/**
	 * 得到导入历史表修改页面
	 * @return
	 */
	@RequestMapping("/getImportHistoryUpdatePage")
	public String getImportHistoryUpdatePage(){
		String id = getParameter("id");
		SysImportHistory importHistory = importHistoryService.getImportHistoryById(Integer.parseInt(id));
		setAttribute("importHistory",importHistory);
		return SUCCESS;
	}

	/**
	 * 得到导入历史表查看页面
	 * @return
	 */
	@RequestMapping("/getImportHistoryDetailPage")
	public String getImportHistoryDetailPage(){
		String id = getParameter("id");
		SysImportHistory importHistory = importHistoryService.getImportHistoryById(Integer.parseInt(id));
		setAttribute("importHistory",importHistory);
		return SUCCESS;
	}

	/**
	 * 新增导入历史表数据
	 * @return
	 */
	@RequestMapping("/insertImportHistory")
	public String insertImportHistory(@ModelAttribute SysImportHistory importHistory){
		Integer loginUserId = getLoginInfo().getUserId();
		importHistoryService.insertImportHistory(importHistory,loginUserId);
		renderText(SUCCESS);
		return null;
	}

	/**
	 * 修改导入历史表数据
	 * @return
	 */
	@RequestMapping("/updateImportHistory")
	public String updateImportHistory(@ModelAttribute SysImportHistory importHistory){
		Integer loginUserId = getLoginInfo().getUserId();
		importHistoryService.updateImportHistory(importHistory,loginUserId);
		renderText(SUCCESS);
		return null;
	}

	/**
	 * 删除导入历史表数据
	 * @return
	 */
	@RequestMapping("/deleteImportHistory")
	public String deleteImportHistory(){
		String id = getParameter("id");
		importHistoryService.deleteImportHistoryById(Integer.parseInt(id));
		renderText(SUCCESS);
		return null;
	}

	/**
	 * @Author: yangdw
	 * @param
	 * @Description:校验之前的导入是否完成
	 * @Date: 14:02 2017/10/10
	 */
	@RequestMapping("/getImportHistoryIsComplated")
	public void deletePotentialCustomersList(@RequestParam(value ="progressCode" ,defaultValue ="") String progressCode) {
		if(StringUtil.isEmpty(progressCode))
			renderText("页面传递参数不正确");
		Integer userId = this.getLoginInfo().getUserId();
		Map<String, Object> condition = new HashedMap();
		condition.put("userId", userId);
		condition.put("isComplete", 0);
//		condition.put("progressCode", "potentialCustImport_" + userId);
		condition.put("progressCode", progressCode + userId);
		List<SysImportHistory> list = importHistoryService.queryImportHistoryList(condition);
		if (list.size() > 0) {
			for (SysImportHistory history : list) {
				ProgressBar bar = progressBarManager.getProgressBarById(progressCode + userId);
				if(bar != null){
					//导入任务进行中
					renderText(ERROR);
				}else{
					//在导入过程中系统宕机,不明情况重启
						history.setIsComplete(1);
						history.setProgressName("导入潜在客户系统宕机");
						importHistoryService.updateImportHistory(history,userId);
					renderText("请重新点击导入按钮");
				}
			}
		}else{
			renderText(SUCCESS);
		}
	}
}
