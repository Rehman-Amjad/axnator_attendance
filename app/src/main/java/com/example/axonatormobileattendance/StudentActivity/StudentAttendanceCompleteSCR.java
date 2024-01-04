package com.example.axonatormobileattendance.StudentActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.axonatormobileattendance.ConfirmScreen;
import com.example.axonatormobileattendance.Model.StudentAttendaceClass;
import com.example.axonatormobileattendance.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nabinbhandari.android.permissions.PermissionHandler;

import java.io.IOException;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import java.security.acl.Permission;
import java.text.SimpleDateFormat;
import androidx.core.content.PermissionChecker;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class StudentAttendanceCompleteSCR extends AppCompatActivity {

    Button btn_com_dash,btn_com_logout;

    String parentcounter;
    int count;

    ValueEventListener valueEventListener;

    DatabaseReference reference;

    String string_city;
    String string_state;
    String string_country;
    String string_location;
    String stringLatitude;
    String stringLongitude;
    String stringLooking;
    LocationManager locationManager;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;


    @Override
    protected void onDestroy() {
        super.onDestroy();

        reference.removeEventListener(valueEventListener);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attendance_complete_s_c_r);

        btn_com_dash=findViewById(R.id.btn_com_dash);
        btn_com_logout=findViewById(R.id.btn_com_logout);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        LocationPremissionCheck();
        GooglePlayServiceCheck();
        GPSLocationServiceCheck();


        btn_com_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadCustomAttendance();

                DatabaseReference counter = FirebaseDatabase.getInstance().getReference("Counter");

                counter.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        parentcounter = snapshot.child("StudentValue").getValue(String.class);

                        count = Integer.parseInt(parentcounter);
                        count=count+1;
                        parentcounter = String.valueOf(count);
                        counter.child("StudentValue").setValue(parentcounter);

                        Intent backIntent=new Intent(StudentAttendanceCompleteSCR.this, ConfirmScreen.class);
                        startActivity(backIntent);
                        finish();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });

        btn_com_dash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadCustomAttendance();

                DatabaseReference counter = FirebaseDatabase.getInstance().getReference("Counter");

                counter.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        parentcounter = snapshot.child("StudentValue").getValue(String.class);

                        count = Integer.parseInt(parentcounter);
                        count=count+1;
                        parentcounter = String.valueOf(count);
                        counter.child("StudentValue").setValue(parentcounter);

                        Intent dashIntent= new Intent(StudentAttendanceCompleteSCR.this,StudentDashboard.class);
                        startActivity(dashIntent);
                        finish();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });


        uploadAttendaceToDatabase();

        CounterCall();
    }

    private void uploadCustomAttendance()
    {
        SharedPreferences sp=getSharedPreferences("STUDENT_DATA",MODE_PRIVATE);

        String studentId=sp.getString("studentID","");

        String currentMonth=getdateWithMonth();
        String currentDate = getCurrentdate();
        String currentTime= getTimeWithAmPm();
        String studentPresent="Present";
        String date=getdateWithMonth()+" "+ getCurrentdateOnly();



        reference= FirebaseDatabase.getInstance().getReference("CustomAttendanceStudent");



        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                SharedPreferences sp=getSharedPreferences("STUDENT_DATA",MODE_PRIVATE);
                String name=sp.getString("currentClass","");

                StudentAttendaceClass classData=new StudentAttendaceClass(currentMonth,currentTime,studentId,name,currentDate,studentPresent,date,stringLatitude,stringLatitude,string_city,string_state);
                String   key = reference.push().getKey();
                reference.child(studentId).child(currentMonth).child(parentcounter).setValue(classData);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        reference.addValueEventListener(valueEventListener);
    }

    private void CounterCall()
    {
        DatabaseReference counter = FirebaseDatabase.getInstance().getReference("Counter");

        counter.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                parentcounter = snapshot.child("StudentValue").getValue(String.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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

    private void uploadAttendaceToDatabase()
    {

        SharedPreferences sp=getSharedPreferences("STUDENT_DATA",MODE_PRIVATE);

        String studentId=sp.getString("studentID","");

        String currentMonth=getdateWithMonth();
        String currentDate = getCurrentdate();
        String currentTime= getTimeWithAmPm();
        String studentPresent="Present";
        String date=getdateWithMonth()+" "+ getCurrentdateOnly();

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("attendanceStudent");

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SharedPreferences sp=getSharedPreferences("STUDENT_DATA",MODE_PRIVATE);
                String name=sp.getString("currentClass","");

                StudentAttendaceClass classData=new StudentAttendaceClass(currentMonth,currentTime,studentId,name,currentDate,studentPresent,date,stringLatitude,stringLatitude,string_city,string_state);
                String key_value=reference.child(currentMonth).getKey();
                reference.child("date").child(date).child(studentId).setValue(classData);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        reference.addValueEventListener(valueEventListener);




    }

    private void LocationPremissionCheck() {

        String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
        String rationale = "Please provide location permission so that you can ...";
        Permissions.Options options = new Permissions.Options()
                .setRationaleDialogTitle("Location Permission")
                .setSettingsDialogTitle("Warning");
        Permissions.check(this/*context*/, permissions, null/*rationale*/, null/*options*/, new PermissionHandler() {
            @Override
            public void onGranted() {
                LocationRequest();

            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                super.onDenied(context, deniedPermissions);
                LocationPremissionCheck();
            }
        });
    }

    private void LocationRequest() {


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PermissionChecker.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PermissionChecker.PERMISSION_GRANTED) {


            fusedLocationProviderClient = new FusedLocationProviderClient(this);

            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {


                    if (location != null) {

                        Double locationLatitude = location.getLatitude();
                        Double locationLongitude = location.getLongitude();

                        stringLatitude = locationLatitude.toString();
                        stringLongitude = locationLongitude.toString();

                        if (!stringLatitude.equals("0.0") && !stringLongitude.equals("0.0")) {

                            LocationRetreive(locationLatitude, locationLongitude);

                        } else {

                            Toast.makeText(StudentAttendanceCompleteSCR.this,
                                    "Please turn on any GPS or location service and restart to use the app", Toast.LENGTH_SHORT).show();

                        }


                    } else {
                        Toast.makeText(StudentAttendanceCompleteSCR.this,
                                "Please turn on any GPS or location service and restart to use the app", Toast.LENGTH_SHORT).show();
                    }

                }

            });


        } else {

            LocationPremissionCheck();

        }
    }

    private void LocationRetreive(Double locationLatitude, Double locationLongitude) {
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(locationLatitude, locationLongitude, 1);
            if (addresses != null && addresses.size() > 0) {
                string_city = addresses.get(0).getLocality();
                string_state = addresses.get(0).getAdminArea();
                string_country = addresses.get(0).getCountryName();
                string_location = addresses.get(0).getAddressLine(0);


                if (string_country == null) {
                    if (string_state != null) {
                        string_country = string_state;
                    } else if (string_city != null) {
                        string_country = string_city;
                    } else {
                        string_country = "null";
                    }
                }
                if (string_city == null) {
                    if (string_state != null) {
                        string_city = string_state;
                    } else {
                        string_city = string_country;
                    }
                }
                if (string_state == null) {
                    if (string_city != null) {
                        string_state = string_city;
                    } else {
                        string_state = string_country;
                    }
                }
                if (string_location == null) {
                    string_location = "Null";
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(StudentAttendanceCompleteSCR.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public boolean GooglePlayServiceCheck() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if (status != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(status)) {
                googleApiAvailability.getErrorDialog(this, status, 2404).show();
            }
            return false;
        }
        return true;
    }

    private void GPSLocationServiceCheck() {
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Your GPS seems to be disabled, enable it to use this app?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            dialog.cancel();
//                            Intent intent = new Intent(RegisterActivity.this, StartActivity.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            startActivity(intent);
                            finish();

                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        GPSLocationServiceCheck();

    }
}