package logicControllerTest;

import application.ApplicationTest;
import com.sygdsoft.model.Debt;
import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * Created by 舒展 on 2016-05-12.
 */
public class DebtControllerTest extends ApplicationTest{
    @Test
    public void addOtherConsume() throws Exception {
        Debt debt=new Debt();
        debt.setPointOfSale("JQ");
        debt.setConsume(200.0);
        debt.setRoomId("101");
        mockMvc.perform(post("/otherConsume")
                .content(this.json(debt))
                .contentType(contentType));
    }
}
