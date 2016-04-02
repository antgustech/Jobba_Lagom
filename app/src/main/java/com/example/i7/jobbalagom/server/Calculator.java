package com.example.i7.jobbalagom.server;

/**
 * Created by Strandberg95 on 2016-03-21.
 */
public class Calculator {

    private TaxContainer taxContainer;

    public Calculator(){

        taxContainer = new TaxContainer();
    }

    public void setTax(float tax){
        taxContainer.setTax(tax);
    }

    public float getTax(){
        return taxContainer.getTax();
    }
}


