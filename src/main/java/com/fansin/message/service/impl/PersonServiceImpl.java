package com.fansin.message.service.impl;

import com.baomidou.mybatisplus.enums.SqlMethod;
import com.baomidou.mybatisplus.exceptions.MybatisPlusException;
import com.fansin.message.entity.Person;
import com.fansin.message.mapper.PersonMapper;
import com.fansin.message.service.PersonService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * ä¸ªäººä¿¡æ¯å››è¦ç´  服务实现类
 * </p>
 *
 * @author zhaofeng
 * @since 2018-05-02
 */
@Service
public class PersonServiceImpl extends ServiceImpl<PersonMapper, Person> implements PersonService {


    @Override
    public void batchUpdate(){


//        baseMapper.update(new Person(),)
        List<Person> entityList = new ArrayList();
        entityList.add(new Person());
        int batchSize= 0;

        try (SqlSession batchSqlSession = sqlSessionBatch()) {
            int size = entityList.size();
            String sqlStatement = sqlStatement(SqlMethod.UPDATE);
            for (int i = 0; i < size; i++) {
                MapperMethod.ParamMap<Person> param = new MapperMethod.ParamMap<>();
                param.put("et", entityList.get(i));

                batchSqlSession.update(sqlStatement, param);
                if (i >= 1 && i % batchSize == 0) {
                    batchSqlSession.flushStatements();
                }
            }
            batchSqlSession.flushStatements();
        } catch (Throwable e) {
            throw new MybatisPlusException("Error: Cannot execute insertBatch Method. Cause", e);
        }


    }

}
