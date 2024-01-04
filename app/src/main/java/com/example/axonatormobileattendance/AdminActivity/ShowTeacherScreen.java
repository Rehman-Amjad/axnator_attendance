package com.example.axonatormobileattendance.AdminActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.axonatormobileattendance.AdapterUse.TeacherAdapter;
import com.example.axonatormobileattendance.Model.AddClassData;
import com.example.axonatormobileattendance.Model.TeacherClassData;
import com.example.axonatormobileattendance.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ShowTeacherScreen extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TeacherAdapter mTeacherAdapter;
    private List<AddClassData> mDatalist;


    ImageView admin_teacherlist_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_teacher_screen);

        admin_teacherlist_back=findViewById(R.id.admin_teacherlist_back);



        admin_teacherlist_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent= new Intent(ShowTeacherScreen.this,AdminDashboard.class);
                startActivity(backIntent);
                finish();
            }
        });

        mDatalist=new ArrayList<>();
        recyclerView=findViewById(R.id.recyclerView_teacher);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerOptions<TeacherClassData> options =
                new FirebaseRecyclerOptions.Builder<TeacherClassData>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("teacherData"),TeacherClassData.class)
                        .build();

        mTeacherAdapter=new TeacherAdapter(options);
        recyclerView.setAdapter(mTeacherAdapter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        mTeacherAdapter.startListening();
    }


    @Override
    protected void onStop() {
        super.onStop();
        mTeacherAdapter.stopListening();
    }
}