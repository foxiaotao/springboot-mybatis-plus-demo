package user;

import com.sdgtt.DemoApplication;
import com.sdgtt.model.auto.User;
import com.sdgtt.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * user模块测试
 *
 * @author tao
 * @version 2020-08-27 14:25
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class, webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration()
public class UserTest {

    @Autowired
    private IUserService userService;

    @Test
    public void userTest() {
        User u = userService.getById(1L);
        System.out.println(u);
    }
}
