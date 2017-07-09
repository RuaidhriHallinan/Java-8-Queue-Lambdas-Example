package com.apt;

import com.apt.domain.WorkRequest;
import com.apt.domain.WorkRequest.WorkRequestType;
import com.apt.util.Util;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Test class to test Util functionality
 * Also tests logic and lambdas
 *
 * Created by Ruaidhri on 06/07/2017.
 */
public class WorkRequestTests {

    private static PriorityQueue<WorkRequest> workRequests;

    /**
     * Setup the tests
     * Populates the WorkRequest Queue @workRequests
     *
     */
    @Before
    public void setup() throws InterruptedException {

        //Populating priority queue
        System.out.println("Setup Populating priority queue");
        workRequests = populateQueue();
    }

    /**
     * Test to sort the Priority Queue by the length of time in queue, regardless of Class/Type
     *
     */
    //https://www.youtube.com/watch?v=MROCaYEmb6Y
    @Test
    public void lambda_sort_length_in_queue() {

        //Printing out values (unSorted)
        System.out.println("Printing out values (unSorted)");

        workRequests.stream().forEach(System.out::println);

        //Printing out sorted by date values
        System.out.println("Printing out sorted values by date");

        //Sorting by Date and Adding to new Queue
        System.out.println("Lambda sorting by Date and Adding to new Queue");

        workRequests.stream().sorted((w1, w2) -> w1.getDateAdded().compareTo(w2.getDateAdded())).forEach(w -> System.out.println(w));

        long id = workRequests.stream().sorted((w1, w2) -> w1.getDateAdded().compareTo(w2.getDateAdded())).findFirst().get().getId();

        System.out.println("Longest in the queue is ID: " + id);
        assertEquals(4, id);
    }

    /**
     * Test to sort by type and rank
     *
     */
    @Test
    public void lambda_sort_two_comparators_type_and_rank() {

        System.out.println("Note: all types are ranked, management or normal will not be sorted by time");

        Comparator<WorkRequest> byType = (WorkRequest w1, WorkRequest w2) ->
                w1.getWorkRequestType().compareTo(w2.getWorkRequestType());

        Comparator<WorkRequest> byRank = (WorkRequest w1, WorkRequest w2) ->
                Util.getRankBySecs(w1.getWorkRequestType(), w1.getDateAdded())
                        .compareTo(Util.getRankBySecs(w2.getWorkRequestType(), w2.getDateAdded()));

        workRequests.stream().sorted(byType.thenComparing(byRank)).forEach(System.out::println);

        long id = workRequests.stream().sorted(byType.thenComparing(byRank)).findFirst().get().getId();

        System.out.println("Highest ranked if all items were ranked: " + id);
        assertEquals(id, 45);
    }

    /**
     * Test that filters and ranks VIP and Priority only
     *
     */
    @Test
    public void lambda_sort_vip_priority_by_rank() {

        System.out.println("VIP and Priority List");

        Comparator<WorkRequest> byRank = (WorkRequest w1, WorkRequest w2) ->
                Util.getRankBySecs(w1.getWorkRequestType(), w1.getDateAdded())
                        .compareTo(Util.getRankBySecs(w2.getWorkRequestType(), w2.getDateAdded()));

        Predicate<WorkRequest> vip = (wr) -> wr.getWorkRequestType() == WorkRequestType.VIP;
        Predicate<WorkRequest> priority = (wr) -> wr.getWorkRequestType() == WorkRequestType.PRIORITY;

        workRequests.stream().filter(vip).sorted(byRank).forEach(System.out::println);
        workRequests.stream().filter(priority).sorted(byRank).forEach(System.out::println);

        long topVIP = workRequests.stream().filter(vip).sorted(byRank).findFirst().get().getId();
        long topPriority = workRequests.stream().filter(priority).sorted(byRank).findFirst().get().getId();

        System.out.println("Top VIP " + topVIP);
        System.out.println("Top Priority " + topPriority);

        assertEquals(topVIP, 20l);
        assertEquals(topPriority, 3l);

    }

    /**
     * Test to get an items object from the queue by id
     */
    @Test
    public void test_get_by_id() {

        Long id = 45l;
        Predicate<WorkRequest> byId = (wr) -> wr.getId() == id;

        WorkRequest wr = workRequests.stream().filter(byId).findFirst().get();

        System.out.println("Retrieved: " + wr);
        assertEquals(wr.getId(), id);
    }

