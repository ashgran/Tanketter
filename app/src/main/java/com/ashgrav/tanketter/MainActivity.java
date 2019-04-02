package com.ashgrav.tanketter;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.ashgrav.tanketter.domain.Device;
import com.ashgrav.tanketter.pinger.Network;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Device> deviceList = new ArrayList<>();
    public static final String EXTRA_MESSAGE = "com.ashgrav.tanketter.DEVICES";

    private Button scanBtn;
    private ProgressBar spinner;
    private Network network;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        network = new Network();
        spinner = findViewById(R.id.spinner);
        spinner.setVisibility(View.GONE);
        scanBtn = findViewById(R.id.scanBtn);
        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setVisibility(View.VISIBLE);

                network.startScan();
                while (true){
                    if(network.isThreadsDone()){
                        break;
                    }
                }
                System.out.println(network.getDevices().size());
                deviceList.addAll(network.getDevices());
                startNextActivity();
                }


        });
    }

    private void startNextActivity(){
        Intent intent = new Intent(this,ListDevicesActivity.class);
        intent.putParcelableArrayListExtra(EXTRA_MESSAGE, (ArrayList<? extends Parcelable>) deviceList);
        startActivity(intent);

    }

}
