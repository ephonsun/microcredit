package banger.service.impl;

import banger.dao.intf.*;
import banger.domain.loan.*;
import banger.domain.system.SysOpeventLog;
import banger.framework.util.OperationUtil;
import banger.moduleIntf.ILoanCrossCheckProvider;
import banger.service.intf.ICrossCheckFormulaService;
import banger.service.intf.ICrossCheckNetProfitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 交叉检验公式通用方法（根据毛利率修改各表偏差率）
 */
@Repository
public class CrossCheckFormulaService implements ICrossCheckFormulaService,ILoanCrossCheckProvider {

    @Autowired
    private ICrossCheckGrossProfitDao crossCheckGrossProfitDao;

    @Autowired
    private ICrossCheckNetProfitDao crossCheckNetProfitDao;

    @Autowired
    private ICrossCheckSaleDao crossCheckSaleDao;

    @Autowired
    private ICrossCheckQuanyiquanDao crossCheckQuanyiquanDao;

    @Autowired
    private IProfitLossInfoDao profitLossInfoDao;

    @Autowired
    private IAssetsInfoDao assetsInfoDao;



    /**
     * 同时修改损益表毛利率之后更新毛利表和净利表中的毛利净利偏差
     *
     * @param loanId 贷款id
     */
    public void updateGroProAndNetProDeviation(Integer loanId) {
        LoanProfitLossInfo lpli = this.getProfitLossInfoByLoanId(loanId);
        this.updateDeviationGrossProfit(loanId,lpli);
        this.updateDeviationNetProfit(loanId,lpli);
    }


    /**
     * 计算并修改交叉检验毛利表偏差率（用在检查毛利率发生改变时使用）
     * @param loanId 贷款id
     */
    public void updateGroProDev(Integer loanId){
        LoanProfitLossInfo lpli = this.getProfitLossInfoByLoanId(loanId);
        this.updateDeviationGrossProfit(loanId,lpli);
    }


    /**
     * 计算并修改交叉检验净利表偏差率 （用在检查净利率发生改变时使用）
     * @param loanId 贷款id
     */
    public void updateNetProDev(Integer loanId){
        LoanProfitLossInfo lpli = this.getProfitLossInfoByLoanId(loanId);
        this.updateDeviationNetProfit(loanId,lpli);
    }

    /**
     * 计算并修改交叉检验销售表偏差率 （用在检查销售表发生改变时使用）
     * @param loanId 贷款id
     */
    public void updateSaleDev(Integer loanId){
        LoanProfitLossInfo lpli = this.getProfitLossInfoByLoanId(loanId);
        this.updateDeviationSale(loanId,lpli);
    }

    /**
     * 计算并修改交叉检验权益表偏差率 （用在检查权益表发生改变时使用）
     * @param loanId 贷款id
     */
    public void updateQuanyiquanDev(Integer loanId){
        LoanAssetsInfo lai = this.getLoanAssetsInfoByLoanId(loanId);
        LoanProfitLossInfo lpli =this.getProfitLossInfoByLoanId(loanId);
        this.updateDeviationQuanyiquan(loanId,lai,lpli);
    }


    /**
     * 修改损益表营业收入之后更新销售额表偏差
     *
     * @param loanId 贷款id
     */
    /*public void updateCroCheSaleDeviation(Integer loanId) {

    }*/


