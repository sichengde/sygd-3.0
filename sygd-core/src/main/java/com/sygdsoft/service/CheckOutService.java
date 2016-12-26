package com.sygdsoft.service;

import com.sygdsoft.mapper.CheckOutMapper;
import com.sygdsoft.model.CheckIn;
import com.sygdsoft.model.CheckOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 舒展 on 2016-05-10.
 */
@Service
@SzMapper(id = "checkOut")
public class CheckOutService extends BaseService<CheckOut>{
}
