package banger.controller;

import java.util.Map;
import java.util.HashMap;
import org.springframework.ui.Model;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import banger.framework.pagesize.IPageList;
import banger.service.intf.IScoreDetailInfoService;
import banger.domain.loan.LoanScoreDetailInfo;
import banger.common.BaseController;
import banger.common.tools.JsonUtil;
import org.springframework.web.bind.annotation.*;

/**
 * 贷款评分明细表页面访问类
 */
@Controller
@RequestMapping("/loanScoreDetailInfo")
public class ScoreDetailInfoController extends BaseController {
	private static final long serialVersionUID = 5430382625328426579L;
	@Autowired
	private IScoreDetailInfoService scoreDetailInfoService;

	/**
	 * 得到贷款评分明细表列表页面
	 * @return
	 */
	@RequestMapping("/getScoreDetailInfoListPage")
	public String getScoreDetailInfoListPage(){
		Map<String,Object> condition = new HashMap<String,Object>();
		IPageList<LoanScoreDetailInfo> scoreDetailInfoList = scoreDetailInfoService.queryScoreDetailInfoList(condition,this.getPage());
		setAttribute("scoreDetailInfoList",scoreDetailInfoList);
		return SUCCESS;
	}

	/**
	 * 查询贷款评分明细表列表数据
	 * @return
	 */
	@RequestMapping("/queryScoreDetailInfoListData")
	public String queryScoreDetailInfoListData(){
		Map<String,Object> condition = new HashMap<String,Object>();
		IPageList<LoanScoreDetailInfo> scoreDetailInfoList = scoreDetailInfoService.queryScoreDetailInfoList(condition,this.getPage());
		renderText(JsonUtil.toJson(scoreDetailInfoList, "id","loanId,fieldId,fieldName,fieldColumn,fieldValue,fieldScore").toString());
		return null;
	}

	/**
	 * 得到贷款评分明细表新增页面
	 * @return
	 */
	@RequestMapping("/getScoreDetailInfoInsertPage")
	public String getScoreDetailInfoInsertPage(){
		LoanScoreDetailInfo scoreDetailInfo = new LoanScoreDetailInfo();
		setAttribute("scoreDetailInfo",scoreDetailInfo);
		return SUCCESS;
	}

	/**
	 * 得到贷款评分明细表修改页面
	 * @return
	 */
	@RequestMapping("/getScoreDetailInfoUpdatePage")
	public String getScoreDetailInfoUpdatePage(){
		String id = getParameter("id");
		LoanScoreDetailInfo scoreDetailInfo = scoreDetailInfoService.getScoreDetailInfoById(Integer.parseInt(id));
		setAttribute("scoreDetailInfo",scoreDetailInfo);
		return SUCCESS;
	}

	/**
	 * 得到贷款评分明细表查看页面
	 * @return
	 */
	@RequestMapping("/getScoreDetailInfoDetailPage")
	public String getScoreDetailInfoDetailPage(){
		String id = getParameter("id");
		LoanScoreDetailInfo scoreDetailInfo = scoreDetailInfoService.getScoreDetailInfoById(Integer.parseInt(id));
		setAttribute("scoreDetailInfo",scoreDetailInfo);
		return SUCCESS;
	}

	/**
	 * 新增贷款评分明细表数据
	 * @return
	 */
	@RequestMapping("/insertScoreDetailInfo")
	public String insertScoreDetailInfo(@ModelAttribute LoanScoreDetailInfo scoreDetailInfo){
		Integer loginUserId = getLoginInfo().getUserId();
		scoreDetailInfoService.insertScoreDetailInfo(scoreDetailInfo,loginUserId);
		renderText(SUCCESS);
		return null;
	}

	/**
	 * 修改贷款评分明细表数据
	 * @return
	 */
	@RequestMapping("/updateScoreDetailInfo")
	public String updateScoreDetailInfo(@ModelAttribute LoanScoreDetailInfo scoreDetailInfo){
		Integer loginUserId = getLoginInfo().getUserId();
		scoreDetailInfoService.updateScoreDetailInfo(scoreDetailInfo,loginUserId);
		renderText(SUCCESS);
		return null;
	}

	/**
	 * 删除贷款评分明细表数据
	 * @return
	 */
	@RequestMapping("/deleteScoreDetailInfo")
	public String deleteScoreDetailInfo(){
		String id = getParameter("id");
		scoreDetailInfoService.deleteScoreDetailInfoById(Integer.parseInt(id));
		renderText(SUCCESS);
		return null;
	}

}
