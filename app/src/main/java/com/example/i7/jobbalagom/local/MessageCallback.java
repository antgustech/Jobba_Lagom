package com.example.i7.jobbalagom.local;

/**
 * Created by Strandberg95 on 2016-03-30.
 */
public interface MessageCallback {

    void updateKommun(String kommun);
    void updateCities(String cities);
    void updateTax(float tax);

}
