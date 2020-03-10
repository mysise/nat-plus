package org.mysise.natplus.common.exception;

/**
 * <p>
 *  基础错误出口类
 * <p>
 * @author fanwenjie@cvte.com
 * @since  oversea v1.0.0 2019/11/14 7:12 下午
 */
public interface BaseException {

    /**
     *  获取错误码
     */
    String getCode();

    /**
     *  获取错误信息
     */
    String getMessage();
}
