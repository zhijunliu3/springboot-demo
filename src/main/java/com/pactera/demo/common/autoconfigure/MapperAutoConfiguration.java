package com.pactera.demo.common.autoconfigure;

import com.pactera.demo.common.helper.MapperHelper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Iterator;
import java.util.List;

/**
 * @description: mapper自动配置
 * @author: LiuZhiJun
 * @create: 2019-08-11 16:19
 **/
@Configuration
@ConditionalOnBean({SqlSessionFactory.class})
@AutoConfigureAfter({MybatisAutoConfiguration.class})
public class MapperAutoConfiguration {
    @Autowired
    private List<SqlSessionFactory> sqlSessionFactoryList;

    @Autowired
    MapperHelper mapperHelper;

    @PostConstruct
    public void addPageInterceptor() throws Exception {
        Iterator iterator = this.sqlSessionFactoryList.iterator();
        while(iterator.hasNext()) {
            SqlSessionFactory sqlSessionFactory = (SqlSessionFactory)iterator.next();
            mapperHelper.processConfiguration(sqlSessionFactory.getConfiguration());
        }
    }
}
