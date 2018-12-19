package banger.controller;

import banger.common.BaseController;
import banger.common.interceptor.NoLoginInterceptor;
import banger.domain.config.IntoLoanUse;
import banger.domain.config.IntoTemplatesFieldQuery;
import banger.domain.html5.IntoCustApplyInfo;
import banger.domain.html5.IntoFileInfo;
import banger.domain.html5.IntoIntefaceRecord;
import banger.domain.html5.IntoPhoneMsgCode;
import banger.domain.system.SysBasicConfig;
import banger.framework.util.DateUtil;
import banger.framework.web.servlet.ServletContextHolder;
import banger.moduleIntf.IBasicConfigProvider;
import banger.moduleIntf.ILoanUseProvider;
import banger.moduleIntf.ITemplatesFieldProvider;
import banger.service.intf.ICustApplyInfoService;
import banger.service.intf.IFileInfoService;
import banger.service.intf.IIntefaceRecordService;
import banger.service.intf.IPhoneMsgCodeService;
import banger.util.SendSmsUtil;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by huangYi on 2017/10/16
 **/
@Controller
@RequestMapping("/api")
public class HtmlIntoController extends BaseController{
    @Autowired
    private IBasicConfigProvider basicConfigProvider;
    @Autowired
    private ITemplatesFieldProvider templatesFieldProvider;
    @Autowired
    private ILoanUseProvider loanUseProvider;
    @Autowired
    private IPhoneMsgCodeService phoneMsgCodeService;
    @Autowired
    private IIntefaceRecordService iIntefaceRecordService;
    @Autowired
    private IFileInfoService fileInfoService;
    @Autowired
    private ICustApplyInfoService custApplyInfoService;
    /**
     * 第一步进入首页》》判断短信验证是否开启
     * @return
     */
    @NoLoginInterceptor
    @RequestMapping("/getIndexPage")
    public String getIndexPage(HttpServletRequest request, HttpServletResponse response){
        SysBasicConfig config = basicConfigProvider.getSysBasicConfigByKey("dxyz");
        //短信验证 1开启，2未开启
        if(config!=null&&"1".equals(config.getConfigStatus())){
            setAttribute("isOpen",true);
        }else{
            setAttribute("isOpen",false);
        }
        String userAccount = this.getParameter("userAccount");
        setAttribute("userAccount",userAccount);
        return "/html5/vm/index";
    }

    /**
     * 判断ocr是否开启
     * @return
     */
    @NoLoginInterceptor
    @RequestMapping("/getIdCardPage")
    public String getIdCardPage(String phone,String userAccount){
        setAttribute("phone",phone);
        setAttribute("userAccount",userAccount);
        SysBasicConfig config = basicConfigProvider.getSysBasicConfigByKey("ocr");
        if(config!=null&&"1".equals(config.getConfigStatus())){
            //ocr开启
            setAttribute("isOpen",true);
            return "/html5/vm/idCard";
        }else {
            //ocr没开启则跳转到手动填写身份信息页面
            setAttribute("isOpen", false);
            return "/html5/vm/idCard1";
        }
    }

    /**
     * 判断人脸识别是否开启
     * @return
     */
    @NoLoginInterceptor
    @RequestMapping("/getCheckIdent")
    public String getCheckIdent(String name,String cardNum,String phone,String address,String sex,String year,String id1,String id2,String id4,String userAccount){
            int age=1;

        SysBasicConfig config = basicConfigProvider.getSysBasicConfigByKey("rlsb");
           try{
              address= URLDecoder.decode(address,"UTF-8");
              name=URLDecoder.decode(name,"UTF-8");
               sex=URLDecoder.decode(sex,"UTF-8");
           }catch(Exception e){

           }
            if("男".equals(sex)){
                sex="1";
            }else if("女".equals(sex)) {
                sex = "0";
            }
            try {
                 age=getAge(new SimpleDateFormat("yyyy").parse(year));
            }catch (Exception e){
                e.printStackTrace();
            }

            setAttribute("name", name);
            setAttribute("cardNum",cardNum);
            setAttribute("phone",phone);
            setAttribute("userAccount",userAccount);
            setAttribute("address",address);
            setAttribute("sex",sex);
            setAttribute("age",age);
            setAttribute("id1",id1);
            setAttribute("id2",id2);
            setAttribute("id4",id4);
        if(config!=null&&"1".equals(config.getConfigStatus())){
            return "/html5/vm/checkIdent";
        }else{
            //拿出贷款用途
            Map<String ,Object> loanuse = new HashedMap();
            List<IntoLoanUse> intoLoanUses = loanUseProvider.queryLoanUseList(loanuse);
            setAttribute("loanUse",intoLoanUses);
            return "/html5/vm/applyPerson";
        }
    }
    /**
     * 上传正反照片后 跳转到填写姓名 身份证页面
     * 传手机号，附件id
     * @return
     */
    @NoLoginInterceptor
    @RequestMapping("/getCheckIdent1")
    public String getCheckIdent1(String phone,String id1,String id2,String userAccount){
        setAttribute("phone",phone);
        setAttribute("id1",id1);
        setAttribute("id2",id2);
        setAttribute("userAccount",userAccount);
        return "/html5/vm/checkIdent1";
    }

