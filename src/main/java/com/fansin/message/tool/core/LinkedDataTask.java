package com.fansin.message.tool.core;

import cn.hutool.core.collection.CollectionUtil;
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
public class LinkedDataTask extends RecursiveTask<Long> {

    private static final int THRESHOLD_PROCESS_SIZE = 100000;
    private static final int THRESHOLD_SUB_TASK = 100;
    private String name;

    /**
     * 数据处理类
     */
    private LinkedDataReceiver receiver;

    private List<String> linkedDataList;

    /**
     * Instantiates a new Linked data task.
     *
     * @param name           the name
     * @param linkedDataList the linked data list
     * @param receiver       the receiver
     */
    public LinkedDataTask(String name ,List<String> linkedDataList, LinkedDataReceiver receiver) {
        this.name = name;
        this.linkedDataList = linkedDataList;
        this.receiver = receiver;
    }

    /**
     * The main computation performed by this task.
     *
     * @return the result of the computation
     */
    @Override
    protected Long compute() {

        long successNum = 0;
        boolean canCompute = linkedDataList.size() < THRESHOLD_PROCESS_SIZE;
        if (canCompute) {
            try {
                long start = System.currentTimeMillis();
                List<AbstractLinkedData> linkedData = receiver.preProcess(linkedDataList);
                if (CollectionUtil.isEmpty(linkedData)) {
                    log.warn("任务处理队列为空!{}",name);
                    return 0L;
                }
                successNum += receiver.exec(linkedData);
                long end = System.currentTimeMillis();
                log.info("任务名称 {} 消耗时间：{}", name, end - start);
            } catch (Exception e) {
                log.error("任务处理异常",e);
                return 0L;
            }
        } else {
            int length = linkedDataList.size();
            int step = length / THRESHOLD_SUB_TASK;
            ArrayList<LinkedDataTask> subTaskList = new ArrayList<>();
            int pos = 0;
            //拆分任务数 100
            for (int i = 0; i < THRESHOLD_SUB_TASK; i++) {
                int lastOne = pos + step;
                if (lastOne > length) {
                    lastOne = length;
                }
                LinkedDataTask subTask = new LinkedDataTask(" SubTask"+i,linkedDataList.subList(pos, lastOne), receiver);
                pos += step;
                subTaskList.add(subTask);
                subTask.fork();
            }
            log.info("拆分任务数 {}",subTaskList.size());
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
    public List<String> getLinkedDataList() {
        return linkedDataList;
    }
}
