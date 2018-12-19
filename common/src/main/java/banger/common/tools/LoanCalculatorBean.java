package banger.common.tools;

import java.io.Serializable;

/**
 * Created by zhusiliang on 2017/6/14.
 * 用于显示贷款计算器的期数 自定义类
 */

public class LoanCalculatorBean implements Serializable {

    //当前期数
    private int resultLimit;
    //月供金额
    private double numMonthPay;
    //月供本金
    private double numMonthCapital;
    //月供利息
    private double numMonthInterest;
    //剩余本金
    private double numLeftCapital;

    public int getResultLimit() {
        return resultLimit;
    }

    public void setResultLimit(int resultLimit) {
        this.resultLimit = resultLimit;
    }

    public double getNumMonthPay() {
        return numMonthPay;
    }

    public void setNumMonthPay(double numMonthPay) {
        this.numMonthPay = numMonthPay;
    }

    public double getNumMonthCapital() {
        return numMonthCapital;
    }

    public void setNumMonthCapital(double numMonthCapital) {
        this.numMonthCapital = numMonthCapital;
    }

    public double getNumMonthInterest() {
        return numMonthInterest;
    }

    public void setNumMonthInterest(double numMonthInterest) {
        this.numMonthInterest = numMonthInterest;
    }

    public double getNumLeftCapital() {
        return numLeftCapital;
    }

    public void setNumLeftCapital(double numLeftCapital) {
        this.numLeftCapital = numLeftCapital;
    }

    @Override
    public String toString() {
        return "LoanCalculatorBean{" +
                "resultLimit=" + resultLimit +
                ", numMonthPay=" + numMonthPay +
                ", numMonthCapital=" + numMonthCapital +
                ", numMonthInterest=" + numMonthInterest +
                ", numLeftCapital=" + numLeftCapital +
                '}';
    }
}
