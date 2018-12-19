package banger.dao.intf;

import banger.domain.config.ModeTemplateInfo;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

import java.util.List;
import java.util.Map;

/**
 * 评分模型积分项模板信息表数据访问接口
 */
public interface ITemplateInfoDao {

	/**
	 * 新增评分模型积分项模板信息表
	 * @param templateInfo 实体对像
	 */
	void insertTemplateInfo(ModeTemplateInfo templateInfo);

	/**
	 *修改评分模型积分项模板信息表
	 * @param templateInfo 实体对像
	 */
	void updateTemplateInfo(ModeTemplateInfo templateInfo);

	/**
	 * 通过主键删除评分模型积分项模板信息表
	 * @param templateId 主键Id
	 */
	void deleteTemplateInfoById(Integer templateId);

	/**
	 * 通过主键得到评分模型积分项模板信息表
	 * @param templateId 主键Id
	 */
	ModeTemplateInfo getTemplateInfoById(Integer templateId);

	/**
	 * 查询评分模型积分项模板信息表
	 * @param condition 查询条件
	 * @return
	 */
	List<ModeTemplateInfo> queryTemplateInfoList(Map<String, Object> condition);

	/**
	 * 分页查询评分模型积分项模板信息表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<ModeTemplateInfo> queryTemplateInfoList(Map<String, Object> condition, IPageSize page);

	List<ModeTemplateInfo> queryTemplateInfoListModel(Map<String, Object> condition);
}
