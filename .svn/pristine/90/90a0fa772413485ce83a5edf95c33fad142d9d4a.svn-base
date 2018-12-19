package banger.moduleIntf;

import banger.domain.system.SysBasicConfig;

import java.util.List;
import java.util.Map;

/**
 * Created by wanggl on 2017/7/21.
 */
public interface IBasicConfigProvider {

    SysBasicConfig getSysBasicConfigByKey(String basicConfigKey);

    Integer queryRepayPlanValue();

    Integer queryFirstMonitorDay();

    Integer queryNormalMonitorDay();

    Integer queryConcernMonitorDay();

    List<SysBasicConfig> querySysBasicConfigList(Map<String, Object> condition);
}
