package banger.domain.config;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/12.
 * 导出用表
 */
public class AutoTableColumnQuery extends  AutoTableColumn implements Serializable {
    private static final long serialVersionUID = -1088090862743521826L;
    private  String tableDisplayName;

    public String getTableDisplayName() {
        return tableDisplayName;
    }

    public void setTableDisplayName(String tableDisplayName) {
        this.tableDisplayName = tableDisplayName;
    }
}
