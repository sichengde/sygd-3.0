package com.sygdsoft.model;

import com.sygdsoft.controller.StorageInController;

import java.util.List;

/**
 * Created by 舒展 on 2016-11-17.
 */
public class StorageInJson {
    private List<StorageInDetail> storageInDetailList;
    private StorageIn storageIn;

    public StorageInJson() {
    }

    public List<StorageInDetail> getStorageInDetailList() {
        return storageInDetailList;
    }

    public void setStorageInDetailList(List<StorageInDetail> storageInDetailList) {
        this.storageInDetailList = storageInDetailList;
    }

    public StorageIn getStorageIn() {
        return storageIn;
    }

    public void setStorageIn(StorageIn storageIn) {
        this.storageIn = storageIn;
    }
}
