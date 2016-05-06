package com.example.i7.jobbalagom.java.local;

import android.app.Application;

import com.example.i7.jobbalagom.java.localDatabase.DBHelper;

/**
 * Created by Anton Gustafsson on 2016-05-04.
 * Holds Controller reference
 * TODO Fix it
 */
public class Singleton extends Application {
    public static Controller controller;
    public static DBHelper dbhelper;

    public static void setController(Controller controller) {
        Singleton.controller = controller;
    }

    public static void setDBHelper(DBHelper helper){
        Singleton.dbhelper = helper;
    }
}
