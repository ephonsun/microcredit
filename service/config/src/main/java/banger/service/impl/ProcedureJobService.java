package banger.service.impl;

import banger.domain.system.SysAppVersion;
import banger.framework.spring.SpringContext;
import banger.framework.sql.util.SqlHelper;
import banger.service.intf.IProcedureService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


/**
 * Created by XUElw on 2017/10/17.
 */

public class ProcedureJobService implements Job {


    @SuppressWarnings("deprecation")
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try{
            IProcedureService procedureService = (IProcedureService)SpringContext.instance().get("procedureService");
            //过滤重复的数据
            //procedureService.countAutoTableCustId();
            //插入的存储过程
            long start = System.currentTimeMillis();
            String insertPro = procedureService.generateInsertProcedure().toString();
            SqlHelper.execute(insertPro);
            SqlHelper.execute("call PRO_INSERT_BASIC()");
            long end = System.currentTimeMillis();
            System.out.println("=========insert==========="+(end - start)/1000);

            //更新的存储过程
            start = System.currentTimeMillis();
            String updatePro = procedureService.generateUpdateProcedure().toString();
            SqlHelper.execute(updatePro);
            SqlHelper.execute("call PRO_UPDATE_BASIC()");
            end = System.currentTimeMillis();
            System.out.println("==========update=========="+(end - start)/1000);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
