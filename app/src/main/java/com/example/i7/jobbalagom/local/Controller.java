package com.example.i7.jobbalagom.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.i7.jobbalagom.callbacks.UpdateTaxCallback;
import com.example.i7.jobbalagom.localDatabase.DBHelper;
import com.example.i7.jobbalagom.remote.Client;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Christoffer, Kajsa, Jakup, Anton and Morgan
 * Handles calls between the UI and Logic classes.
 */

public class Controller {

    private final Client client;
    private final DBHelper dbHelper;
    private float tax;
    private SQLiteDatabase sqLiteDatabase;

    /**
     * Instantiates a new Client and DBHelper at given context.
     *
     * @param context the current state of the application
     */

    public Controller(Context context) {
        int PORT = 4545;
        String IP = "192.168.1.136";
        client = new Client(IP, PORT);
        dbHelper = new DBHelper(context);
    }

    /**
     * Gets all municipalities.
     *
     * @return ArrayList of all municipalities in Strings.
     */

    public ArrayList<String> getMunicipalities() {
        return client.getMunicipalities();
    }


    /**
     * Gets church tax rate.
     *
     * @param municipality the choosen municipality.
     */
    public void getChurchTax(String municipality, UpdateTaxCallback callback) {
        client.getChurchTaxFromServer(municipality, callback);
    }

    /**
     * Gets tax rate.
     *
     * @param municipality the choosen municipality.
     */

    public void getUserTax(String municipality, UpdateTaxCallback callback) {
        client.getTaxFromServer(municipality, callback);
    }

    /**
     * Checks whenever the app has connection to servern
     *
     * @return true if there is a connection.
     */

    public boolean checkConnection() {
        return client.checkConnection();
    }

    /**
     * Adds user to db
     */

    public void addUser(String municipality, float incomeLimit, boolean church) {
        CreateUserListener callback = new CreateUserListener(municipality, incomeLimit);
        if (church) {
            client.getChurchTaxFromServer(municipality, callback);
        } else {
            client.getTaxFromServer(municipality, callback);
        }
        Log.e("controller", "ChurchTAX LOGGING: " + church + " är boolean och tax är : " + tax);
    }

    /**
     * Adds a new job to the database.
     *
     * @param title name of the job.
     * @param wage  the hourly wage of the job.
     */

