package banger.service.impl;

import java.util.List;
import java.util.Map;

import banger.dao.intf.IFaceSettingDao;
import banger.service.intf.IFaceSettingService;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.util.DateUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.face.FaceSetting;

/**
 * 旷世人脸比对配置业务访问类
 */
@Repository
public class FaceSettingService implements IFaceSettingService {

	@Autowired
	private IFaceSettingDao settingDao;

	/**
	 * 新增旷世人脸比对配置
	 * @param setting 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertSetting(FaceSetting setting,Integer loginUserId){
		this.settingDao.insertSetting(setting);
	}

	/**
	 *修改旷世人脸比对配置
	 * @param setting 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateSetting(FaceSetting setting,Integer loginUserId){
		this.settingDao.updateSetting(setting);
	}

	/**
	 * 通过主键删除旷世人脸比对配置
	 * @param faceId 主键Id
	 */
	public void deleteSettingById(Integer faceId){
		this.settingDao.deleteSettingById(faceId);
	}

	/**
	 * 通过主键得到旷世人脸比对配置
	 * @param faceId 主键Id
	 */
	public FaceSetting getSettingById(Integer faceId){
		return this.settingDao.getSettingById(faceId);
	}

	/**
	 * 查询旷世人脸比对配置
	 * @param condition 查询条件
	 * @return
	 */
	public List<FaceSetting> querySettingList(Map<String,Object> condition){
		return this.settingDao.querySettingList(condition);
	}

	/**
	 * 分页查询旷世人脸比对配置
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<FaceSetting> querySettingList(Map<String,Object> condition,IPageSize page){
		return this.settingDao.querySettingList(condition,page);
	}

}
