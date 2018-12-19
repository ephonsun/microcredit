package banger.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.util.DateUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.IFlowStepContentDao;
import banger.service.intf.IFlowStepContentService;
import banger.domain.loan.LoanFlowStepContent;

/**
 * 调查流程步骤内容表业务访问类
 */
@Repository
public class FlowStepContentService implements IFlowStepContentService {

	@Autowired
	private IFlowStepContentDao flowStepContentDao;

	/**
	 * 新增调查流程步骤内容表
	 * @param flowStepContent 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertFlowStepContent(LoanFlowStepContent flowStepContent,Integer loginUserId){
		flowStepContent.setCreateUser(loginUserId);
		flowStepContent.setCreateDate(DateUtil.getCurrentDate());
		flowStepContent.setUpdateUser(loginUserId);
		flowStepContent.setUpdateDate(DateUtil.getCurrentDate());
		this.flowStepContentDao.insertFlowStepContent(flowStepContent);
	}

	/**
	 *修改调查流程步骤内容表
	 * @param flowStepContent 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateFlowStepContent(LoanFlowStepContent flowStepContent,Integer loginUserId){
		flowStepContent.setUpdateUser(loginUserId);
		flowStepContent.setUpdateDate(DateUtil.getCurrentDate());
		this.flowStepContentDao.updateFlowStepContent(flowStepContent);
	}

	/**
	 * 通过主键删除调查流程步骤内容表
	 * @param ID 主键Id
	 */
	public void deleteFlowStepContentById(Integer id){
		this.flowStepContentDao.deleteFlowStepContentById(id);
	}

	/**
	 * 通过主键得到调查流程步骤内容表
	 * @param ID 主键Id
	 */
	public LoanFlowStepContent getFlowStepContentById(Integer id){
		return this.flowStepContentDao.getFlowStepContentById(id);
	}

	/**
	 * 查询调查流程步骤内容表
	 * @param condition 查询条件
	 * @return
	 */
	public List<LoanFlowStepContent> queryFlowStepContentList(Map<String,Object> condition){
		return this.flowStepContentDao.queryFlowStepContentList(condition);
	}

	/**
	 * 分页查询调查流程步骤内容表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<LoanFlowStepContent> queryFlowStepContentList(Map<String,Object> condition,IPageSize page){
		return this.flowStepContentDao.queryFlowStepContentList(condition,page);
	}

}
