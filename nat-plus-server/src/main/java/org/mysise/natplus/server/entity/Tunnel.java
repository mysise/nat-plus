package org.mysise.natplus.server.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 隧道/通道
 * </p>
 *
 * @author fanwenjie
 * @since 2020-03-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Tunnel extends BaseEntity {

    private static final long serialVersionUID = 1L;

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


}
