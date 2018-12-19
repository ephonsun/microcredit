package banger.controller;

import banger.common.BaseController;
import banger.common.constant.GlobalConst;
import banger.framework.component.dataimport.IImportResult;
import banger.framework.component.dataimport.context.ColumnInfo;
import banger.framework.util.FileUtil;
import banger.framework.util.StringUtil;
import banger.moduleIntf.ISystemModule;
import banger.service.intf.IDeptImportService;
import banger.service.intf.IUserImportService;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: zhangfp
 * Date: 15-6-9
 * Time: 下午4:52
 * To change this template use File | Settings | File Templates.
 */
public class UserImportController extends BaseController {
    private File uplodeFile;
    private String fullFilename;		//上传的本地文件名

    private IDeptImportService deptImportService;
    private IUserImportService userImportService;
    private ISystemModule systemModuleImpl;

    /**
     * 文件上传
     * @return
     * @throws java.io.IOException
     */
    public void doUpload() throws IOException {
        fullFilename = URLDecoder.decode(getRequest().getParameter("fullFilename"), "UTF-8");
        HttpServletResponse response = getResponse();
        ((ServletRequest) response).setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String timePath = new SimpleDateFormat("yyyyMMddHmsS").format(Calendar.getInstance().getTime());
        String savePath = FileUtil.contact(getRootPath(), "temp_file"+File.separator+"import"+File.separator+timePath);
        File file = new File(savePath);
        if (!file.exists()) {// 文件不存在则创建
            file.mkdirs();
        }
        String saveFilename =  FileUtil.contact(savePath,"importUser.xls");
        FileUtils.copyFile(uplodeFile,new File(saveFilename));
        uplodeFile.delete();
        renderText(saveFilename);
    }
    /**
     * 下载Excel模板
     * @return
     */
    public void downloadExcelFile() {
    	String filename = FileUtil.contact(getRootPath(), "template"+File.separator+"importUser.xls");
        File file = new File(filename);
        if (file.exists()) {
            try {
                FileInputStream fis = new FileInputStream(file);
                getResponse().addHeader(
                        "Content-Disposition",
                        "attachment;filename="
                                + URLEncoder.encode("用户导入模板.xls",
                                "utf-8").replace("+", "%20"));
                getResponse().setContentType("xls/*"); // 设置返回的文件类型
                OutputStream output = getResponse().getOutputStream(); // 得到向客户端输出二进制数据的对象
                BufferedInputStream bis = new BufferedInputStream(fis);// 输入缓冲流
                BufferedOutputStream bos = new BufferedOutputStream(output);// 输出缓冲流
                byte data[] = new byte[4096];// 缓冲字节数
                int size = 0;
                size = bis.read(data);
                while (size != -1) {
                    bos.write(data, 0, size);
                    size = bis.read(data);
                }
                bis.close();
                bos.flush();// 清空输出缓冲流
                bos.close();
                output.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                log.error("下载Excel模板 error:"+e.getMessage(),e);
            } catch (IOException e) {
                e.printStackTrace();
                log.error("下载Excel模板 error:"+e.getMessage(),e);
            }
        }
    }

    /**
     * 得到表头信息
     * @return
     * @throws IOException
     */
    public String getExcelHeadData(HttpServletRequest request) throws IOException {
        String saveFilename = URLDecoder.decode(this.getParameter("saveFilename"), "UTF-8");
        List<String> headList = userImportService.getImportExcelHead(saveFilename);
        List<ColumnInfo> list = new ArrayList<ColumnInfo>();
        Map<String,String> map = new HashMap<String,String>();
        map.put("用户名(必填)","userAccount");
        map.put("用户名称(必填)","userName");
        map.put("归属机构编码(必填)","belongDeptCode");
        map.put("角色名称(必填)","roleName");
        map.put("产品类型","prodClassStr");
        for(int i=0;i<headList.size();i++){
            String head = headList.get(i);
            if(map.containsKey(head)){
                String field = (map.containsKey(head))?map.get(head):"";
                ColumnInfo col = new ColumnInfo(i,head,field);
                list.add(col);
            }else{
                renderText("warning");
                return null;
            }

        }
        setAttribute("columnInfos",list);
        setAttribute("headList",headList);
        return SUCCESS;
    }

