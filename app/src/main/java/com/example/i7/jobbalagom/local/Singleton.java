package com.example.i7.jobbalagom.local;

import android.app.Application;

/**
 * Created by Anton, Christoffer, Jakup, Kajsa and Morgan.
 * Holds Controller reference
 */
public class Singleton extends Application {
    public static Controller controller;

    /**
     * Sets the controller variable.
     * @param controller the controller reference.
     */

    public static void setController(Controller controller) {
        Singleton.controller = controller;
    }
}
