package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.Supplier;
import com.sygdsoft.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2016/11/28 0028.
 */
@RestController
public class SupplierController {
    @Autowired
    SupplierService supplierService;

    @RequestMapping(value = "supplierAdd")
    public void supplierAdd(@RequestBody Supplier supplier) throws Exception {
        supplierService.add(supplier);
    }

    @RequestMapping(value = "supplierDelete")
    @Transactional(rollbackFor = Exception.class)
    public void supplierDelete(@RequestBody List<Supplier> supplierList) throws Exception {
        supplierService.delete(supplierList);
    }

    @RequestMapping(value = "supplierUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void supplierUpdate(@RequestBody List<Supplier> supplierList) throws Exception {
        if (supplierList.size() > 1) {
            if (supplierList.get(0).getId().equals(supplierList.get(supplierList.size() / 2).getId())) {
                supplierService.update(supplierList.subList(0, supplierList.size() / 2));
                return;
            }
        }
        supplierService.update(supplierList);
    }

    @RequestMapping(value = "supplierGet")
    public List<Supplier> supplierGet(@RequestBody Query query) throws Exception {
        return supplierService.get(query);
    }
}
