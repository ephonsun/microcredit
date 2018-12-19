package banger.common.tools;

import banger.domain.loan.LoanRepayPlanInfoExtend;
import banger.framework.util.DateUtil;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by kangbiao on 2017/11
 * 银行利息都是使用日利计算 比如贷款年利10% 那日利就是 10/100/360
 */

public class ZSCalculateBeanUtils {

    private final static BigDecimal big0 = new BigDecimal(0);
    private final static BigDecimal big1 = new BigDecimal(1);
    private final static BigDecimal big12 = new BigDecimal(12);
    private final static BigDecimal big30 = new BigDecimal(30);
    private final static BigDecimal big100 = new BigDecimal(100);
    private final static BigDecimal big360 = new BigDecimal(360);


    /**db
     * 等额本金  下月还款 首月末月按日计息
     * @param money 贷款金额
     * @param ratio 年利率 %
     * @param limit 期限
     * @param loanEndDate
     * @return
     */
    public static List<ZSCalculatorBean> calculatorBeanList0(BigDecimal money, BigDecimal ratio, int limit, Date beginDate, String loanEndDate, int day){
        if(null==beginDate){
            beginDate = new Date();
        }
        //日利率 360
        BigDecimal dayR = big0;

        //每月还款额
        BigDecimal numMonthPay ;
        //计息天数
        BigDecimal numDays;
        //还款日期
        Date repayDate = new Date();
        //月利息
        BigDecimal numMonthInterest = big0;
        //月本金
        BigDecimal numMonthCapital = big0;

        List<ZSCalculatorBean> list=new ArrayList<ZSCalculatorBean>();
        ZSCalculatorBean bean;
        for (int i = 0; i < limit; i++) {

            bean = new ZSCalculatorBean();
            numDays = big30;


            if(i==0){//第一期计算计息天数
                repayDate = getFirstRepayDate(beginDate, day, false);
                numDays = getBeginEndDay(beginDate, repayDate);
            }else if (i == limit - 1) {//最后一期计算计息天数
                Date tempDate = repayDate;
                if(StringUtils.isNotBlank(loanEndDate)){
                    repayDate = DateUtil.parser(loanEndDate, DateUtil.DEFAULT_DATE_FORMAT);
                }else{
                    repayDate = getLastRepayDateByLimit(beginDate, limit);
                }
                numDays = getBeginEndDay(tempDate,repayDate);
                numMonthCapital = money;
            } else {//后面每期都是
                repayDate = getNextRepayDateByi(repayDate, day,1);
            }

            bean.setSortNo(i+1);//当前期数
            bean.setNumMonthPay(numMonthCapital.add(numMonthInterest)); //月供金额
            bean.setNumMonthCapital(numMonthCapital);//月供本金
            bean.setNumMonthInterest(numMonthInterest);//月供利息
            bean.setNumLeftCapital(money);//剩余本金
            bean.setNumDays(numDays.intValue());//计息天数
            bean.setRepayDate(repayDate);//应还款日期
            list.add(bean);

        }
        return list;
    }


