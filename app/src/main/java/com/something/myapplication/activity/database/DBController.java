package com.something.myapplication.activity.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Adapter;

import androidx.annotation.Nullable;

import com.something.myapplication.activity.displayactivity.displayAdapter;
import com.something.myapplication.activity.model.Student;

import java.util.ArrayList;
import java.util.HashMap;

public class DBController extends SQLiteOpenHelper {

    //defining table structure
    private static final String tablename = "students"; // table name
    private static final String name = "name"; //  name column
    private static final String rollno = "rollno"; // roll no. column (Primary Key)
    private static final String course = "course"; // course column name
    private static final String databasename = "studentsvisit"; // Database name
    private static final int versioncode = 1; // defining versioncode of the database
    private displayAdapter mAdapter;

    public DBController(@Nullable Context context) {
        super(context, databasename, null, versioncode);
    }    //constructor for the SQLiteHelper

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {  //creating the database when the app is running for first time.
        String query;
        query = "CREATE TABLE IF NOT EXISTS " + tablename + "(" + rollno + "integer primary key," + name + " text, " + course + " text" + ")"; //
        sqLiteDatabase.execSQL(query);
    }      //creating table structure using create query

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query;
        query = "DROP TABLE IF EXISTS " + tablename;
        sqLiteDatabase.execSQL(query);
        onCreate(sqLiteDatabase);
    }     //this is function to upgrade the table and I am dropping it and then creating it again.


    public ArrayList<Student> getAllStudents() {  //function to return all the students records.
        ArrayList<Student> studentList;          //arrayList has been created for the retrieval of the data from the DB
        studentList = new ArrayList<Student>();
        String selectQuery = "SELECT * FROM " + tablename;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
//                int id = Integer.parseInt(cursor.getString(0));//
                int rollno = Integer.parseInt(cursor.getString(0));//
//                String rollno = cursor.getString(1);
                String name = cursor.getString(1);
                String course = cursor.getString(2);
                studentList.add(new Student(rollno,name,course));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return studentList;    //return the final updated list
    }


    public void addStudent(Student student){
        ContentValues values = new ContentValues();
        String rollNo = String.valueOf(student.getRollno());

        values.put(rollno,rollNo);
        values.put(name, student.getName());
        values.put(course, student.getCourse());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(tablename, null, values);
    } //function to add an entry and is being called from the MainActicity on click of button.

    public void updateStudent(Student student){
        ContentValues values = new ContentValues();
        String rollNo = String.valueOf(student.getRollno());
        values.put(rollno,rollNo);
        values.put(name, student.getName());
        values.put(course, student.getCourse());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(tablename, values, rollno	+ "	= ?", new String[] { String.valueOf(student.getRollno())});//
        //db.update(tablename, values, rollno	+ "	= ?", new String[] { String.valueOf(student.getRollno())});
    }  //function to edit an entry and is being called from the DisplayActicity on click of edit ImageView.

    public void deleteStudent(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tablename, rollno	+ "	= ?", new String[] { String.valueOf(id)});
        //db.delete(tablename, rollno	+ "	= ?", new String[] { String.valueOf(id)});
    }        //function to delete an entry and is being called from the DisplayActicity on click of delete ImageView.
}
