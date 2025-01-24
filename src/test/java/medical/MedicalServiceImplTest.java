package medical;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.entity.HealthInfo;
import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoFileRepository;
import ru.netology.patient.service.alert.SendAlertServiceImpl;
import ru.netology.patient.service.medical.MedicalServiceImpl;

import java.math.BigDecimal;

public class MedicalServiceImplTest {
    MedicalServiceImpl medicalService;
    BloodPressure bloodPressure;
    HealthInfo healthInfo;
    PatientInfo patientInfo;
    SendAlertServiceImpl alertService;
    PatientInfoFileRepository patientInfoFileRepository;

    @BeforeEach
    public void setUp() {
        bloodPressure = new BloodPressure(120, 80);
        healthInfo = new HealthInfo(new BigDecimal("36.6"), bloodPressure);
        patientInfo = new PatientInfo("1", "Anna", "Karenina", null, healthInfo);
        patientInfoFileRepository = Mockito.mock(PatientInfoFileRepository.class);
        alertService = Mockito.spy(SendAlertServiceImpl.class);
        medicalService = new MedicalServiceImpl(patientInfoFileRepository, alertService);
        Mockito.when(patientInfoFileRepository.getById("1")).thenReturn(patientInfo);
    }

    @Test
    public void checkLowBloodPressureTest() {
        BloodPressure bloodPressureBadly = new BloodPressure(90, 60);
        String expected = "Warning, patient with id: 1, need help";
        medicalService.checkBloodPressure("1", bloodPressureBadly);
        Mockito.verify(alertService).send(expected);
    }

    @Test
    public void checkNormalBloodPressureTest() {
        BloodPressure bloodPressureNormal = new BloodPressure(120, 80);
        String expected = "Warning, patient with id: 1, need help";
        medicalService.checkBloodPressure("1", bloodPressureNormal);
        Mockito.verify(alertService, Mockito.times(0)).send(expected);
    }

    @Test
    public void checkHighBloodPressureTest() {
        BloodPressure bloodPressureBadly = new BloodPressure(160, 100);
        String expected = "Warning, patient with id: 1, need help";
        medicalService.checkBloodPressure("1", bloodPressureBadly);
        Mockito.verify(alertService).send(expected);
    }

    @Test
    public void checkTemperatureTest() {
        String expected = "Warning, patient with id: 1, need help";
        medicalService.checkTemperature("1", new BigDecimal("34.0"));
        Mockito.verify(alertService).send(expected);
    }

    @Test
    public void checkNormalTemperatureTest() {
        String expected = "Warning, patient with id: 1, need help";
        medicalService.checkTemperature("1", new BigDecimal("36.6"));
        Mockito.verify(alertService, Mockito.times(0)).send(expected);
    }
}
