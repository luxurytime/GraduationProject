package model;

import java.io.Serializable;
/**
 * Created by Ling on 2017/3/17 0017.
 */

public class StudentInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String studentId;
    private String studentName;
    private String studentMac;
    private String location;
    private String massage;
    private String infoAnnounce;

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
    public void setStudentMac(String studentMac ) {
        this.studentMac = studentMac;
    }
    public String getStudentMac() {
        return studentMac;
    }
    public void setLocation(String location ) {
        this.location = location;
    }
    public String getLocation() {
        return location;
    }
    public void setMassage(String massage ) {
        this.massage = massage;
    }
    public String getMassage() {
        return massage;
    }
    public void setInfoAnnounce(String infoAnnounce ) {
        this.infoAnnounce = infoAnnounce;
    }
    public String getInfoAnnounce() {
        return infoAnnounce;
    }
}
