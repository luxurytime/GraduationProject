package com.batty.ling.server;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

import model.StudentModel;

public class SigninActivity extends AppCompatActivity {

    public static String clientIp = "";
    private static String signinInfoStr = "";
    private static String oldIdStr = "";
    private TextView mTextView;

    public static StudentModel studentModel = new StudentModel();
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
        Thread t= new Thread(new Runnable() {
            @Override
            public void run() {
                ListenClientSignIn();
            }});
        t.start();

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                if (studentModel.getStudentId() != null && studentModel.getStudentName() != null
                        && !signinInfoStr.contains(studentModel.getStudentId())) {
                    signinInfoStr = signinInfoStr + " ID :" + studentModel.getStudentId() + "   "
                            + " Name: " + studentModel.getStudentName() + "\n";
                    oldIdStr = studentModel.getStudentId();
                }
                mTextView.setText(signinInfoStr);
                Log.e("sign", "TRUE");

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

    public void ListenClientSignIn() {
        try {
            // 创建服务器端ServerSocket连接,监听端口号8000
            ServerSocket serverSocket = new ServerSocket(8000);
            // 轮询等待客户端请求
            while(true) {
                // 等待客户端请求,无请求则闲置;有请求到来时,返回一个对该请求的socket连接
                final Socket clientSocket = serverSocket.accept();

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //建立输入流
                            ObjectInputStream objectInputStream = new ObjectInputStream(new
                                    BufferedInputStream(clientSocket.getInputStream()));
                            Object obj = objectInputStream.readObject();
                            if (obj != null) {
                                //把接收到的对象转化为studentModel
                                studentModel = (StudentModel) obj;
                            }
                            //建立输出流
                            ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                                    clientSocket.getOutputStream());
                            objectOutputStream.writeObject(studentModel);
                            objectOutputStream.flush();

                            objectInputStream.close();
                            objectOutputStream.close();
                            clientSocket.close();

                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
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

