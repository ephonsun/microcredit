package banger.controller;

import banger.common.BaseController;
import banger.common.tools.JsonUtil;
import banger.domain.enumerate.LoanProcessTypeEnum;
import banger.domain.loan.LoanFieldExtend;
import banger.domain.system.SysOpeventLog;
import banger.moduleIntf.IAutoFieldProvider;
import banger.moduleIntf.IAutoTemplateProvider;
import banger.service.intf.*;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 贷款类型表单字段配置
 * 
 * @author
 *
 */
@Controller
@RequestMapping("/LoanTypeTableField")
public class LoanTypeTableFieldController extends BaseController {

	private static final long serialVersionUID = 1L;
	@Autowired
	private ITypeTableFieldService typeTableFieldService;
	@Autowired
	private ITypeService typeService;
	@Autowired
	private IAutoTemplateProvider autoTemplateProvider;
	/**
	 * 跳转页面
	 * 
	 * @param tableId
	 * @param precTypeName
	 *            流程表
	 * @param loanType
	 *            贷款类型
	 * @param tableDisplayName
	 *            自定义信息表
	 * @return
	 */
	@RequestMapping("/getLoanTypeTableFieldPage")
	public ModelAndView getLoanTypeTableField(@RequestParam("tableId") Integer tableId,
			 @RequestParam("typeId") Integer typeId,
			@RequestParam("precType") String precType) throws UnsupportedEncodingException {
		ModelAndView mv = new ModelAndView("/loan/vm/LoanTypeTableField");
		mv.addObject("tableId", tableId);
		mv.addObject("precType", precType);
		mv.addObject("typeId", typeId);
		mv.addObject("precTypeName", LoanProcessTypeEnum.getTypeNameByType(precType)+"表");
		mv.addObject("loanType", typeService.getTypeById(typeId).getLoanTypeName());
		mv.addObject("tableDisplayName",autoTemplateProvider.getTableInfoById(tableId).getTableDisplayName());
		return mv;
	}

	@RequestMapping("/getLoanContractTypeTableFieldPage")
	public ModelAndView getLoanContractTypeTableField(@RequestParam("tableId") Integer tableId,
	                                          @RequestParam("typeId") Integer typeId) throws UnsupportedEncodingException {
		ModelAndView mv = new ModelAndView("/loan/vm/LoanContractTypeTableField");
		mv.addObject("tableId", tableId);
		mv.addObject("typeId", typeId);
		mv.addObject("precTypeName", "个人信息表");
		mv.addObject("loanType", typeService.getTypeById(typeId).getLoanTypeName());
		mv.addObject("tableDisplayName",autoTemplateProvider.getTableInfoById(tableId).getTableDisplayName());
		return mv;
	}

	/**
	 * 获取自定义信息
	 * 
	 * @param tableId
	 */
	@RequestMapping("/queryLoanTypeTableFieldList")
	@ResponseBody
	public void queryLoanTypeTableFieldList(@RequestParam("tableId") Integer tableId,@RequestParam("typeId") Integer typeId,@RequestParam("precType") String precType) {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("tableId", tableId);
		condition.put("typeId",typeId);
		if(!"contract".equals(precType)) {
			condition.put("precType", precType);
		}
		// 自定义联查
		List<LoanFieldExtend> LoanTypeTableFieldList = typeTableFieldService
				.queryTypeTableFieldListByCondition(condition);


		renderText(JsonUtil
				.toJson(LoanTypeTableFieldList, "id",
						"fieldColumnDisplay,fieldType,fieldIsRequired,fieldWebIsShow,fieldAppIsShow,fieldIsCondition,fieldId,fieldName,isFixed,refId")
				.toString());
	}

	/**
	 * 保存
	 *
	 *
	 */
	@RequestMapping("/saveOrUpdateLoanTypeTableField")
	@ResponseBody
	@SuppressWarnings("all")
	public void saveLoanTypeTableField(@RequestParam("json") String json, @RequestParam("precType") String precType,
			@RequestParam("typeId") Integer typeId, @RequestParam("tableId") Integer tableId
			) {
		Integer loginUserId = getLoginInfo().getUserId();
		try {
			JSONObject arrayJson = new JSONObject().fromObject(json);

			String required = (String) arrayJson.get("fieldIsRequired");
			String[] fieldIsRequired = required.split(",");
			String condition = (String) arrayJson.get("fieldIsCondition");
			String[] fieldIsCondition = condition.split(",");
			String appIsShow = (String) arrayJson.get("fieldAppIsShow");
			String[] fieldAppIsShow = appIsShow.split(",");
			String webIsShow = (String) arrayJson.get("fieldWebIsShow");
			String[] fieldWebIsShow = webIsShow.split(",");
			String fieldId = (String) arrayJson.get("fieldIds");
			String[] fieldIds = fieldId.split(",");
			String refId = (String) arrayJson.get("refids");
			String[] refIds = refId.split(",");
			typeTableFieldService.saveOrUpdateTypeTableField(loginUserId, tableId, typeId, precType, fieldIds,
					fieldIsRequired, fieldIsCondition, fieldWebIsShow,fieldAppIsShow,refIds);
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderText(SUCCESS);
	}
}
