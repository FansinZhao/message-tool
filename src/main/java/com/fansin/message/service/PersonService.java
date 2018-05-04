package com.fansin.message.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.IService;
import com.fansin.message.entity.Person;

import java.util.List;

/**
 * <p>
 * ä¸ªäººä¿¡æ¯å››è¦ç´  服务类
 * </p>
 *
 * @author zhaofeng
 * @since 2018 -05-02
 */
public interface PersonService extends IService<Person> {

    /**
     * 导出数据
     * @return
     */
    List  exportData();

    /**
     * 批量更新
     * @return
     */
    boolean batchUpdate(List<Person> entityList, List<EntityWrapper> entityWrapperList, int batchSize);
}
