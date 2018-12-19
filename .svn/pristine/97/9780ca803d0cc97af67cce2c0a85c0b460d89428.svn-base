package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.config.IntoTemplatesInfo;

/**
 * 进件管理模板信息表业务访问接口
 */
public interface ITemplatesInfoService {

	/**
	 * 新增进件管理模板信息表
	 * @param templatesInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertTemplatesInfo(IntoTemplatesInfo templatesInfo, Integer loginUserId);

	/**
	 *修改进件管理模板信息表
	 * @param templatesInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateTemplatesInfo(IntoTemplatesInfo templatesInfo, Integer loginUserId);

	/**
	 * 通过主键删除进件管理模板信息表
	 * @param TEMPLATE_ID 主键Id
	 */
	void deleteTemplatesInfoById(Integer templateId);

	/**
	 * 通过主键得到进件管理模板信息表
	 * @param TEMPLATE_ID 主键Id
	 */
	IntoTemplatesInfo getTemplatesInfoById(Integer templateId);

	/**
	 * 查询进件管理模板信息表
	 * @param condition 查询条件
	 * @return
	 */
	List<IntoTemplatesInfo> queryTemplatesInfoList(Map<String, Object> condition);

	/**
	 * 分页查询进件管理模板信息表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<IntoTemplatesInfo> queryTemplatesInfoList(Map<String, Object> condition, IPageSize page);

	List<IntoTemplatesInfo> queryTemplatesInfoListModel(Map<String, Object> condition);

	String queryIntoTemplatesInfoTree(Map<String, Object> condition);
}
