package com.learn.listener;


import java.util.concurrent.ThreadLocalRandom;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import com.learn.model.Result;
import com.learn.model.Student;


@Component
public class StudentResultCalculator {
	
	//The @KafkaListener is then used to subscribe to the request Kafka topic.
	// the @SendTo annotation enables the listener method the capability to send a response back to another reply topic.
    @KafkaListener(topics = "${kafka.request.topic}", groupId = "${kafka.group.id}")
    @SendTo 
    public Result handle(Student student) {
    	
        System.out.println("Calculating Result...");
        
        double total = ThreadLocalRandom.current().nextDouble(2.5, 9.9);
        Result result = new Result();
        result.setName(student.getName());
        result.setResult((total > 3.5) ? "Pass" : "Fail");
        result.setPercentage(String.valueOf(total * 10).substring(0, 4) + "%");
        return result;
    }
}