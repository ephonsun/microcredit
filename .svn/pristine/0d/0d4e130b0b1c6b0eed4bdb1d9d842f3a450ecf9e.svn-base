package banger.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.ITrialRuleInfoDao;
import banger.domain.config.IntoTrialRuleInfo;

/**
 * 初审规则表数据访问类
 */
@Repository
public class TrialRuleInfoDao extends PageSizeDao<IntoTrialRuleInfo> implements ITrialRuleInfoDao {

	/**
	 * 新增初审规则表
	 * @param trialRuleInfo 实体对像
	 */
	public void insertTrialRuleInfo(IntoTrialRuleInfo trialRuleInfo){
		trialRuleInfo.setRuleId(this.newId().intValue());
		this.execute("insertTrialRuleInfo",trialRuleInfo);
	}

	/**
	 *修改初审规则表
	 * @param trialRuleInfo 实体对像
	 */
	public void updateTrialRuleInfo(IntoTrialRuleInfo trialRuleInfo){
		this.execute("updateTrialRuleInfo",trialRuleInfo);
	}

	/**
	 * 通过主键删除初审规则表
	 * @param ruleId 主键Id
	 */
	public void deleteTrialRuleInfoById(Integer ruleId){
		this.execute("deleteTrialRuleInfoById",ruleId);
	}

	/**
	 * 通过主键得到初审规则表
	 * @param ruleId 主键Id
	 */
	public IntoTrialRuleInfo getTrialRuleInfoById(Integer ruleId){
		return (IntoTrialRuleInfo)this.queryEntity("getTrialRuleInfoById",ruleId);
	}

	/**
	 * 查询初审规则表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<IntoTrialRuleInfo> queryTrialRuleInfoList(Map<String,Object> condition){
		return (List<IntoTrialRuleInfo>)this.queryEntities("queryTrialRuleInfoList", condition);
	}

	/**
	 * 分页查询初审规则表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<IntoTrialRuleInfo> queryTrialRuleInfoList(Map<String,Object> condition,IPageSize page){
		return (IPageList<IntoTrialRuleInfo>)this.queryEntities("queryTrialRuleInfoList", page, condition);
	}

}
