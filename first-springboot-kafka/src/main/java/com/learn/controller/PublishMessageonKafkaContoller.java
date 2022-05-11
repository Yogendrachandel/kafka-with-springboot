package com.learn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Note in this project we are not giving any configuration details because the kafka on the same machine.
//and we are sending the data as String also.

@RestController
@RequestMapping("kafka")
public class PublishMessageonKafkaContoller {
	
	
	private static final String TOPIC="publish";
	
	@Autowired
	private KafkaTemplate<String, String>template; //same like as resttemplate
	
	
	@GetMapping("/publish/{msg}")
	public String publishMsgOnKafkaTopic(@PathVariable String msg) {
		template.send(TOPIC, msg);
		
		return "msg published on kafka";
		
	}

}
