package com.something.myapplication.activity.homeactivity;


import androidx.appcompat.app.AppCompatActivity;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sdsmdg.tastytoast.TastyToast;
import com.something.myapplication.R;
import com.something.myapplication.activity.Network.NetworkChangeListener;
import com.something.myapplication.activity.Network.NetworkUtils;
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
    boolean connected = false;
    private BroadcastReceiver MyReceiver = null;

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
        MyReceiver= new NetworkChangeListener();
        broadcastIntent();



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateFields())
                {
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
    public void broadcastIntent() {
        registerReceiver(MyReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }
    private boolean validateFields() {
        int etRollNoDesiredLength = 3;
        int etNameDesiredLength = 20;
        int etCourseDesiredLength = 10;

        if (etRollNo.getText().length() > etRollNoDesiredLength) {
            etRollNo.setError("Enter first 3 digits only");
            return false;
        } else if (etName.getText().length() > etNameDesiredLength) {
            etName.setError("Max Length Allowed is 10");
            return false;
        } else if (etCourse.getText().length() > etCourseDesiredLength) {
            etCourse.setError("Max Length Allowed is 5");
            return false;
        }else if (etRollNo.getText().length()==0 || etName.getText().length() ==0 || etCourse.getText().length() ==0) {
            etRollNo.setError("This field cannot be empty");
            etName.setError("This field cannot be empty");
            etCourse.setError("This field cannot be empty");
            return false;
        }  else if (etName.getText().toString().trim().equalsIgnoreCase("") || etCourse.getText().toString().trim().equalsIgnoreCase("")) {
            etName.setError("Enter Valid Name");
            etCourse.setError("Enter Valid Course");
            return false;
        }
        else {
            return true;
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(MyReceiver);
    }
    }
