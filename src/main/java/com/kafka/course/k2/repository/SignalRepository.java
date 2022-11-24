package com.kafka.course.k2.repository;

import com.kafka.course.k2.model.Signal;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SignalRepository extends JpaRepository<Signal, Long> {

  Signal findByVehicleId(String vehicleId);

}
