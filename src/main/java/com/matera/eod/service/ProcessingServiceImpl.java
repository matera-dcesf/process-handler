package com.matera.eod.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.matera.eod.producer.ActivityProducer;
import com.matera.eod.record.ActivityRecord;
import com.matera.eod.record.ProcessingRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProcessingServiceImpl implements ProcessingService {

    @Autowired
    private ActivityProducer activityProducer;

    @Override
    public void handleProcessing(ProcessingRecord processing) {
        List<ActivityRecord> activities = buildActivities(processing);
        activities.stream().forEach( a -> {
            try {
                activityProducer.sendActivity(a);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }
    private List<ActivityRecord> buildActivities(ProcessingRecord processing){
        List<ActivityRecord> activities = new ArrayList<>();
        for(int i=0; i< processing.numberOfActivities(); i++){
            activities.add(new ActivityRecord(processing.id(), processing.processName(), "Activity "+i,
                    processing.numberOfActivities()));
        }
        return activities;
    }
}
