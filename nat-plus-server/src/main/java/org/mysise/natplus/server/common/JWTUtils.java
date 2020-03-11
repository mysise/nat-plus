package org.mysise.natplus.server.common;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * <p>
 *  jwt 工具类
 * <p>
 *
 * @author fanwenjie
 * @since 2020/3/11 23:43
 */
@Component
@Data
@Slf4j
@ConfigurationProperties(value = "jwt")
public class JWTUtils {

    private String key;

    private Long ttl;


    /**
     * <p>
     *  生成jwt-token
     * <p>
     *
     * @author fanwenjie
     * @since 2020/3/11 23:49
     */
    public String createToken(){
        return key;
    }


    @PostConstruct
    public void run(){
        log.info(key);
    }
}
