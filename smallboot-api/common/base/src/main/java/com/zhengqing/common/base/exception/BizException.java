package com.zhengqing.common.base.exception;

/**
 * <p> 业务异常 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/8/1 18:07
 */
public class BizException extends MyException {

    public BizException(String message) {
        super(message);
    }

    public BizException(String message, Throwable e) {
        super(message, e);
    }

}
