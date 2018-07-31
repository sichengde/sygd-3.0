package com.sygdsoft.service;

import com.sygdsoft.conf.dataSource.HotelGroup;
import com.sygdsoft.jsonModel.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class VipBaserService<T> extends BaseService<T> {
    @Override
    @HotelGroup

    public void add(T vip) throws Exception {
        super.add(vip);
    }

    @Override
    @HotelGroup

    public void add(List<T> t) throws Exception {
        super.add(t);
    }

    @Override
    @HotelGroup

    public void delete(List<T> vips) throws Exception {
        super.delete(vips);
    }

    @Override
    @HotelGroup

    public void delete(T vip) throws Exception {
        super.delete(vip);
    }

    @Override
    @HotelGroup

    public void update(List<T> vips) throws Exception {
        super.update(vips);
    }

    @Override
    @HotelGroup

    public void update(T vip) throws Exception {
        super.update(vip);
    }

    @Override
    @HotelGroup

    public void updateSelective(List<T> vips) throws Exception {
        super.updateSelective(vips);
    }

    @Override
    @HotelGroup

    public void updateSelective(T vip) throws Exception {
        super.updateSelective(vip);
    }

    @Override
    @HotelGroup

    public List<T> get() throws Exception {
        return super.get();
    }

    @Override
    @HotelGroup

    public List<T> get(Query query) throws Exception {
        return super.get(query);
    }

    @Override
    @HotelGroup

    public T getById(Integer idString) throws Exception {
        return super.getById(idString);
    }
}
