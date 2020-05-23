package com.snsw.rta.controller;

import com.snsw.rta.model.RegistrationPlateModel;
import com.snsw.rta.model.RegistrationPlateRequest;
import com.snsw.rta.model.RegistrationsModel;
import com.snsw.rta.persistence.RegistrationPlate;
import com.snsw.rta.service.RegistrationService;
import javax.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

  private final RegistrationService registrationService;

  public RegistrationController(RegistrationService registrationService) {
    this.registrationService = registrationService;
  }

  @PostMapping(value = "/registrationplate", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<RegistrationPlateModel> createRegistrationPlate(
      @RequestBody RegistrationPlateRequest request) {
    RegistrationPlate registrationPlate = registrationService.createRegistrationPlate(request);
    return ResponseEntity.ok(
        registrationService.convertRegistrationPlateEntityToModel(registrationPlate));
  }

  @GetMapping(value = "/registrationplate", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<RegistrationsModel> getRegistrations() {
    RegistrationsModel registrationsModel = registrationService.getRegistrations();
    return ResponseEntity.ok(registrationsModel);
  }
}
