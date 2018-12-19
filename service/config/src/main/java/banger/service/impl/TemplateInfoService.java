package banger.service.impl;

import banger.dao.intf.ITemplateInfoDao;
import banger.domain.config.ModeTemplateInfo;
import banger.domain.system.SysDataDict;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.util.DateUtil;
import banger.service.intf.ITemplateInfoService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 评分模型积分项模板信息表业务访问类
 */
@Repository
public class TemplateInfoService implements ITemplateInfoService {

	@Autowired
	private ITemplateInfoDao templateInfoDao;

	/**
	 * 新增评分模型积分项模板信息表
	 * @param templateInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertTemplateInfo(ModeTemplateInfo templateInfo,Integer loginUserId){
		templateInfo.setCreateUser(loginUserId);
		templateInfo.setCreateDate(DateUtil.getCurrentDate());
		templateInfo.setUpdateUser(loginUserId);
		templateInfo.setUpdateDate(DateUtil.getCurrentDate());
		this.templateInfoDao.insertTemplateInfo(templateInfo);
	}

	/**
	 *修改评分模型积分项模板信息表
	 * @param templateInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateTemplateInfo(ModeTemplateInfo templateInfo,Integer loginUserId){
		templateInfo.setUpdateUser(loginUserId);
		templateInfo.setUpdateDate(DateUtil.getCurrentDate());
		this.templateInfoDao.updateTemplateInfo(templateInfo);
	}

	/**
	 * 通过主键删除评分模型积分项模板信息表
	 * @param TEMPLATE_ID 主键Id
	 */
	public void deleteTemplateInfoById(Integer templateId){
		this.templateInfoDao.deleteTemplateInfoById(templateId);
	}

	/**
	 * 通过主键得到评分模型积分项模板信息表
	 * @param TEMPLATE_ID 主键Id
	 */
	public ModeTemplateInfo getTemplateInfoById(Integer templateId){
		return this.templateInfoDao.getTemplateInfoById(templateId);
	}

	/**
	 * 查询评分模型积分项模板信息表
	 * @param condition 查询条件
	 * @return
	 */
	public List<ModeTemplateInfo> queryTemplateInfoList(Map<String,Object> condition){
		return this.templateInfoDao.queryTemplateInfoList(condition);
	}

	/**
	 * 分页查询评分模型积分项模板信息表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<ModeTemplateInfo> queryTemplateInfoList(Map<String,Object> condition,IPageSize page){
		return this.templateInfoDao.queryTemplateInfoList(condition,page);
	}

    @Override
    public String queryModelTemplateInfoTree(Map<String, Object> condition) {
		List<ModeTemplateInfo> templateInfos = this.templateInfoDao.queryTemplateInfoListModel(condition);
		if (templateInfos != null && templateInfos.size() > 0) {
			JSONArray ja = new JSONArray();
			JSONObject tempObj = new JSONObject();
			tempObj.put("pId", "0");
			tempObj.put("icon", "../core/imgs/icon/agency.gif");
			for (ModeTemplateInfo modeTemplateInfo : templateInfos) {
				tempObj.put("id", modeTemplateInfo.getTemplateId());
				tempObj.put("name", modeTemplateInfo.getTableName());
				ja.add(tempObj);
			}
			return ja.toString();
		}
		return null;
    }

    @Override
    public List<ModeTemplateInfo> queryTemplateInfoListModel(Map<String, Object> condition) {
        return this.templateInfoDao.queryTemplateInfoListModel(condition);
    }

}
