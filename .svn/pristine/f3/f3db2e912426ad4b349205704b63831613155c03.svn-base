package banger.service.intf;

import banger.domain.loan.*;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.moduleIntf.ILoanApplyProvider;

import java.util.List;
import java.util.Map;

public interface ILoanApplyService extends ILoanApplyProvider {

    /**
     * 新增贷款申请表
     * @param applyInfo 实体对像
     * @param loginUserId 登入用户Id
     */
    void insertApplyInfo(LoanApplyInfo applyInfo,Integer loginUserId);

    /**
     *修改贷款申请表
     * @param applyInfo 实体对像
     * @param loginUserId 登入用户Id
     */
    void updateApplyInfo(LoanApplyInfo applyInfo,Integer loginUserId);

    /**
     * 通过主键删除贷款申请表
     * @param loanId 主键Id
     */
    void deleteApplyInfoById(Integer loanId);

    /**
     * 通过主键得到贷款申请表
     * @param loanId 主键Id
     */
    LoanApplyInfo getApplyInfoById(Integer loanId);

    /**
     * 查询贷款申请表
     * @param condition 查询条件
     * @return
     */
    List<LoanApplyInfo> queryApplyInfoList(Map<String,Object> condition);

    /**
     * 分页查询贷款申请表
     * @param condition 查询条件
     * @param page 分页对像
     * @return
     */
    IPageList<LoanApplyInfo> queryApplyInfoList(Map<String,Object> condition,IPageSize page);


    /**
     * 自定义申请表，列表信息
     * @param condition 查询条件
     * @param page 分页对像
     * @return
     */
    IPageList<LoanApplyInfo_Web_Query> queryAllInfoList(Map<String,Object> condition, IPageSize page);

    List<LoanApplyInfo_Web_Query> queryAllInfoList(Map<String, Object> condition);

    IPageList<LoanApplyInfo_Web_Query> queryLoanAuditList(Map<String, Object> condition, IPageSize page);

    void updateApplyInfoIgnoreNull(LoanApplyInfo applyInfoById, Integer loginUserId);

    /**
     * 校验贷款类型是否已经使用
     * @param typeId
     * @return
     */
    boolean checkLoanType(Integer typeId);

    void updateModelScoreByLoanId(LoanApplyInfo applyInfo);

    IPageList<CommPeoInfo_Query> queryCommPeoInfoListData(Map<String,Object> condition,IPageSize page);

    void updateCommPeoInfo(CommPeoInfo commPeoInfo);

    void deleteCommPeoInfo(Integer id);

    CommPeoInfo queryCommPeoInfoById(Integer id);

    List<CommPeoInfo> queryCommPeoInfoListByItemId(Integer itemId);

    List<CommPeoInfo> queryCommPeoInfoListByLoanId(Integer loanId);

    IPageList<LoanApplyInfo_Web_Query> queryCollectionList(Map<String, Object> condition, IPageSize page);
}
