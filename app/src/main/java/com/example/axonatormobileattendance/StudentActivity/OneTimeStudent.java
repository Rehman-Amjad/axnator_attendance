package com.example.axonatormobileattendance.StudentActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.axonatormobileattendance.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class OneTimeStudent extends AppCompatActivity {

    TextView tv_student_name;
    EditText ed_change_password,ed_change_password_con;
    Button btn_Student_Next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_time_student);

        tv_student_name=findViewById(R.id.tv_student_name);
        ed_change_password=findViewById(R.id.ed_change_password);
        ed_change_password_con=findViewById(R.id.ed_change_password_con);
        btn_Student_Next=findViewById(R.id.btn_Student_Next);

        SharedPreferences studentDataPreference=getSharedPreferences("STUDENT_DATA",MODE_PRIVATE);

        tv_student_name.setText("Student Name: " + studentDataPreference.getString("studentName",""));

        String studentId=studentDataPreference.getString("STUDENT_PRE_ID","");





        btn_Student_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference reference= FirebaseDatabase.getInstance().getReference("studentData");

                Query query=reference.orderByChild("studentId").equalTo(studentId);

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.exists())
                        {
                            String checkStudentId=snapshot.child(studentId).child("studentId").getValue(String.class);

                            if (checkStudentId.equals(studentId))
                            {
                                String studentPassword=ed_change_password.getText().toString().trim();

                                if (studentPassword.equals(ed_change_password_con.getText().toString().trim()))
                                {
                                    String key= reference.child(studentId).getKey();
                                    reference.child(key).child("studentPassword").setValue(studentPassword);
                                    Toast.makeText(OneTimeStudent.this, "Password Changed", Toast.LENGTH_SHORT).show();

                                    Intent intent=new Intent(OneTimeStudent.this,StudentDashboard.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                {
                                    ed_change_password.setError("Password Not Match");
                                    ed_change_password.requestFocus();
                                    ed_change_password.setText("");
                                    ed_change_password_con.setText("");
                                }
                            }

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