package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.config.IntoTrialRuleInfo;

/**
 * 初审规则表业务访问接口
 */
public interface ITrialRuleInfoService {

	/**
	 * 新增初审规则表
	 * @param trialRuleInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertTrialRuleInfo(IntoTrialRuleInfo trialRuleInfo, Integer loginUserId);

	/**
	 *修改初审规则表
	 * @param trialRuleInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateTrialRuleInfo(IntoTrialRuleInfo trialRuleInfo, Integer loginUserId);

	/**
	 * 通过主键删除初审规则表
	 * @param RULE_ID 主键Id
	 */
	void deleteTrialRuleInfoById(Integer ruleId);

	/**
	 * 通过主键得到初审规则表
	 * @param RULE_ID 主键Id
	 */
	IntoTrialRuleInfo getTrialRuleInfoById(Integer ruleId);

	/**
	 * 查询初审规则表
	 * @param condition 查询条件
	 * @return
	 */
	List<IntoTrialRuleInfo> queryTrialRuleInfoList(Map<String, Object> condition);

	/**
	 * 分页查询初审规则表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<IntoTrialRuleInfo> queryTrialRuleInfoList(Map<String, Object> condition, IPageSize page);

}
