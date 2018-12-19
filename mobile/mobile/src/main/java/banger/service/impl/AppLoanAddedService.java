package banger.service.impl;

import banger.domain.loan.LoanInfoAddedFiles;
import banger.moduleIntf.ILoanModule;
import banger.response.AppJsonResponse;
import banger.response.CodeEnum;
import banger.service.intf.IAppLoanAddedService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/6.
 */
@Repository
public class AppLoanAddedService implements IAppLoanAddedService {
    private final String SUCCESS = "success";

    @Resource
    private ILoanModule loanModule;

    /**
     * 修改附件分类
     * @param data
     * @return
     */
    public String saveLoanAddedFileCatalog(JSONObject data) {
        String checkResult = checkJSONData(data);
        if (SUCCESS.equals(checkResult)) {
            JSONArray root = (JSONArray) data.get("data");
            JSONArray result = new JSONArray();
            List<LoanInfoAddedFiles> list = new ArrayList<LoanInfoAddedFiles>();
            for (int i = 0; i < root.size(); i++) {
                JSONObject jo = root.getJSONObject(i);
                Integer id = jo.getInt("id");
                Integer ownerId = jo.getInt("ownerId");
                Integer classId = jo.getInt("classId");

                LoanInfoAddedFiles file = new LoanInfoAddedFiles();
                file.setId(id);
                file.setOwnerId(ownerId);
                file.setClassId(classId);
                list.add(file);
            }
            loanModule.getLoanAddedProvider().saveLoanAddedFileCatalog(list);
            return AppJsonResponse.success(result);
        }else{
            return checkResult;
        }
    }

    private String checkJSONData(JSONObject data){
        if(data.get("data") instanceof JSONArray){
            return SUCCESS;
        }
        return AppJsonResponse.fail(CodeEnum.CODE_110,"参数不能为空！");
    }
}
