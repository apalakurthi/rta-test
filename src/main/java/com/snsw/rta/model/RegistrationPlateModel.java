package com.snsw.rta.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;

@JsonPropertyOrder({ "plate_number", "registration", "vehicle", "insurer"})
@Builder(toBuilder = true)
@Getter
public class RegistrationPlateModel implements Serializable {

  @JsonProperty("plate_number")
  private String plateNumber;

  @JsonProperty("registration")
  private RegistrationModel registrationModel;

  @JsonProperty("vehicle")
  private VehicleModel vehicleModel;

  @JsonProperty("insurer")
  private InsurerModel insurerModel;

}
