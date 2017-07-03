package com.aspect;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * Created by Ruaidhri on 03/07/2017.
 */

@RestController
public class WorkRequestController {

    /**
     * An endpoint for adding a ID to queue (enqueue). This endpoint should
     * accept two parameters, the ID to enqueue and the time at which the ID
     * @param id
     * @param date
     * @return
     */
    @RequestMapping(value = "/put/{id}/{date}", method = RequestMethod.POST)
    public ResponseEntity<String> enqueueId(@PathVariable("id") Long id, @PathVariable("date") String date) {

        return null;
    }

    /**
     * An endpoint for getting the top ID from the queue and removing it (de-queue).
     * This endpoint should return the highest ranked ID and the time
     * @return
     */
    @RequestMapping(value = "/remove/top", method = RequestMethod.POST)
    public ResponseEntity<String> dequeueTop() {

        return null;
    }

    /**
     * An endpoint for getting the list of IDs in the queue.
     * This endpoint should return a list of IDs sorted from highest ranked to lowest.
     * @return
     */
    @RequestMapping(value = "/get/ids", method = RequestMethod.GET)
    public ResponseEntity<List<Integer>> listIds() {

        return null;
    }

    /**
     * An endpoint for removing a specific ID from the queue.
     * This endpoint should accept a single parameter, the ID to remove.
     * @param id
     * @return
     */
    @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> dequeueId(@PathVariable("id") Long id) {

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

        return null;
    }

}
