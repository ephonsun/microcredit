package banger.controller;

import banger.common.BaseController;
import banger.common.annotation.TokenRepeatAnnotation;
import banger.domain.enumerate.LoanProcessTypeEnum;
import banger.domain.loan.LoanApplyInfo;
import banger.domain.loan.LoanApplyInfo_Web_Query;
import banger.domain.loan.LoanType;
import banger.framework.sql.command.SqlTransaction;
import banger.framework.web.dojo.JsonTools;
import banger.service.intf.IApplyInfoService;
import banger.service.intf.ITypeService;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 贷款申请
 * @author zhusw
 *
 */
@RequestMapping("/loanApply")
@Controller
public class LoanApplyController extends BaseController {
	private static final long serialVersionUID = -7305734846109770428L;

	@Autowired
	private IApplyInfoService applyInfoService;
	@Autowired
    private ITypeService typeService;


	/**
	 * 保存贷款申请信息
	 * @param json 自定义表单信息
	 * @return
	 */
	@RequestMapping(value = "/saveLoanApply", method = RequestMethod.POST)
	@ResponseBody
	@SqlTransaction
	@TokenRepeatAnnotation
	public void saveLoanApplyInfo(@RequestParam(value = "json", defaultValue = "") String json,
								  @RequestParam(value = "id", defaultValue = "") String id,
								  @RequestParam(value = "deleteIds", defaultValue = "") String deleteIds,
								  @RequestParam(value = "processType", defaultValue = "Apply") String processType,
								  @RequestParam(value = "customerId", defaultValue = "") String customerId,
								  @RequestParam(value = "applyId", defaultValue = "") String applyId,
								  @RequestParam(value = "loanTypeId", defaultValue = "") String loanTypeId,
								  @RequestParam(value = "potentialId", defaultValue = "0") String potentialId){//潜在客户id
		try {
			if (StringUtils.isNotBlank(json) && StringUtils.isNotBlank(loanTypeId) && StringUtils.isNumeric(loanTypeId)) {
				Integer loginUserId = getLoginInfo().getUserId();
				Integer teamGroupId = this.getLoginInfo().getTeamGroupId();
				if (teamGroupId == null && !"Apply".equals(processType)) {
					renderText(false,"团队信息不正确","json");
				} else {
					Map<String, Object> map = JsonTools.parseJSON2Map(json);
					LoanType loanType=typeService.getTypeById(Integer.parseInt(loanTypeId));
					//查看是否开启带宽分配控制校验，如果开启需要先判断时候具有合适的客户经理，才能执行下面逻辑
					List<LoanApplyInfo_Web_Query> info = applyInfoService.queryMinInGroup(teamGroupId);
					Integer allotId = getMemberId(loginUserId,loanType.getAllotTarget(), info);//分配给谁
					if (loanType.getIsAutoAllot()!=null && loanType.getIsAutoAllot().intValue()==1 && LoanProcessTypeEnum.ALLOT.type.equals(processType)){
						if (allotId == null ) {
							renderText(false,"贷款分配，没有合适的客户经理","");
							return ;
						}
					}
					if (map != null && map.size() > 0) {
						Map<String, Object> deleteIdsMap = JsonTools.parseJSON2Map(deleteIds);
						applyInfoService.deleteLoanTemplateByMap(deleteIdsMap);
						Integer custId = StringUtils.isNotBlank(customerId) && StringUtils.isNumeric(customerId) ? Integer.parseInt(customerId) : null;
						Integer applyIdi = StringUtils.isNotBlank(applyId) && StringUtils.isNumeric(applyId) ? Integer.parseInt(applyId) : null;
						Map<String, Object> resultMap= applyInfoService.saveLoanApplyInfo(map, id, processType, Integer.parseInt(loanTypeId), loginUserId, teamGroupId, custId, applyIdi,Integer.parseInt(potentialId));
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("id", resultMap.get("id"));
						jsonObject.put("newSave", resultMap.get("newSave"));
						renderJson((Boolean) resultMap.get("success"), (String) resultMap.get("message"), jsonObject);
						//判断是否自动分配
						if (loanType.getIsAutoAllot()!=null && loanType.getIsAutoAllot().intValue()==1 &&loanType.getAllotTarget()!=null&& LoanProcessTypeEnum.ALLOT.type.equals(processType)) {
							//查询团队下的各个经理待调查的数量（从小到大排序）
//							List<LoanApplyInfo_Web_Query> info = applyInfoService.queryMinInGroup(teamGroupId);
//							Integer allotId = getMemberId(loginUserId,loanType.getAllotTarget(), info);//分配给谁
//							if (allotId !=null) {
								applyInfoService.loanAllotSignSave(id, allotId, loginUserId);//执行自动分配
//							}
						}
					} else {
						renderText(false,"参数错误","");
					}
				}
			} else {
				renderText(false,"参数错误","");
			}
		} catch (Exception e) {
			log.error("保存贷款申请信息异常|"+json,e);
			renderText(false,"保存失败",String.valueOf(""));
		}

	}

