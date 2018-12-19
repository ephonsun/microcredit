package banger.dao.impl;

import banger.dao.intf.IPmsBancsTelmDao;
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
public class PmsBancsTelmDao extends EntityDao<PmsBancsTelm> implements IPmsBancsTelmDao {

    /**
     * 修改一批用户
     * @param userList
     */
    public void updateUserListByImport(List<PmsUserImport> userList){
        if(userList.size()>0){
            this.executes("updateUserListByImport", userList);
        }
    }

    @Override
    public void insertPmsBancsTelm(List<PmsBancsTelm> bancsTelmList) {
        if(bancsTelmList.size()>0){
            /*List<PmsBancsTelm> insertBancsTelmList = new ArrayList<PmsBancsTelm>();
            for(int i = 0; i < bancsTelmList.size(); i++){
                insertBancsTelmList.add(bancsTelmList.get(i));
                if((i  > 0 && i % 200 == 0) || i == bancsTelmList.size() - 1){*/
                    this.executes("insertBancsTelm", bancsTelmList);
                    /*insertBancsTelmList.clear();
                }
            }*/
        }
    }

    @Override
    public void updatePmsBancsTelm(List<PmsBancsTelm> bancsTelmList) {
        if(bancsTelmList.size()>0){
        	String completeNoLeft = "000000000";
            List<PmsBancsTelm> updateBancsTelmList = new ArrayList<PmsBancsTelm>();
        	for(int i =0;i < bancsTelmList.size(); i++){
                PmsBancsTelm temp = bancsTelmList.get(i);
                temp.setCwo8TellerNo(completeNoLeft+temp.getCwo8TellerNo());
                updateBancsTelmList.add(temp);
                if((i  > 0 && i % 200 == 0) || i == bancsTelmList.size() - 1){
                    this.executes("updateBancsTelm", updateBancsTelmList);
                    updateBancsTelmList.clear();
                }
                //this.execute("updateBancsTelm", temp);
            }
        }
    }

    @Override
    public void deletePmsBancsTelm() {
        this.execute("deleteBancsTelm");
    }

    @Override
    public List<PmsBancsTelm> getBancsTelmInfo() {
        return (List<PmsBancsTelm>)this.queryEntities("getBancsTelmInfo", new Object[]{});
    }
}
