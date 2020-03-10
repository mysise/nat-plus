package org.mysise.natplus.common.exception;


/**
 * <p>
 *  公用错误枚举类
 * <p>
 * @author fanwenjie@cvte.com
 * @since  oversea v1.0.0 2019/11/14 7:15 下午
 */
public enum CommonCode implements BaseException {
    SUCCESS(Constant.SUCCESS,"ok"),
    SERVER_ERROR(Constant.SERVER_ERROR,"server fail"),
    SQL_INSERT_FAIL(Constant.SQL_ERROR,"sql insert execute fail"),
    SQL_UPDATE_FAIL(Constant.SQL_ERROR,"sql update execute fail"),
    SQL_DELETE_FAIL(Constant.SQL_ERROR,"sql delete execute fail"),
    BIZ_DATA_REGION_SAME(Constant.SQL_ERROR,"name or nameEn or namePinYin is same"),
    BIZ_DATA_REGION_PID_NOT_FIND(Constant.SQL_ERROR,"pid not find"),
    BIZ_PARAM_FAIL(Constant.SQL_ERROR,"param has fail"),
    ;



    private String code;
    private String message;

    CommonCode(String code, String message){
        this.code = code;
        this.message = message;
    }
    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
