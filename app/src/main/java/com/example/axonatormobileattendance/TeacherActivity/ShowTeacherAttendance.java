package com.example.axonatormobileattendance.TeacherActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.axonatormobileattendance.AdapterUse.AttendanceTeacherAdapter;
import com.example.axonatormobileattendance.Model.TeacherAttendanceClass;
import com.example.axonatormobileattendance.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ShowTeacherAttendance extends AppCompatActivity {

    private RecyclerView recyclerView_showtech;
    private AttendanceTeacherAdapter mAttendanceAdapter;
    private List<TeacherAttendanceClass> mDataList;


    Spinner search_attendance;

    ImageView tech_att_back;
    String date;

    String month;
    String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_teacher_attendance);

        tech_att_back=findViewById(R.id.tech_att_back);

        search_attendance=findViewById(R.id.search_attendance);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,R.array.MonthArray, android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        search_attendance.setAdapter(adapter);




        date=getdateWithMonth()+" "+ getCurrentdateOnly();

        ShowCurrentAttendace();




        search_attendance.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                value = parent.getItemAtPosition(position).toString();

                Toast.makeText(parent.getContext(), value, Toast.LENGTH_SHORT).show();

                ShowMonthAttendance();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tech_att_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent backIntent= new Intent(ShowTeacherAttendance.this,TeacherDashboard.class);
                startActivity(backIntent);
                finish();
            }
        });


    }

    private void ShowMonthAttendance()
    {
        SharedPreferences sp=getSharedPreferences("TEACHER_DATA",MODE_PRIVATE);
        String teacherId= sp.getString("teacherID","").toString();


        DatabaseReference zonesRef = FirebaseDatabase.getInstance().getReference("CustomAttendanceTeacher");
        DatabaseReference zone1Ref = zonesRef.child(teacherId);
        DatabaseReference zon1 = zone1Ref.child(value);

        recyclerView_showtech=findViewById(R.id.recyclerView_showtech);
        //   database = FirebaseDatabase.getInstance();
        //   myRef = database.getReference("attendanceStudent");
        recyclerView_showtech.setLayoutManager(new LinearLayoutManager(this));

        mDataList=new ArrayList<>();
        mAttendanceAdapter=new AttendanceTeacherAdapter(this,mDataList);
        recyclerView_showtech.setAdapter(mAttendanceAdapter);


        zon1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                if (snapshot.exists())
                {
                    TeacherAttendanceClass datalcass = snapshot.getValue(TeacherAttendanceClass.class);

                    mDataList.add(datalcass);
                    mAttendanceAdapter.notifyDataSetChanged();
                }
                else
                {
                    Toast.makeText(ShowTeacherAttendance.this, "No Attendance...!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void ShowCurrentAttendace()
    {


        SharedPreferences sp=getSharedPreferences("TEACHER_DATA",MODE_PRIVATE);
        String teacherId= sp.getString("teacherID","").toString();



        DatabaseReference zonesRef = FirebaseDatabase.getInstance().getReference("attendanceTeacher");
        DatabaseReference zone1Ref = zonesRef.child("date");
        DatabaseReference zon1 = zone1Ref.child(date);
        DatabaseReference zone1NameRef = zon1.child("teacherId");
        DatabaseReference zon = zone1NameRef.child("teacherPresent");


        recyclerView_showtech=findViewById(R.id.recyclerView_showtech);
        //   database = FirebaseDatabase.getInstance();
        //   myRef = database.getReference("attendanceStudent");
        recyclerView_showtech.setLayoutManager(new LinearLayoutManager(this));

        mDataList=new ArrayList<>();
        mAttendanceAdapter=new AttendanceTeacherAdapter(this,mDataList);
        recyclerView_showtech.setAdapter(mAttendanceAdapter);

        zon1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                if (snapshot.exists())
                {
                    String checkStID=snapshot.child(teacherId).child("teacherId").getValue(String.class);
                    if (checkStID.equals(teacherId))

                    {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren())
                        {

                            TeacherAttendanceClass datalcass = dataSnapshot.getValue(TeacherAttendanceClass.class);

                            if (datalcass.getTeacherId().equals(teacherId))
                            {
                                mDataList.add(datalcass);
                            }

                        }
                        mAttendanceAdapter.notifyDataSetChanged();
                    }
                    else
                    {
                        Toast.makeText(ShowTeacherAttendance.this, "No today attendance", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(ShowTeacherAttendance.this, "Please Take Attendance First", Toast.LENGTH_SHORT).show();
                }





            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private String getdateWithMonth()
    {
        return new SimpleDateFormat("LLL", Locale.getDefault()).format(new Date());
    }

    private String getCurrentdateOnly()
    {
        return new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());
    }
}