package com.aspect.domain;

import com.aspect.util.DateTimeUtil;

import java.util.Date;

/**
 * This object represents a work request
 *
 * Created by Ruaidhri on 04/07/2017.
 */
public class WorkRequest implements Comparable<WorkRequest> {

    public Long id;
    public Date dateAdded;
    public WorkRequestType workRequestType;

    /**
     * Constructor for Work Requests
     *
     * @param id
     * @param dateAdded

     * @param workRequestType
     */
    public WorkRequest(Long id, Date dateAdded, WorkRequestType workRequestType) {
        this.id = id;
        this.dateAdded = dateAdded;
        this.workRequestType = workRequestType;
    }

    /**
     * Id of a WorkRequest
     *
     * @return lond id
     */
    public Long getId() {
        return id;
    }

    /**
     * Date Work Request is added to the queue
     * @return Date dateAdded
     */
    public Date getDateAdded() {
        return dateAdded;
    }

    /**
     * Returns the classification or type
     *
     * @return enum WorkRequestType
     */
    public WorkRequestType getWorkRequestType() {
        return workRequestType;
    }

    /**
     * Overriding compareTo in comparable interface
     * To be used for sorting
     *
     * @param workRequest
     * @return int
     */
    @Override
    public int compareTo(WorkRequest workRequest) {
        return getDateAdded().compareTo(workRequest.getDateAdded());
    }

    /**
     * Overriding the toString representation of the object.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {

        // Get the Work Order Request Time
        long timeInQ = this.dateAdded.getTime();

        String shortTimeStr = String.valueOf((DateTimeUtil.getCurrentTimeMillis() - timeInQ) / 1000);

        return "WorkRequest [Id: " + id + ", Time in Queue: " + shortTimeStr + " secs ]" + " " + workRequestType;
    }

    /**
     * enum for Work Request Types: NORMAL, VIP, PRIORITY, MANAGEMENT_OVERRIDE
     */
    public enum WorkRequestType {
        NORMAL, VIP, PRIORITY, MANAGEMENT_OVERRIDE }
}

