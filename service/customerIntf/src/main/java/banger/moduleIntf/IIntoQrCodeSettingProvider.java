package banger.moduleIntf;

import banger.domain.permission.IntoQrcodeSetting;

/**
 * Created by panl on 2017/12/12
 */
public interface IIntoQrCodeSettingProvider {

	/**
	 * 查询进件二维码配置表
	 */
	IntoQrcodeSetting queryQrcodeSetting();

	/**
	 * 通过主键得到进件二维码配置表
	 * @param QR_ID 主键Id
	 */
	IntoQrcodeSetting getQrcodeSettingById(Integer qrId);
}
