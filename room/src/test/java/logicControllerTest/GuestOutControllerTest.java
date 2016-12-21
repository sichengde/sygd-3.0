package logicControllerTest;

import application.ApplicationTest;
import com.sygdsoft.model.GuestOut;
import com.sygdsoft.model.GuestOutGroup;
import com.sygdsoft.service.SerialService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * Created by 舒展 on 2016-05-12.
 */
public class GuestOutControllerTest extends ApplicationTest {
    @Autowired
    SerialService serialService;

    public GuestOutControllerTest() {
    }

    /**
     * 单人离店结算
     */
    @Test
    public void guestOut() throws Exception {
        GuestOut guestOut=new GuestOut();
        mockMvc.perform(post("/guestOut/")
                .content(json(guestOut))
                .contentType(contentType));
    }
    /**
     * 团队离店结算
     */
    @Test
    public void guestOutGroup() throws Exception {
        GuestOutGroup guestOutGroup=new GuestOutGroup();
        guestOutGroup.setGroupAccount(4000001);
        mockMvc.perform(post("/guestOutGroup/")
                .content(json(guestOutGroup))
                .contentType(contentType));
    }
}
