package banger.controller;

import banger.common.BaseController;
import banger.common.interceptor.NoLoginInterceptor;
import banger.common.tools.StringUtil;
import banger.domain.loan.*;
import banger.framework.util.DateUtil;
import banger.framework.util.OperationUtil;
import banger.moduleIntf.*;
import banger.service.impl.ProfitBizIncomeMonthService;
import banger.service.intf.*;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 损益情况
 * @author xw
 *
 */
@RequestMapping("/loanConInvestigate")
@Controller
public class LoanProLossConInvestigateController extends BaseController {
	/*private static final long serialVersionUID = -7305734846109770428L;*/

	@Resource
	IProfitConsumIncomeItemProvider iProfitConsumIncomeItemProvider;
	@Resource
	ILoanIncomeStatementProvider loanIncomeStatementProvider;
	@Resource
	IProfitLossInfoService iProfitLossInfoService;
	@Resource
	ICrossCheckQuanyiquanService iCrossCheckQuanyiquanService;


	/**
	 * 弹出时间编辑界面
	 */
	@NoLoginInterceptor
	@RequestMapping("getConUpdateTimePage")
	public String getConUpdateTimePage(@RequestParam(value = "loanId" ) String loanId,@RequestParam(value = "createDate" ) String createDate,@RequestParam(value = "createDateEnd" ) String createDateEnd) {
		setAttribute("createDate",createDate);
		setAttribute("createDateEnd",createDateEnd);
		setAttribute("loanId", loanId);
		return "/loan/vm/profitLoss/updateConTime";
	}


	/**
	 * 编辑时间后操作
	 *
	 */

	@RequestMapping("updateConTime")
	@ResponseBody
	public void updateConTime() {
		try {
			String createDate = getParameter("createDate");
			String loanId = getParameter("loanId");
			String createDateEnd = getParameter("createDateEnd");

			Date createDate1 = DateUtil.parser(createDate, "yyyy-MM");
			Date createDateEnd1 = DateUtil.parser(createDateEnd, "yyyy-MM");

			Calendar c1 = Calendar.getInstance();
			Calendar c2 = Calendar.getInstance();
			c1.setTime(createDate1);
			int createYear=c1.get(Calendar.YEAR);
			int createMonth=c1.get(Calendar.MONTH)+1;
			c2.setTime(createDateEnd1);
			int createEndYear=c2.get(Calendar.YEAR);
			int createEndMonth=c2.get(Calendar.MONTH)+1;

			//改变主表的月份
			LoanProfitLossInfo lpli=iProfitLossInfoService.getProfitLossInfoByLoanId(Integer.parseInt(loanId));
			lpli.setMonthEnd(createEndMonth);
			lpli.setMonthStart(createMonth);
			lpli.setYearEnd(createEndYear);
			lpli.setYearStart(createYear);
			iProfitLossInfoService.updateProfitLossInfoByLoanId(lpli,getLoginInfo().getUserId());



			//总共几个月
			int Months=(createEndYear-createYear)*12+(createEndMonth-createMonth)+1;
			BigDecimal bigMonths = new BigDecimal( Months);


			Map<String, Object> map = new HashedMap();
			map.put("loanId", loanId);
			List<LoanProfitConsumIncomeItem> pciiList=iProfitConsumIncomeItemProvider.queryProfitConsumIncomeItemList(map);


			for (LoanProfitConsumIncomeItem pcii : pciiList) {
				BigDecimal MonthAvgAmount=OperationUtil.divide(bigMonths,2,pcii.getTotalAmount());//月均值
				pcii.setMonthAvgAmount(MonthAvgAmount);
				iProfitConsumIncomeItemProvider.updateProfitConsumIncomeItem(pcii,getLoginInfo().getUserId());
			}


			renderText(true, "修改成功！", null);
			return;
		}catch (Exception e){

			e.printStackTrace();
		}
		renderText(false, "修改失败！", null);
		return;
	}

	/**
	 * 获取编辑明细界面
	 *
	 */
	@NoLoginInterceptor
	@RequestMapping("getConDetailPage")
	public String getConDetailPage(@RequestParam(value = "id" ) String id,@RequestParam(value = "createDateEnd" ) String createDateEnd,@RequestParam(value = "createDate" ) String createDate,@RequestParam(value = "just" ) String just) {
		LoanProfitConsumIncomeItem lpcii=iProfitConsumIncomeItemProvider.getProfitConsumIncomeItemById(Integer.parseInt(id));
		setAttribute("lpcii", lpcii);
		setAttribute("createDateEnd", createDateEnd);
		setAttribute("createDate", createDate);

		if("just".equals(just)){
			return "/loan/vm/profitLoss/showJustConDetail";
		}else{
			return "/loan/vm/profitLoss/showConDetail";
		}
	}

