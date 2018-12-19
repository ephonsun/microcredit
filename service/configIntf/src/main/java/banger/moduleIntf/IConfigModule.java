package banger.moduleIntf;

public interface IConfigModule {

	/**
	 * 得到自定义字段接口
	 * @return
	 */
	IAutoFieldProvider getAutoFieldProvider();
	
	/**
	 * 得到自定义模板接口
	 * @return
	 */
	IAutoTemplateProvider getAutoTemplateProvider();


	/**
	 * 得到模板配置接口
	 * @return
	 */
	IConfigFileProvider getConfigFileProvider();

	/**
	 * 得到自定义字段接口
	 * @return
	 */
	ITableInfoProvider getTableInfoProvider();
}
