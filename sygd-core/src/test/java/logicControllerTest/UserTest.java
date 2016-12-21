package logicControllerTest;

import application.ApplicationTest;
import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.mapper.*;
import com.sygdsoft.model.User;
import com.sygdsoft.model.UserLog;
import com.sygdsoft.model.VipIdNumber;
import com.sygdsoft.service.TimeService;
import com.sygdsoft.service.UserLogService;
import com.sygdsoft.service.VipIdNumberService;
import com.sygdsoft.util.Util;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * Created by 舒展 on 2016-05-15.
 */
public class UserTest extends ApplicationTest {
/*
    @Test
    public void login() throws Exception {
        User user = new User("舒展", "0");
        mockMvc.perform(post("/user/login")
                .content(json(user))
                .contentType(contentType))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getModule() throws Exception {
        MvcResult result = mockMvc.perform(get("/module"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Autowired
    BookMapper bookMapper;
    @Autowired
    Util util;
    @Autowired
    SqlMapper sqlMapper;
    @Autowired
    BookRoomMapper bookRoomMapper;
    @Test
    public void testMapper() throws Exception {
        bookRoomMapper.setOpened("1160829001","102");
    }

    @Autowired
    UserLogService userLogService;

    @Test
    public void testInvoker() throws Exception {
        UserLog userLog = new UserLog();
        userLog.setUserId("张晶");
        Query query = new Query();
        List<UserLog> userLogList = userLogService.get(null);
        System.out.println(userLogList.size());
        //userLogList=userLogService.add(userLog);
    }

    @Autowired
    DebtHistoryMapper debtHistoryMapper;
    @Autowired
    TimeService timeService;
    @Autowired
    CalendarMapper calendarMapper;
    @Autowired
    UserMapper userMapper;

    @Autowired
    VipIdNumberService vipIdNumberService;
    @Test
    public void test2() throws Exception {
        List<VipIdNumber> vipIdNumberServiceList=new ArrayList<>();
        for (Integer i = 0; i < 100; i++) {
            vipIdNumberServiceList.add(new VipIdNumber(String.valueOf(i)));
        }
        vipIdNumberService.add(vipIdNumberServiceList);
    }*/
}
