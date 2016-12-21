package com.sygdsoft.service;

import com.sygdsoft.model.StorageInDept;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

/**
 * Created by 舒展 on 2016-11-17.
 */
@Service
@SzMapper(id = "storageInDept")
public class StorageInDeptService extends BaseService<StorageInDept>{
}
