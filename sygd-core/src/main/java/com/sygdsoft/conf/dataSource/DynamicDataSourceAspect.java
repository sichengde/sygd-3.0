package com.sygdsoft.conf.dataSource;

import com.alibaba.fastjson.JSON;
import com.sygdsoft.model.ExceptionRecord;
import com.sygdsoft.service.ExceptionRecordService;
import com.sygdsoft.service.OtherParamService;
import com.sygdsoft.service.UserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;

/**
 * 切换数据源Advice
 *
 * @author 单红宇(365384722)
 * @myblog http://blog.csdn.net/catoop/
 * @create 2016年1月23日
 */
@Aspect
@Order(-1)// 保证该AOP在@Transactional之前执行
@Component
public class DynamicDataSourceAspect {
    @Autowired
    UserService userService;

    //private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);
    @Autowired
    OtherParamService otherParamService;
    @Autowired
    ExceptionRecordService exceptionRecordService;

    @Pointcut("execution(* com.sygdsoft.controller.*.*(..))")
    private void pointcut() {
    }



     @Before("@annotation(ds)")
     public void changeDataSource(JoinPoint point, HotelGroup ds) throws Throwable {

         if (DynamicDataSourceContextHolder.containsDataSource("vip")){
             DynamicDataSourceContextHolder.setDataSourceType("vip");
         }
     }

     @After("@annotation(ds)")
    public void restoreDataSource(JoinPoint point, HotelGroup ds) throws Exception {
        if (DynamicDataSourceContextHolder.containsDataSource("vip")) {
            DynamicDataSourceContextHolder.clearDataSourceType();
        }
    }

    /*@Before("pointcut()")
     public void changeDataSource(JoinPoint point) throws Throwable {
         String dsId = userService.getCurrentHotel();
         if (!DynamicDataSourceContextHolder.containsDataSource(dsId)) {
             //logger.error("数据源[{}]不存在，使用默认数据源 > {}", dsId, point.getSignature());
         } else {
             //logger.debug("Use DataSource : {} > {}", dsId, point.getSignature());
             DynamicDataSourceContextHolder.setDataSourceType(dsId);
         }
     }*/

     /*@After("pointcut()")
     public void restoreDataSource(JoinPoint point) throws Exception{
         //String dsId = userService.getCurrentHotel();
         //logger.debug("Revert DataSource : {} > {}", dsId, point.getSignature());
         DynamicDataSourceContextHolder.clearDataSourceType();}*/

    @AfterThrowing(pointcut = "pointcut()", throwing = "e")
    public void restoreDataSource(JoinPoint point,Exception e) throws Exception {
        Exception exception;

       /*尝试输出mybatis异常*/
        try {
            String message = ((InvocationTargetException) e).getTargetException().getMessage();
            if (((InvocationTargetException) e).getTargetException().getClass() == DuplicateKeyException.class) {
                exception = new Exception("{" + message.substring(message.indexOf(" Duplicate entry \'") + 18, message.indexOf("\' for key")) + "}已经存在，不可重复");
            } else {
                exception = new Exception(message);
            }
        } catch (Exception e1) {
            /*抛出普通异常，抛出代码位置*/
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw, true));
            String str = sw.toString();
            /*记录异常进数据库*/
            ExceptionRecord exceptionRecord = new ExceptionRecord();
            exceptionRecord.setHotelId("本地");
            exceptionRecord.setException(str);
            exceptionRecord.setParam(JSON.toJSONString(point.getArgs()));
            exceptionRecord.setMethod(point.toString());
            exceptionRecordService.add(exceptionRecord);
            throw new Exception(str);
        }
        //e.printStackTrace();
        throw exception;
    }
}
