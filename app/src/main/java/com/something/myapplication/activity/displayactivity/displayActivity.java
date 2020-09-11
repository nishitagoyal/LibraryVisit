package com.something.myapplication.activity.displayactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
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

public class displayActivity extends AppCompatActivity {

    DBController controller = new DBController(this); //getting instance of the dbController class
    private ArrayList<Student> allStudent=new ArrayList<>();
    private displayAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(controller.getAllStudents().isEmpty())
        setContentView(R.layout.display_fallback_layout);
        else
        {
            setContentView(R.layout.activity_display);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            RecyclerView studentView = (RecyclerView)findViewById(R.id.studentsDisplayListView);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            studentView.setLayoutManager(linearLayoutManager);
            studentView.setHasFixedSize(true);
            allStudent = controller.getAllStudents();
            if(allStudent.size() > 0){
                studentView.setVisibility(View.VISIBLE);
                mAdapter = new displayAdapter(this, allStudent);
                studentView.setAdapter(mAdapter);

            }else {
                studentView.setVisibility(View.GONE);
                TastyToast.makeText(this, "There is no contact in the database. Start adding now", Toast.LENGTH_LONG,2).show();
            }
            ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT| ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder target, int i) {

                    TastyToast.makeText(getApplicationContext(), "Item Deleted Successfully", Toast.LENGTH_LONG,1).show();
                    mAdapter.delete(target.getAdapterPosition());

                }
            });
            helper.attachToRecyclerView(studentView);
        }
    }
}