package com.learn.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.learn.model.User;

@Configuration
public class KafkaConfiguration {
	
	@Bean
	public ProducerFactory<String, User> producerFactory() {
		Map<String,Object>config= new HashMap<>();
		
		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");//server nad port
		config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);//for key
		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);// for value 
		
		return new DefaultKafkaProducerFactory<>(config);
		
	}
	
	
	@Bean("myTemplate")
	public KafkaTemplate<String, User> createkafkaTemplate(){
		return new KafkaTemplate<>(producerFactory());
	}
	
	
	
	

}
