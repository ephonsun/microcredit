package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.config.ModeScoreFieldParams;

/**
 * 评分模型评分项参数表数据访问接口
 */
public interface IScoreFieldParamsDao {

	/**
	 * 新增评分模型评分项参数表
	 * @param scoreFieldParams 实体对像
	 */
	void insertScoreFieldParams(ModeScoreFieldParams scoreFieldParams);

	/**
	 *修改评分模型评分项参数表
	 * @param scoreFieldParams 实体对像
	 */
	void updateScoreFieldParams(ModeScoreFieldParams scoreFieldParams);

	/**
	 * 通过主键删除评分模型评分项参数表
	 * @param paramId 主键Id
	 */
	void deleteScoreFieldParamsById(Integer paramId);

	/**
	 * 通过主键得到评分模型评分项参数表
	 * @param paramId 主键Id
	 */
	ModeScoreFieldParams getScoreFieldParamsById(Integer paramId);

	/**
	 * 查询评分模型评分项参数表
	 * @param condition 查询条件
	 * @return
	 */
	List<ModeScoreFieldParams> queryScoreFieldParamsList(Map<String, Object> condition);

	/**
	 * 分页查询评分模型评分项参数表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<ModeScoreFieldParams> queryScoreFieldParamsList(Map<String, Object> condition, IPageSize page);

    void deleteScoreFieldParamsByOptionId(Integer optionId);

    void deleteScoreFieldParamsByModeId(Map<String, Object> conditionDelete);
}
