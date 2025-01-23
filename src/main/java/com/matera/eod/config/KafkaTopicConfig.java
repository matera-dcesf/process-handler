package com.matera.eod.config;


import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaAddress;

    @Value("${topic.processing}")
    private String processingTopic;

    @Value("${topic.processing.result}")
    private String processingResultTopic;

    @Value("${topic.activity}")
    private String activityTopic;

    @Value("${topic.activity.result}")
    private String activityResultTopic;

    @Value("${topic.partition:3}")
    private int partitions;

    @Value("${topic.replicas:1}")
    private int replicas;

   @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic processTopicBuilder() {
        return TopicBuilder
                .name(processingTopic)
                .partitions(partitions)
                .replicas(replicas)
                .build();
    }

    @Bean
    public NewTopic activityTopicBuilder() {
        return TopicBuilder
                .name(activityTopic)
                .partitions(partitions)
                .replicas(replicas)
                .build();
    }

    @Bean
    public NewTopic activityResultTopicBuilder() {
        return TopicBuilder
                .name(activityResultTopic)
                .partitions(partitions)
                .replicas(replicas)
                .build();
    }

    @Bean
    public NewTopic processingResultTopicBuilder() {
        return TopicBuilder
                .name(processingResultTopic)
                .partitions(partitions)
                .replicas(replicas)
                .build();
    }
}
