package banger.service.impl;

import java.util.List;
import java.util.Map;

import banger.domain.config.ModeTemplateInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.util.DateUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.ITemplatesInfoDao;
import banger.service.intf.ITemplatesInfoService;
import banger.domain.config.IntoTemplatesInfo;

/**
 * 进件管理模板信息表业务访问类
 */
@Repository
public class TemplatesInfoService implements ITemplatesInfoService {

	@Autowired
	private ITemplatesInfoDao templatesInfoDao;

	/**
	 * 新增进件管理模板信息表
	 * @param templatesInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertTemplatesInfo(IntoTemplatesInfo templatesInfo,Integer loginUserId){
		templatesInfo.setCreateUser(loginUserId);
		templatesInfo.setCreateDate(DateUtil.getCurrentDate());
		templatesInfo.setUpdateUser(loginUserId);
		templatesInfo.setUpdateDate(DateUtil.getCurrentDate());
		this.templatesInfoDao.insertTemplatesInfo(templatesInfo);
	}

	/**
	 *修改进件管理模板信息表
	 * @param templatesInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateTemplatesInfo(IntoTemplatesInfo templatesInfo,Integer loginUserId){
		templatesInfo.setUpdateUser(loginUserId);
		templatesInfo.setUpdateDate(DateUtil.getCurrentDate());
		this.templatesInfoDao.updateTemplatesInfo(templatesInfo);
	}

	/**
	 * 通过主键删除进件管理模板信息表
	 * @param TEMPLATE_ID 主键Id
	 */
	public void deleteTemplatesInfoById(Integer templateId){
		this.templatesInfoDao.deleteTemplatesInfoById(templateId);
	}

	/**
	 * 通过主键得到进件管理模板信息表
	 * @param TEMPLATE_ID 主键Id
	 */
	public IntoTemplatesInfo getTemplatesInfoById(Integer templateId){
		return this.templatesInfoDao.getTemplatesInfoById(templateId);
	}

	/**
	 * 查询进件管理模板信息表
	 * @param condition 查询条件
	 * @return
	 */
	public List<IntoTemplatesInfo> queryTemplatesInfoList(Map<String,Object> condition){
		return this.templatesInfoDao.queryTemplatesInfoList(condition);
	}

	/**
	 * 分页查询进件管理模板信息表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<IntoTemplatesInfo> queryTemplatesInfoList(Map<String,Object> condition,IPageSize page){
		return this.templatesInfoDao.queryTemplatesInfoList(condition,page);
	}


	public List<IntoTemplatesInfo> queryTemplatesInfoListModel(Map<String, Object> condition) {
		return this.templatesInfoDao.queryTemplatesInfoListModel(condition);
	}


	public String queryIntoTemplatesInfoTree(Map<String, Object> condition) {
		List<IntoTemplatesInfo> templatesInfos = this.templatesInfoDao.queryTemplatesInfoListModel(condition);
		if (templatesInfos != null && templatesInfos.size() > 0) {
			JSONArray ja = new JSONArray();
			JSONObject tempObj = new JSONObject();
			tempObj.put("pId", "0");
			tempObj.put("icon", "../core/imgs/icon/agency.gif");
			for (IntoTemplatesInfo intoTemplatesInfo : templatesInfos) {
				tempObj.put("id", intoTemplatesInfo.getTemplateId());
				tempObj.put("name", intoTemplatesInfo.getTableName());
				ja.add(tempObj);
			}
			return ja.toString();
		}
		return null;
	}
}
