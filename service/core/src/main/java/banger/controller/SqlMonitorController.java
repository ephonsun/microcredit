package banger.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import banger.common.BaseController;
import banger.common.interceptor.NoLoginInterceptor;
import banger.common.tools.JsonUtil;
import banger.framework.component.dataexport.ExcelExport;
import banger.framework.component.dataexport.ExportMode;
import banger.framework.component.dataexport.IExcelBook;
import banger.framework.component.dataexport.IExcelSheet;
import banger.framework.pagesize.IPageList;
import banger.framework.sql.mapping.ISqlItemCount;
import banger.framework.util.FileUtil;
import banger.service.intf.ISqlMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/monitor")
public class SqlMonitorController extends BaseController {
	private static final long serialVersionUID = -8178666322553150022L;

	@Autowired
	private ISqlMonitorService sqlMonitorService;

	/**
	 * 查询SQL执行时间数据
	 */
	@NoLoginInterceptor
	@RequestMapping("/getSqlMonitorListPage")
	public String getSqlMonitorListPage(){
		return "system/vm/sqlMonitorList";
	}

	/**
	 * 查询SQL执行时间数据
	 * @param request
	 * @param response
	 */
	@NoLoginInterceptor
	@RequestMapping("/getSqlMoniterListData")
	public void getSqlMoniterListData(HttpServletRequest request, HttpServletResponse response){
		IPageList<ISqlItemCount> pageList = this.sqlMonitorService.getSqlMonitorList(this.getPage());
		String jsonString = JsonUtil.toJson(pageList, "id", "sqlId,averageTime,exceCount,totalTime,maxTime,minTime,lastTime").toString();
		this.renderText(jsonString);
	}

	/**
	 * 导出SQL执行时间数据
	 * @param request
	 * @param response
	 */
	@NoLoginInterceptor
	@RequestMapping("/exportSqlMonitorData")
	public void exportSqlMonitorData(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try{
			List<ISqlItemCount> sqlList = sqlMonitorService.getAllSqlMonitorList();
	        if(sqlList!=null && sqlList.size()>0){
	        	String filename = "sqlExecTime_"+new Date().getTime()+".xls";
	    		String path = FileUtil.contact(this.getRootPath(), "tempFile");
	    		new File(path).mkdirs();
	    		String fullFileName = FileUtil.contact(path, filename);
	            IExcelBook book = ExcelExport.createBook(fullFileName, ExportMode.POI);
	            String[] head = new String[]{"SQLID","平均执行时间","执行次数","总共执行时间","最大执行时间","最小执行时间","最后执行时间"};
	            IExcelSheet sheet = book.addSheet("用户数据",head);
	            for(ISqlItemCount item : sqlList){
	            	sheet.addRow(new String[]{item.getSqlId(),item.getAverageTime()+"",item.getExceCount()+"",item.getTotalTime()
	            			+"",item.getMaxTime()+"",item.getMinTime()+"",item.getLastTime()+""});
	            }
	            
	            book.write();
	            
	            this.renderText(filename);
	        }
		}
        catch(Exception e){
			
		}
	}

	/**
	 * 下载SQL执行时间数据
	 * @param request
	 * @param response
	 */
	@NoLoginInterceptor
	@RequestMapping("/downloadExportExcelFile")
	public void downloadExportExcelFile(HttpServletRequest request, HttpServletResponse response) {
		try{
			String filename = this.getParameter("filename");
			String path = FileUtil.contact(this.getRootPath(), "tempFile");
			String fullFileName = FileUtil.contact(path, filename);
			
			ServletOutputStream out = this.getResponse().getOutputStream();
			this.getResponse().setHeader("Content-Disposition","attachment; filename="+ java.net.URLEncoder.encode("导出的SQL执行时间数据.xls","UTF-8"));
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fullFileName));
			BufferedOutputStream bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length)))
			{
				bos.write(buff, 0, bytesRead);
			}
			bis.close();
			bos.close();
		}catch(Exception e){
			
		}
	}
}
