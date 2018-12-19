package banger.controller;

import banger.common.BaseController;
import banger.common.tools.JsonUtil;
import banger.domain.loan.LoanIndustryGradeexp;
import banger.framework.pagesize.IPageList;
import banger.service.intf.IIndustryGradeexpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * 贷款行业指引等级说明表页面访问类
 */
@Controller
@RequestMapping("/LoanIndustryGradeexp")
public class IndustryGradeexpController extends BaseController {
	private static final long serialVersionUID = 7376314384966593641L;
	@Autowired
	private IIndustryGradeexpService industryGradeexpService;

	/**
	 * 得到贷款行业指引等级说明表列表页面
	 * @return
	 */
	@RequestMapping("/getIndustryGradeexpListPage")
	public String getIndustryGradeexpListPage(){
		Map<String,Object> condition = new HashMap<String,Object>();
		IPageList<LoanIndustryGradeexp> industryGradeexpList = industryGradeexpService.queryIndustryGradeexpList(condition,this.getPage());
		setAttribute("industryGradeexpList",industryGradeexpList);
		return SUCCESS;
	}

	/**
	 * 查询贷款行业指引等级说明表列表数据
	 * @return
	 */
	@RequestMapping("/queryIndustryGradeexpListData")
	public String queryIndustryGradeexpListData(){
		Map<String,Object> condition = new HashMap<String,Object>();
		IPageList<LoanIndustryGradeexp> industryGradeexpList = industryGradeexpService.queryIndustryGradeexpList(condition,this.getPage());
		renderText(JsonUtil.toJson(industryGradeexpList, "id","loanId,itemName,itemGradecn,itemGradeen,itemGradeexp,remark,createDate,updateDate").toString());
		return null;
	}

	/**
	 * 得到贷款行业指引等级说明表新增页面
	 * @return
	 */
	@RequestMapping("/getIndustryGradeexpInsertPage")
	public String getIndustryGradeexpInsertPage(){
		LoanIndustryGradeexp industryGradeexp = new LoanIndustryGradeexp();
		setAttribute("industryGradeexp",industryGradeexp);
		return SUCCESS;
	}

	/**
	 * 得到贷款行业指引等级说明表修改页面
	 * @return
	 */
	@RequestMapping("/getIndustryGradeexpUpdatePage")
	public String getIndustryGradeexpUpdatePage(){
		String id = getParameter("id");
		LoanIndustryGradeexp industryGradeexp = industryGradeexpService.getIndustryGradeexpById(Integer.parseInt(id));
		setAttribute("industryGradeexp",industryGradeexp);
		return SUCCESS;
	}

	/**
	 * 得到贷款行业指引等级说明表查看页面
	 * @return
	 */
	@RequestMapping("/getIndustryGradeexpDetailPage")
	public String getIndustryGradeexpDetailPage(){
		String id = getParameter("id");
		LoanIndustryGradeexp industryGradeexp = industryGradeexpService.getIndustryGradeexpById(Integer.parseInt(id));
		setAttribute("industryGradeexp",industryGradeexp);
		return SUCCESS;
	}

	/**
	 * 新增贷款行业指引等级说明表数据
	 * @return
	 */
	@RequestMapping("/insertIndustryGradeexp")
	public String insertIndustryGradeexp(@ModelAttribute LoanIndustryGradeexp industryGradeexp){
		Integer loginUserId = getLoginInfo().getUserId();
		industryGradeexpService.insertIndustryGradeexp(industryGradeexp,loginUserId);
		renderText(SUCCESS);
		return null;
	}

	/**
	 * 修改贷款行业指引等级说明表数据
	 * @return
	 */
	@RequestMapping("/updateIndustryGradeexp")
	public String updateIndustryGradeexp(@ModelAttribute LoanIndustryGradeexp industryGradeexp){
		Integer loginUserId = getLoginInfo().getUserId();
		industryGradeexpService.updateIndustryGradeexp(industryGradeexp,loginUserId);
		renderText(SUCCESS);
		return null;
	}

	/**
	 * 删除贷款行业指引等级说明表数据
	 * @return
	 */
	@RequestMapping("/deleteIndustryGradeexp")
	public String deleteIndustryGradeexp(){
		String id = getParameter("id");
		industryGradeexpService.deleteIndustryGradeexpById(Integer.parseInt(id));
		renderText(SUCCESS);
		return null;
	}

}
