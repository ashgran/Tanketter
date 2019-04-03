package com.ashgrav.tanketter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ashgrav.tanketter.domain.Device;
import com.ashgrav.tanketter.joystick.JoyStickClass;
import com.ashgrav.tanketter.pinger.HttpHandler;

public class JoystickActivity extends Activity {
	private Device device;
	private HttpHandler httpHandler;
	private ControllHandler ctrh;

	RelativeLayout layout_joystick;

	ImageView image_joystick;
	ImageView image_border;

	TextView textView1;
	TextView textView2;
	TextView textView3;
	TextView textView4;
	TextView textView5;
	
	JoyStickClass js;
	
    @SuppressLint("ClickableViewAccessibility")
	public void onCreate(Bundle savedInstanceState) {

		device = getIntent().getParcelableExtra(ListDevicesActivity.EXTRA_DEVICE);


		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

		httpHandler = new HttpHandler();
		ctrh = new ControllHandler(device.getHostAddress());

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

        textView1 = (TextView)findViewById(R.id.textView1);
        textView2 = (TextView)findViewById(R.id.textView2);
        textView3 = (TextView)findViewById(R.id.textView3);
        textView4 = (TextView)findViewById(R.id.textView4);
        textView5 = (TextView)findViewById(R.id.textView5);
        
	    layout_joystick = (RelativeLayout)findViewById(R.id.layout_joystick);

        js = new JoyStickClass(getApplicationContext()
        		, layout_joystick, R.drawable.image_button);
	    js.setStickSize(150, 150);
	    js.setLayoutSize(500, 500);
	    js.setLayoutAlpha(150);
	    js.setStickAlpha(100);
	    js.setOffset(90);
	    js.setMinimumDistance(50);
	    
	    layout_joystick.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View arg0, MotionEvent arg1) {
				js.drawStick(arg1);
				if(arg1.getAction() == MotionEvent.ACTION_DOWN
						|| arg1.getAction() == MotionEvent.ACTION_MOVE) {
					textView1.setText("X : " + String.valueOf(js.getX()));
					textView2.setText("Y : " + String.valueOf(js.getY()));
					textView3.setText("Angle : " + String.valueOf(js.getAngle()));
					textView4.setText("Distance : " + String.valueOf(js.getDistance()));
					
					int direction = js.get8Direction();
					if(direction == JoyStickClass.STICK_UP) {
						//ctrh.stop();
						ctrh.goForward();
						textView5.setText("Direction : Up");
					} else if(direction == JoyStickClass.STICK_UPRIGHT) {
						textView5.setText("Direction : Up Right");
					} else if(direction == JoyStickClass.STICK_RIGHT) {
						//ctrh.stop();
						ctrh.goRight();
						textView5.setText("Direction : Right");
					} else if(direction == JoyStickClass.STICK_DOWNRIGHT) {
						textView5.setText("Direction : Down Right");
					} else if(direction == JoyStickClass.STICK_DOWN) {
						//ctrh.stop();
						ctrh.goBack();
						textView5.setText("Direction : Down");
					} else if(direction == JoyStickClass.STICK_DOWNLEFT) {
						textView5.setText("Direction : Down Left");
					} else if(direction == JoyStickClass.STICK_LEFT) {
						//ctrh.stop();
						ctrh.goLeft();
						textView5.setText("Direction : Left");
					} else if(direction == JoyStickClass.STICK_UPLEFT) {
						textView5.setText("Direction : Up Left");
					} else if(direction == JoyStickClass.STICK_NONE) {
						textView5.setText("Direction : Center");
						ctrh.stop();
					}
				} else if(arg1.getAction() == MotionEvent.ACTION_UP) {
					ctrh.stop();
					textView1.setText("X :");
					textView2.setText("Y :");
					textView3.setText("Angle :");
					textView4.setText("Distance :");
					textView5.setText("Direction :");
				}
				return true;
			}
        });
    } 	
}
