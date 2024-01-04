package com.example.axonatormobileattendance.Model;

public class TeacherAttendanceClass {

    String currentMonth;
    String currentTime;
    String teacherId;
    String currentDate;
    String teacherPresent;
    String date;

    public TeacherAttendanceClass(String currentMonth, String currentTime, String teacherId, String currentDate, String teacherPresent, String date) {
        this.currentMonth = currentMonth;
        this.currentTime = currentTime;
        this.teacherId = teacherId;
        this.currentDate = currentDate;
        this.teacherPresent = teacherPresent;
        this.date = date;
    }

    public TeacherAttendanceClass() {
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

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getTeacherPresent() {
        return teacherPresent;
    }

    public void setTeacherPresent(String teacherPresent) {
        this.teacherPresent = teacherPresent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
