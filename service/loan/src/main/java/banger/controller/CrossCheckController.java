package banger.controller;

import banger.common.BaseController;
import banger.common.tools.StringUtil;
import banger.domain.loan.*;
import banger.framework.util.DateUtil;
import banger.moduleIntf.*;
import banger.service.intf.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.Date;


@Controller
@RequestMapping("crossCheck")
public class CrossCheckController extends BaseController {

    @Resource
    ILoanCrossCheckQuanyiquanProvider loanCrossCheckQuanyiquanProvider;
    @Resource
    ILoanCrossCheckIncomeProvider loanCrossCheckIncomeProvider;
    @Resource
    ILoanCrossCheckSaleProvider loanCrossCheckSaleProvider;
    @Resource
    ILoanCrossCheckGrossProfitProvider loanCrossCheckGrossProfitProvider;
    @Resource
    ILoanCrossCheckNetProfitProvider loanCrossCheckNetProfitProvider;
    @Resource
    ILoanApplyService loanApplyService;
    @Resource
    ICrossCheckQuanyiquanService crossCheckQuanyiquanService;
    @Resource
    ICrossCheckSaleService crossCheckSaleService;
    @Resource
    ICrossCheckGrossProfitService crossCheckGrossProfitService;
    @Resource
    ICrossCheckNetProfitService crossCheckNetProfitService;
    @Resource
    ICrossCheckIncomeService crossCheckIncomeService;
    @Autowired
    private ILoanAnalysislBusinessAndConsumService analysislBusinessAndConsumService;
    /**
     * 获取检查检验主界面
     *
     * @param loanId
     * @return
     */
    @RequestMapping("getCrossCheckPage")
    public String getCrossCheckPage(@RequestParam(value = "loanId", defaultValue = "") String loanId, @RequestParam(value = "showApply", defaultValue = "") String showApply) {
        if (StringUtils.isNotBlank(loanId) && StringUtils.isNumeric(loanId)) {
            LoanApplyInfo loanApplyInfo = loanApplyService.getApplyInfoById(Integer.parseInt(loanId));
            Integer loanClassId = loanApplyInfo.getLoanClassId();
            if (loanClassId != null) {
                //查询交叉检验权益表
                LoanCrossCheckQuanyiquan lccq = loanCrossCheckQuanyiquanProvider.getCrossCheckQuanyiquanByLoanId(Integer.parseInt(loanId));
                if(lccq != null && "1".equals(showApply)){
                    lccq.setReduceDesc(StringUtil.blackDisplayForDetail(lccq.getReduceDesc()));
                    lccq.setIncreaseDesc(StringUtil.blackDisplayForDetail(lccq.getIncreaseDesc()));
                    lccq.setInitRightDesc(StringUtil.blackDisplayForDetail(lccq.getInitRightDesc()));
                }
                if (loanClassId == 1) {
                    //查询交叉检验销售表
                    LoanCrossCheckSale lccs = loanCrossCheckSaleProvider.getCrossCheckSaleByLoanId(Integer.parseInt(loanId));
                    if(lccs != null && "1".equals(showApply)){
                        lccs.setInfomation1(StringUtil.blackDisplayForDetail(lccs.getInfomation1()));
                        lccs.setInfomation2(StringUtil.blackDisplayForDetail(lccs.getInfomation2()));
                        lccs.setInfomation3(StringUtil.blackDisplayForDetail(lccs.getInfomation3()));
                    }
                    //查询交叉检验毛利表
                    LoanCrossCheckGrossProfit lccgp = loanCrossCheckGrossProfitProvider.getCrossCheckGrossProfitByLoanId(Integer.parseInt(loanId));
                    if(lccgp != null && "1".equals(showApply)){
                        lccgp.setInfomation1(StringUtil.blackDisplayForDetail(lccgp.getInfomation1()));
                        lccgp.setInfomation2(StringUtil.blackDisplayForDetail(lccgp.getInfomation2()));
                        lccgp.setInfomation3(StringUtil.blackDisplayForDetail(lccgp.getInfomation3()));
                    }
                    //查询交叉检验净利表
                    LoanCrossCheckNetProfit lccnp = loanCrossCheckNetProfitProvider.getCrossCheckNetProfitByLoanId(Integer.parseInt(loanId));
                    if(lccnp != null && "1".equals(showApply)){
                        lccnp.setInfomation1(StringUtil.blackDisplayForDetail(lccnp.getInfomation1()));
                        lccnp.setInfomation2(StringUtil.blackDisplayForDetail(lccnp.getInfomation2()));
                        lccnp.setInfomation3(StringUtil.blackDisplayForDetail(lccnp.getInfomation3()));
                    }
                    setAttribute("lccq", lccq);
                    setAttribute("lccs", lccs);
                    setAttribute("lccgp", lccgp);
                    setAttribute("lccnp", lccnp);
                    setAttribute("loanClassId", 1);
                }
                if (loanClassId == 2) {
                    //查询交叉检验收入表
                    LoanCrossCheckIncome lcci = loanCrossCheckIncomeProvider.getCrossCheckIncomeByLoanId(Integer.parseInt(loanId));
                    if(lcci != null && "1".equals(showApply)){
                        lcci.setInfomation(StringUtil.blackDisplayForDetail(lcci.getInfomation()));
                    }
                    setAttribute("lccq", lccq);
                    setAttribute("lcci", lcci);
                    setAttribute("loanClassId", 2);
                }
                setAttribute("loanId", loanId);
                if (StringUtils.isNotBlank(showApply) && "1".equals(showApply)) {
                    return "loan/vm/investigate/queryCrossCheck";
                }
                return "loan/vm/investigate/crossCheck";

            }
        }
        return "";
    }

