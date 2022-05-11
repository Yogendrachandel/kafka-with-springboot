package com.learn.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.learn.model.User;

@EnableKafka// only in case of KafkaConsumer not for Producer
@Configuration //here  we had created to configuration depend on the Msg type to read.
public class KafkaConsumerConfiguration {
	
	
	//This configuration is used to read the String type messages from topic.
	@Bean
	public ConsumerFactory<String, String> consumerFactory(){
		Map<String, Object> config = new HashMap<>();
		
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "plain-msg-groupId");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(config);
		
	}
	
	// Creating a Listener to read String type msgs from topic.
	@Bean
    public ConcurrentKafkaListenerContainerFactory<String, String>  kafkaListenerContainerFactory()
    {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
	
	
	
	//This configuration is used to read the custom messages like JSon of User type messages from topic.
		@Bean
		public ConsumerFactory<String, User> userCustomConsumerFactory(){
			Map<String, Object> config = new HashMap<>();
			
			config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
	        config.put(ConsumerConfig.GROUP_ID_CONFIG, "json-msg-groupId");
	        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
	        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

	       return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), new JsonDeserializer(User.class));
		}
		
		
		
		// Creating a Listener to read Custom msg type like json of User model from topic.
		@Bean
		 public ConcurrentKafkaListenerContainerFactory<String, User>  userJsonkafkaListenerContainerFactory()
	    {
	        ConcurrentKafkaListenerContainerFactory<String, User> factory = new ConcurrentKafkaListenerContainerFactory<>();
	        factory.setConsumerFactory(userCustomConsumerFactory());
	        return factory;
	    }
		

}
