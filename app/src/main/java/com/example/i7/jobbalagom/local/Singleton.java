package com.example.i7.jobbalagom.local;

import android.app.Application;

/**
 * Created by Anton Gustafsson on 2016-05-04.
 * Holds Controller reference
 * TODO Fix it
 */
public class Singleton extends Application {
    public static Controller controller;

    public static Controller getController() {
        return Singleton.controller;
    }

    public static void setController(Controller controller) {
        Singleton.controller = controller;
    }
}
