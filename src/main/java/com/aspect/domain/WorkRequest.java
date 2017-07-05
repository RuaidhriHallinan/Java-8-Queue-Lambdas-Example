package com.aspect.domain;

import com.aspect.util.DateTimeUtil;

import java.util.Date;

/**
 * This object represents a work request
 *
 * Created by Ruaidhri on 04/07/2017.
 */
public class WorkRequest implements Comparable<WorkRequest> {

    public long id;
    public Date dataAdded;
    public WorkRequestType workRequestType;

    /**
     * Constructor for Work Requests
     *
     * @param id
     * @param dateAdded
     * @param workRequestType
     */
    public WorkRequest(long id, Date dateAdded, WorkRequestType workRequestType) {
        this.id = id;
        this.dataAdded = dateAdded;
        this.workRequestType = workRequestType;
    }

    /**
     * Id of a
     *
     * @return
     */
    public long getId() {
        return id;
    }

    /**
     * @return added
     */
    public Date getDateAdded() {
        return dataAdded;
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
        long timeInQ = this.dataAdded.getTime();

        String shortTimeStr = DateTimeUtil.simpleDateFormatSecs.format(DateTimeUtil.getCurrentTimeMillis() - timeInQ);

        return "WorkRequest [Id: " + id + ", Time in Queue: " + shortTimeStr + "]";
    }

    /**
     * enum for Work Request Types: NORMAL, VIP, PRIORITY, MANAGEMENT_OVERRIDE
     */
    public enum WorkRequestType {
        NORMAL, VIP, PRIORITY, MANAGEMENT_OVERRIDE }
}

