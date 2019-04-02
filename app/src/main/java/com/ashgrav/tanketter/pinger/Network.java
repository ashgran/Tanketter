package com.ashgrav.tanketter.pinger;


import android.util.Log;

import com.ashgrav.tanketter.domain.Device;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.*;

import static android.content.ContentValues.TAG;

public class Network {
    private List<Device> devices;
    private boolean threadsDone;
    private byte[] ip;


    private ExecutorService executorService;
    private List<Future<Device>> futures;


    public Network() {
        this.devices = Collections.synchronizedList(new ArrayList<Device>());

        this.futures = new ArrayList<Future<Device>>();
        this.executorService = Executors.newFixedThreadPool(255);

        this.ip = getLocalIpAddress();

    }

    public Network(List<Device> devices) {
        this.devices = Collections.synchronizedList(devices);
        this.threadsDone = false;

    }

    private byte[] getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        if(inetAddress instanceof Inet4Address){
                            byte[] ip = inetAddress.getAddress();
                            Log.i(TAG, "***** IP="+ ip);
                            return ip;
                        }
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e(TAG, ex.toString());
        }
        return null;
    }

    public void startScan() {

        for(int i=0;i<=254;i++) {
            if(ip!=null && i!=ip[3] ){
                final int j = i;  // i as non-final variable cannot be referenced from inner class

                Future<Device> deviceFuture =executorService.submit(new PingerThread("Subnet"+j+"Thread",ip,j));
                this.futures.add(deviceFuture);
            }
        }
        for (Future<Device> future: this.futures){
            try {
                    if(future.get() != null){
                        this.devices.add(future.get());
                    }
            }catch (InterruptedException | ExecutionException e){
                e.printStackTrace();
            }
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE,TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setThreadsDone(true);
    }



    public List<Device> getDevices() {
        if(!devices.isEmpty()){
            return devices;
        }
        return null;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    public boolean isThreadsDone() {
        return threadsDone;
    }

    public void setThreadsDone(boolean threadsDone) {
        this.threadsDone = threadsDone;
    }
}
