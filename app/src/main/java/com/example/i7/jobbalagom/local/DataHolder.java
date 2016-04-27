package com.example.i7.jobbalagom.local;

/**
 * Created by Anton on 2016-04-27.
 * Used to hold data for all classes to access more specificly the controller
 */
public class DataHolder {

    private Object data;
    private static final DataHolder holder = new DataHolder();

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static DataHolder getInstance() {
        return holder;
    }
}
