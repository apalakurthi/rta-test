package com.snsw.rta.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;

@JsonPropertyOrder({"code", "name"})
@Builder(toBuilder = true)
@Getter
public class InsurerModel implements Serializable {

  private Long code;

  private String name;
}