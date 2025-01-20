package com.matera.eod.service;

import com.matera.eod.record.ProcessingRecord;

public interface ProcessingService {

    void handleProcessing(ProcessingRecord processing);
}
