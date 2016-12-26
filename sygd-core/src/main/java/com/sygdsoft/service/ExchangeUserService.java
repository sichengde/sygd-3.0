package com.sygdsoft.service;

import com.sygdsoft.mapper.ExchangeUserMapper;
import com.sygdsoft.model.DebtPay;
import com.sygdsoft.model.ExchangeUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 舒展 on 2016-06-27.
 */
@Service
@SzMapper(id = "exchangeUser")
public class ExchangeUserService extends BaseService<ExchangeUser>{
}
