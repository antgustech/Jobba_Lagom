package com.example.i7.jobbalagom.localDatabase;

import android.util.Log;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Strandberg95 on 2016-05-01.
 * EXPERIMENTAL CLASS JUST FOR TESTING SOME THINGS
 */
public class SQLiteConnection {

    private static Connection con;
    private static boolean hasData = false;


    public ResultSet displayUsers() throws SQLException, ClassNotFoundException{
        Log.d("DBTAG","Trying to display users...");
        if(con == null){
            getConnection();
        }

        Statement state = con.createStatement();
        ResultSet res = state.executeQuery("SELECT name,tax FROM user");
        return res;
    }

    private void getConnection()throws SQLException, ClassNotFoundException{
        Class.forName("org.sqlite.JDBC");
        con = DriverManager.getConnection("jdbc:sqlite:JobbaLagom.db");
        initialise();
    }

    private void initialise() throws SQLException{
        Log.d("DBTAG","Setting up database...");
        if(!hasData){
            hasData = true;
            Statement state = con.createStatement();
            ResultSet res = state.executeQuery("SELECT name FROM sqlite master WHERE typ = 'table' AND name = 'user'");

            if(!res.next()){
                Log.d("DBTAG","Building user table with prepoluted values");
                //need to build a table
                Statement state2 = con.createStatement();
                state2.execute("CREATE TABLE user(namn varchar(45) primary key,tax FLOAT, earned integer");
                Log.d("DBTAG", "Creating table");
                //insert some data
                PreparedStatement prep = con.prepareStatement("INSERT INTO user values(?,?,?");
                prep.setString(1,"Christoffer Strandberg");
                prep.setFloat(2, 32.2f);
                prep.setInt(3, 15000);
                prep.execute();
            }
        }
    }
}
