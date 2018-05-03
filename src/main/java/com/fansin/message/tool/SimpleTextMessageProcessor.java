package com.fansin.message.tool;

import com.fansin.message.tool.core.AbstractTextMessageProcessor;
import com.fansin.message.tool.core.LinkedDataReceiver;

/**
 * <p>Title: SimpleTextMessageProcessor</p>
 * <p>Description: 简单的文本处理类 </p>
 *
 * @author zhaofeng
 * @version 1.0
 * @date 18 -5-2
 */
public class SimpleTextMessageProcessor extends AbstractTextMessageProcessor {

    /**
     * Instantiates a new Simple text message processor.
     *
     * @param receiver the receiver
     */
    public SimpleTextMessageProcessor(LinkedDataReceiver receiver) {
        super(receiver);
    }
}
