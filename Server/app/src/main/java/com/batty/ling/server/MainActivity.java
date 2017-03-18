package com.batty.ling.server;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import model.StudentInfo;



public class MainActivity extends AppCompatActivity {

    public static StudentInfo studentInfo = new StudentInfo();

    private Button mButton1, mButton2, mButton3, mButton4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton1 = (Button)findViewById(R.id.button1);
        mButton1.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,ServerActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        mButton2 = (Button)findViewById(R.id.button2);
        mButton2.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,SigninActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        mButton3 = (Button)findViewById(R.id.button3);
        mButton3.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,DiscussActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        mButton4 = (Button)findViewById(R.id.button4);
        mButton4.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,InfoActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });


        new Thread(new  Runnable() {
            @Override
            public void run() {

                while(true) {
                    try {
                        ServerSocket serverSocket = new ServerSocket(8000);
                        Socket socket = serverSocket.accept();

                        //建立输入流
                        ObjectInputStream objectInputStream = new ObjectInputStream(
                                new BufferedInputStream(socket.getInputStream()));
                        Object obj = objectInputStream.readObject();
                        if (obj != null) {
                            studentInfo = (StudentInfo) obj;//把接收到的对象转化为studentInfo
                            Log.e("id", studentInfo.getStudentId().toString());
                        }

                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (UnknownHostException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }}).start();
    }

}
