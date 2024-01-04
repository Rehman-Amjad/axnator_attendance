package com.example.axonatormobileattendance.Model;

public class AddClassData {

    String classYear;
    String addClass;

    public AddClassData(String classYear, String addClass) {
        this.classYear = classYear;
        this.addClass = addClass;
    }

    public AddClassData() {
    }

    public String getClassYear() {
        return classYear;
    }

    public void setClassYear(String classYear) {
        this.classYear = classYear;
    }

    public String getAddClass() {
        return addClass;
    }

    public void setAddClass(String addClass) {
        this.addClass = addClass;
    }
}
