package banger.service.impl;

import banger.dao.intf.ISysOpeventLogDao;
import banger.domain.system.SysOpeventLog;
import banger.domain.system.SysOpeventLog_Query;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import banger.service.intf.ISysOpeventLogService;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysOpeventLogService implements ISysOpeventLogService {

	@Autowired
	private ISysOpeventLogDao sysOpeventLogDao;

	public void addSysOpereventLog(SysOpeventLog sysOpeventLog) {
		sysOpeventLogDao.addSysOpeventLog(sysOpeventLog);
	}
	
	public void deleteAllSysOperentLog() {
		Map<String, Object> cond =  new HashMap<String, Object>();			
		sysOpeventLogDao.delSysOpeventLog(cond);
	}

	public void deleteSysOperentLogByUser(int userId) {
		Map<String, Object> cond =  new HashMap<String, Object>();		
		cond.put("UserId", userId);	
		sysOpeventLogDao.delSysOpeventLog(cond);
	}

	public void deleteSysOperentLogByDate(Date beginDate, Date endDate) {
		Map<String, Object> cond =  new HashMap<String, Object>();		
		cond.put("BeginDate", beginDate);	
		cond.put("EndDate", endDate);
		sysOpeventLogDao.delSysOpeventLog(cond);
	}
	
	public IPageList<SysOpeventLog_Query> querySysOpeventLog(IPageSize page,Map<String,Object> cond){
		return sysOpeventLogDao.querySysOpeventLog(page, cond);
	}

    public List<SysOpeventLog_Query> querySysOpeventLog(Map<String,Object> cond){
        return sysOpeventLogDao.querySysOpeventLog(cond);
    }

    public List<SysOpeventLog> getOpeventModule() {
        return sysOpeventLogDao.getOpeventModule();
    }

    public IPageList<SysOpeventLog_Query> queryLogByCondition(Map<String, Object> condition, IPageSize page) {
        return sysOpeventLogDao.queryLogByCondition(condition,page);
    }

	/**
	 * CSV文件生成方法
	 *
	 * @param head
	 * @param dataList
	 * @param outPutPath
	 * @param filename
	 * @return
	 */
	public File createCSVFile(List<String> head, List<List<String>> dataList, String outPutPath, String filename) throws IOException {
		File csvFile = null;
		BufferedWriter csvWtriter = null;
		try {
			csvFile = new File(outPutPath + File.separator + filename + ".csv");
			File parent = csvFile.getParentFile();
			if (parent != null && !parent.exists()) {
				parent.mkdirs();
			}
			csvFile.createNewFile();

			// GB2312使正确读取分隔符","
			csvWtriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
					csvFile), "GB2312"), 1024);
			// 写入文件头部
			writeRow(head, csvWtriter);

			// 写入文件内容
			for (List<String> row : dataList) {
				writeRow(row, csvWtriter);
			}
			csvWtriter.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				csvWtriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return csvFile;
	}


	/**
	 * 写一行数据方法
	 *
	 * @param row
	 * @param csvWriter
	 * @throws IOException
	 */
	public void writeRow(List<String> row, BufferedWriter csvWriter) throws IOException {
		// 写入文件头部
		for (String data : row) {
			StringBuffer sb = new StringBuffer();
			String rowStr = sb.append("\"").append(data).append("\",").toString();
			csvWriter.write(rowStr);
		}
		csvWriter.newLine();
	}
}
