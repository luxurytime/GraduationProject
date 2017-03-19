package com.batty.ling.client;

import android.app.Fragment;
import android.os.Bundle;
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

import model.MessageModel;
import model.StudentModel;


public class SigninFragment extends Fragment {


    public static StudentModel studentModel = new StudentModel();
    private TextView mTextView;
    private Button mButton;
    private String checkinfo;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        View signinLayout = inflater.inflate(R.layout.fragment_signin, container, false);

        mTextView = (TextView) signinLayout.findViewById(R.id.checkTextView);
        studentModel.setStudentMac("");
        checkinfo = "\n" + " " + this.getString(R.string.student_id) + " : " + studentModel.getStudentId() + "\n" +
                " " + this.getString(R.string.student_name) + " : "  + studentModel.getStudentName() + "\n" +
                " " + this.getString(R.string.student_mac) + " : "  + studentModel.getStudentMac()+ "\n\n";
        mTextView.setText(checkinfo.toString());

        mButton = (Button) signinLayout.findViewById(R.id.button);
        mButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO:socket通信,将checkinfo发送到服务器
                new Thread(new  Runnable() {
                    @Override
                    public void run() {

                        try {
                            String ip = "192.168.43.1";
                            Socket socket = new Socket(ip, 8000);

                            //建立输出流
                            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket
                                    .getOutputStream());
                            Log.e("id", studentModel.getStudentId());
                            Log.e("name", studentModel.getStudentName());
                            objectOutputStream.writeObject(studentModel);
                            objectOutputStream.flush();

                            //建立输入流
                            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                            Object obj = objectInputStream.readObject();
                            if (obj != null) {
                                studentModel = (StudentModel) obj;//把接收到的对象转化为annouce
//                                Log.e("id", annouce.getAnnoucemsg().toString());
                            }
                            objectInputStream.close();
                            objectOutputStream.close();
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
                mButton.setText("已签到");
//                mButton.setClickable(false);
            }
        });
        return signinLayout;
    }

}