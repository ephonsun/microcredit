package banger.service.impl;

import java.util.List;
import java.util.Map;

import banger.domain.config.IntoTrialRuleDetailQuery;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.util.DateUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.ITrialRuleDetailDao;
import banger.service.intf.ITrialRuleDetailService;
import banger.domain.config.IntoTrialRuleDetail;

/**
 * 初审规则明细表业务访问类
 */
@Repository
public class TrialRuleDetailService implements ITrialRuleDetailService {

	@Autowired
	private ITrialRuleDetailDao trialRuleDetailDao;

	/**
	 * 新增初审规则明细表
	 * @param trialRuleDetail 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertTrialRuleDetail(IntoTrialRuleDetail trialRuleDetail,Integer loginUserId){
		trialRuleDetail.setCreateUser(loginUserId);
		trialRuleDetail.setCreateDate(DateUtil.getCurrentDate());
		trialRuleDetail.setUpdateUser(loginUserId);
		trialRuleDetail.setUpdateDate(DateUtil.getCurrentDate());
		this.trialRuleDetailDao.insertTrialRuleDetail(trialRuleDetail);
	}

	/**
	 *修改初审规则明细表
	 * @param trialRuleDetail 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateTrialRuleDetail(IntoTrialRuleDetail trialRuleDetail,Integer loginUserId){
		trialRuleDetail.setUpdateUser(loginUserId);
		trialRuleDetail.setUpdateDate(DateUtil.getCurrentDate());
		this.trialRuleDetailDao.updateTrialRuleDetail(trialRuleDetail);
	}

	/**
	 * 通过主键删除初审规则明细表
	 * @param OPTION_ID 主键Id
	 */
	public void deleteTrialRuleDetailById(Integer optionId){
		this.trialRuleDetailDao.deleteTrialRuleDetailById(optionId);
	}

	/**
	 * 通过主键得到初审规则明细表
	 * @param OPTION_ID 主键Id
	 */
	public IntoTrialRuleDetailQuery getTrialRuleDetailById(Integer optionId){
		return this.trialRuleDetailDao.getTrialRuleDetailById(optionId);
	}

	/**
	 * 查询初审规则明细表
	 * @param condition 查询条件
	 * @return
	 */
	public List<IntoTrialRuleDetailQuery> queryTrialRuleDetailList(Map<String,Object> condition){
		return this.trialRuleDetailDao.queryTrialRuleDetailList(condition);
	}

	/**
	 * 分页查询初审规则明细表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<IntoTrialRuleDetailQuery> queryTrialRuleDetailList(Map<String,Object> condition, IPageSize page){
		return this.trialRuleDetailDao.queryTrialRuleDetailList(condition,page);
	}

	@Override
	public boolean checkTrialRuleDetaiOnly(Map<String, Object> condition) {
		List<IntoTrialRuleDetailQuery> list =trialRuleDetailDao.checkTrialRuleDetaiOnly(condition);
		if(list.size()>0)
		{
			return false;
		}else {
			return true;
		}
	}

}
