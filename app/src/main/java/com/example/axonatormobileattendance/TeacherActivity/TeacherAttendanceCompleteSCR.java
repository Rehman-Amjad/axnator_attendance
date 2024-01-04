package com.example.axonatormobileattendance.TeacherActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.axonatormobileattendance.ConfirmScreen;
import com.example.axonatormobileattendance.Model.TeacherAttendanceClass;
import com.example.axonatormobileattendance.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TeacherAttendanceCompleteSCR extends AppCompatActivity {

    Button btn_com_dash_tech,btn_com_logout_tech;

    String date;

    ValueEventListener valueEventListener;

    DatabaseReference reference;

    String parentcounter;
    int count;

    @Override
    protected void onDestroy() {
        super.onDestroy();

        reference.removeEventListener(valueEventListener);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_attendance_complete_s_c_r);

        btn_com_dash_tech=findViewById(R.id.btn_com_dash_tech);
        btn_com_logout_tech=findViewById(R.id.btn_com_logout_tech);

        uploadAttendaceToDatabase();

        CounterCall();





        btn_com_logout_tech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadCustomAttendance();

                DatabaseReference counter = FirebaseDatabase.getInstance().getReference("Counter");

                counter.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        parentcounter = snapshot.child("value").getValue(String.class);

                        count = Integer.parseInt(parentcounter);
                        count=count+1;
                        parentcounter = String.valueOf(count);
                        counter.child("value").setValue(parentcounter);

                        Intent backIntent=new Intent(TeacherAttendanceCompleteSCR.this, ConfirmScreen.class);
                        startActivity(backIntent);
                        finish();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });


        btn_com_dash_tech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadCustomAttendance();

                DatabaseReference counter = FirebaseDatabase.getInstance().getReference("Counter");

                counter.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        parentcounter = snapshot.child("value").getValue(String.class);

                        count = Integer.parseInt(parentcounter);
                        count=count+1;
                        parentcounter = String.valueOf(count);
                        counter.child("value").setValue(parentcounter);

                        Intent dashIntent=new Intent(TeacherAttendanceCompleteSCR.this,TeacherDashboard.class);
                        startActivity(dashIntent);
                        finish();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }
        });





    }

    private void CounterCall()
    {
        DatabaseReference counter = FirebaseDatabase.getInstance().getReference("Counter");

        counter.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                parentcounter = snapshot.child("value").getValue(String.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void uploadCustomAttendance()
    {

        SharedPreferences sp=getSharedPreferences("TEACHER_DATA",MODE_PRIVATE);
        String teacherId=sp.getString("teacherID","");

        String currentMonth=getdateWithMonth();
        String currentDate = getCurrentdate();
        String currentTime= getTimeWithAmPm();
        String studentPresent="Present";

        reference= FirebaseDatabase.getInstance().getReference("CustomAttendanceTeacher");



        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TeacherAttendanceClass classData=new TeacherAttendanceClass(currentMonth,currentTime,teacherId,currentDate,studentPresent,date);
                String   key = reference.push().getKey();
                reference.child(teacherId).child(currentMonth).child(parentcounter).setValue(classData);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        reference.addValueEventListener(valueEventListener);



    }


    private String getTimeWithAmPm()
    {
        return new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
    }

    private String getCurrentdate()
    {
        return new SimpleDateFormat("dd/LLL/yyyy", Locale.getDefault()).format(new Date());
    }

    private String getCurrentdateOnly()
    {
        return new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());
    }

    private String getdateWithMonth()
    {
        return new SimpleDateFormat("LLL", Locale.getDefault()).format(new Date());
    }

    public void uploadAttendaceToDatabase()
    {


        SharedPreferences sp=getSharedPreferences("TEACHER_DATA",MODE_PRIVATE);
        String teacherId=sp.getString("teacherID","");

        String currentMonth=getdateWithMonth();
        String currentDate = getCurrentdate();
        String currentTime= getTimeWithAmPm();
        String studentPresent="Present";
        date=getdateWithMonth()+" "+ getCurrentdateOnly();


        reference= FirebaseDatabase.getInstance().getReference("attendanceTeacher");


        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                SharedPreferences sp=getSharedPreferences("STUDENT_DATA",MODE_PRIVATE);
                String name=sp.getString("currentClass","");

                TeacherAttendanceClass classData=new TeacherAttendanceClass(currentMonth,currentTime,teacherId,currentDate,studentPresent,date);
                String key_value=reference.child(currentMonth).getKey();
                reference.child("date").child(date).child(teacherId).setValue(classData);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        reference.addValueEventListener(valueEventListener);





    }
}