    /**db
     * 等额本金  下月还款 首月末月按日计息
     * @param money 贷款金额
     * @param ratio 年利率 %
     * @param limit 期限
     * @param loanEndDate
     * @return
     */
    public static List<ZSCalculatorBean> calculatorBeanList1(BigDecimal money, BigDecimal ratio, int limit, Date beginDate, String loanEndDate, int day){
        if(null==beginDate){
            beginDate = new Date();
        }
        //日利率 360
        BigDecimal dayR = ratio.divide(big100, 20, BigDecimal.ROUND_CEILING).divide(big360, 20, BigDecimal.ROUND_CEILING);

        //每月还款额
        BigDecimal numMonthPay ;
        //计息天数
        BigDecimal numDays;
        //还款日期
        Date repayDate = new Date();
        //月利息
        BigDecimal numMonthInterest;
        //月本金
        BigDecimal numMonthCapital = money.divide(new BigDecimal(limit),2,BigDecimal.ROUND_HALF_UP);

        List<ZSCalculatorBean> list=new ArrayList<ZSCalculatorBean>();
        ZSCalculatorBean bean;
        for (int i = 0; i < limit; i++) {

            bean = new ZSCalculatorBean();
            numDays = big30;
            //30天利息 = 总本金*日利*天数
            numMonthInterest = money.multiply(dayR).multiply(numDays).setScale(2,BigDecimal.ROUND_HALF_UP);

            if(i==0){//第一期计算计息天数
                repayDate = getFirstRepayDate(beginDate, day, true);
                numDays = getBeginEndDay(beginDate, repayDate);
                //实际利息 = 总本金*日利*天数
                numMonthInterest = money.multiply(dayR).multiply(numDays).setScale(2,BigDecimal.ROUND_HALF_UP);
            }else if (i == limit - 1) {//最后一期计算计息天数
                Date tempDate = repayDate;
                if(StringUtils.isNotBlank(loanEndDate)){
                    repayDate = DateUtil.parser(loanEndDate, DateUtil.DEFAULT_DATE_FORMAT);
                }else{
                    repayDate = getLastRepayDateByLimit(beginDate, limit);
                }
                numDays = getBeginEndDay(tempDate,repayDate);
                //实际利息 = 总本金*日利*天数
                numMonthInterest = money.multiply(dayR).multiply(numDays).setScale(2, BigDecimal.ROUND_HALF_UP);
                numMonthCapital = money;
            } else {//后面每期都是
                repayDate = getNextRepayDateByi(repayDate, day,1);
            }


            //总本金 = 总本金-月本金
            money = money.subtract(numMonthCapital);

            bean.setSortNo(i+1);//当前期数
            bean.setNumMonthPay(numMonthCapital.add(numMonthInterest)); //月供金额
            bean.setNumMonthCapital(numMonthCapital);//月供本金
            bean.setNumMonthInterest(numMonthInterest);//月供利息
            bean.setNumLeftCapital(money);//剩余本金
            bean.setNumDays(numDays.intValue());//计息天数
            bean.setRepayDate(repayDate);//应还款日期
            list.add(bean);

        }
        return list;
    }


    /**
     * 等额本息 下月还款 首月末月按日计息
     * @param money 贷款金额
     * @param ratio 年利率 %
     * @param limit 期限
     * @param loanEndDate
     * @return
     */
    public static List<ZSCalculatorBean> calculatorBeanList2(BigDecimal money, BigDecimal ratio, int limit, Date beginDate, String loanEndDate, int day){
        if(null==beginDate){
            beginDate = new Date();
        }
        //日利率 360
        BigDecimal dayR = ratio.divide(big100, 20, BigDecimal.ROUND_CEILING).divide(big360, 20, BigDecimal.ROUND_CEILING);
        //月利率 12
        BigDecimal mouthR = ratio.divide(big100, 20, BigDecimal.ROUND_CEILING).divide(big12, 20, BigDecimal.ROUND_CEILING);

        //每月还款额=贷款本金×[月利率×(1+月利率) ^ 还款月数]÷{[(1+月利率) ^ 还款月数]-1}
        BigDecimal numMonthPay = money.multiply(mouthR.multiply(mouthR.add(big1).pow(limit))).divide(mouthR.add(big1).pow(limit).subtract(big1),2,BigDecimal.ROUND_HALF_UP);

        //计息天数
        BigDecimal numDays;
        //还款日期
        Date repayDate = new Date();
        //月利息
        BigDecimal numMonthInterest;
        //月本金
        BigDecimal numMonthCapital;

        List<ZSCalculatorBean> list=new ArrayList<ZSCalculatorBean>();
        ZSCalculatorBean bean;
        for (int i = 0; i < limit; i++) {

            bean = new ZSCalculatorBean();
            numDays = big30;
            //30天利息 = 总本金*日利*天数
            numMonthInterest = money.multiply(dayR).multiply(numDays).setScale(2,BigDecimal.ROUND_HALF_UP);
            //月本金 = 等额本息-利息
            numMonthCapital = numMonthPay.subtract(numMonthInterest);

            if(i==0){//第一期计算计息天数
                repayDate = getFirstRepayDate(beginDate, day, true);
                numDays = getBeginEndDay(beginDate, repayDate);
                //实际利息 = 总本金*日利*天数
                numMonthInterest = money.multiply(dayR).multiply(numDays).setScale(2,BigDecimal.ROUND_HALF_UP);
            }else if (i == limit - 1) {//最后一期计算计息天数
                Date tempDate = repayDate;
                if(StringUtils.isNotBlank(loanEndDate)){
                    repayDate = DateUtil.parser(loanEndDate, DateUtil.DEFAULT_DATE_FORMAT);
                }else{
                    repayDate = getLastRepayDateByLimit(beginDate, limit);
                }
                numDays = getBeginEndDay(tempDate,repayDate);
                //实际利息 = 总本金*日利*天数
                numMonthInterest = money.multiply(dayR).multiply(numDays).setScale(2, BigDecimal.ROUND_HALF_UP);
                numMonthCapital = money;
            } else {//后面每期都是
                repayDate = getNextRepayDateByi(repayDate, day, 1);
            }


            //总本金 = 总本金-月本金
            money = money.subtract(numMonthCapital);

            bean.setSortNo(i+1);//当前期数
            bean.setNumMonthPay(numMonthCapital.add(numMonthInterest)); //月供金额
            bean.setNumMonthCapital(numMonthCapital);//月供本金
            bean.setNumMonthInterest(numMonthInterest);//月供利息
            bean.setNumLeftCapital(money);//剩余本金
            bean.setNumDays(numDays.intValue());//计息天数
            bean.setRepayDate(repayDate);//应还款日期
            list.add(bean);

        }
        return list;
    }



