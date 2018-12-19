package banger.dao.intf;

import banger.domain.loan.CommPeoInfo;
import banger.domain.loan.CommPeoInfo_Query;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

import java.util.List;
import java.util.Map;

/**
 * Created by ygb on 2018/5/28.
 */
public interface ICommPeoInfoDao {

    IPageList<CommPeoInfo_Query> queryCommPeoInfoListData(Map<String,Object> condition,IPageSize page);

    void updateCommPeoInfo(CommPeoInfo commPeoInfo);

    void deleteCommPeoInfo(Integer id);

    CommPeoInfo queryCommPeoInfoById(Integer id);

    List<CommPeoInfo> queryCommPeoInfoListByItemId(Integer itemId);

    List<CommPeoInfo> queryCommPeoInfoListByLoanId(Integer loanId);
}
