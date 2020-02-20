package com.pactera.demo.common.helper;

import com.pactera.demo.annotation.TableInfo;
import com.pactera.demo.common.mapper.BaseMapper;
import org.apache.ibatis.mapping.MappedStatement;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: mapper模板
 * @author: LiuZhiJun
 * @create: 2019-08-11 18:30
 **/
@Component
public class MapperTemplate {
    private Map<String, Class<?>> entityClassMap = new HashMap();
    private Map<Class<?>, EntityTableInfo> entityTableMap = new HashMap();

    public Class<?> getMapperClass(String msId){
        if (msId.indexOf(".") == -1) {
            throw new RuntimeException("当前MappedStatement的id=" + msId + ",不符合MappedStatement的规则!");
        } else {
            String mapperClassStr = msId.substring(0, msId.lastIndexOf("."));

            try {
                return Class.forName(mapperClassStr);
            } catch (ClassNotFoundException var3) {
                return null;
            }
        }
    }

    public Class<?> getEntityClass(MappedStatement ms) {
        String msId = ms.getId();
        if (this.entityClassMap.containsKey(msId)) {
            return (Class)this.entityClassMap.get(msId);
        } else {
            Class<?> mapperClass = getMapperClass(msId);
            Type[] types = mapperClass.getGenericInterfaces();
            Class<?> entityClass = null;
            for (Type type : types) {
                if (type instanceof ParameterizedType) {
                    ParameterizedType t = (ParameterizedType) type;
                    //判断父接口是否为 BaseMapper.class
                    if (t.getRawType() == BaseMapper.class) {
                        //得到泛型类型
                        Class<?> returnType = (Class<?>) t.getActualTypeArguments()[0];
                        this.entityClassMap.put(msId, returnType);
                        return returnType;
                    }
                }
            }
            throw new RuntimeException("无法获取Mapper<T>泛型类型:" + msId);
        }
    }

    public EntityTableInfo getEntityTableInfo(Class<?> entityClass) throws Exception{
        if(this.entityTableMap.containsKey(entityClass)){
            return this.entityTableMap.get(entityClass);
        }else{
            TableInfo tableInfo = entityClass.getAnnotation(TableInfo.class);
            String tableName = tableInfo.tableName();
            if(StringUtils.isEmpty(tableName)){
                throw new RuntimeException("无法获取Mapper<T>泛型类型的表名信息"+entityClass);
            }
            String primaryKey = tableInfo.primaryKey();
            if(StringUtils.isEmpty(primaryKey)){
                throw new RuntimeException("无法获取Mapper<T>泛型类型的主键信息"+entityClass);
            }
            EntityTableInfo entityTable = new EntityTableInfo(tableName, primaryKey);
            this.entityTableMap.put(entityClass, entityTable);
            return entityTable;
        }
    }

    public String insert(Class<?> entityClass) throws Exception{
        Field[] fields = entityClass.getDeclaredFields();
        EntityTableInfo entityTableInfo = this.getEntityTableInfo(entityClass);
        StringBuilder sql = new StringBuilder();
        sql.append("<script>");
        sql.append("insert into ");
        sql.append(entityTableInfo.getTableName());
        sql.append("(").append(getColumns(fields)).append(")");
        sql.append(" values(");
        sql.append(getInsertColumns(fields, ""));
        sql.append(")");
        sql.append("</script>");
        return sql.toString();
    }

    public String update(Class<?> entityClass) throws Exception{
        Field[] fields = entityClass.getDeclaredFields();
        EntityTableInfo entityTableInfo = this.getEntityTableInfo(entityClass);
        StringBuilder sql = new StringBuilder();
        sql.append("<script>");
        sql.append("update ").append(entityTableInfo.getTableName());
        sql.append(setAllColumns(fields, entityTableInfo.getPrimaryKey()));
        sql.append(wherePK(entityTableInfo.getPrimaryKey()));
        sql.append("</script>");
        return sql.toString();
    }