    /**
     * 计算并修改交叉检验毛利表偏差率
     *
     * @param loanId 贷款id
     * @param lpli   损益表信息（主要是总金额，和毛利率）
     */
    private void updateDeviationGrossProfit(Integer loanId, LoanProfitLossInfo lpli) {
        BigDecimal grProRatio=null;
        if(null != lpli) {
            grProRatio = lpli.getGrossProfitRatio();
        }
        //获取损益表毛利率
        //获取毛利表信息（分别获取检查毛利率）
        LoanCrossCheckGrossProfit lccgp = crossCheckGrossProfitDao.getCrossCheckGrossProfitByLoanId(loanId);
        BigDecimal devRatio1 = null;   //偏差1
        BigDecimal devRatio2 = null;  //偏差2
        BigDecimal devRatio3 = null;   //偏差3
        if (null != lccgp) {
            BigDecimal ratio1 = lccgp.getCheckGrossProfitRatio1();
            BigDecimal ratio2 = lccgp.getCheckGrossProfitRatio2();
            BigDecimal ratio3 = lccgp.getCheckGrossProfitRatio3();
            if (ratio1 != null && null != grProRatio) {
                devRatio1 = OperationUtil.getDeviationRatio(grProRatio, ratio1);
            }
            if (ratio2 != null && null != grProRatio) {
                devRatio2 = OperationUtil.getDeviationRatio(grProRatio, ratio2);
            }
            if (ratio3 != null && null != grProRatio) {
                devRatio3 = OperationUtil.getDeviationRatio(grProRatio, ratio3);
            }
            Map<String, Object> condition = new HashMap<String, Object>();
            condition.put("deviationRatio1", devRatio1);
            condition.put("deviationRatio2", devRatio2);
            condition.put("deviationRatio3", devRatio3);
            condition.put("loanId", loanId);

            crossCheckGrossProfitDao.updateCrossCheckGrossProfitDeviationRatio(condition);
        }
    }

    /**
     * 计算并修改交叉检验净利表偏差率
     *
     * @param loanId 贷款id
     * @param lpli   损益表信息（主要是总金额，和毛利率）
     */
    private void updateDeviationNetProfit(Integer loanId, LoanProfitLossInfo lpli) {
        BigDecimal grProRatio=null;                        //获取损益表毛利率
        BigDecimal businessIncomeAmount=null; //营业收入（总销售额）
        BigDecimal otherIncomeAmount=null;//其他收入
        BigDecimal fixedCostDefrayAmount=null;//固定支出
        BigDecimal texDefrayAmount=null;//所得税
        BigDecimal otherDefrayAmount=null;//其他支出
        BigDecimal zongJlrun=null;//总净利润
        BigDecimal jinglv=null; //净利率
        if(null != lpli) {
            grProRatio = lpli.getGrossProfitRatio();
            businessIncomeAmount=lpli.getBusinessIncomeAmount();
            otherIncomeAmount=lpli.getOtherIncomeAmount();
            fixedCostDefrayAmount=lpli.getFixedCostDefrayAmount();
            texDefrayAmount=lpli.getTexDefrayAmount();
            otherDefrayAmount=lpli.getOtherDefrayAmount();
        }
        if(businessIncomeAmount!=null&&businessIncomeAmount.compareTo(BigDecimal.ZERO)!=0){
            //总毛利润=总销售额*毛利率
            BigDecimal totalGrossProfitRatio=OperationUtil.multiplyByRatio(businessIncomeAmount,grProRatio);
            //期间利润（总净利润）=总毛利润+其他收入-固定支出-所得税-其他支出
            zongJlrun=OperationUtil.getTotalNetProfit(totalGrossProfitRatio,otherIncomeAmount,fixedCostDefrayAmount,texDefrayAmount,otherDefrayAmount);
            //保留一位小数
            jinglv=zongJlrun.multiply(BigDecimal.valueOf(100)).divide(businessIncomeAmount,1,BigDecimal.ROUND_HALF_UP);
    }

        //获取净利表信息（分别获取检查净利率）
        LoanCrossCheckNetProfit lccnp = crossCheckNetProfitDao.getCrossCheckNetProfitByLoanId(loanId);
        BigDecimal devRatio1 = null;   //偏差1
        BigDecimal devRatio2 = null;  //偏差2
        BigDecimal devRatio3 = null;   //偏差3
        if (lccnp != null) {
            BigDecimal ratio1 = lccnp.getCheckNetProfitRatio1();
            BigDecimal ratio2 = lccnp.getCheckNetProfitRatio2();
            BigDecimal ratio3 = lccnp.getCheckNetProfitRatio3();
            if (ratio1 != null && null != jinglv) {
                devRatio1 = OperationUtil.getDeviationRatio(jinglv, ratio1);
            }
            if (ratio2 != null && null != jinglv) {
                devRatio2 = OperationUtil.getDeviationRatio(jinglv, ratio2);
            }
            if (ratio3 != null && null != jinglv) {
                devRatio3 = OperationUtil.getDeviationRatio(jinglv, ratio3);
            }
            Map<String, Object> condition = new HashMap<String, Object>();
            condition.put("deviationRatio1", devRatio1);
            condition.put("deviationRatio2", devRatio2);
            condition.put("deviationRatio3", devRatio3);
            condition.put("loanId", loanId);
            crossCheckNetProfitDao.updateCroCheNetProDevRatio(condition);
        }
    }

