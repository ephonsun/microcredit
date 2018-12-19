package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.domain.config.ModeScoreFieldExtend;
import banger.domain.config.ModeScoreFieldParams;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.config.ModeScoreField;

/**
 * 评分模型评分项明细表业务访问接口
 */
public interface IScoreFieldService {

	/**
	 * 新增评分模型评分项明细表
	 * @param scoreField 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertScoreField(ModeScoreField scoreField, Integer loginUserId);

	/**
	 *修改评分模型评分项明细表
	 * @param scoreField 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateScoreField(ModeScoreField scoreField, Integer loginUserId);

	/**
	 * 通过主键删除评分模型评分项明细表
	 * @param OPTION_ID 主键Id
	 */
	void deleteScoreFieldById(Integer optionId);

	/**
	 * 通过主键得到评分模型评分项明细表
	 * @param OPTION_ID 主键Id
	 */
	ModeScoreField getScoreFieldById(Integer optionId);

	/**
	 * 查询评分模型评分项明细表
	 * @param condition 查询条件
	 * @return
	 */
	List<ModeScoreField> queryScoreFieldList(Map<String, Object> condition);

	/**
	 * 分页查询评分模型评分项明细表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<ModeScoreField> queryScoreFieldList(Map<String, Object> condition, IPageSize page);

    List<ModeScoreFieldExtend> queryModelScoreInfoFieldList(Map<String, Object> condition);

	void deleteScoreFieldByModeId(Map<String, Object> condition);

	/**
	 * @param modeId 当前模型
	 * @param copyModeId 要复制的模型
	 * @param modeScoreFields 要复制的模型的评分项
	 * @param modeScoreFieldParams 要复制的模型的评分项参数
	 * @param loginUserId
	 */
    void copyModel(Integer modeId, Integer copyModeId, List<ModeScoreField> modeScoreFields, List<ModeScoreFieldParams> modeScoreFieldParams, Integer loginUserId);

	void saveModelScoreField(ModeScoreField modeScoreField);
}
