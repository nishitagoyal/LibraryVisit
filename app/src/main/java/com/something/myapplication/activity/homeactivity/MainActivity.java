package com.something.myapplication.activity.homeactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sdsmdg.tastytoast.TastyToast;
import com.something.myapplication.R;
import com.something.myapplication.activity.database.DBController;
import com.something.myapplication.activity.model.Student;

public class MainActivity extends AppCompatActivity {

    DBController controller = new DBController(this);
    Button add,view;
    EditText etRollNo,etName,etCourse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etRollNo = findViewById(R.id.s_rollno);
        etName = findViewById(R.id.s_name);
        etCourse = findViewById(R.id.s_course);
        add = findViewById(R.id.s_addButton);
        view = findViewById(R.id.s_viewButton);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, com.something.myapplication.activity.displayactivity.displayActivity.class);
                startActivity(i);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etRollNo.getText().toString().trim().equals("") || etName.getText().toString().trim().equals("") || etCourse.getText().toString().trim().equals("")) {
                    TastyToast.makeText(getApplicationContext(),"Please fill all the details.",TastyToast.LENGTH_LONG,4).show();
                } else {
                    int rollno = Integer.parseInt(etRollNo.getText().toString());
                    String name = etName.getText().toString();
                    String course = etCourse.getText().toString();
                    Student student = new Student(rollno,name,course);
                    controller.addStudent(student);
                    TastyToast.makeText(getApplicationContext(),"Details Added Successfully",TastyToast.LENGTH_LONG,TastyToast.SUCCESS).show();
                    etName.setText("");
                    etCourse.setText("");
                    etRollNo.setText("");

                }
            }
        });
    }


}