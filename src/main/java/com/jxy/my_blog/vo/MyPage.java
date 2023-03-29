package com.jxy.my_blog.vo;

import lombok.Data;

import java.util.List;

@Data
public class MyPage<T> {
    private List<T> content;
    private Long totalElements;
    private Integer totalPages;
    private boolean first;
    private boolean last;
    private Integer number;
}
