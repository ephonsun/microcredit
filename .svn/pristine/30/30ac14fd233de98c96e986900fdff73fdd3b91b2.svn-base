package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.permission.PadClentDevice;

/**
 * 业务访问接口
 */
public interface IClentDeviceService {

	/**
	 * 新增
	 * @param clentDevice 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertClentDevice(PadClentDevice clentDevice, Integer loginUserId);

	/**
	 *修改
	 * @param clentDevice 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateClentDevice(PadClentDevice clentDevice, Integer loginUserId);



	/**
	 * 通过主键得到
	 * @param ID 主键Id
	 */
	PadClentDevice getClentDeviceById(String id);



	/**
	 * 分页查询
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<PadClentDevice> queryClentDeviceList(Map<String, Object> condition, IPageSize page);

}
