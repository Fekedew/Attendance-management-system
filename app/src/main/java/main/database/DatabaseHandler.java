package main.database;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class DatabaseHandler {
    Activity activity;
    private SQLiteDatabase database;

    public DatabaseHandler(Activity activity) {
        this.activity = activity;
        database = activity.openOrCreateDatabase("Feke", Context.MODE_PRIVATE, null);
        createTable();
    }

    public void createTable() {
        try {
            String qu = "CREATE TABLE IF NOT EXISTS MARK(out10one varchar(5),out10two varchar(5),out10three varchar(5)," +
                    "out60 varchar(5),out100 varchar(5), roll varchar(1000) primary key);";
            database.execSQL(qu);
        } catch (Exception e) {
            Toast.makeText(activity, "Error Occurred to create table", Toast.LENGTH_LONG).show();
        }
        try {
            String qu = "CREATE TABLE IF NOT EXISTS STUDENT(name varchar(1000)," +
                    "contact varchar(100),roll integer primary key, gender varchar(5), age integer);";
            database.execSQL(qu);
        } catch (Exception e) {
            Toast.makeText(activity, "Error Occurred to create table", Toast.LENGTH_LONG).show();
        }
        try {
            String qu = "CREATE TABLE IF NOT EXISTS ATTENDANCE(day int, month int, year int," +
                    "register varchar(1000) ,isPresent int);";
            database.execSQL(qu);
        } catch (Exception e) {
            Toast.makeText(activity, "Error Occurred to create table", Toast.LENGTH_LONG).show();
        }

        try {
            String qu = "CREATE TABLE IF NOT EXISTS user(uname varchar(100),password varchar(1000));";
            database.execSQL(qu);
        } catch (Exception e) {
            Toast.makeText(activity, "Error Occurred to create table", Toast.LENGTH_LONG).show();
        }

        try {
            String qu = "CREATE TABLE IF NOT EXISTS SUBJECT(sname varchar(100));";
            database.execSQL(qu);
        } catch (Exception e) {
            Toast.makeText(activity, "Error Occurred to create table subject", Toast.LENGTH_LONG).show();
        }
        try {
            String qu = "CREATE TABLE IF NOT EXISTS BORROWEDSUBJECT(bid integer primary key autoincrement, roll varchar(100), subid int, returned int);";
            database.execSQL(qu);
        } catch (Exception e) {
            Toast.makeText(activity, "Error Occurred to create table"+e, Toast.LENGTH_LONG).show();
        }
    }

    public boolean execAction(String qu) {
        try {
            database.execSQL(qu);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public Cursor execQuery(String qu) {
        try {
            return database.rawQuery(qu, null);
        } catch (Exception e) {
//            Toast.makeText(activity, "Error Occurred for execAction" + e, Toast.LENGTH_LONG).show();
        }
        return null;
    }
}
