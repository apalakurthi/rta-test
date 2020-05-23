package com.snsw.rta.service;

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
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.util.StringUtils;

public class RegistrationService {

  private final InsurerRepository insurerRepository;

  private final RegistrationPlateRepository registrationPlateRepository;

  public RegistrationService(InsurerRepository insurerRepository,
      RegistrationPlateRepository registrationPlateRepository) {
    this.insurerRepository = insurerRepository;
    this.registrationPlateRepository = registrationPlateRepository;
  }

  public RegistrationPlate createRegistrationPlate(RegistrationPlateRequest request) {

    Long insurerId = getInsurerId(request.getInsurerName());

    Vehicle vehicle = Vehicle.builder()
        .type(request.getType())
        .make(request.getMake())
        .model(request.getModel())
        .colour(request.getColour())
        .vin(request.getVin())
        .tareWeight(request.getTareWeight())
        .grossMass(request.getGrossMass())
        .insurerId(insurerId)
        .registrationExpiryDate(request.getRegistrationExpiry())
        .build();
    RegistrationPlate registrationPlate = RegistrationPlate.builder()
        .plateNumber(request.getPlateNumber())
        .vehicle(vehicle)
        .build();

    return registrationPlateRepository.save(registrationPlate);
  }

  private Long getInsurerId(String name) {
    Long insurerId = null;

    if (StringUtils.hasText(name)) {
      Insurer insurer = insurerRepository.findByName(name);
      if (insurer == null) {
        insurer = Insurer.builder().name(name).build();
        insurer = insurerRepository.saveAndFlush(insurer);
      }
      insurerId = insurer.getCode();
    }
    return insurerId;
  }

  public RegistrationsModel getRegistrations() {
    List<RegistrationPlate> registrationPlates = registrationPlateRepository.findAll();

    List<RegistrationPlateModel> registrationPlateModels = new ArrayList<>();
    for(RegistrationPlate registrationPlate : registrationPlates) {
      registrationPlateModels.add(convertRegistrationPlateEntityToModel(registrationPlate));
    }
    return RegistrationsModel.builder()
        .registrations(registrationPlateModels)
        .build();

  }

  public RegistrationPlateModel convertRegistrationPlateEntityToModel(
      RegistrationPlate registrationPlate) {
    InsurerModel insurerModel = InsurerModel.builder().build();
    Optional<Insurer> insurer = insurerRepository.findById(registrationPlate.getVehicle().getInsurerId());
    if (insurer.isPresent()) {
      insurerModel = convertInsurerEntityToModel(insurer.get());
    }

    RegistrationModel registrationModel = convertRegistrationEntityToModel(
        registrationPlate.getVehicle());
    VehicleModel vehicleModel = convertVehicleEntityToVehicleModel(
        registrationPlate.getVehicle());

    return RegistrationPlateModel.builder()
        .plateNumber(registrationPlate.getPlateNumber())
        .insurerModel(insurerModel)
        .registrationModel(registrationModel)
        .vehicleModel(vehicleModel)
        .build();
  }

  private InsurerModel convertInsurerEntityToModel(Insurer insurer) {
    return InsurerModel.builder()
        .code(insurer.getCode())
        .name(insurer.getName())
        .build();
  }

  private RegistrationModel convertRegistrationEntityToModel(Vehicle vehicle) {
    boolean isRegistrationExpired = vehicle.getRegistrationExpiryDate()
        .isBefore(LocalDateTime.now());

    return RegistrationModel.builder()
        .expired(isRegistrationExpired)
        .expiryDate(OffsetDateTime.of(vehicle.getRegistrationExpiryDate(), ZoneOffset.UTC))
        .build();
  }

  private VehicleModel convertVehicleEntityToVehicleModel(Vehicle vehicle) {
    return VehicleModel.builder()
        .type(vehicle.getType())
        .make(vehicle.getMake())
        .model(vehicle.getModel())
        .colour(vehicle.getColour())
        .vin(vehicle.getVin())
        .tareWeight(vehicle.getTareWeight())
        .grossMass(vehicle.getGrossMass())
        .build();
  }

}
