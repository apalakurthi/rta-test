package com.snsw.rta.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.time.OffsetDateTime;
import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
public class RegistrationModel implements Serializable {

  private boolean expired;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "UTC")
  @JsonProperty("expiry_date")
  private OffsetDateTime expiryDate;
}