    /**
     * 进入申请信息界面
     * @return
     */
    @NoLoginInterceptor
    @RequestMapping("/getApplyPerson")
    public String getApplyPerson(String name,String cardNum,String phone,String address,String sex,String age,String id1,String id2,String id3,String id4,String id5,String userAccount){
        //拿出自定义字段
        Map<String ,Object> con = new HashedMap();
        List<IntoTemplatesFieldQuery> list= templatesFieldProvider.queryTemplatesFieldList(con);
        setAttribute("intoList",list);//把需要填写的自定义字段传入前台
        //拿出贷款用途
        Map<String ,Object> loanuse = new HashedMap();
        List<IntoLoanUse> intoLoanUses = loanUseProvider.queryLoanUseList(loanuse);
        try{
            if(address != null){
                address= URLDecoder.decode(address,"UTF-8");
            }
            name=URLDecoder.decode(name,"UTF-8");
        }catch(Exception e){
            e.printStackTrace();
        }
        setAttribute("loanUse",intoLoanUses);
        setAttribute("name", name);
        setAttribute("cardNum",cardNum);
        setAttribute("phone",phone);
        setAttribute("userAccount",userAccount);
        setAttribute("address",address);
        setAttribute("sex",sex);
        setAttribute("age",age);
        setAttribute("id1",id1);
        setAttribute("id2",id2);
        setAttribute("id3",id3);
        setAttribute("id4",id4);
        setAttribute("id5",id5);
        return "/html5/vm/applyPerson";
    }
    @NoLoginInterceptor
    @RequestMapping("/saveIntoInfo")
    @ResponseBody
    public void saveIntoInfo(String name,String cardNum,String phone,String address,String sex,
                             Integer age,Integer id1,Integer id2,Integer id3,Integer id4,
                             Integer id5,String loanUserOption,BigDecimal applyAmount,String userAccount){
        IntoCustApplyInfo info=new IntoCustApplyInfo();
        try{
            address= URLDecoder.decode(address,"UTF-8");
            name=URLDecoder.decode(name,"UTF-8");
        }catch(Exception e){
            e.printStackTrace();
        }
        //info.setUseId(useId);
        info.setLoanUserOption(loanUserOption);
        info.setApplyAmount(applyAmount);
        info.setCustName(name);
        info.setCustPhone(phone);
        info.setUserAccount(userAccount);
        info.setCustAge(age);
        info.setCustSex(sex);
        info.setHomeAddress(address);
        info.setIdCard(cardNum);
        info.setLoanId(0);
        info.setIsDel(0);
        info.setSignSate(1);
        Integer applyId=custApplyInfoService.insertCustApplyInfo(info,0);
        //更新附件表
        IntoFileInfo file1=new IntoFileInfo();
        IntoFileInfo file2=new IntoFileInfo();
        IntoFileInfo file3=new IntoFileInfo();
        IntoIntefaceRecord record1=new IntoIntefaceRecord();
        IntoIntefaceRecord record2=new IntoIntefaceRecord();
        //正面照片
        if(id1!=null){
            file1.setApplyId(applyId);
            file1.setId(id1);
            fileInfoService.updateFileInfo(file1,0);
        }
        //反面照片
        if(id2!=null){
            file2.setApplyId(applyId);
            file2.setId(id2);
            fileInfoService.updateFileInfo(file2,0);
        }

        //人脸照片
        if(id3!=null){
            file3.setApplyId(applyId);
            file3.setId(id3);
            fileInfoService.updateFileInfo(file3,0);
        }

        //ocr
        if(id4!=null){
            record1.setApplyId(applyId);
            record1.setRequestId(id4);
            iIntefaceRecordService.updateIntefaceRecord(record1,0);
        }
        //人脸识别
        if(id5!=null){
            record2.setApplyId(applyId);
            record2.setRequestId(id5);
            iIntefaceRecordService.updateIntefaceRecord(record2,0);
        }

    }

