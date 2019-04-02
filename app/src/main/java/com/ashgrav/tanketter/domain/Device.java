package com.ashgrav.tanketter.domain;

import android.os.Parcel;
import android.os.Parcelable;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Device implements Parcelable {
    private String hostAddress;
    private InetAddress ipAddress;
    private String hostname;
    private boolean isRobot;


    public Device() {
    }

    public Device(InetAddress ipAddress, String hostname, String mac) {
        this.ipAddress = ipAddress;
        this.hostname = hostname;
        this.hostAddress = mac;
        this.isRobot = false;
    }

    protected Device(Parcel in) {
        hostAddress = in.readString();
        try {
            ipAddress = Inet4Address.getByAddress(in.createByteArray());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        hostname = in.readString();
        isRobot = in.readByte() != 0;
    }

    public static final Creator<Device> CREATOR = new Creator<Device>() {
        @Override
        public Device createFromParcel(Parcel in) {
            return new Device(in);
        }

        @Override
        public Device[] newArray(int size) {
            return new Device[size];
        }
    };

    public byte[] getIpAddress() {
        return this.ipAddress.getAddress();
    }

    public InetAddress getInetAddress(){
        return this.ipAddress;
    }
    public String getIpAddressString() {
        return ipAddress.getHostAddress();
    }

    public void setIpAddress(InetAddress ipAddress) {
        this.ipAddress = ipAddress;
    }


    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getMac() {
        return hostAddress;
    }

    public void setMac(String mac) {
        this.hostAddress = mac;
    }

    public String getHostAddress() {
        return hostAddress;
    }

    public void setHostAddress(String hostAddress) {
        this.hostAddress = hostAddress;
    }

    public boolean isRobot() {
        return isRobot;
    }

    public void setRobot(boolean robot) {
        isRobot = robot;
    }

    @Override
    public String toString() {
        return "\n"+"Device{" +
                "hostAddress='" + hostAddress + '\'' +
                ", ipAddress=" + ipAddress.getHostAddress() +
                ", hostname='" + hostname + '\'' +
                ", isRobot=" + isRobot +
                '}'+"\n";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(hostAddress);
        dest.writeByteArray(ipAddress.getAddress());
        dest.writeString(hostname);
        dest.writeByte((byte) (isRobot ? 1 : 0));

    }
}
