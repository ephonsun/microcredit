package banger.controller;

import java.util.List;

import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import banger.common.BaseController;
import banger.common.tools.JsonUtil;
import banger.domain.loan.LoanAuditTableFieldExtend;
import banger.domain.loan.WfApproveProcess;
import banger.service.intf.IApproveProcessService;
import banger.service.intf.IAuditTableFieldService;
import net.sf.json.JSONObject;

/**
 * 审批字段controller
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/approveProcessField")
public class LoanAuditTableFieldController extends BaseController {

	private static final long serialVersionUID = -1475931158569507402L;
	@Autowired
	private IAuditTableFieldService auditTableFieldService;
	@Autowired
	private IApproveProcessService approveProcessService;

	/**
	 * 获取审批字段页面
	 */
	@RequestMapping("/getSaveApproveProcessField")
	public ModelAndView getSaveApproveProcessField(
			@RequestParam("processId") Integer processId, @RequestParam("typeId") Integer typeId) {
		ModelAndView mv = new ModelAndView("/loan/vm/approveProcessFieldSave");

		WfApproveProcess wfApproveProcess = approveProcessService.getApproveProcessById(processId);

		mv.addObject("wfApproveProcess", wfApproveProcess);
		mv.addObject("typeId", typeId);
		return mv;
	}

	/**
	 * 查询选择审批字段
	 */
	@RequestMapping("/queryApproveProcessField")
	public void queryApproveProcessField(@RequestParam("processId") Integer processId) {
		List<LoanAuditTableFieldExtend> fieldList = auditTableFieldService.queryAuditTableFieldSelect(processId);
		renderText(JsonUtil
				.toJson(fieldList, "auditFieldId",
						"fieldId,fieldName,fieldAppIsShow,fieldWebIsShow,fieldIsRequired,tableId")
				.toString());
	}

	/**
	 * 保存选择审批字段
	 */
	@RequestMapping("/saveApproveProcessField")
	@ResponseBody
	public void saveApproveProcessField(@RequestParam("json") String json, @RequestParam("processId") Integer processId,
			@RequestParam("typeId") Integer typeId, @RequestParam("paramId") Integer paramId) {
		// TODO flowId(页面中未获取)用来查询conditionId 现在不知道要不要去查，还是从别人那里获取
		Integer loginUserId = getLoginInfo().getUserId();
		JSONArray jsonArray = JSONArray.fromObject(json);
		// 保存
		auditTableFieldService.saveOrUpdateLoanAuditTableField(loginUserId, processId, typeId, paramId, jsonArray);

	}
}
