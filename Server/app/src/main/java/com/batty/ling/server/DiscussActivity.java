package com.batty.ling.server;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

import model.MessageModel;
import model.StudentModel;
import java.util.Date;
import java.text.SimpleDateFormat;
public class DiscussActivity extends AppCompatActivity {

    public static MessageModel messageModel = new MessageModel();
    private final Timer timer = new Timer();
    private TimerTask task;
    private TextView mMsgTextView;
    private TextView mSendMsg;
    private Button mSendButton;
    private String ip;
    private String tempMessage;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discuss);

        //actionbar设置
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.ActionBar));
        actionBar.setTitle(R.string.discuss_room);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.show();

        mMsgTextView = (TextView)findViewById(R.id.msgTextview);
        mSendMsg = (TextView)findViewById(R.id.sendMsg);
        Thread t= new Thread(new Runnable() {
            @Override
            public void run() {
                ChatRoomListen();
            }});
        t.start();

        mSendButton = (Button)findViewById(R.id.sendButton);

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");//设置时间格式
                messageModel.setMsgTime(df.format(new Date()).toString());
                messageModel.setMsg("13331062" + " "
                        + "服务器" + " " + messageModel.getMsgTime()
                        + "\n" + mSendMsg.getText().toString() + "\n\n");
                messageModel.setTotalMsg(messageModel.getTotalMsg() + messageModel.getMsg());
                mMsgTextView.setText(messageModel.getTotalMsg());
                mSendMsg.setText("");
            }
        });



        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                mMsgTextView.setText(messageModel.getTotalMsg());

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
        timer.schedule(task, 10000, 10000);



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

    public void ChatRoomListen() {
        try {
            // 创建服务器端ServerSocket连接,监听端口号6000
            ServerSocket serverSocket = new ServerSocket(6000);
            // 轮询等待客户端请求
            while(true) {
                // 等待客户端请求,无请求则闲置;有请求到来时,返回一个对该请求的socket连接
                final Socket clientSocket = serverSocket.accept();
                ip = clientSocket.getInetAddress().getHostAddress();
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
                                MessageModel temp = new MessageModel();
                                temp = (MessageModel) obj;
                                messageModel.setTotalMsg(temp.getTotalMsg());
                            }
                            //建立输出流
                            ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                                    clientSocket.getOutputStream());
                            objectOutputStream.writeObject(messageModel);
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
