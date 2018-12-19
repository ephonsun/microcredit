package banger.service.impl;

import banger.dao.intf.IFlowStepItemDao;
import banger.domain.loan.LoanApplyInfo;
import banger.domain.loan.LoanFlowStepContent;
import banger.domain.loan.LoanFlowStepItem;
import banger.domain.loan.LoanType;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.util.DateUtil;
import banger.framework.util.StringUtil;
import banger.moduleIntf.ILoanSurveryFlowProvider;
import banger.service.intf.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 流程步骤明细表业务访问类
 */
@Repository
public class loanSurveryFlowProviderImpl implements ILoanSurveryFlowProvider{

	@Autowired
	private ITypeService typeService;

	@Autowired
	private IFlowStepContentService flowStepContentService;
	@Autowired
	private IFlowStepItemService flowStepItemService;

	@Autowired
	private ILoanApplyService loanApplyService;

	/**
	 * 根据贷款类型获得贷款类型信息
	 * @param typeId
	 * @return
	 */
	public LoanType getTypeById(Integer typeId){
		return 	this.typeService.getTypeById(typeId);
	}

	/**
	 * 查询流程步骤明细表
	 * @param condition 查询条件
	 * @return
	 */
	public List<LoanFlowStepItem> queryFlowStepItemList(Map<String, Object> condition){
		return this.flowStepItemService.queryFlowStepItemList(condition);
	}

	/**
	 * 查询调查流程步骤内容表
	 * @param condition 查询条件
	 * @return
	 */
	public List<LoanFlowStepContent> queryFlowStepContentList(Map<String,Object> condition){
		 return  this.flowStepContentService.queryFlowStepContentList(condition);
	}

	/**
	 * 通过主键得到贷款申请表
	 * @param loanId 主键Id
	 */
    public LoanApplyInfo getApplyInfoById(Integer loanId) {
        return this.loanApplyService.getApplyInfoById(loanId);
    }

	/**
	 * 得到所有有流程步骤的贷款类型ID列表,启用缓存
	 * @return
	 */
	public List<LoanType> getAllLoanTypeList(){
		return this.typeService.getAllLoanTypeList();
	}


}
