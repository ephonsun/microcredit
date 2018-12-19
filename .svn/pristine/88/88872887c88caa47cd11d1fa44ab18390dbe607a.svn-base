package banger.dao.impl;

import java.util.List;
import java.util.Map;

import banger.domain.config.IntoTrialRuleDetailQuery;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.ITrialRuleDetailDao;
import banger.domain.config.IntoTrialRuleDetail;

/**
 * 初审规则明细表数据访问类
 */
@Repository
public class TrialRuleDetailDao extends PageSizeDao<IntoTrialRuleDetail> implements ITrialRuleDetailDao {

	/**
	 * 新增初审规则明细表
	 * @param trialRuleDetail 实体对像
	 */
	public void insertTrialRuleDetail(IntoTrialRuleDetail trialRuleDetail){
		trialRuleDetail.setOptionId(this.newId().intValue());
		this.execute("insertTrialRuleDetail",trialRuleDetail);
	}

	/**
	 *修改初审规则明细表
	 * @param trialRuleDetail 实体对像
	 */
	public void updateTrialRuleDetail(IntoTrialRuleDetail trialRuleDetail){
		this.execute("updateTrialRuleDetail",trialRuleDetail);
	}

	/**
	 * 通过主键删除初审规则明细表
	 * @param optionId 主键Id
	 */
	public void deleteTrialRuleDetailById(Integer optionId){
		this.execute("deleteTrialRuleDetailById",optionId);
	}

	/**
	 * 通过主键得到初审规则明细表
	 * @param optionId 主键Id
	 */
	public IntoTrialRuleDetailQuery getTrialRuleDetailById(Integer optionId){
		return (IntoTrialRuleDetailQuery)this.queryEntity("getTrialRuleDetailById",optionId);
	}

	/**
	 * 查询初审规则明细表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<IntoTrialRuleDetailQuery> queryTrialRuleDetailList(Map<String,Object> condition){
		return (List<IntoTrialRuleDetailQuery>)this.queryEntities("queryTrialRuleDetailList", condition);
	}

	/**
	 * 分页查询初审规则明细表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<IntoTrialRuleDetailQuery> queryTrialRuleDetailList(Map<String,Object> condition, IPageSize page){
		return (IPageList<IntoTrialRuleDetailQuery>)this.queryEntities("queryTrialRuleDetailList", page, condition);
	}

	@Override
	public List<IntoTrialRuleDetailQuery> checkTrialRuleDetaiOnly(Map<String, Object> condition) {
		return (List<IntoTrialRuleDetailQuery>)this.queryEntities("checkTrialRuleDetaiOnly", condition);
	}

}
