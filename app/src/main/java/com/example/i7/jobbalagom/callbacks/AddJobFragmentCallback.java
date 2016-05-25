package com.example.i7.jobbalagom.callbacks;

/**
 * Created by Kajsa on 2016-05-04.
 */

public interface AddJobFragmentCallback {
    void addJob(String title, Float wage);
    void addOB(String jobTitle, String day, String fromTime, String toTime, float obIndex);
}
