package com.aspect.util;

import com.aspect.domain.WorkRequest;
import com.aspect.domain.WorkRequest.WorkRequestType;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Utility class for any reuseable logic that is needed
 * <p>
 * Created by Ruaidhri on 05/07/2017.
 */
public class Util {

    /**
     * This method calculates the classification or type of the Work Requests
     * in the queue.
     * <p>
     * IDs that are evenly divisible by 3 are priority IDs.
     * IDs that are evenly divisible by 5 are VIP IDs.
     * IDs that are evenly divisible by both 3 and 5 are management override IDs.
     * IDs that are not evenly divisible by 3 or 5 are normal IDs.
     *
     * @param id identifer of teh Work Reqyest
     * @return Work Request enum type NORMAL, PRIORITY, VIP, MANAGEMENT_OVERRIDE
     */
    public static WorkRequestType getWorkRequestType(Long id) {

        if (id % 3 == 0 && id % 5 == 0) {
            return WorkRequestType.MANAGEMENT_OVERRIDE;
        } else if (id % 5 == 0) {
            return WorkRequestType.VIP;
        } else if (id % 3 == 0) {
            return WorkRequestType.PRIORITY;
        }

        return WorkRequestType.NORMAL;
    }


    /**
     * Ranks the Work Request Queue based on the Type of Work Request
     * (1) Normal IDs are given a rank equal to the number of seconds they’ve been in the queue.
     * (2) Priority IDs are given a rank equal to the result of applying the following formula
     * to the number of seconds they’ve been in the queue:
     * max(3, n log n)
     * (3) VIP IDs are given a rank equal to the result of applying the following formula
     * to the number of seconds they’ve been in the queue:
     * max(4, 2n log n)
     * (4) Management Override IDs are always ranked ahead of all other IDs and
     * are ranked among themselves according to the number of secondsthey’ve been in the queue.
     *
     * @param type
     * @param dateAdded
     * @return
     */
    public static Long getRankBySecs(WorkRequestType type, Date dateAdded) {

        Long currentTimeMillis = System.currentTimeMillis();
        Long timeAddedQueueMillis = dateAdded.getTime();
        Long timeInQueue = currentTimeMillis - timeAddedQueueMillis;

        long secsInQueue = new Date(timeInQueue).getTime();

        switch (type) {
            case MANAGEMENT_OVERRIDE:
                //System.out.println("MANAGEMENT_OVERRIDE rank: " + secondsInQueue);
                return 1l;

            case NORMAL:
                //System.out.println("NORMAL rank: " + secondsInQueue);
                return 0l;

            case PRIORITY:
                //System.out.println("PRIORITY rank: " + (long) Math.max(3, secondsInQueue * Math.log(secondsInQueue)));
                //max(3, n log n) where n is number of seconds in queue?
                return (long) Math.max(3, secsInQueue * Math.log(secsInQueue));

            case VIP:
                //System.out.println("VIP rank: " + (long) Math.max(4, 2 * secondsInQueue * Math.log(secondsInQueue)));
                //max(4, 2n log n) where n is number of seconds in queue?
                return (long) Math.max(4, 2 * secsInQueue * Math.log(secsInQueue));
        }
        return null;
    }

    /**
     * Calculates the average (mean) wait time for Work Requests in the Queue
     *
     * @param currentTime      date passed into the request
     * @param workRequestQueue work request queue to be used to calculate mean time of wait
     * @return average (mean) wait time of items in the queue
     */
    public static Long getWaitTime(Date currentTime, PriorityQueue<WorkRequest> workRequestQueue) {

        //Java 8 way to convert Priority Queue to a List
        List<WorkRequest> wrList = workRequestQueue.stream().collect(Collectors.toList());
        long totMillis = 0;
        int counter = 0;
        for (WorkRequest wr : wrList) {
            if (currentTime.getTime() > wr.getDateAdded().getTime()) {
                long differMilliSecs = (currentTime.getTime() - wr.getDateAdded().getTime());
                totMillis = totMillis + differMilliSecs;
                counter++;
            }
        }

        long secs = new Date(totMillis / counter).getTime() / 1000;

        return secs;
    }

    /**
     * Retrieves the work request ids in order
     *
     * @param workRequestQueue
     * @return list if ids in working order
     */
    public static List<Long> getWorkOrderIds(PriorityQueue<WorkRequest> workRequestQueue) {

        List<WorkRequest> wrs = getWorkingOrder(workRequestQueue);

        List<Long> ids = wrs.stream()
                .map(WorkRequest::getId).collect(Collectors.toList());

        return ids;
    }

    /**
     * Retrieves the position of the work request based on its ID
     * Filters and sorts the queue
     *
     * @param id
     * @param workRequestQueue
     * @return the index of the work request
     */
    public static Integer getWorkRequestPosition(Long id, PriorityQueue<WorkRequest> workRequestQueue) {

        List<WorkRequest> listForReturning = getWorkingOrder(workRequestQueue);

        for (int i = 0; i < listForReturning.size(); i++) {
            if (id.equals(listForReturning.get(i))) {
                return i;
            }
        }

        return null;
    }

    public static List<WorkRequest> getWorkingOrder(PriorityQueue<WorkRequest> workRequestQueue) {
        Comparator<WorkRequest> byDate = (WorkRequest w1, WorkRequest w2) -> w1.getDateAdded().compareTo(w2.getDateAdded());
        Comparator<WorkRequest> byRank = (WorkRequest w1, WorkRequest w2) ->
                Util.getRankBySecs(w1.getWorkRequestType(), w1.getDateAdded())
                        .compareTo(Util.getRankBySecs(w2.getWorkRequestType(), w2.getDateAdded()));

        List<WorkRequest> listForReturning = new ArrayList<>();

        Predicate<WorkRequest> management = (wr) -> wr.getWorkRequestType() == WorkRequestType.MANAGEMENT_OVERRIDE;
        List<WorkRequest> wrList = workRequestQueue.stream().filter(management).sorted(byDate).collect(Collectors.toList());
        wrList.forEach(e -> System.out.println(e));
        wrList.forEach(e -> listForReturning.add(e));

        Predicate<WorkRequest> vip = (wr) -> wr.getWorkRequestType() == WorkRequestType.VIP;
        wrList = workRequestQueue.stream().filter(vip).sorted(byRank).collect(Collectors.toList());
        wrList.forEach(e -> System.out.println(e));
        wrList.forEach(e -> listForReturning.add(e));

        Predicate<WorkRequest> priority = (ca) -> ca.getWorkRequestType() == WorkRequestType.PRIORITY;
        wrList = workRequestQueue.stream().filter(priority).sorted(byRank).collect(Collectors.toList());
        wrList.forEach(e -> System.out.println(e));
        wrList.forEach(e -> listForReturning.add(e));

        Predicate<WorkRequest> normal = (wr) -> wr.getWorkRequestType() == WorkRequestType.NORMAL;
        wrList = workRequestQueue.stream().filter(normal).sorted(byDate).collect(Collectors.toList());
        wrList.forEach(e -> System.out.println(e));
        wrList.forEach(e -> listForReturning.add(e));

        return wrList;

    }

    public static WorkRequest findTopWorkRequest(PriorityQueue<WorkRequest> workRequestQueue) {

        long topId = getWorkOrderIds(workRequestQueue).get(0);

        WorkRequest wr = workRequestQueue.stream().filter((w) -> w.getId().equals(topId)).findFirst().get();

        return wr;
    }

}
