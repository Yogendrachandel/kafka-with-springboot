package com.learn.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;

import com.learn.model.Result;
import com.learn.model.Student;

@Configuration
public class KafkaConfig {
	
	@Value("${kafka.group.id}")
	private String groupId;

	@Value("${kafka.reply.topic}")
	private String replyTopic;
	
	/*
		Spring provides a ReplyingKafkaTemplate <K, V, R>, which offers a return object or a reply object once the message is consumed by the Kafka listener from another side. 
		The type parameters represent: K – Key type, V – Outbound data type, and R – Reply data type.
		In our example, we have defined the bean as ReplyingKafkaTemplate<String, Student, Result>, where we will send the Student details to Kafka and then get the Result object as reply data type. 
		We are going to create the Student and the Result DTO classes in the next section.
	*/
	  @Bean
	  public ReplyingKafkaTemplate<String, Student,Result>	  replyingKafkaTemplate(ProducerFactory<String, Student> pf,
	  ConcurrentKafkaListenerContainerFactory<String, Result> factory)
	{
	  
	  ConcurrentMessageListenerContainer<String, Result> replyContainer = factory.createContainer(replyTopic);
	  replyContainer.getContainerProperties().setMissingTopicsFatal(false);
	  replyContainer.getContainerProperties().setGroupId(groupId); 
	  return new  ReplyingKafkaTemplate<>(pf, replyContainer); 
	  
	}
	 

	
	//Creating Kafka Template
	/*
		We then need to define a KafkaTemplate bean, which we will use as the reply template with producer 
		factory having data type: ProducerFactory<String, Result>.
		Also, this template is set as the reply template of the ConcurrentKafkaListenerContainerFactory(which is the expecting Result as a return type from the listener).
	*/
	  @Bean
	  public KafkaTemplate<String, Result> replyTemplate(ProducerFactory<String, Result> pf,
			  ConcurrentKafkaListenerContainerFactory<String, Result> factory) 
		{
		  System.out.println("AAAA - "+pf.getClass().getName());
		  KafkaTemplate<String, Result> kafkaTemplate = new KafkaTemplate<>(pf);
		
		  factory.getContainerProperties().setMissingTopicsFatal(false);
		  factory.setReplyTemplate(kafkaTemplate);
		  return kafkaTemplate; 
	  }
	 
	
	
	
}
