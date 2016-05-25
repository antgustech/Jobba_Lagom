package com.example.i7.jobbalagom.callbacks;

/**
 * Created by Kajsa, Anton, Morgan, Jakup and Christoffer.
 */

public interface AddJobFragmentCallback {
    void addJob(String title, Float wage);
    void addOB(String jobTitle, String day, String fromTime, String toTime, float obIndex);
}
