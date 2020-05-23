package com.snsw.rta;

import com.snsw.rta.persistence.repository.InsurerRepository;
import com.snsw.rta.persistence.repository.RegistrationPlateRepository;
import com.snsw.rta.service.RegistrationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RtaApplication {

  private final RegistrationPlateRepository registrationPlateRepository;

  private final InsurerRepository insurerRepository;

  public RtaApplication(
      RegistrationPlateRepository registrationPlateRepository,
      InsurerRepository insurerRepository) {
    this.registrationPlateRepository = registrationPlateRepository;
    this.insurerRepository = insurerRepository;
  }

  @Bean
  public RegistrationService registrationService() {
    return new RegistrationService(insurerRepository, registrationPlateRepository);
  }

  public static void main(String[] args) {
    SpringApplication.run(RtaApplication.class, args);
  }

}
