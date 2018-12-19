package banger.service.intf;

import banger.domain.system.SysBasicConfig;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/18.
 */
public interface IBasicConfigService {

    /**
     * 查询基础配置项列表
     * @param condition
     * @return
     */
    List<SysBasicConfig> querySysBasicConfigList(Map<String,Object> condition);

    /**
     * 修改基础数据
     * @param basicConfig
     * @param loginUserId
     */
    void updateBasicConfig(SysBasicConfig basicConfig, Integer loginUserId);

    /**
     * 根据基础配置key值获取单个配置对象
     * @param basicConfigKey
     * @return
     */
    SysBasicConfig getSysBasicConfigByKey(String basicConfigKey);

	/**
	 * 通过主键得到基本配置表
	 * @param BASIC_CONFIG_ID 主键Id
	 */
	SysBasicConfig getBasicConfigById(Integer basicConfigId);

    Integer queryRepayPlanValue();
}
