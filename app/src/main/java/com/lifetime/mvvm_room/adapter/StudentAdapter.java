package com.lifetime.mvvm_room.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lifetime.mvvm_room.R;
import com.lifetime.mvvm_room.activity.UpdateStudentActivity;
import com.lifetime.mvvm_room.model.Student;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder>{

    private Context mCtx;
    private List<Student> studentList;

    public StudentAdapter(Context mCtx){
        this.mCtx = mCtx;
    }

    public StudentAdapter(Context mCtx, List<Student> studentList){
        this.mCtx = mCtx;
        this.studentList = studentList;
    }

    @NonNull
    @Override
    public StudentAdapter.StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_students,parent,false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.StudentViewHolder holder, int position) {
        holder.bindView(studentList.get(position));
    }

    @Override
    public int getItemCount() {
        return studentList != null ? studentList.size() : 0;
    }

    public void setStudents(List<Student> studentList){
        this.studentList = studentList;
        notifyDataSetChanged();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewStatus, textViewName, textViewAddress, textViewSubjects, textViewBirth, textViewGender;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewStatus = itemView.findViewById(R.id.textViewStatus);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewAddress = itemView.findViewById(R.id.textViewAddress);
            textViewSubjects = itemView.findViewById(R.id.textViewSubject);
            textViewBirth = itemView.findViewById(R.id.textViewBirth);
            textViewGender = itemView.findViewById(R.id.textViewGender);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Student student = studentList.get(getAdapterPosition());

            Intent intent = new Intent(mCtx, UpdateStudentActivity.class);
            intent.putExtra("student",student);

            mCtx.startActivity(intent);
        }

        public void bindView(Student student){
            textViewName.setText(student.getName());
            textViewAddress.setText(student.getAddress());
            textViewSubjects.setText(student.getSubject());
            textViewSubjects.setText(student.getSubject());

        }
    }
}
