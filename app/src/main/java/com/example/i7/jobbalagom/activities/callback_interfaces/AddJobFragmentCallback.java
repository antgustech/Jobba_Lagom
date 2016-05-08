package com.example.i7.jobbalagom.activities.callback_interfaces;

/**
 * Created by Kajsa on 2016-05-04.
 */

public interface AddJobFragmentCallback {
    public void addJob(String title, Float wage);
    public void addOB(String jobTitle, String day, String fromTime, String toTime, Float ob);
}
