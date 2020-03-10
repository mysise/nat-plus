package org.mysise.natplus.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *  业务异常
 * <p>
 * @author fanwenjie@cvte.com
 * @since  oversea v1.0.0 2019/11/14 7:10 下午
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BizException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    protected String errorCode;
    /**
     * 错误信息
     */
    protected String errorMsg;

    public BizException() {
        super();
    }

    public BizException(BaseException baseException) {
        super(baseException.getCode());
        this.errorCode = baseException.getCode();
        this.errorMsg = baseException.getMessage();
    }

    public BizException(BaseException baseException, Throwable cause) {
        super(baseException.getCode(), cause);
        this.errorCode = baseException.getCode();
        this.errorMsg = baseException.getMessage();
    }

    public BizException(String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
    }

    public BizException(String errorCode, String errorMsg) {
        super(errorCode);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public BizException(String errorCode, String errorMsg, Throwable cause) {
        super(errorCode, cause);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }




    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
