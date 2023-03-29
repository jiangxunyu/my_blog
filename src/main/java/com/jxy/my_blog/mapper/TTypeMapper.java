package com.jxy.my_blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jxy.my_blog.entity.TType;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author alex wong
 * @since 2023-03-23
 */
@Component
public interface TTypeMapper extends BaseMapper<TType> {

    IPage<TType> listType(IPage<TType> viewPage);

    List<TType> listAllType();

    TType getTypeById(Long id);

    IPage<TType> getTypes(IPage<TType> viewPage);

    TType getTypeByName(String name);
}
