package com.fansin.message.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * ä¸ªäººä¿¡æ¯å››è¦ç´ 
 * </p>
 *
 * @author zhaofeng
 * @since 2018 -05-02
 */
@Data
@TableName("person")
public class Person implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String name;
    private String idCard;
    private String bankCard;
    private String mobilePhone;
    private Integer age;
    private String home;
    private String office;
    private Date createDatetime;
    private String token;
    private Date updateDatetime;

}