    /**
     * 执行用户导入
     * @throws IOException
     */
    public void doImportUser(HttpServletRequest request) throws IOException {
        String leftColumns = this.getParameter("leftColumns");
        String rightFields = this.getParameter("rightFields");
        String saveFilename = URLDecoder.decode(this.getParameter("saveFilename"), "UTF-8");
        String[] excelColumns = leftColumns.split(",");
        String[] fields = rightFields.split(",");
        List<ColumnInfo> columns = new ArrayList<ColumnInfo>();
        for(int i=0;i<excelColumns.length;i++){
            ColumnInfo info = new ColumnInfo();
            info.setIndex(i);
            info.setColumnName(excelColumns[i]);
            if(!StringUtil.isNullOrEmpty(fields[i]))info.setFieldName(fields[i]);
            columns.add(info);
        }

        Integer userId = getLoginInfo().getUserId();
        String userDeptId = getLoginInfo().getDeptId().toString();
        userImportService.importExcel(userId.toString(),userDeptId, saveFilename, columns);
    }

    /**
     * 得到机构导入结果信息
     */
    public void getImportResult(){
        Integer userId = getLoginInfo().getUserId();
        JSONObject jo = new JSONObject();
        IImportResult result = userImportService.getImportResultByUserId(userId.toString());
        if(result!=null){
            jo.put("total", result.getRowCount());
            jo.put("success", result.getSuccessCount());
            jo.put("insert", result.getInsertCount());
            jo.put("update", result.getUpdateCount());
            jo.put("fail", result.getFailCount());
            jo.put("filename", result.getFailFilename());
            jo.put("reportFileName", result.getReportFileName());
        }
        renderText(jo.toString());
    }
    
    /**
     * 下载Excel模板
     * @return
     * @throws IOException 
     */
    public void downloadFailFile(HttpServletRequest request) throws IOException {
    	String filename = URLDecoder.decode(this.getParameter("filename"), "UTF-8");
        File file = new File(filename);
        if (file.exists()) {
            try {
                FileInputStream fis = new FileInputStream(file);
                getResponse().addHeader(
                        "Content-Disposition",
                        "attachment;filename="
                                + URLEncoder.encode("用户导入错误数据.csv",
                                "utf-8").replace("+", "%20"));
                getResponse().setContentType("csv/*"); // 设置返回的文件类型
                OutputStream output = getResponse().getOutputStream(); // 得到向客户端输出二进制数据的对象
                BufferedInputStream bis = new BufferedInputStream(fis);// 输入缓冲流
                BufferedOutputStream bos = new BufferedOutputStream(output);// 输出缓冲流
                byte data[] = new byte[4096];// 缓冲字节数
                int size = 0;
                size = bis.read(data);
                while (size != -1) {
                    bos.write(data, 0, size);
                    size = bis.read(data);
                }
                bis.close();
                bos.flush();// 清空输出缓冲流
                bos.close();
                output.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                log.error("下载Excel模板 error:"+e.getMessage(),e);
            } catch (IOException e) {
                e.printStackTrace();
                log.error("下载Excel模板 error:"+e.getMessage(),e);
            }
        }
    }

    public File getUplodeFile() {
        return uplodeFile;
    }

    public void setUplodeFile(File uplodeFile) {
        uplodeFile = uplodeFile;
    }

    public String getFullFilename() {
        return fullFilename;
    }

    public void setFullFilename(String fullFilename) {
        fullFilename = fullFilename;
    }

    public IDeptImportService getDeptImportService() {
        return deptImportService;
    }

    public void setDeptImportService(IDeptImportService deptImportService) {
        deptImportService = deptImportService;
    }

    public IUserImportService getUserImportService() {
        return userImportService;
    }

    public void setUserImportService(IUserImportService userImportService) {
        userImportService = userImportService;
    }
    public void setSystemModuleImpl(ISystemModule systemModuleImpl) {
        systemModuleImpl = systemModuleImpl;
    }
    /**
     * 得到文件名
     * @param
     * @return
     */
    public String getFilename()
    {
        return FileUtil.getFileName(fullFilename);
    }

    public String autoImportUser(){
    	String opeVentAction = "";
        try{
            String opeVentActionDetail = userImportService.autoImportUserInfo();
            opeVentAction = "自动导入EHR用户:"+opeVentActionDetail;
            renderText("SUCCESS");
        } catch (Exception e) {
            renderText(ERROR);
        } finally {
        	// insert系统日志表   模块名称、操作对象名称、操作者userId、操作者ip地址
        	systemModuleImpl.addSysOpeventLog("用户管理", opeVentAction, 1, GlobalConst.SERVICE_IP);
        }
        return null;
    }
}
