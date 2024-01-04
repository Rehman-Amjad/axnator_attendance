package com.example.axonatormobileattendance.Model;

public class TeacherTakeStudentAttClass {

    String teacherId;
    String teacherName;
    String currentMonth;
    String currentTime;
    String attendance;
    String studentId;

    public TeacherTakeStudentAttClass(String teacherId, String teacherName, String currentMonth, String currentTime, String attendance, String studentId) {
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.currentMonth = currentMonth;
        this.currentTime = currentTime;
        this.attendance = attendance;
        this.studentId = studentId;
    }

    public TeacherTakeStudentAttClass() {
    }


    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getCurrentMonth() {
        return currentMonth;
    }

    public void setCurrentMonth(String currentMonth) {
        this.currentMonth = currentMonth;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}
