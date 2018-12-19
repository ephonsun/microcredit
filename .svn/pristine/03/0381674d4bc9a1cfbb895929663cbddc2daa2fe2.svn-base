package banger.controller;

import banger.common.BaseController;
import banger.framework.component.dataimport.IImportResult;
import banger.framework.component.dataimport.context.ColumnInfo;
import banger.framework.component.progress.ProgressBar;
import banger.framework.component.progress.IProgressBarManager;
import banger.framework.util.FileUtil;
import banger.framework.util.StringUtil;
import banger.service.intf.IDeptImportService;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 15-4-27.
 */
public class DeptImportController  extends BaseController {
	private static final long serialVersionUID = 4036984726200363553L;
	private IDeptImportService deptImportService;
    private IProgressBarManager progressBarManager;
    private File uplodeFile;
    private String fullFilename;		//上传的本地文件名

    public void setDeptImportService(IDeptImportService deptImportService) {
        deptImportService = deptImportService;
    }

    public void setProgressBarManager(IProgressBarManager progressBarManager) {
		progressBarManager = progressBarManager;
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

    /**
     * 得到文件名
     * @param
     * @return
     */
    public String getFilename()
        {
            return FileUtil.getFileName(fullFilename);
        }

    /**
     * 文件上传
     * @return
     * @throws java.io.IOException
     */
    public void doUpload(HttpServletRequest request) throws IOException {
        fullFilename = URLDecoder.decode(this.getParameter("fullFilename"), "UTF-8");
        HttpServletResponse response = getResponse();
        ((ServletRequest) response).setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String timePath = new SimpleDateFormat("yyyyMMddHmsS").format(Calendar.getInstance().getTime());
        String savePath = FileUtil.contact(getRootPath(), "temp_file"+File.separator+"import"+File.separator+timePath);
        File file = new File(savePath);
        if (!file.exists()) {// 文件不存在则创建
            file.mkdirs();
        }
        String saveFilename =  FileUtil.contact(savePath,"importDept.xls");
        FileUtils.copyFile(uplodeFile,new File(saveFilename));
        uplodeFile.delete();
        renderText(saveFilename);
    }

    public String getExcelHeadData(HttpServletRequest request) throws IOException {
    	String saveFilename = URLDecoder.decode(this.getParameter("saveFilename"), "UTF-8");
        List<String> headList = deptImportService.getImportExcelHead(saveFilename);
        List<ColumnInfo> list = new ArrayList<ColumnInfo>();
        Map<String,String> map = new HashMap<String,String>();
        map.put("机构编码(必填)","deptCode");
        map.put("机构名称(必填)","deptName");
        map.put("上级机构编码(必填)","parentDeptCode");
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
     * 执行机构导入
     * @throws IOException
     */
    public void doImportDept(HttpServletRequest request) throws IOException {
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
    	deptImportService.importExcel(userId.toString(), saveFilename, columns);
    }
    
    /**
     * 得到机构导入进度信息
     */
    public void getImportProgress(HttpServletRequest request){
    	String progressId = this.getParameter("progressId");
    	ProgressBar bar = progressBarManager.getProgressBarById(progressId);
    	JSONObject jo = new JSONObject();
    	if(bar!=null){
	    	jo.put("progressId", bar.getId());
	    	jo.put("total", bar.getTotal());
	    	jo.put("rate", bar.getRate());
	    	jo.put("current", bar.getCurrent());
	    	jo.put("isCompleted", bar.isCompleted());
	    	jo.put("isClosed", bar.isClosed());
    	}
        renderText(jo.toString());
    }
    
    /**
     * 得到机构导入结果信息
     */
    public void getImportResult(){
    	Integer userId = getLoginInfo().getUserId();
    	JSONObject jo = new JSONObject();
    	IImportResult result = deptImportService.getImportResultByUserId(userId.toString());
    	if(result!=null){
	    	jo.put("total", result.getRowCount());
	    	jo.put("success", result.getSuccessCount());
	    	jo.put("insert", result.getInsertCount());
	    	jo.put("update", result.getUpdateCount());
	    	jo.put("fail", result.getFailCount());
	    	jo.put("filename", result.getFailFilename());
    	}
    	renderText(jo.toString());
    }
    
    /**
     * 下载Excel模板
     * @return
     */
    public void downloadExcelFile() {
    	String filename = FileUtil.contact(getRootPath(), "template"+File.separator+"importDept.xls");
        File file = new File(filename);
        if (file.exists()) {
            try {
                FileInputStream fis = new FileInputStream(file);
                getResponse().addHeader(
                        "Content-Disposition",
                        "attachment;filename="
                                + URLEncoder.encode("机构导入模板.xls",
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
                                + URLEncoder.encode("机构导入错误数据.csv",
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
}
