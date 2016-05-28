package com.example.i7.jobbalagom.callbacks;

/**
 * Created by Kajsa, Anton, Morgan, Jakup and Christoffer.
 * Interface for callback implementation for adding a new user for the first time.
 */

public interface SetupFragmentCallback {
    /**
     * Adds new user to database.
     *
     * @param municipality the choosen municipality.
     * @param incomeLimit  the choosen income limit.
     * @param church       returns true if you pressed that you are a member of the swedish church.
     */
    void addUser(String municipality, float incomeLimit, boolean church);
}
