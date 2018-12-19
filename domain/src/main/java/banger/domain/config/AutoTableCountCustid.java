package banger.domain.config;

/**
 * Created by XUElw on 2017/10/19.
 * 统计导入的客户表中客户id是否有重复
 */
public class AutoTableCountCustid {
    private Integer count;
    private Integer custId;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }
}
