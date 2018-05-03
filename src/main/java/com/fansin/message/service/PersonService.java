package com.fansin.message.service;

import com.fansin.message.entity.Person;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * ä¸ªäººä¿¡æ¯å››è¦ç´  服务类
 * </p>
 *
 * @author zhaofeng
 * @since 2018-05-02
 */
public interface PersonService extends IService<Person> {


    void batchUpdate();

}
