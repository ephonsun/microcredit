package banger.dao.intf;

import banger.domain.config.AutoTableCountCustid;
import banger.domain.system.SysDataDictCol;

import java.util.List;

/**
 * Created by XUElw on 2017/10/13.
 */
public interface IProcedureDao {
    /**
     * 根据字典表英文名称获得字典对象
     * @return
     */
    List<SysDataDictCol> queryDictColByDictName(String dictName);

    /**
     * 统计客户导入表中重复的记录
     * @return
     */
    List<AutoTableCountCustid> countAutoTableCustId();

    /**
     * 修改错误标志
     * @param arr
     */
    void updateErrorFlag(List<Integer> arr);

    /**
     * 修改导入客户表的custid
     */
    void updateCustId();
}
