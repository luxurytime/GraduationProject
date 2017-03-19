package model;

import java.io.Serializable;

/**
 * Created by Ling on 2017/3/19 0019.
 */

public class MessageModel implements Serializable {

    public MessageModel() {
        studentId = "";
        studentName = "";
        msgTime = "";
        msg = "";
        totalMsg = "";
    }
    private String studentId;
    private String studentName;
    private String msgTime;
    private String msg;
    private String totalMsg;

    public void setStudentId(String studentId ) {
        this.studentId = studentId;
    }
    public String getStudentId() {
        return studentId;
    }
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    public String getStudentName() {
        return studentName;
    }
    public void setMsgTime(String msgTime) {
        this.msgTime = msgTime;
    }
    public String getMsgTime() {
        return msgTime;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getMsg() {
        return msg;
    }
    public void setTotalMsg(String totalMsg) {
        this.totalMsg = totalMsg;
    }
    public String getTotalMsg() {
        return totalMsg;
    }
}