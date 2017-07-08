package com.apt.domain;

import com.apt.util.DateTimeUtil;

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
     * @param id id of the requests
     * @param dateAdded date request was added
     * @param workRequestType type or classification of Work Request
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
     *
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
     * To be used for sorting automatically by rank based on seconds in the queue
     *
     * @param workRequest2 WorkRequest for comparing
     * @return int signed comparison between values
     */
    @Override
    public int compareTo(WorkRequest workRequest2) {
        return getDateAdded().compareTo(workRequest2.getDateAdded());
    }


    /**
     * Overriding the toString representation of the object.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {

        // Get the Work Order Request Time
        long timeInQ = getDateAdded().getTime();

        String shortTimeStr = String.valueOf((DateTimeUtil.getCurrentTimeMillis() - timeInQ));

        return "WorkRequest [Id: " + id + ", Length: " + shortTimeStr + " millis, " + workRequestType + " ]";
    }

    /**
     * enum for Work Request Types: NORMAL, VIP, PRIORITY, MANAGEMENT_OVERRIDE
     */
    public enum WorkRequestType {
        MANAGEMENT_OVERRIDE, VIP, PRIORITY, NORMAL
    }
}

