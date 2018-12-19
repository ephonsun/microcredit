package banger.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.ITrialRuleParamsDao;
import banger.domain.config.IntoTrialRuleParams;

/**
 * 初审规则参数表数据访问类
 */
@Repository
public class TrialRuleParamsDao extends PageSizeDao<IntoTrialRuleParams> implements ITrialRuleParamsDao {

	/**
	 * 新增初审规则参数表
	 * @param trialRuleParams 实体对像
	 */
	public void insertTrialRuleParams(IntoTrialRuleParams trialRuleParams){
		trialRuleParams.setParamId(this.newId().intValue());
		this.execute("insertTrialRuleParams",trialRuleParams);
	}

	/**
	 *修改初审规则参数表
	 * @param trialRuleParams 实体对像
	 */
	public void updateTrialRuleParams(IntoTrialRuleParams trialRuleParams){
		this.execute("updateTrialRuleParams",trialRuleParams);
	}

	/**
	 * 通过主键删除初审规则参数表
	 * @param paramId 主键Id
	 */
	public void deleteTrialRuleParamsById(Integer paramId){
		this.execute("deleteTrialRuleParamsById",paramId);
	}

	/**
	 * 通过主键得到初审规则参数表
	 * @param paramId 主键Id
	 */
	public IntoTrialRuleParams getTrialRuleParamsById(Integer paramId){
		return (IntoTrialRuleParams)this.queryEntity("getTrialRuleParamsById",paramId);
	}

	/**
	 * 查询初审规则参数表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<IntoTrialRuleParams> queryTrialRuleParamsList(Map<String,Object> condition){
		return (List<IntoTrialRuleParams>)this.queryEntities("queryTrialRuleParamsList", condition);
	}

	/**
	 * 分页查询初审规则参数表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<IntoTrialRuleParams> queryTrialRuleParamsList(Map<String,Object> condition,IPageSize page){
		return (IPageList<IntoTrialRuleParams>)this.queryEntities("queryTrialRuleParamsList", page, condition);
	}

}
