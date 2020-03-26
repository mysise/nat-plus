package org.mysise.natplus.server.controller;

import org.mysise.natplus.common.exception.BizException;
import org.mysise.natplus.server.common.JWTUtils;
import org.mysise.natplus.server.common.model.TunnelRequest;
import org.mysise.natplus.server.entity.SysUser;
import org.mysise.natplus.server.service.ISysUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 登陆 控制器
 *
 * @author haizi
 * @since 2020/03/12 18:23
 */

@RestController
@RequestMapping("/api/login")
public class LoginController {
    @Resource
    private ISysUserService sysUserService;

    @GetMapping("/login")
    public String login(@RequestParam("email") String email, @RequestParam("passWord") String passWord){

        SysUser user = sysUserService.getUser(email,passWord);
        if(user == null){
            throw new BizException("用户不存在");
        }
        return JWTUtils.createToken(user);
    }
}
