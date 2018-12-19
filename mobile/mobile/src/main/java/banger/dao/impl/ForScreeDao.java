package banger.dao.impl;

import banger.dao.intf.IForScreeDao;
import banger.domain.app.AppScreenCount;
import banger.domain.app.AppScreenLoan;
import banger.domain.loan.LoanApplyInfo;
import banger.framework.dao.PageSizeDao;
import banger.framework.util.DateUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 投屏信息app
 * @author zmd
 *
 */
@Repository
public class ForScreeDao extends PageSizeDao<LoanApplyInfo> implements IForScreeDao {
    /**1
     * 累计放款
     *
     */
    @Override
    public List<AppScreenLoan> getTotalLoanInfo() {
               //查询有交易记录的贷款
        return (List< AppScreenLoan>) this.queryEntities("getTotalLoanInfo");
    }
    /**2
     * 当年放款
     *
     */
    @Override
    public List<AppScreenLoan> getWhenLoan() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy");
        String time = df.format(c.getTime())+"-01-01";
        c.add(Calendar.YEAR, 1);
        String timeEnc = df.format(c.getTime())+"-01-01";
        return (List< AppScreenLoan>) this.queryEntities("getWhenLoan",time, timeEnc);
    }
    /**4
     * 贷款阶段
     *
     */
    @Override
    public List<AppScreenCount> getLoanPhaseInfo() {
        return (List<AppScreenCount>)this.queryEntities("getLoanPhaseInfo");
    }
    /**5
     * 放款趋势
     *
     */
    @Override
    public JSONObject getLoanTrend() {
        JSONObject jsonObject = new JSONObject();
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.add(Calendar.MONTH, -11);
        Map<String,Object> condition = new HashMap<String,Object>();
        List<Map<String,Object>> listmap = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 12; i++) {
            condition.clear();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("month", c.get(Calendar.MONTH) + 1);
            condition.put("beginTime", DateUtil.format(c.getTime(), DateUtil.DEFAULT_DATE_FORMAT));
            c.add(Calendar.MONTH, 1);
            condition.put("endTime", DateUtil.format(c.getTime(), DateUtil.DEFAULT_DATE_FORMAT));
            AppScreenCount appScreenCount = (AppScreenCount) this.queryEntity("getLoanTrend" ,condition);
            map.put("num", appScreenCount.getNum());
            if (appScreenCount.getMoney()!=null){
                map.put("money", appScreenCount.getMoney().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP));
            }else {
                map.put("money",BigDecimal.valueOf(0));
            }
            listmap.add(map);
        }
        jsonObject.put("listLoan",listmap);
        return  jsonObject;
    }
    /**6
     * 获取当月排行榜
     *
     */
    @Override
    public List<AppScreenCount> getTopLoanMonthInfo() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        String time = df.format(c.getTime())+"-01";
        c.add(Calendar.MONTH, 1);
        String timeEnd = df.format(c.getTime())+"-01";
        return (List<AppScreenCount>)this.queryTopEntities("getTopLoanMonthInfo", 10, time, timeEnd);
    }

}