    /**
     * 计算并修改交叉检验销售额表偏差率
     *
     * @param loanId
     * @param lpli
     */
    private void updateDeviationSale(Integer loanId, LoanProfitLossInfo lpli) {
        BigDecimal grProRatio=null;
        if(null != lpli) {
            grProRatio = lpli.getBusinessIncomeAmount();
        }
        //获取损益表总金额
        //获取毛利表信息（分别获取检查毛利率）
        LoanCrossCheckSale lccs = crossCheckSaleDao.getCrossCheckSaleByLoanId(loanId);
        BigDecimal devRatio1 = null;   //偏差1
        BigDecimal devRatio2 = null;  //偏差2
        BigDecimal devRatio3 = null;   //偏差3
        if (lccs != null) {
            BigDecimal ratio1 = lccs.getCheckSaleAmount1();
            BigDecimal ratio2 = lccs.getCheckSaleAmount2();
            BigDecimal ratio3 = lccs.getCheckSaleAmount3();

            if (ratio1 != null && null != grProRatio) {
                devRatio1 = OperationUtil.getDeviationRatio(grProRatio, ratio1);
            }
            if (ratio2 != null && null != grProRatio) {
                devRatio2 = OperationUtil.getDeviationRatio(grProRatio, ratio2);
            }
            if (ratio3 != null && null != grProRatio) {
                devRatio3 = OperationUtil.getDeviationRatio(grProRatio, ratio3);
            }
            Map<String, Object> condition = new HashMap<String, Object>();
            condition.put("deviationRatio1", devRatio1);
            condition.put("deviationRatio2", devRatio2);
            condition.put("deviationRatio3", devRatio3);
            condition.put("loanId", loanId);

            crossCheckSaleDao.updateCrossCheckSaleDevRatio(condition);
        }
    }

