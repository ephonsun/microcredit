package banger.dao.impl;

import banger.dao.intf.IBasicConfigDao;
import banger.domain.system.SysBasicConfig;
import banger.framework.dao.PageSizeDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/18.
 */
public class BasicConfigDao extends PageSizeDao<SysBasicConfig> implements IBasicConfigDao {

    @Override
    public List<SysBasicConfig> querySysBasicConfigList(Map<String, Object> condition) {
        return (List<SysBasicConfig>)this.queryEntities("querySysBasicConfigList", condition);
    }

    @Override
    public void updateSysBasicConfig(SysBasicConfig basicConfig) {
        this.execute("updateSysBasicConfig",basicConfig);
    }

    @Override
    public SysBasicConfig getSysBasicConfig(String basicConfigKey) {
        return (SysBasicConfig)this.queryEntity("getSysBasicConfigByKey",basicConfigKey);
    }

    public Integer queryFirstMonitorDay() {
        Map<String,Object> condition = new HashMap<String, Object>();
        return this.queryInt("getFirstMonitorDay",condition);
    }

    public Integer queryNormalMonitorDay() {
        Map<String,Object> condition = new HashMap<String, Object>();
        return this.queryInt("queryNormalMonitorDay",condition);
    }

    /**
     * 通过主键得到基本配置表
     * @param basicConfigId 主键Id
     */
    public SysBasicConfig getBasicConfigById(Integer basicConfigId){
        return (SysBasicConfig)this.queryEntity("getBasicConfigById",basicConfigId);
    }

    public Integer queryConcernMonitorDay() {
        Map<String,Object> condition = new HashMap<String, Object>();
        return this.queryInt("queryConcernMonitorDay",condition);
    }

    public Integer queryRepayPlanValue() {
        Map<String,Object> condition = new HashMap<String, Object>();
        return this.queryInt("queryRepayPlanDay",condition);
    }
}