	//修改明细界面
	@RequestMapping("updateConDetail")
	@ResponseBody
	public void updateConDetail(LoanProfitConsumIncomeItem lpcii) {

		try {
			Date createDate = DateUtil.parser(getParameter("startCreateDate"), "yyyy-MM");
			Date createDateEnd = DateUtil.parser(getParameter("endCreateDateEnd"), "yyyy-MM");
			Calendar c = Calendar.getInstance();
			c.setTime(createDate);
			int createYear=c.get(Calendar.YEAR);
			int createMonth=c.get(Calendar.MONTH)+1;
			c.setTime(createDateEnd);
			int createEndYear=c.get(Calendar.YEAR);
			int createEndMonth=c.get(Calendar.MONTH)+1;
			//总共几个月
			int Months=(createEndYear-createYear)*12+(createEndMonth-createMonth)+1;
			BigDecimal bigMonths = new BigDecimal( Months);
			BigDecimal totalAmount=OperationUtil.plus(lpcii.getPreYearAmount(),lpcii.getCurYearAmount());
			lpcii.setTotalAmount(totalAmount);
			lpcii.setMonthAvgAmount(OperationUtil.divide(bigMonths,2,totalAmount));
			iProfitConsumIncomeItemProvider.updateProfitConsumIncomeItem(lpcii,getLoginInfo().getUserId());
			//更新主表的收入总计
			loanIncomeStatementProvider.updateLoanProfitLossInfoByOpt(lpcii.getLoanId(), 2, Integer.valueOf(getLoginInfo().getUserId()));
			renderText(true, "修改成功！", null);
			return;
		}catch (Exception e){
			e.printStackTrace();
		}
		renderText(false, "修改失败！", null);
		return;
	}



	/**
	 * 新增明细详情
	 */
	@RequestMapping("addConMingxi")
	@ResponseBody
	public void addConMingxi(LoanProfitConsumIncomeItem lpcii) {

		try {
			Date createDate = DateUtil.parser(getParameter("startCreateDate"), "yyyy-MM");
			Date createDateEnd = DateUtil.parser(getParameter("endCreateDateEnd"), "yyyy-MM");
			Calendar c = Calendar.getInstance();
			c.setTime(createDate);
			int createYear=c.get(Calendar.YEAR);
			int createMonth=c.get(Calendar.MONTH)+1;
			c.setTime(createDateEnd);
			int createEndYear=c.get(Calendar.YEAR);
			int createEndMonth=c.get(Calendar.MONTH)+1;
			//总共几个月
			int Months=(createEndYear-createYear)*12+(createEndMonth-createMonth)+1;
			BigDecimal bigMonths = new BigDecimal( Months);
			lpcii.setLoanClassId(2);
			lpcii.setTableName("LOAN_PROFIT_CONSUM_INCOME_ITEM");
			BigDecimal totalAmout=OperationUtil.plus(lpcii.getCurYearAmount(),lpcii.getPreYearAmount());
			lpcii.setTotalAmount(totalAmout);
			BigDecimal monthAvgAmount=OperationUtil.divide(bigMonths,2,totalAmout);
			lpcii.setMonthAvgAmount(monthAvgAmount);
			//添加明细表
			iProfitConsumIncomeItemProvider.insertProfitConsumIncomeItem(lpcii,getLoginInfo().getUserId());
			//更新主表的收入总计
			loanIncomeStatementProvider.updateLoanProfitLossInfoByOpt(lpcii.getLoanId(), 2, Integer.valueOf(getLoginInfo().getUserId()));
			renderText(true, "新增成功！", null);
			return ;

		} catch (Exception e) {
			log.error("新增报错", e);
			renderText(false, "新增失败！", null);
		}
		return ;
	}

	/**
	 * 跳转添加明细界面
	 */
	@NoLoginInterceptor
	@RequestMapping("getConMingxiAddPage")
	public String getMingxiAddPage(@RequestParam(value = "columnName" ) String columnName,@RequestParam(value = "createDateEnd" ) String createDateEnd,@RequestParam(value = "loanId" ) String loanId,@RequestParam(value = "createDate" ) String createDate) {

		setAttribute("columnName", columnName);//columnName
		setAttribute("loanId", loanId);// LoanId
		setAttribute("createDateEnd", createDateEnd);
		setAttribute("createDate", createDate);

		return "/loan/vm/profitLoss/addConMingxi";
	}


	/**
	 * 删除家庭收入以及其他
	 *
	 */
	@RequestMapping("deleteConBsIncome")
	@ResponseBody
	public void deleteBsIncome(@RequestParam(value = "id" ) String id) {
		try {

			if (StringUtil.isNotEmpty(id)) {
				LoanProfitConsumIncomeItem itme = iProfitConsumIncomeItemProvider.getProfitConsumIncomeItemById(Integer.valueOf(id));
				iProfitConsumIncomeItemProvider.deleteProfitConsumIncomeItemById(Integer.valueOf(id));
				/*iProfitBizIncomeMonthProvider.deleteProfitBizIncomeItemMonthByItemId(Integer.valueOf(id));*/
				//更新主表的收入总计
				loanIncomeStatementProvider.updateLoanProfitLossInfoByOpt(itme.getLoanId(), 2, Integer.valueOf(getLoginInfo().getUserId()));

				renderText(true, "删除成功！", null);
				return;
			}

		} catch (Exception e) {
			log.error("删除报错", e);
		}
		renderText(false, "删除失败！", null);
		return;
	}




}