    /**
     * 按月还息 到期还本  的当月还款 首月末月按日计息
     * @param money 贷款金额
     * @param ratio 年利率 %
     * @param limit 期限
     * @param loanEndDate
     * @return
     */
    public static List<ZSCalculatorBean> calculatorBeanList3(BigDecimal money, BigDecimal ratio, int limit, Date beginDate, String loanEndDate, int day){
        if(null==beginDate){
            beginDate = new Date();
        }
        //日利率 360
        BigDecimal dayR = ratio.divide(big100, 20, BigDecimal.ROUND_CEILING).divide(big360, 20, BigDecimal.ROUND_CEILING);

        //每月还款额
        BigDecimal numMonthPay ;
        //计息天数
        BigDecimal numDays;
        //还款日期
        Date repayDate = new Date();
        //月利息
        BigDecimal numMonthInterest;
        //月本金
        BigDecimal numMonthCapital = big0;

        List<ZSCalculatorBean> list=new ArrayList<ZSCalculatorBean>();
        ZSCalculatorBean bean;
        for (int i = 0; i < limit; i++) {

            bean = new ZSCalculatorBean();
            numDays = big30;
            //30天利息 = 总本金*日利*天数
            numMonthInterest = money.multiply(dayR).multiply(numDays).setScale(2,BigDecimal.ROUND_HALF_UP);

            if(i==0){//第一期计算计息天数
                repayDate = getFirstRepayDate(beginDate, day, false);
                numDays = getBeginEndDay(beginDate, repayDate);
                //实际利息 = 总本金*日利*天数
                numMonthInterest = money.multiply(dayR).multiply(numDays).setScale(2,BigDecimal.ROUND_HALF_UP);
            }else if (i == limit - 1) {//最后一期计算计息天数
                Date tempDate = repayDate;
                if(StringUtils.isNotBlank(loanEndDate)){
                    repayDate = DateUtil.parser(loanEndDate, DateUtil.DEFAULT_DATE_FORMAT);
                }else{
                    repayDate = getLastRepayDateByLimit(beginDate, limit);
                }
                numDays = getBeginEndDay(tempDate,repayDate);
                //实际利息 = 总本金*日利*天数
                numMonthInterest = money.multiply(dayR).multiply(numDays).setScale(2, BigDecimal.ROUND_HALF_UP);
                numMonthCapital = money;
            } else {//后面每期都是
                repayDate = getNextRepayDateByi(repayDate, day,1);
            }


            //总本金 = 总本金-月本金
            money = money.subtract(numMonthCapital);

            bean.setSortNo(i+1);//当前期数
            bean.setNumMonthPay(numMonthCapital.add(numMonthInterest)); //月供金额
            bean.setNumMonthCapital(numMonthCapital);//月供本金
            bean.setNumMonthInterest(numMonthInterest);//月供利息
            bean.setNumLeftCapital(money);//剩余本金
            bean.setNumDays(numDays.intValue());//计息天数
            bean.setRepayDate(repayDate);//应还款日期
            list.add(bean);

        }
        return list;
    }



