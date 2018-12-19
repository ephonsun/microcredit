package banger.util;

import banger.dao.intf.IApplyInfoDao;
import banger.domain.enumerate.OperationCode;
import banger.domain.system.SysBasicConfig;
import banger.framework.spring.SpringContext;
import banger.framework.util.DateUtil;
import banger.moduleIntf.IBasicConfigProvider;
import net.sf.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by ygb on 2018/3/18.
 */
public class CodedProduceUtil {

    private static IApplyInfoDao applyInfoDao = (IApplyInfoDao) SpringContext.instance().get("applyInfoDao");
    //操作码+当前年份（YYYY）+顺序号+校验位
    public static String getCode(Integer operationCode,boolean type){
        try {
            if(operationCode != null) {
                Integer configValue = applyInfoDao.queryOperationCode();
                //处理顺序号
                String value = String.valueOf(configValue+1);
                if(type){
                    //update
                    applyInfoDao.updateOperationCode();

                }else {
                    //just look
                }
                StringBuffer sxh = new StringBuffer(value);
                for (int i = 0; i < 7 - value.length(); i++) {
                    sxh.insert(0, "0");
                }
                //当前年份
//                Calendar cal = Calendar.getInstance();
//                int year = cal.get(Calendar.YEAR);
                String year = DateUtil.format(new Date(),"YYYY");
                String acc = operationCode + year + "97" + sxh.toString();
                //校验位
                char c = getCheckDigitHbs(acc);
                //编码
                String code = acc + c;
                return  code;
            }else {
                return "-1";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "-1";

        }
    }

    //授权流水号=日期6位（YYMMDD）+ 顺序号(8位,1位为首位)
    //type false 只查看下一个授权流水号 ,true 生成授权流水号
    public static String getAuthorizedSerialCode(boolean type){
        try {
            //日期
            String date = DateUtil.format(new Date(),"YYMMdd");

            //流水号
            String  value = String.valueOf(applyInfoDao.querySerialCode()+1);
            if(type){
                applyInfoDao.updateSerialCode();
            }
            StringBuffer sb = new StringBuffer(value);
            for(int i=0;i<7-value.length();i++){
                sb.insert(0,"0");
            }
            String code = date + sb.insert(0,"1").toString();
            return code;
        }catch (Exception e){
            return String.valueOf(System.currentTimeMillis());
        }

    }

    //生成客户编码 规则（0000008(7位)+10位随机数
    public static String getCusCode(){
        StringBuffer sb = new StringBuffer("0000008");
        int[] a = new int[5];
        for(int i=0;i<a.length;i++){
            a[i]=new Random().nextInt(8);
        }
        a[0] &= new Random().nextInt(8);
        a[1] |= new Random().nextInt(8);
        a[2] &= new Random().nextInt(8);
        a[3] |= new Random().nextInt(8);
        for(int i=0;i<a.length;i++){
            sb.append(a[i]);
        }
        int[] b = new int[5];
        for(int j=0;j<b.length;j++){
            b[j] = Math.abs(new Random().hashCode());
        }
        b[0]=b[0]%2 + new Random().nextInt(9);
        b[1]=b[1]%3 + new Random().nextInt(8);
        b[2]=b[2]%5 + new Random().nextInt(6);
        b[3]=b[3]%7 + new Random().nextInt(4);
        b[4]=b[4]%9 + new Random().nextInt(2);
        for(int i=0;i<b.length;i++){
            sb.append(b[i]);
        }
        return sb.toString();
    }


    //校验位
    private static char getCheckDigitHbs(String acc){
        char[] apPure_acct=acc.toCharArray();
        char digit=' ';
        int liDigitSum = 0;
        int liNum = 0;
        String lsDigit="N";
        int i = apPure_acct.length;
        char NewAcc[] = "00000000000000000".toCharArray();

        char wa_table_numbers[][]=
                {
                        "100908070605040302".toCharArray(),
                        "051004090308020701".toCharArray(),
                        "080502100704010906".toCharArray(),
                        "040801050902061003".toCharArray(),
                        "020406081001030507".toCharArray(),
                        "010203040506070809".toCharArray(),
                        "060107020803090410".toCharArray(),
                        "030609010407100205".toCharArray(),
                        "070310060209050108".toCharArray(),
                        "090705030110080604".toCharArray(),
                        "100908070605040302".toCharArray(),
                        "051004090308020701".toCharArray(),
                        "080502100704010906".toCharArray(),
                        "040801050902061003".toCharArray(),
                        "020406081001030507".toCharArray(),
                        "010203040506070809".toCharArray()
                };

        if (i>16 ) /* Account Number length not than 16 */
        {
            digit='L';
            return digit;
        }

        i--;

        for (int j = 0; j <= i; j++ )   /* make every byte digital       */
        {
            if ( apPure_acct[j] - '0' > 9 ||  apPure_acct[j] - '0' < 0 )
            {
                digit='C';
                return digit;
            }
        }

        String.copyValueOf(apPure_acct);
        String tempStr=String.copyValueOf(NewAcc).substring(0, 15-i)+String.copyValueOf(apPure_acct);;
        NewAcc=tempStr.toCharArray();

        for (i =15;i>= 0;i--)
        {
            if ( NewAcc[i]   != '0')
            {
                lsDigit="0";
                liNum =NewAcc[i]  - '0';
                lsDigit=lsDigit+wa_table_numbers[i][ (liNum - 1 )*2];
                lsDigit=lsDigit+wa_table_numbers[i][ (liNum - 1 )*2+1];
                liDigitSum += Integer.valueOf(lsDigit);
            }
        }
        liDigitSum %=10;
        digit=(char)('0'+liDigitSum);
        return digit;
    }


}
