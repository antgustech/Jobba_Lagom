package com.example.i7.jobbalagom;

/**
 * Created by Strandberg95 on 2016-03-21.
 */
public class Calculator {
    private final float toEarn = 60000;
    private float hasEarned = 0;

    public float calculatePercentage(float earned){
        hasEarned += earned;
        return (hasEarned / toEarn);
    }

}


