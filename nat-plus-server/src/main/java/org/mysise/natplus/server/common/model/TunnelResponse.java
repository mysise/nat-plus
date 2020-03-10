package org.mysise.natplus.server.common.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *  隧道信息返回
 * <p>
 *
 * @author fanwenjie
 * @since 2020/3/10 0:21
 */
@Data
public class TunnelResponse implements Serializable {

    private Integer id;

    private String token;

    private String name;

//    private Integer uid;

    /**
     * 1:TCP 2:HTTP
     */
    private Integer type;

    /**
     * 绑定的host
     */
    private String hostName;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
