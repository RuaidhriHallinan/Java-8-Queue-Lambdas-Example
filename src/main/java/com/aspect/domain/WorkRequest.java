package com.aspect.domain;

import com.aspect.util.DateTimeUtil;

import java.util.Date;

/**
 * Created by Ruaidhri on 04/07/2017.
 */
public class WorkRequest implements Comparable<WorkRequest> {

    public long id;
    public Date added;

    public WorkRequest(long id, Date dateAdded) {
        this.id = id;
        this.added = dateAdded;
    }

    public long getId() {
        return id;
    }

    public Date getAdded() {
        return added;
    }

    @Override
    public int compareTo(WorkRequest o) {
        return added.compareTo(o.added);
    }

    /**
     * Overriding the toString representation of the object.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {

        // Get the Work Order Request Time
        long timeInQ = this.added.getTime();

        String shortTimeStr = DateTimeUtil.simpleDateFormatSecs.format(DateTimeUtil.getCurrentTimeMillis() - timeInQ);

        return "WorkRequest [Id: " + id + ", Time in Queue: " + shortTimeStr + "]";
    }
}

