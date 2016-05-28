package com.example.i7.jobbalagom.callbacks;

/**
 * Created by Kajsa, Anton, Morgan, Jakup and Christoffer.
 * Interface for callback implementation for adding expense.
 */

public interface AddExpenseFragmentCallback {

    /**
     * Adds expense to database.
     *
     * @param title  name of expense.
     * @param amount amount of expense.
     * @param year   the year the expense ocurred.
     * @param month  the month the expense ocurred.
     * @param day    the day the expense ocurred.
     */

    void addExpense(String title, float amount, int year, int month, int day);
}
