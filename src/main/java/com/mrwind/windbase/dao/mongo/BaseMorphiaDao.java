package com.mrwind.windbase.dao.mongo;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * 对morphia框架的扩展接口，支持以下功能：
 * 1.根据id更新对象属性
 * 2.根据某个字段更新对象属性
 * @author wuyiming
 */
public interface BaseMorphiaDao<T,K> {

    /**
     * 根据Id更新类属性
     * @param entity 对象
     * @return       更新个数
     */
    @SuppressWarnings("unchecked")
    default int insertOrUpdateById(T entity) {
        BasicDAO<T,K> basicDAO = (BasicDAO<T,K>) this;
        Query<T> query = basicDAO.createQuery();
        UpdateOperations<T> update = basicDAO.createUpdateOperations();

        for (Field field : entity.getClass().getDeclaredFields()) {
            //过滤静态字段
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }

            String fieldName = field.getName();
            Id id = field.getAnnotation(Id.class);
            field.setAccessible(true);
            Object value;
            try {
                value = field.get(entity);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            if (id != null) {
                //id为空则插入新值
                if (value == null) {
                    basicDAO.save(entity);
                    return 1;
                }
                query.field(fieldName).equal(new ObjectId((String)value));
            } else {
                if (value != null) {
                    update.set(fieldName,value);
                }
            }
        }

        return basicDAO.updateFirst(query,update).getUpdatedCount();
    }

    /**
     * 根据某个字段更新属性
     * @param entity    对象
     * @param fieldName 字段名
     * @return          更新个数
     */
    @SuppressWarnings("unchecked")
    default int updateByField(T entity, String fieldName) {
        BasicDAO<T,K> basicDAO = (BasicDAO<T,K>) this;
        Query<T> query = basicDAO.createQuery();
        UpdateOperations<T> update = basicDAO.createUpdateOperations();

        for (Field field : entity.getClass().getDeclaredFields()) {
            //过滤静态字段
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }

            String name = field.getName();
            Id id = field.getAnnotation(Id.class);
            field.setAccessible(true);
            Object value;
            try {
                value = field.get(entity);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            if (name.equals(fieldName)) {
                if (value == null) {
                    throw new RuntimeException(String.format("The value of '%s' is null",fieldName));
                }
                query.field(fieldName).equal(value);
            } else {
                if (value != null && id == null) {
                    update.set(name,value);
                }
            }
        }

        return basicDAO.update(query,update).getUpdatedCount();
    }

}