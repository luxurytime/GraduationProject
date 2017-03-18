package com.batty.ling.client;
import model.StudentInfo;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import static com.batty.ling.client.LoginActivity.studentInfo;

public class SigninFragment extends Fragment {

    private TextView mTextView;
    private Button mButton;
    private String checkinfo;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        View signinLayout = inflater.inflate(R.layout.fragment_signin, container, false);

        mTextView = (TextView) signinLayout.findViewById(R.id.checkTextView);
        studentInfo.setStudentMac("");
        studentInfo.setLocation("");
        checkinfo = "\n" + " " + this.getString(R.string.student_id) + " : " + studentInfo.getStudentId() + "\n" +
                " " + this.getString(R.string.student_name) + " : "  + studentInfo.getStudentName() + "\n" +
                " " + this.getString(R.string.student_mac) + " : "  + studentInfo.getStudentMac()+ "\n" +
                " " + this.getString(R.string.student_location) + " : "  + studentInfo.getLocation() + "\n";
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
                            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket
                                    .getOutputStream());
                            Log.e("id",studentInfo.getStudentId());
                            Log.e("name",studentInfo.getStudentName());

                            objectOutputStream.writeObject(studentInfo);

//                            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
//                            Object obj = objectInputStream.readObject();
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
                mButton.setText("已签到");
                mButton.setClickable(false);
            }
        });
        return signinLayout;
    }

}