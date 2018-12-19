package banger.moduleIntf;


import java.util.List;

import banger.domain.system.SysBasicConfig;
import banger.domain.system.SysDataDictCol;
import banger.domain.system.SysMobileInfo;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import net.sf.json.JSONObject;

public interface ISystemModule {

	void addSysSocketLog(Integer loanId, String socketCode, String sendXml, String returnMassage, String remark,String codeNum,String codeNumTwo);

	boolean isSocketSuccess(Integer loanId, String socketCode,String codeNum,String codeNumTwo);

	/**
	 * 系统模块对外接口，新增日志记录
	 * @param module 模块名称
	 * @param action 操作对象名称
	 * @param userId 操作者userId
	 * @param ip     操作者Ip
	 */
	public void addSysOpeventLog(String module,String action,Integer userId,String ip);

	/**
	 * 系统模块对外接口，获取基础数据、功能开关、接口开关
	 * @param basicConfigKey
	 * @return
	 */
	 SysBasicConfig getSysBasicConfig(String basicConfigKey);

	void saveSysMobileInfo(String serialNo, String brand, String model, String sysVersion, String appVersion, Integer userId,String loginLongitude,String loginLatitude,String loginIp);

	public IPageList<SysMobileInfo> queryMobileList(String serialNo, String brand, String model, String sysVersion, String appVersion, String userName, IPageSize page);
	
	public List<SysDataDictCol> queryDataDictColListByName(String name);
	
	IDataDictProvider getDataDictProvider();

	/**
	 * 得到产品类型
	 * @return
	 */
	JSONObject queryProductType();

	/**
	 * 通过主键得到
	 * @param serialNo 主键Id
	 */
	SysMobileInfo getMobileInfoById(String serialNo);
}
