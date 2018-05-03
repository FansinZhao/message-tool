package com.fansin.message.controller;


import com.baomidou.mybatisplus.service.IService;
import com.fansin.message.entity.Person;
import com.fansin.message.service.PersonService;
import com.fansin.message.utils.BuilderHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * <p>
 *   前端控制器
 * </p>
 *
 * @author zhaofeng
 * @since 2018 -05-02
 */
@RestController
@RequestMapping("/person")
@Slf4j
public class PersonController {

    @Resource
    private PersonService personService;

    /**
     * Find string.
     *
     * @return the string
     */
    @RequestMapping
    public String find() {
        return "OK";
    }


    /**
     * Insert batch string.
     *
     * @param total the total
     * @return the string
     */
    @RequestMapping("/insertBatch/{total}")
    public String insertBatch(@PathVariable("total") int total) {

        if (total == 0) {
            total = 1000000;
        }

        List<Person> list = builder(total);


        ForkJoinPool pool = new ForkJoinPool();
        BatchTask task = new BatchTask("BatchTask", list, personService);
        pool.submit(task);
        int sum = 0;
        try {
            sum = task.get();
            log.info("批量插入数量:{}", sum);
        } catch (InterruptedException e) {
            e.printStackTrace();
            sum = -1;
        } catch (ExecutionException e) {
            e.printStackTrace();
            sum = -1;
        }
        return "批量插入数量:" + sum;
    }

    private List builder(int size) {

        List list = new ArrayList();

        for (int i = 0; i < size; i++) {
            Person person = new Person();
            person.setName(BuilderHelper.randomName());
            person.setBankCard(BuilderHelper.randomBankCard());
            person.setIdCard(BuilderHelper.randomIdNo());
            person.setMobilePhone(BuilderHelper.randomPhone());
            person.setAge(18);
            person.setHome("234");
            person.setCreateDatetime(new Date());
            person.setOffice("22222");
            person.setRemark("批量插入数据");
            list.add(person);
        }
        return list;
    }

    /**
     * The type Batch task.
     */
    public class BatchTask extends RecursiveTask<Integer> {

        private static final int THRESHOLD_PROCESS_SIZE = 100000;
        private static final int THRESHOLD_SUB_TASK = 100;


        private IService service;
        private List dataList;
        private String name;

        /**
         * Instantiates a new Batch task.
         *
         * @param name     the name
         * @param dataList the data list
         * @param service  the service
         */
        public BatchTask(String name, List dataList, IService service) {
            this.service = service;
            this.dataList = dataList;
            this.name = name;
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
                    if (service.insertBatch(dataList)) {
                        successNum += dataList.size();
                    }
                    long time = System.currentTimeMillis() - start;
                    log.info("任务名称 {} 处理数据大小 {} 消耗时间：{} ", name, dataList.size(), time);
                } catch (Exception e) {
                    log.error("任务处理异常 taskName=" + name, e);
                    return 0;
                }
            } else {
                int length = dataList.size();
                int step = length / THRESHOLD_SUB_TASK;
                ArrayList<BatchTask> subTaskList = new ArrayList<>();
                int pos = 0;
                //拆分任务数 100
                for (int i = 0; i < THRESHOLD_SUB_TASK; i++) {
                    int lastOne = pos + step;
                    if (lastOne > length) {
                        lastOne = length;
                    }
                    //工厂方法
                    BatchTask subTask = new BatchTask(" SubTask" + i, dataList.subList(pos, lastOne), service);
                    pos += step;
                    subTaskList.add(subTask);
                    subTask.fork();
                }
                log.info("拆分任务数 {}", subTaskList.size());
                for (BatchTask task : subTaskList) {
                    successNum += task.join();
                }
            }
            return successNum;
        }
    }


}

