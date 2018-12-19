package banger.service.impl;

import java.util.List;
import java.util.Map;

import banger.moduleIntf.IIntoQrCodeSettingProvider;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.util.DateUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.IQrcodeSettingDao;
import banger.service.intf.IQrcodeSettingService;
import banger.domain.permission.IntoQrcodeSetting;

/**
 * 进件二维码配置表业务访问类
 */
@Repository
public class QrcodeSettingService implements IQrcodeSettingService ,IIntoQrCodeSettingProvider{

	@Autowired
	private IQrcodeSettingDao qrcodeSettingDao;

	/**
	 * 新增进件二维码配置表
	 * @param qrcodeSetting 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertQrcodeSetting(IntoQrcodeSetting qrcodeSetting,Integer loginUserId){
		this.qrcodeSettingDao.insertQrcodeSetting(qrcodeSetting);
	}

	/**
	 *修改进件二维码配置表
	 * @param qrcodeSetting 实体对像
	 */
	public void updateQrcodeSetting(IntoQrcodeSetting qrcodeSetting){
		this.qrcodeSettingDao.updateQrcodeSetting(qrcodeSetting);
	}

	/**
	 * 通过主键删除进件二维码配置表
	 * @param QR_ID 主键Id
	 */
	public void deleteQrcodeSettingById(Integer qrId){
		this.qrcodeSettingDao.deleteQrcodeSettingById(qrId);
	}

	/**
	 * 通过主键得到进件二维码配置表
	 * @param QR_ID 主键Id
	 */
	public IntoQrcodeSetting getQrcodeSettingById(Integer qrId){
		return this.qrcodeSettingDao.getQrcodeSettingById(qrId);
	}

	@Override
	public IntoQrcodeSetting queryQrcodeSetting() {
		return this.qrcodeSettingDao.queryQrcodeSetting();
	}

	/**
	 * 查询进件二维码配置表
	 * @param condition 查询条件
	 * @return
	 */
	public List<IntoQrcodeSetting> queryQrcodeSettingList(Map<String,Object> condition){
		return this.qrcodeSettingDao.queryQrcodeSettingList(condition);
	}

	/**
	 * 分页查询进件二维码配置表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<IntoQrcodeSetting> queryQrcodeSettingList(Map<String,Object> condition,IPageSize page){
		return this.qrcodeSettingDao.queryQrcodeSettingList(condition,page);
	}

}
