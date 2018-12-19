package banger.controller;

import banger.domain.enumerate.CodeDictEnum;
import banger.domain.enumerate.LoanAssetsColumnEnum;
import banger.domain.enumerate.LoanExamineType;
import banger.domain.loan.LoanExamineReview;
import banger.domain.loan.LoanExamineUser;
import banger.framework.util.DateUtil;
import banger.framework.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/7/11.
 */
public class LoanUtil {


    public static JSONArray getJsonByLoanExamine(List<LoanExamineReview> loanExamineReviews) {
        boolean random = true;
        JSONArray reviews = new JSONArray();
        for (LoanExamineReview review : loanExamineReviews) {
            List<LoanExamineUser> users = null;
            if (LoanExamineType.SELECT_USER.type.equals(review.getType().type) || !random) {
                users = review.getExamineUserList();
            } else {
                users = review.getRandomUserList();
            }
            JSONObject reviewJson = new JSONObject();
            reviewJson.put("reviewCount", review.getReviewCount());
            reviewJson.put("loanExamineType", review.getType());
            reviewJson.put("roleName", review.getRoleName());
            reviewJson.put("islimit", review.isLimit());
            if (CollectionUtils.isNotEmpty(users)) {
                JSONArray usersArray = new JSONArray();
                for (LoanExamineUser user : users) {
                    JSONObject userJson = new JSONObject();
                    userJson.put("userId", user.getUserId());
                    userJson.put("userName", user.getUserName());
                    if (user.getLimitAmount() == null || user.getLimitAmount().intValue() == 0)
                        userJson.put("limitAmount", "——");
                    else
                        userJson.put("limitAmount", user.getLimitAmount());
                    userJson.put("limitEnable", user.isLimitEnable());
                    usersArray.add(userJson);
                }
                reviewJson.put("users", usersArray);
                reviews.add(reviewJson);
            }
        }
        return reviews;
    }

    //资产负债
    //所有字段项
    public static final String LOAN_ASSETS_ITEM ="ASSETS_RECEIVABLE_AMOUNT,ASSETS_PAYMENT_AMOUNT,DEBTS_PAYABLE_AMOUNT,DEBTS_RECEIVED_AMOUNT," +
            "ASSETS_CASH_AMOUNT,ASSETS_OPERATING_AMOUNT,ASSETS_NON_OPERATING_AMOUNT,ASSETS_OTHER_AMOUNT,ASSETS_INVEST_AMOUNT,ASSETS_EXTERNAL_CLAIMS," +
            "ASSETS_ADVANCE_PAYMENT_AMOUNT,DEBTS_BIZ_OTHER_AMOUNT,DEBTS_SHORT_AMOUNT,DEBTS_LONG_AMOUNT,DEBTS_INVEST_AMOUNT,DEBTS_SELF_USER_AMOUNT,DEBTS_CONSUME_AMOUNT," +
            "DEBTS_OTHER_AMOUNT,ASSETS_FIXED_AMOUNT,ASSETS_STOCK_AMOUNT";
    //--账款ASSETS_STOCK_AMOUNT
    public static final String LOAN_ASSETS_ACCOUNT_ITEM ="ASSETS_RECEIVABLE_AMOUNT,ASSETS_PAYMENT_AMOUNT,DEBTS_PAYABLE_AMOUNT,DEBTS_RECEIVED_AMOUNT";
    //--现金
    public static final String LOAN_ASSETS_AMOUNT_ITEM ="ASSETS_CASH_AMOUNT,ASSETS_OPERATING_AMOUNT,ASSETS_NON_OPERATING_AMOUNT,ASSETS_OTHER_AMOUNT,ASSETS_INVEST_AMOUNT,ASSETS_EXTERNAL_CLAIMS,ASSETS_ADVANCE_PAYMENT_AMOUNT,DEBTS_BIZ_OTHER_AMOUNT";
    //--负债
    public static final String LOAN_ASSETS_DEBTS_ITEM ="DEBTS_SHORT_AMOUNT,DEBTS_LONG_AMOUNT,DEBTS_INVEST_AMOUNT,DEBTS_SELF_USER_AMOUNT,DEBTS_CONSUME_AMOUNT,DEBTS_OTHER_AMOUNT";
    //--固定资产
    public static final String LOAN_ASSETS_FIXED_ITEM ="ASSETS_FIXED_AMOUNT";
    //--存货
    public static final String   LOAN_ASSETS_STOCK_ITEM ="ASSETS_STOCK_AMOUNT";

    public static String codeTableParam(String columnName,String loanClassId){
        if(StringUtil.isNotEmpty(columnName)) {
            if("ASSETS_FIXED_AMOUNT".equalsIgnoreCase(columnName)){
                CodeDictEnum codeDictEnum = CodeDictEnum.valueOfColumn(columnName+loanClassId);
                if(codeDictEnum!=null){
                    return codeDictEnum.params;
                }
            }else{
                CodeDictEnum codeDictEnum = CodeDictEnum.valueOfColumn(columnName);
                if(codeDictEnum!=null){
                   return codeDictEnum.params;
                }
            }
        }
        return null;
    }

    /**
     * 格式化日期
     * @param date
     * @return
     */
    public static String format(Date date){
        if (date == null)
            return "";
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public static String getItemNameByCol(String columnName){
        LoanAssetsColumnEnum columnEnum = LoanAssetsColumnEnum.itemNameOfColumn(columnName);
        if(columnEnum != null){
            return columnEnum.itemName;
        }
        return  null;
    }

}
