package banger.service.intf;

import java.util.List;

import banger.domain.config.AutoBaseTemplate;

public interface IAutoFieldService {

	/**
	 * 得到模板集合
	 * @return
	 */
	List<AutoBaseTemplate> getTemplateListByIds(Integer[] tableIds);
	
	/**
	 * 得到模板集合
	 * @return
	 */
	List<AutoBaseTemplate> getTemplateListByTables(String[] tableNames);
	
}
