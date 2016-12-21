package ProcessTest;

import logicControllerTest.GuestInControllerTest;
import logicControllerTest.GuestOutControllerTest;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by 舒展 on 2016-05-12.
 */
public class ProcessOne extends GuestInControllerTest{
    /**
     * 分别调用散客开房和散客结账
     */
    @Test
    @Transactional
    public void inAndOutSelf() throws Exception {
        GuestInControllerTest guestInControllerTest = new GuestInControllerTest();
        guestInControllerTest.setMockMvcAndHttpMessageConverter(mockMvc,mappingJackson2HttpMessageConverter);
        GuestOutControllerTest guestOutControllerTest = new GuestOutControllerTest();
        guestOutControllerTest.setMockMvcAndHttpMessageConverter(mockMvc,mappingJackson2HttpMessageConverter);
        guestInControllerTest.guestIn();
        guestOutControllerTest.guestOut();
    }
}
