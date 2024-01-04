package com.example.axonatormobileattendance.TeacherActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.axonatormobileattendance.ConfirmScreen;
import com.example.axonatormobileattendance.R;
import com.example.axonatormobileattendance.SiderAdapter.ImageSliderAdapter;
import com.example.axonatormobileattendance.StudentActivity.StudentDashboard;
import com.google.android.material.navigation.NavigationView;

import java.util.Timer;
import java.util.TimerTask;

public class TeacherDashboard extends AppCompatActivity {

    NavigationView navMenu;
    ActionBarDrawerToggle toggle;
    DrawerLayout drayerLayout;

   // CardView cardTwo,cardOne;
  //  ViewPager viewPager;
  //  ImageSliderAdapter adapter;

    Fragment temp=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dashboard);

        Toolbar toolbar=findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);

     //   cardTwo=findViewById(R.id.cardTwo);
      //  cardOne=findViewById(R.id.cardOne);



        navMenu=findViewById(R.id.navMenu);
        drayerLayout=findViewById(R.id.drawerlayout);

        //    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,new AdminTeacherProfileFrag()).commit();
      //  temp=new TeacherMyProfileFrag();
     //  getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,temp).commit();

        toggle=new ActionBarDrawerToggle(this,drayerLayout,toolbar,R.string.app_name,R.string.app_name);
        drayerLayout.addDrawerListener(toggle);
        toggle.syncState();

     //   sliderCode();



        navMenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId())
                {


                    case R.id.nav_teacher_profile:
                        startActivity(new Intent(TeacherDashboard.this,TeacherMyProfileFrag.class));
                        drayerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_teacher_attendence:
                        Intent attendenceintent = new
                                Intent(TeacherDashboard.this,TeacherAttendanceSCR.class);
                        startActivity(attendenceintent);
                        toolbar.setTitle("My Attendence");
                        drayerLayout.closeDrawer(GravityCompat.START);
                        break;


                    case R.id.nav_teacher_stAttendence:

                        Intent stIntent= new Intent(TeacherDashboard.this,TeacherTakeAttendanceStudent.class);
                        startActivity(stIntent);

                        toolbar.setTitle("Add Student Attendence");
                        drayerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_teacher_ShowAttendence:

                        Intent showIntent=new Intent(TeacherDashboard.this,ShowTeacherAttendance.class);
                        startActivity(showIntent);
                        toolbar.setTitle("Show Attendance");
                        drayerLayout.closeDrawer(GravityCompat.START);
                        break;


                    case R.id.nav_teacher_checkAtten:

                        Intent checkIntent=new Intent(TeacherDashboard.this, TeacherCheckStudAttenSCR.class);
                        startActivity(checkIntent);

                        drayerLayout.closeDrawer(GravityCompat.START);
                        break;




                    case R.id.nav_logout:
                        Toast.makeText(TeacherDashboard.this, "Logout", Toast.LENGTH_SHORT).show();
                        Intent logoutIntent=new Intent(TeacherDashboard.this, ConfirmScreen.class);
                        startActivity(logoutIntent);
                        finish();
                        drayerLayout.closeDrawer(GravityCompat.START);
                        break;



                }
                return false;
            }
        });

    }

  /*  private void sliderCode()
    {
        adapter = new ImageSliderAdapter(this);
        viewPager.setAdapter(adapter);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 4000);
    }

    public class MyTimerTask extends TimerTask {

        @Override
        public void run() {

            TeacherDashboard.this.runOnUiThread(new Runnable()
            {

                @Override
                public void run() {


                    //  myanim = AnimationUtils.loadAnimation(StudentDashboard.this,R.anim.left_to_right_anim);


                    if(viewPager.getCurrentItem() == 0)
                    {
                        viewPager.setCurrentItem(1);
                        //   dashText1.setText("Start Learning and improve your Skills");
                        //   dashText1.startAnimation(myanim);


                    }
                    else if(viewPager.getCurrentItem() == 1)
                    {
                        viewPager.setCurrentItem(2);
                        //    dashText1.setText("Grow Up and Build a Bright Future");
                        //    dashText1.startAnimation(myanim);

                    }

                    else if (viewPager.getCurrentItem() == 2)
                    {
                        viewPager.setCurrentItem(3);
                        //   dashText1.setText("A Teacher Affects Eternity");
                        //     dashText1.startAnimation(myanim);

                    }

                    else {
                        viewPager.setCurrentItem(0);
                        //    dashText1.setText("Wisdom Begins With Wonder");
                        //    dashText1.startAnimation(myanim);

                    }

                }
            });

        }
    }

   */
}