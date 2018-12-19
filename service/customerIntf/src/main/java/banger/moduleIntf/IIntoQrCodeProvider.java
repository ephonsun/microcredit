package banger.moduleIntf;

import banger.domain.permission.IntoQrcodeSave;

import java.util.List;
import java.util.Map;

/**
 * Created by panl on 2017/12/11
 */
public interface IIntoQrCodeProvider {

	/**
	 * 查询进件二维码存储表
	 * @param condition 查询条件
	 * @return
	 */
	List<IntoQrcodeSave> queryQrcodeSaveList(Map<String, Object> condition);

	IntoQrcodeSave queryQrcode(String userAccount);

	/**
	 * 新增进件二维码存储表
	 * @param qrcodeSave 实体对像
	 */
	void insertQrcodeSave(IntoQrcodeSave qrcodeSave);

	/**
	 * 通过主键得到进件二维码存储表
	 * @param ID 主键Id
	 */
	IntoQrcodeSave getQrcodeSaveById(Integer id);

}
