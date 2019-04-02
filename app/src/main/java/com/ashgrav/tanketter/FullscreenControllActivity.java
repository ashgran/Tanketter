package com.ashgrav.tanketter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import com.ashgrav.tanketter.domain.Device;
import com.ashgrav.tanketter.pinger.HttpHandler;


public class FullscreenControllActivity extends AppCompatActivity {
    private Device device;
    private HttpHandler httpHandler;
    private ControllHandler ctrh;
    private ImageButton btnForward;
    private ImageButton btnBackward;

    private ImageButton btnLeft;
    private ImageButton btnRigh;

    private ImageButton btnMore;
    private int spinner;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        device = getIntent().getParcelableExtra(ListDevicesActivity.EXTRA_DEVICE);
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        //  getActionBar().hide();

        setContentView(R.layout.activity_fullscreen_controll);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        httpHandler = new HttpHandler();
        ctrh = new ControllHandler(device.getHostAddress());

        btnLeft = findViewById(R.id.a3_left_up_button);
        btnForward = findViewById(R.id.a3_left_down_button);

        btnRigh = findViewById(R.id.a3_right_up_button);
        btnBackward = findViewById(R.id.a3_right_down_button);


        btnForward.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ctrh.goForward();
                        break;
                    case MotionEvent.ACTION_UP:
                        ctrh.stop();
                        break;
                }
                return true;
            }
        });

        btnBackward.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ctrh.goBack();
                        break;
                    case MotionEvent.ACTION_UP:
                        ctrh.stop();
                        break;
                }
                return true;
            }
        });

        btnLeft.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ctrh.goLeft();
                        break;
                    case MotionEvent.ACTION_UP:
                        ctrh.stop();
                        break;
                }
                return true;            }
        });

        btnRigh.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ctrh.goRight();
                        break;
                    case MotionEvent.ACTION_UP:
                        ctrh.stop();
                        break;
                }
                return true;
            }
        });


    }

}
