package com.example.i7.jobbalagom.callbacks;

/**
 * Created by Kajsa, Anton, Morgan, Jakup and Christoffer.
 * Interface for callback implementation for removing job.
 */

public interface RemoveJobFragmentCallback {

    /**
     * Removes job from database.
     *
     * @param string the name of the job.
     */
    void removeJob(String string);
}
