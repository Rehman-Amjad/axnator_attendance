package com.example.axonatormobileattendance.AdapterUse;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.axonatormobileattendance.Model.TeacherAttendanceClass;
import com.example.axonatormobileattendance.R;

import java.util.List;

public class AttendanceTeacherAdapter extends RecyclerView.Adapter<AttendanceTeacherAdapter.MyTeacherHolder>{


    public final Context context;
    public final List<TeacherAttendanceClass> mDataList;

    public AttendanceTeacherAdapter(Context context, List<TeacherAttendanceClass> mDataList) {
        this.context = context;
        this.mDataList = mDataList;
    }

    @NonNull
    @Override
    public MyTeacherHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myview= LayoutInflater.from(context).inflate(R.layout.teacher_atten_list,parent,false);


        return new MyTeacherHolder(myview);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyTeacherHolder holder, int position) {

        TeacherAttendanceClass dataclass=mDataList.get(position);

        holder.tv_att_date.setText("Date: " + dataclass.getDate());
        holder.tv_att_tech_id.setText("Teacher ID: " + dataclass.getTeacherId());
        holder.tv_att_time.setText("Time: " + dataclass.getCurrentTime());
        holder.tv_att.setText("Attendance: " + dataclass.getTeacherPresent());

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public class MyTeacherHolder extends RecyclerView.ViewHolder{

        TextView tv_att_date,tv_att_tech_id,tv_att_time,tv_att;


        public MyTeacherHolder(@NonNull View itemView) {
            super(itemView);

            tv_att_date=itemView.findViewById(R.id.tv_att_date);
            tv_att_tech_id=itemView.findViewById(R.id.tv_att_tech_id);
            tv_att_time=itemView.findViewById(R.id.tv_att_time);
            tv_att=itemView.findViewById(R.id.tv_att);
        }
    }

}


