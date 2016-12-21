package logicControllerTest;

import application.ApplicationTest;
import com.sygdsoft.model.CheckIn;
import com.sygdsoft.model.CheckInGroup;
import com.sygdsoft.model.CheckInGuest;
import com.sygdsoft.model.GuestIn;
import com.sygdsoft.model.GuestInGroup;
import com.sygdsoft.service.RoomPriceService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * Created by 舒展 on 2016-05-12.
 */
public class GuestInControllerTest extends ApplicationTest{
    private CheckIn checkInFirst;
    private CheckIn checkInSecond;
    private CheckInGuest checkInGuestFirst;
    private CheckInGuest checkInGuestSecond;
    @Autowired
    RoomPriceService roomPriceService;

    public GuestInControllerTest() {
        this.initData();
    }
    /**
     * 散客开房(普通房)
     * @throws Exception
     */
    @Test
    public void guestIn() throws Exception {
        List<CheckInGuest> checkInGuestList=new ArrayList<>();
        checkInGuestList.add(checkInGuestFirst);
        /*提交信息*/
        GuestIn guestIn=new GuestIn();
        guestIn.setCheckInGuestList(checkInGuestList);
        mockMvc.perform(post("/guestIn/")
                .content(this.json(guestIn))
                .contentType(contentType));
    }
    /**
     * 团队开房(普通房)
     * @throws Exception
     */
    @Test
    public void guestInGroup() throws Exception {
        List<CheckIn> checkInList = new ArrayList<>();
        checkInList.add(checkInFirst);
        checkInList.add(checkInSecond);
        List<CheckInGuest> checkInGuestList=new ArrayList<>();
        checkInGuestList.add(checkInGuestFirst);
        checkInGuestList.add(checkInGuestSecond);
        CheckInGroup checkInGroup=new CheckInGroup();
        checkInGroup.setName("测试团1");
        checkInGroup.setCurrency("RMB");
        checkInGroup.setDeposit(1000.0);
        /*提交信息*/
        GuestInGroup guestInGroup=new GuestInGroup();
        guestInGroup.setCheckInList(checkInList);
        guestInGroup.setCheckInGuestList(checkInGuestList);
        guestInGroup.setCheckInGroup(checkInGroup);
        mockMvc.perform(post("/guestInGroup/")
                .content(this.json(guestInGroup))
                .contentType(contentType));
    }

    private void initData(){
        /*一个checkIn*/
        checkInFirst =new CheckIn();
        checkInFirst.setRoomId("101");
        checkInFirst.setFinalRoomPrice(200.0);
        checkInFirst.setDeposit(500.0);
        checkInFirst.setCurrency("RMB");
        checkInFirst.setRoomPriceCategory(roomPriceService.day);
        /*另一个checkIn*/
        checkInSecond =new CheckIn();
        checkInSecond.setRoomId("102");
        checkInSecond.setFinalRoomPrice(200.0);
        checkInSecond.setDeposit(500.0);
        checkInSecond.setCurrency("RMB");
        checkInSecond.setRoomPriceCategory(roomPriceService.day);
        /*一个客人档案*/
        checkInGuestFirst=new CheckInGuest();
        checkInGuestFirst.setRoomId("101");
        checkInGuestFirst.setCardId("210103");
        /*另一个客人档案*/
        checkInGuestSecond=new CheckInGuest();
        checkInGuestSecond.setRoomId("102");
        checkInGuestSecond.setCardId("210104");
    }
}
