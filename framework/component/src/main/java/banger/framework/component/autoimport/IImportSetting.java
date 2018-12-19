package banger.framework.component.autoimport;

import banger.framework.component.autoimport.setting.MappingInfo;

import java.util.List;

/**
 * Created by zhusw on 2017/9/26.
 */
public interface IImportSetting {

    /**
     * 得到列映射
     * @return
     */
    List<MappingInfo> getMapping();

}
