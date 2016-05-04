package com.example.i7.jobbalagom.local;

import android.app.Application;

/**
 * Created by Anton Gustafsson on 2016-05-04.
 */
public class Singleton extends Application {
    private String data;
    private static Controller controller;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public static Controller getController() {
        return Singleton.controller;
    }

    public static void setController(Controller controller) {
        Singleton.controller = controller;
    }
}
