package com.something.myapplication.activity.homeactivity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.appupdate.testing.FakeAppUpdateManager;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;
import com.sdsmdg.tastytoast.TastyToast;
import com.something.myapplication.BuildConfig;
import com.something.myapplication.R;
import com.something.myapplication.activity.BaseActivity.BaseActivity;
import com.something.myapplication.activity.Network.NetworkChangeListener;
import com.something.myapplication.activity.database.DBController;
import com.something.myapplication.activity.displayactivity.displayActivity;
import com.something.myapplication.activity.model.Student;
import com.something.myapplication.activity.settingsActivity.SettingsActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{

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
    AppUpdateManager appUpdateManager;
    FakeAppUpdateManager fakeappUpdateManager;
    private int APP_UPDATE_TYPE_SUPPORTED = AppUpdateType.IMMEDIATE;
    int RequestUpdate = 100;

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
        checkForUpdates();
    }
    private void checkForUpdates()
    {
        if(BuildConfig.DEBUG)
        {
            fakeappUpdateManager = new FakeAppUpdateManager(this);
            fakeappUpdateManager.setUpdateAvailable(2);
            fakeappUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
                @Override
                public void onSuccess(AppUpdateInfo result) {
                    handleFakeImmediateUpdate(fakeappUpdateManager, result);
                }
            });
        }
        else
        {
            appUpdateManager = AppUpdateManagerFactory.create(this);
            appUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
                @Override
                public void onSuccess(AppUpdateInfo result) {
                  // handleImmediateUpdate(fakeappUpdateManager, result);
                }
            });
        }

    }
    private void handleFakeImmediateUpdate(FakeAppUpdateManager fakeAppUpdate, AppUpdateInfo result)
    {
        if((result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE)
                && result.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE))
        {
            fakeAppUpdate.startUpdateFlowForResult(
                    result,
                    AppUpdateType.IMMEDIATE,
                    MainActivity.this,
                    RequestUpdate);
        }
        if(BuildConfig.DEBUG)
        {
            if(fakeAppUpdate.isImmediateFlowVisible())
            {
                fakeAppUpdate.userAcceptsUpdate();
                fakeAppUpdate.downloadStarts();
                fakeAppUpdate.downloadCompletes();
                launchRestartDialog(fakeAppUpdate);
            }
        }
    }
    private void launchRestartDialog (final FakeAppUpdateManager fakeAppUpdateManager)
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.Update_Title))
                .setMessage(getString(R.string.Update_Message))
                .setPositiveButton(R.string.Update_Button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        fakeAppUpdateManager.completeUpdate();
                    }
                });
        alertDialogBuilder.create().show();
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
