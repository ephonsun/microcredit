package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.domain.config.IntoTrialRuleDetailQuery;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.config.IntoTrialRuleDetail;

/**
 * 初审规则明细表业务访问接口
 */
public interface ITrialRuleDetailService {

	/**
	 * 新增初审规则明细表
	 * @param trialRuleDetail 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertTrialRuleDetail(IntoTrialRuleDetail trialRuleDetail, Integer loginUserId);

	/**
	 *修改初审规则明细表
	 * @param trialRuleDetail 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateTrialRuleDetail(IntoTrialRuleDetail trialRuleDetail, Integer loginUserId);

	/**
	 * 通过主键删除初审规则明细表
	 * @param OPTION_ID 主键Id
	 */
	void deleteTrialRuleDetailById(Integer optionId);

	/**
	 * 通过主键得到初审规则明细表
	 * @param OPTION_ID 主键Id
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
	boolean checkTrialRuleDetaiOnly(Map<String, Object> condition);
}
