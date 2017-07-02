package com.sygdsoft.service;

import com.sygdsoft.mapper.StorageInDetailMapper;
import com.sygdsoft.model.Cargo;
import com.sygdsoft.model.StorageInDetail;
import com.sygdsoft.util.SzMath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Created by 舒展 on 2016-11-17.
 */
@Service
@SzMapper(id = "storageInDetail")
public class StorageInDetailService extends BaseService<StorageInDetail>{
    @Autowired
    StorageInDetailMapper storageInDetailMapper;
    @Autowired
    SzMath szMath;

    /**
     * 获得当前仓库中各个货品数量总和
     */
    public List<StorageInDetail> getSumNumByHouse(String house){
        return storageInDetailMapper.getSumNumByHouse(house);
    }

    /**
     * 获得所有仓库中各个货品数量总和(建立视图之后该方法被废弃)
     */
    public List<StorageInDetail> getSumNum(){
        return storageInDetailMapper.getSumNum();
    }

    /**
     * 获得当前仓库某个商品的余量
     */
    public Double getSumNumByNameHouse(String house,String cargo){
        List<StorageInDetail> storageInDetailList=storageInDetailMapper.getSumNumByNameHouse(house, cargo);
        if(storageInDetailList.size()==0){
            return 0.0;
        }else {
            return storageInDetailList.get(0).getRemain();
        }
    }

    /**
     * 获得该商品的入库记录（大于0的）
     */
    public List<StorageInDetail> getByCargoExist(String house,String cargo){
        return storageInDetailMapper.getByCargoExist(house,cargo);
    }

    /**
     * 计算平均价格
     */
    //TODO:有问题，超出部分时余量还是0
    public Double storageParsePrice(String house,String cargo,Double num){
        Double totalNum=num;
        List<StorageInDetail> storageInDetailList=getByCargoExist(house, cargo);
        Double totalMoney=0.0;
        for (StorageInDetail inDetail : storageInDetailList) {
            Double remain=inDetail.getRemain();
            if(num>remain){
                totalMoney+=remain*inDetail.getPrice();
                num-=remain;
            }else if (Objects.equals(num, remain)){
                totalMoney+=remain*inDetail.getPrice();
                break;
            }else {
                totalMoney+=num*inDetail.getPrice();
                break;
            }
        }
        return totalMoney/totalNum;
    }
    /**
     * 根据仓库名称删除
     */
    public void deleteByHouse(String house){
        storageInDetailMapper.deleteByHouse(house);
    }
    /**
     * 根据货品名获取，无视仓库
     */
    public List<StorageInDetail> getByCargo(String cargo){
        StorageInDetail storageInDetailQuery=new StorageInDetail();
        storageInDetailQuery.setCargo(cargo);
        return storageInDetailMapper.select(storageInDetailQuery);
    }
}
