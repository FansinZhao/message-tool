package com.fansin.message.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.fansin.message.entity.Person;
import com.fansin.message.service.PersonService;
import com.fansin.message.tool.LocalDateReceiver;
import com.fansin.message.tool.SimpleTextMessageProcessor;
import com.fansin.message.tool.core.LinkedDataReceiver;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>Title: SimpleTextMessageProcessorController</p>
 * <p>Description: </p>
 *
 * @author zhaofeng
 * @version 1.0
 * @date 18 -5-2
 */
@RestController
@RequestMapping("/text")
public class SimpleTextMessageProcessorController {



    /**
     * Index string.
     *
     * @return the string
     */
    @RequestMapping()
    public String index() {
        return "ok";
    }

    /**
     * Process string.
     *
     * @return the string
     */
    @RequestMapping("/local")
    public String processLocal(@RequestParam("filePath") String filePath) {
        new SimpleTextMessageProcessor(new LocalDateReceiver()).read(filePath);
        return "本地解析报文完毕!";
    }


    @Resource
    private PersonService personService;

    @RequestMapping("/db")
    public String processDb(@RequestParam("filePath") String filePath){

        new SimpleTextMessageProcessor(new LinkedDataReceiver<String>() {
            @Override
            public Integer exec(List<String> dataList) {
                //保存处理结果
                AtomicInteger successNum = new AtomicInteger(0);
                List<Person> entryList = new ArrayList<>();
                List<EntityWrapper> wrapperList = new ArrayList<>();
                for (int i = 0; i < dataList.size(); i++) {

                    //批处理
                    if(i % 1000 == 0 &&  i > 0){
                        personService.batchUpdate(entryList,wrapperList,1000);
                        entryList = new ArrayList<>();
                        wrapperList = new ArrayList<>();
                    }
                    String[] strings = dataList.get(i).split(SEPERATOR);
                    Person person = new Person();
                    person.setRemark(strings[1]);
                    entryList.add(person);
                    EntityWrapper<Person> entityWrapper = new EntityWrapper<>();
                    entityWrapper.setEntity(new Person());
                    entityWrapper.where("id={0}",strings[0]);
                    wrapperList.add(entityWrapper);
                }

                System.out.println("[重写]业务处理,数据库持久化等...批量提交剩余数据!");

                return successNum.get();
            }
        }).read(filePath);

        return "数据库持久化,解析报文完毕!";

    }


}
