package com.jxy.my_blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jxy.my_blog.entity.TUser;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author alex wong
 * @since 2023-03-23
 */
public interface TUserMapper extends BaseMapper<TUser> {

    TUser findByUsernameAndPassword(String username, String password);
}
