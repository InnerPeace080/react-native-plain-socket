package com.thudo.socket.react;

/** @file ....c
 *  @brief ....c source file.
 *
 *  File/module comments.
 *
 *  @author Phuongtq
 *	@mobile 01684499886
 *  @note No Note at the moment
 *  @bug No known bugs.
 *
 * <pre>
 * MODIFICATION HISTORY:
 *
 * Ver   Who  	  Date       Changes
 * ----- --------- ---------- -----------------------------------------------
 * 1.00  Phuongtq ../../.... First release
 *
 *
 *</pre>
 ******************************************************************************/

/***************************** Include Files *********************************/
import android.support.annotation.Nullable;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/*****************************************************************************/

// TODO: manager open, close in, out put stream , thread.

public class ReactSocketModule extends ReactContextBaseJavaModule{

    private ReactContext _reactContext;

    private String _serverIP = "localhost";
    private Integer _serverPort = 8000;

    private Socket _socket = null;

    private InputStream _inputStream ;
    private OutputStream _outputStream;


    private Thread _listenThread;
    private boolean _listenThreadStop = false;
    private boolean _isListenThreadStop = false;


    private byte [] _inputBuffer = new byte[1048576];

//    private byte [] _outputBuffer = new byte[1048576];

    // this variable use for control send event to js (wait until event process finish)
    private boolean _lockEventSignal = false;

    public ReactSocketModule(ReactApplicationContext reactContext){
        super(reactContext);
        _reactContext  =reactContext;
    }

    @Override
    public String getName(){
        return "ReactPlainSocket";
    }


    @ReactMethod
    public void init(String serverIP,int serverPort){
        _serverIP = serverIP;
        _serverPort = serverPort;
    }


    @ReactMethod
    public void connect() {
        Thread connect = new Thread(new ClientThread());
        connect.start();
    }

    @ReactMethod
    public void disconnect() {
        FullLog.i("Socket disconnecting");
        try {

            FullLog.v("stop listen thread");

            _listenThreadStop =true;

            while (_isListenThreadStop == false){}

            _socket.close();

        }
        catch(Exception ex){
            FullLog.e("" + ex);
        }
        FullLog.i("Socket disconnected");
    }

    @ReactMethod
    public void releaseEventSignal(){
        _lockEventSignal = false;
    }

    @ReactMethod
    public void sendData(ReadableArray sentData){
        assert _outputStream != null : "_outputStream null ";

        FullLog.v("sentData : " + sentData);

        try {
            for (int count = 0; count < sentData.size(); count++) {
                _outputStream.write(sentData.getInt(count));
            }
        }
        catch (Exception ex){
            FullLog.e("" + ex);
        }
    }

    /** thread for connect socket
     *
     *
     *  @param
     *
     *  @return void
     */

    public class ClientThread implements Runnable{
        @Override
        public void run() {
            FullLog.v("start");
            // try to connect server
            FullLog.i("try to connect server");
            try{
                _socket = new Socket(_serverIP,_serverPort);

                FullLog.i("Connect to " + _serverIP + ":" + _serverPort +  " success ");
                // if connect successful . create thread to listen data
                _inputStream = _socket.getInputStream();
                _outputStream = _socket.getOutputStream();

                _listenThread = new Thread(new ListenThread());
                _listenThread.start();

            }
            catch (Exception ex){
                FullLog.e("" + ex);
                return;
            }



        }
    }

    /**
     *
     *
     *  @param
     *  @return Void.
     *
     */

    public class ListenThread implements Runnable{
        @Override
        public void run() {
            FullLog.v("ListenThread run");
            int inputAvaiableSize = 0;
            _isListenThreadStop = false;
            _listenThreadStop = false;
            try {
                do {
                    inputAvaiableSize = _inputStream.available();

                    if (inputAvaiableSize > 0) {
                        FullLog.d("number byte receiver " + inputAvaiableSize);

                        // if have no event is sending -> read data and send another event
                        if (_lockEventSignal == false) {
                            _lockEventSignal = true;
                            _inputStream.read(_inputBuffer);

                            FullLog.d("receiver data = " + _inputBuffer);

                            //convert data in buffer to string
                            String stringData ;
                            try {
                                stringData = new String(_inputBuffer,0,inputAvaiableSize, "ISO-8859-1");
                            }
                            catch (Exception ex){
                                FullLog.e(ex.getMessage());
                                return;
                            }

                            // sent event to js
                            FullLog.v("Sent event to JS");

                            WritableMap map2JS = new WritableNativeMap();

                            map2JS.putString("DataReceiver",stringData);

                            sendEvent(_reactContext, "ReactSocketModule_onDataReceiver", map2JS);
                        }

                    }
                }
                while (_listenThreadStop == false);

//                _inputStream.close();
                FullLog.v("Listen thread stop");
                _isListenThreadStop = true;

            }
            catch(Exception ex){
                FullLog.e("" + ex);
            }
        }
    }


    /**
     *
     *
     *  @param
     *  @return Void.
     *
     */

    private void sendEvent( ReactContext reactContext,String eventName,@Nullable WritableMap param){
        reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, param);

    }

}
