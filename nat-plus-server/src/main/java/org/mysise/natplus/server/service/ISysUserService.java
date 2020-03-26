package org.mysise.natplus.server.service;

import org.mysise.natplus.server.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author fanwenjie
 * @since 2020-03-09
 */
public interface ISysUserService extends IService<SysUser> {

    /**
     * 根据email和密码获取用户
     *
     * @param email
     * @param passWord
     * @return SysUser
     * @author haizi
     * @since 2020/3/12
     */

    SysUser getUser(String email, String passWord);
}
