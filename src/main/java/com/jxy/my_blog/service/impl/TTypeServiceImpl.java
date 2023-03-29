package com.jxy.my_blog.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jxy.my_blog.entity.TType;
import com.jxy.my_blog.mapper.TTypeMapper;
import com.jxy.my_blog.service.TTypeService;
import com.jxy.my_blog.util.PageUtils;
import com.jxy.my_blog.vo.MyPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class TTypeServiceImpl extends ServiceImpl<TTypeMapper, TType> implements TTypeService {

    @Autowired
    private TTypeMapper tTypeDao;

    @Override
    public List<TType> listTypeTop(int size) {
        IPage<TType> viewPage = new Page<>(1,size);
        IPage<TType> result = tTypeDao.listType(viewPage);
        return result.getRecords();
    }

    @Override
    public List<TType> listType() {
        return tTypeDao.listAllType();
    }

    @Override
    public TType getType(Long id) {
        return tTypeDao.getTypeById(id);
    }

    @Override
    public MyPage<TType> getTypes(Integer page) {
        MyPage<TType> myPage = new MyPage<>();
        IPage<TType> viewPage = new Page<>(page,10);
        IPage<TType> result = tTypeDao.getTypes(viewPage);
        PageUtils<TType> tBlogPageUtils = new PageUtils<>();
        tBlogPageUtils.getMyPage(page, myPage, result);
        return myPage;
    }

    @Override
    public TType getTypeByName(String name) {
        return tTypeDao.getTypeByName(name);
    }

    @Override
    public TType saveType(TType type) {
        type.setBlogs(null);
        tTypeDao.insert(type);
        return type;
    }

    @Override
    public TType updateType(TType type) {
        type.setBlogs(null);
        tTypeDao.updateById(type);
        return type;
    }

    @Override
    public void deleteType(Long id) {
        tTypeDao.deleteById(id);
    }
}






























