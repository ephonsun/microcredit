package banger.controller;

import banger.common.BaseController;
import banger.common.interceptor.NoLoginInterceptor;
import banger.framework.util.StringUtil;
import banger.util.SocketUtil;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.dom4j.DocumentHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by ygb on 2018/6/25.
 */

@Controller
@RequestMapping("/monitor")
public class SocketDebugController extends BaseController {


    private static final long serialVersionUID = -5937836661999153605L;

    @Value("${socket.ip}")
    private String socketIp;
    @Value("${socket.port}")
    private String socketPort;

    /**
     * socket测试页面
     */
    @NoLoginInterceptor
    @RequestMapping("/getSocketDebugPage")
    public String getSqlMonitorListPage(){
        return "system/vm/socketDebugPage";
    }

    /**
     **格式化xml
     * @param
     */
    @RequestMapping(value = "/doFormatXml", method = RequestMethod.POST)
    @ResponseBody
    public void doFormatXml(@RequestParam(value = "postString", defaultValue = "") String postString){
        try{
            if(StringUtil.isNotEmpty(postString)){
                String formatXml = "";
                if(isXml(postString)){
                    formatXml = format(postString);
                }else{
                    formatXml = format(postString.substring(18));
                }
                renderText(true,"格式化成功",formatXml);
            }else{
                renderText(false,"请输入发送报文!","");
            }
        }catch (Exception e){
            renderText(false,"格式化xml失败!原因为：<br>"+e.getMessage(),"");
        }
    }

    /**
     **格式化xml
     * @param
     */
    @RequestMapping(value = "/doSocketPost", method = RequestMethod.POST)
    @ResponseBody
    public void doSocketPost(@RequestParam(value = "postString", defaultValue = "") String postString){
        try{
            if(StringUtil.isNotEmpty(postString)){
                String sendXml = "";
                String returnMsg = "";
                if(!isXml(postString)){
                    renderText(false,"发送报文格式不对!","");
                }else {
                    sendXml = getSendXml(postString);
                    returnMsg = SocketUtil.socketPost(socketIp,socketPort,sendXml);
                    renderText(true,"执行成功!",returnMsg);
                }

            }else{
                renderText(false,"请输入发送报文!","");
            }
        }catch (Exception e){
            renderText(false,"执行失败!原因为：<br>"+e.getMessage(),"");
        }
    }


    private static String format(String context) {
        try {
            final Document document = parseXmlFile(context);
            OutputFormat format = new OutputFormat(document);
            format.setLineWidth(65);
            format.setIndenting(true);
            format.setIndent(2);
            format.setEncoding("GBK");
            Writer out = new StringWriter();
            XMLSerializer serializer = new XMLSerializer(out, format);
            serializer.serialize(document);
            return out.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Document parseXmlFile(String in) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(in));
            return db.parse(is);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isXml(String context){
        try {
            format(context);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    private static String getSendXml(String xml){

        try{
            org.dom4j.Document document = DocumentHelper.parseText(xml);
            String socketCode = document.getRootElement().element("head").element("tx_code").getText().trim();;
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

}
