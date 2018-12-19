package banger.util;

import banger.util.constant.BaseXmlBeanEnum;
import banger.util.constant.Socket_99CHNCMI4047;
import banger.util.constant.Socket_Head;
import net.sf.json.JSONArray;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;

/**
 * Created by kangbiao on 2018/1/29.
 */
public class SocketUtil {

    //根据对象 和 数据 拼装xml报文
    public static String getSocketXml(String socketCode, List<BaseXmlBeanEnum> bodyList, Map<String,String> map) throws Exception {

        try{
            map.put("交易码",socketCode);

            Element lifcsElement = DocumentHelper.createElement("lifcs");
            Document doc = DocumentHelper.createDocument(lifcsElement);
            doc.setXMLEncoding("GBK");

            Element headElement = lifcsElement.addElement("head");
            Element bodyElement = lifcsElement.addElement("body");

            /*报文头*/
            List<BaseXmlBeanEnum> headList = Socket_Head.getAllElement();
            addXmlElement(headList,  map, headElement);

            if(socketCode.equals("99CHNCMI2034")){
                //贷款主合同签订
                addXmlElement_99CHNCMI2034(map, bodyElement);
            }else if(socketCode.equals("99CHNCMI2035")){
                //抵押担保合同签订
                addXmlElement_99CHNCMI2035(map, bodyElement);
            }else{
                 /*报文体*/
                addXmlElement(bodyList,  map, bodyElement);
            }

            /*报文前缀 000128 99CHNCMI2001*/
            String xml = doc.asXML();

            return getSendXml(xml,socketCode);

        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }

    }

    //贷款主合同签订
    private static void addXmlElement_99CHNCMI2034(Map<String, String> map, Element parentElement)throws Exception  {
        try {
            Element element ; String value;

            //合同号
            value = MapUtils.getString(map,"合同号");
            element = DocumentHelper.createElement("cont_no");
            element.addText(value);
            parentElement.add(element);
            //合同状态
            value = MapUtils.getString(map,"合同状态","200");//300---注销  //200---生效
            element = DocumentHelper.createElement("cont_state");
            element.addText(value);
            parentElement.add(element);
            //场景
            value = MapUtils.getString(map,"场景","1");//300---注销  //200---生效
            element = DocumentHelper.createElement("sence");
            element.addText(value);
            parentElement.add(element);

            // list row
            Element listElement = parentElement.addElement("list");
            Element rowElement;
            String guar_cont_no ;
            //保证人担保合同
            guar_cont_no = MapUtils.getString(map,"保证人合同号");
            if(StringUtils.isNotBlank(guar_cont_no)){
                rowElement = listElement.addElement("row");
                //担保合同编号
                element = DocumentHelper.createElement("guar_cont_no");
                element.addText(guar_cont_no);
                rowElement.add(element);
                //担保合同状态
                value = MapUtils.getString(map, "合同状态", "200");//300---注销  //200---生效
                element = DocumentHelper.createElement("guar_cont_state");
                element.addText(value);
                rowElement.add(element);
                //担保合同状态
                value = MapUtils.getString(map, "审批决议.决议金额", "0");//300---注销  //200---生效
                element = DocumentHelper.createElement("used_amt");
                element.addText(value);
                rowElement.add(element);
            }

            //抵押担保合同
            guar_cont_no = MapUtils.getString(map,"抵押合同号");
            if(StringUtils.isNotBlank(guar_cont_no)){
                rowElement = listElement.addElement("row");
                //担保合同编号
                element = DocumentHelper.createElement("guar_cont_no");
                element.addText(guar_cont_no);
                rowElement.add(element);
                //担保合同状态
                value = MapUtils.getString(map, "合同状态", "200");//300---注销  //200---生效
                element = DocumentHelper.createElement("guar_cont_state");
                element.addText(value);
                rowElement.add(element);
                //担保合同状态
                value = MapUtils.getString(map, "审批决议.决议金额", "0");//300---注销  //200---生效
                element = DocumentHelper.createElement("used_amt");
                element.addText(value);
                rowElement.add(element);
            }

            //质押担保合同
            guar_cont_no = MapUtils.getString(map,"质押合同号");
            if(StringUtils.isNotBlank(guar_cont_no)){
                rowElement = listElement.addElement("row");
                //担保合同编号
                element = DocumentHelper.createElement("guar_cont_no");
                element.addText(guar_cont_no);
                rowElement.add(element);
                //担保合同状态
                value = MapUtils.getString(map, "合同状态", "200");//300---注销  //200---生效
                element = DocumentHelper.createElement("guar_cont_state");
                element.addText(value);
                rowElement.add(element);
                //担保合同状态
                value = MapUtils.getString(map, "审批决议.决议金额", "0");//300---注销  //200---生效
                element = DocumentHelper.createElement("used_amt");
                element.addText(value);
                rowElement.add(element);
            }

        }catch (Exception e){
            throw new Exception("封装请求报文出错："+e.getMessage());
        }

    }

