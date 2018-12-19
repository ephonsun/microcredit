package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.permission.IntoQrcodeSave;

/**
 * 进件二维码存储表业务访问接口
 */
public interface IQrcodeSaveService {

	/**
	 * 新增进件二维码存储表
	 * @param qrcodeSave 实体对像
	 */
	void insertQrcodeSave(IntoQrcodeSave qrcodeSave);

	/**
	 *修改进件二维码存储表
	 * @param qrcodeSave 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateQrcodeSave(IntoQrcodeSave qrcodeSave, Integer loginUserId);

	/**
	 * 通过主键删除进件二维码存储表
	 * @param ID 主键Id
	 */
	void deleteQrcodeSave();

	/**
	 * 通过主键得到进件二维码存储表
	 * @param ID 主键Id
	 */
	IntoQrcodeSave getQrcodeSaveById(Integer id);

	/**
	 * 查询进件二维码存储表
	 * @param condition 查询条件
	 * @return
	 */
	List<IntoQrcodeSave> queryQrcodeSaveList(Map<String, Object> condition);

	/**
	 * 分页查询进件二维码存储表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<IntoQrcodeSave> queryQrcodeSaveList(Map<String, Object> condition, IPageSize page);

}
