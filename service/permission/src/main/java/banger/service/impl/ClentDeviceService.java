package banger.service.impl;

import java.util.List;
import java.util.Map;

import banger.dao.intf.IPadClentDeviceDao;
import banger.framework.util.DateUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.service.intf.IClentDeviceService;
import banger.domain.permission.PadClentDevice;

/**
 * 业务访问类
 */
public class ClentDeviceService implements IClentDeviceService {
	private IPadClentDeviceDao padClentDeviceDao;

	public void setPadClentDeviceDao(IPadClentDeviceDao padClentDeviceDao) {
		this.padClentDeviceDao = padClentDeviceDao;
	}

	/**
	 * 新增
	 * @param clentDevice 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertClentDevice(PadClentDevice clentDevice,Integer loginUserId){
		this.padClentDeviceDao.innsertPadClentDevice(clentDevice);
	}

	/**
	 *修改
	 * @param clentDevice 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateClentDevice(PadClentDevice clentDevice,Integer loginUserId){
		this.padClentDeviceDao.updatePadClentDevice(clentDevice);
	}


	/**
	 * 通过主键得到
	 */
	public PadClentDevice getClentDeviceById(String id){
		return this.padClentDeviceDao.getPadClentDeviceInfo(id);
	}



	/**
	 * 分页查询
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<PadClentDevice> queryClentDeviceList(Map<String,Object> condition,IPageSize page){
		return this.padClentDeviceDao.queryPadClentDeviceList(page, condition);
	}

}
