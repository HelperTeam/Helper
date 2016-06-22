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

/**
 * Created by 小怪兽 on 2016/5/28.
 */
public class SignActivity extends Activity implements View.OnClickListener {

    private int second = 60;
    private EditText phone;
    private EditText code;
    private Button getCode;
    private EditText password;
    private Button sign;
    private String result_data;
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 0:
                    getCode.setClickable(false);
                    getCode.setText("请等待"+second+"s");
                    break;
                case 1:
                    getCode.setClickable(true);
                    getCode.setText("获取验证码");
                    second = 60;
                    break;
                case 2:
                    try {
                                JSONObject json = new JSONObject(result_data);
                                String status = json.getString("status");
                                String data = json.getString("data");
                                if(status.equals("success")){
                                    Toast.makeText(SignActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                                    IntentUtils.getIntent(SignActivity.this,MainActivity.class);
                                    finish();
                        }
                        else{
                            Toast.makeText(SignActivity.this,data,Toast.LENGTH_SHORT).show();
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
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        init();
    }
    private void init(){
        phone = (EditText)findViewById(R.id.sign_phone);
        code = (EditText)findViewById(R.id.sign_code);
        getCode = (Button)findViewById(R.id.sign_getCode);
        password = (EditText)findViewById(R.id.sign_password);
        sign = (Button)findViewById(R.id.sign_ok);
        getCode.setOnClickListener(this);
        sign.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sign_getCode:
                VerifyCode();
                break;
            case R.id.sign_ok:
                Next();
                break;
            default:
                break;
        }
    }
    private void VerifyCode(){
        final String phoneNumber = phone.getText().toString();
        if(phoneNumber.length() == 11){
            new Thread(){
                @Override
                public void run(){
                    HttpTool.SendMobileCode(phoneNumber);
                    for(int i = 0;i < 60;i++){
                        try{
                            handler.sendEmptyMessage(0);
                            Thread.sleep(1000);
                            second--;
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                    handler.sendEmptyMessage(1);
                }
            }.start();
        }
        else {
            Toast.makeText(SignActivity.this,"请输入正确的手机号码",Toast.LENGTH_SHORT).show();
        }
    }
    private void Next(){
        String phoneNumber = phone.getText().toString();
        String verifyCode = code.getText().toString();
        String psw = password.getText().toString();
        if(phoneNumber.length() == 11){
            if(verifyCode.length() == 6){
                if(psw.length() >= 6){
                    new Thread(){
                        @Override
                        public void run(){
                            String phoneNumber = phone.getText().toString();
                            String verifyCode = code.getText().toString();
                            String psw = password.toString().toString();
                            result_data = HttpTool.AppRegister(phoneNumber,verifyCode,psw);
                            handler.sendEmptyMessage(2);
                        }
                    }.start();
                }else {
                    Toast.makeText(SignActivity.this,"请输入不少于6位的密码",Toast.LENGTH_SHORT).show();
                    password.setText("");
                }
            }else {
                Toast.makeText(SignActivity.this,"请输入正确的验证码",Toast.LENGTH_SHORT).show();
                code.setText("");
            }
        }else {
            Toast.makeText(SignActivity.this,"请输入正确的电话号码",Toast.LENGTH_SHORT).show();
            phone.setText("");
        }
    }
}
