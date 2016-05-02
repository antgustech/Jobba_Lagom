package com.example.i7.jobbalagom.localDatabase;

import android.util.Log;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Strandberg95 on 2016-05-01.
 */
public class App {
    private SQLiteConnection connection;

    public App(){
        Log.d("aylmao","fixing database");
        setUpDb();
    }

    void setUpDb(){
        try{
            connection = new SQLiteConnection();
            ResultSet resultSet;

            resultSet = connection.displayUsers();

            while(resultSet.next()){
                Log.d("DBTAG",resultSet.getString("name") + " " + resultSet.getFloat("tax"));
            }
        }catch(SQLException e){
            Log.d("DBTAG","Something went wrong");
        }
        catch(ClassNotFoundException e2){}

    }
}
