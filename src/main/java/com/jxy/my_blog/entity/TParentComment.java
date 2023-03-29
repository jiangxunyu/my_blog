package com.jxy.my_blog.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author alex wong
 * @since 2023-03-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TParentComment implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long commentId;

    private Long parentId;


}
