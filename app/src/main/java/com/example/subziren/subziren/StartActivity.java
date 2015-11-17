package com.example.subziren.subziren;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class StartActivity extends AppCompatActivity {

    private TextView temperatureText;
    private SeekBar tempSlider;
    private String temperatureString;
    public TextView temperatureTextView;
    private int max_temp = 100;
    private int min_temp = -50;
    private int currentGoalTemp = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        temperatureText = (TextView)findViewById(R.id.TemperatureText);
        tempSlider = (SeekBar)findViewById(R.id.temperatureSlider);
        temperatureTextView = (TextView)findViewById(R.id.temperatureTextIndicator);
        temperatureTextView.setText("-50");
        tempSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentGoalTemp = progress + (min_temp);
                temperatureTextView.setText("" + currentGoalTemp);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        // Create an ArrayAdapter using the string array and a default spinner layout
       // ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
        //         android.R.layout.simple_spinner_item,{"test"});


        timer();
    }
    public void shit()
    {
        /*
        int minPlus = min_temp;
        int maxPlus = max_temp;
        if(minPlus < 0)
        {
            minPlus *= -1;
        }
        if(maxPlus < 0)
        {
            maxPlus *= -1;
        }
        int count = minPlus + maxPlus;
        String[] temperatures = new String[count];
        int counter = 0;
        for(Integer i = min_temp;;i++)
        {
            temperatures[counter] = i.toString();
            counter++;
            if(counter == count -1)
            {
                break;
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, temperatures);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        goalTemperatureInput.setAdapter(adapter);
        setGoalTemperatureSpinner(30);
        goalTemperatureInput.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        */
    }
    public void StartButton_Click(View view)
    {

    }

    public void timer(){
        Thread timerLoop = new Thread(new Runnable() {
            @Override
            public synchronized void run() {
                while (true) {
                    final String temp = MyActivity.GetTemperature();
                    Float tempFloat = Float.parseFloat(temp);
                    if (tempFloat <= currentGoalTemp) {
                        showNotification(temp);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            temperatureText.setText(temp+"C");
                        }
                    });
                    try {
                        wait(5000,0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        timerLoop.start();
    }

    public void showNotification(String temp) {
        String x = "Juoma on sopivan kylmää("+temp+"C)";
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, ShowNotificationDetailActivity.class), 0);
        Resources r = getResources();
        Notification notification = new NotificationCompat.Builder(this)
                .setTicker(r.getString(R.string.notification_title))
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle(r.getString(R.string.notification_title))
                .setContentText(x)
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }

    private class ShowNotificationDetailActivity {
    }
}
