package com.batty.ling.server;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class SigninActivity extends AppCompatActivity {

    private static String signinInfoStr = "";
    private static String oldIdStr = "";
    private TextView mTextView;

    private final Timer timer = new Timer();
    private TimerTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //actionbar设置
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.ActionBar));
        actionBar.setTitle(R.string.signin_result);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.show();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);


        mTextView = (TextView) findViewById(R.id.signTextView);

        if (MainActivity.studentInfo.getStudentId() != null && MainActivity.studentInfo.getStudentName() != null
    && MainActivity.studentInfo.getStudentId() != oldIdStr) {
            signinInfoStr = signinInfoStr + " ID :" + MainActivity.studentInfo.getStudentId() + "   " +
                    " Name: " + MainActivity.studentInfo.getStudentName() + "\n";
            oldIdStr = MainActivity.studentInfo.getStudentId();
        }
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                mTextView.setText(signinInfoStr);
                Log.e("handler", "TRUE");
                super.handleMessage(msg);
            }
        };

        task = new TimerTask() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        };


        timer.schedule(task, 1000, 1000);

//        mTextView.setText(text);
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
