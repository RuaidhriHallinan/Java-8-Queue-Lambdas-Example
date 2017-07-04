package com.aspect;

import com.aspect.domain.WorkRequest;
import com.aspect.services.WorkOrderRequestService;
import com.aspect.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

/**
 * Created by Ruaidhri on 03/07/2017.
 */
@RestController
public class WorkRequestController {

    private WorkOrderRequestService workOrderRequestService;

    @Autowired
    WorkRequestController(WorkOrderRequestService workOrderRequestService) {
        this.workOrderRequestService = workOrderRequestService;
    }

    @RequestMapping("/say/hello/{name}")
    String hello(@PathVariable String name) {
        return "Hello there, " + name + "!";
    }

    /**
     * An endpoint for adding a ID to queue (enqueue). This endpoint should
     * accept two parameters, the ID to enqueue and the time at which the ID
     * @param id
     * @param date
     * @return
     */
    @RequestMapping(value = "/put/{id}/{date}", method = RequestMethod.POST)
    public ResponseEntity<String> enqueueIdDate(@PathVariable("id") Long id, @PathVariable("date") String date) {

        if (id != null && id >= 0 && date != null) {

            try {
                if (workOrderRequestService.enqueue(new WorkRequest(id, DateTimeUtil.composeDate(date)))) {
                    return new ResponseEntity(HttpStatus.OK);
                }
            } catch (ParseException e) {
                return new ResponseEntity<>("Invalid date format", HttpStatus.BAD_REQUEST);
            }

        } else {
            return new ResponseEntity<>("Invalid id", HttpStatus.BAD_REQUEST);
        }

        return null;
    }

    /**
     * An endpoint for removing a specific ID from the queue.
     * This endpoint should accept a single parameter, the ID to remove.
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/remove/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> dequeueId(@PathVariable("id") Long id) {

        if (id != null) {
            if (workOrderRequestService.dequeue(id)) {
                return new ResponseEntity(HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Empty queue", HttpStatus.BAD_REQUEST);
            }

        } else {
            return new ResponseEntity<>("Invalid id", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * An endpoint for getting the top ID from the queue and removing it (de-queue).
     * This endpoint should return the highest ranked ID and the time
     * @return
     */
    @RequestMapping(value = "/get/top", method = RequestMethod.GET)
    public ResponseEntity<String> dequeueTop() {

        try {
            WorkRequest workRequest = workOrderRequestService.dequeueTop();

            if (workRequest == null) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity(workRequest, HttpStatus.OK);
            }

        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * An endpoint for getting the list of IDs in the queue.
     * This endpoint should return a list of IDs sorted from highest ranked to lowest.
     * @return
     */
    @RequestMapping(value = "/get/ids", method = RequestMethod.GET)
    public ResponseEntity<List<Integer>> listIds() {

        // TODO use Lambdas here
        // Predicates for the conditions, Stream, and forEach? etc eg

        // Full filter predicate
        //List<WorkRequest> requests = workRequests.stream().filter(fullFilterPredicate).collect(Collectors.toList());
        //see notes
        return null;
    }

    /**
     * An endpoint to get the position of a specific ID in the queue.
     * This endpoint should accept one parameter, the ID to get the position of.
     * It should return the position of the ID in the queue indexed from 0.
     * @param id
     * @return
     */
    @RequestMapping(value = "/get/position/{id}", method = RequestMethod.GET)
    public ResponseEntity<Integer> position(@PathVariable("id") Long id) {

        //TODO use Lambdas here

        return null;
    }

    /**
     * An endpoint to get the average wait time.
     * This endpoint should accept a single parameter, the current time,
     * and should return the average (mean) number of seconds that each ID has been waiting in the queue.
     * @return
     */
    @RequestMapping(value = "/get/{time}", method = RequestMethod.GET)
    public ResponseEntity<List<Integer>> averageTime() {

        //TODO use Lambdas / filters here

        return null;
    }

}
