package com.matera.eod.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.matera.eod.record.ProcessingRecord;
import com.matera.eod.service.ProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
public class ProcessingConsumer {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProcessingService service;

    @KafkaListener(topics = "${topic.processing}", groupId = "${topic.processing.group.id.config}",
            containerFactory = "kafkaListenerContainerFactory")
    public void processingListener(String  processingContent) throws JsonProcessingException {
        ProcessingRecord processingRecord;
        processingRecord = objectMapper.readValue(processingContent, ProcessingRecord.class);
        String msg = MessageFormat.format("Received Processing information: processing id: {0} - process name: {1}",
                String.valueOf(processingRecord.id()), processingRecord.processName());
        System.out.println(msg);

        service.handleProcessing(processingRecord);
    }

    @KafkaListener(topics = "${topic.processing.result}", groupId = "${topic.processing.result.group.id.config}",
            containerFactory = "kafkaListenerContainerFactory")
    public void processingResultListener(String msg) throws JsonProcessingException {
        System.out.println(msg);
    }
}
