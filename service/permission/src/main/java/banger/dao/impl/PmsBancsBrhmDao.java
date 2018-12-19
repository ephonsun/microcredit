package banger.dao.impl;

import banger.dao.intf.IPmsBancsBrhmDao;
import banger.dao.intf.IRoleDao;
import banger.dao.intf.IUserImportDao;
import banger.dao.intf.IUserRoleDao;
import banger.domain.permission.*;
import banger.framework.dao.EntityDao;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-6-10
 * Time: 上午11:24
 * To change this template use File | Settings | File Templates.
 */

@Repository
public class PmsBancsBrhmDao extends EntityDao<PmsBancsBrhm> implements IPmsBancsBrhmDao {

    @Override
    public void insertPmsBancsBrhm(List<PmsBancsBrhm> pmsBancsBrhmList) {
        if(pmsBancsBrhmList.size()>0){
            List<PmsBancsBrhm> insertBancsBrhmList = new ArrayList<PmsBancsBrhm>();
            for(int i = 0;i<pmsBancsBrhmList.size();i++){
                insertBancsBrhmList.add(pmsBancsBrhmList.get(i));
                if((i  > 0 && i % 200 == 0) || i == pmsBancsBrhmList.size() - 1){
                    this.executes("insertBancsBrhm", insertBancsBrhmList);
                    insertBancsBrhmList.clear();
                }
            }
        }
    }

    @Override
    public void deletePmsBancsBrhm() {
        this.execute("deleteBancsBrhm");
    }
}
