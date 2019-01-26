package com.example.hp.neveralone;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

public class CreateEvent extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private static final int DIALOG_ID = 0;
    Button btn_pick,btn_create,btn_time;
    EditText Name,Description;
    int day,month,year,hour,minutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        btn_create = findViewById(R.id.EvCreateBtn);
        btn_pick = findViewById(R.id.btn_click);
        btn_time = findViewById(R.id.btn_time);
        Name = findViewById(R.id.Ename);
        Description = findViewById(R.id.desc);
        btn_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day= c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog= new DatePickerDialog(CreateEvent.this,CreateEvent.this,year,month,day);
                datePickerDialog.show();
            }
        });
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String name = Name.getText().toString();
                String description = Description.getText().toString();

                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                String organiser = firebaseUser.getUid().toString();
                sendEvent(organiser,name,description,day,month,year,hour,minutes);
                finish();
            }
        });

        btn_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID);
            }
        });

    }

    private void sendEvent(String organiser,String name, String description, int day, int month, int year, int hour, int minutes) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        HashMap<String,Object>hashMap = new HashMap<>();
        hashMap.put("EventName",name);
        hashMap.put("Description",description);
        hashMap.put("Organiser",organiser);
        hashMap.put("Day",day);
        hashMap.put("Month",month);
        hashMap.put("Year",year);
        hashMap.put("Hour",hour);
        hashMap.put("Minutes",minutes);
        reference.child("Events").push().setValue(hashMap);

    }


    public void showTimePickerDialog(){

        btn_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID);
            }
        });
    }


    @Override
    protected Dialog onCreateDialog(int id) {

        if (id == DIALOG_ID){
            return new TimePickerDialog(CreateEvent.this,kTimePickerListener,hour,minutes,false);
        }
        return null;

    }

    protected TimePickerDialog.OnTimeSetListener kTimePickerListener =
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    hour =hourOfDay;
                    minutes = minute;
                    Toast.makeText(CreateEvent.this,hour+":hour     "+minutes+":minutes",Toast.LENGTH_SHORT ).show();
                }
    };

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        this.year = year;
        this.month= month;
        this.day = dayOfMonth;

        Calendar c = Calendar.getInstance();
        hour =c.get(Calendar.HOUR);
        minutes = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(CreateEvent.this,CreateEvent.this,hour,minutes, true);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {


        this.hour = hourOfDay;
        this.minutes = minute;

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
