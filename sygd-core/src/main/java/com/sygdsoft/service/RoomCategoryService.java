package com.sygdsoft.service;

import com.sygdsoft.mapper.RoomCategoryMapper;
import com.sygdsoft.model.RoomCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016-05-23.
 */
@Service
@SzMapper(id = "roomCategory")
public class RoomCategoryService extends BaseService<RoomCategory>{

}
