package com.jxy.my_blog.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jxy.my_blog.entity.TTag;
import com.jxy.my_blog.mapper.TTagMapper;
import com.jxy.my_blog.service.TTagService;
import com.jxy.my_blog.util.PageUtils;
import com.jxy.my_blog.vo.MyPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author alex wong
 * @since 2023-03-23
 */
@Service
public class TTagServiceImpl extends ServiceImpl<TTagMapper, TTag> implements TTagService {

    @Autowired
    private TTagMapper tTagDao;

    @Override
    public List<TTag> listTagTop(int size) {
        IPage<TTag> viewPage = new Page<>(1,size);
        IPage<TTag> result = tTagDao.listTage(viewPage);
        return result.getRecords();
    }

    @Override
    public List<TTag> listTag() {
        return tTagDao.listAllTag();
    }

    @Override
    public List<TTag> listTag(String tagIds) {
        return tTagDao.listByIds(convertToList(tagIds));
    }

    @Override
    public MyPage<TTag> getTags(Integer page) {
        MyPage<TTag> myPage = new MyPage<>();
        IPage<TTag> viewPage = new Page<>(page,10);
        IPage<TTag> result = tTagDao.getTags(viewPage);
        PageUtils<TTag> tBlogPageUtils = new PageUtils<>();
        tBlogPageUtils.getMyPage(page, myPage, result);
        return myPage;
    }

    @Override
    public TTag getTag(Long id) {
        return tTagDao.getTagById(id);
    }

    @Override
    public TTag getTagByName(String name) {
        return tTagDao.getTagByName(name);
    }

    @Override
    public TTag saveTag(TTag tag) {
        tag.setBlogs(null);
        tTagDao.insert(tag);
        return tag;
    }

    @Override
    public TTag updateTag(TTag tag) {
        tag.setBlogs(null);
        tTagDao.updateById(tag);
        return tag;
    }

    @Override
    public void deleteTag(Long id) {
        tTagDao.deleteById(id);
    }

    private List<Long> convertToList(String ids) {
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idarray = ids.split(",");
            for (int i=0; i < idarray.length;i++) {
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }
}












































