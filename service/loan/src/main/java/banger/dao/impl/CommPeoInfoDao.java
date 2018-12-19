package banger.dao.impl;

import banger.dao.intf.ICommPeoInfoDao;
import banger.domain.loan.CommPeoInfo;
import banger.domain.loan.CommPeoInfo_Query;
import banger.framework.dao.PageSizeDao;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

import java.util.List;
import java.util.Map;

/**
 * Created by ygb on 2018/5/28.
 */
public class CommPeoInfoDao extends PageSizeDao<CommPeoInfo> implements ICommPeoInfoDao {

    public IPageList<CommPeoInfo_Query> queryCommPeoInfoListData(Map<String,Object> condition,IPageSize page){
        return (IPageList<CommPeoInfo_Query>)this.queryEntities("queryCommPeoInfoListByCondition", page, condition);
    }

    public void updateCommPeoInfo(CommPeoInfo commPeoInfo){
        if(commPeoInfo.getId()==null){
            commPeoInfo.setId(this.newId().intValue());
            this.execute("insertCommPeoInfo",commPeoInfo);
        }else {
            this.execute("updateCommPeoInfoById",commPeoInfo);
        }
    }

    public void deleteCommPeoInfo(Integer id){
        this.execute("deleteCommPeoInfo",id);
    }

    public CommPeoInfo queryCommPeoInfoById(Integer id){
        return (CommPeoInfo)this.queryEntity("queryCommPeoInfoById",id);
    }

    public List<CommPeoInfo> queryCommPeoInfoListByItemId(Integer itemId){
        return (List<CommPeoInfo>)this.queryEntities("queryCommPeoInfoListByItemId",itemId);
    }

    public List<CommPeoInfo> queryCommPeoInfoListByLoanId(Integer loanId){
        return (List<CommPeoInfo>)this.queryEntities("queryCommPeoInfoListByLoanId",loanId);
    }
}
