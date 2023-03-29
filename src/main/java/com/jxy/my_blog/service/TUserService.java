package com.jxy.my_blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jxy.my_blog.entity.TUser;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author alex wong
 * @since 2023-03-23
 */
public interface TUserService extends IService<TUser> {

    TUser checkUser(String username, String password);
}