    //抵押担保合同签订
    private static void addXmlElement_99CHNCMI2035(Map<String, String> map, Element parentElement)throws Exception  {
        try {
            Element element ; String value;

            //担保合同号
            value = MapUtils.getString(map,"担保合同号");
//            element = DocumentHelper.createElement("担保合同号");
            element = DocumentHelper.createElement("guar_cont_no");
            element.addText(value);
            parentElement.add(element);
            //担保合同状态
            value = MapUtils.getString(map,"担保合同状态","101");//104---注销  101---生效
            element = DocumentHelper.createElement("guar_cont_state");
            element.addText(value);
            parentElement.add(element);
            //担保方式
            value = MapUtils.getString(map,"担保方式","1");//20---质押                       10---抵押                       30---保证                       00---信用
            element = DocumentHelper.createElement("guar_way");
            element.addText(value);
            parentElement.add(element);
            //场景
            value = MapUtils.getString(map,"场景","1");//1.	签订  2.	注销
            element = DocumentHelper.createElement("sence");
            element.addText(value);
            parentElement.add(element);

            // list row
            Element listElement = parentElement.addElement("list");
            Element rowElement;
            List<Map<String,String>> list = (List < Map < String, String >>)JSONArray.toList(JSONArray.fromObject(map.get("99CHNCMI2035")),HashMap.class);
            for(Map<String,String> rowMap : list) {
                rowElement = listElement.addElement("row");
                for (Map.Entry<String, String> entry : rowMap.entrySet()) {
                    element = DocumentHelper.createElement(entry.getKey());
                    element.addText(entry.getValue());
                    rowElement.add(element);
                }
            }
        }catch (Exception e){
            throw new Exception("封装请求报文出错："+e.getMessage());
        }

    }







