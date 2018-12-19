package banger.controller;

import banger.common.BaseController;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;
import java.io.*;

/**
 * Created by Administrator on 2017/8/29.
 */
public class BaseMapController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(BaseMapController.class);

    @Value("${ngnix.map.ip.port}")
    private String ipport;
    @Value("${ngnix.map.source}")
    private String sourcefile;
    private boolean reWrite = false;
    @Value("${map.use.ruitu}")
    private boolean useRuitu;

    private static String BASE_PATH = null;

    public String getBasePath(String modulePath){
        if (BASE_PATH == null) {
            BASE_PATH = useRuitu ? "/track/ruitu/vm/" : "/track/baidu/vm/";
        }
        return BASE_PATH + modulePath + "/";
    }

    public void setBaiduMapJs(){
        //如果不使用瑞图，则使用百度地图，替换js外网请求链接
        if (!useRuitu){
            String mapjs = getMapJsUrl(getRequest());
            System.out.println(mapjs);
            setAttribute("mapjs", mapjs);
        }
    }


    /**
     * 重写百度js，替换外网请求到ngnix，如果未配置ngnix，使用默认的百度js
     * @param request
     * @return
     */
    private String getMapJsUrl(HttpServletRequest request) {
        String host = request.getServerName() + ":" + request.getServerPort();
        String resultUrl = "";
        if (StringUtils.isBlank(ipport)) {
            resultUrl =  host + "/" + sourcefile;
        } else {
            String newFile = sourcefile.replace(".js", "N.js");
            resultUrl =  host + "/" + newFile;
            if (!reWrite) {
                reWrite = true;
                try {
                    String classPath = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
                    classPath = classPath.replace("/WEB-INF/classes", "");
                    classPath = classPath.replace("//","/");
//                    System.out.println(classPath + sourcefile);
//                    System.out.println(classPath + newFile);
                    File file = new File(classPath + sourcefile);
                    Reader reader = new FileReader(file);
                    int ch=reader.read();
                    StringBuffer buffer=new StringBuffer();
                    while(ch!=-1){ //读取成功
                        buffer.append((char)ch);
                        ch=reader.read();
                    }
                    reader.close();
                    String text = buffer.toString();
                    text = text.replaceAll("online0",ipport + "/online0");
                    text = text.replaceAll("online1",ipport + "/online1");
                    text = text.replaceAll("online2",ipport + "/online2");
                    text = text.replaceAll("online3",ipport + "/online3");
                    text = text.replaceAll("online4",ipport + "/online4");

                    text = text.replaceAll("shangetu",ipport + "/shangetu");
                    text = text.replaceAll("d0.map.baidu.com",ipport + "/d0.map.baidu.com");
                    text = text.replaceAll("d1.map.baidu.com",ipport + "/d1.map.baidu.com");
                    text = text.replaceAll("d2.map.baidu.com",ipport + "/d2.map.baidu.com");
                    text = text.replaceAll("d3.map.baidu.com",ipport + "/d3.map.baidu.com");

                    text = text.replaceAll("pcsv",ipport + "/pcsv");

                    text = text.replaceAll("\"d0.","\"" + ipport + "/d0.");
                    text = text.replaceAll("\"d1.","\"" + ipport + "/d1.");
                    text = text.replaceAll("\"d2.","\"" + ipport + "/d2.");
                    text = text.replaceAll("\"d3.","\"" + ipport + "/d3.");

                    text = text.replaceAll("\"g0.","\"" + ipport + "/g0.");
                    text = text.replaceAll("\"g1.","\"" + ipport + "/g1.");
                    text = text.replaceAll("\"g2.","\"" + ipport + "/g2.");
                    text = text.replaceAll("\"g3.","\"" + ipport + "/g3.");
                    text = text.replaceAll("http://api0","http://" + ipport + "/api0");
                    text = text.replaceAll("\"api.map","\"" + ipport + "/api.map");
                    text = text.replaceAll("\"api0.map","\"" + ipport + "/api0.map");
                    text = text.replaceAll("\"api1.map","\"" + ipport + "/api1.map");
                    text = text.replaceAll("\"api2.map","\"" + ipport + "/api2.map");

                    File fileCopy = new File(classPath + newFile);
                    if (!fileCopy.exists()) {
                        fileCopy.createNewFile();
                    }
                    //1、打开流
                    Writer w=new FileWriter(fileCopy);
                    //2、写入内容
                    w.write(text);
                    //3、关闭流
                    w.close();

                } catch (FileNotFoundException e) {
                    logger.error(e.getMessage());
                    resultUrl =  host + "/" + sourcefile;
//                    e.printStackTrace();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                    resultUrl =  host + "/" + sourcefile;
//                    e.printStackTrace();
                }
            }
        }
        return resultUrl;
    }
}
