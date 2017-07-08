package com.aspect.domain;

import com.aspect.util.DateTimeUtil;
import com.aspect.util.Util;

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
     * To be used for sorting automatically by rank based on seconds in the queue
     *
     * @param workRequest2
     * @return int
     */
    @Override
    public int compareTo(WorkRequest workRequest2) {

        if (this.getWorkRequestType() == WorkRequestType.MANAGEMENT_OVERRIDE
                && workRequest2.getWorkRequestType() == WorkRequestType.MANAGEMENT_OVERRIDE) {

            Long w1Millis = this.getDateAdded().getTime();
            Long w2Millis = workRequest2.getDateAdded().getTime();

            return w1Millis.compareTo(w2Millis);
        }

        if (this.getWorkRequestType() == WorkRequestType.NORMAL
                && workRequest2.getWorkRequestType() == WorkRequestType.NORMAL) {

            Long w1Millis = this.getDateAdded().getTime();
            Long w2Millis = workRequest2.getDateAdded().getTime();

            return w1Millis.compareTo(w2Millis);
        }

        if (this.getWorkRequestType() == WorkRequestType.MANAGEMENT_OVERRIDE
                && workRequest2.getWorkRequestType() == WorkRequestType.NORMAL) {
            return 1;
        }

        if (this.getWorkRequestType() == WorkRequestType.NORMAL
                && workRequest2.getWorkRequestType() == WorkRequestType.MANAGEMENT_OVERRIDE) {
            return -1;
        }

        Date currentTime = new Date();

        Long w1RankFromSecs = Util.getRankBySecs(this.getWorkRequestType(),
                new Date(currentTime.getTime() - this.getDateAdded().getTime()));

        Long w2RankFromSecs = Util.getRankBySecs(workRequest2.getWorkRequestType(),
                new Date(currentTime.getTime() - workRequest2.getDateAdded().getTime()));

        return w1RankFromSecs.compareTo(w2RankFromSecs);
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

