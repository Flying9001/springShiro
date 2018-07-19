package com.ljq.demo.shiro.common.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description: 日志封装类
 * @Author: junqiang.lu
 * @Date: 2018/7/19
 */
public class Log {

    private static final Logger logger = LoggerFactory.getLogger(Log.class);

    //Log输出所在类
    private static String className;

    //Log输出所在方法
    private static String methodName;

    //Log输出所行号
    private static int lineNumber;

    /**
     * 获取输出所在位置的信息className methodName lineNumber
     * @param elements
     */
    private static void getDetail(StackTraceElement[] elements){
        className = elements[1].getClassName();
        methodName = elements[1].getMethodName();
        lineNumber = elements[1].getLineNumber();
    }

    /**
     * 创建Log输出的基本信息
     * @param log
     * @return
     */
    private static String createLog(String log){
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append(className);
        buffer.append(".java ");
        buffer.append(methodName);
        buffer.append("()");
        buffer.append(" line:");
        buffer.append(lineNumber);
        buffer.append("] ");
        buffer.append(log);
        return buffer.toString();
    }

    public static void debug(String message){
        getDetail(new Throwable().getStackTrace());
        logger.debug(createLog(message));
    }

    public static void debug(String message, Exception e){
        getDetail(new Throwable().getStackTrace());
        logger.debug(createLog(message), e);
    }

    public static void info(String message){
        getDetail(new Throwable().getStackTrace());
        logger.info(createLog(message));
    }

    public static void info(String message, Exception e){
        getDetail(new Throwable().getStackTrace());
        logger.info(createLog(message), e);
    }

    public static void warn(String message){
        getDetail(new Throwable().getStackTrace());
        logger.warn(createLog(message));
    }

    public static void warn(String message, Exception e){
        getDetail(new Throwable().getStackTrace());
        logger.warn(createLog(message), e);
    }

    public static void error(String message){
        getDetail(new Throwable().getStackTrace());
        logger.error(createLog(message));
    }

    public static void error(String message, Exception e){
        getDetail(new Throwable().getStackTrace());
        logger.error(createLog(message), e);
    }

}
