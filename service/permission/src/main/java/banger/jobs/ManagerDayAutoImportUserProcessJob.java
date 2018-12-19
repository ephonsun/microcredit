package banger.jobs;

import banger.common.constant.GlobalConst;
import banger.common.listener.SpringContextUtil;
import banger.framework.util.DateUtil;
import banger.framework.util.FileUtil;
import banger.framework.util.ZipUtil;
import banger.moduleIntf.ISystemModule;
import banger.quartz.job.QuartzJob;
import banger.service.intf.IUserImportService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-5-25
 * Time: 下午5:03
 * To change this template use File | Settings | File Templates.
 */
public class ManagerDayAutoImportUserProcessJob extends QuartzJob {
    protected transient final Log log = LogFactory.getLog(getClass());
    private ISystemModule systemModuleImpl = (ISystemModule) SpringContextUtil.getBean("systemModuleImpl");
    private IUserImportService userImportService = (IUserImportService) SpringContextUtil.getBean("userImportService");

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        if(!GlobalConst.QUARTZ_JOB_ENABLE){
            return;
        }
        String opeVentActionDetail = "";
        try{
        	log.info(DateUtil.getCurrentDate()+":执行每日导入EHR用户数据 begin");
        	// 导入EHR用户
        	opeVentActionDetail = this.userImportService.autoImportUserInfo();
        } catch (Exception e) {
        	log.error("执行每日导入EHR用户数据捕获异常:"+e);
        } finally{
        	//添加系统日志
        	addSystemLog(opeVentActionDetail);
        	log.info(DateUtil.getCurrentDate()+":执行每日导入EHR用户数据 end");
        }
    }

    /**
     * 添加系统日志
     * @param opeVentActionDetail TODO
     */
    private void addSystemLog(String opeVentActionDetail) {
        String opeVentAction = "自动导入EHR用户:"+opeVentActionDetail;
        // 新增系统日志
        // insert系统日志表   模块名称、操作对象名称、操作者userId、操作者ip地址
        this.systemModuleImpl.addSysOpeventLog("用户管理", opeVentAction, 1, GlobalConst.SERVICE_IP);
    }
}
