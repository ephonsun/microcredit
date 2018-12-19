package banger.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import banger.domain.permission.ILoginInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import banger.common.BaseController;
import banger.common.tools.JsonUtil;
import banger.domain.customer.CustMarketCustomerQuery;
import banger.domain.customer.MarketCustomerGroup;
import banger.domain.customer.MarketCustomerMember;
import banger.domain.permission.SysTeamGroup_Query;
import banger.domain.permission.SysTeamMember_Query;
import banger.framework.pagesize.IPageList;
import banger.moduleIntf.IPermissionModule;
import banger.service.intf.ICustMarketCustomerService;
import banger.service.intf.IMarketCustomerGroupService;
import banger.service.intf.IMarketCustomerMemberService;

/**
 * 营销客户表页面访问类
 */
@Controller
@RequestMapping("/marketCustomer")
public class CustMarketCustomerController extends BaseController {
	private static final long serialVersionUID = 8716950142181716031L;
	@Autowired
	private ICustMarketCustomerService custMarketCustomerService;
	@Autowired
	private IMarketCustomerGroupService marketCustomerGroupService;	
	@Autowired
	private IMarketCustomerMemberService marketCustomerMemberService;		
	@Autowired
	private IPermissionModule permissionModule;

	private  String BASE_PATH = "customer/vm/marketCustomer/";

	/**
	 * 得到营销客户表列表页面
	 * @return
	 */
	@RequestMapping("/getCustMarketCustomerListPage")
	public String getCustMarketCustomerListPage(){
		String signSate=this.getRequest().getParameter("signSate");
		if(StringUtils.isEmpty(signSate)){
			return BASE_PATH + "marketCustomerList";
		}
		if("2".equals(signSate)){//预申请分配列表
			return BASE_PATH + "groupCustomerList";
		}
		if("3".equals(signSate)){//预申请列表
			return BASE_PATH + "memberCustomerList";
		}
		return BASE_PATH + "marketCustomerList";
	}

	/**
	 * 查询营销客户表列表数据
	 * @return
	 */
	@RequestMapping("/queryCustMarketCustomerListData")
	@ResponseBody
	public String queryCustMarketCustomerListData(CustMarketCustomerQuery custMarketCustomerQuery){
		Map<String,Object> condition = new HashMap<String,Object>();
		
		condition.put("customerName", custMarketCustomerQuery.getCustomerName());
		condition.put("customerType", custMarketCustomerQuery.getCustomerType());
		condition.put("startDate", custMarketCustomerQuery.getStartDate());
		condition.put("endDate", custMarketCustomerQuery.getEndDate());
		
		IPageList<CustMarketCustomerQuery> custMarketCustomerList = custMarketCustomerService.queryCustMarketCustomerList(condition,this.getPage());
		renderText(JsonUtil.toJson(custMarketCustomerList, "marketCustomerId","customerName,sexCN,ageStr,phoneNumber,cardTypeCN,cardNumber,customerType,loanMoneyStr,loanUse,createDateStr").toString());
		return null;
	}

	/**
	 * 查询预申请分配客户表列表数据--团队主管
	 * @return
	 */
	@RequestMapping("/queryGroupCustomerListData")
	@ResponseBody
	public String queryGroupCustomerListData(CustMarketCustomerQuery custMarketCustomerQuery){
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

		condition.put("customerName", custMarketCustomerQuery.getCustomerName());
		condition.put("startDate", custMarketCustomerQuery.getStartDate());
		condition.put("endDate", custMarketCustomerQuery.getEndDate());
		
		IPageList<CustMarketCustomerQuery> custMarketCustomerList = custMarketCustomerService.queryGroupMarketCustomerList(condition,this.getPage());
		renderText(JsonUtil.toJson(custMarketCustomerList, "marketCustomerId","customerName,sexCN,ageStr,phoneNumber,cardTypeCN,cardNumber,customerType,loanMoneyStr,loanUse,signDateStr").toString());
		return null;
	}

	/**
	 * 客户经理查询预申请分配客户表列表数据--客户经理
	 * @return
	 */
	@RequestMapping("/queryMemberCustomerListData")
	@ResponseBody
	public String queryMemberCustomerListData(CustMarketCustomerQuery custMarketCustomerQuery){
		
		Map<String,Object> condition = new HashMap<String,Object>();
		Integer loginUserId = getLoginInfo().getUserId();
		//客户经理只查询自己的数据
		condition.put("userId", loginUserId);
		condition.put("customerName", custMarketCustomerQuery.getCustomerName());
		condition.put("startDate", custMarketCustomerQuery.getStartDate());
		condition.put("endDate", custMarketCustomerQuery.getEndDate());

		IPageList<CustMarketCustomerQuery> custMarketCustomerList = custMarketCustomerService.queryMemberMarketCustomerList(condition,this.getPage(),false);
		renderText(JsonUtil.toJson(custMarketCustomerList, "marketCustomerId","customerName,sexCN,ageStr,phoneNumber,cardTypeCN,cardNumber,customerType,loanMoneyStr,loanUse,signDateStr").toString());
		return null;
	}	
	
