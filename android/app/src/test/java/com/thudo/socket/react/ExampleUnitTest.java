package com.thudo.socket.react;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */

//@SuppressLint({"ClipboardManager", "DeprecatedClass"})
@RunWith(RobolectricTestRunner.class)
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})

public class ExampleUnitTest {
    private ReactSocketModule mReactSocketModule;

    @Before
    public void setUp() {
        mReactSocketModule = new ReactSocketModule(ReactTestHelper.createCatalystContextForTest());
    }

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
        mReactSocketModule.show();
    }
}