package com.fansin.message.tool.core;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈关联数据接收者〉
 *
 * @param <V> the type parameter
 * @author z00461
 * @create 2018 /4/19
 * @since 1.0.0
 */
public interface LinkedDataReceiver<V> {

    /**
     * The constant SEPERATOR.
     */
    String SEPERATOR = ",";

    /**
     * The constant SEPERATOR.
     */
    int PAGE_SIZE = 30;

    /**
     * 处理数据
     *
     * @param dataList the data list
     * @return long integer
     */
    void exec(List<V> dataList);

}
