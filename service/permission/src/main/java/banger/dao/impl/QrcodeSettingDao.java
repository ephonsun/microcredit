package banger.dao.impl;

import banger.dao.intf.IQrcodeSettingDao;
import banger.domain.permission.IntoQrcodeSetting;
import banger.framework.dao.PageSizeDao;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 进件二维码配置表数据访问类
 */
@Repository
public class QrcodeSettingDao extends PageSizeDao<IntoQrcodeSetting> implements IQrcodeSettingDao {

	/**
	 * 新增进件二维码配置表
	 * @param qrcodeSetting 实体对像
	 */
	public void insertQrcodeSetting(IntoQrcodeSetting qrcodeSetting){
		qrcodeSetting.setQrId(this.newId().intValue());
		this.execute("insertQrcodeSetting",qrcodeSetting);
	}

	/**
	 *修改进件二维码配置表
	 * @param qrcodeSetting 实体对像
	 */
	public void updateQrcodeSetting(IntoQrcodeSetting qrcodeSetting){
		this.execute("updateQrcodeSetting",qrcodeSetting);
	}

	/**
	 * 通过主键删除进件二维码配置表
	 * @param qrId 主键Id
	 */
	public void deleteQrcodeSettingById(Integer qrId){
		this.execute("deleteQrcodeSettingById",qrId);
	}

	/**
	 * 通过主键得到进件二维码配置表
	 * @param qrId 主键Id
	 */
	public IntoQrcodeSetting getQrcodeSettingById(Integer qrId){
		return (IntoQrcodeSetting)this.queryEntity("getQrcodeSettingById",qrId);
	}

	@Override
	public IntoQrcodeSetting queryQrcodeSetting() {
		return (IntoQrcodeSetting)this.queryEntity("queryQrcodeSetting");
	}

	/**
	 * 查询进件二维码配置表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<IntoQrcodeSetting> queryQrcodeSettingList(Map<String,Object> condition){
		return (List<IntoQrcodeSetting>)this.queryEntities("queryQrcodeSettingList", condition);
	}

	/**
	 * 分页查询进件二维码配置表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<IntoQrcodeSetting> queryQrcodeSettingList(Map<String,Object> condition,IPageSize page){
		return (IPageList<IntoQrcodeSetting>)this.queryEntities("queryQrcodeSettingList", page, condition);
	}

}
