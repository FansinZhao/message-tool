package com.fansin.message.tool.core;

import cn.hutool.core.util.ZipUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * <p>Title: AbstractMessageProcessor</p>
 * <p>Description: </p>
 *
 * @author zhaofeng
 * @version 1.0
 * @date 18 -5-2
 */
@Slf4j
public abstract class AbstractMessageProcessor {

    private static final String MODE_READ = "r";
    private static final String MODE_WRITE = "rw";
    private static final int WAITING_DAYS = 1;

    /**
     * The Receiver.
     */
    protected LinkedDataReceiver receiver;

    /**
     * Instantiates a new Abstract message processor.
     *
     * @param receiver the receiver
     */
    public AbstractMessageProcessor(LinkedDataReceiver receiver) {
        this.receiver = receiver;
    }

    /**
     * 从zip读取文件
     *
     * @param filePath the file path
     */
    public void readZip(String filePath) {
        //限于RandomAccessFile只能从文件获取流，先解压文件
        readZip(new File(filePath));
    }

    /**
     * Read zip.
     *
     * @param file the file
     */
    public void readZip(File file) {
        log.info("识别文件类型为zip....{}", file.getAbsolutePath());
        File files = ZipUtil.unzip(file);
        readDir(files);
    }

    /**
     * 读取目录
     *
     * @param files
     */
    private void readDir(File files) {
        log.info("识别文件类型为 目录....{}", files.getAbsolutePath());
        if (files.isDirectory()) {
            //递归目录
            File[] list = files.listFiles();
            if (list != null){
                for (File file : list) {
                    readDir(file);
                }
            }
        } else {
            //读取文件
            read(files.getAbsolutePath());
        }
    }

    /**
     * Read dir.
     *
     * @param filePath the file path
     */
    public void readDir(String filePath) {
        readDir(new File(filePath));
    }

    /**
     * 读取文件
     *
     * @param filePath the file path
     */
    public void read(String filePath) {
        if (filePath.toLowerCase().endsWith(".txt")) {
            readFile(filePath);
        } else if (filePath.toLowerCase().endsWith(".zip")) {
            readZip(filePath);
        } else if (new File(filePath).isDirectory()) {
            readDir(filePath);
        } else {
            log.error("文件类型不支持{}", filePath);
        }
    }

    /**
     * @param filePath
     */
    private void readFile(String filePath) {
        log.info("识别文件类型为 文件....{}", filePath);
        read0(filePath, MODE_READ);
    }

    /**
     * 增加统计代码
     *
     * @param filePath
     * @param mode
     */
    private void read0(String filePath, String mode) {
        log.info("解析任务开始...异步任务处理");
        read(filePath, mode);

    }


    /**
     * 为不同的报文解析提供具体实现
     *
     * @param filePath the file path
     * @param mode     the mode
     */
    protected abstract void read(String filePath, String mode);

}
