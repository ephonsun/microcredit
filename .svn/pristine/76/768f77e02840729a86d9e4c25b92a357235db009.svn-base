package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.config.IntoTrialRuleParams;

/**
 * 初审规则参数表数据访问接口
 */
public interface ITrialRuleParamsDao {

	/**
	 * 新增初审规则参数表
	 * @param trialRuleParams 实体对像
	 */
	void insertTrialRuleParams(IntoTrialRuleParams trialRuleParams);

	/**
	 *修改初审规则参数表
	 * @param trialRuleParams 实体对像
	 */
	void updateTrialRuleParams(IntoTrialRuleParams trialRuleParams);

	/**
	 * 通过主键删除初审规则参数表
	 * @param paramId 主键Id
	 */
	void deleteTrialRuleParamsById(Integer paramId);

	/**
	 * 通过主键得到初审规则参数表
	 * @param paramId 主键Id
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
