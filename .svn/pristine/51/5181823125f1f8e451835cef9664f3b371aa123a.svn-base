package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.config.IntoTrialRuleParams;

/**
 * 初审规则参数表业务访问接口
 */
public interface ITrialRuleParamsService {

	/**
	 * 新增初审规则参数表
	 * @param trialRuleParams 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertTrialRuleParams(IntoTrialRuleParams trialRuleParams, Integer loginUserId);

	/**
	 *修改初审规则参数表
	 * @param trialRuleParams 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateTrialRuleParams(IntoTrialRuleParams trialRuleParams, Integer loginUserId);

	/**
	 * 通过主键删除初审规则参数表
	 * @param PARAM_ID 主键Id
	 */
	void deleteTrialRuleParamsById(Integer paramId);

	/**
	 * 通过主键得到初审规则参数表
	 * @param PARAM_ID 主键Id
	 */
	IntoTrialRuleParams getTrialRuleParamsById(Integer paramId);

	/**
	 * 查询初审规则参数表
	 * @param condition 查询条件
	 * @return
	 */
	List<IntoTrialRuleParams> queryTrialRuleParamsList(Map<String, Object> condition);

	/**
	 * 分页查询初审规则参数表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<IntoTrialRuleParams> queryTrialRuleParamsList(Map<String, Object> condition, IPageSize page);

}
