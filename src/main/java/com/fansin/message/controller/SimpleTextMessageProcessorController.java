package com.fansin.message.controller;

import com.fansin.message.tool.LocalDateReceiver;
import com.fansin.message.tool.SimpleTextMessageProcessor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Title: SimpleTextMessageProcessorController</p>
 * <p>Description: </p>
 *
 * @author zhaofeng
 * @version 1.0
 * @date 18 -5-2
 */
@RestController
@RequestMapping("/text")
public class SimpleTextMessageProcessorController {

    /**
     * Index string.
     *
     * @return the string
     */
    @RequestMapping()
    public String index() {
        return "ok";
    }

    /**
     * Process string.
     *
     * @return the string
     */
    @RequestMapping("/process")
    public String process() {
        new SimpleTextMessageProcessor(new LocalDateReceiver()).read("/home/zhaofeng/IdeaProjects/message-tool/src/main/resources/testData.txt");
        return "解析报文!";
    }
}
