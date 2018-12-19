package banger.moduleImpl;


import banger.dao.intf.IDataDictColDao;
import banger.dao.intf.IMobileInfoDao;
import banger.domain.system.*;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.moduleIntf.IDataDictProvider;
import banger.moduleIntf.ISystemModule;
import banger.service.intf.IBasicConfigService;
import banger.service.intf.IMobileRecordService;
import banger.service.intf.ISysOpeventLogService;
import banger.service.intf.ISysSocketLogService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

@Service
public class SystemModuleImpl implements InitializingBean, ISystemModule{

	@Autowired
	private ISysOpeventLogService sysOpeventLogService;

	@Autowired
	private IBasicConfigService basicConfigService;

	@Autowired
	private IMobileInfoDao mobileInfoDao;
	
	@Autowired
	private IDataDictColDao dataDictColDao;
	
	@Resource
	private IDataDictProvider dataDictProvider;

	@Autowired
    private IMobileRecordService mobileRecordService;

	@Autowired
	private ISysSocketLogService sysSocketLogService;

	//模块加载初始化函数
	public void afterPropertiesSet() throws Exception {
	}

	@Override
	public void addSysSocketLog(Integer loanId, String socketCode, String sendXml, String returnMassage, String remark,String codeNum,String codeNumTwo) {

		SysSocketLog sysSocketLog = new SysSocketLog();
		sysSocketLog.setLoanId(loanId);
		sysSocketLog.setSocketCode(socketCode);
		sysSocketLog.setSendXml(sendXml);
		sysSocketLog.setReturnMassage(returnMassage);
		sysSocketLog.setRemark(remark);
		sysSocketLog.setCodeNum(codeNum);
		sysSocketLog.setCodeNumTwo(codeNumTwo);

		sysSocketLogService.insertSocketLog(sysSocketLog,0);
	}

//	<clause prepend="and" assert="!isNull([loanId])" > LOAN_ID = [loanId] </clause>
//	<clause prepend="and" assert="!isNullOrEmpty([socketCode])" > SOCKET_CODE = '[socketCode]' </clause>
//	<clause prepend="and" assert="!isNullOrEmpty([returnMassage])" > RETURN_MASSAGE = '[returnMassage]' </clause>
//	<clause prepend="and" assert="!isNullOrEmpty([remark])" > REMARK = '[remark]' </clause>

	@Override
	public boolean isSocketSuccess(Integer loanId, String socketCode,String codeNum ,String codeNumTwo) {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("loanId",loanId);
		condition.put("socketCode",socketCode);
		condition.put("remark","success");
		condition.put("codeNum",codeNum);
		condition.put("codeNumTwo",codeNumTwo);
		List<SysSocketLog> list = sysSocketLogService.querySocketLogList(condition);
		boolean isSuccess = false;
		if(list!=null&&list.size()>0){
			isSuccess = true;
		}
		return isSuccess;

	}

	/**
	 * @param module 模块名称
	 * @param action 操作对象名称
	 * @param userId 操作者userId
	 * @param ip     操作者ip地址
	 * @see banger.moduleIntf.ISystemModule#addSysOpeventLog(String, String, Integer, String)
	 */
	@Override
	public void addSysOpeventLog(String module, String action, Integer userId,
			String ip) {
		SysOpeventLog sysOpeventLog = new SysOpeventLog();
		sysOpeventLog.setOpeventAction(action);
		sysOpeventLog.setOpeventIp(ip);
		sysOpeventLog.setOpeventModule(module);
		sysOpeventLog.setOpeventUserId(userId);
		sysOpeventLog.setOpeventDate(Calendar.getInstance().getTime());
		sysOpeventLogService.addSysOpereventLog(sysOpeventLog);
	}

	@Override
	public SysBasicConfig getSysBasicConfig(String basicConfigKey) {
		return basicConfigService.getSysBasicConfigByKey(basicConfigKey);
	}