    private static void addXmlElement(List<BaseXmlBeanEnum> list, Map<String,String> map, Element parentElement) throws Exception {
        try {
            Element element ; String value;
            //报文体
            for (BaseXmlBeanEnum xmlBean : list) {
                value = MapUtils.getString(map,xmlBean.getElementColumnName());
                //转换options
                if(MapUtils.isNotEmpty(xmlBean.getElementOptions())&& StringUtils.isNotBlank(value)){
                    value = MapUtils.getString(xmlBean.getElementOptions(),value,value);
                }
                //默认值
                if(StringUtils.isBlank(value)&&StringUtils.isNotBlank(xmlBean.getElementDefaultValue())){
                    value = xmlBean.getElementDefaultValue();
                }
                //是否可为空
                if(xmlBean.getElementNotNull().equals("1")&&StringUtils.isBlank(value)){
                    throw new Exception(xmlBean.getElementColumnName()+"，不允许为空");
                }
                //长度截取
                if(StringUtils.isNotBlank(value)){
                    int eLength = Integer.parseInt(xmlBean.getElementLength());
                    byte[] bytes = value.getBytes("GBK");
                    if(bytes.length>eLength){
//                        byte[] tempb = new byte[eLength];
//                        for (int i = 0; i < eLength; i++) {
//                            tempb[i]=bytes[i];
//                        }
//                        value = tempb.toString();
                        if(eLength>value.length()){
                            eLength = value.length()/2;
                        }

                        value = value.substring(0,eLength);
                    }
                }else{
                    value = "";
                }
                element = DocumentHelper.createElement(xmlBean.getElementName());
                element.addText(value);
                parentElement.add(element);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("封装请求报文出错："+e.getMessage());
        }

    }

    private static String getStringDate(Date date, String pattern) {
        try{
            if(null!=date){
                SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                return sdf.format(date);
            }else{
                return "";
            }
        }catch (Exception e){
            return "";
        }
    }
    public static void main(String[] args) {
        try {

            Map<String,String> map = new HashMap<String, String>();
            map.put("当前日期", getStringDate(new Date(), "yyyy-MM-dd"));
            //其他 报文头 时间
            map.put("请求方日期", getStringDate(new Date(), "yyyyMMdd"));
            map.put("请求方时间", getStringDate(new Date(), "HHmmss"));
            map.put("请求方流水号", "122"+String.valueOf(System.currentTimeMillis()));
            map.put("调查结论.贷款性质", "1");
            map.put("调查结论.产品类型", "02");
            map.put("贷款起始日", "2018-04-01");
            map.put("贷款终止日", "2019-04-01");
            map.put("利率类型", "1");
            map.put("币种", "CNY");
            map.put("币种", "CNY");
            map.put("产品编号", "022372");
            map.put("是否住房公积金贷款", "2");
            map.put("业务品种名称", "个人类产品>非农个人消费贷款>个人消费贷款(自助循环)>个人消费贷款(自助循环)");

            String s = getSocketXml(Socket_99CHNCMI4047.getSocketCode(), Socket_99CHNCMI4047.getAllElement(), map);

            System.out.println(s);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static String getSendXml(String xml,String socketCode) {

        try{
            StringBuffer v1 = new StringBuffer(""), v2 = new StringBuffer(socketCode);
            //数据长度 交易码长度
            int len1 = 6,len2 = 12;

            int xmlLength = (xml).getBytes("GBK").length + len2;

            len1 -= String.valueOf(xmlLength).getBytes("GBK").length;
            for (int i = 0; i < len1; i++) {
                v1.append("0");
            }
            for(int i=0;i<len2-socketCode.getBytes("GBK").length;i++){
                v2.append(" ");
            }
            v1.append(xmlLength).append(v2).append(xml);
            return v1.toString();
        }catch (Exception e){
            return xml;
        }

    }

//    public


    //
    private static Logger logger = Logger.getLogger(SocketUtil.class);
//    /**
//     * 发送socket请求
//     * @param clientIp
//     * @param clientPort
//     * @param xml
//     * @return
//     */
//    public static synchronized String socketPost(String clientIp,String clientPort,String xml) throws Exception {
//        String rs = "";
//
//        if(clientIp==null||"".equals(clientIp)||clientPort==null||"".equals(clientPort)){
//            throw new Exception("数据同步地址信息有误，请联系管理员！");
//        }
//
//        int clientPortInt = Integer.parseInt(clientPort);
//
////        logger.info("clientIp："+clientIp+" clientPort："+clientPort);
//
//        Socket s = null;
//        OutputStream out = null;
//        InputStream in = null;
//        try {
//            s = new Socket(clientIp, clientPortInt);
//            s.setSendBufferSize(4096);
//            s.setTcpNoDelay(true);
//            s.setSoTimeout(60*1000);
//            s.setKeepAlive(true);
//            out = s.getOutputStream();
//            in = s.getInputStream();
//
//            //准备报文msg
////            logger.info("准备发送报文："+xml);
//
//            out.write(xml.getBytes("GBK"));
//            out.flush();
//
//            byte[] rsByte = readStream(in);
//
//            if(rsByte!=null){
//                rs = new String(rsByte, "GBK");
//            }
//
//        } catch (Exception e) {
////             e.printStackTrace();
//            throw new Exception("socketPost发送请求异常："+e.getMessage());
//        }finally{
//            try {
//                if(out!=null){
//                     out.close();
//                    out = null;
//                }
//                if(in!=null){
//                    in.close();
//                    in = null;
//                }
//                if(s!=null){
//                    s.close();
//                    s = null;
//                }
//            } catch (IOException e) {
//            }
//        }
//
//        return rs;
//
//    }

    /**
     * 发送socket请求
     * @param clientIp
     * @param clientPort
     * @param msg
     * @return
     */
    public static synchronized String socketPost(String clientIp,String clientPort,String msg){
        String rs = "";
        try {
            // 要连接的服务端IP地址和端口
            // 与服务端建立连接
            Socket socket = new Socket(clientIp, Integer.parseInt(clientPort));
            // 建立连接后获得输出流
            OutputStream outputStream = socket.getOutputStream();
            socket.getOutputStream().write(msg.getBytes("GBK"));
            //通过shutdownOutput高速服务器已经发送完数据，后续只能接受数据
            socket.shutdownOutput();

            InputStream inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            int len;
            StringBuilder sb = new StringBuilder();
            while ((len = inputStream.read(bytes)) != -1) {
                //注意指定编码格式，发送方和接收方一定要统一，建议使用UTF-8
                sb.append(new String(bytes, 0, len,"GBK"));
            }
//            System.out.println("get message from server: " + sb);
            rs = sb.toString();
            inputStream.close();
            outputStream.close();
            socket.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return rs;

    }

    /**
     * 读取输入流
     * @param in
     * @return
     */
    private static byte[] readStream(InputStream in) throws Exception {
        if(in==null){
            return null;
        }

        byte[] b = null;
        ByteArrayOutputStream outSteam = null;
        try {
            byte[] buffer = new byte[1024];
            outSteam = new ByteArrayOutputStream();

            int len = -1;
            while ((len = in.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }

            b = outSteam.toByteArray();
        } catch (IOException e) {
            throw new Exception("读取流信息异常:"+e.getMessage());
        } finally{
            try {
                if(outSteam!=null){
                    outSteam.close();
                    outSteam = null;
                }
                if(in!=null){
                    in.close();
                    in = null;
                }
            } catch (IOException e) {
            }
        }
        return b;
    }

}
