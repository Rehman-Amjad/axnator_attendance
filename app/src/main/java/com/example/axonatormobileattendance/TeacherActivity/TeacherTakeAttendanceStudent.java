package com.example.axonatormobileattendance.TeacherActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.axonatormobileattendance.Model.TeacherTakeStudentAttClass;
import com.example.axonatormobileattendance.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TeacherTakeAttendanceStudent extends AppCompatActivity {

    EditText ed_student_id;
    Button btn_Student_ve,btn_upload;
    ImageView img_student,tech_getatt_back;
    public Bitmap oribitmap;

    String studimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_take_attendance_student);

        ed_student_id=findViewById(R.id.ed_student_id);
        btn_Student_ve=findViewById(R.id.btn_Student_ve);
        btn_upload=findViewById(R.id.btn_upload);

        img_student=findViewById(R.id.img_student);
        tech_getatt_back=findViewById(R.id.tech_getatt_back);


        tech_getatt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent= new Intent(TeacherTakeAttendanceStudent.this,TeacherDashboard.class);
                startActivity(backIntent);
                finish();
            }
        });





        btn_Student_ve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stuedntId=ed_student_id.getText().toString().trim();

                SharedPreferences preferences=getSharedPreferences("KEYID",MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();

                editor.putString("key",stuedntId);
                if (TextUtils.isEmpty(stuedntId))
                {
                    ed_student_id.setError("Please Enter Student Id");
                    ed_student_id.requestFocus();
                    ed_student_id.setText("");

                }
                DatabaseReference reference= FirebaseDatabase.getInstance().getReference("studentImage");


                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        studimage = snapshot.child(stuedntId).child("newImage").getValue(String.class);
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        byte[] bytes = byteArrayOutputStream.toByteArray();
                        bytes= Base64.decode(studimage, Base64.DEFAULT);
                        oribitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                        img_student.setImageBitmap(oribitmap);

                        SharedPreferences preferences=getSharedPreferences("STD_ATT_IMF_URL",MODE_PRIVATE);
                        SharedPreferences.Editor ed=preferences.edit();
                        ed.putString("STDATT_LINK",studimage);
                        ed.apply();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                btn_upload.setVisibility(View.VISIBLE);

            }
        });



        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentMonth=getdateWithMonth();
                String currentDate = getCurrentdate();
                String currentTime= getTimeWithAmPm();
                String attendance="Present";
                String date=getdateWithMonth()+" "+ getCurrentdateOnly();

                SharedPreferences preferences=getSharedPreferences("KEYID",MODE_PRIVATE);
                String stuedntIDt= preferences.getString("key","");

                SharedPreferences sp=getSharedPreferences("TEACHER_DATA",MODE_PRIVATE);
                String teacherID= sp.getString("teacherID","");
                String teacherName =sp.getString("teacherName","");

                DatabaseReference ref= FirebaseDatabase.getInstance().getReference("teacherGetAttendance");


                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        TeacherTakeStudentAttClass data= new TeacherTakeStudentAttClass(teacherID,teacherName,currentMonth,currentTime,attendance,stuedntIDt);

                        ref.child("date").child(date).child(teacherID).setValue(data);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                Toast.makeText(TeacherTakeAttendanceStudent.this, "Attendance Saved..", Toast.LENGTH_SHORT).show();

            }
        });

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
}