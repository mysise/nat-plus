package org.mysise.natplus.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.mysise.natplus.server.entity.SysUser;
import org.mysise.natplus.server.entity.Tunnel;
import org.mysise.natplus.server.mapper.SysUserMapper;
import org.mysise.natplus.server.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author fanwenjie
 * @since 2020-03-09
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Override
    public SysUser getUser(String email, String passWord) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email)
                .eq("password", passWord);
        return this.getOne(queryWrapper);
    }
}
