package com.fansin.message.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.fansin.message.entity.Person;
import com.fansin.message.service.PersonService;
import com.fansin.message.tool.LocalDateReceiver;
import com.fansin.message.tool.SimpleTextMessageProcessor;
import com.fansin.message.tool.core.LinkedDataReceiver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>Title: SimpleTextMessageProcessorController</p>
 * <p>Description: </p>
 *
 * @author zhaofeng
 * @version 1.0
 * @date 18 -5-2
 */
@Slf4j
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
        long start = System.currentTimeMillis();
        new SimpleTextMessageProcessor(new LocalDateReceiver()).read(filePath);
        return "本地解析报文完毕! 耗时:"+(System.currentTimeMillis() - start);
    }


    @Resource
    private PersonService personService;

    @RequestMapping("/db")
    public String processDb(@RequestParam("filePath") String filePath){
        long start = System.currentTimeMillis();
        int batchSize = 5;

        new SimpleTextMessageProcessor(new LinkedDataReceiver<String>() {

            @Override
            public void exec(List<String> dataList) {

                if (CollUtil.isEmpty(dataList)) {
                    log.warn("读取报文数据为空!请检查文件是否存在或文件路径是否正确! filePath={} limitSize={}", filePath, batchSize);
                    return;
                }
                //保存处理结果
                List<Person> entryList = new LinkedList<>();
                List<EntityWrapper> wrapperList = new ArrayList<>();
                try {
                    for (int i = 0; i < dataList.size(); i++) {

                        //批处理
                        String[] strings = dataList.get(i).split(SEPERATOR);
                        if (strings.length != 2 || StrUtil.isBlank(strings[0]) || StrUtil.isBlank(strings[1])) {
                            log.error("读取到损坏数据! {} line = {} filPath={}", Thread.currentThread().getName(),
                                    dataList.get(i), filePath);
                            continue;
                        }

                        if (i % batchSize == 0 && i > 0) {
                            //批量更新
                            personService.batchUpdate(entryList,wrapperList,batchSize);
                            wrapperList = new ArrayList<>();
                            entryList = new LinkedList<>();
                        }

                        Person person = new Person();
                        person.setToken(strings[1]);
                        entryList.add(person);
                        EntityWrapper<Person> entityWrapper = new EntityWrapper<>();
                        entityWrapper.setEntity(new Person());
                        entityWrapper.where("id={0}",strings[0]);
                        wrapperList.add(entityWrapper);
                    }
                    //更新剩余
                    personService.batchUpdate(entryList,wrapperList,batchSize);
                    log.info("更新数据成功,本次更新数据大小:" + dataList.size());
                } catch (Exception e) {
                    log.error("更新数据失败!filePath=" + filePath + " limitSize=" + batchSize, e);
                }
            }


        }).read(filePath);

        return "数据库持久化,解析报文完毕!耗时:"+(System.currentTimeMillis() - start);

    }


}
