package com.ashgrav.tanketter;

import com.ashgrav.tanketter.domain.Device;
import com.ashgrav.tanketter.pinger.HttpHandler;

public class ControllHandler {
    private HttpHandler httpHandler;
    private String deviceIp;

    public ControllHandler(String deviceIp) {
        this.httpHandler = new HttpHandler();
        this.deviceIp = deviceIp;
    }


    public void goForward(){
        httpHandler.httpSend(getDeviceIp(),"F");
    }

    public void goLeft(){
        httpHandler.httpSend(getDeviceIp(),"L");
    }
    public void goRight(){
        httpHandler.httpSend(getDeviceIp(),"R");
    }
    public void goBack(){
        httpHandler.httpSend(getDeviceIp(),"B");
    }

    public void stop(){
        httpHandler.httpSend(getDeviceIp(),"S");
    }


    public String getDeviceIp() {
        return deviceIp;
    }

    public void setDeviceIp(String deviceIp) {
        this.deviceIp = deviceIp;
    }
}
