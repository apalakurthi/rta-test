package com.snsw.rta.model;

import java.io.Serializable;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class RegistrationsModel implements Serializable {

  private List<RegistrationPlateModel> registrations;

}
