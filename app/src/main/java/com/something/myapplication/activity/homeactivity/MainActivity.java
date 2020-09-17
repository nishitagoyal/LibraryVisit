package com.something.myapplication.activity.homeactivity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;


import android.content.BroadcastReceiver;
import android.content.Intent;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;
import com.sdsmdg.tastytoast.TastyToast;
import com.something.myapplication.R;
import com.something.myapplication.activity.Network.NetworkChangeListener;
import com.something.myapplication.activity.database.DBController;
import com.something.myapplication.activity.displayactivity.displayActivity;
import com.something.myapplication.activity.model.Student;
import com.something.myapplication.activity.settingsActivity.MyPrefFrag;
import com.something.myapplication.activity.settingsActivity.SettingsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
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
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        dl = (DrawerLayout)findViewById(R.id.navigation_drawer);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);
        dl.addDrawerListener(t);
        t.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nv = (NavigationView)findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(this);
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
        }else if (etRollNo.getText().length()==0 || etName.getText().length() ==0) {
            etRollNo.setError("This field cannot be empty");
            return false;
        }  else if (etCourse.getText().length()==0) {
            etCourse.setError("This field cannot be empty");
            return false;
        } else if (etName.getText().length()==0) {
            etName.setError("This field cannot be empty");
            return false;
        } else if (etName.getText().toString().trim().equalsIgnoreCase("")) {
            etCourse.setError("Enter Valid Course");
            return false;
        } else if (etName.getText().toString().trim().equalsIgnoreCase("") ) {
            etName.setError("Enter Valid Name");
            return false;
        } else {
            return true;
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return t.onOptionsItemSelected(item)|| super.onOptionsItemSelected(item);
    }
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(MyReceiver);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
                switch(id)
                {
                    case R.id.settings:
                       startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                        Toast.makeText(MainActivity.this, "Settings",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        return true;
                }
                return true;
    }
}
