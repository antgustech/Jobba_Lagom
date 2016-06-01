package com.example.i7.jobbalagom.callbacks;

/**
 * Created by Kajsa, Anton, Morgan, Jakup and Christoffer.
 * Interface for callback implementation of BudgetFragment.
 * TODO Not in use.
 */

public interface BudgetFragmentCallback {
    String[] getExpenses(int month);
    String[] getIncomes( int month);

    void removeExpense (int id);
    void removeIncome (int id);

    String getDate(int selectedMonth, int selectedYear);
}
