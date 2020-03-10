package org.mysise.natplus.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mysise.natplus.common.exception.BaseException;
import org.mysise.natplus.common.exception.Constant;

import java.io.Serializable;

/**
 * <p>
 *  通用数据返回接口
 * <p>
 * @author fanwenjie@cvte.com
 * @since  oversea v1.0.0 2019/11/14 11:13 上午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> implements Serializable {

    /**
     * 状态码，默认成功为0
     */
    private String code = Constant.SUCCESS;

    /**
     *  消息内容
     */
    private String message;

    /**
     * 结果集
     */
    private T result;

    public Result(T result){
        this.result = result;
    }

    public Result(String code, String message){
        this.code = code;
        this.message = message;
    }
    public Result(BaseException baseCode){
        this.code=baseCode.getCode();
        this.message=baseCode.getMessage();
    }
}
