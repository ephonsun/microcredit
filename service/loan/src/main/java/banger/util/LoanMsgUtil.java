package banger.util;

import com.sun.jndi.toolkit.url.UrlUtil;
import net.sf.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static banger.util.HttpUtils.postRequest;

public class LoanMsgUtil {

    //陈xx（客户经理）12:25 申请一笔20元的经营性贷款，客户为：小燕子，18743086992，提交给陈诚（团队主管）分配
    //oper operName  operRole amount customerName customerMobile superName superRole
    public static String sendLoanMsg(String operType, String operName, String operRole, double amount, String customerName, String customerMobile, String superName, String superRole, Integer loanTypeId, String tpUrl){

        //1申请提交 2分配 3调查提交 4一审提交 5二审提交 6合同提交 7合同签订 8放款 9授权 10放款完成
        JSONObject jo = new JSONObject();
        Map<String, String> map = new HashMap<String, String>();
        String returnStr = null;
        try {
            jo.put("operType", UrlUtil.encode(operType,"UTF-8"));
            jo.put("operName", UrlUtil.encode(operName,"UTF-8"));
            jo.put("operRole", UrlUtil.encode(operRole,"UTF-8"));
            jo.put("amount", amount);
            jo.put("customerName", UrlUtil.encode(customerName,"UTF-8"));
            jo.put("customerMobile", UrlUtil.encode(customerMobile,"UTF-8"));
            jo.put("superName", UrlUtil.encode(superName,"UTF-8"));
            jo.put("superRole", UrlUtil.encode(superRole,"UTF-8"));
            if(loanTypeId==1){
                jo.put("loanTypeName", UrlUtil.encode("消费贷","UTF-8"));
            }else if(loanTypeId==2){
                jo.put("loanTypeName", UrlUtil.encode("经营贷","UTF-8"));
            }
            map.put("msg",jo.toString());
            returnStr = postRequest(tpUrl, map);
            System.out.println("-------------------"+returnStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnStr;

    }


    public static void main(String[] args) {
        System.out.println(getTime());
    }

    public static String getTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(new Date());
    }

}
