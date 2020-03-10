package org.mysise.natplus.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *  基础类
 * <p>
 *
 * @author fanwenjie
 * @since 2020/3/6 23:58
 */
@Data
public class BaseEntity implements Serializable {

    /**
     *  自增，本项目默认为自增，如果不是自增的请不要继承该接口
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}