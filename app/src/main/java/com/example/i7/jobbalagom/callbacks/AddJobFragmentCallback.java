package com.example.i7.jobbalagom.callbacks;

/**
 * Created by Kajsa, Anton, Morgan, Jakup and Christoffer.
 * Interface for callback implementation for adding job.
 */

public interface AddJobFragmentCallback {

    /**
     * Adds jobb to database
     *
     * @param title name of the job.
     * @param wage  hourly wage for the job.
     */

    void addJob(String title, Float wage);

    /**
     * Adds ob to databse.
     *
     * @param jobTitle  name of the job.
     * @param day       the ob rate is valid.
     * @param startTime the time the ob rate starts.
     * @param endTime   the time the ob rate ends.
     * @param obIndex   the acutal ob rate.
     */

    void addOB(String jobTitle, String day, String startTime, String endTime, float obIndex);
}
