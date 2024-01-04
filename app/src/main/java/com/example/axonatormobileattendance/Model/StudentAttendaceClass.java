package com.example.axonatormobileattendance.Model;

public class StudentAttendaceClass
{
    String currentMonth;
    String currentTime;
    String studentId;
    String studentSubject;
    String currentDate;
    String studentPresent;
    String date;
    String currentLatitude;
    String currentLongitude;
    String city;
    String state;

    public StudentAttendaceClass(String currentMonth, String currentTime, String studentId, String studentSubject, String currentDate, String studentPresent, String date, String currentLatitude, String currentLongitude, String city, String state) {
        this.currentMonth = currentMonth;
        this.currentTime = currentTime;
        this.studentId = studentId;
        this.studentSubject = studentSubject;
        this.currentDate = currentDate;
        this.studentPresent = studentPresent;
        this.date = date;
        this.currentLatitude = currentLatitude;
        this.currentLongitude = currentLongitude;
        this.city = city;
        this.state = state;
    }

    public StudentAttendaceClass() {
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

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentSubject() {
        return studentSubject;
    }

    public void setStudentSubject(String studentSubject) {
        this.studentSubject = studentSubject;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getStudentPresent() {
        return studentPresent;
    }

    public void setStudentPresent(String studentPresent) {
        this.studentPresent = studentPresent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCurrentLatitude() {
        return currentLatitude;
    }

    public void setCurrentLatitude(String currentLatitude) {
        this.currentLatitude = currentLatitude;
    }

    public String getCurrentLongitude() {
        return currentLongitude;
    }

    public void setCurrentLongitude(String currentLongitude) {
        this.currentLongitude = currentLongitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
