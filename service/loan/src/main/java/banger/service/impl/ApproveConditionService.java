package banger.service.impl;

import java.util.*;

import javax.annotation.Resource;

import banger.dao.intf.*;
import banger.domain.enumerate.EnumFiledType;
import banger.framework.codetable.CodeTableUtil;
import banger.framework.codetable.ICodeTable;
import banger.moduleIntf.IAutoFieldProvider;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import banger.domain.config.AutoTableColumn;
import banger.domain.loan.WfApproveCondition;
import banger.domain.loan.WfApproveConditionParams;
import banger.domain.loan.WfApproveInfoDef;
import banger.domain.system.SysDataDictCol;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.util.DateUtil;
import banger.moduleIntf.ISystemModule;
import banger.service.intf.IApproveConditionParamsService;
import banger.service.intf.IApproveConditionService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 审批进程条件表业务访问类
 */
@Service
public class ApproveConditionService implements IApproveConditionService {

	@Resource
	private IApproveConditionDao approveConditionDao;
	
	@Resource
	private IApproveConditionParamsDao approveConditionParamsDao;
	
	@Resource
	private IApproveInfoDefDao approveInfoDefDao;
	
	@Autowired
	private IApproveConditionParamsService approveConditionParamsService;
	
	@Autowired
	private ISystemModule systemModule;

	@Autowired
	private IApplyInfoDao applyInfoDao;

	@Autowired
	private ICurrentAuditStatusDao currentAuditStatusDao;

	@Autowired
	private IActionHistoryDao actionHistoryDao;

	@Autowired
	private IAutoFieldProvider autoFieldProvider;

	/**
	 * 新增审批进程条件表
	 * @param approveCondition 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertApproveCondition(WfApproveCondition approveCondition,Integer loginUserId){
		approveCondition.setCreateUser(loginUserId);
		approveCondition.setCreateDate(DateUtil.getCurrentDate());
		approveCondition.setUpdateUser(loginUserId);
		approveCondition.setUpdateDate(DateUtil.getCurrentDate());
		this.approveConditionDao.insertApproveCondition(approveCondition);
	}

	/**
	 *修改审批进程条件表
	 * @param approveCondition 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateApproveCondition(WfApproveCondition approveCondition,Integer loginUserId){
		approveCondition.setUpdateUser(loginUserId);
		approveCondition.setUpdateDate(DateUtil.getCurrentDate());
		this.approveConditionDao.updateApproveCondition(approveCondition);
	}

	/**
	 * 通过主键删除审批进程条件表
	 * @param conditionId 主键Id
	 */
	public void deleteApproveConditionById(Integer conditionId){
		this.approveConditionDao.deleteApproveConditionById(conditionId);
	}

	/**
	 * 通过主键得到审批进程条件表
	 * @param conditionId 主键Id
	 */
	public WfApproveCondition getApproveConditionById(Integer conditionId){
		return this.approveConditionDao.getApproveConditionById(conditionId);
	}

	/**
	 * 查询审批进程条件表
	 * @param condition 查询条件
	 * @return
	 */
	public List<WfApproveCondition> queryApproveConditionList(Map<String,Object> condition){
		return this.approveConditionDao.queryApproveConditionList(condition);
	}

