package banger.dao.impl;

import java.util.List;
import java.util.Map;

import banger.domain.config.ModeTemplateInfo;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.ITemplatesInfoDao;
import banger.domain.config.IntoTemplatesInfo;

/**
 * 进件管理模板信息表数据访问类
 */
@Repository
public class TemplatesInfoDao extends PageSizeDao<IntoTemplatesInfo> implements ITemplatesInfoDao {

	/**
	 * 新增进件管理模板信息表
	 * @param templatesInfo 实体对像
	 */
	public void insertTemplatesInfo(IntoTemplatesInfo templatesInfo){
		templatesInfo.setTemplateId(this.newId().intValue());
		this.execute("insertTemplatesInfo",templatesInfo);
	}

	/**
	 *修改进件管理模板信息表
	 * @param templatesInfo 实体对像
	 */
	public void updateTemplatesInfo(IntoTemplatesInfo templatesInfo){
		this.execute("updateTemplatesInfo",templatesInfo);
	}

	/**
	 * 通过主键删除进件管理模板信息表
	 * @param templateId 主键Id
	 */
	public void deleteTemplatesInfoById(Integer templateId){
		this.execute("deleteTemplatesInfoById",templateId);
	}

	/**
	 * 通过主键得到进件管理模板信息表
	 * @param templateId 主键Id
	 */
	public IntoTemplatesInfo getTemplatesInfoById(Integer templateId){
		return (IntoTemplatesInfo)this.queryEntity("getTemplatesInfoById",templateId);
	}

	/**
	 * 查询进件管理模板信息表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<IntoTemplatesInfo> queryTemplatesInfoList(Map<String,Object> condition){
		return (List<IntoTemplatesInfo>)this.queryEntities("queryTemplatesInfoList", condition);
	}

	/**
	 * 分页查询进件管理模板信息表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<IntoTemplatesInfo> queryTemplatesInfoList(Map<String,Object> condition,IPageSize page){
		return (IPageList<IntoTemplatesInfo>)this.queryEntities("queryTemplatesInfoList", page, condition);
	}


	public List<IntoTemplatesInfo> queryTemplatesInfoListModel(Map<String, Object> condition) {
		return (List<IntoTemplatesInfo>) this.queryEntities("queryTemplatesInfoListModel",condition);
	}
}
