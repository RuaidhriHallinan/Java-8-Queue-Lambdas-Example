package com.aspect.services;

import com.aspect.components.WorkRequestQueue;
import com.aspect.domain.WorkRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Ruaidhri on 04/07/2017.
 */
@Service
public class WorkOrderRequestService {

    @Autowired
    WorkRequestQueue workOrderRequest;

    public boolean enqueue(WorkRequest newWorkRequest) {
        return workOrderRequest.enqueue(newWorkRequest);
    }

    public boolean dequeue(long id) {
        return workOrderRequest.dequeue(id);
    }

    public WorkRequest dequeueTop() {
        return workOrderRequest.dequeueTop();
    }
}