	/**
	 * 分页查询审批进程条件表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<WfApproveCondition> queryApproveConditionList(Map<String,Object> condition,IPageSize page){
		return this.approveConditionDao.queryApproveConditionList(condition,page);
	}
	
	/**
	 * 查询审批条件
	 */
	public String queryConditionInfo(WfApproveInfoDef approveInfoDef){
		Integer flowId = approveInfoDef.getFlowId();
		if(approveInfoDef == null || approveInfoDef.getIsCondition() == 0){
			return "[{'paramId':0,'paramName':''}]";
		}else {
			Map<String,Object> condition = new HashMap<String,Object>();
			condition.put("flowId", flowId);
			condition.put("isDel", 0);
			List<WfApproveCondition> approveConditions = this.approveConditionDao.queryApproveConditionList(condition);
			if(approveConditions == null || approveConditions.size() <1){
				return "[]";
			}
			WfApproveCondition approveCondition = approveConditions.get(0);
			condition.put("conditionId", approveCondition.getConditionId());
			List<WfApproveConditionParams> approveConditionParamses = this.approveConditionParamsDao.queryApproveConditionParamsList(condition);
			if(approveConditionParamses == null || approveConditionParamses.size()<1){
				return "[]";
			}
			JSONArray array = new JSONArray();
			int size = approveConditionParamses.size();
			String fieldType = approveCondition.getFieldType();
			if(EnumFiledType.SELECT.equalType(fieldType) || EnumFiledType.MULTIPLE_SELECT.equalType(fieldType)) {
				List<ICodeTable.IItem> itemList = getSelectOptionItemByCondition(approveCondition);
				Map<String,String> itemMap = new HashMap<String,String>();
				Map<String,String> paramMap = new HashMap<String, String>();
				for(ICodeTable.IItem item : itemList)
					itemMap.put(item.getValue(),item.getName());
				for (int i = 0; i < size; i++) {
					WfApproveConditionParams temp = approveConditionParamses.get(i);
					JSONObject obj = new JSONObject();
					obj.put("paramId", temp.getParamsId());
					obj.put("conditionId",approveCondition.getConditionId());
					if(itemMap.containsKey(temp.getParam2())){	//代码表里有，显示代码表名称，没有显示参数名称
						obj.put("paramName", itemMap.get(temp.getParam2()));
					}else {
						obj.put("paramName", temp.getParam1());
					}
					obj.put("paramValue", temp.getParam2());
					array.add(obj);
					paramMap.put(temp.getParam2(),temp.getParam1());
				}
				for(ICodeTable.IItem item : itemList){			//数据源新增的数所项，也要显示出来
					if(!paramMap.containsKey(item.getValue())) {
						JSONObject obj = new JSONObject();
						obj.put("paramId", 0);
						obj.put("conditionId",approveCondition.getConditionId());
						obj.put("paramName", item.getName());
						obj.put("paramValue", item.getValue());
						array.add(obj);
					}
				}
			}else{
				for (int i = 0; i < size; i++) {
					WfApproveConditionParams temp = approveConditionParamses.get(i);
					JSONObject obj = new JSONObject();
					obj.put("paramId", temp.getParamsId());
					obj.put("conditionId",approveCondition.getConditionId());
					obj.put("paramName", createParamName(approveCondition, temp, i, size));
					obj.put("paramValue", temp.getParam2());
					array.add(obj);
				}
			}
			return array.toString();
		}
	}

	private List<ICodeTable.IItem> getSelectOptionItemByCondition(WfApproveCondition approveCondition){
		AutoTableColumn column = autoFieldProvider.getTableColumnByTableAndColumn(approveCondition.getTableName(),approveCondition.getTableColumn());
		if(column!=null){
			List<ICodeTable.IItem> itemList = CodeTableUtil.getCodeTable("cdDictColByName",column.getFieldDictName());
			return itemList;
		}
		return new ArrayList<ICodeTable.IItem>();
	}
	
	/**
	 * 根据自定义字段名称和自定义字段类型 创建名称
	 * fieldName;	自定义字段名称
	 * fieldType;	自定义字段类型Decimal   金额Number   数值Select     单选下拉框
	 */
	private String createParamName(WfApproveCondition con,WfApproveConditionParams params,int i,int size){
		String name = "";
		String fieldType = con.getFieldType();
		String fieldName = con.getFieldName();
		String param1 = params.getParam1();
		if(EnumFiledType.SELECT.equalType(fieldType) || EnumFiledType.MULTIPLE_SELECT.equalType(fieldType)){
			name =  param1;
		}else if (EnumFiledType.DECIMAL.equalType(fieldType)||EnumFiledType.NUMBER.equalType(fieldType)||EnumFiledType.FLOAT.equalType(fieldType)) {
			if(i == 0){
				name =  "当 “"+fieldName+"” < "+param1;
			}else if (i == size -1) {
				name =  "当 "+param1+" ≤ “"+fieldName+"”";
			}else {
				name =  "当 "+param1+" ≤ “"+fieldName+"” < "+params.getParam2();
			}
		}
		return name;
	}
	
