package banger.service.impl;

import banger.dao.intf.IForScreeDao;
import banger.domain.app.AppScreenCount;
import banger.domain.app.AppScreenLoan;
import banger.service.intf.IForScreeService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 投屏信息app
 * @author zmd
 *
 */
@Repository
public class ForScreeService implements IForScreeService {
    @Autowired
    IForScreeDao forScreeDao;

    /**1
     * 累计放款
     *
     */
    @Override
    public List<AppScreenLoan> getTotalLoanInfo() {
        return this.forScreeDao.getTotalLoanInfo();
    }
    /**2
     * 当年放款
     *
     */
    @Override
    public List<AppScreenLoan> getWhenLoan() {
        return this.forScreeDao.getWhenLoan();
    }
    /**4
     * 贷款阶段
     *
     */
    @Override
    public List<AppScreenCount> getLoanPhaseInfo() {
        return this.forScreeDao.getLoanPhaseInfo();
    }
    /**5
     * 放款趋势
     *
     */
    @Override
    public JSONObject getLoanTrend() {
        return this.forScreeDao.getLoanTrend();
    }
    /**6
     * 获取当月排行榜
     *
     */
    @Override
    public List<AppScreenCount> getTopLoanMonthInfo() {
        return this.forScreeDao.getTopLoanMonthInfo();
    }

}
