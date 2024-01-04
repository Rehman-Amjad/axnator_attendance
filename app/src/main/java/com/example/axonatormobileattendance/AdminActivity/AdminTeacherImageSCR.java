package com.example.axonatormobileattendance.AdminActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.axonatormobileattendance.Model.TeacherImage;
import com.example.axonatormobileattendance.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AdminTeacherImageSCR extends AppCompatActivity {

    ImageView img_teacher,admin_teacherImage_back;
    Button btn_gallery,btn_upload;

    Bitmap galbitmap;
    Uri imageuri;

    String sImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_teacher_image_s_c_r);

        btn_gallery = findViewById(R.id.btn_gallery);

        btn_upload = findViewById(R.id.btn_upload);
        img_teacher = findViewById(R.id.img_teacher);
        admin_teacherImage_back = findViewById(R.id.admin_teacherImage_back);

        admin_teacherImage_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(AdminTeacherImageSCR.this,AdminDashboard.class);
                startActivity(backIntent);
                finish();
            }
        });



        if (ContextCompat.checkSelfPermission(AdminTeacherImageSCR.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AdminTeacherImageSCR.this, new String[]{
                    Manifest.permission.CAMERA


            }, 100);
        }

 /*   btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 100);
            }
        });*/

        btn_gallery.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 12);
                btn_upload.setVisibility(View.VISIBLE);
            }
        });



        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sp=getSharedPreferences("Teacher_Key", Context.MODE_PRIVATE);
                String teacherKey=sp.getString("teacherID","");
                DatabaseReference reference= FirebaseDatabase.getInstance().getReference("teacherImage")
                        ;
                //    Query query=reference.orderByChild("teacherId").equalTo(teacherKey);

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        TeacherImage teacherImage = new TeacherImage(teacherKey,sImage);
                        reference.child(teacherKey).setValue(teacherImage);
                        Toast.makeText(AdminTeacherImageSCR.this, "Uploading Complete...", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(AdminTeacherImageSCR.this,AdminDashboard.class));
                        finish();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==12 && resultCode==RESULT_OK && data!=null)
        {
            imageuri = data.getData();
            try {
                galbitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageuri);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                galbitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
                byte[] bytes = stream.toByteArray();
                sImage = Base64.encodeToString(bytes,Base64.DEFAULT);
                img_teacher.setImageBitmap(galbitmap);

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

    }
}