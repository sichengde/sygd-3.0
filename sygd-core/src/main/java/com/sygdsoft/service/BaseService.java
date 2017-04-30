package com.sygdsoft.service;

import com.sygdsoft.jsonModel.Query;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by 舒展 on 2016-06-16.
 */
@Component
public class BaseService<T> {
    @Autowired
    ApplicationContext context;
    Object mapper;
    Class entity;

    /**
     * param[0]:条件
     * param[1]:数量
     * param[2]:排序
     */
    public List<T> get(Query query) throws Exception {
        String id = this.getClass().getAnnotation(SzMapper.class).id();
        entity = Class.forName("com.sygdsoft.model." + id.substring(0, 1).toUpperCase() + id.substring(1));
        mapper = context.getBean(id + "Mapper");
        Method selectAll = mapper.getClass().getMethod("selectAll");
        Method selectByExampleAndRowBounds = mapper.getClass().getMethod("selectByExampleAndRowBounds", Object.class, RowBounds.class);
        if (query == null) {
            return (List<T>) selectAll.invoke(mapper);
        }
        String condition = query.getCondition();
        Integer num = query.getNum();
        String[] orderByList = query.getOrderByList();
        String[] orderByDescList = query.getOrderByListDesc();
        Example example = new Example(entity);
        RowBounds rowBounds = RowBounds.DEFAULT;
        if (condition != null) {
            example.createCriteria().andCondition(condition);
        }
        if (num != null) {
            rowBounds = new RowBounds(0, num);
        }
        if (orderByList != null) {
            for (String s : orderByList) {
                example.orderBy(s);
            }
        }
        if (orderByDescList != null) {
            for (String s : orderByDescList) {
                example.orderBy(s).desc();
            }
        }
        return (List<T>) selectByExampleAndRowBounds.invoke(mapper, new Object[]{example, rowBounds});
    }

    public T getById(Integer idString) throws Exception {
        String id = this.getClass().getAnnotation(SzMapper.class).id();
        entity = Class.forName("com.sygdsoft.model." + id.substring(0, 1).toUpperCase() + id.substring(1));
        mapper = context.getBean(id + "Mapper");
        Method selectByPrimaryKey = mapper.getClass().getMethod("selectByPrimaryKey",Object.class);
        return (T) selectByPrimaryKey.invoke(mapper,idString);
    }

    public void delete(List<T> tList) throws Exception {
        if (tList.size() == 0) {
            return;
        }
        mapper = context.getBean(this.getClass().getAnnotation(SzMapper.class).id() + "Mapper");
        Method delete = mapper.getClass().getMethod("deleteByPrimaryKey", Object.class);
        for (T t : tList) {
            delete.invoke(mapper, t);
        }
    }

    public void delete(T t) throws Exception {
        mapper = context.getBean(this.getClass().getAnnotation(SzMapper.class).id() + "Mapper");
        Method delete = mapper.getClass().getMethod("deleteByPrimaryKey", Object.class);
        delete.invoke(mapper, t);
    }

    public void update(List<T> tList) throws Exception {
        if (tList.size() == 0) {
            return;
        }
        mapper = context.getBean(this.getClass().getAnnotation(SzMapper.class).id() + "Mapper");
        Method update = mapper.getClass().getMethod("updateByPrimaryKey", Object.class);
        for (T t : tList) {
            update.invoke(mapper, t);
        }
    }

    public void update(T t) throws Exception {
        mapper = context.getBean(this.getClass().getAnnotation(SzMapper.class).id() + "Mapper");
        Method update = mapper.getClass().getMethod("updateByPrimaryKey", Object.class);
        update.invoke(mapper, t);
    }

    public void updateSelective(List<T> tList) throws Exception {
        if (tList.size() == 0) {
            return;
        }
        mapper = context.getBean(this.getClass().getAnnotation(SzMapper.class).id() + "Mapper");
        Method update = mapper.getClass().getMethod("updateByPrimaryKeySelective", Object.class);
        for (T t : tList) {
            update.invoke(mapper, t);
        }
    }

    public void updateSelective(T t) throws Exception {
        mapper = context.getBean(this.getClass().getAnnotation(SzMapper.class).id() + "Mapper");
        Method update = mapper.getClass().getMethod("updateByPrimaryKeySelective", Object.class);
        update.invoke(mapper, t);
    }

    public void add(T t) throws Exception {
        mapper = context.getBean(this.getClass().getAnnotation(SzMapper.class).id() + "Mapper");
        Method insert = mapper.getClass().getMethod("insert", Object.class);
        insert.invoke(mapper, t);
    }

    public void add(List<T> t) throws Exception {
        if (t.size() == 0) {
            return;
        }
        mapper = context.getBean(this.getClass().getAnnotation(SzMapper.class).id() + "Mapper");
        Method insert = mapper.getClass().getMethod("insertList", List.class);
        insert.invoke(mapper, t);
    }

}