    /**
     * 一次性还本付息  下月还款 首月末月按日计息
     * @param money 贷款金额
     * @param ratio 年利率 %
     * @param limit 期限
     * @param loanEndDate
     * @return
     */
    public static List<ZSCalculatorBean> calculatorBeanList4(BigDecimal money, BigDecimal ratio, int limit, Date beginDate, String loanEndDate, int day){
        if(null==beginDate){
            beginDate = new Date();
        }
        //日利率 360
        BigDecimal dayR = ratio.divide(big100, 20, BigDecimal.ROUND_CEILING).divide(big360, 20, BigDecimal.ROUND_CEILING);

        //每月还款额
        BigDecimal numMonthPay ;
        //计息天数
        BigDecimal numDays;
        //还款日期
        Date repayDate = new Date();
        //月利息
        BigDecimal numMonthInterest;
        //月本金
        BigDecimal numMonthCapital = big0;

        List<ZSCalculatorBean> list=new ArrayList<ZSCalculatorBean>();
        ZSCalculatorBean bean;
        for (int i = 0; i < limit; i++) {

             if (i == limit - 1) {//最后一期计算计息天数
                //还款日期
                 Calendar ca = Calendar.getInstance();
                 ca.setTime(beginDate);
                 if(StringUtils.isNotBlank(loanEndDate)){
                     repayDate = DateUtil.parser(loanEndDate, DateUtil.DEFAULT_DATE_FORMAT);
                 }else{
                     repayDate = getLastRepayDateByLimit(beginDate, limit);
                 }
                 //计息天数
                 numDays = getBeginEndDay(beginDate,repayDate);
                 //月利息
                 numMonthInterest = money.multiply(dayR).multiply(numDays).setScale(2,BigDecimal.ROUND_HALF_UP);
                 //月本金
                 numMonthCapital = money;

                 bean = new ZSCalculatorBean();

                 bean.setSortNo(i+1);//当前期数
                 bean.setNumMonthPay(numMonthCapital.add(numMonthInterest)); //月供金额
                 bean.setNumMonthCapital(numMonthCapital);//月供本金
                 bean.setNumMonthInterest(numMonthInterest);//月供利息
                 bean.setNumLeftCapital(big0);//剩余本金
                 bean.setNumDays(numDays.intValue());//计息天数
                 bean.setRepayDate(repayDate);//应还款日期
            }else{
                 bean = new ZSCalculatorBean();
                 numDays = big30;
                 //30天利息 = 总本金*日利*天数

                 if(i==0){//第一期计算计息天数
                     repayDate = getFirstRepayDate(beginDate, day, false);
                     numDays = getBeginEndDay(beginDate, repayDate);
                     //实际利息 = 总本金*日利*天数
                 } else {//后面每期都是
                     repayDate = getNextRepayDateByi(repayDate, day,1);
                 }

                 bean.setSortNo(i + 1);//当前期数
                 bean.setNumMonthPay(big0); //月供金额
                 bean.setNumMonthCapital(big0);//月供本金
                 bean.setNumMonthInterest(big0);//月供利息
                 bean.setNumLeftCapital(money);//剩余本金
                 bean.setNumDays(0);//计息天数
                 bean.setRepayDate(repayDate);//应还款日期
             }

            list.add(bean);


        }
        return list;
    }


