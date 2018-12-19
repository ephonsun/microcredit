package banger.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.IScoreFieldParamsDao;
import banger.domain.config.ModeScoreFieldParams;

/**
 * 评分模型评分项参数表数据访问类
 */
@Repository
public class ScoreFieldParamsDao extends PageSizeDao<ModeScoreFieldParams> implements IScoreFieldParamsDao {

	/**
	 * 新增评分模型评分项参数表
	 * @param scoreFieldParams 实体对像
	 */
	public void insertScoreFieldParams(ModeScoreFieldParams scoreFieldParams){
		scoreFieldParams.setParamId(this.newId().intValue());
		this.execute("insertScoreFieldParams",scoreFieldParams);
	}

	/**
	 *修改评分模型评分项参数表
	 * @param scoreFieldParams 实体对像
	 */
	public void updateScoreFieldParams(ModeScoreFieldParams scoreFieldParams){
		this.execute("updateScoreFieldParams",scoreFieldParams);
	}

	/**
	 * 通过主键删除评分模型评分项参数表
	 * @param paramId 主键Id
	 */
	public void deleteScoreFieldParamsById(Integer paramId){
		this.execute("deleteScoreFieldParamsById",paramId);
	}

	/**
	 * 通过主键得到评分模型评分项参数表
	 * @param paramId 主键Id
	 */
	public ModeScoreFieldParams getScoreFieldParamsById(Integer paramId){
		return (ModeScoreFieldParams)this.queryEntity("getScoreFieldParamsById",paramId);
	}

	/**
	 * 查询评分模型评分项参数表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ModeScoreFieldParams> queryScoreFieldParamsList(Map<String,Object> condition){
		return (List<ModeScoreFieldParams>)this.queryEntities("queryScoreFieldParamsList", condition);
	}

	/**
	 * 分页查询评分模型评分项参数表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<ModeScoreFieldParams> queryScoreFieldParamsList(Map<String,Object> condition,IPageSize page){
		return (IPageList<ModeScoreFieldParams>)this.queryEntities("queryScoreFieldParamsList", page, condition);
	}

    @Override
    public void deleteScoreFieldParamsByOptionId(Integer optionId) {
		Map<String,Object> condition =new HashMap<String,Object>();
		condition.put("optionId",optionId);
        this.execute("deleteScoreFieldParamsByOptionId",condition);
    }

    @Override
    public void deleteScoreFieldParamsByModeId(Map<String, Object> conditionDelete) {
        this.execute("deleteScoreFieldParamsByModeId",conditionDelete);
    }

}
