package com.batty.ling.client;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import model.AnnounceInfo;
import model.MessageModel;
import model.StudentModel;

public class DiscussFragment extends Fragment {

    public static MessageModel messageModel = new MessageModel();
    private final Timer timer = new Timer();
    private TimerTask task;
    private TextView mMsgTextView;
    private TextView mSendMsg;
    private Button mSendButton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View messageLayout = inflater.inflate(R.layout.fragment_discuss, container, false);


        mMsgTextView = (TextView)messageLayout.findViewById(R.id.msgTextview);
        mSendMsg = (TextView)messageLayout.findViewById(R.id.sendMsg);

        mSendButton = (Button)messageLayout.findViewById(R.id.sendButton);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messageModel.setStudentId(SigninFragment.studentModel.getStudentId());
                messageModel.setStudentName(SigninFragment.studentModel.getStudentName());
                SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");//设置时间格式
                messageModel.setMsgTime(df.format(new Date()).toString());
                messageModel.setMsg(messageModel.getStudentId() + " "
                        + messageModel.getStudentName() + " " + messageModel.getMsgTime()
                        + "\n" + mSendMsg.getText().toString() + "\n\n");
                messageModel.setTotalMsg(messageModel.getTotalMsg() + messageModel.getMsg());
                mMsgTextView.setText(messageModel.getTotalMsg());
                mSendMsg.setText("");
                ChatRoomSend();
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
        timer.schedule(task, 1000, 1000);

        Log.e("isfinish","yes");
         return messageLayout;
    }
    public void ChatRoomSend() {
        // TODO:socket通信,将checkinfo发送到服务器
        new Thread(new  Runnable() {
            @Override
            public void run() {
                    try {
                        String ip = "192.168.43.1";
                        Socket socket = new Socket(ip, 6000);

                        //建立输出流
                        Log.e("message",messageModel.getTotalMsg());
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket
                                .getOutputStream());
                        objectOutputStream.writeObject(messageModel);
                        objectOutputStream.flush();

                        objectOutputStream.close();
                        socket.close();
                    } catch (UnknownHostException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
            }
        }).start();

    }

}
