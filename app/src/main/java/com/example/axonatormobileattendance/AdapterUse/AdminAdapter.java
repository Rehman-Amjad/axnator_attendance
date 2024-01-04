package com.example.axonatormobileattendance.AdapterUse;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.axonatormobileattendance.Model.TeacherAttendanceClass;
import com.example.axonatormobileattendance.R;

import java.util.List;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.MyAttendanceHolder>{

    private Context context;
    private List<TeacherAttendanceClass> mDatalist;

    public AdminAdapter(Context context, List<TeacherAttendanceClass> mDatalist) {
        this.context = context;
        this.mDatalist = mDatalist;
    }

    @NonNull
    @Override
    public MyAttendanceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myview = LayoutInflater.from(context).inflate(R.layout.teacher_atten_list,parent,false);


        return new MyAttendanceHolder(myview);
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public void onBindViewHolder(@NonNull MyAttendanceHolder holder, int position) {

        TeacherAttendanceClass data = mDatalist.get(position);

        holder.tv_att_date.setText("Date: " + data.getDate());
        holder.tv_att_tech_id.setText("Teacher ID: " + data.getTeacherId());
        holder.tv_att_time.setText("Time: " + data.getCurrentTime());
        holder.tv_att.setText("Attendance: " + data.getTeacherPresent());



    }

    @Override
    public int getItemCount() {
        return mDatalist.size();
    }

    public class MyAttendanceHolder extends RecyclerView.ViewHolder{

        TextView tv_att_date,tv_att_tech_id,tv_att_time,tv_att;

        public MyAttendanceHolder(@NonNull View itemView) {
            super(itemView);


            tv_att_date=itemView.findViewById(R.id.tv_att_date);
            tv_att_tech_id=itemView.findViewById(R.id.tv_att_tech_id);
            tv_att_time=itemView.findViewById(R.id.tv_att_time);
            tv_att=itemView.findViewById(R.id.tv_att);
        }
    }
}

