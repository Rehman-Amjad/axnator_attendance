package com.example.axonatormobileattendance.AdminActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.axonatormobileattendance.Model.AddClassData;
import com.example.axonatormobileattendance.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AdminAddClass extends AppCompatActivity {

    EditText ed_ClassSession,ed_addClass;
    Button btn_Save;

    ImageView admin_studentImage_back;

    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_class);

        //EditTxt Typecasting
        ed_ClassSession=findViewById(R.id.ed_ClassSession);
        ed_addClass=findViewById(R.id.ed_addClass);
        admin_studentImage_back=findViewById(R.id.admin_studentImage_back);

        //Button typecasting
        btn_Save=findViewById(R.id.btn_Save);


        admin_studentImage_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminAddClass.this,AdminDashboard.class));
                finish();
            }
        });

        database=FirebaseDatabase.getInstance();
        myRef=database.getReference("classesName");


        btn_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String checkClassName=ed_addClass.getText().toString();
                DatabaseReference reference=FirebaseDatabase.getInstance().getReference("classesName");

                Query query=reference.orderByChild("addClass").equalTo(checkClassName);

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.exists())
                        {

                            String checkName=snapshot.child(checkClassName).child("addClass").getValue(String.class);

                            if (checkName.equals(checkClassName))
                            {
                                Toast.makeText(AdminAddClass.this, "Class ALready Added", Toast.LENGTH_SHORT).show();
                            }

                        }
                        else
                        {
                            String classYear=ed_ClassSession.getText().toString();
                            String addClass=ed_addClass.getText().toString();


                            AddClassData data=new AddClassData(classYear,addClass);
                            String key= myRef.child(addClass).getKey();
                            myRef.child(key).setValue(data);

                            ed_addClass.setText("");
                            ed_ClassSession.setText("");
                            ed_ClassSession.requestFocus();
                            Toast.makeText(AdminAddClass.this, "Class Saved..", Toast.LENGTH_SHORT).show();



                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });

    }
}