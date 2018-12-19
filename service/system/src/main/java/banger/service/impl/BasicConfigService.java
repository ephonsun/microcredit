package banger.service.impl;

import banger.dao.impl.BasicConfigDao;
import banger.domain.system.SysBasicConfig;
import banger.framework.util.DateUtil;
import banger.moduleIntf.IBasicConfigProvider;
import banger.service.intf.IBasicConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/18.
 */
@Service
public class BasicConfigService implements IBasicConfigService,IBasicConfigProvider{

    @Resource
    private BasicConfigDao basicConfigDao;

    @Override
    public List<SysBasicConfig> querySysBasicConfigList(Map<String, Object> condition) {
        List<SysBasicConfig> sysBasicConfigList = basicConfigDao.querySysBasicConfigList(condition);
        /**
         * 如果为接口开关和功能开关，修改status值为中文展示
         */
        for(SysBasicConfig sysBasicConfig : sysBasicConfigList){
            if(1!=sysBasicConfig.getConfigType()){
                if("1".equals(sysBasicConfig.getConfigStatus())){
                    sysBasicConfig.setConfigStatus("开启");
                }else{
                    sysBasicConfig.setConfigStatus("关闭");
                }
            }
        }
        return sysBasicConfigList;
    }

    @Override
    public void updateBasicConfig(SysBasicConfig basicConfig, Integer loginUserId) {
        basicConfig.setUpdateUser(loginUserId);
        basicConfig.setUpdateDate(DateUtil.getCurrentDate());
        basicConfigDao.updateSysBasicConfig(basicConfig);
    }

    @Override
    public SysBasicConfig getSysBasicConfigByKey(String  basicConfigKey) {
        return basicConfigDao.getSysBasicConfig(basicConfigKey);
    }

	/**
	 * 通过主键得到基本配置表
	 * @param BASIC_CONFIG_ID 主键Id
	 */
	public SysBasicConfig getBasicConfigById(Integer basicConfigId){
		return this.basicConfigDao.getBasicConfigById(basicConfigId);
	}

    @Override
    public Integer queryRepayPlanValue() {
        return this.basicConfigDao.queryRepayPlanValue();
    }

    @Override
    public Integer queryFirstMonitorDay() {
        return this.basicConfigDao.queryFirstMonitorDay();
    }

    @Override
    public Integer queryNormalMonitorDay() {
        return this.basicConfigDao.queryNormalMonitorDay();
    }

    @Override
    public Integer queryConcernMonitorDay() {
        return this.basicConfigDao.queryConcernMonitorDay();
    }

}