package com.matera.eod.config;

import com.matera.eod.record.ProcessingRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Autowired
    private KafkaProperties kafkaProperties;

    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaAddress;

    @Value("${topic.processing.group.id.config}")
    private String processingGroupId;

   public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> properties = kafkaProperties.buildConsumerProperties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,  kafkaAddress);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, processingGroupId);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
       properties.put(ConsumerConfig.ALLOW_AUTO_CREATE_TOPICS_CONFIG, false);
        return new DefaultKafkaConsumerFactory<>(properties);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}