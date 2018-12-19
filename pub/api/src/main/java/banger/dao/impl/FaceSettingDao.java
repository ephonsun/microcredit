package banger.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.IFaceSettingDao;
import banger.domain.face.FaceSetting;

/**
 * 旷世人脸比对配置数据访问类
 */
@Repository
public class FaceSettingDao extends PageSizeDao<FaceSetting> implements IFaceSettingDao {

	/**
	 * 新增旷世人脸比对配置
	 * @param setting 实体对像
	 */
	public void insertSetting(FaceSetting setting){
		setting.setFaceId(this.newId().intValue());
		this.execute("insertSetting",setting);
	}

	/**
	 *修改旷世人脸比对配置
	 * @param setting 实体对像
	 */
	public void updateSetting(FaceSetting setting){
		this.execute("updateSetting",setting);
	}

	/**
	 * 通过主键删除旷世人脸比对配置
	 * @param faceId 主键Id
	 */
	public void deleteSettingById(Integer faceId){
		this.execute("deleteSettingById",faceId);
	}

	/**
	 * 通过主键得到旷世人脸比对配置
	 * @param faceId 主键Id
	 */
	public FaceSetting getSettingById(Integer faceId){
		return (FaceSetting)this.queryEntity("getSettingById",faceId);
	}

	/**
	 * 查询旷世人脸比对配置
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<FaceSetting> querySettingList(Map<String,Object> condition){
		return (List<FaceSetting>)this.queryEntities("querySettingList", condition);
	}

	/**
	 * 分页查询旷世人脸比对配置
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<FaceSetting> querySettingList(Map<String,Object> condition,IPageSize page){
		return (IPageList<FaceSetting>)this.queryEntities("querySettingList", page, condition);
	}

}
