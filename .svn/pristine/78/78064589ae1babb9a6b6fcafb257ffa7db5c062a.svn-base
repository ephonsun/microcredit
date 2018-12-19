package banger.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import banger.domain.enumerate.LoanAuditResultEnum;
import banger.moduleIntf.ICurrentAuditStatusProvider;
import org.springframework.stereotype.Service;

import banger.dao.intf.ICurrentAuditStatusDao;
import banger.domain.loan.LoanCurrentAuditStatus;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.util.DateUtil;
import banger.service.intf.ICurrentAuditStatusService;

/**
 * 审批进度审核状态表业务访问类
 */
@Service
public class CurrentAuditStatusService implements ICurrentAuditStatusService,ICurrentAuditStatusProvider {

	@Resource
	private ICurrentAuditStatusDao currentAuditStatusDao;

	/**
	 * 新增审批进度审核状态表
	 * @param currentAuditStatus 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertCurrentAuditStatus(LoanCurrentAuditStatus currentAuditStatus,Integer loginUserId){
		//设置审核状态和是否有效的默认值
		currentAuditStatus.setAuditResult(LoanAuditResultEnum.PERSONAL.value);
		currentAuditStatus.setIsValid(1);
		//设置初始时间和创建用户
		currentAuditStatus.setCreateUser(loginUserId);
		currentAuditStatus.setCreateDate(DateUtil.getCurrentDate());
		currentAuditStatus.setUpdateUser(loginUserId);
		currentAuditStatus.setUpdateDate(DateUtil.getCurrentDate());
		this.currentAuditStatusDao.insertCurrentAuditStatus(currentAuditStatus);
	}

	/**
	 *修改审批进度审核状态表
	 * @param currentAuditStatus 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateCurrentAuditStatus(LoanCurrentAuditStatus currentAuditStatus,Integer loginUserId){
		currentAuditStatus.setUpdateUser(loginUserId);
		currentAuditStatus.setUpdateDate(DateUtil.getCurrentDate());
		this.currentAuditStatusDao.updateCurrentAuditStatus(currentAuditStatus);
	}

	/**
	 * 通过主键删除审批进度审核状态表
	 * @param id 主键Id
	 */
	public void deleteCurrentAuditStatusById(Integer id){
		this.currentAuditStatusDao.deleteCurrentAuditStatusById(id);
	}

	/**
	 * 通过主键得到审批进度审核状态表
	 * @param id 主键Id
	 */
	public LoanCurrentAuditStatus getCurrentAuditStatusById(Integer id){
		return this.currentAuditStatusDao.getCurrentAuditStatusById(id);
	}

	/**
	 * 查询审批进度审核状态表
	 * @param condition 查询条件
	 * @return
	 */
	public List<LoanCurrentAuditStatus> queryCurrentAuditStatusList(Map<String,Object> condition){
		return this.currentAuditStatusDao.queryCurrentAuditStatusList(condition);
	}

	/**
	 * 分页查询审批进度审核状态表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<LoanCurrentAuditStatus> queryCurrentAuditStatusList(Map<String,Object> condition,IPageSize page){
		return this.currentAuditStatusDao.queryCurrentAuditStatusList(condition,page);
	}

	/**
	 * 审批驳回时，更新审批人员的所有信息不可用
	 * @param loanId
	 */
	@Override
	public void updateAuditStatusByLoanId(Integer loanId) {
		this.currentAuditStatusDao.updateAuditStatusByLoanId(loanId);
	}

	@Override
	public void deleteAuditStatusByLoanId(Integer loanId) {
		this.currentAuditStatusDao.deleteAuditStatusByLoanId(loanId);
	}
}
