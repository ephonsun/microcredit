package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.config.ModeScoreFieldParams;

/**
 * 评分模型评分项参数表业务访问接口
 */
public interface IScoreFieldParamsService {

	/**
	 * 新增评分模型评分项参数表
	 * @param scoreFieldParams 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertScoreFieldParams(ModeScoreFieldParams scoreFieldParams, Integer loginUserId);

	/**
	 *修改评分模型评分项参数表
	 * @param scoreFieldParams 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateScoreFieldParams(ModeScoreFieldParams scoreFieldParams, Integer loginUserId);

	/**
	 * 通过主键删除评分模型评分项参数表
	 * @param PARAM_ID 主键Id
	 */
	void deleteScoreFieldParamsById(Integer paramId);

	/**
	 * 通过主键得到评分模型评分项参数表
	 * @param PARAM_ID 主键Id
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

	/**
	 * 根据optionId删除参数表数据
	 * @param optionId
	 */
    void deleteScoreFieldParamsByOptionId(Integer optionId);

    void deleteScoreFieldParamsByModeId(Map<String, Object> conditionDelete);
}
