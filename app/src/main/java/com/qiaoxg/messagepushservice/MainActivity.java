package com.qiaoxg.messagepushservice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.qiaoxg.messagepushservice.service.MessagePushService;

public class MainActivity extends AppCompatActivity {

    View mStartBtn, mStopBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStartBtn = findViewById(R.id.start_btn);
        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MessagePushService.class);
                startService(intent);
            }
        });


        mStopBtn = findViewById(R.id.stop_btn);
        mStopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stopIntent = new Intent(MainActivity.this, MessagePushService.class);
                stopService(stopIntent);
            }
        });

    }
}