    /**
     * 新增或编辑交叉检验表
     */
    @RequestMapping(value = "/saveCrossCheck")
    public void saveCrossCheck(@RequestParam(value = "param", defaultValue = "") String param, @RequestParam(value = "loanId", defaultValue = "") String loanId) {
        try {
            JSONArray array = JSONArray.fromObject(param);
            Integer userId = getLoginInfo().getUserId();
            LoanCrossCheckQuanyiquan jo1 = (LoanCrossCheckQuanyiquan) JSONObject.toBean(array.getJSONObject(0), LoanCrossCheckQuanyiquan.class);
            if (StringUtil.isNotEmpty(array.getJSONObject(0).getString("initRightDate"))) {
                jo1.setInitRightDate(DateUtil.parser(array.getJSONObject(0).getString("initRightDate"),DateUtil.DEFAULT_DATE_FORMAT));
            }
            jo1.setInitRightDate(null);
            if (StringUtils.isNotBlank(loanId) && StringUtils.isNumeric(loanId)) {
                LoanApplyInfo loanApplyInfo = loanApplyService.getApplyInfoById(Integer.parseInt(loanId));
                Integer loanClassId = loanApplyInfo.getLoanClassId();

                if (loanClassId != null) {
                    if (loanClassId == 1) {
                        //查询交叉检验权益表
                        LoanCrossCheckQuanyiquan lccq = loanCrossCheckQuanyiquanProvider.getCrossCheckQuanyiquanByLoanId(Integer.parseInt(loanId));
                        jo1.setLoanClassId(1);
                        jo1.setLoanId(Integer.parseInt(loanId));
                        if (lccq != null) {//更新
                            jo1.setId(lccq.getId());
                            crossCheckQuanyiquanService.updateCrossCheckQuanyiquan(jo1, userId);
                        } else {//新增
                            crossCheckQuanyiquanService.insertCrossCheckQuanyiquan(jo1, userId);
                        }
                        //计算并修改交叉检验权益表偏差率 （用在检查权益表发生改变时使用）
                        loanCrossCheckQuanyiquanProvider.updateQuanyiquanDev(Integer.parseInt(loanId));
                        LoanCrossCheckSale jo2 = (LoanCrossCheckSale) JSONObject.toBean(array.getJSONObject(1), LoanCrossCheckSale.class);
                        LoanCrossCheckGrossProfit jo3 = (LoanCrossCheckGrossProfit) JSONObject.toBean(array.getJSONObject(2), LoanCrossCheckGrossProfit.class);
                        LoanCrossCheckNetProfit jo4 = (LoanCrossCheckNetProfit) JSONObject.toBean(array.getJSONObject(3), LoanCrossCheckNetProfit.class);
                        //查询交叉检验销售表
                        LoanCrossCheckSale lccs = loanCrossCheckSaleProvider.getCrossCheckSaleByLoanId(Integer.parseInt(loanId));
                        jo2.setLoanId(Integer.parseInt(loanId));
                        jo2.setLoanClassId(1);
                        if (lccs != null) {
                            jo2.setId(lccs.getId());
                            crossCheckSaleService.updateCrossCheckSale(jo2, userId);
                        } else {
                            crossCheckSaleService.insertCrossCheckSale(jo2, userId);
                        }
                        //计算并修改交叉检验销售额表偏差率 （用在检查销售率发生改变时使用）
                        loanCrossCheckSaleProvider.updateSaleDev(Integer.parseInt(loanId));

                        //查询交叉检验毛利表
                        LoanCrossCheckGrossProfit lccgp = loanCrossCheckGrossProfitProvider.getCrossCheckGrossProfitByLoanId(Integer.parseInt(loanId));
                        jo3.setLoanId(Integer.parseInt(loanId));
                        jo3.setLoanClassId(1);
                        if (lccgp != null) {
                            jo3.setId(lccgp.getId());
                            crossCheckGrossProfitService.updateNullCrossCheckGrossProfit(jo3, userId);
                        } else {
                            crossCheckGrossProfitService.insertCrossCheckGrossProfit(jo3, userId);

                        }
                        //计算并修改交叉检验毛利表偏差率（用在检查毛利率发生改变时使用）
                        loanCrossCheckGrossProfitProvider.updateGroProDev(Integer.parseInt(loanId));


                        //查询交叉检验净利表
                        LoanCrossCheckNetProfit lccnp = loanCrossCheckNetProfitProvider.getCrossCheckNetProfitByLoanId(Integer.parseInt(loanId));
                        jo4.setLoanId(Integer.parseInt(loanId));
                        jo4.setLoanClassId(1);
                        if (lccnp != null) {
                            jo4.setId(lccnp.getId());
                            crossCheckNetProfitService.updateNullCrossCheckNetProfit(jo4, userId);
                        } else {
                            crossCheckNetProfitService.insertCrossCheckNetProfit(jo4, userId);
                        }
                        //计算并修改交叉检验净利表偏差率 （用在检查净利率发生改变时使用）
                        loanCrossCheckNetProfitProvider.updateNetProDev(Integer.parseInt(loanId));

                    } else {
                        //查询交叉检验权益表
                        LoanCrossCheckQuanyiquan lccq = loanCrossCheckQuanyiquanProvider.getCrossCheckQuanyiquanByLoanId(Integer.parseInt(loanId));
                        jo1.setLoanClassId(2);
                        jo1.setLoanId(Integer.parseInt(loanId));
                        if (lccq != null) {//更新
                            jo1.setId(lccq.getId());
                            crossCheckQuanyiquanService.updateCrossCheckQuanyiquan(jo1, userId);
                        } else {//新增
                            crossCheckQuanyiquanService.insertCrossCheckQuanyiquan(jo1, userId);
                        }
                        //计算并修改交叉检验权益表偏差率 （用在检查权益表发生改变时使用）
                        loanCrossCheckQuanyiquanProvider.updateQuanyiquanDev(Integer.parseInt(loanId));
                        LoanCrossCheckIncome jo2 = (LoanCrossCheckIncome) JSONObject.toBean(array.getJSONObject(1), LoanCrossCheckIncome.class);
                        //查询交叉检验收入表
                        LoanCrossCheckIncome lcci = loanCrossCheckIncomeProvider.getCrossCheckIncomeByLoanId(Integer.parseInt(loanId));
                        jo2.setLoanId(Integer.parseInt(loanId));
                        jo2.setLoanClassId(2);
                        if (lcci != null) {
                            jo2.setId(lcci.getId());
                            crossCheckIncomeService.updateNullCrossCheckIncome(jo2, userId);
                        } else {
                            crossCheckIncomeService.insertCrossCheckIncome(jo2, userId);

                        }

                    }
                    //根据贷款id处理三表数据,另存财务分析详情明细
                    analysislBusinessAndConsumService.saveAnalysislBusinessOrConsum(Integer.parseInt(loanId));
                    renderText(true, "保存成功", String.valueOf(""));
                }

            }

        } catch (Exception e) {
            log.error("保存交叉检验信息异常|");
            renderText(false, "保存失败", String.valueOf(""));
        }

    }

}
