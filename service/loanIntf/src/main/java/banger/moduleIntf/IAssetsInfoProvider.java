package banger.moduleIntf;

import banger.domain.loan.LoanAssetsInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/24.
 */
public interface IAssetsInfoProvider {


    /**
     * 查询资产负债表信息表
     * @param condition 查询条件
     * @return
     */
    LoanAssetsInfo queryOneAssetsInfoList(Map<String, Object> condition);
}
