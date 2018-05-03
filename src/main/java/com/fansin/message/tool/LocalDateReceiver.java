package com.fansin.message.tool;

import com.fansin.message.tool.core.LinkedDataReceiver;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * <p>Title: LocalDateReceiver</p>
 * <p>Description: </p>
 *
 * @author zhaofeng
 * @version 1.0
 * @date 18-5-2
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
    public Integer exec(List<String> stringList) {

        //保存处理结果
        int successNum = 0;

        System.out.println("[重写]根据自己需要本方法");
        for (String s : stringList) {
            String[] strings = s.split(SEPERATOR);
            BaseLinkedData baseLinkedData = new BaseLinkedData(strings[0], strings[1]);
            //批处理
            successNum = 100;
            System.out.println("[重写]业务处理,数据库持久化等...");
        }

        return successNum;
    }
}
