package com.sygdsoft.model;

import java.util.List;

/**
 * Created by Administrator on 2016/11/19 0019.
 */
public class StorageOutJson {
    private List<StorageOutDetail> storageOutDetailList;
    private StorageOut storageOut;

    public StorageOutJson() {
    }

    public List<StorageOutDetail> getStorageOutDetailList() {
        return storageOutDetailList;
    }

    public void setStorageOutDetailList(List<StorageOutDetail> storageOutDetailList) {
        this.storageOutDetailList = storageOutDetailList;
    }

    public StorageOut getStorageOut() {
        return storageOut;
    }

    public void setStorageOut(StorageOut storageOut) {
        this.storageOut = storageOut;
    }
}
