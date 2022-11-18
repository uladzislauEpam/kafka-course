package com.kafka.course.k1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//Manual test controller (Self-initiated)
@RestController
public class ProducerController {

    @Autowired
    private Producer producer;

    @PostMapping("/send")
    public void messageToTopic(@RequestParam("message") String message){
        producer.send("1", message);
    }
}
