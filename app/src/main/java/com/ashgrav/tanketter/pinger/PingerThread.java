package com.ashgrav.tanketter.pinger;


import com.ashgrav.tanketter.domain.Device;

import java.net.InetAddress;
import java.util.concurrent.Callable;

public class PingerThread implements Callable<Device> {
    private String threadName;
    private int host;
    private byte[] ipAddress;
    private boolean foundDevice;
    protected Device device;


    public PingerThread(String threadName, byte[] ipAddress, int host) {
        this.threadName = threadName;
        this.ipAddress = ipAddress;
        this.host = host;
        this.foundDevice = false;
        this.device = null;
    }

    public void run() {
        if (this.getHost() > 0) {
            try {
                this.ipAddress[3] = this.getHostByte();
                InetAddress address = InetAddress.getByAddress(ipAddress);
                String adress = address.toString().substring(1);
                if (address.isReachable(5000)) {
                    this.setFoundDevice(true);
                    this.setDevice(new Device());
                    this.getDevice().setIpAddress(address);
                    this.getDevice().setHostname(address.getHostName());
                    this.getDevice().setMac(address.getHostAddress());

                    System.out.println(this.getThreadName() + " " + this.getDevice().getIpAddressString() + " is on the network");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public int getHost() {
        return host;
    }

    public byte getHostByte(){
        return (byte) host;
    }

    public void setHost(int host) {
        this.host = host;
    }

    public byte[] getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(byte[] ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public boolean isFoundDevice() {
        return foundDevice;
    }

    public void setFoundDevice(boolean foundDevice) {
        this.foundDevice = foundDevice;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    @Override
    public Device call() throws Exception {
        this.run();

        return device;

    }
}
