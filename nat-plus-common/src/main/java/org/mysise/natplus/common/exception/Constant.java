package org.mysise.natplus.common.exception;

/**
 * <p>
 *  常量  todo 错误码需要重新设计
 * <p>
 * @author fanwenjie@cvte.com
 * @since  oversea v1.0.0 2019/11/14 11:21 上午
 */
public class Constant {

    /**
     *  默认返回
     */
    public static final String SUCCESS  = "0";

    /**
     * 程序未知异常
     */
    public static final String SERVER_ERROR = "-1";

    /**
     *  数据库异常
     */
    public static final String SQL_ERROR = "-2";

    /**
     * 是否删除
     */
    public interface DELETED{
        int YES = 1;
        int NO  = 0;
    }
}
