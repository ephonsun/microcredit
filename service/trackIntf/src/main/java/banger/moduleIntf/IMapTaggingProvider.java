package banger.moduleIntf;

import banger.domain.loan.LoanApplyInfo;
import banger.domain.track.MapTagging;
import net.sf.json.JSONArray;

import java.util.List;
import java.util.Map;

/**
 * 地图座标
 * Created by zhusw on 2017/8/2.
 */
public interface IMapTaggingProvider {

    /**
     * 查询经纬度
     * @param condition
     * @return
     */
    List<MapTagging> queryTaggingList(Map<String,Object> condition);

    /**
     * 新增经纬度
     * @param tagging
     * @param loginUserId
     */
    void insertTagging(MapTagging tagging,Integer loginUserId);

    /**
     * 修改经纬度
     * @param tagging
     * @param loginUserId
     */
    void updateTagging(MapTagging tagging,Integer loginUserId);

    /**
     * 同步经纬度，客户到贷款，三要素新增的时候
     */
    void syncTaggingCustomerToLoan(Integer loanId, Integer customerId, Integer loginUserId);

    /**
     * 获取经纬度地址的json，返回tableName，columnName，lbizId
     * 如果是贷款，客户id传空，如果是客户贷款id传空
     * @param loanId
     * @param customerId
     * @return
     */
    JSONArray getAddressJson(String loanId, String customerId);


    void syncTaggingLoanToCustomer(MapTagging mapTagging, Integer loanCustomerId, Integer userId);

    /**
     * 根据经纬度查询中文
     * @param lng
     * @param lat
     * @return
     */
    String getAddressByLngLat(String lng, String lat);
}
