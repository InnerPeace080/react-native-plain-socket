package com.thudo.socket.react;

import android.content.Context;
import android.test.AndroidTestCase;

import com.facebook.react.bridge.ReactApplicationContext;

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

//        assertEquals(false, true);
        mRContext = new ReactApplicationContext(context);
        mReactNativeSocketModule = new ReactSocketModule(mRContext);
        mReactNativeSocketModule.show();

        mReactNativeSocketModule.connect();

    }

//    public ApplicationTest() {
//        super(Application.class);
//
//
//        mRContext = new ReactApplicationContext(getContext());
//
//        mReactNativeSocketModule = new ReactSocketModule(mRContext);
//
//        mReactNativeSocketModule.show();
//
//
//    }
}