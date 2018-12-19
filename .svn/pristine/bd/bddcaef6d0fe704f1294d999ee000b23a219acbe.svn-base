package banger.controller;

import banger.common.BaseController;
import banger.domain.loan.LoanApplyInfo;
import banger.domain.permission.SysTeamMember_Query;
import banger.domain.system.SysBasicConfig;
import banger.framework.sql.command.SqlTransaction;
import banger.moduleIntf.IPermissionModule;
import banger.service.intf.IApplyInfoService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 贷款申请
 * @author zhusw
 *
 */
@RequestMapping("/loanAllot")
@Controller
public class LoanAllotController extends BaseController {
	private static final long serialVersionUID = -7305734846109770428L;


	@Autowired
	private IPermissionModule permissionModule;
	@Autowired
	private IApplyInfoService applyInfoService;


	private String basePath = "/loan/vm/allot/";

	/**
	 * 跳转到客户-客户经理分配页面
	 * @description 2017-11-12 关闭批量分配的入口，只能单个分配
	 * @return
	 */
	@RequestMapping("/gotoAllotSignPage")
	public String gotoAllotSignPage(@RequestParam(value = "ids", defaultValue = "") String ids){
		//查询所有团队的客户经理
//		String teamGroupId=getLoginInfo().getTeamGroupIdByRole();
		if(StringUtils.isNotBlank(ids) && StringUtils.isNumeric(ids)) {
			LoanApplyInfo loanApplyInfo = applyInfoService.getApplyInfoById(Integer.parseInt(ids));
			Integer userId = loanApplyInfo.getLoanBelongId();
			Integer teamGroupId = permissionModule.getGroupIdByUserId(userId);
			String teamGroupIdStr = teamGroupId == null ? "-1" : teamGroupId.toString();
			List<SysTeamMember_Query> members=permissionModule.getMangerByGroupId(teamGroupIdStr);
			setAttribute("teamMembers",members);
			setAttribute("userId",userId);
		}


		setAttribute("ids",ids);
		//申请人
		return basePath + "loanAllotSign";
	}


	@RequestMapping(value = "/loanAllotSignSave")
	@SqlTransaction
	@ResponseBody
	public void loanAllotSignSave(@RequestParam(value = "ids", defaultValue = "") String ids, @RequestParam(value = "memberUserId", defaultValue = "") String memberUserId){
		if (StringUtils.isNotBlank(ids) && StringUtils.isNotBlank(memberUserId)) {
			Integer loginerId = getLoginInfo().getUserId();
			Map<String, Object> resultMap = applyInfoService.loanAllotSignSave(ids,Integer.parseInt(memberUserId),loginerId);
			renderText((Boolean) resultMap.get("success"), (String) resultMap.get("message"), "ids:"+ids + ";memberUserId:" + memberUserId);
		} else {
			renderText(false,"参数错误","ids:"+ids + ";memberUserId:" + memberUserId);
		}
	}

}
