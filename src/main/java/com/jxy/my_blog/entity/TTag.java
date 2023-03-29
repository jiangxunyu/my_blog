package com.jxy.my_blog.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author alex wong
 * @since 2023-03-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TTag implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private List<TBlog> blogs = new ArrayList<>();

}
