package com.jxy.my_blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jxy.my_blog.entity.TTag;
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
public interface TTagMapper extends BaseMapper<TTag> {

    IPage<TTag> listTage(IPage<TTag> viewPage);

    List<TTag> listAllTag();

    List<TTag> listByIds(List<Long> ids);

    IPage<TTag> getTags(IPage<TTag> viewPage);

    TTag getTagById(Long id);

    TTag getTagByName(String name);
}