	@Override
	public void saveSysMobileInfo(String serialNo, String brand, String model, String sysVersion, String appVersion, Integer userId,String loginLongitude,String loginLatitude,String loginIp) {

		SysBasicConfig config = basicConfigService.getSysBasicConfigByKey("yhbd");
		SysMobileInfo info = mobileInfoDao.getMobileInfoById(serialNo);
        //插入登录记录
        SysMobileRecord mobileRecord = new SysMobileRecord();
		Boolean isNew = true;
		if(null!=info){
			isNew = false;
			if(config!=null&&"1".equals(config.getConfigStatus())&&info.getIsBind()==2){
				info.setIsBind(1);
			}
		}else{
			info = new SysMobileInfo();
			info.setCreateDate(new Date());
			info.setCreateUser(userId);
			info.setIsActived(1);
			info.setIsBind(1);
		}
		info.setSerialNo(serialNo);
		info.setMobileBrand(brand);
		info.setMobileModel(model);
		info.setSystemVersion(sysVersion);
		info.setAppVersion(appVersion);
		info.setLastUserId(userId);
		info.setUpdateUser(userId);
		info.setUpdateDate(new Date());
		info.setLastLoginTime(new Date());
		info.setDelFlag(1);
		if(StringUtils.isNotBlank(loginLatitude) && StringUtils.isNotBlank(loginLongitude)){
			info.setLoginLongitude(Double.parseDouble(loginLongitude));
			info.setLoginLatitude(Double.parseDouble(loginLatitude));
			mobileRecord.setLoginLatitude(Double.parseDouble(loginLatitude));
			mobileRecord.setLoginLongitude(Double.parseDouble(loginLongitude));
		}


        mobileRecord.setSerialNo(serialNo);
        mobileRecord.setLoginDate(new Date());
        mobileRecord.setLoginIp(loginIp);
        mobileRecord.setUserId(userId);

        mobileRecordService.insertMobileRecord(mobileRecord,userId);
		if(isNew){
			mobileInfoDao.insertMobileInfo(info);
		}else{
			mobileInfoDao.updateMobileInfo(info);
		}
	}


	@Override
	public IPageList<SysMobileInfo> queryMobileList(String serialNo, String brand, String model, String sysVersion, String appVersion, String userName,  IPageSize page) {
		Map<String,Object> condition = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(serialNo)){
			condition.put("serialNo",serialNo);
		}
		if(StringUtils.isNotBlank(brand)){
			condition.put("brand",brand);
		}
		if(StringUtils.isNotBlank(model)){
			condition.put("model",model);
		}
		if(StringUtils.isNotBlank(sysVersion)){
			condition.put("sysVersion",sysVersion);
		}
		if(StringUtils.isNotBlank(appVersion)){
			condition.put("appVersion",appVersion);
		}
		if(StringUtils.isNotBlank(userName)){
			condition.put("userName",userName);
		}
		return mobileInfoDao.queryMobileInfoList(condition,page);
	}

	public void setSysOpeventLogService(ISysOpeventLogService sysOpeventLogService) {
		this.sysOpeventLogService = sysOpeventLogService;
	}

	public void setBasicConfigService(IBasicConfigService basicConfigService) {
		this.basicConfigService = basicConfigService;
	}

	public void addOpeventLog(SysOpeventLog sysOpeventLog) {
		sysOpeventLogService.addSysOpereventLog(sysOpeventLog);		
	}

	@Override
	public List<SysDataDictCol> queryDataDictColListByName(String name) {
		return dataDictColDao.queryDataDictColListByName(name);
	}

	@Override
	public IDataDictProvider getDataDictProvider() {
		return dataDictProvider;
	}

	/**
	 * 得到产品类型
	 * @return
	 */
	@Override
	public JSONObject queryProductType() {
		JSONObject root = new JSONObject();
		List<SysDataDictCol> list = dataDictColDao.queryDataDictColListByName("CD_PRODUCT_TYPE");
		JSONArray ja = new JSONArray();
		for(SysDataDictCol dictCol : list){
			JSONObject json = new JSONObject();
			json.put("value",dictCol.getDictValue());
			json.put("name",dictCol.getDictName());
			ja.add(json);
		}
		root.put("data",ja);
		 return root;
	}

	@Override
	public SysMobileInfo getMobileInfoById(String serialNo) {
		return mobileInfoDao.getMobileInfoById(serialNo);
	}


}