    public static List<LoanRepayPlanInfoExtend> calculatorBeanListDate(List<LoanRepayPlanInfoExtend> loanRepayPlanInfos, BigDecimal ratio, Date beginDate, String loanEndDate, Integer day, String repaymentMode, int limit) {

        if(null==beginDate){
            beginDate = new Date();
        }
        //日利率 360
        BigDecimal dayR = ratio.divide(big100, 20, BigDecimal.ROUND_CEILING).divide(big360, 20, BigDecimal.ROUND_CEILING);

        //每月还款额
        BigDecimal numMonthPay ;
        //计息天数
        BigDecimal numDays;
        //还款日期
        Date repayDate = new Date();
        //月利息
        BigDecimal numMonthInterest;
        //月本金
        BigDecimal numMonthCapital = big0;

        for (int i = 0; i < loanRepayPlanInfos.size(); i++) {

            numDays = big30;
            numMonthInterest = big0;
            //30天利息 = 总本金*日利*天数
            if(null!=loanRepayPlanInfos.get(i).getLoanAccrualBalanceAmount()){
                numMonthInterest = loanRepayPlanInfos.get(i).getLoanAccrualBalanceAmount().multiply(dayR).multiply(numDays).setScale(2,BigDecimal.ROUND_HALF_UP);
            }

            if(i==0){//第一期计算计息天数
                if(repaymentMode.equals("1")||repaymentMode.equals("2")){
                    repayDate = getFirstRepayDate(beginDate, day, true);
                }else{
                    repayDate = getFirstRepayDate(beginDate, day, false);
                }
                numDays = getBeginEndDay(beginDate, repayDate);
                //实际利息 = 总本金*日利*天数
            }else if (i == loanRepayPlanInfos.size() - 1) {//最后一期计算计息天数
                Date tempDate = repayDate;
                if(StringUtils.isNotBlank(loanEndDate)){
                    repayDate = DateUtil.parser(loanEndDate, DateUtil.DEFAULT_DATE_FORMAT);
                }else{
                    repayDate = getLastRepayDateByLimit(beginDate, limit);
                }
                numDays = getBeginEndDay(tempDate,repayDate);
            } else {//后面每期都是
                repayDate = getNextRepayDateByi(repayDate, day,1);
            }

            loanRepayPlanInfos.get(i).setLoanRepayDate(repayDate);
            loanRepayPlanInfos.get(i).setLoanAccrualDays(numDays.intValue());

//            if(null==loanRepayPlanInfos.get(i).getLoanAccrualAmount()){
                loanRepayPlanInfos.get(i).setLoanAccrualAmount(numMonthInterest);
//            }
            if(null==loanRepayPlanInfos.get(i).repayment){
                loanRepayPlanInfos.get(i).setRepayment(loanRepayPlanInfos.get(i).getLoanPrincipalAmount().add(loanRepayPlanInfos.get(i).getLoanAccrualAmount()));
            }

        }

        return  loanRepayPlanInfos;
    }

    /**
     * 一次性还本付息  下月还款 首月末月按日计息
     * @param money 贷款金额
     * @param ratio 年利率 %
     * @param limit 期限
     * @param limit 起息日
     * @param limit 每月还款日
     * @return
     */
//    public static ZSCalculatorBean calculatorBeanList4(BigDecimal money, BigDecimal ratio, int limit, Date beginDate, int day){
//
//        //日利率 360
//        BigDecimal dayR = ratio.divide(big100, 20, BigDecimal.ROUND_CEILING).divide(big360, 20, BigDecimal.ROUND_CEILING);
//
//        //还款日期
//        Calendar ca = Calendar.getInstance();
//        ca.setTime(beginDate);
//        Date repayDate = getNextRepayDateByi(beginDate,ca.get(Calendar.DAY_OF_MONTH), limit);;
//        //计息天数
//        BigDecimal numDays = getBeginEndDay(beginDate,repayDate);
//        //月利息
//        BigDecimal numMonthInterest = money.multiply(dayR).multiply(numDays).setScale(2,BigDecimal.ROUND_HALF_UP);
//        //月本金
//        BigDecimal numMonthCapital = money;
//
//        ZSCalculatorBean bean = new ZSCalculatorBean();
//
//        bean.setSortNo(1);//当前期数
//        bean.setNumMonthPay(numMonthCapital.add(numMonthInterest)); //月供金额
//        bean.setNumMonthCapital(numMonthCapital);//月供本金
//        bean.setNumMonthInterest(numMonthInterest);//月供利息
//        bean.setNumLeftCapital(money);//剩余本金
//        bean.setNumDays(numDays.intValue());//计息天数
//        bean.setRepayDate(repayDate);//应还款日期
//
//
//        return bean;
//    }

    public static String  getDoubleTwo(double num){
        BigDecimal bg=new BigDecimal(num).setScale(2, BigDecimal.ROUND_HALF_UP);
        return ""+bg.doubleValue();
    }


    private static Date getFirstRepayDate(Date beginDate, int day, boolean isNext) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(beginDate);

