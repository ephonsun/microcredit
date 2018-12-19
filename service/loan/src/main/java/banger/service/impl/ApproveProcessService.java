package banger.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import banger.dao.intf.IActionHistoryDao;
import banger.dao.intf.IApplyInfoDao;
import banger.dao.intf.ICurrentAuditStatusDao;
import banger.domain.loan.WfApproveProcess_Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import banger.dao.intf.IApproveProcessDao;
import banger.domain.loan.WfApproveProcess;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.util.DateUtil;
import banger.service.intf.IApproveProcessService;

/**
 * 审批流环节定义表业务访问类
 */
@Service
public class ApproveProcessService implements IApproveProcessService {

	@Resource
	private IApproveProcessDao approveProcessDao;

	@Autowired
	private IApplyInfoDao applyInfoDao;

	@Autowired
	private IActionHistoryDao actionHistoryDao;

	@Autowired
	private ICurrentAuditStatusDao currentAuditStatusDao;

	/**
	 * 新增审批流环节定义表
	 * @param approveProcess 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertApproveProcess(WfApproveProcess approveProcess,Integer loginUserId){
		approveProcess.setCreateUser(loginUserId);
		approveProcess.setCreateDate(DateUtil.getCurrentDate());
		approveProcess.setUpdateUser(loginUserId);
		approveProcess.setUpdateDate(DateUtil.getCurrentDate());
		this.approveProcessDao.insertApproveProcess(approveProcess);
	}

	/**
	 *修改审批流环节定义表
	 * @param approveProcess 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateApproveProcess(WfApproveProcess approveProcess,Integer loginUserId){
		approveProcess.setUpdateUser(loginUserId);
		approveProcess.setUpdateDate(DateUtil.getCurrentDate());
		this.approveProcessDao.updateApproveProcess(approveProcess);
	}

	/**
	 * 通过主键删除审批流环节定义表
	 * @param processId 主键Id
	 */
	public void deleteApproveProcessById(Integer processId){
		this.resetLoanFlow(processId);
		this.approveProcessDao.deleteApproveProcessById(processId);
	}

	/**
	 *
	 * @param processId
	 */
	private void resetLoanFlow(Integer processId){
		this.currentAuditStatusDao.resetLoanFlowAuditStatusByProcessId(processId);
		this.actionHistoryDao.insertHistoryByResetLoanFlowProcessId(processId);
		this.applyInfoDao.resetLoanFlowByProcessId(processId);
	}

	/**
	 * 通过主键得到审批流环节定义表
	 * @param processId 主键Id
	 */
	public WfApproveProcess getApproveProcessById(Integer processId){
		return this.approveProcessDao.getApproveProcessById(processId);
	}

	/**
	 * 查询审批流环节定义表
	 * @param condition 查询条件
	 * @return
	 */
	public List<WfApproveProcess_Query> queryApproveProcessList(Map<String,Object> condition){
		List<WfApproveProcess_Query> approveProcesses = this.approveProcessDao.queryApproveProcessList(condition);
		if(approveProcesses != null){
			for (int i = 0; i < approveProcesses.size(); i++) {
				approveProcesses.get(i).setProcessName(String.valueOf(i+1));
			}
		}
		return approveProcesses;
	}


	/**
	 * 分页查询审批流环节定义表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<WfApproveProcess> queryApproveProcessList(Map<String,Object> condition,IPageSize page){
		return this.approveProcessDao.queryApproveProcessList(condition,page);
	}

	@Override
	public void setApproveProcessName(WfApproveProcess approveProcess) {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("flowId", approveProcess.getFlowId());
		condition.put("paramId", approveProcess.getParamId());
		condition.put("isDel", 0);
		Integer count = this.approveProcessDao.getApproveProcessCount(condition);
		//转成中文
		String[] chineStrs = new String[]{"一","二","三","四","五","六","七","八","九","十"};
		if(count<10) {
			approveProcess.setProcessName(chineStrs[count]+"审");
		}else if(count<20){
			approveProcess.setProcessName("十"+chineStrs[count%10]+"审");
		}else{
			approveProcess.setProcessName(chineStrs[count/10]+"十"+chineStrs[count%10]+"审");
		}
	}

	@Override
	public void setApproveProcessNo(WfApproveProcess approveProcess) {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("flowId", approveProcess.getFlowId());
		condition.put("paramId", approveProcess.getParamId());
		//condition.put("isDel", 0);
		Integer no = this.approveProcessDao.getApproveProcessMaxNo(condition);
		if(no == null)
			no = 0;
		approveProcess.setOrderNo(no+1);
	}

}
