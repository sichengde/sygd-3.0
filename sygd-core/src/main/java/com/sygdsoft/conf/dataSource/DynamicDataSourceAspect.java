package com.sygdsoft.conf.dataSource;

import com.sygdsoft.service.HotelService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    @Pointcut("execution(* com.sygdsoft.controller.*.*(..))")
    private void pointcut() {
    }
    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);
    /*@Autowired
    HotelService hotelService;

    @Before("@annotation(ds)")
    public void changeDataSource(JoinPoint point, TargetDataSource ds) throws Throwable {
        String dsId = ds.name();
        if (!DynamicDataSourceContextHolder.containsDataSource(dsId)) {
            logger.error("数据源[{}]不存在，使用默认数据源 > {}", ds.name(), point.getSignature());
        } else {
            logger.debug("Use DataSource : {} > {}", ds.name(), point.getSignature());
            DynamicDataSourceContextHolder.setDataSourceType(ds.name());
        }
    }

    @Before("pointcut()")
    public void changeDataSource(JoinPoint point) throws Throwable {
        String dsId = hotelService.getCurrentHotel();
        if (!DynamicDataSourceContextHolder.containsDataSource(dsId)) {
            logger.error("数据源[{}]不存在，使用默认数据源 > {}", dsId, point.getSignature());
        } else {
            logger.debug("Use DataSource : {} > {}", dsId, point.getSignature());
            DynamicDataSourceContextHolder.setDataSourceType(dsId);
        }
    }

    @After("pointcut()")
    public void restoreDataSource(JoinPoint point) {
        String dsId = hotelService.getCurrentHotel();
        logger.debug("Revert DataSource : {} > {}", dsId, point.getSignature());
        DynamicDataSourceContextHolder.clearDataSourceType();
    }*/
   @AfterThrowing(pointcut = "pointcut()", throwing = "e")
   public void restoreDataSource(Exception e) throws Exception{
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
            throw new Exception(str);
        }
        e.printStackTrace();
        throw exception;
    }
}
