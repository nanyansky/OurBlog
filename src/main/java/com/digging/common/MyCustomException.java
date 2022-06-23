package com.digging.common;

/**
 * 自定义异常类
 */
public class MyCustomException extends RuntimeException{

    public MyCustomException(String msg)
    {
        super(msg);
    }
}
