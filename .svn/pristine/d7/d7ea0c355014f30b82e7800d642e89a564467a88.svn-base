package banger.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import banger.domain.loan.LoanFlowStepContent;
import banger.framework.util.StringUtil;
import banger.moduleIntf.ILoanSurveryFlowProvider;
import banger.service.intf.IFlowStepContentService;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import banger.domain.loan.LoanFlowStepItem;
import banger.framework.util.DateUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.IFlowStepItemDao;
import banger.service.intf.IFlowStepItemService;

/**
 * 流程步骤明细表业务访问类
 */
@Repository
public class FlowStepItemService implements IFlowStepItemService {

	@Autowired
	private IFlowStepItemDao flowStepItemDao;

	@Autowired
	private IFlowStepContentService flowStepContentService;

	/**
	 * 新增流程步骤明细表
	 * @param flowStepItem 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertFlowStepItem(LoanFlowStepItem flowStepItem,Integer loginUserId){
		flowStepItem.setCreateUser(loginUserId);
		flowStepItem.setCreateDate(DateUtil.getCurrentDate());
		flowStepItem.setUpdateUser(loginUserId);
		flowStepItem.setUpdateDate(DateUtil.getCurrentDate());
		this.flowStepItemDao.insertFlowStepItem(flowStepItem);
	}

	/**
	 *修改流程步骤明细表
	 * @param flowStepItem 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateFlowStepItem(LoanFlowStepItem flowStepItem,Integer loginUserId){
		flowStepItem.setUpdateUser(loginUserId);
		flowStepItem.setUpdateDate(DateUtil.getCurrentDate());
		this.flowStepItemDao.updateFlowStepItem(flowStepItem);
	}

	/**
	 * 通过主键删除流程步骤明细表
	 * @param id 主键Id
	 */
	public void deleteFlowStepItemById(Integer id){
		this.flowStepItemDao.deleteFlowStepItemById(id);
	}

	/**
	 * 通过主键得到流程步骤明细表
	 * @param id 主键Id
	 */
	public LoanFlowStepItem getFlowStepItemById(Integer id){
		return this.flowStepItemDao.getFlowStepItemById(id);
	}

	/**
	 * 查询流程步骤明细表
	 * @param condition 查询条件
	 * @return
	 */
	public List<LoanFlowStepItem> queryFlowStepItemList(Map<String,Object> condition){
		return this.flowStepItemDao.queryFlowStepItemList(condition);
	}

	/**
	 * 分页查询流程步骤明细表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<LoanFlowStepItem> queryFlowStepItemList(Map<String,Object> condition,IPageSize page){
		return this.flowStepItemDao.queryFlowStepItemList(condition,page);
	}


	/**
	 * 判断还是添加中转方法
	 * @param subObj json对象
	 * @param stepId 步骤id
	 */
	public void updaOrAddSub(JSONObject subObj, Integer stepId, Integer loginUserId)throws Exception{
		String subId = subObj.getString("subId");
		String subStance = subObj.getString("subStance");
		Integer sortNo = subObj.getInt("sortNo");
		if(StringUtil.isNullOrEmpty(subId)){          //新增
			LoanFlowStepContent stepContent = new LoanFlowStepContent();
			stepContent.setSortNo(sortNo);
			stepContent.setSubstance(subStance);
			stepContent.setFlowId(stepId);//步骤id
			flowStepContentService.insertFlowStepContent(stepContent,loginUserId);
		}else{//修改
			LoanFlowStepContent stepContent = new LoanFlowStepContent();
			stepContent.setSortNo(sortNo);
			stepContent.setSubstance(subStance);
			stepContent.setId(Integer.valueOf(subId));
			flowStepContentService.updateFlowStepContent(stepContent,loginUserId);
		}
	}

	/**
	 * 获取牌序号
	 * @return
	 */
	public Integer getMaxSortNum(){
		return this.flowStepItemDao.getMaxSortNum();
	}



	/**
	 * 上移下移
	 * @param stepId
	 * @param type
	 */
	public void moveStepSortNo(Integer stepId,String type,Integer flowId){
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("isDel",0);
		condition.put("flowId",flowId);
		List<LoanFlowStepItem> list = flowStepItemDao.queryFlowStepItemList(condition);
		LoanFlowStepItem moveCol = null;
		LoanFlowStepItem changeCol = null;

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getId().intValue() == stepId.intValue()) {
				// 移动行
				moveCol = list.get(i);
				// 下移
				if ("moveDown".equals(type)) {
					int last = i + 1;
					// 如果不是最后一行
					if (last < list.size()) {
						changeCol = list.get(last);
					}
					// 上移
				} else if ("moveUp".equals(type)) {
					int pre = i - 1;
					if (i > 0) {
						changeCol = list.get(pre);
					}
				}
			}
		}
		// 对换排序号
		if (changeCol != null && moveCol != null) {
			int sortno = moveCol.getSortNo();
			moveCol.setSortNo(changeCol.getSortNo());
			changeCol.setSortNo(sortno);
			flowStepItemDao.updateFlowStepItem(changeCol);
			flowStepItemDao.updateFlowStepItem(moveCol);
		}

	}

}
