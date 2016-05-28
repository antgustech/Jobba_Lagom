package com.example.i7.jobbalagom.callbacks;

/**
 * Created by Kajsa, Anton, Morgan, Jakup and Christoffer.
 * Interface for callback implementation for adding shift.
 */

public interface AddShiftFragmentCallback {

    /**
     * Add shift to database.
     *
     * @param jobTitle     name of the job.
     * @param startTime    time shift started.
     * @param endTime      time shift ended.
     * @param hoursWorked  endTime-startTime.
     * @param year         year the shift was completed.
     * @param month        month the shift was completed.
     * @param day          day the shift was completed.
     * @param breakMinutes the break in minutes in the shift.
     */
    void addShift(String jobTitle, float startTime, float endTime, float hoursWorked, int year, int month, int day, float breakMinutes);
}
