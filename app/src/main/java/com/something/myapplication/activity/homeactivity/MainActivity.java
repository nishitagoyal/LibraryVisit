package com.something.myapplication.activity.homeactivity;


import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sdsmdg.tastytoast.TastyToast;
import com.something.myapplication.R;
import com.something.myapplication.activity.database.DBController;
import com.something.myapplication.activity.displayactivity.displayActivity;
import com.something.myapplication.activity.model.Student;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    DBController controller = new DBController(this);
    @BindView(R.id.s_rollno)EditText etRollNo;
    @BindView(R.id.s_name)EditText etName;
    @BindView(R.id.s_course)EditText etCourse;
    Button add,view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        add = findViewById(R.id.s_addButton);
        view = findViewById(R.id.s_viewButton);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, displayActivity.class);
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
//            @OnClick(R.id.s_viewButton)
//        public void ViewList()
//        {
//            Intent i = new Intent(MainActivity.this, displayActivity.class);
//            startActivity(i);
//        }
//        @OnClick(R.id.s_addButton)
//        public void AddStudent()
//        {
//            if (etRollNo.getText().toString().trim().equals("") || etName.getText().toString().trim().equals("") || etCourse.getText().toString().trim().equals("")) {
//                TastyToast.makeText(getApplicationContext(),"Please fill all the details.",TastyToast.LENGTH_LONG,4).show();
//            } else {
//                int rollno = Integer.parseInt(etRollNo.getText().toString());
//                String name = etName.getText().toString();
//                String course = etCourse.getText().toString();
//                Student student = new Student(rollno,name,course);
//                controller.addStudent(student);
//                TastyToast.makeText(getApplicationContext(),"Details Added Successfully",TastyToast.LENGTH_LONG,TastyToast.SUCCESS).show();
//                etName.setText("");
//                etCourse.setText("");
//                etRollNo.setText("");
//
//            }
//        }

}