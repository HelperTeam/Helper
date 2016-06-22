package com.help.helper;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.help.helper.net.HttpTool;
import com.help.helper.utils.IntentUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends Activity implements View.OnClickListener {

    private EditText phone;
    private EditText password;
    private Button login;
    private String result_data;
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 0:
                    try {
                        JSONObject json = new JSONObject(result_data);
                        String status = json.getString("status");
                        String data = json.getString("data");
                        if (status.equals("success")){
                            Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                            IntentUtils.getIntent(LoginActivity.this,MainActivity.class);
                            finish();
                        }else {
                            Toast.makeText(LoginActivity.this,data,Toast.LENGTH_SHORT).show();
                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }
    private void init(){
        phone = (EditText) findViewById(R.id.phone);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                Login();
                break;
            default:
                break;
        }
    }
    private void Login(){
        String phoneNumber = phone.getText().toString();
        if (phoneNumber.length() == 11){
            new Thread(){
                public void run(){
                    String phoneNumber = phone.getText().toString();
                    String psw = password.getText().toString();
                    result_data = HttpTool.Login(phoneNumber,psw);
                    handler.sendEmptyMessage(0);
                }
            }.start();
        }else {
            Toast.makeText(LoginActivity.this,"请检查手机号码是否正确",Toast.LENGTH_SHORT).show();
        }
    }
}

