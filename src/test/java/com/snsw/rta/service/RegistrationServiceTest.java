package com.snsw.rta.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.snsw.rta.model.InsurerModel;
import com.snsw.rta.model.RegistrationModel;
import com.snsw.rta.model.RegistrationPlateModel;
import com.snsw.rta.model.RegistrationPlateRequest;
import com.snsw.rta.model.RegistrationsModel;
import com.snsw.rta.model.VehicleModel;
import com.snsw.rta.persistence.Insurer;
import com.snsw.rta.persistence.RegistrationPlate;
import com.snsw.rta.persistence.Vehicle;
import com.snsw.rta.persistence.repository.InsurerRepository;
import com.snsw.rta.persistence.repository.RegistrationPlateRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
class RegistrationServiceTest {

  private InsurerRepository insurerRepository;

  private RegistrationPlateRepository registrationPlateRepository;

  private RegistrationService registrationService;

  @BeforeEach
  void setUp() {
    insurerRepository = mock(InsurerRepository.class);
    registrationPlateRepository = mock(RegistrationPlateRepository.class);
    registrationService = new RegistrationService(insurerRepository, registrationPlateRepository);
  }

  @Test
  void createRegistrationPlate() {
    when(insurerRepository.findByName(anyString())).thenReturn(
        Insurer.builder().code(1L).name("GIO").build());
    ArgumentCaptor<RegistrationPlate> registrationPlateArgumentCaptor =
        ArgumentCaptor.forClass(RegistrationPlate.class);

    RegistrationPlateRequest registrationPlateRequest =
        RegistrationPlateRequest.builder()
            .plateNumber("WOP29P")
            .registrationExpiry(LocalDateTime.parse("2020-12-08T23:15:30.000Z", DateTimeFormatter.ISO_DATE_TIME))
            .type("Sedan")
            .make("Mercedes")
            .model("X4 M40i")
            .colour("Blue")
            .vin("87676676762")
            .tareWeight(1700)
            .grossMass(null)
            .insurerName("GIO")
            .build();

    registrationService.createRegistrationPlate(registrationPlateRequest);
    verify(registrationPlateRepository, times(1))
        .save(registrationPlateArgumentCaptor.capture());
    RegistrationPlate registrationPlate = registrationPlateArgumentCaptor.getValue();
    assertNotNull(registrationPlate);
    assertEquals("WOP29P", registrationPlate.getPlateNumber());
    Vehicle vehicle = registrationPlate.getVehicle();
    assertEquals("2020-12-08T23:15:30", vehicle.getRegistrationExpiryDate().toString());
    assertEquals("Sedan", vehicle.getType());
    assertEquals("Mercedes", vehicle.getMake());
    assertEquals("X4 M40i", vehicle.getModel());
    assertEquals("Blue", vehicle.getColour());
    assertEquals("87676676762", vehicle.getVin());
    assertEquals(1700, vehicle.getTareWeight());
    assertNull(vehicle.getGrossMass());
    assertEquals(1L, vehicle.getInsurerId().longValue());
  }

  @Test
  void getRegistrationPlates() {
    Vehicle vehicle = Vehicle.builder()
        .id(1L)
        .type("Sedan")
        .make("Mercedes")
        .model("X4 M40i")
        .colour("Blue")
        .vin("87676676762")
        .tareWeight(1700)
        .grossMass(null)
        .registrationExpiryDate(LocalDateTime.parse("2020-12-08T23:15:30"))
        .insurerId(1L)
        .build();
    RegistrationPlate registrationPlate = RegistrationPlate.builder()
        .id(1L)
        .plateNumber("WOP29P")
        .vehicle(vehicle)
        .build();

    when(registrationPlateRepository.findAll())
        .thenReturn(Collections.singletonList(registrationPlate));
    when(insurerRepository.findById(anyLong()))
        .thenReturn(Optional.of(Insurer.builder().code(1L).name("GIO").build()));

    RegistrationsModel registrationsModel = registrationService.getRegistrations();

    assertNotNull(registrationsModel);
    assertFalse(registrationsModel.getRegistrations().isEmpty());
    assertTrue(registrationsModel.getRegistrations().size() ==1);

    RegistrationPlateModel registrationPlateModel = registrationsModel.getRegistrations().get(0);
    assertNotNull(registrationPlateModel);
    assertEquals("WOP29P", registrationPlateModel.getPlateNumber());

    RegistrationModel registrationModel = registrationPlateModel.getRegistrationModel();
    assertNotNull(registrationModel);
    assertFalse(registrationModel.isExpired());
    assertEquals("2020-12-08T23:15:30Z", registrationModel.getExpiryDate().toString());

    VehicleModel vehicleModel = registrationPlateModel.getVehicleModel();
    assertNotNull(vehicleModel);
    assertEquals("Sedan", vehicleModel.getType());
    assertEquals("Mercedes", vehicleModel.getMake());
    assertEquals("X4 M40i", vehicleModel.getModel());
    assertEquals("Blue", vehicleModel.getColour());
    assertEquals("87676676762", vehicleModel.getVin());
    assertNull(vehicleModel.getGrossMass());

    InsurerModel insurerModel = registrationPlateModel.getInsurerModel();
    assertNotNull(insurerModel);
    assertEquals("GIO", insurerModel.getName());
    assertEquals(1L, insurerModel.getCode().longValue());
  }
}

