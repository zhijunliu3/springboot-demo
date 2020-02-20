package com.pactera.demo.common.helper;

import com.pactera.demo.common.mapper.BaseMapper;
import org.apache.ibatis.builder.annotation.ProviderSqlSource;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @description: mapper工具类
 * @author: LiuZhiJun
 * @create: 2019-08-08 17:04
 **/
@Component
public class MapperHelper {

    @Autowired
    MapperTemplate mapperTemplate;

    private List<String> commonMethodName = new ArrayList<>();

    public MapperHelper(){
        Method[] methods = BaseMapper.class.getDeclaredMethods();
        for (Method method : methods){
            commonMethodName.add(method.getName());
        }
    }

    private final XMLLanguageDriver languageDriver = new XMLLanguageDriver();

    public void processConfiguration(Configuration configuration)throws Exception{
        Iterator iterator = (new ArrayList(configuration.getMappedStatements())).iterator();
        while(iterator.hasNext()) {
            Object object = iterator.next();
            if (object instanceof MappedStatement) {
                MappedStatement ms = (MappedStatement)object;
                String msId = ms.getId();
                int lastIndex = msId.lastIndexOf(".");
                String methodName = msId.substring(lastIndex + 1);
                String interfaceName = msId.substring(0, lastIndex);
                Class<?> mapperClass = Class.forName(interfaceName);
                if (BaseMapper.class.isAssignableFrom(mapperClass) && commonMethodName.indexOf(methodName) > -1 && ms.getSqlSource() instanceof ProviderSqlSource) {
                    this.setSqlSource(ms, methodName);
                }
            }
        }
    }

    public void setSqlSource(MappedStatement mappedStatement, String methodName) throws Exception {
        Class entityClass = mapperTemplate.getEntityClass(mappedStatement);
        Method executionMethod = mapperTemplate.getClass().getMethod(methodName, Class.class);
        String sql = (String)executionMethod.invoke(mapperTemplate, entityClass);
        SqlSource sqlSource = languageDriver.createSqlSource(mappedStatement.getConfiguration(), sql, entityClass);
        // 替换
        MetaObject msObject = SystemMetaObject.forObject(mappedStatement);
        msObject.setValue("sqlSource", sqlSource);
    }


}
