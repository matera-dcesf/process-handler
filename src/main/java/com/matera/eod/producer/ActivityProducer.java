package com.matera.eod.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.matera.eod.record.ActivityRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Random;

@Component
public class ActivityProducer {

    @Value("${topic.activity}")
    private String activityTopic;

    @Value("${topic.partition:3}")
    private int partitions;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendActivity(ActivityRecord activity) throws JsonProcessingException {
        String data = objectMapper.writeValueAsString(activity);        ;
        System.out.println(MessageFormat.format("Sending activity {0} from process {1} to topic {2}",
                activity.activityName(), activity.processName(), activityTopic));
        kafkaTemplate.send(activityTopic,  new Random().nextInt(partitions),null, data);
    }

}
