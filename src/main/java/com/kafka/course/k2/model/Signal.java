package com.kafka.course.k2.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "signal")
public class Signal {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  int id;

  @Column(name = "vehicleId")
  String vehicleId;

  @Column(name = "x")
  double x;

  @Column(name = "y")
  double y;

}
