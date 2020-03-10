package org.mysise.natplus.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.mysise.natplus.server.common.model.TunnelRequest;
import org.mysise.natplus.server.common.model.TunnelResponse;
import org.mysise.natplus.server.common.model.TunnelSearch;
import org.mysise.natplus.server.entity.Tunnel;

import java.util.List;

/**
 * <p>
 * 隧道/通道 服务类
 * </p>
 *
 * @author fanwenjie
 * @since 2020-03-07
 */
public interface ITunnelService extends IService<Tunnel> {

    /**
     * <p>
     *  判断令牌是否存在
     * <p>
     *
     * @author fanwenjie
     * @since 2020/3/9 23:32
     */
    Boolean tokenExist(String token);
    /**
     * <p>
     *  通过域名查询
     * <p>
     *
     * @author fanwenjie
     * @since 2020/3/9 23:32
     */
    Tunnel queryTokenByHostName(String hostName);

    /**
     * <p>
     *  保存
     * <p>
     *
     * @author fanwenjie
     * @since 2020/3/10 0:13
     */
    Integer save(TunnelRequest request);

    /**
     * <p>
     *  修改隧道信息
     * <p>
     *
     * @author fanwenjie
     * @since 2020/3/10 0:35
     */
    Integer update(TunnelRequest request);
    
    /**
     * <p>
     *  列表返回查询
     * <p>
     *
     * @author fanwenjie
     * @since 2020/3/10 0:23
     */
    List<TunnelResponse> listTunnel(TunnelSearch request);
}
