package banger.controller;

import banger.common.BaseController;
import banger.common.tools.JsonUtil;
import banger.domain.customer.IntoCustomerMember;
import banger.domain.html5.IntoCustApplyInfoQuery;
import banger.domain.permission.ILoginInfo;
import banger.domain.permission.SysTeamMember_Query;
import banger.framework.pagesize.IPageList;
import banger.moduleIntf.IPermissionModule;
import banger.service.intf.ICustApplyService;
import banger.service.intf.ICustomerMemberService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by panl on 2017/11/20
 * 进件分配页面访问类
 */
@Controller
@RequestMapping("/intoCustomer")
public class CustIntoCustomerController extends BaseController {
	@Autowired
	private ICustApplyService intoCustomer;
	@Autowired
	private IPermissionModule permissionModule;
	@Autowired
	private ICustomerMemberService customerMemberService;

	private  String BASE_PATH = "customer/vm/intoCustApply/";

	/**
	 * 得到营销客户表列表页面
	 * @return
	 */
	@RequestMapping("/getIntoCustomerListPage")
	public String getIntoCustomerListPage(){
		String signSate=this.getRequest().getParameter("signSate");
//		if(StringUtils.isEmpty(signSate)){
//			return BASE_PATH + "marketCustomerList";
//		}
		if("2".equals(signSate)){//进件分配列表
			return BASE_PATH + "intoGroupCustomerList";
		}
		if("3".equals(signSate)){//预申请列表
			return BASE_PATH + "memberCustomerList";
		}
		return BASE_PATH + "intoGroupCustomerList";
	}


	/**
	 * 查询进件分配客户表列表数据--团队主管
	 * @return
	 */
	@RequestMapping("/queryIntoGroupCustomerListData")
	@ResponseBody
	public String queryIntoGroupCustomerListData(IntoCustApplyInfoQuery IntoCustApplyInfoQuery){
		Map<String,Object> condition = new HashMap<String,Object>();
		if(!this.isTeamManager()){//如果是非团队主管进来，不加载任何数据
			condition.put("teamGroupId", "-1");
		}

		ILoginInfo loginInfo = getLoginInfo();
		String groupIds = loginInfo.getTeamGroupIdByRole(false);
		if (StringUtils.isNotBlank(groupIds)) {
			condition.put("teamGroupId", groupIds);
		} else {
			condition.put("teamGroupId", "-1");
		}

		condition.put("custName", IntoCustApplyInfoQuery.getCustName());
		condition.put("startDate", IntoCustApplyInfoQuery.getStartDate());
		condition.put("endDate", IntoCustApplyInfoQuery.getEndDate());

		IPageList<IntoCustApplyInfoQuery> intoCustApplyInfoQueries = intoCustomer.queryIntoCustApplyInfoList(condition,this.getPage());
		renderText(JsonUtil.toJson(intoCustApplyInfoQueries, "applyId","custName,sexCN,custAge,idCard,custPhone,applyAmount,useType,loanUserOptionName,signDates","yyyy-MM-dd HH:mm").toString());
		return null;
	}

	/**
	 * 跳转到客户-客户经理分配页面
	 * @return
	 */
	@RequestMapping("/gotoIntoMemberSignPage")
	public String gotoIntoMemberSignPage(){
		String applyIds = getParameter("applyIds");
		//查询所有团队的客户经理
		String teamGroupId=getLoginInfo().getTeamGroupIdByRole(false);
		List<SysTeamMember_Query> members=permissionModule.getMangerByGroupId(teamGroupId);

		setAttribute("teamMembers",members);
		setAttribute("applyIds",applyIds);
		return BASE_PATH + "customerMemberSign";
	}

	/**
	 * 作废进件客户表数据
	 * @return
	 */
	@RequestMapping("/deleteIntoCustomer")
	@ResponseBody
	public void deleteIntoCustomer(){
		String applyId = getParameter("applyId");

		try {
			intoCustomer.deleteIntoCustomerById(Integer.parseInt(applyId));
			renderText(true, "作废客户成功！", null);
			return;
		} catch (Exception e) {
			log.error("作废客户报错", e);
		}
		renderText(false, "客户作废失败！", null);

	}

	/**
	 * 分配客户-分配到客户经理
	 * @return
	 */
	@RequestMapping("/intoCustMemberSign")
	@ResponseBody
	public void intoCustMemberSign(){
		String applyIds = getParameter("applyIds");
		Integer teamGroupId=getLoginInfo().getTeamGroupId();
		Integer memberUserId=Integer.valueOf(getParameter("memberUserId"));

		try {
			//修改客户 分配状态
			Map<String,Object> condition=new HashMap<String,Object>();
			condition.put("applyIds", applyIds);
			condition.put("signSate", 3);
			intoCustomer.signIntoCustomer(condition);
			//往团队客户分配表插入数据
			String[] customerIdArray=applyIds.split(",");
			for (String customerIdStr : customerIdArray) {
				Integer customerId=Integer.valueOf(customerIdStr);
				Integer loginUserId = getLoginInfo().getUserId();
				IntoCustomerMember intoCustomerMember = new IntoCustomerMember();

				//MarketCustomerMember marketCustomerMember=new MarketCustomerMember();
				intoCustomerMember.setCustomerId(customerId);
				intoCustomerMember.setSignDate(new Date());
				intoCustomerMember.setSignUserId(loginUserId);
				intoCustomerMember.setTeamGroupId(teamGroupId);
				intoCustomerMember.setTeamMemberId(memberUserId);
				intoCustomerMember.setApplySate(1);
				customerMemberService.insertCustomerMember(intoCustomerMember, loginUserId);
			}

			renderText(true, "分配客户成功！", null);
			return;
		} catch (Exception e) {
			log.error("分配客户报错", e);
		}
		renderText(false, "分配客户失败！", null);

	}

	/**
	 * 客户经理查询预申请分配客户表列表数据--客户经理
	 * @return
	 */

	@RequestMapping("/queryIntoMemberCustomerListData")
	@ResponseBody
	public String queryIntoMemberCustomerListData(IntoCustApplyInfoQuery intoCustApplyInfoQuery){

		Map<String,Object> condition = new HashMap<String,Object>();
		Integer loginUserId = getLoginInfo().getUserId();
		//客户经理只查询自己的数据
		condition.put("userId", loginUserId);
		condition.put("custName", intoCustApplyInfoQuery.getCustName());
		condition.put("isDel", intoCustApplyInfoQuery.getIsDel());
		condition.put("startDate", intoCustApplyInfoQuery.getStartDate());
		condition.put("endDate", intoCustApplyInfoQuery.getEndDate());

		IPageList<IntoCustApplyInfoQuery> intoCustApplyInfoQueries =intoCustomer.IntoCustApplyInfoMemberList(condition,this.getPage());
		renderText(JsonUtil.toJson(intoCustApplyInfoQueries, "applyId","custName,sexCN,custAge,custPhone,idCard,applyAmount,useType,isDel,loanUserOptionName,signDates","yyyy-MM-dd HH:mm").toString());
		return null;
	}



}
