package com.batty.ling.server;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ServerActivity extends AppCompatActivity {

    private Button mButton1, mButton2;
    private EditText mSSIDView, mPasswordView;
    private String SSID, Password;
    private Context mContext = null;
    private WifiApAdmin wifiApAdmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //actionbar设置
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.ActionBar));
        actionBar.setTitle(R.string.wifi_setting);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.show();

        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_server);

        mSSIDView = (EditText)findViewById(R.id.SSID) ;
        mPasswordView = (EditText)findViewById(R.id.Password);
        mButton1 = (Button)findViewById(R.id.button1);
        mButton1.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                 wifiApAdmin = new WifiApAdmin(mContext);
                SSID = mSSIDView.getText().toString();
                Password = mPasswordView.getText().toString();
                wifiApAdmin.startWifiAp(SSID, Password);


            }
        });
        mButton2 = (Button)findViewById(R.id.button2);
        mButton2.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
//                // TODO Auto-generated method stub
               wifiApAdmin.closeWifiAp(mContext);
            }
        });
    }


    //actionbar上的点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //返回按钮
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
