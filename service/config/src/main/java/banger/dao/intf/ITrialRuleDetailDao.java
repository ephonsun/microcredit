package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.domain.config.IntoTrialRuleDetailQuery;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.config.IntoTrialRuleDetail;

/**
 * 初审规则明细表数据访问接口
 */
public interface ITrialRuleDetailDao {

	/**
	 * 新增初审规则明细表
	 * @param trialRuleDetail 实体对像
	 */
	void insertTrialRuleDetail(IntoTrialRuleDetail trialRuleDetail);

	/**
	 *修改初审规则明细表
	 * @param trialRuleDetail 实体对像
	 */
	void updateTrialRuleDetail(IntoTrialRuleDetail trialRuleDetail);

	/**
	 * 通过主键删除初审规则明细表
	 * @param optionId 主键Id
	 */
	void deleteTrialRuleDetailById(Integer optionId);

	/**
	 * 通过主键得到初审规则明细表
	 * @param optionId 主键Id
	 */
	IntoTrialRuleDetailQuery getTrialRuleDetailById(Integer optionId);

	/**
	 * 查询初审规则明细表
	 * @param condition 查询条件
	 * @return
	 */
	List<IntoTrialRuleDetailQuery> queryTrialRuleDetailList(Map<String, Object> condition);

	/**
	 * 分页查询初审规则明细表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<IntoTrialRuleDetailQuery> queryTrialRuleDetailList(Map<String, Object> condition, IPageSize page);

	/**
	 * 检测唯一性
	 * @param condition
	 * @return
	 */
	List<IntoTrialRuleDetailQuery> checkTrialRuleDetaiOnly(Map<String, Object> condition);

}
