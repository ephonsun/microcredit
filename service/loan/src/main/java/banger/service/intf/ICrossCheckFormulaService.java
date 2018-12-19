package banger.service.intf;

/**
 *交叉检验公式通用方法（根据毛利率获取各表偏差率）
 *
 */
public interface ICrossCheckFormulaService {


    /**
     * 同时修改损益表毛利率之后更新毛利表和净利表中的毛利净利偏差
     * @param loanId
     */
    void updateGroProAndNetProDeviation(Integer loanId);

    /**
     * 计算并修改交叉检验毛利表偏差率（用在检查毛利率发生改变时使用）
     * @param loanId 贷款id
     */
     void updateGroProDev(Integer loanId);

    /**
     * 计算并修改交叉检验净利表偏差率 （用在检查净利率发生改变时使用）
     * @param loanId 贷款id
     */
     void updateNetProDev(Integer loanId);

    /**
     * 计算并修改交叉检验销售表偏差率 （用在损益情况信息表发生改变时使用）
     * @param loanId 贷款id
     */
    void updateSaleDev(Integer loanId);

    /**
     * 计算并修改交叉检验权益表 （用在资产负责表信息表和损益情况信息表发生改变时使用）
     * @param loanId 贷款id
     */
    void updateQuanyiquanDev(Integer loanId);

}
