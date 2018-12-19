package banger.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.util.DateUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.ITrialRuleParamsDao;
import banger.service.intf.ITrialRuleParamsService;
import banger.domain.config.IntoTrialRuleParams;

/**
 * 初审规则参数表业务访问类
 */
@Repository
public class TrialRuleParamsService implements ITrialRuleParamsService {

	@Autowired
	private ITrialRuleParamsDao trialRuleParamsDao;

	/**
	 * 新增初审规则参数表
	 * @param trialRuleParams 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertTrialRuleParams(IntoTrialRuleParams trialRuleParams,Integer loginUserId){
		this.trialRuleParamsDao.insertTrialRuleParams(trialRuleParams);
	}

	/**
	 *修改初审规则参数表
	 * @param trialRuleParams 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateTrialRuleParams(IntoTrialRuleParams trialRuleParams,Integer loginUserId){
		this.trialRuleParamsDao.updateTrialRuleParams(trialRuleParams);
	}

	/**
	 * 通过主键删除初审规则参数表
	 * @param PARAM_ID 主键Id
	 */
	public void deleteTrialRuleParamsById(Integer paramId){
		this.trialRuleParamsDao.deleteTrialRuleParamsById(paramId);
	}

	/**
	 * 通过主键得到初审规则参数表
	 * @param PARAM_ID 主键Id
	 */
	public IntoTrialRuleParams getTrialRuleParamsById(Integer paramId){
		return this.trialRuleParamsDao.getTrialRuleParamsById(paramId);
	}

	/**
	 * 查询初审规则参数表
	 * @param condition 查询条件
	 * @return
	 */
	public List<IntoTrialRuleParams> queryTrialRuleParamsList(Map<String,Object> condition){
		return this.trialRuleParamsDao.queryTrialRuleParamsList(condition);
	}

	/**
	 * 分页查询初审规则参数表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<IntoTrialRuleParams> queryTrialRuleParamsList(Map<String,Object> condition,IPageSize page){
		return this.trialRuleParamsDao.queryTrialRuleParamsList(condition,page);
	}

}
