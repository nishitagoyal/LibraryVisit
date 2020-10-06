package com.something.myapplication.activity.displayactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PixelFormat;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.sdsmdg.tastytoast.TastyToast;
import com.something.myapplication.R;
import com.something.myapplication.activity.database.DBController;
import com.something.myapplication.activity.model.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class displayActivity extends AppCompatActivity implements displayAdapter.OnEditListener {

    DBController controller = new DBController(this); //getting instance of the dbController class
    private ArrayList<Student> allStudent;
    private displayAdapter mAdapter;
    RecyclerView studentView;
    EditText nameField;
    EditText courseField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (controller.getAllStudents().isEmpty())
            setContentView(R.layout.display_fallback_layout);
        else {
            setContentView(R.layout.activity_display);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            studentView = (RecyclerView) findViewById(R.id.studentsDisplayListView);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            studentView.setLayoutManager(linearLayoutManager);
            studentView.setHasFixedSize(true);
            allStudent = controller.getAllStudents();
            if (allStudent.size() > 0) {
                studentView.setVisibility(View.VISIBLE);
                mAdapter = new displayAdapter(this, allStudent, this);
                studentView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            } else {
                studentView.setVisibility(View.GONE);
                TastyToast.makeText(this, "There is no contact in the database. Start adding now", Toast.LENGTH_LONG, 2).show();
            }
            ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder target, int i) {

                    TastyToast.makeText(getApplicationContext(), "Item Deleted Successfully", Toast.LENGTH_LONG, 1).show();
                    mAdapter.delete(target.getAdapterPosition());

                }
            });
            helper.attachToRecyclerView(studentView);
        }
    }

    @Override
    public void onEditClick(int position) {
        Student student = allStudent.get(position);
        editTaskDialog(student, position);

    }


    private void editTaskDialog(final Student student, final int position) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View subView = inflater.inflate(R.layout.edit_details_layout, null);
        nameField = (EditText) subView.findViewById(R.id.enter_name);
        courseField = (EditText) subView.findViewById(R.id.enter_course);
        if(student!=null) {
            nameField.setText(student.getName());
            courseField.setText(student.getCourse());
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit contact");
        builder.setView(subView);
        builder.create();
        builder.setPositiveButton("EDIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                    String name = nameField.getText().toString();
                    String course = courseField.getText().toString();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(course)) {
                    TastyToast.makeText(getApplicationContext(), "Something went wrong. Check your input values", Toast.LENGTH_LONG, 2).show();
                    }
                else {
                        controller.updateStudent(new Student(student.getRollno(), name, course));
                        for(int i=0;i<allStudent.size();i++)
                        {
                            if(student.getRollno()==allStudent.get(i).getRollno())
                            {
                                student.setName(name);
                                student.setCourse(course);
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                }

            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
             TastyToast.makeText(getApplicationContext(), "Edit cancelled", Toast.LENGTH_LONG, 4).show();
            }
        });
        builder.show();
    }          //funtion to show edit details.


}