package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.config.ModeScoreInfo;

/**
 * 评分模型信息表数据访问接口
 */
public interface IScoreInfoDao {

	/**
	 * 新增评分模型信息表
	 * @param scoreInfo 实体对像
	 */
	void insertScoreInfo(ModeScoreInfo scoreInfo);

	/**
	 *修改评分模型信息表
	 * @param scoreInfo 实体对像
	 */
	void updateScoreInfo(ModeScoreInfo scoreInfo);

	/**
	 * 通过主键删除评分模型信息表
	 * @param modeId 主键Id
	 */
	void deleteScoreInfoById(Integer modeId);

	/**
	 * 通过主键得到评分模型信息表
	 * @param modeId 主键Id
	 */
	ModeScoreInfo getScoreInfoById(Integer modeId);

	/**
	 * 查询评分模型信息表
	 * @param condition 查询条件
	 * @return
	 */
	List<ModeScoreInfo> queryScoreInfoList(Map<String, Object> condition);

	/**
	 * 分页查询评分模型信息表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<ModeScoreInfo> queryScoreInfoList(Map<String, Object> condition, IPageSize page);

    List<ModeScoreInfo> queryModelScoreInfoCopy(Map<String, Object> condition);

    List<ModeScoreInfo> queryModeScoreInfoes();
}
