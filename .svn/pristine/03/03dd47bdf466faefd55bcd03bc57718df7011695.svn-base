package banger.service.intf;

import banger.domain.config.ModeTemplateInfo;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

import java.util.List;
import java.util.Map;

/**
 * 评分模型积分项模板信息表业务访问接口
 */
public interface ITemplateInfoService {

	/**
	 * 新增评分模型积分项模板信息表
	 * @param templateInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertTemplateInfo(ModeTemplateInfo templateInfo, Integer loginUserId);

	/**
	 *修改评分模型积分项模板信息表
	 * @param templateInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateTemplateInfo(ModeTemplateInfo templateInfo, Integer loginUserId);

	/**
	 * 通过主键删除评分模型积分项模板信息表
	 * @param TEMPLATE_ID 主键Id
	 */
	void deleteTemplateInfoById(Integer templateId);

	/**
	 * 通过主键得到评分模型积分项模板信息表
	 * @param TEMPLATE_ID 主键Id
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

    String queryModelTemplateInfoTree(Map<String, Object> condition);

    List<ModeTemplateInfo> queryTemplateInfoListModel(Map<String, Object> condition);
}
