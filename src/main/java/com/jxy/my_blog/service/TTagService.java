package com.jxy.my_blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jxy.my_blog.entity.TTag;
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
public interface TTagService extends IService<TTag> {

    List<TTag> listTagTop(int size);

    List<TTag> listTag();

    List<TTag> listTag(String tagIds);

    MyPage<TTag> getTags(Integer page);

    TTag getTag(Long id);

    TTag getTagByName(String name);

    TTag saveTag(TTag tag);

    TTag updateTag(TTag tag);

    void deleteTag(Long id);
}
