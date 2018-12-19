package banger.controller;

import banger.common.BaseController;
import banger.common.tools.JsonUtil;
import banger.domain.system.SysOpeventLog;
import banger.domain.system.SysOpeventLog_Query;
import banger.framework.pagesize.IPageList;
import banger.framework.util.FileUtil;
import banger.service.intf.ISysOpeventLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/systemLog")
public class SystemLogController extends BaseController{
    @Autowired
    private ISysOpeventLogService iSysOpeventLogService;


    /**
     * 跳转日志页
     */
    @RequestMapping("/initSystemLogPage")
    public ModelAndView initSystemLogPage(){
        ModelAndView mv = new ModelAndView("/system/vm/systemLogList");
        List<SysOpeventLog> list = iSysOpeventLogService.getOpeventModule();
        mv.addObject("moduleNameList",list);
        return mv;
    }
    /**
     * 查询操作日志列表
     *
     */
    @RequestMapping("queryLogByCondition")
    public void queryLogByCondition(){
        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put("logDateBegin", getParameter("logDateBegin"));
        condition.put("logDateEnd", getParameter("logDateEnd"));
        condition.put("opeventModule",getParameter("opeventModule"));
        condition.put("userName",getParameter("txtBelongTo"));
        IPageList<SysOpeventLog_Query> list = iSysOpeventLogService.queryLogByCondition(condition,this.getPage());
        renderText(JsonUtil.toJson(list, "opeventId","opeventUserName,opeventUserAccount,opeventId,opeventUserId,opeventModule,opeventAction,opeventDate,opeventIp").toString());
    }

    /**
     *导出日志
     */
    @RequestMapping("/exportLog")
    public void exportLog(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put("logDateBegin", getParameter("logDateBegin"));
        condition.put("logDateEnd", getParameter("logDateEnd"));
        String opeventModule = getParameter("opeventModule");
        String userName = getParameter("txtBelongTo");
        opeventModule = URLDecoder.decode(opeventModule, "UTF-8");
        userName = URLDecoder.decode(userName, "UTF-8");
        condition.put("opeventModule",opeventModule);
        condition.put("userName",userName);
        //原始实体数据
        List<SysOpeventLog_Query> list = iSysOpeventLogService.querySysOpeventLog(condition);
        //转为list<String[] data>
        List<List<String>> logList = new ArrayList<List<String>>();
        for (int i = 0; i < list.size(); i++) {
            SysOpeventLog_Query log =  list.get(i);
            List<String> colunms = new ArrayList<String>();
            colunms.add(log.getOpeventDate().toString());
            colunms.add(log.getOpeventModule().toString());
            colunms.add(log.getOpeventAction().toString());
            colunms.add(log.getOpeventUserAccount().toString());
            colunms.add(log.getOpeventUserName().toString());
            colunms.add(log.getOpeventIp().toString());
            logList.add(colunms);
        }
        List<String> head = new ArrayList<String>();
        head.add("操作时间");
        head.add("操作名称");
        head.add("操作内容");
        head.add("操作者账号");
        head.add("操作者姓名");
        head.add("操作IP");
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmm");
        String filename = "exportLog"+sdf.format(new Date());
        String path = getRootPath()+"exportLog";
        String filePath = FileUtil.contact(path,filename);
        File csvFile = iSysOpeventLogService.createCSVFile(head, logList, path, filename);
        downFile(response,csvFile.getPath(),filename+".csv");
    }



    private void downFile(HttpServletResponse response, String path,String filename) {
        File file = new File(path);
        try {
            if (file.exists()) {


                InputStream ins = new FileInputStream(file);
                BufferedInputStream bins = new BufferedInputStream(ins);// 放到缓冲流里面


                OutputStream outs = response.getOutputStream();// 获取文件输出IO流
                BufferedOutputStream bouts = new BufferedOutputStream(outs);
                response.setContentType("application/force-download");// 设置强制下载不打开
                response.addHeader(
                        "Content-disposition",
                        "attachment;filename="
                                + filename);// 设置头部信息
                int bytesRead = 0;
                byte[] buffer = new byte[8192];
                // 开始向网络传输文件流
                while ((bytesRead = bins.read(buffer, 0, 8192)) != -1) {
                    bouts.write(buffer, 0, bytesRead);
                }
                bouts.flush();// 这里一定要调用flush()方法
                ins.close();
                bins.close();
                outs.close();
                bouts.close();
            }
        } catch (IOException e) {
            log.error("文件下载出错", e);
        }finally {
            if(file.exists()){
                file.delete();
            }
        }
    }
}

