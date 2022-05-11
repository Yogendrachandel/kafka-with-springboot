package com.learn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learn.model.User;

//Note in this project we are not giving any configuration details because the kafka on the same machine.
//and we are sending the data as String also.

@RestController
@RequestMapping("kafka")
public class PublishMessageonKafkaContoller {
	
	
	private static final String TOPIC="publish";
	
	@Autowired
	private KafkaTemplate<String, User>template; //same like as resttemplate
	
	
	@GetMapping("/publish/{id}/{name}/{salary}")
	public String publishMsgOnKafkaTopic(@PathVariable int id,@PathVariable String name,@PathVariable double salary) {
		template.send(TOPIC, new User(id, name, salary));
		
		return "msg published on kafka";
		
	}

}
