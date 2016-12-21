package com.sygdsoft.service;

import com.sygdsoft.mapper.ProtocolMapper;
import com.sygdsoft.model.Protocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 舒展 on 2016-06-17.
 */
@Service
@SzMapper(id = "protocol")
public class ProtocolService extends BaseService<Protocol>{
    @Autowired
    ProtocolMapper protocolMapper;
    /**
     * 通过房价协议名获得唯一协议（针对于自定义房价）
     */
    public Protocol getByNameTemp(String protocolName){
        Protocol protocolQuery=new Protocol();
        protocolQuery.setProtocol(protocolName);
        protocolQuery.setTemp(true);
        return protocolMapper.selectOne(protocolQuery);
    }
}
