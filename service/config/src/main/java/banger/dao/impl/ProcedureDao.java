package banger.dao.impl;

import banger.dao.intf.IProcedureDao;
import banger.domain.config.AutoTableCountCustid;
import banger.domain.system.SysDataDictCol;
import banger.framework.dao.PageSizeDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**r
 * Created by XUElw on 2017/10/13.
 */
@Repository
public class ProcedureDao extends PageSizeDao<SysDataDictCol> implements IProcedureDao{

    @Override
    public List<SysDataDictCol> queryDictColByDictName(String dictName) {
        return (List<SysDataDictCol>) this.queryEntities("queryDataDictColListByName",dictName);
    }

    @Override
    public List<AutoTableCountCustid> countAutoTableCustId() {
        return (List<AutoTableCountCustid>) this.queryEntities("countCustId");
    }

    @Override
    public void updateErrorFlag(List<Integer> arr) {
        this.execute("updateErrorFlag",arr);
    }

    @Override
    public void updateCustId() {
        this.execute("updateCustId");
    }
}
