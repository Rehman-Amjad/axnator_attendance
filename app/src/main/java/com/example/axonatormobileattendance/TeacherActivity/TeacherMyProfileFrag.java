package com.example.axonatormobileattendance.TeacherActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
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

public class TeacherMyProfileFrag extends AppCompatActivity {

    public Bitmap tec_prof_bitmap;
    String teacimage;
    TextView tv_teacher_name,tv_teacher_Id,tv_teacher_email,tv_teacher_DOB,tv_teacher_Qual,tv_teacher_pass;
    ImageView img_prof_tech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_my_profile_frag);

        tv_teacher_name=findViewById(R.id.tv_teacher_name);
        tv_teacher_Id=findViewById(R.id.tv_teacher_Id);
        tv_teacher_email=findViewById(R.id.tv_teacher_email);
        tv_teacher_DOB=findViewById(R.id.tv_teacher_DOB);
        tv_teacher_Qual=findViewById(R.id.tv_teacher_Qual);
        tv_teacher_pass=findViewById(R.id.tv_teacher_pass);

        img_prof_tech=findViewById(R.id.img_prof_tech);


        SharedPreferences sp=getSharedPreferences("TEACHER_DATA", Context.MODE_PRIVATE);

        tv_teacher_name.setText(sp.getString("teacherName",""));
        tv_teacher_Id.setText(sp.getString("teacherID",""));
        tv_teacher_email.setText(sp.getString("teacherEmail",""));
        tv_teacher_DOB.setText(sp.getString("teacherDOB",""));
        tv_teacher_Qual.setText(sp.getString("teacherQualification",""));
        tv_teacher_pass.setText(sp.getString("teacherPassword",""));

        retrieveimage();

    }

    private void retrieveimage()
    {
        SharedPreferences sp=getSharedPreferences("TEACHER_DATA",Context.MODE_PRIVATE);

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("teacherImage");
        String teacherId= sp.getString("teacherID","");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                if (snapshot.exists())
                {
                    teacimage = snapshot.child(teacherId).child("newImage").getValue(String.class);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    byte[] bytes = byteArrayOutputStream.toByteArray();
                    bytes= Base64.decode(teacimage, Base64.DEFAULT);
                    tec_prof_bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    img_prof_tech.setImageBitmap(tec_prof_bitmap);
                }
                else
                {
                    Toast.makeText(TeacherMyProfileFrag.this, "No Image Found", Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}