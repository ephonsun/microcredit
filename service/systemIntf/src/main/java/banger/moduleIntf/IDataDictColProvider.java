package banger.moduleIntf;

import banger.domain.system.SysDataDictCol;

import java.util.List;
import java.util.Map;

/**
 * Created by wanggl on 2017/7/17.
 */
public interface IDataDictColProvider {

    List<SysDataDictCol> getSysDataDictListByDictName(String name);

    List<SysDataDictCol> queryDataDictColList(Map<String,Object> condition);

}
