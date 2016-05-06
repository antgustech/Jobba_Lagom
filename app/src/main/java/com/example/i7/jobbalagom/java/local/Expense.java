package com.example.i7.jobbalagom.java.local;

/**
 * Created by Jakup on 2016-04-11.
 */
public class Expense {
    private String date, title, sum;

    public Expense(String inDate, String inTitle, String inSum){
        date = inDate;
        title = inTitle;
        sum = inSum;
    }

    public String getDate(){
        return date;
    }

    public String getTitle (){
        return title;
    }

    public String getSum (){
        return sum;
    }

    public void setDate (String inDate){
        date = inDate;
    }

    public void setTitle (String inTitle){
        title = inTitle;
    }

    public void setSum (String inSum){
        sum = inSum;
    }

    public double getSumAsDouble(){
        return Double.parseDouble(getSum());
    }
}
