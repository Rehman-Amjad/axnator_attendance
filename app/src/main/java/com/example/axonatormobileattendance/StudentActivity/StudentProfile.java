package com.example.axonatormobileattendance.StudentActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.axonatormobileattendance.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;

public class StudentProfile extends AppCompatActivity {

    String studimage;
    public Bitmap prf_bitmap;

    TextView tv_student_name,tv_student_Id,tv_student_DOB,tv_student_Qual,tv_student_pass;

    ImageView img_prof_stu,stdAtt_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        tv_student_name=findViewById(R.id.tv_student_name);
        tv_student_Id=findViewById(R.id.tv_student_Id);
        tv_student_DOB=findViewById(R.id.tv_student_DOB);
        tv_student_Qual=findViewById(R.id.tv_student_Qual);
        tv_student_pass=findViewById(R.id.tv_student_pass);
        stdAtt_back=findViewById(R.id.stdAtt_back);

        img_prof_stu=findViewById(R.id.img_prof_stu);



        stdAtt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentProfile.this,StudentDashboard.class));
                finish();
            }
        });



        SharedPreferences sp=getSharedPreferences("STUDENT_DATA", Context.MODE_PRIVATE);

        tv_student_name.setText(sp.getString("studentName",""));
        tv_student_Id.setText(sp.getString("studentID",""));
        tv_student_DOB.setText(sp.getString("studentDOB",""));
        tv_student_Qual.setText(sp.getString("studentQualification",""));
        tv_student_pass.setText(sp.getString("studentPassword",""));

        retrieveimage();
    }
    private void retrieveimage()
    {
        SharedPreferences sp=getSharedPreferences("STUDENT_DATA",Context.MODE_PRIVATE);

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("studentImage");
        String studentId= sp.getString("studentID","");

        //listner for retrive image from database
        reference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if (snapshot.exists())
                {
                    studimage = snapshot.child(studentId).child("newImage").getValue(String.class);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    byte[] bytes = byteArrayOutputStream.toByteArray();
                    bytes= Base64.decode(studimage, Base64.DEFAULT);
                    prf_bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    img_prof_stu.setImageBitmap(prf_bitmap);

                    SharedPreferences preferences=getSharedPreferences("STD_ATT_IMF_URL",Context.MODE_PRIVATE);
                    SharedPreferences.Editor ed=preferences.edit();
                    ed.putString("STDATT_LINK",studimage);
                    ed.apply();
                }
                else
                {
                    Toast.makeText(StudentProfile.this, "No Image Found", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
    }
}