    public String delete(Class<?> entityClass) throws Exception{
        EntityTableInfo entityTableInfo = this.getEntityTableInfo(entityClass);
        StringBuilder sql = new StringBuilder();
        sql.append("<script>");
        sql.append("delete from ").append(entityTableInfo.getTableName());
        sql.append(wherePK(entityTableInfo.getPrimaryKey()));
        sql.append("</script>");
        return sql.toString();
    }

    public String selectAll(Class<?> entityClass) throws Exception {
        Field[] fields = entityClass.getDeclaredFields();
        StringBuilder sql = new StringBuilder();
        sql.append("<script>");
        sql.append("select ");
        sql.append(getColumns(fields));
        EntityTableInfo entityTableInfo = this.getEntityTableInfo(entityClass);
        sql.append(" from ");
        sql.append(entityTableInfo.getTableName());
        sql.append(whereAllColumns(fields));
        sql.append("</script>");
        return sql.toString();
    }

    public String selectByPK(Class<?> entityClass) throws Exception{
        Field[] fields = entityClass.getDeclaredFields();
        StringBuilder sql = new StringBuilder();
        sql.append("<script>");
        sql.append("select ");
        sql.append(getColumns(fields));
        EntityTableInfo entityTableInfo = this.getEntityTableInfo(entityClass);
        sql.append(" from ");
        sql.append(entityTableInfo.getTableName());
        sql.append(wherePK(entityTableInfo.getPrimaryKey()));
        sql.append("</script>");
        return sql.toString();
    }

    public String getInsertColumns(Field[] fields, String primary){
        StringBuilder result = new StringBuilder();
        for (int i=0,length = fields.length; i<length;i++){
            if(!primary.equals(fields[i].getName())){
                if(i == 0){
                    result.append("#{").append(fields[i].getName()).append("}");
                }else{
                    result.append(",").append("#{").append(fields[i].getName()).append("}");
                }
            }
        }
        return result.toString();
    }

    public String getColumns(Field[] fields){
        StringBuilder result = new StringBuilder();
        for (int i=0,length = fields.length; i<length;i++){
            if(i == 0){
                result.append(fields[i].getName());
            }else{
                result.append(",").append(fields[i].getName());
            }
        }
        return result.toString();
    }

    public String setAllColumns(Field[] fields, String pk){
        List<String> pkList = Arrays.asList(pk.split(","));
        StringBuilder result = new StringBuilder();
        result.append(" <set>");
        for (Field field : fields) {
            if(pkList.indexOf(field.getName()) == -1){
                result.append("<if test=\"")
                        .append(field.getName()).append("!=null\">");
                //字段名直接作为列名
                result.append(field.getName())
                        .append(" = #{").append(field.getName()).append("}").append(",");
                result.append("</if>");
            }
        }
        result.append("</set>");
        return result.toString();
    }

    public String whereAllColumns(Field[] fields){
        StringBuilder result = new StringBuilder();
        result.append(" <where>");
        result.append(allColumns(fields));
        result.append("</where>");
        return result.toString();
    }

    public String allColumns(Field[] fields){
        StringBuilder result = new StringBuilder();
        for (Field field : fields) {
            result.append("<if test=\"")
                    .append(field.getName()).append("!=null\">");
            //字段名直接作为列名
            result.append(" and ").append(field.getName())
                    .append(" = #{").append(field.getName()).append("}");
            result.append("</if>");
        }
        return result.toString();
    }

    public String wherePK(String pk){
        String[] split = pk.split(",");
        StringBuilder result = new StringBuilder();
        result.append(" <where>");
        for (String primary : split) {
            result.append("<if test=\"")
                    .append(primary).append("!=null\">");
            //字段名直接作为列名
            result.append(" and ").append(primary)
                    .append(" = #{").append(primary).append("}");
            result.append("</if>");
        }
        result.append("</where>");
        return result.toString();
    }
}
