package com.fansin.message.service.impl;

import com.fansin.message.entity.Person;
import com.fansin.message.mapper.PersonMapper;
import com.fansin.message.service.PersonService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * ä¸ªäººä¿¡æ¯å››è¦ç´  服务实现类
 * </p>
 *
 * @author zhaofeng
 * @since 2018-05-02
 */
@Service
public class PersonServiceImpl extends ServiceImpl<PersonMapper, Person> implements PersonService {

}
