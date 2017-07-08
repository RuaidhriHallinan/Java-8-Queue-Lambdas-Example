package com.apt.components;

import com.apt.domain.WorkRequest;
import com.apt.util.Util;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by Ruaidhri on 04/07/2017.
 */
@Component
public class WorkRequestQueue {

    public PriorityQueue<WorkRequest> workRequestQueue;

    /**
     * Checks that the id of the WorkRequest is not in the queue
     * Adds a WorkRequest to the PriorityQueue
     *
     * @param workRequest work request object
     * @return true or false depending on successfully adding
     */
    public synchronized boolean enqueue(WorkRequest workRequest) {

        if (workRequest != null) {

            if (workRequestQueue != null) {

                for (WorkRequest wr : workRequestQueue) {
                    if (workRequest.getId().equals(wr.getId())) {
                        return false;
                    }
                }
            } else {
                workRequestQueue = new PriorityQueue<>();
            }

        } else {
            return false;
        }
        workRequestQueue.add(workRequest);

        return true;

    }

    /**
     * Finds WorkRequest by id and removes it from the PriorityQueue
     *
     * @param id identifer of thw work request
     * @return true or false depending on success of dequeuing
     */
    public synchronized boolean dequeue(long id) {

        if (workRequestQueue != null) {
            for (WorkRequest wr : workRequestQueue) {
                if (wr.getId().equals(id)) {
                    workRequestQueue.remove(wr);
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Removes the first or top WorkRequest from the PriorityQueue
     *
     * @return true or false depending on successfully removing top work request from queue
     */
    public long dequeueTop() {
        WorkRequest wr = Util.findTopWorkRequest(workRequestQueue);
        workRequestQueue.remove(wr);
        return wr.getId();
    }

    /**
     * Retrives a list of sorted WorkRequest Ids from the PriorityQueue
     *
     * @return list of ordered/sorted ID's of work requests
     */
    public List<Long> getWorkOrderIDs() {
        return Util.getWorkOrderIds(workRequestQueue);
    }

    /**
     * Retrieves the position or index of the item in the WorkRequest
     *
     * @param id of the work request
     * @return position (index) of the work request in the queue
     */
    public Integer getPosition(Long id) {
        return Util.getWorkRequestPosition(id, workRequestQueue);
    }

    /**
     * @param currentTime date passed into calculate mean before date
     * @return average (mean) wait time of items in the queue
     */
    public Long getWaitTime(Date currentTime) {
        return Util.getWaitTime(currentTime, workRequestQueue);
    }
}
