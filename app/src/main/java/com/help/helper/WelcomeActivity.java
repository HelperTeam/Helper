package com.help.helper;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.help.helper.utils.IntentUtils;

/**
 * Created by 小怪兽 on 2016/6/14.
 */
public class WelcomeActivity extends Activity implements View.OnClickListener {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Button sign = (Button)findViewById(R.id.welcome_sign);
        sign.setOnClickListener(this);
        Button login = (Button)findViewById(R.id.welcome_login);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.welcome_sign:
                IntentUtils.getIntent(WelcomeActivity.this,SignActivity.class);
                break;
            case R.id.welcome_login:
                IntentUtils.getIntent(WelcomeActivity.this,LoginActivity.class);
                break;
            default:
                break;
        }
    }
}
