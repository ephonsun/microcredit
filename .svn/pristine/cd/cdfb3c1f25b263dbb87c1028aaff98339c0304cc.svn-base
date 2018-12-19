package banger.dao.intf;

import java.util.Map;

import banger.domain.permission.PadClentDevice;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;


public interface IPadClentDeviceDao  {

	
	/**
	 * 通过设备ID获取详细
	 * @param deviceId
	 * @return
	 */
	PadClentDevice getPadClentDeviceInfo(String deviceId);
	
	/**
	 * 新增
	 * @param padClentDevice
	 */
	void innsertPadClentDevice(PadClentDevice padClentDevice);
	
	/**
	 * 更新 
	 * @param padClentDevice
	 */
	void updatePadClentDevice(PadClentDevice padClentDevice);
	
	
	/**
	 * 查询列表
	 * @param page
	 * @param condition
	 * @return
	 */
	IPageList<PadClentDevice> queryPadClentDeviceList(IPageSize page,Map<String,Object> condition);
	
}
