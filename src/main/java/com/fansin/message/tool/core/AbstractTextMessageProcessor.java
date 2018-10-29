package com.fansin.message.tool.core;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
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
public abstract class AbstractTextMessageProcessor extends AbstractMessageProcessor {
    protected static final int MAX_CAP      = 0x7fff;
    /**
     * 限制最大cup使用数目为系统一半
     * */
    protected int parallelism = Math.min(MAX_CAP, Runtime.getRuntime().availableProcessors())/2;

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
                if (StrUtil.isBlank(line) || line.startsWith("#") || line.startsWith("/")) {
                    continue;
                }
                //数据格式化
                line = StrUtil.trim(line);
                batchList.add(line);
                count.incrementAndGet();
            }
            ForkJoinPool pool = new ForkJoinPool(parallelism);
            LinkedDataTask task = new LinkedDataTask("RootTask", batchList, receiver);
            pool.execute(task);
            //先调用关闭
            pool.shutdown();
            pool.awaitTermination(1, TimeUnit.DAYS);
        } catch (IOException e) {
            log.error(" 报文解析错误！", e);
        } catch (InterruptedException e) {
            log.error(" 终止异常！", e);
        }
    }
}
