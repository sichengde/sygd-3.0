package com.sygdsoft.service;

import com.sygdsoft.jsonModel.HotelParseLineRow;
import com.sygdsoft.jsonModel.report.DeskCategoryOut;
import com.sygdsoft.jsonModel.report.DeskProfitOut;
import com.sygdsoft.mapper.DeskDetailHistoryMapper;
import com.sygdsoft.model.DeskDetailHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2016-09-13.
 */
@Service
@SzMapper(id = "deskDetailHistory")
public class DeskDetailHistoryService extends BaseService<DeskDetailHistory> {
    @Autowired
    DeskDetailHistoryMapper deskDetailHistoryMapper;

    /**
     * 通过结账序列号获得历史菜品列表
     */
    public List<DeskDetailHistory> getListByCkSerial(String ckSerial) {
        DeskDetailHistory deskDetailHistoryQuery = new DeskDetailHistory();
        deskDetailHistoryQuery.setCkSerial(ckSerial);
        return deskDetailHistoryMapper.select(deskDetailHistoryQuery);
    }

    /**
     * 获得该日期该类别的消费额(二级统计类别)
     */
    public Double getDeskMoneyByDatePointOfSale(Date beginTime, Date endTime, String firstPointOfSale, String secondPointOfSale) {
        if (secondPointOfSale == null) {
            return deskDetailHistoryMapper.getDeskMoneyByDateFirstPointOfSale(beginTime, endTime, firstPointOfSale);
        } else {
            return deskDetailHistoryMapper.getDeskMoneyByDatePointOfSale(beginTime, endTime, firstPointOfSale, secondPointOfSale);
        }
    }

    /**
     * 获得该日期该类别的消费额(二级统计类别)
     */
    public List<HotelParseLineRow> getDeskMoneyDateLineByDatePointOfSale(Date beginTime, Date endTime, String firstPointOfSale, String secondPointOfSale) {
        if (secondPointOfSale == null) {
            return deskDetailHistoryMapper.getDeskMoneyDateLineByDateFirstPointOfSale(beginTime, endTime, firstPointOfSale);
        } else {
            return deskDetailHistoryMapper.getDeskMoneyDateLineByDatePointOfSale(beginTime, endTime, firstPointOfSale, secondPointOfSale);
        }
    }

    /**
     * 获得该时间段该币种的对象列表（不带操作员）
     */
    public List<DeskDetailHistory> getByDatePointOfSale(Date beginTime, Date endTime, String firstPointOfSale, String secondPointOfSale) {
        return deskDetailHistoryMapper.getByDatePointOfSale(beginTime, endTime, firstPointOfSale, secondPointOfSale);
    }

    /**
     * 统计该时间段该类别的人数合计
     */
    public Integer getNumByDateSaleCount(Date beginTime, Date endTime, String firstPointOfSale, String saleCount) {
        return deskDetailHistoryMapper.getNumByDateSaleCount(beginTime, endTime, firstPointOfSale, saleCount);
    }

    /**
     * 统计各个菜品的销售数据，金额，数量
     */
    public List<DeskDetailHistory> getByTimePointOfSale(Date beginTime, Date endTime, String pointOfSale, String category, Boolean mergeFood) {
        if (mergeFood) {
            if (category != null) {
                return deskDetailHistoryMapper.getByTimePointOfSaleCategoryMerge(beginTime, endTime, pointOfSale, category);
            } else {
                return deskDetailHistoryMapper.getByTimePointOfSaleMerge(beginTime, endTime, pointOfSale);
            }
        } else {
            if (category != null) {
                return deskDetailHistoryMapper.getByTimePointOfSaleCategory(beginTime, endTime, pointOfSale, category);
            } else {
                return deskDetailHistoryMapper.getByTimePointOfSale(beginTime, endTime, pointOfSale);
            }
        }
    }

    /**
     * 类别分析
     */
    /*不限定账单号，分析三级类别*/
    public List<DeskCategoryOut> getCategoryParse(Date beginTime, Date endTime, String pointOfSale) {
        return deskDetailHistoryMapper.getCategoryParse(beginTime, endTime, pointOfSale);
    }

    /*根据某个账单，分析三级类别*/
    public List<DeskCategoryOut> getCategoryParseBySerial(Date beginTime, Date endTime, String pointOfSale, String ckSerial) {
        return deskDetailHistoryMapper.getCategoryParseBySerial(beginTime, endTime, pointOfSale, ckSerial);
    }

    /*不限定账单号，分析二级类别*/
    public List<DeskCategoryOut> getCategorySecondParse(Date beginTime, Date endTime, String pointOfSale) {
        return deskDetailHistoryMapper.getCategorySecondParse(beginTime, endTime, pointOfSale);
    }

    /*根据某个账单，分析二级类别*/
    public List<DeskCategoryOut> getCategorySecondParseBySerial(Date beginTime, Date endTime, String pointOfSale, String ckSerial) {
        return deskDetailHistoryMapper.getCategorySecondParseBySerial(beginTime, endTime, pointOfSale, ckSerial);
    }

    /**
     * 毛利率
     */
    public List<DeskProfitOut> getDeskProfitList(Date beginTime, Date endTime, String pointOfSale, String category) {
        if (category != null) {
            return deskDetailHistoryMapper.getDeskProfitListByCategory(beginTime, endTime, pointOfSale, category);
        } else {
            return deskDetailHistoryMapper.getDeskProfitList(beginTime, endTime, pointOfSale);
        }
    }

    /**
     * 查询所有没有统计出库的商品
     */
    public List<DeskDetailHistory> getByStorageDone() {
        return deskDetailHistoryMapper.getByStorageDone();
    }

    /**
     * 设置为不可用
     */
    public void setDisabledBySerial(String serial) {
        deskDetailHistoryMapper.setDisabledBySerial(serial);
    }
}
