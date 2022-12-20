package com.example.sqllite.Model;

public class Student {
    public String maSV;
    public String tenSV;
    public float diemTB;
    public String Khoa;

    public Student(String maSV, String tenSV, float diemTB,String khoa) {
        this.maSV = maSV;
        this.tenSV = tenSV;
        this.diemTB = diemTB;
        Khoa = khoa;
    }

    public Student() {
    }
}
