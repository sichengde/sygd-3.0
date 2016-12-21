package com.sygdsoft.service;

import com.sygdsoft.mapper.GuestSourceMapper;
import com.sygdsoft.model.GuestSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016-05-23.
 */
@Service
@SzMapper(id = "guestSource")
public class GuestSourceService extends BaseService<GuestSource>{

}
