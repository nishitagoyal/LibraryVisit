package com.something.myapplication.activity.displayactivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.sdsmdg.tastytoast.TastyToast;
import com.something.myapplication.R;
import com.something.myapplication.activity.database.DBController;
import com.something.myapplication.activity.homeactivity.MainActivity;
import com.something.myapplication.activity.model.Student;

import java.util.ArrayList;     //import files

public class displayAdapter extends RecyclerView.Adapter<displayAdapter.StudentViewHolder> {
    private Context context;
    private OnEditListener mOnEditListener;
    private ArrayList<Student> students;
    private ArrayList<Student> mArrayList;
    private DBController mDatabase;

    public displayAdapter(Context context, ArrayList<Student> students, OnEditListener onEditListener) {
        this.context = context;
        this.students = students;
        this.mOnEditListener = onEditListener;
        this.mArrayList = students;
        mDatabase = new DBController(context);

    } //constructor for the adapter

    @NonNull
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.displayrow_layout, parent, false);
        return new StudentViewHolder(view);
    }  //this helps in inflating the view designed for a single row to the recycler view.

    @Override
    public void onBindViewHolder(@NonNull final StudentViewHolder holder, final int position) {
        final Student student = students.get(position);
        String rollNo = String.valueOf(student.getRollno());
        holder.rollno.setText(rollNo);
        holder.name.setText(student.getName());
        holder.course.setText(student.getCourse());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnEditListener.onEditClick(holder.getAdapterPosition());
            }
        });

//        holder.edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                editTaskDialog(student,position);
//            }
//        });
    }       //this helps in binding the data to the above inflated view

    @Override
    public int getItemCount() {
        return students.size();
    } //returning list size

//    private void editTaskDialog(final Student student, final int position) {
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View subView = inflater.inflate(R.layout.edit_details_layout, null);
//        nameField = (EditText) subView.findViewById(R.id.enter_name);
//        courseField = (EditText) subView.findViewById(R.id.enter_course);
//        if (student != null) {
//            String rollNo = String.valueOf(student.getRollno());
//            nameField.setText(student.getName());
//            courseField.setText(student.getCourse());
//        }
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle("Edit contact");
//        builder.setView(subView);
//        builder.create();
//
//        builder.setPositiveButton("EDIT", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//                    final String name = nameField.getText().toString();
//                    final String course = courseField.getText().toString();
//
//                    if (TextUtils.isEmpty(name) || TextUtils.isEmpty(course)) {
//                       TastyToast.makeText(context, "Something went wrong. Check your input values", Toast.LENGTH_LONG, 2).show();
//                    }
//                else {
//                        mDatabase.updateStudent(new Student(student.getRollno(), name, course));
//                        ((Activity) context).recreate();
//                  }
//
//            }
//        });
//
//        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                TastyToast.makeText(context, "Edit cancelled", Toast.LENGTH_LONG, 4).show();
//            }
//        });
//        builder.show();
//    }          //funtion to show edit details.

    public void delete(int pos) {
        Student student = students.get(pos);
        int id = student.getRollno();
        //int id = student.getRollno();
        DBController db = new DBController(context);
        students.remove(pos);
        db.deleteStudent(id);
        this.notifyItemRemoved(pos);
    }  //delete using swipe

    public class StudentViewHolder extends RecyclerView.ViewHolder {

        public TextView rollno, name, course;

        public ImageView edit;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            rollno = (TextView) itemView.findViewById(R.id.tv_studentRollNo);
            name = (TextView) itemView.findViewById(R.id.tv_studentName);
            course = (TextView) itemView.findViewById(R.id.tv_studentCourse);
            edit = (ImageView) itemView.findViewById(R.id.s_edit);

        }

}
    public interface OnEditListener {
        void onEditClick(int position);
    }

}
