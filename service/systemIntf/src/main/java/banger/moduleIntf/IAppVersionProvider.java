package banger.moduleIntf;

import banger.domain.system.SysAppVersion;

/**
 * @Author: yangdw
 * @Description:安卓版本升级对外接口
 * @Date: Created in 16:42 2017/09/30.
 */
public interface IAppVersionProvider {
	/**
	 * @Author: yangdw
	 * @param
	 * @Description:获取最新的版本
	 * @Date: 15:04 2017/9/30
	 */
	SysAppVersion getLastOneVersionUpd();
}

