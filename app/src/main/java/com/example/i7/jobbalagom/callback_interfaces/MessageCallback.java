package com.example.i7.jobbalagom.callback_interfaces;

/**
 * Created by Strandberg95 on 2016-03-30.
 */
public interface MessageCallback {

    void updateMunicipality(String kommun);
    void updateCities(String cities);
    void updateTax(float tax);

}
