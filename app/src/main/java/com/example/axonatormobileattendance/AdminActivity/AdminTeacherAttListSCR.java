package com.example.axonatormobileattendance.AdminActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.axonatormobileattendance.AdapterUse.AttendanceTeacherAdapter;
import com.example.axonatormobileattendance.Model.TeacherAttendanceClass;
import com.example.axonatormobileattendance.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AdminTeacherAttListSCR extends AppCompatActivity {

    private RecyclerView recyclerView_showAtt;
    private AttendanceTeacherAdapter mAttendanceAdapter;
    private List<TeacherAttendanceClass> mDataList;



    ImageView st_att_back;

    Button btn_search;
    EditText ed_stID,ed_stMonth;

    String value,teacherID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_teacher_att_list_s_c_r);

        initView();



        st_att_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminTeacherAttListSCR.this,AdminDashboard.class));
                finish();

            }
        });





        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                teacherID = ed_stID.getText().toString().trim();
                value  = ed_stMonth.getText().toString().trim();

                if (isValid())
                {
                    ShowMonthAttendance();
                }

            }
        });
    }

    private void initView()
    {
        btn_search=findViewById(R.id.btn_search);
        st_att_back=findViewById(R.id.st_att_back);
        ed_stID=findViewById(R.id.ed_stID);
        ed_stMonth=findViewById(R.id.ed_stMonth);
        recyclerView_showAtt=findViewById(R.id.recyclerView_showAtt);
    }

    private boolean isValid()
    {
        teacherID = ed_stID.getText().toString().trim();
        value  = ed_stMonth.getText().toString().trim();

        if (teacherID.isEmpty())
        {
            ed_stID.setError("invalid student ID");
            ed_stID.requestFocus();
            return false;
        }

        if (value.isEmpty())
        {
            ed_stMonth.setError("invalid Month plz Enter 'Jan'");
            ed_stMonth.requestFocus();
            return false;
        }

        return true;
    }

    private void ShowMonthAttendance()
    {

        DatabaseReference zonesRef = FirebaseDatabase.getInstance().getReference("CustomAttendanceTeacher");
        DatabaseReference zone1Ref = zonesRef.child(teacherID);
        DatabaseReference zon1 = zone1Ref.child(value);

        recyclerView_showAtt=findViewById(R.id.recyclerView_showAtt);
        recyclerView_showAtt.setLayoutManager(new LinearLayoutManager(this));

        mDataList=new ArrayList<>();
        mAttendanceAdapter=new AttendanceTeacherAdapter(this,mDataList);
        recyclerView_showAtt.setAdapter(mAttendanceAdapter);


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
                    Toast.makeText(AdminTeacherAttListSCR.this, "No Attendance...!", Toast.LENGTH_SHORT).show();
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