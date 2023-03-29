package com.jxy.my_blog.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jxy.my_blog.vo.MyPage;

import java.util.List;

public class PageUtils<T> {
    public void getMyPage(Integer page, MyPage<T> myPage, IPage<T> result) {
        List<T> records = result.getRecords();
        long total = result.getTotal();
        int totalPages = (int) (total/10) + 1;
        myPage.setContent(records);
        myPage.setTotalElements(total);
        myPage.setTotalPages(totalPages);
        boolean first = true;
        boolean last = false;
        if (page != 1){
            first = false;
        }
        if (page == totalPages){
            last = true;
        }
        myPage.setFirst(first);
        myPage.setLast(last);
        myPage.setNumber(page);
    }
}
