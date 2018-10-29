package com.fansin.message.tool.core;

import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.RecursiveAction;

/**
 * 〈一句话功能简述〉<br>
 * 〈处理关联数据task〉
 *
 * @author z00461
 * @create 2018 /4/19
 * @since 1.0.0
 */
@Slf4j
public class LinkedDataTask extends RecursiveAction {

    private static final int    THRESHOLD_PROCESS_SIZE = 100000;

    private              String name;

    /**
     * 数据处理类
     */
    private LinkedDataReceiver receiver;

    private List<String> dataList;

    /**
     * Instantiates a new Linked data task.
     *
     * @param name     the name
     * @param dataList the linked data list
     * @param receiver the receiver
     */
    public LinkedDataTask(String name, List<String> dataList, LinkedDataReceiver receiver) {
        this.name = name;
        this.dataList = dataList;
        this.receiver = receiver;
    }

    /**
     * The main computation performed by this task.
     *
     * @return the result of the computation
     */
    @Override
    protected void compute() {

        if (CollUtil.isEmpty(dataList)) {
            log.error("数据为空!请联系管理员!");
            return;
        }

        int length = dataList.size();
        boolean canCompute = length <= THRESHOLD_PROCESS_SIZE;
        if (canCompute) {
            try {
                long start = System.currentTimeMillis();
                //数据处理
                receiver.exec(dataList);
                long time = System.currentTimeMillis() - start;
                log.info("任务名称 {} 处理数据大小 {} 消耗时间：{} ", name, dataList.size(),time);
            } catch (Exception e) {
                log.error("任务处理异常 taskName=" + name, e);
                return;
            }
        } else {

            int step = (int)Math.ceil((double)length / THRESHOLD_PROCESS_SIZE);
            int pos = 0;
            //拆分任务数 100
            for (int i = 0; i < step; i++) {
                int lastOne = pos + THRESHOLD_PROCESS_SIZE;
                if (lastOne > length) {
                    lastOne = length;
                }
                log.info(" SubTask" + i+" pos = " + pos+" lastOne = " + lastOne);
                //工厂方法
                LinkedDataTask subTask = new LinkedDataTask(" SubTask" + i, dataList.subList(pos, lastOne), receiver);
                pos += THRESHOLD_PROCESS_SIZE;
                subTask.fork();

            }
        }
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets receiver.
     *
     * @return the receiver
     */
    public LinkedDataReceiver getReceiver() {
        return receiver;
    }

    /**
     * Gets linked data list.
     *
     * @return the linked data list
     */
    public List<String> getDataList() {
        return dataList;
    }
}
