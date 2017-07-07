package com.aspect;

import com.aspect.domain.WorkRequest;
import com.aspect.domain.WorkRequest.WorkRequestType;
import com.aspect.util.Util;
import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;
import java.util.Date;
import java.util.Optional;
import java.util.PriorityQueue;

import static org.junit.Assert.assertEquals;

/**
 * Created by Ruaidhri on 06/07/2017.
 */
public class WorkRequestTests {

    static PriorityQueue<WorkRequest> workRequests;

    @Before
    public void setup() throws InterruptedException {

        //Populating priority queue
        System.out.println("Setup Populating priority queue");
        workRequests = populateQueue();
    }

    //https://www.youtube.com/watch?v=MROCaYEmb6Y
    @Test
    public void java8_lambda_sort_date() throws InterruptedException {

        //Printing out values (unSorted)
        System.out.println("Printing out values (unSorted)");

        workRequests.stream().forEach(System.out::println);

        //Creating new queue for sorted elements
        PriorityQueue<WorkRequest> newWorkRequestQueue = new PriorityQueue<WorkRequest>();

        //Sorting by Date and Adding to new Queue
        System.out.println("Lambda sorting by Date and Adding to new Queue");

        //Comparator<WorkRequest> byId = (WorkRequest w1, WorkRequest w2) -> w1.getDateAdded().compareTo(w2.getDateAdded());
        workRequests.stream().sorted((w1, w2) -> w1.getDateAdded().compareTo(w2.getDateAdded())).forEach(e -> newWorkRequestQueue.add(e));

        //Printing out sorted by date values
        System.out.println("Printing out sorted values by date");

        newWorkRequestQueue.stream().forEach(System.out::println);

        assertEquals(newWorkRequestQueue.size(), 14);
    }

    //https://www.youtube.com/watch?v=MROCaYEmb6Y
    @Test
    public void java8_lambda_sort_date_find_first() throws InterruptedException {

        //Printing out values (unSorted)
        System.out.println("Printing out values (unSorted)");
        workRequests.stream().forEach(System.out::println);

        //Creating new queue for sorted elements

        PriorityQueue<WorkRequest> newWorkRequestQueue = new PriorityQueue<WorkRequest>();
        workRequests.stream().sorted((w1, w2) -> w1.getDateAdded().compareTo(w2.getDateAdded())).forEach(e -> newWorkRequestQueue.add(e));

        //Optional returns an empty object if it is not found
        Optional<WorkRequest> wr = newWorkRequestQueue.stream().findFirst();

        //Printing out values (unSorted)
        System.out.println("Printing out sorted values");
        newWorkRequestQueue.stream().forEach(System.out::println);
        System.out.println("First id: " + wr.get().getId());

        assertEquals(newWorkRequestQueue.size(), 14);
    }


    //https://www.youtube.com/watch?v=MROCaYEmb6Y
    @Test
    public void java8_lambda_sort_two_comparators_type_and_id() throws InterruptedException {

        Comparator<WorkRequest> byId = (WorkRequest w1, WorkRequest w2) -> w1.getId().compareTo(w2.getId());
        Comparator<WorkRequest> byType = (WorkRequest w1, WorkRequest w2) -> w1.getWorkRequestType().compareTo(w2.getWorkRequestType());

        workRequests.stream().sorted(byType.thenComparing(byId)).forEach(e -> System.out.println(e));

    }


    //https://www.youtube.com/watch?v=MROCaYEmb6Y
    @Test
    public void java8_lambda_sort_two_comparators_type_and_date() throws InterruptedException {

        Comparator<WorkRequest> byType = (WorkRequest w1, WorkRequest w2) -> w1.getWorkRequestType().compareTo(w2.getWorkRequestType());
        Comparator<WorkRequest> byDate = (WorkRequest w1, WorkRequest w2) -> w1.getDateAdded().compareTo(w2.getDateAdded());

        workRequests.stream().sorted(byType.thenComparing(byDate)).forEach(e -> System.out.println(e));

    }

    @Test
    public void java8_lambda_sort_two_comparators_type_and_rank() throws InterruptedException {

        Comparator<WorkRequest> byType = (WorkRequest w1, WorkRequest w2) ->
                w1.getWorkRequestType().compareTo(w2.getWorkRequestType());

        Comparator<WorkRequest> byRank = (WorkRequest w1, WorkRequest w2) ->
                getRankBySecs(w1.getWorkRequestType(), w1.getDateAdded())
                        .compareTo(getRankBySecs(w2.getWorkRequestType(), w2.getDateAdded()));

        workRequests.stream().sorted(byType.thenComparing(byRank)).forEach(e -> System.out.println(e));

    }


