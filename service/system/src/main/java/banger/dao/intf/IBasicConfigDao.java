package banger.dao.intf;

import banger.domain.system.SysBasicConfig;

import java.util.List;
import java.util.Map;

/**
 * 基础配置
 */
public interface IBasicConfigDao {

	/**
	 * 查询基础配置配置表
	 * @param condition 查询条件
	 * @return
	 */
	List<SysBasicConfig> querySysBasicConfigList(Map<String, Object> condition);

	/**
	 * 修改基础数据
	 * @param basicConfig
	 */
	void updateSysBasicConfig(SysBasicConfig basicConfig);

	/**
	 * 根据基础数据key获取配置对象
	 * @param basicConfigKey
	 * @return
	 */
	SysBasicConfig getSysBasicConfig(String basicConfigKey);

    Integer queryFirstMonitorDay();

	Integer queryNormalMonitorDay();

	Integer queryConcernMonitorDay();

	/**
	 * 通过主键得到基本配置表
	 * @param basicConfigId 主键Id
	 */
	SysBasicConfig getBasicConfigById(Integer basicConfigId);

	Integer queryRepayPlanValue();
}
