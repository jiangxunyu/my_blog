package com.jxy.my_blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jxy.my_blog.entity.TType;
import com.jxy.my_blog.vo.MyPage;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author alex wong
 * @since 2023-03-23
 */
@Service
public interface TTypeService extends IService<TType> {

    List<TType> listTypeTop(int size);

    List<TType> listType();

    TType getType(Long id);

    MyPage<TType> getTypes(Integer page);

    TType getTypeByName(String name);

    TType saveType(TType type);

    TType updateType(TType type);

    void deleteType(Long id);
}
