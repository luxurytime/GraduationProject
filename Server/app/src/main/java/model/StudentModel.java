package model;

import java.io.Serializable;

/**
 * Created by Ling on 2017/3/19 0019.
 */

public class StudentModel implements Serializable {
    private String studentId;
    private String studentName;
    private String studentMac;

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
}
