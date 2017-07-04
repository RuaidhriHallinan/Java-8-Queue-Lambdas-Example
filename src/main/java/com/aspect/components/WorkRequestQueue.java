package com.aspect.components;

import com.aspect.domain.WorkRequest;
import org.springframework.stereotype.Component;

import java.util.PriorityQueue;

/**
 * Created by Ruaidhri on 04/07/2017.
 */
@Component
public class WorkRequestQueue {

    public PriorityQueue<WorkRequest> workRequestQueue;

    /**
     * Adds a work request to the queue
     * Checks that the id of the request is not in the queue
     *
     * @param workRequest
     * @return true or false depending on successfully adding
     */
    public synchronized boolean enqueue(WorkRequest workRequest) {

        if (workRequest != null) {

            if (workRequestQueue != null) {
                for (WorkRequest wr : workRequestQueue) {
                    if (workRequest.getId() == wr.getId()) {
                        return false;
                    }
                }
            } else {
                workRequestQueue = new PriorityQueue<>();
            }

        } else {
            return false;
        }

        //TODO use a Lambda to sort the Array?
        /*
            stream.forEach ?
         */

        return workRequestQueue.add(workRequest);

    }

    public synchronized boolean dequeue(long id) {

        if (workRequestQueue != null) {
            for (WorkRequest wr : workRequestQueue) {
                if (wr.getId() == id) {
                    return workRequestQueue.remove(wr);
                }
            }
        }
        return false;
    }


    public WorkRequest dequeueTop() {

        //TODO use a Lambda to sort the Array
        //Remove the top
        //see notes

        return null;
    }
}