    //https://www.youtube.com/watch?v=MROCaYEmb6Y
    @Test
    public void java8_lambda_sort_by_class_reverse() throws InterruptedException {

        Comparator<WorkRequest> byRank = (WorkRequest w1, WorkRequest w2) ->
                getRankBySecs(w2.getWorkRequestType(), w2.getDateAdded())
                        .compareTo(getRankBySecs(w1.getWorkRequestType(), w1.getDateAdded()));

        workRequests.stream().sorted(byRank).forEach(e -> System.out.println(e));
    }


    public Long getRankBySecs(WorkRequestType type, Date dateAdded) {

        Long secondsInQueue = (new Date().getTime() - dateAdded.getTime()) / 1000;

        // get the natural logarithm for x
        //System.out.println("Math.log(secondsInQueue)= " + Math.log(secondsInQueue));

        // get the natural logarithm for y
        //System.out.println("Math.log(secondsInQueue)= " + Math.log(secondsInQueue));

        // get the greater of the two numbers
        //System.out.println("Math.max(3, secondsInQueue)= " +  Math.max(3, secondsInQueue));

        // get the smaller of the two numbers
        // System.out.println("Math.min(3, secondsInQueue)= " +  Math.min(3, secondsInQueue));

        switch (type) {
            case MANAGEMENT_OVERRIDE:
                return secondsInQueue;

            case NORMAL:
                return secondsInQueue;

            case PRIORITY:
                //max(3, n log n) where n is number of seconds in queue?
                return (long) Math.max(3, secondsInQueue * Math.log(secondsInQueue));

            case VIP:
                //max(4, 2n log n) where n is number of seconds in queue?
                return (long) Math.max(4, 2 * secondsInQueue * Math.log(secondsInQueue));
        }
        return null;
    }


    private PriorityQueue<WorkRequest> populateQueue() {

        workRequests = new PriorityQueue();

        Date dateAdded = new Date();

        long millis = dateAdded.getTime();

        workRequests.add(new WorkRequest(2l, new Date(millis), Util.getWorkRequestType(2l)));
        millis = dateAdded.getTime() - 2109;
        workRequests.add(new WorkRequest(21l, new Date(millis), Util.getWorkRequestType(21l)));
        millis = dateAdded.getTime() - 1232;
        workRequests.add(new WorkRequest(12l, new Date(millis), Util.getWorkRequestType(12l)));
        millis = dateAdded.getTime() - 1239;
        workRequests.add(new WorkRequest(14l, new Date(millis), Util.getWorkRequestType(14l)));
        millis = dateAdded.getTime() - 382;
        workRequests.add(new WorkRequest(15l, new Date(millis), Util.getWorkRequestType(15l)));
        millis = dateAdded.getTime() - 1479;
        workRequests.add(new WorkRequest(20l, new Date(millis), Util.getWorkRequestType(20l)));
        millis = dateAdded.getTime() - 3861;
        workRequests.add(new WorkRequest(5l, new Date(millis), Util.getWorkRequestType(5l)));
        millis = dateAdded.getTime() - 5571;
        workRequests.add(new WorkRequest(4l, new Date(millis), Util.getWorkRequestType(4l)));
        millis = dateAdded.getTime() - 7764;
        workRequests.add(new WorkRequest(10l, new Date(millis), Util.getWorkRequestType(10l)));
        millis = dateAdded.getTime() - 845;
        workRequests.add(new WorkRequest(1l, new Date(millis), Util.getWorkRequestType(1l)));
        millis = dateAdded.getTime() - 1449;
        workRequests.add(new WorkRequest(30l, new Date(millis), Util.getWorkRequestType(30l)));
        millis = dateAdded.getTime() - 1032;
        workRequests.add(new WorkRequest(3l, new Date(millis), Util.getWorkRequestType(3l)));
        millis = dateAdded.getTime() - 1421;
        workRequests.add(new WorkRequest(7l, new Date(millis), Util.getWorkRequestType(7l)));
        millis = dateAdded.getTime() - 5112;
        workRequests.add(new WorkRequest(45l, new Date(millis), Util.getWorkRequestType(45l)));

        return workRequests;

    }


}
