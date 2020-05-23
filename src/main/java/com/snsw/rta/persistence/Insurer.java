package com.snsw.rta.persistence;

import java.io.Serializable;
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
@Entity(name = "insurer")
public class Insurer implements Serializable {

  @Id
  @GeneratedValue
  @Column(name = "code")
  private Long code;

  @Column(name = "name")
  private String name;
}
