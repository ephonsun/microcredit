package banger.moduleIntf;

import banger.domain.config.AutoBaseTemplate;

import java.util.List;

/**
 * Created by Administrator on 2017/11/21.
 */
public interface IConfigService {

    List<AutoBaseTemplate> getAllTemplateList();


    AutoBaseTemplate getTemplateListByTable(String tableName);

    List<AutoBaseTemplate>  getTemplateListByTables(String[] tableName);
}
