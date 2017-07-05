package com.aspect.util;

import com.aspect.domain.WorkRequest;
import com.aspect.domain.WorkRequest.WorkRequestType;

import java.util.PriorityQueue;

/**
 * Utility class for any reuseable logic that is needed
 * <p>
 * Created by Ruaidhri on 05/07/2017.
 */
public class Util {

    /**
     * This method calculates the classification or type of the Work Requests
     * in the queue.
     * <p>
     * IDs that are evenly divisible by 3 are priority IDs.
     * IDs that are evenly divisible by 5 are VIP IDs.
     * IDs that are evenly divisible by both 3 and 5 are management override IDs.
     * IDs that are not evenly divisible by 3 or 5 are normal IDs.
     *
     * @param id identifer of teh Work Reqyest
     * @return Work Request enum type NORMAL, PRIORITY, VIP, MANAGEMENT_OVERRIDE
     */
    public static WorkRequestType getWorkRequestType(Long id) {

        if (id % 3 == 0 && id % 5 == 0) {
            return WorkRequestType.MANAGEMENT_OVERRIDE;
        } else if (id % 5 == 0) {
            return WorkRequestType.VIP;
        } else if (id % 3 == 0) {
            return WorkRequestType.PRIORITY;
        }

        return WorkRequestType.NORMAL;
    }

    public static void sortPriorityQueue(PriorityQueue<WorkRequest> workRequestQueue) {

        //TODO Sort this queue


    }
}
