package com.apt;

import com.apt.domain.WorkRequest;
import com.apt.services.WorkOrderRequestService;
import com.apt.util.DateTimeUtil;
import com.apt.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * WorkRequestController RESTful controller that accepts GET, PUT, POST, DELETE requests
 * <p>
 * Created by Ruaidhri on 03/07/2017.
 */
@RestController
public class WorkRequestController {

    private WorkOrderRequestService workOrderRequestService;

    @Autowired
    WorkRequestController(WorkOrderRequestService workOrderRequestService) {
        this.workOrderRequestService = workOrderRequestService;
    }

    /**
     * An endpoint for adding a ID to queue (enqueue). This endpoint should
     * accept two parameters, the ID to enqueue and the time at which the ID
     *
     * @param id identifer of WorkRequest
     * @param date date of WorkRequest in format DD-MM-YYYY hh:mm:ss
     * @return Ok, unprocessable or bad request status
     */
    @RequestMapping(value = "/put/{id}/{date}", method = RequestMethod.POST)
    public ResponseEntity<String> enqueueIdDate(@PathVariable("id") Long id, @PathVariable("date") String date) {

        if (id != null && id >= 0 && date != null) {
            try {
                WorkRequest wr = new WorkRequest(id, DateTimeUtil.composeDate(date), Util.getWorkRequestType(id));
                if (workOrderRequestService.enqueue(wr)) {
                    return new ResponseEntity(HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Queue already contains Id", HttpStatus.UNPROCESSABLE_ENTITY);
                }
            } catch (ParseException e) {
                return new ResponseEntity<>("Invalid Date format", HttpStatus.UNPROCESSABLE_ENTITY);
            }

        } else {
            return new ResponseEntity<>("No id or date parameter", HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * An endpoint for removing a specific ID from the queue.
     * This endpoint should accept a single parameter, the ID to remove.
     *
     * @param id identifer of WorkRequest
     * @return Ok, not found or bad request status
     */
    @RequestMapping(value = "/remove/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> dequeueId(@PathVariable("id") Long id) {

        if (id != null && id > 0) {
            if (workOrderRequestService.dequeue(id)) {
                return new ResponseEntity(HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Id not found", HttpStatus.NOT_FOUND);
            }

        } else {
            return new ResponseEntity<>("Invalid id parameter", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * An endpoint for getting the top ID from the queue and removing it (de-queue).
     * This endpoint should return the highest ranked ID and the time
     *
     * @return Ok, internal error or not found response
     */
    @RequestMapping(value = "/remove/top", method = RequestMethod.DELETE)
    public ResponseEntity<String> dequeueTop() {

        if (workOrderRequestService != null) {
            try {

                long id = workOrderRequestService.dequeueTop();
                return new ResponseEntity("Removed " + id + " from the queue", HttpStatus.OK);

            } catch (Exception e) {
                return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity("Priority Queue empty", HttpStatus.NOT_FOUND);
        }

    }

    /**
     * An endpoint for getting the list of IDs in the queue.
     * This endpoint should return a list of IDs sorted from highest ranked to lowest.
     *
     * @return List of ids
     */
    @RequestMapping(value = "/get/ids", method = RequestMethod.GET)
    public ResponseEntity<List<Long>> listIds() {
        List<Long> workOrders = workOrderRequestService.getWorkOrderIDs();
        return new ResponseEntity(workOrders, HttpStatus.OK);
    }

    /**
     * An endpoint to get the position of a specific ID in the queue.
     * This endpoint should accept one parameter, the ID to get the position of.
     * It should return the position of the ID in the queue indexed from 0.
     *
     * @param id identifer of WorkRequest to get position of
     * @return Integer index of postion
     */
    @RequestMapping(value = "/get/position/{id}", method = RequestMethod.GET)
    public ResponseEntity<Integer> position(@PathVariable("id") Long id) {

        if (id != null && id > 0) {
            int position = workOrderRequestService.getPosition(id);
            return new ResponseEntity(position, HttpStatus.OK);
        }

        return new ResponseEntity("Invalid id", HttpStatus.BAD_REQUEST);
    }

    /**
     * An endpoint to get the average wait time.
     * This endpoint should accept a single parameter, the current time,
     * and should return the average (mean) number of seconds that each ID has been waiting in the queue.
     *
     * @param currentDateRequest time to compare dates before anc calculate mean
     * @return Ok or unproccessable entry
     */
    @RequestMapping(value = "/get/mean/{time}", method = RequestMethod.GET)
    public ResponseEntity<String> averageTime(@PathVariable("time") String currentDateRequest) {

        try {
            if (currentDateRequest != null && !currentDateRequest.isEmpty()) {
                Date currentDate = DateTimeUtil.composeDate(currentDateRequest);
                Long meanWaitSecs = workOrderRequestService.getWaitTime(currentDate);
                return new ResponseEntity(meanWaitSecs, HttpStatus.OK);
            }
        } catch (ParseException p) {
            return new ResponseEntity("Invalid date format", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return null;
    }

}
