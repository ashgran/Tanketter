package com.ashgrav.tanketter.recyclerUtils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ashgrav.tanketter.R;
import com.ashgrav.tanketter.domain.Device;

import java.util.List;

public class DeviceLineAdapter extends RecyclerView.Adapter<DeviceLineAdapter.MyViewHolder> {

    private List<Device> deviceList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView hostname, ipAddress, isRobot;

        public MyViewHolder(View view) {
            super(view);
            hostname = (TextView) view.findViewById(R.id.hostname);
            ipAddress = (TextView) view.findViewById(R.id.ipAddress);
            isRobot = (TextView) view.findViewById(R.id.isRobot);
        }
    }


    public DeviceLineAdapter(List<Device> deviceList) {
        this.deviceList = deviceList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_line_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Device device = deviceList.get(position);
        holder.hostname.setText(device.getHostname());
        holder.ipAddress.setText(device.getIpAddressString());
        holder.isRobot.setText(Boolean.toString(device.isRobot()));
    }

    @Override
    public int getItemCount() {
        return deviceList.size();
    }
}