package react.socket.thudo.com.socket;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;

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
}
