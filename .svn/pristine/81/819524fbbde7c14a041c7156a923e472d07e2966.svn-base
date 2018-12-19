package banger.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import banger.domain.config.ModeConfigFile;
import banger.domain.config.ModeScoreInfo;
import banger.domain.loan.LoanApplyInfo;
import banger.domain.loan.LoanSurveyFlowInfo;
import banger.framework.util.OperationUtil;
import banger.moduleIntf.IConfigModule;
import banger.moduleIntf.IModeScoreInfoProvider;
import banger.service.intf.IApplyInfoService;
import banger.service.intf.ISurveyFlowInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import banger.common.BaseController;
import banger.common.constant.GlobalConst;
import banger.common.tools.JsonUtil;
import banger.domain.enumerate.LoanProcessTypeEnum;
import banger.domain.loan.LoanType;
import banger.domain.loan.LoanTypeRelTable;
import banger.service.intf.ITypeRelTableService;
import banger.service.intf.ITypeService;
import org.springframework.web.servlet.ModelAndView;

import static com.ibm.db2.jcc.am.ib.id;

@Controller
@RequestMapping("/loanType")
public class LoanTypeListController extends BaseController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private IConfigModule configModule;
	@Autowired
	private ITypeService typeService;

	@Autowired
	private ITypeRelTableService typeRelTableService;

	@Autowired
	private IApplyInfoService applyInfoService;

	@Autowired
	private IModeScoreInfoProvider modeScoreInfoProvider;

	@Autowired
	private ISurveyFlowInfoService surveyFlowInfoService;

	@Value("${loan.three.table}")
	private String threeTable;
	@Value("${loan.source.mode}")
	private String sourceMode;
	/**
	 * 进入页面
	 * 
	 * @return
	 */
	@RequestMapping("/getLoanTypePage")
	public String getLoanTypePage() {
		setAttribute("userId",this.getLoginInfo().getUserId());
		return "/loan/vm/loanTypeList";
	}

	/**
	 * 查询贷款类型
	 */
	@RequestMapping("/queryLoanTypeList")
	@ResponseBody
	public void queryLoanTypeList(@RequestParam(value = "type") String type) {
		Map<String, Object> condition = new HashMap<String, Object>();
		if("contract".equals(type)){
			condition.put("isContractType",1);
		}else{
			condition.put("isContractType",0);
		}
			List<LoanType> loanTypeList = typeService.queryTypeList(condition);
			renderText(JsonUtil.toJson(loanTypeList, "typeId", "loanTypeName,isActived").toString());
	}


	/**
	 * 进入页面
	 *
	 * @return
	 */
	@RequestMapping("/getContractTypePage")
	public String getContractTypePage() {
		setAttribute("userId",this.getLoginInfo().getUserId());
		return "/loan/vm/contractTypeList";
	}


	/**
	 * 选择模板页面
	 * @param id
	 * @return
	 */
	@RequestMapping("/selectMode")
	public String selectMode(@RequestParam(value = "id", defaultValue = "") String id) {
		setAttribute("typeId", id);
		if (StringUtils.isNotBlank(id) && StringUtils.isNumeric(id)) {
			LoanType type = typeService.getTypeById(Integer.parseInt(id));
			if (type != null) {
				setAttribute("configId", type.getModeConfigId());
			}
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("isDel", 0);
		params.put("modeType", 1);
		List<ModeConfigFile> modeConfigFiles = configModule.getConfigFileProvider().queryConfigFileList(params);
		setAttribute("configFiles", modeConfigFiles);
		return "/config/vm/modelConfig/selectMode";
	}

	/**
	 *
	 * @param id
	 * @param configId
	 */
	@RequestMapping(value = "/selectModeSave")
	@ResponseBody
	public void selectModeSave(@RequestParam(value = "id", defaultValue = "") String id, @RequestParam(value = "configId", defaultValue = "") String configId){
		if (StringUtils.isNotBlank(id) && StringUtils.isNumeric(id)) {
			LoanType type = new LoanType();
			type.setTypeId(Integer.parseInt(id));
			if (StringUtils.isNotBlank(configId) && StringUtils.isNumeric(configId)) {
				type.setModeConfigId(Integer.parseInt(configId));
			} else {
				type.setModeConfigId(0);
			}
			typeService.updateType(type, getLoginInfo().getUserId());
			renderText(true,"配置成功","");
		} else {
			renderText(false,"参数错误","id:"+id + ";configId:" + configId);
		}
	}

	/**
	 * 启用贷款类型
	 * 
	 * @param id
	 */
	@RequestMapping("/enable/{id}")
	public void enableLoanType(@PathVariable Integer id) {
		Integer loginUserId = getLoginInfo().getUserId();
		LoanType lt = new LoanType();
		lt.setTypeId(id);
		lt.setIsActived(GlobalConst.YesOrNo.YES);
		typeService.updateType(lt, loginUserId);
		renderText(SUCCESS);
	}

	/**
	 * 禁用贷款类型
	 * 
	 * @param id
	 */
	@RequestMapping("/disable/{id}")
	public void disableLoanType(@PathVariable Integer id) {
		Integer loginUserId = getLoginInfo().getUserId();
		LoanType lt = new LoanType();
		lt.setTypeId(id);
		lt.setIsActived(GlobalConst.YesOrNo.NO);
		typeService.updateType(lt, loginUserId);
		renderText(SUCCESS);
	}

	/**
	 * 进入合同修改页面
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("/getContractTypeUpdatePage")
	public String getContractTypeUpdatePage(@RequestParam("typeId") Integer id) {
		LoanType lt = typeService.getTypeById(id);
		Integer classId= lt.getLoanClassId();
		Integer typeId = lt.getTypeId();
		String loanTypeName = lt.getLoanTypeName();
		setAttribute("loanTypeName",loanTypeName);
		setAttribute("classId",classId);
		setAttribute("typeId",typeId);
		setAttribute("isUsed",true);
		return "/loan/vm/contractTypeSave";
	}

	/**
	 * 进入修改页面
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/getLoanTypeUpdatePage")
	public String getUpdateLoanTypePage(@RequestParam("typeId") Integer id) {
		LoanType lt = typeService.getTypeById(id);
		Integer classId= lt.getLoanClassId();
		Integer typeId = lt.getTypeId();
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("loanTypeId",typeId);
		condition.put("loanCLassId",classId);
		List<LoanApplyInfo> loanApplyInfos = applyInfoService.queryApplyInfoList(condition);
		List<ModeScoreInfo> modeScoreInfos = modeScoreInfoProvider.queryModeScoreInfoes();
		Map<String,Object> suCondition = new HashMap<String,Object>();
		suCondition.put("isDel",0);
		List<LoanSurveyFlowInfo> surveyFlows = surveyFlowInfoService.querySurveyFlowInfoList(suCondition);
		setAttribute("surveyFlows",surveyFlows);
		if(loanApplyInfos != null && loanApplyInfos.size() >0){
			//已经有贷款使用这个三表类型
			setAttribute("isUsed",true);
		}else{
			setAttribute("isUsed",false);
		}
		setAttribute("threeTable",threeTable);
		setAttribute("sourceMode",sourceMode);
		setAttribute("LoanType", lt);
		setAttribute("modeScoreInfos",modeScoreInfos);
		return "/loan/vm/loanTypeUpdate";
	}

	/**
	 * 更新贷款类型
	 * 
	 * @param id
	 */
	@RequestMapping("/updateLoanType")
	public void updateLoanTypePage(@RequestParam("typeId") Integer id,
								   @RequestParam("loanTypeName") String typeName,
								   @RequestParam(value = "loanClassId", defaultValue = "") String loanClassId,
								   @RequestParam(value = "modeId", defaultValue = "") String modeId,
								   @RequestParam("isAutoAllot")Integer isAutoAllot,
								   @RequestParam("allotTarget") Integer allotTarget,
								   @RequestParam("flowId") String flowId){
		Integer modeIdi =  StringUtils.isBlank(modeId) ? 0 : Integer.parseInt(modeId);
		Integer classId = StringUtils.isBlank(loanClassId) ? 0 : Integer.parseInt(loanClassId);
		Integer suFlowId = StringUtils.isBlank(flowId) ? 0 : Integer.parseInt(flowId);
		Integer loginUserId = getLoginInfo().getUserId();
		LoanType lt = new LoanType();
		lt.setTypeId(id);
		lt.setLoanTypeName(typeName);
		lt.setModeId(modeIdi);
		lt.setLoanClassId(classId);
		lt.setIsAutoAllot(isAutoAllot);
		lt.setAllotTarget(allotTarget);
		lt.setFlowId(suFlowId);
		typeService.updateType(lt, loginUserId);
		renderText(SUCCESS);
	}

	/**
	 * 跳转新增合同类型
	 * 
	 * @return
	 */
	@RequestMapping("getLoanContractTypeInsertPage")
	public ModelAndView getLoanTypeInsertPage() {
		ModelAndView mv = new ModelAndView("/loan/vm/contractTypeSave");
		return mv;
	}

	/**
	 * 跳转新增贷款类型
	 *
	 * @return
	 */
	@RequestMapping("getLoanTypeInsertPage")
	public ModelAndView getLoanContractTypeInsertPage() {

		ModelAndView mv = new ModelAndView("/loan/vm/loanTypeSave");
		List<ModeScoreInfo> modeScoreInfos = modeScoreInfoProvider.queryModeScoreInfoes();
		Map<String,Object> suCondition = new HashMap<String,Object>();
		suCondition.put("isDel",0);
		List<LoanSurveyFlowInfo> surveyFlows = surveyFlowInfoService.querySurveyFlowInfoList(suCondition);
		mv.addObject("surveyFlows",surveyFlows);
		mv.addObject("modeScoreInfos",modeScoreInfos);
		mv.addObject("threeTable",threeTable);
		mv.addObject("sourceMode",sourceMode);
		return mv;
	}

	/**
	 * 保存贷款类型
	 * 
	 * @param loanTypeName
	 */
	@RequestMapping("saveLoanType")
	public void InsertLoanType(@RequestParam("loanTypeName") String loanTypeName,
							   @RequestParam(value = "loanClassId", defaultValue = "") String loanClassId,
							   @RequestParam(value = "modeId", defaultValue = "") String modeId,
							   @RequestParam("isAutoAllot")Integer isAutoAllot,
							   @RequestParam("allotTarget") Integer allotTarget,
							   @RequestParam("flowId") String flowId,
	                           @RequestParam("type") String type) {
		Integer modeIdi =  StringUtils.isBlank(modeId) ? 0 : Integer.parseInt(modeId);
		Integer classId = StringUtils.isBlank(loanClassId) ? 0 : Integer.parseInt(loanClassId);
		Integer suFlowId = StringUtils.isBlank(flowId) ? 0 : Integer.parseInt(flowId);
		// 判断数据库中是否有相同的贷款类型
		Map<String, Object> condition = new HashMap<String, Object>();
		// 不存在保存贷款类型,保存
		Integer loginUserId = getLoginInfo().getUserId();
		LoanType lt = new LoanType();
		lt.setModeId(modeIdi);
		lt.setLoanTypeName(loanTypeName);
		lt.setLoanClassId(classId);
		lt.setIsAutoAllot(isAutoAllot);
		lt.setAllotTarget(allotTarget);
		lt.setIsActived(GlobalConst.YesOrNo.YES);
		lt.setFlowId(suFlowId);
		if("loan".equals(type)){
			Integer maxOrderNum = typeService.queryMaxOrderNum();
			if (null == maxOrderNum) {
				maxOrderNum = 0;
			}
			lt.setSortno(maxOrderNum + 1);
			lt.setIsContractType(0);
		}else{
			Integer maxOrderNum = typeService.queryContractMaxOrderNum();
			if (null == maxOrderNum) {
				maxOrderNum = 0;
			}
			lt.setSortno(maxOrderNum + 1);
			lt.setIsContractType(1);
		}
		typeService.insertType(lt, loginUserId);
		// 获取loanType id
		condition.put("loanTypeName", loanTypeName);
		Integer typeId = typeService.queryTypeList(condition).get(0).getTypeId();
		// 查询
		if("loan".equals(type)){
			for (LoanProcessTypeEnum e : LoanProcessTypeEnum.values()) {
				int[] tableIds = e.tableIds;
				// 遍历枚举判断每个步骤是否含有表
				if (tableIds.length > 0) {
					for (int i = 0; i < tableIds.length; i++) {
						LoanTypeRelTable ltrt = new LoanTypeRelTable();
						ltrt.setLoanTypeId(typeId);
						ltrt.setLoanPrecType(e.type);
						ltrt.setTableId(tableIds[i]);
						ltrt.setIsActived(GlobalConst.YesOrNo.YES);
						// 排序号
						ltrt.setSortno(i + 1);
						typeRelTableService.insertTypeRelTable(ltrt, loginUserId);
					}
				}
			}
		}else{
				int[] tableIds = LoanProcessTypeEnum.CONTRACT.tableIds;
				if (tableIds.length > 0) {
					for (int i = 0; i < tableIds.length; i++) {
						LoanTypeRelTable ltrt = new LoanTypeRelTable();
						ltrt.setLoanTypeId(typeId);
						ltrt.setLoanPrecType(LoanProcessTypeEnum.CONTRACT.type);
						ltrt.setTableId(tableIds[i]);
						ltrt.setIsActived(GlobalConst.YesOrNo.YES);
						// 排序号
						ltrt.setSortno(i + 1);
						typeRelTableService.insertTypeRelTable(ltrt, loginUserId);
					}
				}
		}
	}


	/**
	 * 校验姓名
	 * 
	 * @param loanTypeName
	 */
	@RequestMapping("checkLoanTypeNameRule")
	@ResponseBody
	public void checkLoanTypeNameRule(@RequestParam("loanTypeName") String loanTypeName,
			@RequestParam("typeId") Integer typeId,@RequestParam("type") String type) {
		if(typeId != null){
			LoanType typeById = typeService.getTypeById(typeId);
			if (null != typeById && loanTypeName.equals(typeById.getLoanTypeName())) {
				renderText(true, "", null);
			}
		}
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("loanTypeName", loanTypeName);
		if("loan".equals(type)){
			condition.put("isContractType",0);
		}else{
			condition.put("isContractType",1);
		}
		List<LoanType> list = typeService.queryTypeList(condition);

		// 如果数据库中存在相同的名字
		if (list.size() > 0) {
			renderText(false, "贷款类型已经存在，请重新输入", null);
		} else {
			renderText(true, "", null);
		}
	}
}
