package com.aspect.services;

import com.aspect.components.WorkRequestQueue;
import com.aspect.domain.WorkRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * WorkOrderRequestService used to perform operations on the Priority Queue
 *
 * Created by Ruaidhri on 04/07/2017.
 */
@Service
public class WorkOrderRequestService {

    /**
     * Work request queue
     */
    @Autowired
    WorkRequestQueue workOrderRequest;

    /**
     * Enqueue a WorkRequest by id to the WorkRequestQueue
     *
     * @param newWorkRequest
     * @return boolean
     */
    public boolean enqueue(WorkRequest newWorkRequest) {
        return workOrderRequest.enqueue(newWorkRequest);
    }

    /**
     * Dequeue a WorkRequest by id from the WorkRequestQueue
     *
     * @param id
     * @return
     */
    public boolean dequeue(long id) {
        return workOrderRequest.dequeue(id);
    }

    /**
     * Dequeue WorkRequest in top/first position
     *
     * @return boolean true or false depending on successful dequeuing
     */
    public Long dequeueTop() {
        return workOrderRequest.dequeueTop();
    }

    /**
     * Returns sorted list of Work Ids
     *
     * @return sorted list of IDs of work requests
     */
    public List<Integer> getWorkOrderIDs() {
        return workOrderRequest.getWorkOrderIDs();
    }

    /**
     * Returns the position of the Work Request in the queue
     *
     * @param id work request identifer
     * @return teh position (index) of an id in the queue
     */
    public int getPosition(Long id) {
        return workOrderRequest.getPosition(id);
    }

    /**
     * Calculates the average (mean) wait time of objects in teh queue
     *
     * @param currentDate
     * @return average wait time in Date object
     */
    public Date getWaitTime(Date currentDate) {
        return workOrderRequest.getWaitTime(currentDate);
    }
}