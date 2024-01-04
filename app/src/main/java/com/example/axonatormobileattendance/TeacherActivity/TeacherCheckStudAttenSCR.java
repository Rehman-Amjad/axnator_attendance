package com.example.axonatormobileattendance.TeacherActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.axonatormobileattendance.AdapterUse.AttendanceAdapter;
import com.example.axonatormobileattendance.Model.StudentAttendaceClass;
import com.example.axonatormobileattendance.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class TeacherCheckStudAttenSCR extends AppCompatActivity {

    EditText ed_stID,ed_stMonth;
    Button btn_search;
    private RecyclerView recyclerView_showAtt;
    private AttendanceAdapter mAttendanceAdapter;
    private List<StudentAttendaceClass> mDataList;

    String value, studentId;

    ImageView st_att_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_check_stud_atten_s_c_r);

        initView();

        st_att_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TeacherCheckStudAttenSCR.this, TeacherDashboard.class));
                finish();
            }
        });


        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                studentId = ed_stID.getText().toString().trim();
                value  = ed_stMonth.getText().toString().trim();

                if (isValid())
                {
                    ShowMonthAttendance();
                }

            }
        });
    }

    private boolean isValid()
    {

        studentId = ed_stID.getText().toString().trim();
        value  = ed_stMonth.getText().toString().trim();

        if (TextUtils.isEmpty(studentId))
        {
            ed_stID.setError("invalid student ID");
            ed_stID.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(value))
        {
            ed_stMonth.setError("invalid Month plz Enter 'Jan'");
            ed_stMonth.requestFocus();
            return false;
        }

        return true;
    }

    private void initView()
    {
        ed_stID = findViewById(R.id.ed_stID);
        ed_stMonth = findViewById(R.id.ed_stMonth);
        btn_search = findViewById(R.id.btn_search);
        recyclerView_showAtt = findViewById(R.id.recyclerView_showAtt);
        st_att_back = findViewById(R.id.st_att_back);
    }


    private void ShowMonthAttendance()
    {



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
                    Toast.makeText(TeacherCheckStudAttenSCR.this, "No Attendance...!", Toast.LENGTH_SHORT).show();
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
}