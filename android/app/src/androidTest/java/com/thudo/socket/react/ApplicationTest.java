package com.thudo.socket.react;

import android.content.Context;
import android.test.AndroidTestCase;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.Callback;

import org.mockito.Mockito;
//import static org.fest.assertions.api.Assertions.assertThat;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends AndroidTestCase {
    ReactSocketModule mReactNativeSocketModule;
    ReactApplicationContext mRContext;


    Context context;

    public void setUp() throws Exception {
        super.setUp();

        context = getContext();

        assertNotNull(context);

    }

    public void testSomething() {

        mRContext = new ReactApplicationContext(context);
        mReactNativeSocketModule = new ReactSocketModule(mRContext);


        Callback setCallback = Mockito.mock(Callback.class);
//        setCallback.invoke();
//        mReactNativeSocketModule.init("192.168.2.61", 5000, null, null, setCallback, null);
        FullLog.i("connect to server");
        mReactNativeSocketModule.connect();

//        Mockito.verify(setCallback, Mockito.times(1)).invoke();


        try {
            Thread.sleep(2000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        Mockito.verify(setCallback, Mockito.times(1)).invoke();

        FullLog.i("disconnect to server");
        mReactNativeSocketModule.disconnect();



    }





}