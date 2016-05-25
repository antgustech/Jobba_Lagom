package com.example.i7.jobbalagom.callbacks;

/**
 * Created by Kajsa on 2016-05-08.
 */
public interface AddShiftFragmentCallback {
    void addShift(String jobTitle, float startTime, float endTime, float hoursWorked, int year, int month, int day, float breakMinutes);
}
