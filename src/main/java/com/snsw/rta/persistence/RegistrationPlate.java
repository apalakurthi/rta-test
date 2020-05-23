package com.snsw.rta.persistence;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "registrationplate")
public class RegistrationPlate implements Serializable {

  @Id
  @GeneratedValue
  private Long id;

  @Column(name = "platenumber")
  private String plateNumber;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name="vehicleid")
  private Vehicle vehicle;

}