    /**
     * Test to filter all the Work Requests by the desired requirements
     * VIP and Priority get sorted by Rank, Normal and Management Override are sorted by time in queue
     * Results are returned in order
     *
     */
    @Test
    public void lambda_filter_results() {

        Comparator<WorkRequest> byDate = (WorkRequest w1, WorkRequest w2) -> w1.getDateAdded().compareTo(w2.getDateAdded());
        Comparator<WorkRequest> byRank = (WorkRequest w1, WorkRequest w2) ->
                Util.getRankBySecs(w1.getWorkRequestType(), w1.getDateAdded())
                        .compareTo(Util.getRankBySecs(w2.getWorkRequestType(), w2.getDateAdded()));

        List<WorkRequest> listForReturning = new ArrayList<>();

        Predicate<WorkRequest> management = (wr) -> wr.getWorkRequestType() == WorkRequestType.MANAGEMENT_OVERRIDE;
        List<WorkRequest> wrList = workRequests.stream().filter(management).sorted(byDate).collect(Collectors.toList());
        wrList.forEach(e -> System.out.println(e));
        wrList.forEach(e -> listForReturning.add(e));
        assertEquals(wrList.size(), 3);

        Predicate<WorkRequest> vip = (wr) -> wr.getWorkRequestType() == WorkRequestType.VIP;
        wrList = workRequests.stream().filter(vip).sorted(byRank).collect(Collectors.toList());
        wrList.forEach(e -> System.out.println(e));
        wrList.forEach(e -> listForReturning.add(e));
        assertEquals(wrList.size(), 3);

        Predicate<WorkRequest> priority = (ca) -> ca.getWorkRequestType() == WorkRequestType.PRIORITY;
        wrList = workRequests.stream().filter(priority).sorted(byRank).collect(Collectors.toList());
        wrList.forEach(e -> System.out.println(e));
        wrList.forEach(e -> listForReturning.add(e));
        assertEquals(wrList.size(), 3);

        Predicate<WorkRequest> normal = (wr) -> wr.getWorkRequestType() == WorkRequestType.NORMAL;
        wrList = workRequests.stream().filter(normal).sorted(byDate).collect(Collectors.toList());
        wrList.forEach(e -> System.out.println(e));
        wrList.forEach(e -> listForReturning.add(e));
        assertEquals(wrList.size(), 5);

        System.out.println("Returning List of sorted IDs: ");
        listForReturning.forEach(e -> System.out.println(e.getId()));

        assertEquals(listForReturning.size(), workRequests.size());
        assertEquals(listForReturning.get(0).getId(), new Long(45));
        //45, 30, 15, 20, 5, 10, 3, 12, 21, 4, 7, 14, 1, 2
    }

    /**
     * Test to calclate the average wait time in the queue
     *
     */
    @Test
    public void calculate_wait_time() {

        Date currentDate = new Date();
        long avgSecs = Util.getWaitTime(currentDate, workRequests);

        System.out.println("Average wait time in queue in seconds: " + avgSecs);
        assertEquals(avgSecs, 2l);
    }

    /**
     * Test to return a list of ids from the queue
     *
     */
    @Test
    public void get_ids() {

        List<Long> ids = Util.getWorkOrderIds(workRequests);

        System.out.println("List of Ids: " + ids);
        assertThat(ids, CoreMatchers.hasItems(4l, 14l, 7l, 2l, 1l, 12l, 5l, 21l, 10l, 15l, 30l, 20l, 3l, 45l));

        System.out.println("List of Integer Ids: " + 14);
        assertEquals(ids.size(), 14);
    }

    /**
     * Test to get the position of an id in the queue
     *
     */
    @Test
    public void get_position() {

        int pos = Util.getWorkRequestPosition(21l, workRequests);
        System.out.println("Position of ID 21: " + pos);
        assertEquals(pos, 8);
    }

    /**
     * Test for polling or removing the top items off the queue
     *
     */
    @Test
    public void poll_test() {

        System.out.println("First element: " + workRequests.stream().findFirst().get());
        WorkRequest wr = workRequests.poll();
        System.out.println("Removed : " + wr);
        System.out.println("First element: " + workRequests.stream().findFirst().get());
        wr = workRequests.poll();
        System.out.println("Removed : " + wr);
        System.out.println("First element: " + workRequests.stream().findFirst().get());
        wr = workRequests.poll();
        System.out.println("Removed : " + wr);
        System.out.println("First element: " + workRequests.stream().findFirst().get());
        wr = workRequests.poll();
        System.out.println("Removed : " + wr);
        System.out.println("First element: " + workRequests.stream().findFirst().get());
        wr = workRequests.poll();
        System.out.println("Removed : " + wr);
        System.out.println("First element: " + workRequests.stream().findFirst().get());
        wr = workRequests.poll();
        System.out.println("Removed : " + wr);
        System.out.println("First element: " + workRequests.stream().findFirst().get());
        wr = workRequests.poll();
        System.out.println("Removed : " + wr);
        System.out.println("First element: " + workRequests.stream().findFirst().get());
        wr = workRequests.poll();
        System.out.println("Removed : " + wr);
        System.out.println("First element: " + workRequests.stream().findFirst().get());
        wr = workRequests.poll();
        System.out.println("Removed : " + wr);
        System.out.println("First element: " + workRequests.stream().findFirst().get());
        wr = workRequests.poll();
        System.out.println("Removed : " + wr);
        System.out.println("First element: " + workRequests.stream().findFirst().get());
        wr = workRequests.poll();
        System.out.println("Removed : " + wr);
        System.out.println("First element: " + workRequests.stream().findFirst().get());
        wr = workRequests.poll();
        System.out.println("Removed : " + wr);
        System.out.println("First element: " + workRequests.stream().findFirst().get());
        wr = workRequests.poll();
        System.out.println("Removed 1: " + wr);
        System.out.println("First element: " + workRequests.stream().findFirst().get());
        wr = workRequests.poll();
        System.out.println("Removed 2: " + wr);

        assertEquals(0, workRequests.size());

    }

    /**
     * Method to populate the queue with random values
     *
     * @return WorkRequest PriorityQueue
     */
    private PriorityQueue<WorkRequest> populateQueue() {

        workRequests = new PriorityQueue<WorkRequest>();
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
        millis = dateAdded.getTime() - 1879;
        workRequests.add(new WorkRequest(20l, new Date(millis), Util.getWorkRequestType(20l)));
        millis = dateAdded.getTime() - 4161;
        workRequests.add(new WorkRequest(5l, new Date(millis), Util.getWorkRequestType(5l)));
        millis = dateAdded.getTime() - 5571;
        workRequests.add(new WorkRequest(4l, new Date(millis), Util.getWorkRequestType(4l)));
        millis = dateAdded.getTime() - 5564;
        workRequests.add(new WorkRequest(10l, new Date(millis), Util.getWorkRequestType(10l)));
        millis = dateAdded.getTime() - 1845;
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