    /**
     * 获取验证码
     * @param phone
     */
    @NoLoginInterceptor
    @RequestMapping("/getMsg")
    @ResponseBody
    public void getMsg(@RequestParam(value = "phone",defaultValue = "") String phone){
        HttpServletRequest req = ServletContextHolder.getRequest();
        //生成4位数字
        String code=generateCode();
        //发短信
        SendSmsUtil.sendSMS("","","",phone,"您的验证码是"+code+",请在5分钟内使用！");
        IntoPhoneMsgCode intoPhoneMsgCode=new IntoPhoneMsgCode();
        intoPhoneMsgCode.setAddTime(new Date());
        intoPhoneMsgCode.setIp(getIpAddr(req));
        intoPhoneMsgCode.setCheckCode(code);
        intoPhoneMsgCode.setPhone(phone);
        //记录短信
        phoneMsgCodeService.insertPhoneMsgCode(intoPhoneMsgCode,0);
    }

    /**
     * 验证码校验>按最新的验证码判断
     * @param phone
     * @param checkCode
     */
    @NoLoginInterceptor
    @RequestMapping("/checkCode")
    @ResponseBody
    public void checkCode(@RequestParam(value = "phone",defaultValue = "")String phone,
                          @RequestParam(value = "checkCode",defaultValue = "")String checkCode){
        //HttpServletRequest req = ServletContextHolder.getRequest();
        IntoPhoneMsgCode msgCode=phoneMsgCodeService.queryMsgInfoByPhone(phone);
        if(msgCode!=null){
            //失效或者不正确
            Date date=msgCode.getAddTime();
            long time= DateUtil.getMinutes(new Date(),msgCode.getAddTime());
            if(time>5||!checkCode.equals(msgCode.getCheckCode())){
               renderText(false,"验证码错误","");
               return;
            }
            renderText(true,"","");
            return;
        }
        renderText(false,"验证码错误","");
    }


    /**
     * 获取客户端真实Ip
     * @param req
     * @return
     */
    private String getIpAddr(HttpServletRequest req){
        String ipAddress = null;
        //ipAddress = req.getRemoteAddr();
        ipAddress = req.getHeader("x-forwarded-for");
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)){
            ipAddress = req.getHeader("Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)){
            ipAddress = req.getHeader("WL-Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)){
            ipAddress = req.getRemoteAddr();
        }
        if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){
            //根据网卡去本机配置的IP
            InetAddress inet =null;
            try{
                inet = InetAddress.getLocalHost();
            }catch(UnknownHostException e){
                e.printStackTrace();
            }
            ipAddress = inet.getHostAddress();
        }

        //对于通过多个代理的情况，第一个IP为客户端真实IP，多个IP按照','分割
        if(ipAddress != null && ipAddress.length()>15){
            if(ipAddress.indexOf(",")>0){
                ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));
            }
        }

        return ipAddress;
    }

    /**
     * 生成随机的4位验证码
     * @return
     */
    public String generateCode(){
        String str="0123456789";
        StringBuilder sb=new StringBuilder(4);
        for(int i=0;i<4;i++)
        {
            char ch=str.charAt(new Random().nextInt(str.length()));
            sb.append(ch);
        }
        return  sb.toString();
    }
    /**
     * 插入调用接口记录
     * @return
     */
    @NoLoginInterceptor
    @RequestMapping("/insertIntefaceRecord")
    @ResponseBody
    public void insertIntefaceRecord(Integer interfaceType,Integer isSuccess,Integer errorType,String errorMessage,Integer applyId,String ids){

        String[] s =ids.split(",");
        IntoIntefaceRecord intoIntefaceRecord = new IntoIntefaceRecord();
        intoIntefaceRecord.setApplyId(applyId);
        intoIntefaceRecord.setCallTime(new Date());
        intoIntefaceRecord.setErrorMessage(errorMessage);
        intoIntefaceRecord.setErrorType(errorType);
        intoIntefaceRecord.setInterfaceType(interfaceType);
        intoIntefaceRecord.setIsSuccess(isSuccess);
        Integer requestId =  iIntefaceRecordService.insertIntefaceRecord(intoIntefaceRecord);

        IntoFileInfo intoFileInfo = new IntoFileInfo();
        intoFileInfo.setRequestId(requestId);
        for (String id : s){
            intoFileInfo.setId(Integer.parseInt(id));
            fileInfoService.updateFileInfo(intoFileInfo,0);
        }
        JSONObject json =new JSONObject();
        json.put("requestId",requestId);
        renderJson(true,"",json);
    }

    //由出生日期获得年龄
    public  int getAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);


        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;
            }else{
                age--;
            }
        }
        return age;
    }
}