        if (isNext) {
            ca.set(Calendar.MONTH, ca.get(Calendar.MONTH) + 1);
        }else if(ca.get(Calendar.DAY_OF_MONTH)>=day){
            ca.set(Calendar.MONTH, ca.get(Calendar.MONTH) + 1);
        }
        int maxDay = ca.getActualMaximum(Calendar.DAY_OF_MONTH);
        if(day>maxDay){
            day = maxDay;
        }
        ca.set(Calendar.DAY_OF_MONTH,day);
        return ca.getTime();

    }

    private static Date getNextRepayDateByi(Date beginDate,int day,int i) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(beginDate);
        ca.set(Calendar.MONTH, ca.get(Calendar.MONTH) + i);

        int maxDay = ca.getActualMaximum(Calendar.DAY_OF_MONTH);
        if(day>maxDay){
            day = maxDay;
        }
        ca.set(Calendar.DAY_OF_MONTH,day);
        return ca.getTime();
    }


    //夸月按照30天来算
    public static BigDecimal getBeginEndDay(Date begin,Date end)
    {
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(begin);
//        long time1 = cal.getTimeInMillis();
//        cal.setTime(end);
//        long time2 = cal.getTimeInMillis();
//        long between_days=(time2-time1)/(1000*3600*24);

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(begin);
        int month1 = cal1.get(Calendar.MONTH);
        int day1 = cal1.get(Calendar.DAY_OF_MONTH);
        int year1 = cal1.get(Calendar.YEAR);


        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(end);
        int month2 = cal2.get(Calendar.MONTH);
        int day2 = cal2.get(Calendar.DAY_OF_MONTH);
        int year2 = cal2.get(Calendar.YEAR);


        return new BigDecimal((year2-year1)*360+(month2-month1)*30+(day2-day1));
    }

    private static Date getLastRepayDateByLimit(Date beginDate, int limit) {
        //
        Calendar beginCa = Calendar.getInstance();
        beginCa.setTime(beginDate);
        int day = beginCa.get(Calendar.DAY_OF_MONTH);
        //
        Calendar repayCa = Calendar.getInstance();
        repayCa.setTime(beginDate);
        repayCa.set(Calendar.MONTH,repayCa.get(Calendar.MONTH)+limit);

        int maxDay = repayCa.getActualMaximum(Calendar.DAY_OF_MONTH);
        if(day>maxDay){
            day = maxDay;
        }
        repayCa.set(Calendar.DAY_OF_MONTH,day);
        //
        return repayCa.getTime();

    }

//    private static Date getLastRepayDate(Date beginDate, Date repayDate) {
//        Date tempDate = repayDate;
//        //
//        Calendar beginCa = Calendar.getInstance();
//        beginCa.setTime(beginDate);
//        int day = beginCa.get(Calendar.DAY_OF_MONTH);
//        //
//        Calendar repayCa = Calendar.getInstance();
//        repayCa.setTime(repayDate);
//        repayCa.set(Calendar.MONTH,repayCa.get(Calendar.MONTH)+1);
//
//        int maxDay = repayCa.getActualMaximum(Calendar.DAY_OF_MONTH);
//        if(day>maxDay){
//            day = maxDay;
//        }
//        repayCa.set(Calendar.DAY_OF_MONTH,day);
//        //
//        return repayCa.getTime();
//
//    }

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

//        等额本金
        List<ZSCalculatorBean> beans = calculatorBeanList1(new BigDecimal(10000), new BigDecimal(10), 12, sdf.parse("2018-02-06"), "", 21);
//        等额本息
//        List<ZSCalculatorBean> beans = calculatorBeanList2(new BigDecimal(1200.00), new BigDecimal(10), 12, sdf.parse("2018-02-06"), 21);
//        按月还息 到期还本
//        List<ZSCalculatorBean> beans = calculatorBeanList3(new BigDecimal(3000000), new BigDecimal(6.4125), 60, sdf.parse("2017-11-24"), 21);

//        一次性还本付息
//        List<ZSCalculatorBean> beans = calculatorBeanList4(new BigDecimal(3000000), new BigDecimal(6.4125), 60, sdf.parse("2017-11-24"), 21);
//        List<ZSCalculatorBean> beans = new ArrayList<ZSCalculatorBean>();
//        beans.add(bean);

        for (int i = 0; i < beans.size(); i++) {
            System.out.print(beans.get(i).getSortNo()+"    ");
            System.out.print(sdf.format(beans.get(i).getRepayDate())+"    ");
            System.out.print(beans.get(i).getNumDays()+"    ");
            System.out.print(beans.get(i).getNumMonthPay()+"    ");
            System.out.print(beans.get(i).getNumMonthCapital()+"    ");
            System.out.print(beans.get(i).getNumMonthInterest()+"    ");
            System.out.println(beans.get(i).getNumLeftCapital());
        }


//        System.out.println(sdf.format(getNextRepayDate(new Date(), 31)));
//
//        try {
//            System.out.println(getBeginEndDay( sdf.parse("2017-11-24"),sdf.parse("2017-12-21")));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

    }



}
