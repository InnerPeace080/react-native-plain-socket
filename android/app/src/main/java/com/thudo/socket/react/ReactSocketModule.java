package com.thudo.socket.react;

import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by phuongtq on 12/15/2015.
 */

public class ReactSocketModule extends ReactContextBaseJavaModule{

    public ReactSocketModule(ReactApplicationContext reactContext){
        super(reactContext);
    }

    @Override
    public String getName(){
        return "SocketAndroid";
    }

    @ReactMethod
    public void show(){
        Log.d("ReactSocketModule","I am here");
    }

    @ReactMethod
    public void connect() {
        InetAddress host;
        try {
            host = InetAddress.getLocalHost();
        }
        catch(Exception ex){
            Log.d("ReactSocketModule","InetAddress.getLocalHost();" + ex);
            return;
        }
        Socket socket = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;

        //establish socket connection to server
        try {
            socket = new Socket("192.168.2.61", 5000);
        }
        catch(Exception ex){
            Log.d("ReactSocketModule","establish socket connection to server" + ex);
        }
    }
}
