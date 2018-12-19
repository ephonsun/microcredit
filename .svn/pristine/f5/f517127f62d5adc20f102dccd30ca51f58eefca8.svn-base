package banger.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import banger.dao.intf.IApproveInfoDefDao;
import banger.domain.loan.WfApproveInfoDef;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.util.DateUtil;
import banger.service.intf.IApproveInfoDefService;

/**
 * 审批流程表信息定义表业务访问类
 */
@Service
public class ApproveInfoDefService implements IApproveInfoDefService {

	@Resource
	private IApproveInfoDefDao approveInfoDefDao;

	/**
	 * 新增审批流程表信息定义表
	 * @param approveInfoDef 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertApproveInfoDef(WfApproveInfoDef approveInfoDef,Integer loginUserId){
		approveInfoDef.setCreateUser(loginUserId);
		approveInfoDef.setCreateDate(DateUtil.getCurrentDate());
		approveInfoDef.setUpdateUser(loginUserId);
		approveInfoDef.setUpdateDate(DateUtil.getCurrentDate());
		this.approveInfoDefDao.insertApproveInfoDef(approveInfoDef);
	}

	/**
	 *修改审批流程表信息定义表
	 * @param approveInfoDef 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateApproveInfoDef(WfApproveInfoDef approveInfoDef,Integer loginUserId){
		approveInfoDef.setUpdateUser(loginUserId);
		approveInfoDef.setUpdateDate(DateUtil.getCurrentDate());
		this.approveInfoDefDao.updateApproveInfoDef(approveInfoDef);
	}

	/**
	 * 通过主键删除审批流程表信息定义表
	 * @param FLOW_ID 主键Id
	 */
	public void deleteApproveInfoDefById(Integer flowId){
		this.approveInfoDefDao.deleteApproveInfoDefById(flowId);
	}

	/**
	 * 通过主键得到审批流程表信息定义表
	 * @param FLOW_ID 主键Id
	 */
	public WfApproveInfoDef getApproveInfoDefById(Integer flowId){
		return this.approveInfoDefDao.getApproveInfoDefById(flowId);
	}

	/**
	 * 查询审批流程表信息定义表
	 * @param condition 查询条件
	 * @return
	 */
	public List<WfApproveInfoDef> queryApproveInfoDefList(Map<String,Object> condition){
		return this.approveInfoDefDao.queryApproveInfoDefList(condition);
	}

	/**
	 * 分页查询审批流程表信息定义表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<WfApproveInfoDef> queryApproveInfoDefList(Map<String,Object> condition,IPageSize page){
		return this.approveInfoDefDao.queryApproveInfoDefList(condition,page);
	}

	@Override
	public void insertApproveInfoDef(Integer isActived, Integer classId, Integer isCondition, Integer loginUserId) {
		WfApproveInfoDef approveInfoDef = new WfApproveInfoDef();
		approveInfoDef.setClassId(classId);
		approveInfoDef.setIsActived(isActived);
		approveInfoDef.setIsCondition(isCondition);
		approveInfoDef.setAuditVersion(new Date().getTime()+"");
		approveInfoDef.setCreateUser(loginUserId);
		approveInfoDef.setCreateDate(DateUtil.getCurrentDate());
		approveInfoDef.setUpdateUser(loginUserId);
		approveInfoDef.setUpdateDate(DateUtil.getCurrentDate());
		this.approveInfoDefDao.insertApproveInfoDef(approveInfoDef);
	}

	@Override
	public void updateVersionById(Integer flowId) {
		String version = new Date().getTime()+"";
		this.approveInfoDefDao.updateVersionById(version, flowId);
	}

}
