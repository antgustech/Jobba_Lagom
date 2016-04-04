package com.example.i7.jobbalagom.remote;

/**
 * Created by Strandberg95 on 2016-04-01.
 */
public class TaxContainer {
    private float tax = 0.2f;

    public synchronized void setTax(float tax){
        this.tax = tax;
    }

    public synchronized float getTax(){
        tax++;

        return tax;
    }

}
