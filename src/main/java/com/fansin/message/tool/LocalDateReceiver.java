package com.fansin.message.tool;

import com.fansin.message.tool.core.LinkedDataReceiver;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>Title: LocalDateReceiver</p>
 * <p>Description: </p>
 *
 * @author zhaofeng
 * @version 1.0
 * @date 18 -5-2
 */
@Slf4j
public class LocalDateReceiver implements LinkedDataReceiver<String> {


    /**
     * 处理数据
     *
     * @param stringList the data list
     * @return long
     */
    @Override
    public void exec(List<String> stringList) {

        //保存处理结果
        AtomicInteger successNum = new AtomicInteger(0);

        System.out.println("[重写]根据自己需要本方法");
        for (int i = 0; i < stringList.size(); i++) {

            String[] strings = stringList.get(i).split(SEPERATOR);
            BaseLinkedData baseLinkedData = new BaseLinkedData(strings[0], strings[1]);
            System.out.println("baseLinkedData = " + baseLinkedData.getRaw());
            //批处理
            successNum.addAndGet(1);
            if(i %1000 == 0 &&  i > 0){
                System.out.println("[重写]业务处理,数据库持久化等...批量处理");
            }
        }

        System.out.println("[重写]业务处理,数据库持久化等...批量提交剩余数据!"+successNum.get());

    }
}