	/**
	 * 删除条件(包括条件参数 和 参数下的审批环节) 新增条件和条件参数
	 */
	public void saveApproveCondition(AutoTableColumn tableColumn,Integer flowId,String count,String[] nums,Integer loginUserId){
		//更新版本号
		this.approveInfoDefDao.updateVersionById(new Date().getTime()+"", flowId);

		//把审核中的贷款打回调查
		this.resetLoanFlow(flowId);
		//删除条件
		Map<String,Object> conditionMap = new HashMap<String,Object>();
		conditionMap.put("flowId", flowId);
		conditionMap.put("isDel", 0);
		List<WfApproveCondition> approveConditions = this.approveConditionDao.queryApproveConditionList(conditionMap);
		if(approveConditions != null && approveConditions.size() > 0){
			WfApproveCondition approveCondition = approveConditions.get(0);
			this.approveConditionParamsService.deleteParamsAndProcess(approveCondition.getConditionId(),flowId);
		}
		this.approveConditionDao.deleteApproveConditionByFlowId(flowId);
		//新增条件和条件参数
		WfApproveCondition condition = new WfApproveCondition();
		condition.setFlowId(flowId);
		condition.setFieldId(tableColumn.getFieldId());
		condition.setFieldName(tableColumn.getFieldName());
		condition.setFieldType(tableColumn.getFieldType());
		condition.setTableName(tableColumn.getTableName());
		condition.setTableColumn(tableColumn.getFieldColumn());
		this.insertApproveCondition(condition,loginUserId);
		createConditionParams(tableColumn,flowId,condition.getConditionId(),count,nums,loginUserId);
	}

	private void resetLoanFlow(Integer flowId){
		currentAuditStatusDao.resetLoanFlowAuditStatusByFlowId(flowId);
		actionHistoryDao.insertHistoryByResetFlowFlowId(flowId);
		applyInfoDao.resetLoanFlowByFlowId(flowId);
	}
	
	
	/**
	 * 新建条件参数
	 */
	private void createConditionParams(AutoTableColumn tableColumn,Integer flowId,Integer conditionId,String count,String[] nums,Integer loginUserId){
		String fieldType = tableColumn.getFieldType();
		if("Select".equalsIgnoreCase(fieldType)){
			String discName = tableColumn.getFieldDictName();
			if(StringUtils.isBlank(discName))
				return ;
			List<SysDataDictCol> dataDictCols= systemModule.queryDataDictColListByName(tableColumn.getFieldDictName());
			if(dataDictCols != null && dataDictCols.size()>0){
				for (int i = 0; i < dataDictCols.size(); i++){
					WfApproveConditionParams params = new WfApproveConditionParams();
					params.setConditionId(conditionId);
					params.setFlowId(flowId);
					params.setParamNo(i+1);
					params.setParam1(dataDictCols.get(i).getDictName());
					params.setParam2(dataDictCols.get(i).getDictValue());
					approveConditionParamsService.insertApproveConditionParams(params,loginUserId);
				}
			}
		}else if ("Decimal".equalsIgnoreCase(fieldType)||"Number".equalsIgnoreCase(fieldType)||"Float".equalsIgnoreCase(fieldType)) {
			if(StringUtils.isNotBlank(count)&&StringUtils.isNumeric(count)){
				Integer sum = Integer.parseInt(count);
				for (int i = 0; i < sum; i++) {
					WfApproveConditionParams params = new WfApproveConditionParams();
					params.setConditionId(conditionId);
					params.setFlowId(flowId);
					params.setParamNo(i+1);
					if(i == 0)
						params.setParam1(nums[i]);
					else if(i+1 == sum)
						params.setParam1(nums[i-1]);
					else{
						params.setParam1(nums[i-1]);
						params.setParam2(nums[i]);
					}
					approveConditionParamsService.insertApproveConditionParams(params,loginUserId);
				}
			}
		}
	}

}
