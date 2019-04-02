package com.ashgrav.tanketter;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.ashgrav.tanketter.domain.Device;
import com.ashgrav.tanketter.recyclerUtils.DeviceLineAdapter;
import com.ashgrav.tanketter.recyclerUtils.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

public class ListDevicesActivity extends AppCompatActivity {
    public static final String EXTRA_DEVICE = "com.ashgrav.tanketter.DEVICE";
    private List<Device> deviceList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DeviceLineAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_devices);
        deviceList = getIntent().getParcelableArrayListExtra(MainActivity.EXTRA_MESSAGE);


        recyclerView = findViewById(R.id.my_recycler_view);
        adapter = new DeviceLineAdapter(deviceList);
        RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(mlayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Device device = deviceList.get(position);
                Toast.makeText(getApplicationContext(), device.getHostname() + " is selected!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),FullscreenControllActivity.class);
                intent.putExtra(EXTRA_DEVICE,device);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        prepareDeviceData();
    }

    private void prepareDeviceData() {
        try {
            deviceList = getIntent().getParcelableArrayListExtra(MainActivity.EXTRA_MESSAGE);
        } catch (NullPointerException nle) {
            nle.printStackTrace();
        }
        adapter.notifyDataSetChanged();
    }


}
