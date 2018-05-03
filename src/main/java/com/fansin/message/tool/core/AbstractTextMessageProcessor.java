package com.fansin.message.tool.core;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.atomic.AtomicLong;

/**
 * <p>Title: AbstractTextMessageProcessor</p>
 * <p>Description: </p>
 *
 * @author zhaofeng
 * @version 1.0
 * @date 18 -5-2
 */
@Slf4j
public abstract class AbstractTextMessageProcessor extends AbstractMessageProcessor{

    /**
     * The Count.
     */
    protected AtomicLong count = new AtomicLong(0);

    /**
     * Instantiates a new Abstract text message processor.
     *
     * @param receiver the receiver
     */
    public AbstractTextMessageProcessor(LinkedDataReceiver receiver) {
        super(receiver);
    }

    /**
     * 为不同的报文解析提供具体实现
     *
     * @param filePath
     * @param mode
     */
    @Override
    protected void read(String filePath, String mode) {
        try (BufferedRandomAccessFile file = new BufferedRandomAccessFile(filePath, mode)) {
            String line;
            List<String> batchList = new LinkedList<>();
            while ((line = file.readLine()) != null) {
                //数据校验
                if (StrUtil.isEmpty(line) || line.startsWith("#") || line.startsWith("/")) {
                    continue;
                }
                //数据格式化
                line = StrUtil.trim(line);
                if (log.isDebugEnabled()) {
                    log.debug("line = " + line);
                }
                batchList.add(line);
                count.incrementAndGet();
            }
            ForkJoinPool pool = new ForkJoinPool();
            LinkedDataTask task = new LinkedDataTask("RootTask",batchList, receiver);
            ForkJoinTask<Integer> forkJoinTask = pool.submit(task);
            long start = System.currentTimeMillis();
            Integer successNum = forkJoinTask.get();
            long time = System.currentTimeMillis() - start;
            log.info("更新成功记录数{} 总条数 {} 消耗总时间 {}", successNum,count.get(),time);
        } catch (IOException e) {
            log.error(" 报文解析错误！", e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
