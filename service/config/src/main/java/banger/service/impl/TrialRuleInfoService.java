package banger.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.util.DateUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.ITrialRuleInfoDao;
import banger.service.intf.ITrialRuleInfoService;
import banger.domain.config.IntoTrialRuleInfo;

/**
 * 初审规则表业务访问类
 */
@Repository
public class TrialRuleInfoService implements ITrialRuleInfoService {

	@Autowired
	private ITrialRuleInfoDao trialRuleInfoDao;

	/**
	 * 新增初审规则表
	 * @param trialRuleInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertTrialRuleInfo(IntoTrialRuleInfo trialRuleInfo,Integer loginUserId){
		trialRuleInfo.setCreateUser(loginUserId);
		trialRuleInfo.setCreateDate(DateUtil.getCurrentDate());
		trialRuleInfo.setUpdateUser(loginUserId);
		trialRuleInfo.setUpdateDate(DateUtil.getCurrentDate());
		this.trialRuleInfoDao.insertTrialRuleInfo(trialRuleInfo);
	}

	/**
	 *修改初审规则表
	 * @param trialRuleInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateTrialRuleInfo(IntoTrialRuleInfo trialRuleInfo,Integer loginUserId){
		trialRuleInfo.setUpdateUser(loginUserId);
		trialRuleInfo.setUpdateDate(DateUtil.getCurrentDate());
		this.trialRuleInfoDao.updateTrialRuleInfo(trialRuleInfo);
	}

	/**
	 * 通过主键删除初审规则表
	 * @param RULE_ID 主键Id
	 */
	public void deleteTrialRuleInfoById(Integer ruleId){
		this.trialRuleInfoDao.deleteTrialRuleInfoById(ruleId);
	}

	/**
	 * 通过主键得到初审规则表
	 * @param RULE_ID 主键Id
	 */
	public IntoTrialRuleInfo getTrialRuleInfoById(Integer ruleId){
		return this.trialRuleInfoDao.getTrialRuleInfoById(ruleId);
	}

	/**
	 * 查询初审规则表
	 * @param condition 查询条件
	 * @return
	 */
	public List<IntoTrialRuleInfo> queryTrialRuleInfoList(Map<String,Object> condition){
		return this.trialRuleInfoDao.queryTrialRuleInfoList(condition);
	}

	/**
	 * 分页查询初审规则表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<IntoTrialRuleInfo> queryTrialRuleInfoList(Map<String,Object> condition,IPageSize page){
		return this.trialRuleInfoDao.queryTrialRuleInfoList(condition,page);
	}

}
