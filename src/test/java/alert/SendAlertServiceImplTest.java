package alert;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.patient.service.alert.SendAlertServiceImpl;

public class SendAlertServiceImplTest {

    @Test
    public void sendTest(){
        SendAlertServiceImpl sendAlertService = Mockito.spy(SendAlertServiceImpl.class);
        String expected = "Warning, patient with id: 1, need help";
        sendAlertService.send(expected);
        Mockito.verify(sendAlertService, Mockito.times(1)).send(expected);
    }
}
