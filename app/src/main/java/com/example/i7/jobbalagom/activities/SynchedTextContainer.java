package com.example.i7.jobbalagom.activities;

/**
 * Created by Strandberg95 on 2016-04-23.
 */
public class SynchedTextContainer {

    private String text;

    public SynchedTextContainer(String text){
        this.text = text;
    }

    public synchronized void setText(String text){
        this.text = text;
    }

    public synchronized String getText(){
        return text;
    }

}
