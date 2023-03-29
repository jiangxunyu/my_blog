package com.jxy.my_blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jxy.my_blog.entity.TUser;
import com.jxy.my_blog.mapper.TUserMapper;
import com.jxy.my_blog.service.TUserService;
import com.jxy.my_blog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author alex wong
 * @since 2023-03-23
 */
@Service
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser> implements TUserService {

    @Autowired
    private TUserMapper tUserMapper;

    @Override
    public TUser checkUser(String username, String password) {
        TUser user = tUserMapper.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }

    public static void main(String[] args) {
        String code = MD5Utils.code("123456");
        System.out.println(code);
    }
}
