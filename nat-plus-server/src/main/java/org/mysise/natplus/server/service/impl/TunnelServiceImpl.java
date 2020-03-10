package org.mysise.natplus.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.mysise.natplus.common.exception.CommonCode;
import org.mysise.natplus.common.exception.SqlException;
import org.mysise.natplus.common.utils.DtoUtils;
import org.mysise.natplus.server.common.model.TunnelRequest;
import org.mysise.natplus.server.common.model.TunnelResponse;
import org.mysise.natplus.server.common.model.TunnelSearch;
import org.mysise.natplus.server.entity.Tunnel;
import org.mysise.natplus.server.mapper.TunnelMapper;
import org.mysise.natplus.server.service.ITunnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 隧道/通道 服务实现类
 * </p>
 *
 * @author fanwenjie
 * @since 2020-03-07
 */
@Service
public class TunnelServiceImpl extends ServiceImpl<TunnelMapper, Tunnel> implements ITunnelService {

    @Autowired
    private TunnelMapper tunnelMapper;

    @Override
    public Boolean tokenExist(String token) {
        QueryWrapper<Tunnel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("token", token);
        Tunnel tunnel = tunnelMapper.selectOne(queryWrapper);
        return tunnel != null;
    }

    @Override
    public Tunnel queryTokenByHostName(String hostName) {
        QueryWrapper<Tunnel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("host_name", hostName);
        return tunnelMapper.selectOne(queryWrapper);
    }

    @Override
    public Integer save(TunnelRequest request) {

        Tunnel tunnel = queryTokenByHostName(request.getHostName());
        if(tunnel != null){
            throw new SqlException(CommonCode.SQL_INSERT_FAIL, "此域名已存在绑定的隧道信息");
        }
        Tunnel entity = DtoUtils.map(request, Tunnel.class, false);
        LocalDateTime localDateTime = LocalDateTime.now();
        entity.setCreateTime(localDateTime);
        entity.setUpdateTime(localDateTime);
        return tunnelMapper.insert(entity);
    }

    @Override
    public Integer update(TunnelRequest request) {
        Tunnel tunnel = queryTokenByHostName(request.getHostName());
        if(tunnel != null){
            throw new SqlException(CommonCode.SQL_INSERT_FAIL, "此域名已存在绑定的隧道信息");
        }
        Tunnel entity = tunnelMapper.selectById(request.getId());
        LocalDateTime localDateTime = LocalDateTime.now();
        entity.setHostName(request.getHostName());
        entity.setName(request.getName());
        entity.setType(request.getType());
        entity.setUpdateTime(localDateTime);
        return tunnelMapper.updateById(entity);
    }

    @Override
    public List<TunnelResponse> listTunnel(TunnelSearch request) {
        QueryWrapper<Tunnel> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(request.getHostName())){
            queryWrapper.eq("host_name", request.getHostName());
        }
        if(StringUtils.isNotEmpty(request.getToken())){
            queryWrapper.eq("token", request.getToken());
        }
        if(StringUtils.isNotEmpty(request.getName())){
            queryWrapper.eq("name", request.getName());
        }
        if(request.getType() != null){
            queryWrapper.eq("type", request.getType());
        }
        List<Tunnel> result = tunnelMapper.selectList(queryWrapper);
        return DtoUtils.mapList(result, TunnelResponse.class, false);
    }


}
