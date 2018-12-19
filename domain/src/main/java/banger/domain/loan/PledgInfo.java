package banger.domain.loan;

import java.math.BigDecimal;

/**
 * 抵质押物补录字段
 */
public class PledgInfo {
    private Integer id;
    private String txqzh;//他项权证号
    private String qsdjjg;//权属登记机关
    private String djrq;//登记日期
    private String djjg;//登记机构
    private String djr;//登记人
    private String tbxz;//投保险种
    private String dbbh;//保单编号
    private BigDecimal dbje;//保险金额
    private String dbgsmc;//保险公司名称
    private String tbrq;//投保日期
    private String tbdqrq;//保险到期日期

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTxqzh() {
        return txqzh;
    }

    public void setTxqzh(String txqzh) {
        this.txqzh = txqzh;
    }

    public String getQsdjjg() {
        return qsdjjg;
    }

    public void setQsdjjg(String qsdjjg) {
        this.qsdjjg = qsdjjg;
    }

    public String getDjrq() {
        return djrq;
    }

    public void setDjrq(String djrq) {
        this.djrq = djrq;
    }

    public String getDjjg() {
        return djjg;
    }

    public void setDjjg(String djjg) {
        this.djjg = djjg;
    }

    public String getDjr() {
        return djr;
    }

    public void setDjr(String djr) {
        this.djr = djr;
    }

    public String getTbxz() {
        return tbxz;
    }

    public void setTbxz(String tbxz) {
        this.tbxz = tbxz;
    }

    public String getDbbh() {
        return dbbh;
    }

    public void setDbbh(String dbbh) {
        this.dbbh = dbbh;
    }

    public BigDecimal getDbje() {
        return dbje;
    }

    public void setDbje(BigDecimal dbje) {
        this.dbje = dbje;
    }

    public String getDbgsmc() {
        return dbgsmc;
    }

    public void setDbgsmc(String dbgsmc) {
        this.dbgsmc = dbgsmc;
    }

    public String getTbrq() {
        return tbrq;
    }

    public void setTbrq(String tbrq) {
        this.tbrq = tbrq;
    }

    public String getTbdqrq() {
        return tbdqrq;
    }

    public void setTbdqrq(String tbdqrq) {
        this.tbdqrq = tbdqrq;
    }
}
