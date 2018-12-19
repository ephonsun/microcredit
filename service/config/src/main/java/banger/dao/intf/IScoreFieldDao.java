package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.domain.config.ModeScoreFieldExtend;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.config.ModeScoreField;

/**
 * 评分模型评分项明细表数据访问接口
 */
public interface IScoreFieldDao {

	/**
	 * 新增评分模型评分项明细表
	 * @param scoreField 实体对像
	 */
	void insertScoreField(ModeScoreField scoreField);

	/**
	 *修改评分模型评分项明细表
	 * @param scoreField 实体对像
	 */
	void updateScoreField(ModeScoreField scoreField);

	/**
	 * 通过主键删除评分模型评分项明细表
	 * @param optionId 主键Id
	 */
	void deleteScoreFieldById(Integer optionId);

	/**
	 * 通过主键得到评分模型评分项明细表
	 * @param optionId 主键Id
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

    Integer insertScoreFieldReturnId(ModeScoreField scoreField);
}
