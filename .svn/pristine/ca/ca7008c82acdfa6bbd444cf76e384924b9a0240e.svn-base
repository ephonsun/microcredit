package banger.common.tools;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by zhusiliang on 2017/6/14.
 */

public class CalculateUtils  {
    /**
     * 等额本息
     * @param money 贷款金额
     * @param resultRatio 利率
     * @param resultLimit 期限
     * @return
     */
    public static List<LoanCalculatorBean> calculatorBeanList(double money,double resultRatio,int resultLimit){
//        double monthInterest=resultRatio/12/100;
        double numMonthPay=0;
        List<LoanCalculatorBean> list=new ArrayList<LoanCalculatorBean>();
        for (int i = 0; i <resultLimit ; i++) {
            LoanCalculatorBean bean = new LoanCalculatorBean();
            //月供
            if(numMonthPay==0){
                numMonthPay=Math.pow((1+resultRatio),resultLimit)*resultRatio/(Math.pow((1+resultRatio),resultLimit)-1)*money;
            }
            //月供利息
            double numMonthInterest=money*resultRatio;
            //月供本金
            double numMonthCapital=numMonthPay-numMonthInterest;
            //剩余本金
            money=money-numMonthCapital;
            //给相关的bean设置参数
            bean.setResultLimit(i+1);
            bean.setNumMonthPay(numMonthPay);
            bean.setNumMonthCapital(numMonthCapital);
            bean.setNumMonthInterest(numMonthInterest);
            bean.setNumLeftCapital(money);
            list.add(bean);
            //System.out.println("calculatorBeanList: "+ bean.toString());

        }
        return list;
    }

    /**
     * 等额本金
     * @param money 贷款金额
     * @param resultRatio 利率
     * @param resultLimit 期限
     * @return
     */
    public static List<LoanCalculatorBean> calculatorBeanList2(double money,double resultRatio,int resultLimit){
//        double monthInterest=resultRatio/12/100;
        //月供本金
        double numMonthCapital=0;
        List<LoanCalculatorBean>  list=new ArrayList<LoanCalculatorBean>();
        for (int i = 0; i <resultLimit ; i++) {
            LoanCalculatorBean bean=new LoanCalculatorBean();
            //月供本金只算一次
            if(numMonthCapital==0){
                numMonthCapital=money/resultLimit;
            }
            //月供利息
            double numMonthInterest=money*resultRatio;
            //月供金额
            double numMonthPay=numMonthCapital+numMonthInterest;

            //剩余本金
            money=money-numMonthCapital;
            //给相关的bean设置参数
            bean.setResultLimit(i + 1);
            bean.setNumMonthPay(numMonthPay);
            bean.setNumMonthCapital(numMonthCapital);
            bean.setNumMonthInterest(numMonthInterest);
            bean.setNumLeftCapital(money);
            list.add(bean);
            //System.out.println("calculatorBeanList: "+ bean.toString());
        }
        return list;
    }

    public static String  getDoubleTwo(double num){
        BigDecimal bg=new BigDecimal(num).setScale(2, BigDecimal.ROUND_HALF_UP);
        return ""+bg.doubleValue();
    }

    public static void main(String[] args) {
//        calculatorBeanList(10000, 4.35, 12);
//        calculatorBeanList2(10000, 4.35, 12);
    }

}
