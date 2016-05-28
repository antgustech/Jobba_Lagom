package com.example.i7.jobbalagom.callbacks;

/**
 * Created by Kajsa, Anton, Morgan, Jakup and Christoffer.
 * Interface for callback implementation for updating the tax rate.
 */

public interface UpdateTaxCallback {
    /**
     * Sets a new tax rate to database.
     *
     * @param tax the new tax rate as a float.
     */
    public void UpdateTax(float tax);
}
