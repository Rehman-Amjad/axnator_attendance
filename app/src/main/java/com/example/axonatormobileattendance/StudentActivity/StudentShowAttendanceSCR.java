package com.example.axonatormobileattendance.StudentActivity;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.axonatormobileattendance.AdapterUse.AttendanceAdapter;
import com.example.axonatormobileattendance.Model.StudentAttendaceClass;
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

public class StudentShowAttendanceSCR extends AppCompatActivity {

    private RecyclerView recyclerView_showAtt;
    private AttendanceAdapter mAttendanceAdapter;
    private List<StudentAttendaceClass> mDataList;

    Spinner ed_search_attendance;
    Button btn_search;

    FirebaseDatabase database;
    DatabaseReference myRef;

    ImageView st_att_back;

    String date;
    String month;
    String value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_show_attendance_s_c_r);

        st_att_back=findViewById(R.id.st_att_back);
        ed_search_attendance=findViewById(R.id.ed_search_attendance);
        btn_search=findViewById(R.id.btn_search);


        st_att_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent= new Intent(StudentShowAttendanceSCR.this,StudentDashboard.class);
                startActivity(backIntent);
                finish();
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,R.array.MonthArray, android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ed_search_attendance.setAdapter(adapter);



        date=getdateWithMonth()+" "+ getCurrentdateOnly();

        // ShowAttendace();


        ed_search_attendance.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    }

    private void ShowMonthAttendance()
    {

        SharedPreferences sp=getSharedPreferences("STUDENT_DATA",MODE_PRIVATE);
        String studentId= sp.getString("studentID","").toString();

        DatabaseReference zonesRef = FirebaseDatabase.getInstance().getReference("CustomAttendanceStudent");
        DatabaseReference zone1Ref = zonesRef.child(studentId);
        DatabaseReference zon1 = zone1Ref.child(value);

        recyclerView_showAtt=findViewById(R.id.recyclerView_showAtt);
        //   database = FirebaseDatabase.getInstance();
        //   myRef = database.getReference("attendanceStudent");
        recyclerView_showAtt.setHasFixedSize(true);
        recyclerView_showAtt.setLayoutManager(new LinearLayoutManager(this));


        mDataList=new ArrayList<>();
        mAttendanceAdapter=new AttendanceAdapter(this,mDataList);
        recyclerView_showAtt.setAdapter(mAttendanceAdapter);


        zon1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                if (snapshot.exists())
                {
                    StudentAttendaceClass datalcass = snapshot.getValue(StudentAttendaceClass.class);

                    mDataList.add(datalcass);
                    mAttendanceAdapter.notifyDataSetChanged();
                }
                else
                {
                    Toast.makeText(StudentShowAttendanceSCR.this, "No Attendance...!", Toast.LENGTH_SHORT).show();
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

    private void ShowAttendace()
    {
        SharedPreferences sp=getSharedPreferences("STUDENT_DATA",MODE_PRIVATE);
        String studentId= sp.getString("studentID","").toString();

        DatabaseReference zonesRef = FirebaseDatabase.getInstance().getReference("CustomAttendanceStudent");
        DatabaseReference zone1Ref = zonesRef.child("date");
        DatabaseReference zon1 = zone1Ref.child(date);
        DatabaseReference zone1NameRef = zon1.child("studentId");
        DatabaseReference zon = zone1NameRef.child("studentPresent");

        recyclerView_showAtt=findViewById(R.id.recyclerView_showAtt);
        //   database = FirebaseDatabase.getInstance();
        //   myRef = database.getReference("attendanceStudent");
        recyclerView_showAtt.setHasFixedSize(true);
        recyclerView_showAtt.setLayoutManager(new LinearLayoutManager(this));


        mDataList=new ArrayList<>();
        mAttendanceAdapter=new AttendanceAdapter(this,mDataList);
        recyclerView_showAtt.setAdapter(mAttendanceAdapter);



        zon1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                if (snapshot.exists())
                {
                    String checkStID=snapshot.child(studentId).child("studentId").getValue(String.class);
                    if (checkStID.equals(studentId))

                    {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren())
                        {

                            StudentAttendaceClass datalcass = dataSnapshot.getValue(StudentAttendaceClass.class);

                            if (datalcass.getStudentId().equals(studentId))
                            {
                                mDataList.add(datalcass);
                            }

                        }
                        mAttendanceAdapter.notifyDataSetChanged();
                    }
                    else
                    {
                        Toast.makeText(StudentShowAttendanceSCR.this, "No today attendance", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(StudentShowAttendanceSCR.this, "Please Take Attendance", Toast.LENGTH_SHORT).show();
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