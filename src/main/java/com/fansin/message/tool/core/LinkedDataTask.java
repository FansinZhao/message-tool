package com.fansin.message.tool.core;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 * 〈一句话功能简述〉<br>
 * 〈处理关联数据task〉
 *
 * @author z00461
 * @create 2018 /4/19
 * @since 1.0.0
 */
@Slf4j
public class LinkedDataTask extends RecursiveTask<Integer> {

    private static final int THRESHOLD_PROCESS_SIZE = 100000;
    private static final int THRESHOLD_SUB_TASK = 100;
    private String name;

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
    protected Integer compute() {

        int successNum = 0;
        boolean canCompute = dataList.size() < THRESHOLD_PROCESS_SIZE;
        if (canCompute) {
            try {
                long start = System.currentTimeMillis();
                //数据处理
                successNum += receiver.exec(dataList);
                long time = System.currentTimeMillis() - start;
                log.info("任务名称 {} 处理数据大小 {} 消耗时间：{} ", name, dataList.size(), time);
            } catch (Exception e) {
                log.error("任务处理异常 taskName=" + name, e);
                return 0;
            }
        } else {
            int length = dataList.size();
            int step = length / THRESHOLD_SUB_TASK;
            ArrayList<LinkedDataTask> subTaskList = new ArrayList<>();
            int pos = 0;
            //拆分任务数 100
            for (int i = 0; i < THRESHOLD_SUB_TASK; i++) {
                int lastOne = pos + step;
                if (lastOne > length) {
                    lastOne = length;
                }
                //工厂方法
                LinkedDataTask subTask = new LinkedDataTask(" SubTask" + i, dataList.subList(pos, lastOne), receiver);
                pos += step;
                subTaskList.add(subTask);
                subTask.fork();
            }
            log.info("拆分任务数 {}", subTaskList.size());
            for (LinkedDataTask task : subTaskList) {
                successNum += task.join();
            }
        }
        return successNum;
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
