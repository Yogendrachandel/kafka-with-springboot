package com.learn.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.model.User;

@Component
public class KafkaConsumerListener {
	
	//To run this 2 listner simultaneously we need to create two different topic,so that our listner first read msg of String type and 
	//Listner  second json.
	
	//This listener read the String based msgs. we can also add multiple topic by , seprated.
	@KafkaListener(topics = "publish", groupId = "plain-msg-groupId", containerFactory = "kafkaListenerContainerFactory")
	public void consume(String msg) {
		System.out.println("Plain Message consumed on topic: "+msg);
	}
	
	
	//This listener read the Custom msgs like json of User type model msgs.
	@KafkaListener(topics = "publish-json", groupId = "json-msg-groupId", containerFactory = "userJsonkafkaListenerContainerFactory")
	public void consumeJsonMsg(User  user) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(user);
		System.out.println("Json Message consumed on topic: "+jsonString);
	}
	
	

}
