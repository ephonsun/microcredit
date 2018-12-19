package banger.service.intf;

import banger.domain.config.AutoTableCountCustid;

import java.util.List;

/**
 * 生成导入客户信息存储过程
 * Created by XUElw on 2017/10/10.
 */
public interface IProcedureService {
    /**
     * 生成新增存储过程
     */
    StringBuffer generateInsertProcedure();

    /**
     * 生成更新存储过程
     * @return
     */
    StringBuffer generateUpdateProcedure();

    /**
     * 统计客户导入表中重复的记录
     * @return
     */
    void countAutoTableCustId();
}
