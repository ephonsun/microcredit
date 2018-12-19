package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.permission.IntoQrcodeSetting;

/**
 * 进件二维码配置表业务访问接口
 */
public interface IQrcodeSettingService {

	/**
	 * 新增进件二维码配置表
	 * @param qrcodeSetting 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertQrcodeSetting(IntoQrcodeSetting qrcodeSetting, Integer loginUserId);

	/**
	 *修改进件二维码配置表
	 * @param qrcodeSetting 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateQrcodeSetting(IntoQrcodeSetting qrcodeSetting);

	/**
	 * 通过主键删除进件二维码配置表
	 * @param QR_ID 主键Id
	 */
	void deleteQrcodeSettingById(Integer qrId);

	/**
	 * 通过主键得到进件二维码配置表
	 * @param QR_ID 主键Id
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
