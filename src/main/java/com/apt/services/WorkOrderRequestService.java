package com.apt.services;

import com.apt.components.WorkRequestQueue;
import com.apt.domain.WorkRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * WorkOrderRequestService used to perform operations on the Priority Queue
 * <p>
 * Created by Ruaidhri on 04/07/2017.
 */
@Service
public class WorkOrderRequestService {

    /**
     * Work request queue
     */
    @Autowired
    private WorkRequestQueue workOrderRequest;

    /**
     * Enqueue a WorkRequest by id to the WorkRequestQueue
     *
     * @param newWorkRequest new WorkRequest for adding to queue
     * @return boolean true/false depending on success
     */
    public boolean enqueue(WorkRequest newWorkRequest) {
        return workOrderRequest.enqueue(newWorkRequest);
    }

    /**
     * Dequeue a WorkRequest by id from the WorkRequestQueue
     *
     * @param id id wof WorkRequest
     * @return true/false depending on success
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
    public List<Long> getWorkOrderIDs() {
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
     * Calculates the average (mean) wait time of objects in seconds
     *
     * @param currentDate current date to compare against other dates in queue
     * @return average wait time in Date object
     */
    public Long getWaitTime(Date currentDate) {
        return workOrderRequest.getWaitTime(currentDate);
    }
}