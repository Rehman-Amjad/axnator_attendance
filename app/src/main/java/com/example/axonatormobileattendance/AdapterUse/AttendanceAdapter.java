package com.example.axonatormobileattendance.AdapterUse;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.axonatormobileattendance.Model.StudentAttendaceClass;
import com.example.axonatormobileattendance.R;

import java.util.List;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.MyAttendanceHolder>{

    private final Context context;
    private final List<StudentAttendaceClass> mDataList;

    public AttendanceAdapter(Context context, List<StudentAttendaceClass> mDataList) {
        this.context = context;
        this.mDataList = mDataList;
    }

    @NonNull
    @Override
    public MyAttendanceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View myview= LayoutInflater.from(context).inflate(R.layout.attendance_list,parent,false);


        return new MyAttendanceHolder(myview);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyAttendanceHolder holder, int position) {


        StudentAttendaceClass classData=mDataList.get(position);

        holder.tv_att_date.setText("Date: " + classData.getDate());
        holder.tv_att_st_id.setText("Student ID: " + classData.getStudentId());
        holder.tv_att_time.setText("Time: " + classData.getCurrentTime());
        holder.tv_att.setText("Attendance: " + classData.getStudentPresent());
        holder.tv_att_sub.setText("Class: " + classData.getStudentSubject());


    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public static class MyAttendanceHolder extends RecyclerView.ViewHolder{

        TextView tv_att_date,tv_att_st_id,tv_att_time,tv_att,tv_att_sub;

        public MyAttendanceHolder(@NonNull View itemView) {
            super(itemView);

            tv_att_date=itemView.findViewById(R.id.tv_att_date);
            tv_att_st_id=itemView.findViewById(R.id.tv_att_st_id);
            tv_att_time=itemView.findViewById(R.id.tv_att_time);
            tv_att=itemView.findViewById(R.id.tv_att);
            tv_att_sub=itemView.findViewById(R.id.tv_att_sub);


        }
    }

}


