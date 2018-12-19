package banger.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import banger.dao.intf.IApproveConditionParamsDao;
import banger.dao.intf.IApproveProcessDao;
import banger.domain.loan.WfApproveConditionParams;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.util.DateUtil;
import banger.service.intf.IApproveConditionParamsService;

/**
 * 审批进程条件参数表业务访问类
 */
@Service
public class ApproveConditionParamsService implements IApproveConditionParamsService {

	@Resource
	private IApproveConditionParamsDao approveConditionParamsDao;
	
	@Resource
	private IApproveProcessDao approveProcessDao;
	
	@Override
	public void deleteParamsAndProcess(Integer conditionId,Integer flowId) {
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("flowId", flowId);
		condition.put("isDel", 0);
		condition.put("conditionId", conditionId);
		List<WfApproveConditionParams> approveConditionParamses = this.approveConditionParamsDao.queryApproveConditionParamsList(condition);
		if(approveConditionParamses != null && approveConditionParamses.size()>0){
			for (WfApproveConditionParams temp : approveConditionParamses) {
				this.approveProcessDao.deleteApproveProcessByParamId(temp.getParamsId());
			}
		}
		this.approveConditionParamsDao.deleteApproveConditionParamsByConditionId(conditionId);
	}

	/**
	 * 新增审批进程条件参数表
	 * @param approveConditionParams 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertApproveConditionParams(WfApproveConditionParams approveConditionParams,Integer loginUserId){
		approveConditionParams.setCreateUser(loginUserId);
		approveConditionParams.setCreateDate(DateUtil.getCurrentDate());
		approveConditionParams.setUpdateUser(loginUserId);
		approveConditionParams.setUpdateDate(DateUtil.getCurrentDate());
		this.approveConditionParamsDao.insertApproveConditionParams(approveConditionParams);
	}

	/**
	 *修改审批进程条件参数表
	 * @param approveConditionParams 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateApproveConditionParams(WfApproveConditionParams approveConditionParams,Integer loginUserId){
		approveConditionParams.setUpdateUser(loginUserId);
		approveConditionParams.setUpdateDate(DateUtil.getCurrentDate());
		this.approveConditionParamsDao.updateApproveConditionParams(approveConditionParams);
	}

	/**
	 * 通过主键删除审批进程条件参数表
	 * @param PARAMS_ID 主键Id
	 */
	public void deleteApproveConditionParamsById(Integer paramsId){
		this.approveConditionParamsDao.deleteApproveConditionParamsById(paramsId);
	}

	/**
	 * 通过主键得到审批进程条件参数表
	 * @param PARAMS_ID 主键Id
	 */
	public WfApproveConditionParams getApproveConditionParamsById(Integer paramsId){
		return this.approveConditionParamsDao.getApproveConditionParamsById(paramsId);
	}

	/**
	 * 查询审批进程条件参数表
	 * @param condition 查询条件
	 * @return
	 */
	public List<WfApproveConditionParams> queryApproveConditionParamsList(Map<String,Object> condition){
		return this.approveConditionParamsDao.queryApproveConditionParamsList(condition);
	}

	/**
	 * 分页查询审批进程条件参数表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<WfApproveConditionParams> queryApproveConditionParamsList(Map<String,Object> condition,IPageSize page){
		return this.approveConditionParamsDao.queryApproveConditionParamsList(condition,page);
	}

}
