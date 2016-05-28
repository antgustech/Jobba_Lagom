package com.example.i7.jobbalagom.callbacks;

/**
 * Created by Kajsa, Anton, Morgan, Jakup and Christoffer.
 * Interface for callback implementation for changing income limit.
 */

public interface ChangeIncomeLimitFragmentCallback {

    /**
     * Get the current income limit.
     *
     * @return the current income limit as a float.
     */
    float getIncomeLimit();

    /**
     * Set a new income limit in database.
     *
     * @param limit the new limit.
     */
    void setIncomeLimit(float limit);
}