    /**
     * 计算并修改交叉检验权益表偏差率
     *
     * @param loanId 贷款id
     */
    private void updateDeviationQuanyiquan(Integer loanId, LoanAssetsInfo lai,LoanProfitLossInfo lpli) {
        BigDecimal assetsTotalAmount=null;//总资产
        BigDecimal debtsTotalAmount=null;//总负债
        BigDecimal businessIncomeAmount=null; //营业收入（总销售额）
        BigDecimal otherIncomeAmount=null;//其他收入
        BigDecimal fixedCostDefrayAmount=null;//固定支出
        BigDecimal texDefrayAmount=null;//所得税
        BigDecimal otherDefrayAmount=null;//其他支出
        BigDecimal grossProfitRatio=null;//毛利率


        if(null != lai) {
            assetsTotalAmount = lai.getAssetsTotalAmount();
            debtsTotalAmount = lai.getDebtsTotalAmount();
        }
        if(null !=  lpli) {
            grossProfitRatio=lpli.getGrossProfitRatio();
            businessIncomeAmount=lpli.getBusinessIncomeAmount();
            otherIncomeAmount=lpli.getOtherIncomeAmount();
            fixedCostDefrayAmount=lpli.getFixedCostDefrayAmount();
            texDefrayAmount=lpli.getTexDefrayAmount();
            otherDefrayAmount=lpli.getOtherDefrayAmount();

        }
        BigDecimal periodProfit = null;
        if (businessIncomeAmount!=null&&businessIncomeAmount.compareTo(BigDecimal.ZERO)!=0) {
            BigDecimal totalGrossProfitRatio=OperationUtil.multiplyByRatio(businessIncomeAmount,grossProfitRatio);//总毛利润=总销售额*毛利率

            //期间利润=总毛利润+其他收入-固定支出-所得税-其他支出
            periodProfit=OperationUtil.getTotalNetProfit(totalGrossProfitRatio,otherIncomeAmount,fixedCostDefrayAmount,texDefrayAmount,otherDefrayAmount);//期间利润（总净利润）
        }
//        else if(grossProfitRatio == null&&businessIncomeAmount!=null){
//            periodProfit=BigDecimal.valueOf(0);
//        }

        Integer loanClassId=null;
        LoanCrossCheckQuanyiquan lccq = crossCheckQuanyiquanDao.getCrossCheckQuanyiquanByLoanId(loanId);
        if(lccq==null){
            return;
        }else{
             loanClassId=lccq.getLoanClassId();
        }




        BigDecimal deservedRight = null;   //应有权益
        BigDecimal actualRight = null;  //实际权益
        BigDecimal deviation = null;   //差别
        BigDecimal deviationRatio= null;   //偏差率


        if(loanClassId==1) {
            if (lccq != null) {
                BigDecimal initRight = lccq.getInitRight();//初始权益
               /* BigDecimal periodProfit = lccq.getPeriodProfit(); // 期间利润(总利润利)*/
                BigDecimal appreciation = lccq.getAppreciation(); // 升值
                BigDecimal periodInjection = lccq.getPeriodInjection(); // 期间注资
                BigDecimal depreciation = lccq.getDepreciation();// 折旧/贬值
                BigDecimal periodExtract = lccq.getPeriodExtract(); // 期间提取


                deservedRight = OperationUtil.getDeservedRight(initRight, periodProfit, periodInjection, appreciation, periodExtract, depreciation);

                actualRight = OperationUtil.minus(assetsTotalAmount, debtsTotalAmount);

                deviation = OperationUtil.minus(actualRight, deservedRight);

                if (deviation != null && null != actualRight) {
                    deviationRatio = OperationUtil.divideByRatio(actualRight, 2, deviation);//偏差=差别/实际权益
                }
                Map<String, Object> condition = new HashMap<String, Object>();
                condition.put("periodProfit", periodProfit);
                condition.put("deservedRight", deservedRight);
                condition.put("actualRight", actualRight);
                condition.put("deviation", deviation);
                condition.put("deviationRatio", deviationRatio);
                condition.put("loanId", loanId);
                crossCheckQuanyiquanDao.updateCrossCheckQuanyiquanDevRatio(condition);
            }
        }else if(loanClassId==2) {
            BigDecimal initRight = lccq.getInitRight();//初始权益
            BigDecimal appreciation = lccq.getAppreciation(); // 升值
            BigDecimal periodInjection = lccq.getPeriodInjection(); // 期间注资
            BigDecimal depreciation = lccq.getDepreciation();// 折旧/贬值
            BigDecimal periodExtract = lccq.getPeriodExtract(); // 期间提取

            deservedRight = OperationUtil.getDeservedRight(initRight, periodInjection, appreciation, periodExtract, depreciation);


            actualRight = OperationUtil.minus(assetsTotalAmount, debtsTotalAmount);

            deviation = OperationUtil.minus(actualRight, deservedRight);

            if (deviation != null && null != actualRight) {
                deviationRatio = OperationUtil.divideByRatio(actualRight, 2, deviation);//偏差=差别/实际权益
            }
            Map<String, Object> condition = new HashMap<String, Object>();
            condition.put("periodProfit", periodProfit);//在消费类用不到
            condition.put("deservedRight", deservedRight);
            condition.put("actualRight", actualRight);
            condition.put("deviation", deviation);
            condition.put("deviationRatio", deviationRatio);
            condition.put("loanId", loanId);
            crossCheckQuanyiquanDao.updateCrossCheckQuanyiquanDevRatio(condition);
        }
    }

    /**
     * 根据贷款id从损益表获取损益情况
     *
     * @param loanId 贷款id
     */
    private LoanProfitLossInfo getProfitLossInfoByLoanId(Integer loanId) {
        return this.profitLossInfoDao.getProfitLossInfoByLoanId(loanId);
    }

    /**
     * 根据贷款id从损益表获取损益情况
     *
     * @param loanId 贷款id
     */
    private LoanAssetsInfo getLoanAssetsInfoByLoanId(Integer loanId) {
        return this.assetsInfoDao.getAssetsInfoByLoanId(loanId);
    }



}
