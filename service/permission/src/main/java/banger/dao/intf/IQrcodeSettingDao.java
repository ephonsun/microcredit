package banger.dao.intf;

import banger.domain.permission.IntoQrcodeSetting;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

import java.util.List;
import java.util.Map;

/**
 * 进件二维码配置表数据访问接口
 */
public interface IQrcodeSettingDao {

	/**
	 * 新增进件二维码配置表
	 * @param qrcodeSetting 实体对像
	 */
	void insertQrcodeSetting(IntoQrcodeSetting qrcodeSetting);

	/**
	 *修改进件二维码配置表
	 * @param qrcodeSetting 实体对像
	 */
	void updateQrcodeSetting(IntoQrcodeSetting qrcodeSetting);

	/**
	 * 通过主键删除进件二维码配置表
	 * @param qrId 主键Id
	 */
	void deleteQrcodeSettingById(Integer qrId);

	/**
	 * 通过主键得到进件二维码配置表
	 * @param qrId 主键Id
	 */
	IntoQrcodeSetting getQrcodeSettingById(Integer qrId);

	/**
	 * 查询进件二维码配置表
	 */
	IntoQrcodeSetting queryQrcodeSetting();

	/**
	 * 查询进件二维码配置表
	 * @param condition 查询条件
	 * @return
	 */
	List<IntoQrcodeSetting> queryQrcodeSettingList(Map<String, Object> condition);

	/**
	 * 分页查询进件二维码配置表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<IntoQrcodeSetting> queryQrcodeSettingList(Map<String, Object> condition, IPageSize page);

}
