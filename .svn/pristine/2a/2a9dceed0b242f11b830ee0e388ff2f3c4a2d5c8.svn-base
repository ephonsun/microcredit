package banger.dao.intf;

import banger.domain.app.AppScreenCount;
import banger.domain.app.AppScreenLoan;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * 投屏信息app
 * @author zmd
 *
 */
public interface IForScreeDao {
    /**1
     * 累计放款
     *
     */
    List<AppScreenLoan> getTotalLoanInfo();
    /**2
     * 当年放款
     *
     */
    List<AppScreenLoan> getWhenLoan();
    /**4
     * 贷款阶段
     *
     */
    List<AppScreenCount> getLoanPhaseInfo();
    /**5
     * 放款趋势
     *
     */
    JSONObject getLoanTrend();
    /**6
     * 获取当月排行榜
     *
     */
    List<AppScreenCount> getTopLoanMonthInfo();


}
