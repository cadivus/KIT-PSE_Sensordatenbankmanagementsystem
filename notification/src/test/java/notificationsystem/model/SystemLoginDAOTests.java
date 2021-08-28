package notificationsystem.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SystemLoginDAOTests {

    @Autowired
    SystemLoginDAO systemLoginDAO;

    @Test
    void testGetLogin() {
        long loginId = 1;

        SystemLogin systemLogin = systemLoginDAO.getLogin(loginId).get();

        assertNotNull(systemLogin);
        assertEquals("sensornotificationsystemPSE@gmail.com", systemLogin.getUsername());
        assertEquals("cKqp4Wa83pLddBv", systemLogin.getPassword());
    }
}