    public void addJob(String title, float wage) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        dbHelper.addJob(title, wage, sqLiteDatabase);
    }

    /**
     * Adds a new obRate for the addedJob.
     *
     * @param jobTitle the name of the job.
     * @param day      the day the ob rate is valid.
     * @param endTime  the time the ob rate ends.
     * @param obIndex  the actual ob rate.
     */

    public void addOB(String jobTitle, String day, String startTime, String endTime, Float obIndex) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        dbHelper.addOB(jobTitle, day, startTime, endTime, obIndex, sqLiteDatabase);
    }

    /**
     * Adds a new shift to the database.
     *
     * @param jobTitle    name of the job.
     * @param startTime   the time the shift starts.
     * @param endTime     the time the shift ends.
     * @param hoursWorked endTime-startTime.
     * @param year        the year the shift was done.
     * @param month       the month the shift was done.
     * @param day         the day the shift was done.
     */

    private void addShift(String jobTitle, float startTime, float endTime, float hoursWorked, int year, int month, int day, float taxIncome, float noTaxIncome) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        dbHelper.addShift(jobTitle, startTime, endTime, hoursWorked, year, month, day, taxIncome, noTaxIncome, sqLiteDatabase);
    }

    /**
     * Adds an expense to the database.
     *
     * @param name  name of the expense.
     * @param sum   of the expense.
     * @param year  the year the expense was done.
     * @param month the month the expense was done.
     * @param day   the day the expense was done.
     */

    public void addExpense(String name, float sum, int year, int month, int day) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        dbHelper.addExpense(name, sum, year, month, day, sqLiteDatabase);
    }

    /**
     * Delete User from db.
     *
     * @param name of the user.
     */

    public void deleteUser(String name) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        dbHelper.deleteUser(sqLiteDatabase);
    }

    /**
     * Delete Job from db.
     *
     * @param name of the job.
     */

    public void deleteJob(String name) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        dbHelper.deleteJob(name, sqLiteDatabase);
    }

    /**
     * Delete Shift from db.
     *
     * @param id of shift.
     */

    public void deleteShift(int id) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        dbHelper.deleteShift(id, sqLiteDatabase);
    }

    /**
     * Delete Expense from db.
     *
     * @param name of the expense.
     */

    public void deleteExpense(String name) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        dbHelper.deleteExpense(name, sqLiteDatabase);
        dbHelper.close();
    }

    /**
     * Check if already is an registered user.
     *
     * @return true if there is.
     */
    public boolean isUserCreated() {
        sqLiteDatabase = dbHelper.getReadableDatabase();
        return dbHelper.isUserCreated(sqLiteDatabase);
    }

    /**
     * Get 6 months sum of income.
     *
     * @param month the selected month.
     * @param year  the selected year.
     * @return sum of all income as a float.
     */

    public float getHalfYearIncome(int month, int year) {
        sqLiteDatabase = dbHelper.getReadableDatabase();
        return dbHelper.getHalfYearIncome(month, year, sqLiteDatabase);
    }

    /**
     * Gets income limit.
     *
     * @return the income limit as a float.
     */

    public float getIncomeLimit() {
        sqLiteDatabase = dbHelper.getReadableDatabase();
        return dbHelper.getIncomeLimit(sqLiteDatabase);
    }

    /**
     * Set a new income limit.
     *
     * @param limit the new limit.
     */

    public void setIncomeLimit(float limit) {
        sqLiteDatabase = dbHelper.getReadableDatabase();
        dbHelper.setIncomeLimit(limit, sqLiteDatabase);
    }

    /**
     * Get all the job names.
     *
     * @return job names as a String[].
     */

    public String[] getJobTitles() {
        sqLiteDatabase = dbHelper.getReadableDatabase();
        return dbHelper.getJobTitles(sqLiteDatabase);
    }

    /**
     * Get wage for select job.
     *
     * @param jobTitle the name of the choosen name.
     * @return the hourly wage for select job.
     */

    private float getWage(String jobTitle) {
        sqLiteDatabase = dbHelper.getReadableDatabase();
        return dbHelper.getWage(jobTitle, sqLiteDatabase);
    }

    /**
     * Get tax rate for the user.
     *
     * @return the currently stored tax rate as a float.
     */

    public float getUserTax() {
        sqLiteDatabase = dbHelper.getReadableDatabase();
        return dbHelper.getUserTax(sqLiteDatabase);
    }

    /**
     * Get the muncipality for the user.
     *
     * @return the currently stored municipality as a String.
     */

    public String getUserMunicipality() {
        sqLiteDatabase = dbHelper.getReadableDatabase();
        return dbHelper.getUserMunicipality(sqLiteDatabase);
    }

    /**
     * Get the ob rate.
     *
     * @param jobTitle the name of the job.
     * @param day      the day the ob rate is valid.
     * @return the ob rate for the selected job and day as a float.
     */

    private float getOB(String jobTitle, String day) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        return dbHelper.getOB(jobTitle, day, sqLiteDatabase);
    }

    /**
     * Get the start time for the ob rate.
     *
     * @param jobTitle the name of the job
     * @param day      the day the ob rate is valid.
     * @return the start time for the ob rate.
     */

    private float getOBStart(String jobTitle, String day) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        return dbHelper.getOBStart(jobTitle, day, sqLiteDatabase);
    }

    /**
     * Get the end time for the ob rate.
     *
     * @param jobTitle the name of the job
     * @param day      the day the ob rate is valid.
     * @return the end time for the ob rate.
     */

    private float getOBEnd(String jobTitle, String day) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        return dbHelper.getOBEnd(jobTitle, day, sqLiteDatabase);
    }

    /**
     * Set tax for user.
     *
     * @param tax the new tax.
     */


    public void setTax(float tax) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        dbHelper.setTax(tax, sqLiteDatabase);
    }

    /**
     * Remove job from db.
     *
     * @param jobTitle the name of the job to be removed.
     */

    public void removeJob(String jobTitle) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        dbHelper.deleteJob(jobTitle, sqLiteDatabase);
    }

    /**
     * Calculates the given shift with tax, ob rates and break.
     *
     * @param jobTitle     the name of the job
     * @param startTime    start time of shift.
     * @param endTime      end time of shift.
     * @param year         the year the shift was completed on.
     * @param month        the month the shift was completed on.
     * @param day          the day the shift was completed on.
     * @param breakMinutes the number of minutes the break lasted in the shift.
     * @return the final income for the shift as a float.
     */
    public float caculateShift(String jobTitle, float startTime, float endTime, int year, int month, int day, float breakMinutes) {

        float workedPay, workedTime, realTax, workedObPay = 0;
        int dayOfWeek;
        String dayName;
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR) / 100 * 100 + year;
        c.set(year, month - 1, day);
        dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek > 1 && dayOfWeek < 7) {
            dayName = "Vardag";
        } else if (dayOfWeek == 7) {
            dayName = "Lördag";
        } else {
            dayName = "Söndag";
        }
        float wage = getWage(jobTitle);
        tax = getUserTax();
        float obIndex = getOB(jobTitle, dayName);
        float obStart = getOBStart(jobTitle, dayName);
        float obEnd = getOBEnd(jobTitle, dayName);
        float taxIncome = 0;
        float noTaxIncome=0;
        if (obIndex == 0) {
            obIndex++;
        }
        if (obStart < endTime) {
            if (endTime > obEnd) {
                workedObPay = ((obEnd - obStart) * ((obIndex - 1) * wage));
            } else if (endTime <= obEnd) {
                workedObPay = ((endTime - obStart) * ((obIndex - 1) * wage));
            }
        }
        workedTime = (((endTime - startTime) / 2) - breakMinutes) + ((endTime - startTime) / 2);
        workedPay = workedTime * wage;
        realTax = 1 - (tax / 100);
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(4);
        nf.setMinimumFractionDigits(0);
        nf.format(realTax);
        noTaxIncome = (workedPay + workedObPay);
        taxIncome = (noTaxIncome * realTax);



        Log.e("Calculation ", "Result after calculation: wage: " + wage + " StartTime: " + startTime + " EndTime " + endTime + " tax " + tax + " new tax " + realTax + " obIndex " + obIndex + " obStart " + obStart + " obEnd " + obEnd + " day " + dayOfWeek + " = " + dayName + " calculated ob " + workedObPay + " TaxIncome= " + taxIncome + "no Tax income: " + noTaxIncome);
        addShift(jobTitle, startTime, endTime, workedTime, year % 100, month + 1, day, taxIncome, noTaxIncome);
        return taxIncome;
    }

    /**
     * Get the income for the month.
     *
     * @param month select month.
     * @param year  selected year.
     * @return the monthly income as a float.
     */

    public float getMonthlyIncome(int month, int year) {
        sqLiteDatabase = dbHelper.getReadableDatabase();
        return dbHelper.getMonthlyIncome(month, year, sqLiteDatabase);
    }

    /**
     * Get the expense for the month.
     *
     * @param month select month.
     * @param year  selected year.
     * @return the monthly income as a float.
     */

    public float getMonthlyExpenses(int month, int year) {
        sqLiteDatabase = dbHelper.getReadableDatabase();
        return dbHelper.getMonthlyExpenses(month, year, sqLiteDatabase);
    }

    /**
     * Get the date.
     *
     * @param selectedMonth the selected month.
     * @param selectedYear  the selected year.
     * @return The month and year as a String.
     */

    public String getDate(int selectedMonth, int selectedYear) {
        String month = "";
        switch (selectedMonth) {
            case 1:
                month = "Jan";
                break;
            case 2:
                month = "Feb";
                break;
            case 3:
                month = "Mar";
                break;
            case 4:
                month = "Apr";
                break;
            case 5:
                month = "Maj";
                break;
            case 6:
                month = "Jun";
                break;
            case 7:
                month = "Jul";
                break;
            case 8:
                month = "Aug";
                break;
            case 9:
                month = "Sep";
                break;
            case 10:
                month = "Okt";
                break;
            case 11:
                month = "Nov";
                break;
            case 12:
                month = "Dec";
                break;
        }
        return month + "\n" + Calendar.getInstance().get(Calendar.YEAR) / 100 + "" + selectedYear;
    }

    /**
     * Listener for when user is created.
     */
    private class CreateUserListener implements UpdateTaxCallback {

        final String municipality;
        final float incomeLimit;

        /**
         * Sets parameters.
         *
         * @param municipality choosen municipality.
         * @param incomeLimit  choosen income limit.
         */

        public CreateUserListener(String municipality, float incomeLimit) {
            this.municipality = municipality;
            this.incomeLimit = incomeLimit;
        }

        /**
         * Sets the tax in the database
         *
         * @param tax the new tax rate as a float.
         */

        @Override
        public void UpdateTax(float tax) {
            sqLiteDatabase = dbHelper.getWritableDatabase();
            dbHelper.addUser(tax, incomeLimit, sqLiteDatabase, municipality);
        }
    }



    public  String[] getExpenses( int month) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
       return dbHelper.getExpenses(month, sqLiteDatabase);
    }

    public String[]  getIncomes(int month) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
      return  dbHelper.getIncomes( month , sqLiteDatabase);
    }

    public void removeExpense(int id){
        sqLiteDatabase = dbHelper.getWritableDatabase();
        dbHelper.removeExpense( id , sqLiteDatabase);
    }
    public void removeIncome(int id){
        sqLiteDatabase = dbHelper.getWritableDatabase();
        dbHelper.removeIncome( id , sqLiteDatabase);
    }
}

