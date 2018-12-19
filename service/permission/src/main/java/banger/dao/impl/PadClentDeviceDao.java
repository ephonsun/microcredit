package banger.dao.impl;

import java.util.Map;

import banger.dao.intf.IPadClentDeviceDao;
import banger.domain.permission.PadClentDevice;
import banger.framework.dao.PageSizeDao;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import org.springframework.stereotype.Repository;

@Repository
public class PadClentDeviceDao extends PageSizeDao<PadClentDevice> implements IPadClentDeviceDao {

	@Override
	public PadClentDevice getPadClentDeviceInfo(String deviceId) {
		return (PadClentDevice)this.queryEntity("getPadClentDeviceInfo",deviceId);
	}

	@Override
	public void innsertPadClentDevice(PadClentDevice padClentDevice) {
		padClentDevice.setId(this.newId().intValue());
		this.execute("innsertPadClentDevice", padClentDevice);
	}

	@Override
	public void updatePadClentDevice(PadClentDevice padClentDevice) {
		this.execute("updatePadClentDevice", padClentDevice);
	}

	@Override
	public IPageList<PadClentDevice> queryPadClentDeviceList(IPageSize page, Map<String, Object> condition) {
		return (IPageList<PadClentDevice>)this.queryEntities("queryPadClentDeviceList",page,condition);
	}

}
