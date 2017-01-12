package com.sygdsoft.service;

import com.sygdsoft.model.InterfaceAddr;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 舒展 on 2017-01-12.
 */
@Service
@SzMapper(id = "interfaceAddr")
public class InterfaceAddrService extends BaseService<InterfaceAddr> {
}
