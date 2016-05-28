package com.example.i7.jobbalagom.callbacks;

/**
 * Created by Kajsa, Anton, Morgan, Jakup and Christoffer.
 * Interface for callback implementation for changing tax rate.
 */

public interface ChangeTaxFragmentCallback {
    /**
     * Sets a new tax rate in database.
     *
     * @param tax the new tax rate.
     */
    void updateTax(float tax);

    /**
     * Checks if there is connection to server or not.
     *
     * @return true if there is a connection.
     */
    boolean checkConnection();
}
