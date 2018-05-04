package com.fansin.message.service.impl;

import com.baomidou.mybatisplus.enums.SqlMethod;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.fansin.message.entity.Person;
import com.fansin.message.mapper.PersonMapper;
import com.fansin.message.service.PersonService;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * ä¸ªäººä¿¡æ¯å››è¦ç´  服务实现类
 * </p>
 *
 * @author zhaofeng
 * @since 2018 -05-02
 */
@Service
public class PersonServiceImpl extends ServiceImpl<PersonMapper, Person> implements PersonService {

    /**
     * 导出数据
     *
     * @return
     */
    @Override
    public List exportData() {
        return baseMapper.exportData();
    }

    /**
     * 批量更新
     *
     * @return
     */
    @Override
    public boolean batchUpdate(List<Person> entityList, List<EntityWrapper> entityWrapperList, int batchSize) {

        try (SqlSession batchSqlSession = sqlSessionBatch()) {
            int size = entityList.size();
            SqlMethod sqlMethod = SqlMethod.UPDATE;
            String sqlStatement = sqlStatement(sqlMethod);
            for (int i = 0; i < size; i++) {
                MapperMethod.ParamMap<Object> param = new MapperMethod.ParamMap<>();
                param.put("et", entityList.get(i));
                param.put("ew",entityWrapperList.get(i) );
                //批量更新会返回负数
                batchSqlSession.update(sqlStatement, param);
                if (i >= 1 && i % batchSize == 0) {
                    batchSqlSession.flushStatements();
                }
            }
            batchSqlSession.flushStatements();

        }

        return false;
    }
}
