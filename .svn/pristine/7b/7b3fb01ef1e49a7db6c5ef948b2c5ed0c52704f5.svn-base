package banger.common.aspect;

import banger.framework.sql.util.SqlHelper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * Created by zhusw on 2017/12/19.
 */
@Component
@Aspect
public class SqlTransactionAspect {

    @Pointcut(value = "@annotation(banger.framework.sql.command.SqlTransaction)")
    private void pointcut() {

    }

    @Around(value = "pointcut()")
    public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        boolean errorState = false;
        try {
            System.out.println("数据库事务开始");
            SqlHelper.instance().beginTransaction();
            return proceedingJoinPoint.proceed();   //执行该方法
        }catch (Exception e){
            errorState = true;
            throw e;
        }finally {
            System.out.println("数据库事务结束");
            SqlHelper.instance().endTransaction(errorState);
        }
    }

}
