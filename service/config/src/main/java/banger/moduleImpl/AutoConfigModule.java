package banger.moduleImpl;

import javax.annotation.Resource;

import banger.moduleIntf.*;
import org.springframework.stereotype.Repository;

@Repository
public class AutoConfigModule implements IConfigModule {
	@Resource
	IAutoFieldProvider autoFieldProvider;
	
	@Resource
	IAutoTemplateProvider autoTemplateProvider;
	@Resource
	IConfigFileProvider configFileProvider;
	@Resource
	ITableInfoProvider tableInfoProvider;


	public IAutoFieldProvider getAutoFieldProvider() {
		return autoFieldProvider;
	}
	
	/**
	 * 得到自定义模板接口
	 * @return
	 */
	public IAutoTemplateProvider getAutoTemplateProvider() {
		return autoTemplateProvider;
	}

	@Override
	public IConfigFileProvider getConfigFileProvider() {
		return configFileProvider;
	}

	@Override
	public ITableInfoProvider getTableInfoProvider() {
		return tableInfoProvider;
	}

}
