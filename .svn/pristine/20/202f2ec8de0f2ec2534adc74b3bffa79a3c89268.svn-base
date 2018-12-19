package banger.service.intf;

import banger.domain.permission.ILoginInfo;
import banger.domain.permission.PadClentDevice;
import banger.domain.permission.PmsUser;

public interface IUserLoginService {

	/**
	 * 登录
	 * @param account
	 * @param password
	 * @return
	 */
	String login(String account,String password);


	/**
	 * 退出
	 * @param loginInfo
	 * @return
	 */
	String logout(ILoginInfo loginInfo);

	void removeSession();

	/**
	 * 根据账号获取登录用户信息
	 * @param account
	 * @return
	 */
	PmsUser getUserByAccount(String account);
	
	
	
	
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
	
	
	
}
