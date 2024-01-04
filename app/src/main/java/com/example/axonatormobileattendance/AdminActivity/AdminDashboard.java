package com.example.axonatormobileattendance.AdminActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.axonatormobileattendance.ConfirmScreen;
import com.example.axonatormobileattendance.R;
import com.google.android.material.navigation.NavigationView;

public class AdminDashboard extends AppCompatActivity {

    NavigationView navMenu;
    ActionBarDrawerToggle toggle;
    DrawerLayout drayerLayout;

    Fragment temp=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        Toolbar toolbar=findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);

        navMenu=findViewById(R.id.navMenu);
        drayerLayout=findViewById(R.id.drawerlayout);

         getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,new StartFragment()).commit();


        toggle=new ActionBarDrawerToggle(this,drayerLayout,toolbar,R.string.app_name,R.string.app_name);
        drayerLayout.addDrawerListener(toggle);
        toggle.syncState();



        navMenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId())
                {


                    case R.id.nav_teacher_profile:
                        temp=new AdminTeacherProfileFrag();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,temp).commit();
                        toolbar.setTitle("Add Teacher Profile");
                        drayerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_stud_profile:
                        temp=new StudentClassListFrag();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,temp).commit();
                        toolbar.setTitle("Add Student Profile");
                        drayerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_add_class:
                       startActivity(new Intent(AdminDashboard.this,AdminAddClass.class));
                        drayerLayout.closeDrawer(GravityCompat.START);
                        break;


                    case R.id.nav_show_teacher:

                        Intent showTeacherIntent= new Intent(AdminDashboard.this,ShowTeacherScreen.class);
                        startActivity(showTeacherIntent);
                        drayerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_show_student:

                        Intent showStudentIntent= new Intent(AdminDashboard.this,AdminShowStudents.class);
                        startActivity(showStudentIntent);
                        drayerLayout.closeDrawer(GravityCompat.START);
                        break;


                /*    case R.id.nav_show_attTech:

                        Intent attIntent= new Intent(AdminDashboard.this,AdminTeacherAttListSCR.class);
                        startActivity(attIntent);
                        drayerLayout.closeDrawer(GravityCompat.START);
                        break;

                 */

                    case R.id.nav_show_attStud:
                        Intent stdIntent= new Intent(AdminDashboard.this, AdminStudentAttListSCR.class);
                        startActivity(stdIntent);
                        drayerLayout.closeDrawer(GravityCompat.START);
                        break;



                    case R.id.nav_logout:
                        Toast.makeText(AdminDashboard.this, "Logout", Toast.LENGTH_SHORT).show();
                        drayerLayout.closeDrawer(GravityCompat.START);
                        Intent adminLogoutIntent=new Intent(AdminDashboard.this, ConfirmScreen.class);
                        startActivity(adminLogoutIntent);
                        finish();


                        break;




                }
                return false;
            }
        });


    }
}