	/**
	 * 跳转到客户团队分配页面
	 * @return
	 */
	@RequestMapping("/gotoGroupSignPage")
	public String gotoGroupSignPage(){
		String marketCustomerIds = getParameter("marketCustomerIds");
		
		//查询所有团队
		List<SysTeamGroup_Query> groups=permissionModule.queryAllSysTeamGroup();
		
		setAttribute("teamGroups",groups);
		setAttribute("marketCustomerIds",marketCustomerIds);
		return BASE_PATH + "customerGroupSign";
	}

	/**
	 * 跳转到客户-客户经理分配页面
	 * @return
	 */
	@RequestMapping("/gotoMemberSignPage")
	public String gotoMemberSignPage(){
		String marketCustomerIds = getParameter("marketCustomerIds");
		
		//查询所有团队的客户经理
		String teamGroupId=getLoginInfo().getTeamGroupIdByRole(false);
		List<SysTeamMember_Query> members=permissionModule.getMangerByGroupId(teamGroupId);
		
		setAttribute("teamMembers",members);
		setAttribute("marketCustomerIds",marketCustomerIds);
		return BASE_PATH + "customerMemberSign";
	}
	
	/**
	 * 作废营销客户表数据
	 * @return
	 */
	@RequestMapping("/deleteCustMarketCustomer")
	@ResponseBody
	public void deleteCustMarketCustomer(){
		String marketCustomerId = getParameter("marketCustomerId");
		
		try {
			custMarketCustomerService.deleteCustMarketCustomerById(Integer.parseInt(marketCustomerId));
			renderText(true, "作废客户成功！", null);
			return;
		} catch (Exception e) {
			log.error("作废客户报错", e);
		}
		renderText(false, "客户作废失败！", null);
		
	}
	
	/**
	 * 分配客户-团队
	 * @return
	 */
	@RequestMapping("/mktCustGroupSign")
	@ResponseBody
	public void mktCustGroupSign(){
		String marketCustomerIds = getParameter("marketCustomerIds");
		Integer teamGroupId = Integer.parseInt(getParameter("teamGroupId"));
//		Integer teamGroupId=getLoginInfo().getTeamGroupId();
		try {
			//修改客户 分配状态
			Map<String,Object> condition=new HashMap<String,Object>();
			condition.put("marketCustomerIds", marketCustomerIds);
			condition.put("signSate", 2);
			custMarketCustomerService.signCustomer(condition);
			//往团队客户分配表插入数据
			String[] customerIdArray=marketCustomerIds.split(",");
			for (String customerIdStr : customerIdArray) {
				Integer customerId=Integer.valueOf(customerIdStr);
				Integer loginUserId = getLoginInfo().getUserId();
				MarketCustomerGroup marketCustomerGroup=new MarketCustomerGroup();
				marketCustomerGroup.setCustomerId(customerId);
				marketCustomerGroup.setTeamGroupId(teamGroupId);
				marketCustomerGroup.setSignDate(new Date());
				marketCustomerGroupService.insertMarketCustomerGroup(marketCustomerGroup, loginUserId);
			}
			
			renderText(true, "分配客户成功！", null);
			return;
		} catch (Exception e) {
			log.error("分配客户报错", e);
		}
		renderText(false, "分配客户失败！", null);
		
	}

	/**
	 * 分配客户-分配到客户经理
	 * @return
	 */
	@RequestMapping("/mktCustMemberSign")
	@ResponseBody
	public void mktCustMemberSign(){
		String marketCustomerIds = getParameter("marketCustomerIds");
		Integer teamGroupId=getLoginInfo().getTeamGroupId();
		Integer memberUserId=Integer.valueOf(getParameter("memberUserId"));
		
		try {
			//修改客户 分配状态
			Map<String,Object> condition=new HashMap<String,Object>();
			condition.put("marketCustomerIds", marketCustomerIds);
			condition.put("signSate", 3);
			custMarketCustomerService.signCustomer(condition);
			//往团队客户分配表插入数据
			String[] customerIdArray=marketCustomerIds.split(",");
			for (String customerIdStr : customerIdArray) {
				Integer customerId=Integer.valueOf(customerIdStr);
				Integer loginUserId = getLoginInfo().getUserId();
				MarketCustomerMember marketCustomerMember=new MarketCustomerMember();
				marketCustomerMember.setCustomerId(customerId);
				marketCustomerMember.setSignDate(new Date());
				marketCustomerMember.setSignUserId(loginUserId);
				marketCustomerMember.setTeamGroupId(teamGroupId);
				marketCustomerMember.setTeamMemberId(memberUserId);
				marketCustomerMember.setApplySate(1);
				marketCustomerMemberService.insertMarketCustomerMember(marketCustomerMember, loginUserId);
			}
			
			renderText(true, "分配客户成功！", null);
			return;
		} catch (Exception e) {
			log.error("分配客户报错", e);
		}
		renderText(false, "分配客户失败！", null);
		
	}	

}
