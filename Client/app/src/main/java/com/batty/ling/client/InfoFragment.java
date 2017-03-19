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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

import model.AnnounceInfo;
import model.StudentModel;

import static com.batty.ling.client.SigninFragment.studentModel;


public class InfoFragment extends Fragment {

    private Button mGetButton;
    private TextView mAnnouceTextView;
    private final Timer timer = new Timer();
    private TimerTask task;
    public static AnnounceInfo announceInfo = new AnnounceInfo();

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View infoLayout = inflater.inflate(R.layout.fragment_info, container, false);
        mAnnouceTextView = (TextView)infoLayout.findViewById(R.id.annouceTextView);
        mGetButton = (Button) infoLayout.findViewById(R.id.publishButton);
        mGetButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO:socket通信,将checkinfo发送到服务器
                new Thread(new  Runnable() {
                    @Override
                    public void run() {

                        try {
                            String ip = "192.168.43.1";
                            Socket socket = new Socket(ip, 5000);
                            Log.e("?","connect success");
                            //建立输入流
                            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                            Object obj = objectInputStream.readObject();
                            if (obj != null) {
                                announceInfo = (AnnounceInfo) obj;//把接收到的对象转化为annouce
                                Log.e("announce", announceInfo.getAnnoucemsg());
                            }
                            objectInputStream.close();
                            socket.close();
                        }catch (ClassNotFoundException e){
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (UnknownHostException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }).start();
                mAnnouceTextView.setText(announceInfo.getAnnoucemsg());
            }
        });

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                mAnnouceTextView.setText(announceInfo.getAnnoucemsg());
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


        return infoLayout;
    }
}
