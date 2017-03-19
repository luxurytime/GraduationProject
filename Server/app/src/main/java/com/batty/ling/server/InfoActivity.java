package com.batty.ling.server;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import model.AnnounceInfo;

public class InfoActivity extends AppCompatActivity {

    private EditText mEditText;
    private Button mButton;
    public static AnnounceInfo announceInfo = new AnnounceInfo();

    protected void onCreate(Bundle savedInstanceState) {

        //actionbar设置
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.ActionBar));
        actionBar.setTitle(R.string.announce);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.show();



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Thread t= new Thread(new Runnable() {
            @Override
            public void run() {
                ListenClientGetAnnounce();
            }});
        t.start();

        mEditText = (EditText)findViewById(R.id.announceMsgView);
        mEditText.setText(announceInfo.getAnnoucemsg());
        mEditText.setCursorVisible(true);
        mButton = (Button)findViewById(R.id.publishButton);

        mButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                announceInfo.setAnnouncemsg(mEditText.getText().toString());
                mEditText.setCursorVisible(false);
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
    public void ListenClientGetAnnounce() {
        try {
            // 创建服务器端ServerSocket连接,监听端口号5000
            ServerSocket serverSocket = new ServerSocket(5000);
            // 轮询等待客户端请求
            while(true) {
                // 等待客户端请求,无请求则闲置;有请求到来时,返回一个对该请求的socket连接
                final Socket clientSocket = serverSocket.accept();

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
//                            //建立输入流
//                            ObjectInputStream objectInputStream = new ObjectInputStream(new
//                                    BufferedInputStream(clientSocket.getInputStream()));
//                            Object obj = objectInputStream.readObject();
//                            if (obj != null) {
//                                //把接收到的对象转化为studentModel
//                                announceInfo = (AnnounceInfo) obj;
//                            }
                            //建立输出流
                            ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                                    clientSocket.getOutputStream());
                            objectOutputStream.writeObject(announceInfo);
                            Log.e("announceInfo",announceInfo.getAnnoucemsg());
                            objectOutputStream.flush();

//                            objectInputStream.close();
                            objectOutputStream.close();
                            clientSocket.close();

                        } catch (UnknownHostException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {

                        }
                    }
                });
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
