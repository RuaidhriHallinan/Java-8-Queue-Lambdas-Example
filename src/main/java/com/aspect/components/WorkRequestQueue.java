package com.aspect.components;

import com.aspect.domain.WorkRequest;
import com.aspect.util.Util;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;
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
        workRequestQueue.add(workRequest);

        Util.sortPriorityQueue(workRequestQueue);

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
                if (wr.getId() == id) {
                    workRequestQueue.remove(wr);
                    Util.sortPriorityQueue(workRequestQueue);
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

        PriorityQueue<WorkRequest> newWorkRequestQueue = new PriorityQueue<WorkRequest>();
        workRequestQueue.stream().sorted((w1, w2) -> w1.getDateAdded().compareTo(w2.getDateAdded())).forEach(e -> newWorkRequestQueue.add(e));

        //Optional returns an empty object if it is not found
        Optional<WorkRequest> wr = newWorkRequestQueue.stream().findFirst();

        if (wr != null) {
            workRequestQueue.remove(wr);
        }

        //This method retrieves and removes the head of this queue,
        // or returns null if this queue is empty.
        //WorkRequest wr = workRequestQueue.poll();

        //TODO is this sort needed, should be sorted? Do you sort after the poll/delete?
        Util.sortPriorityQueue(workRequestQueue);

        return wr.get().getId();
    }

    /**
     * Retrives a list of sorted WorkRequest Ids from the PriorityQueue
     *
     * @return list of ordered/sorted ID's of work requests
     */
    public List<Integer> getWorkOrderIDs() {

        // TODO use Lambdas here
        // Predicates for the conditions, Stream, and forEach? etc eg

        // Full filter predicate
        //List<WorkRequest> requests = workRequests.stream().filter(fullFilterPredicate).collect(Collectors.toList());
        //see notes

        return null;
    }

    /**
     * Retrieves the position or index of the item in the work request
     *
     * @param id of the work request
     * @return position (index) of the work request in the queue
     */
    public Integer getPosition(Long id) {
        //TODO use Lambdas here
        return null;
    }

    /**
     * @param currentTime date passed into the request
     * @return average (mean) wait time of items in the queue
     */
    public Date getWaitTime(Date currentTime) {

        //Stream.forEach
        //if < beforeDate
        //get total of items in millis
        //get mean of items in millis
        //convert to time in Days, Hours, Mins, Secs

        return null;
    }
}
