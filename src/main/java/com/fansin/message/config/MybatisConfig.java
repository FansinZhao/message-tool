package com.fansin.message.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Title: MybatisConfig</p>
 * <p>Description: </p>
 *
 * @author zhaofeng
 * @version 1.0
 * @date 18 -5-2
 */
@MapperScan("com.**.mapper")
@Configuration
public class MybatisConfig {
}
