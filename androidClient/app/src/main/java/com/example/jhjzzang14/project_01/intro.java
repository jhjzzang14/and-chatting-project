package com.example.jhjzzang14.project_01;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.os.Message;
//import java.util.logging.Handler;
import android.os.Handler;
/**
 * Created by jhjzzang14 on 2015-12-03.
 */
public class intro extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.intro);

        Handler handler = new Handler(){
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                startActivity(new Intent(intro.this, login.class));
                //overridePendingTransition(R.anim.abc_fade_in, 0);
                finish();
            }
        };
        handler.sendEmptyMessageDelayed(0,3000);
    }
}
