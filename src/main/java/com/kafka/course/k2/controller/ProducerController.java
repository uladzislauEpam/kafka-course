package com.kafka.course.k2.controller;

import com.kafka.course.k2.model.Signal;
import com.kafka.course.k2.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {

  @Autowired
  private ProducerService producerService;

  @PostMapping("/signal")
  public String getVehicleSignal(@RequestBody Signal signal) {
    producerService.validateAndSend(signal);
    return "Success";
  }

}
