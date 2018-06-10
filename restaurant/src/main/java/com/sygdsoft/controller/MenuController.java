package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.CookRoom;
import com.sygdsoft.model.Menu;
import com.sygdsoft.model.SaleCount;
import com.sygdsoft.service.CleanRoomService;
import com.sygdsoft.service.CookRoomService;
import com.sygdsoft.service.MenuService;
import com.sygdsoft.service.SaleCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by Administrator on 2016/7/18 0018.
 */
@RestController
public class MenuController {
    @Autowired
    MenuService menuService;
    @Autowired
    CookRoomService cookRoomService;
    @Autowired
    SaleCountService saleCountService;

    @RequestMapping(value = "menuAdd")
    @Transactional
    public void menuAdd(@RequestBody Menu menu) throws Exception {
        /*检查厨房有没有*/
        //checkCookRoom(menu.getCookRoom());
        /*增加的时候看看有没有厨房，没有的话用营业部门的同步*/
        if (menu.getCookRoom() == null || menu.getCookRoom().equals("")) {
            SaleCount saleCount = saleCountService.getByName(menu.getPointOfSale(), menu.getCategory());
            menu.setCookRoom(saleCount.getCookRoom());
        }
        if(menu.getAlias()==null){
            menu.setAlias(getAllFirstLetter(menu.getName()));
        }
        menuService.add(menu);
    }

    @RequestMapping(value = "menuDelete")
    @Transactional(rollbackFor = Exception.class)
    public void menuDelete(@RequestBody List<Menu> menuList) throws Exception {
        menuService.delete(menuList);
    }

    @RequestMapping(value = "menuUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void menuUpdate(@RequestBody List<Menu> menuList) throws Exception {
        if (menuList.size() > 1) {
            if (menuList.get(0).getId().equals(menuList.get(menuList.size() / 2).getId())) {
                List<Menu> menuListHalf=menuList.subList(0, menuList.size() / 2);
                menuService.update(menuListHalf);
                return;
            }
        }
        menuService.update(menuList);
    }

    @RequestMapping(value = "menuGet")
    public List<Menu> menuGet(@RequestBody Query query) throws Exception {
        return menuService.get(query);
    }

    private void checkCookRoom(String cookRoom) throws Exception {
        if(cookRoom!=null) {
            String[] cookRoomList = cookRoom.split(",");
            for (String s : cookRoomList) {
                if (cookRoomService.getByCookName(s).size()==0) {
                    throw new Exception("厨房:" + s + " 不存在");
                }
            }
        }
    }

    /**
     * 自动生成缩写
     */
    private final static int[] li_SecPosValue = { 1601, 1637, 1833, 2078, 2274,
            2302, 2433, 2594, 2787, 3106, 3212, 3472, 3635, 3722, 3730, 3858,
            4027, 4086, 4390, 4558, 4684, 4925, 5249, 5590 };
    private final static String[] lc_FirstLetter = { "a", "b", "c", "d", "e",
            "f", "g", "h", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "w", "x", "y", "z" };

    private String conversionStr(String str, String charsetName,String toCharsetName) throws UnsupportedEncodingException {
        try {
            str = new String(str.getBytes(charsetName), toCharsetName);
        } catch (UnsupportedEncodingException ex) {
            System.out.println("字符串编码转换异常：" + ex.getMessage());
        }
        return str;
    }

    public String getAllFirstLetter(String str) throws UnsupportedEncodingException {
        if (str == null || str.trim().length() == 0) {
            return "";
        }

        String _str = "";
        for (int i = 0; i < str.length(); i++) {
            _str = _str + this.getFirstLetter(str.substring(i, i + 1));
        }

        return _str;
    }

    private String getFirstLetter(String chinese) throws UnsupportedEncodingException {
        if (chinese == null || chinese.trim().length() == 0) {
            return "";
        }
        chinese = this.conversionStr(chinese, "GB2312", "ISO8859-1");

        if (chinese.length() > 1) // 判断是不是汉字
        {
            int li_SectorCode = (int) chinese.charAt(0); // 汉字区码
            int li_PositionCode = (int) chinese.charAt(1); // 汉字位码
            li_SectorCode = li_SectorCode - 160;
            li_PositionCode = li_PositionCode - 160;
            int li_SecPosCode = li_SectorCode * 100 + li_PositionCode; // 汉字区位码
            if (li_SecPosCode > 1600 && li_SecPosCode < 5590) {
                for (int i = 0; i < 23; i++) {
                    if (li_SecPosCode >= li_SecPosValue[i]
                            && li_SecPosCode < li_SecPosValue[i + 1]) {
                        chinese = lc_FirstLetter[i];
                        break;
                    }
                }
            } else // 非汉字字符,如图形符号或ASCII码
            {
                chinese = this.conversionStr(chinese, "ISO8859-1", "GB2312");
                chinese = chinese.substring(0, 1);
            }
        }

        return chinese;
    }
}
