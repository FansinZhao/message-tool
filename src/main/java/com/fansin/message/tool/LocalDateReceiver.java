package com.fansin.message.tool;

import com.fansin.message.tool.core.LinkedDataReceiver;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>Title: LocalDateReceiver</p>
 * <p>Description: </p>
 *
 * @author zhaofeng
 * @version 1.0
 * @date 18-5-2
 */
public class LocalDateReceiver implements LinkedDataReceiver<String> {
    /**
     * 将String 解析为 List
     *
     * @param stringList the string list
     * @return list
     */
    @Override
    public List<String> preProcess(List<String> stringList) {
        System.out.println("[重写]根据自己需要本方法");
        List dataList = new LinkedList();
        for (String s : stringList) {
            String[] strings = s.split(SEPERATOR);
            dataList.add(new BaseLinkedData(strings[0], strings[1]));
        }
        return dataList;
    }

    /**
     * 处理数据
     *
     * @param dataList the data list
     * @return long
     */
    @Override
    public Long exec(List<String> dataList) {
        System.out.println("[重写]业务处理,数据库持久化等...");
        return Long.valueOf(dataList.size());
    }
}
