package com.example.axonatormobileattendance.Model;

public class StudentDataClass
{
    String studentName;
    String studentId;
    String studentDateOfBirth;
    String studentQualification;
    String studentPassword;
    String className;

    public StudentDataClass(String studentName, String studentId, String studentDateOfBirth, String studentQualification, String studentPassword, String className) {
        this.studentName = studentName;
        this.studentId = studentId;
        this.studentDateOfBirth = studentDateOfBirth;
        this.studentQualification = studentQualification;
        this.studentPassword = studentPassword;
        this.className = className;
    }

    public StudentDataClass() {
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentDateOfBirth() {
        return studentDateOfBirth;
    }

    public void setStudentDateOfBirth(String studentDateOfBirth) {
        this.studentDateOfBirth = studentDateOfBirth;
    }

    public String getStudentQualification() {
        return studentQualification;
    }

    public void setStudentQualification(String studentQualification) {
        this.studentQualification = studentQualification;
    }

    public String getStudentPassword() {
        return studentPassword;
    }

    public void setStudentPassword(String studentPassword) {
        this.studentPassword = studentPassword;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
