package banger.service.intf;

import net.sf.json.JSONObject;

/**
 * Created by zhusw on 2017/6/6.
 */
public interface IAppLoanAddedService {

    /**
     * 修改附件分类
     * @param data
     * @return
     */
    String saveLoanAddedFileCatalog(JSONObject data);

}
