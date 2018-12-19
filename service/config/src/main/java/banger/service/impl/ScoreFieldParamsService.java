package banger.service.impl;

import java.util.List;
import java.util.Map;

import banger.moduleIntf.IScoreFieldParamsProvider;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.util.DateUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.IScoreFieldParamsDao;
import banger.service.intf.IScoreFieldParamsService;
import banger.domain.config.ModeScoreFieldParams;

/**
 * 评分模型评分项参数表业务访问类
 */
@Repository
public class ScoreFieldParamsService implements IScoreFieldParamsService,IScoreFieldParamsProvider {

	@Autowired
	private IScoreFieldParamsDao scoreFieldParamsDao;

	/**
	 * 新增评分模型评分项参数表
	 * @param scoreFieldParams 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertScoreFieldParams(ModeScoreFieldParams scoreFieldParams,Integer loginUserId){
		this.scoreFieldParamsDao.insertScoreFieldParams(scoreFieldParams);
	}

	/**
	 *修改评分模型评分项参数表
	 * @param scoreFieldParams 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateScoreFieldParams(ModeScoreFieldParams scoreFieldParams,Integer loginUserId){
		this.scoreFieldParamsDao.updateScoreFieldParams(scoreFieldParams);
	}

	/**
	 * 通过主键删除评分模型评分项参数表
	 * @param PARAM_ID 主键Id
	 */
	public void deleteScoreFieldParamsById(Integer paramId){
		this.scoreFieldParamsDao.deleteScoreFieldParamsById(paramId);
	}

	/**
	 * 通过主键得到评分模型评分项参数表
	 * @param PARAM_ID 主键Id
	 */
	public ModeScoreFieldParams getScoreFieldParamsById(Integer paramId){
		return this.scoreFieldParamsDao.getScoreFieldParamsById(paramId);
	}

	/**
	 * 查询评分模型评分项参数表
	 * @param condition 查询条件
	 * @return
	 */
	public List<ModeScoreFieldParams> queryScoreFieldParamsList(Map<String,Object> condition){
		return this.scoreFieldParamsDao.queryScoreFieldParamsList(condition);
	}

	/**
	 * 分页查询评分模型评分项参数表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<ModeScoreFieldParams> queryScoreFieldParamsList(Map<String,Object> condition,IPageSize page){
		return this.scoreFieldParamsDao.queryScoreFieldParamsList(condition,page);
	}

    @Override
    public void deleteScoreFieldParamsByOptionId(Integer optionId) {
        this.scoreFieldParamsDao.deleteScoreFieldParamsByOptionId(optionId);
    }

    @Override
    public void deleteScoreFieldParamsByModeId(Map<String, Object> conditionDelete) {
        this.scoreFieldParamsDao.deleteScoreFieldParamsByModeId(conditionDelete);
    }

}
