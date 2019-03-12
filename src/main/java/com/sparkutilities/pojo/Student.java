package com.sparkutilities.pojo;

import java.io.Serializable;

public class Student implements Serializable
{
    private int student_id;
    private  String student_name;
    private String student_city;
    private long student_fees;
    private long student_phone;

    public Student(int student_id, String student_name, String student_city, long student_fees, long student_phone) {
        this.student_id = student_id;
        this.student_name = student_name;
        this.student_city = student_city;
        this.student_fees = student_fees;
        this.student_phone = student_phone;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getStudent_city() {
        return student_city;
    }

    public void setStudent_city(String student_city) {
        this.student_city = student_city;
    }

    public long getStudent_fees() {
        return student_fees;
    }

    public void setStudent_fees(long student_fees) {
        this.student_fees = student_fees;
    }

    public long getStudent_phone() {
        return student_phone;
    }

    public void setStudent_phone(long student_phone) {
        this.student_phone = student_phone;
    }
}