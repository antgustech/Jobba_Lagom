package com.example.i7.jobbalagom.local;

import com.example.i7.jobbalagom.localDatabase.DBHelper;

/**
 * Created by Anton on 2016-04-27.
 * Used to hold data for all classes to access more specificly the controller
 */
public class DataHolder {

    private Controller controller;
    private DBHelper dbHelper;
    private static final DataHolder holder = new DataHolder();

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
    public void setDbHelper(DBHelper dbHelper){
        this.dbHelper=dbHelper;
    }
    public DBHelper getDbHelper(){
        return dbHelper;
    }





    public static DataHolder getInstance() {
        return holder;
    }
}
