package com.snsw.rta.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
public class VehicleModel implements Serializable {

  private String type;

  private String make;

  private String model;

  private String colour;

  private String vin;

  @JsonProperty("tare_weight")
  private int tareWeight;

  @JsonProperty("gross_mass")
  private Long grossMass;
}
