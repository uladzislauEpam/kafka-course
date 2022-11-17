package com.kafka.course.k2.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class Reposition {

  String vehicleId;

  double travelledX;

  double travelledY;

}
