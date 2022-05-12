package com.learn.contoller;


import java.util.concurrent.ExecutionException;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.learn.model.Result;
import com.learn.model.Student;


@RestController
public class KafkaController {
	
	
    @Value("${kafka.request.topic}")
    private String requestTopic;
    
    
    @Autowired
    private ReplyingKafkaTemplate<String, Student, Result> replyingKafkaTemplate;
    
    
    /**
     * Creates a Producer record to be sent to a specified topic and partition
     *
     * @param topic The topic the record will be appended to
     * @param partition The partition to which the record should be sent
     * @param key The key that will be included in the record
     * @param value The record contents
     */
    
    @PostMapping("/get-result")
    public ResponseEntity<Result> getObject(@RequestBody Student student)throws InterruptedException, ExecutionException 
    {
        ProducerRecord<String, Student> record = new ProducerRecord<>(requestTopic, null, student.getRegistrationNumber(), student);
        
        // replyingKafkaTemplate configured in config class with replyTopic(i.e result)
        RequestReplyFuture<String, Student, Result> future = replyingKafkaTemplate.sendAndReceive(record);//Send a request and receive a reply with the default timeout.
        ConsumerRecord<String, Result> response = future.get();
        return new ResponseEntity<>(response.value(), HttpStatus.OK);
    }
}