package banger.dao.impl;

import banger.dao.intf.ITemplateInfoDao;
import banger.domain.config.ModeTemplateInfo;
import banger.framework.dao.PageSizeDao;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 评分模型积分项模板信息表数据访问类
 */
@Repository
public class TemplateInfoDao extends PageSizeDao<ModeTemplateInfo> implements ITemplateInfoDao {

	/**
	 * 新增评分模型积分项模板信息表
	 * @param templateInfo 实体对像
	 */
	public void insertTemplateInfo(ModeTemplateInfo templateInfo){
		templateInfo.setTemplateId(this.newId().intValue());
		this.execute("insertTemplateInfo",templateInfo);
	}

	/**
	 *修改评分模型积分项模板信息表
	 * @param templateInfo 实体对像
	 */
	public void updateTemplateInfo(ModeTemplateInfo templateInfo){
		this.execute("updateTemplateInfo",templateInfo);
	}

	/**
	 * 通过主键删除评分模型积分项模板信息表
	 * @param templateId 主键Id
	 */
	public void deleteTemplateInfoById(Integer templateId){
		this.execute("deleteTemplateInfoById",templateId);
	}

	/**
	 * 通过主键得到评分模型积分项模板信息表
	 * @param templateId 主键Id
	 */
	public ModeTemplateInfo getTemplateInfoById(Integer templateId){
		return (ModeTemplateInfo)this.queryEntity("getTemplateInfoById",templateId);
	}

	/**
	 * 查询评分模型积分项模板信息表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ModeTemplateInfo> queryTemplateInfoList(Map<String,Object> condition){
		return (List<ModeTemplateInfo>)this.queryEntities("queryTemplateInfoList", condition);
	}

	/**
	 * 分页查询评分模型积分项模板信息表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<ModeTemplateInfo> queryTemplateInfoList(Map<String,Object> condition,IPageSize page){
		return (IPageList<ModeTemplateInfo>)this.queryEntities("queryTemplateInfoList", page, condition);
	}

    @Override
    public List<ModeTemplateInfo> queryTemplateInfoListModel(Map<String, Object> condition) {
        return (List<ModeTemplateInfo>) this.queryEntities("queryTemplateInfoListModel",condition);
    }

}
