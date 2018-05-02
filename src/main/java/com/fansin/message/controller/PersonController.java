package com.fansin.message.controller;


import cn.hutool.core.util.RandomUtil;
import com.fansin.message.entity.Person;
import com.fansin.message.service.PersonService;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Random;

/**
 * <p>
 *   前端控制器
 * </p>
 *
 * @author zhaofeng
 * @since 2018-05-02
 */
@RestController
@RequestMapping("/person")
public class PersonController {

    @Resource
    private PersonService personService;

    @RequestMapping
    public String find(){
        Person person = new Person();
        person.setName("zhaofeng");
        person.setAge(18);
        person.setBankCard("123456789");
        person.setHome("234");
        person.setIdCard("2356677"+RandomUtil.randomString(3));
        person.setMobilePhone("12345667");

        personService.insert(person);


        return personService.selectById("1").toString();
    }

}

