package com.something.myapplication.activity.displayactivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.something.myapplication.R;

public class StudentViewHolder extends RecyclerView.ViewHolder {

    public TextView rollno, name, course;
   // public ImageView delete;
    public ImageView edit;
    public StudentViewHolder(@NonNull View itemView) {
        super(itemView);
        rollno = (TextView)itemView.findViewById(R.id.tv_studentRollNo);
        name = (TextView)itemView.findViewById(R.id.tv_studentName);
        course = (TextView)itemView.findViewById(R.id.tv_studentCourse);
        edit = (ImageView)itemView.findViewById(R.id.s_edit);
       // delete= (ImageView)itemView.findViewById(R.id.s_delete);


    }
}
