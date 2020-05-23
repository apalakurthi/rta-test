package com.snsw.rta.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;


@Builder(toBuilder = true)
@Data
public class RegistrationPlateRequest implements Serializable {

  private String plateNumber;

  private String type;

  private String make;

  private String model;

  private String colour;

  private String vin;

  private int tareWeight;

  private Long grossMass;

  private String insurerName;

  //@DateTimeFormat(style = "yyyy-MM-dd'T'HH:mm:ss.SSS")
  private LocalDateTime registrationExpiry;

}
