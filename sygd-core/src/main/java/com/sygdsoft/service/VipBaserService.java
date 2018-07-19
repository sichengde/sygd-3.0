package com.sygdsoft.service;

import com.sygdsoft.conf.dataSource.HotelGroup;
import com.sygdsoft.jsonModel.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class VipBaserService<T> extends BaseService<T> {
    @Override
    @HotelGroup
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void add(T vip) throws Exception {
        super.add(vip);
    }

    @Override
    @HotelGroup
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void add(List<T> t) throws Exception {
        super.add(t);
    }

    @Override
    @HotelGroup
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void delete(List<T> vips) throws Exception {
        super.delete(vips);
    }

    @Override
    @HotelGroup
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void delete(T vip) throws Exception {
        super.delete(vip);
    }

    @Override
    @HotelGroup
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void update(List<T> vips) throws Exception {
        super.update(vips);
    }

    @Override
    @HotelGroup
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void update(T vip) throws Exception {
        super.update(vip);
    }

    @Override
    @HotelGroup
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void updateSelective(List<T> vips) throws Exception {
        super.updateSelective(vips);
    }

    @Override
    @HotelGroup
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void updateSelective(T vip) throws Exception {
        super.updateSelective(vip);
    }

    @Override
    @HotelGroup
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public List<T> get() throws Exception {
        return super.get();
    }

    @Override
    @HotelGroup
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public List<T> get(Query query) throws Exception {
        return super.get(query);
    }

    @Override
    @HotelGroup
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public T getById(Integer idString) throws Exception {
        return super.getById(idString);
    }
}