	public Integer getMemberId(Integer loginUserId,Integer autoTarget,List<LoanApplyInfo_Web_Query> info){
		Integer memberId=null;
		if(autoTarget==0){//团队
			if(info != null && info.size()>0){
				memberId=info.get(0).getUserId();
			}else{
				return null;
			}
		}
		if(autoTarget==1){//申请人
			String loginUser = loginUserId.toString();
			for(LoanApplyInfo_Web_Query lai : info){
				String id =lai.getUserId().toString();
				if(id.equals(loginUser)){
					memberId=loginUserId;
					return memberId;
				}
			}
			return null;
		}
		if(autoTarget==2){//非申请人
			if(info != null && info.size()>0){
				if(info.get(0).getUserId().intValue()==loginUserId.intValue()){
					if(info.size()>1) {
						memberId = info.get(1).getUserId();
					}else{
						memberId=null;
					}
				}else{
					memberId=info.get(0).getUserId();
				}
			}else{
				return null;
			}
		}
		return  memberId;
	}

	/**
	 * 私有方法，从表单map设值给applyInfo
	 * @param applyInfo
	 * @param map
	 */
	private void setApplyInfo(LoanApplyInfo applyInfo, Map<String, Object> map){
		List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("LBIZ_PERSONAL_INFO");
		Map<String, Object> customerMap = new HashMap<String, Object>();
		if (CollectionUtils.isNotEmpty(list))
			customerMap = list.get(0);


		applyInfo.setLoanName((String)customerMap.get("field.customerName"));
		applyInfo.setLoanIdentifyType((String)customerMap.get("field.identifyType"));
		applyInfo.setLoanIdentifyNum((String)customerMap.get("field.identifyNum"));
		String customerAge = (String)customerMap.get("field.customerAge");
		if(StringUtils.isNotBlank(customerAge) && StringUtils.isNumeric(customerAge) ){
			applyInfo.setLoanAge(Integer.valueOf(customerAge));
		}
		applyInfo.setLoanSex((String)customerMap.get("field.customerSex"));
		applyInfo.setLoanTelNum((String)customerMap.get("field.phoneNumber"));

		list = (List<Map<String, Object>>) map.get("LBIZ_LOAN_APPLY_INFO");
		if (CollectionUtils.isNotEmpty(list))
			customerMap = list.get(0);
		String loanApplyAmout = (String)customerMap.get("field.loanApplyAmount");
		if (StringUtils.isNotBlank(loanApplyAmout)){
			BigDecimal bd = new BigDecimal(loanApplyAmout);
			bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
			applyInfo.setLoanApplyAmount(bd);
		}
		//保存行业
		list = (List<Map<String, Object>>) map.get("LBIZ_BUSINESS_SUBJECT");
		if (CollectionUtils.isNotEmpty(list)){
			String businessCatalog = (String)list.get(0).get("field.businessCatalog");
			applyInfo.setLoanBusinessCatalog(businessCatalog);
		}
	}
	
}
