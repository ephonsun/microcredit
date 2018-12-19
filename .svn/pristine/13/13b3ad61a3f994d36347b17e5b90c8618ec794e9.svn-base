package banger.service.impl;

import banger.dao.intf.IQrcodeSaveDao;
import banger.domain.permission.IntoQrcodeSave;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.moduleIntf.IIntoQrCodeProvider;
import banger.service.intf.IQrcodeSaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 进件二维码存储表业务访问类
 */
@Repository
public class QrcodeSaveService implements IQrcodeSaveService ,IIntoQrCodeProvider {


	@Autowired
	private IQrcodeSaveDao qrcodeSaveDao;

	@Override
	public IntoQrcodeSave queryQrcode(String userAccount) {
		return this.qrcodeSaveDao.queryQrcode(userAccount);
	}

	/**
	 * 新增进件二维码存储表
	 * @param qrcodeSave 实体对像
	 */
	public void insertQrcodeSave(IntoQrcodeSave qrcodeSave){
		this.qrcodeSaveDao.insertQrcodeSave(qrcodeSave);
	}

	/**
	 *修改进件二维码存储表
	 * @param qrcodeSave 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateQrcodeSave(IntoQrcodeSave qrcodeSave,Integer loginUserId){
		this.qrcodeSaveDao.updateQrcodeSave(qrcodeSave);
	}

	/**
	 * 通过主键删除进件二维码存储表
	 */
	public void deleteQrcodeSave(){
		this.qrcodeSaveDao.deleteQrcodeSave();
	}

	/**
	 * 通过主键得到进件二维码存储表
	 */
	public IntoQrcodeSave getQrcodeSaveById(Integer id){
		return this.qrcodeSaveDao.getQrcodeSaveById(id);
	}

	/**
	 * 查询进件二维码存储表
	 * @param condition 查询条件
	 * @return
	 */
	public List<IntoQrcodeSave> queryQrcodeSaveList(Map<String,Object> condition){
		return this.qrcodeSaveDao.queryQrcodeSaveList(condition);
	}

	/**
	 * 分页查询进件二维码存储表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<IntoQrcodeSave> queryQrcodeSaveList(Map<String,Object> condition,IPageSize page){
		return this.qrcodeSaveDao.queryQrcodeSaveList(condition,page);
	}

}
