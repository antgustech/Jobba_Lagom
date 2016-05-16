package com.example.i7.jobbalagom.callback_interfaces;

/**
 * Created by Kajsa on 2016-05-08.
 */
public interface AddShiftFragmentCallback {
    public void addShift(String jobTitle, float startTime, float endTime, float hoursWorked, int year, int month, int day);
    public void showAddJobFragment();
}
