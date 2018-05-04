package com.fansin.message.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.fansin.message.entity.Person;

import java.util.List;

/**
 * <p>
 * ä¸ªäººä¿¡æ¯å››è¦ç´  Mapper 接口
 * </p>
 *
 * @author zhaofeng
 * @since 2018 -05-02
 */
public interface PersonMapper extends BaseMapper<Person> {


    /**
     * 导出数据
     * @return
     */
    List exportData();

}
