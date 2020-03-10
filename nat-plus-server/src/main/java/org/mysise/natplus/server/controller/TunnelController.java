package org.mysise.natplus.server.controller;


import org.mysise.natplus.server.common.model.TunnelRequest;
import org.mysise.natplus.server.common.model.TunnelResponse;
import org.mysise.natplus.server.common.model.TunnelSearch;
import org.mysise.natplus.server.service.ITunnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 隧道/通道 前端控制器
 * </p>
 *
 * @author fanwenjie
 * @since 2020-03-07
 */
@RestController
@RequestMapping("/api/tunnel")
public class TunnelController {


    @Autowired
    private ITunnelService tunnelService;
    /**
     * <p>
     *  增加隧道
     * <p>
     *
     * @author fanwenjie
     * @since 2020/3/10 0:01
     */
    @PostMapping("/")
    public Integer add(@RequestBody TunnelRequest request){
        return tunnelService.save(request);
    }


    /**
     * <p>
     *  隧道查询
     * <p>
     *
     * @author fanwenjie
     * @since 2020/3/10 0:45
     */
    @GetMapping("/")
    public List<TunnelResponse> listTunnel(TunnelSearch request){
        return tunnelService.listTunnel(request);
    }
    
    /**
     * <p>
     *  编辑隧道信息
     * <p>
     *
     * @author fanwenjie
     * @since 2020/3/10 0:45
     */
    @PutMapping("/{id}")
    public Integer update(@PathVariable(name = "id") Integer id, @RequestBody TunnelRequest  request){
        return tunnelService.update(request);
    }
}
