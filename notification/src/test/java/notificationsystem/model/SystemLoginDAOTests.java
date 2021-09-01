package notificationsystem.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SystemLoginDAOTests {

    @Autowired
    SystemLoginDAO systemLoginDAO;
    @MockBean
    SystemLoginRepository systemLoginRepository;

    @Test
    public void testGetLogin() {
        long loginId = 1;
        SystemLogin systemLogin = new SystemLogin();
        Mockito.when(systemLoginRepository.findById(loginId)).thenReturn(Optional.of(systemLogin));

        SystemLogin returnedSystemLogin = systemLoginDAO.getLogin(loginId).get();

        assertNotNull(systemLogin);
        assertEquals(systemLogin.getUsername(), returnedSystemLogin.getUsername());
        assertEquals(systemLogin.getPassword(), returnedSystemLogin.getPassword());
    }

    @Configuration
    @Import(SystemLoginDAO.class)
    static class TestConfig {
        @Bean
        SystemLoginRepository systemLoginRepository() {
            return mock(SystemLoginRepository.class);
        }
    }
}
