package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.face.FaceSetting;

/**
 * 旷世人脸比对配置数据访问接口
 */
public interface IFaceSettingDao {

	/**
	 * 新增旷世人脸比对配置
	 * @param setting 实体对像
	 */
	void insertSetting(FaceSetting setting);

	/**
	 *修改旷世人脸比对配置
	 * @param setting 实体对像
	 */
	void updateSetting(FaceSetting setting);

	/**
	 * 通过主键删除旷世人脸比对配置
	 * @param faceId 主键Id
	 */
	void deleteSettingById(Integer faceId);

	/**
	 * 通过主键得到旷世人脸比对配置
	 * @param faceId 主键Id
	 */
	FaceSetting getSettingById(Integer faceId);

	/**
	 * 查询旷世人脸比对配置
	 * @param condition 查询条件
	 * @return
	 */
	List<FaceSetting> querySettingList(Map<String,Object> condition);

	/**
	 * 分页查询旷世人脸比对配置
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<FaceSetting> querySettingList(Map<String,Object> condition,IPageSize page);

}
