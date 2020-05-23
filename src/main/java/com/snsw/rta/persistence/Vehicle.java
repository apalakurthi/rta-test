package com.snsw.rta.persistence;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "vehicle")
public class Vehicle implements Serializable {

  @Id
  @GeneratedValue
  @Column(name = "id")
  private Long id;

  @Column(name = "type")
  private String type;

  @Column(name = "make")
  private String make;

  @Column(name = "model")
  private String model;

  @Column(name = "colour")
  private String colour;

  @Column(name = "vin")
  private String vin;

  @Column(name = "tareweight")
  private int tareWeight;

  @Column(name = "grossmass")
  private Long grossMass;

  @Column(name = "registrationexpirydate")
  private LocalDateTime registrationExpiryDate;

  @Column(name = "insurerid")
  private Long insurerId;

}
